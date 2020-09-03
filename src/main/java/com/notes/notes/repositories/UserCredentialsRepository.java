package com.notes.notes.repositories;

import com.notes.notes.models.UserCredentials;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserCredentialsRepository extends JpaRepository<UserCredentials, Integer> {

    @Query(value = "select * from notes.user_credentials where user_name = ?1 limit 1", nativeQuery = true)
    UserCredentials findOneByUserName(String userName);

    @Query(value = "select * from notes.user_credentials where transaction_id = ?1 limit 1", nativeQuery = true)
    UserCredentials findOneByTransactionId(String transactionId);
}
