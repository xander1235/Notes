package com.notes.notes.services;

import com.notes.notes.models.UserCredentials;
import com.notes.notes.pojos.requests.ReqUpdateUser;
import com.notes.notes.pojos.requests.ReqUser;
import com.notes.notes.pojos.responses.ResUser;
import com.notes.notes.repositories.UserCredentialsRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static com.notes.notes.utils.NotesUtils.checkIfNotNull;
import static com.notes.notes.utils.NotesUtils.checkIfNull;

@Service
@Slf4j
public class UserService {

    private final UserCredentialsRepository userCredentialsRepository;

    @Autowired
    public UserService(UserCredentialsRepository userCredentialsRepository) {
        this.userCredentialsRepository = userCredentialsRepository;
    }

    public ResUser createUser(ReqUser reqUser) {
        checkIfNotNull(userCredentialsRepository.findOneByUserName(reqUser.getUserName()), "User already exists user name: " + reqUser.getUserName());
        UserCredentials userCredentials = UserCredentials.builder()
                .userName(reqUser.getUserName())
                .password(reqUser.getPassword())
                .build();
        userCredentialsRepository.save(userCredentials);
        return ResUser.builder()
                .userName(reqUser.getUserName())
                .message("User Created successfully")
                .build();
    }

    public ResUser updateUser(ReqUpdateUser reqUser) {
        UserCredentials userCredentials = checkIfNull(userCredentialsRepository.findOneByUserName(reqUser.getUserName()), "User does not exists");
        if (!reqUser.getOldPassword().equals(userCredentials.getPassword())) {
            log.error("Password does not match");
            throw new RuntimeException("Password does not match");
        }
        userCredentials.setPassword(reqUser.getNewPassword());
        userCredentialsRepository.save(userCredentials);
        return ResUser.builder()
                .userName(reqUser.getUserName())
                .message("Password updated successfully")
                .build();
    }

    public String userLogin(String userName, String password) {
        UserCredentials userCredentials = checkIfNull(userCredentialsRepository.findOneByUserName(userName), "User does not exists");
        if (!password.equals(userCredentials.getPassword())) {
            log.error("Password does not match");
            throw new RuntimeException("Password does not match");
        }
        String transactionId = UUID.randomUUID().toString();
        userCredentials.setTransactionId(transactionId);
        userCredentialsRepository.save(userCredentials);
        return transactionId;
    }

    public String userLogout(String transactionId) {
        UserCredentials userCredentials = userCredentialsRepository.findOneByTransactionId(transactionId);
        checkIfNull(userCredentials, "Already logged out.");
        userCredentials.setTransactionId(null);
        userCredentialsRepository.save(userCredentials);
        return "User successfully logged out";
    }

    public UserCredentials getUserByTransactionId(String transactionId) {
        return userCredentialsRepository.findOneByTransactionId(transactionId);
    }
}
