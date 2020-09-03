package com.notes.notes.contollers;

import com.notes.notes.pojos.requests.ReqUpdateUser;
import com.notes.notes.pojos.requests.ReqUser;
import com.notes.notes.pojos.responses.ResUser;
import com.notes.notes.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

@Slf4j
@RestController("userCredentialsController")
public class UserController {

    private final UserService userService;

    @Value("${self.auth.token}")
    private String selfAuthToken;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(value = "/user")
    public ResponseEntity<ResUser> createUser(@Valid @RequestBody ReqUser reqUser, @RequestHeader("token") String token) {
        checkApiToken(token);
        ResUser resUser = userService.createUser(reqUser);
        return new ResponseEntity<>(resUser, HttpStatus.CREATED);
    }

    @PutMapping(value = "/user")
    public ResponseEntity<ResUser> updateUser(@Valid @RequestBody ReqUpdateUser reqUser, @RequestHeader("token") String token) {
        checkApiToken(token);
        ResUser resUser = userService.updateUser(reqUser);
        return new ResponseEntity<>(resUser, HttpStatus.OK);
    }

    @GetMapping(value = "/user/login/{user_name}")
    public ResponseEntity<String> userLogin(
            @NotBlank(message = "user name can not be blank") @PathVariable("user_name") String userName,
            @NotBlank(message = "password can not be blank") @RequestParam("password") String password) {
        String transactionId = userService.userLogin(userName, password);
        return new ResponseEntity<>(transactionId, HttpStatus.OK);
    }

    @GetMapping(value = "/user/logout")
    public ResponseEntity<String> userLogout(@RequestParam("transaction_id") String transactionId) {
        String message = userService.userLogout(transactionId);
        return  new ResponseEntity<>(message, HttpStatus.OK);
    }

    private void checkApiToken(String token){
        if (!selfAuthToken.equals(token)) {
            log.error("Authorization failure");
            throw new RuntimeException("Authorization failure");
        }
    }
}
