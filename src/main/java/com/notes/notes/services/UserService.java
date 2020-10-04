package com.notes.notes.services;

import com.notes.notes.exceptions.BadRequestException;
import com.notes.notes.models.UserCredentials;
import com.notes.notes.pojos.requests.ReqUpdateUser;
import com.notes.notes.pojos.requests.ReqUser;
import com.notes.notes.pojos.responses.ResUser;
import com.notes.notes.repositories.UserCredentialsRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static com.notes.notes.constants.ExceptionConstants.*;
import static com.notes.notes.constants.ResponseConstants.*;
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
        checkIfNotNull(userCredentialsRepository.findOneByUserName(reqUser.getUserName()), USER_ALREADY_EXISTS + reqUser.getUserName());
        UserCredentials userCredentials = UserCredentials.builder()
                .userName(reqUser.getUserName())
                .password(reqUser.getPassword())
                .build();
        userCredentialsRepository.save(userCredentials);
        return ResUser.builder()
                .userName(reqUser.getUserName())
                .message(USER_CREATED)
                .build();
    }

    public ResUser updateUser(ReqUpdateUser reqUser) {
        UserCredentials userCredentials = checkIfNull(userCredentialsRepository.findOneByUserName(reqUser.getUserName()), USER_NOT_EXISTS);
        if (!reqUser.getOldPassword().equals(userCredentials.getPassword())) {
            log.error(PASSWORD_MISMATCH);
            throw new BadRequestException(PASSWORD_MISMATCH);
        }
        userCredentials.setPassword(reqUser.getNewPassword());
        userCredentialsRepository.save(userCredentials);
        return ResUser.builder()
                .userName(reqUser.getUserName())
                .message(PASSWORD_UPDATED)
                .build();
    }

    public String userLogin(String userName, String password) {
        UserCredentials userCredentials = checkIfNull(userCredentialsRepository.findOneByUserName(userName), USER_NOT_EXISTS);
        if (!password.equals(userCredentials.getPassword())) {
            log.error(PASSWORD_MISMATCH);
            throw new BadRequestException(PASSWORD_MISMATCH);
        }
        String transactionId = UUID.randomUUID().toString();
        userCredentials.setTransactionId(transactionId);
        userCredentialsRepository.save(userCredentials);
        return transactionId;
    }

    public String userLogout(String transactionId) {
        UserCredentials userCredentials = userCredentialsRepository.findOneByTransactionId(transactionId);
        checkIfNull(userCredentials, ALREADY_LOGGED_OUT);
        userCredentials.setTransactionId(null);
        userCredentialsRepository.save(userCredentials);
        return LOGGED_OUT;
    }

    public UserCredentials getUserByTransactionId(String transactionId) {
        return userCredentialsRepository.findOneByTransactionId(transactionId);
    }
}
