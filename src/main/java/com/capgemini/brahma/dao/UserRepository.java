package com.capgemini.brahma.dao;

import java.sql.Types;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import com.capgemini.brahma.model.User;
import com.capgemini.brahma.util.UserRowMapper;

@Repository
public class UserRepository {

    private static final Logger log = LoggerFactory.getLogger(UserRepository.class);
    
    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    public int store(final User user) {
        
        log.info("Are we inside a transaction? " + TransactionSynchronizationManager.isActualTransactionActive());
        
        int storedUserCount = jdbcTemplate.update("INSERT INTO USERS(first_name, last_name) VALUES(?,?)", 
                                new Object[]{user.getForeName(), user.getSurname()}, 
                                new int[]{Types.VARCHAR, Types.VARCHAR});
        log.info("Added USER record to database:: First Name: " + user.getForeName() + " Last Name: " + user.getSurname());
        
        return storedUserCount;
    }
    
    @Transactional(readOnly=true)
    public List<User> getStoredUsers(){
        String sql = "SELECT * FROM USERS";
        List<User> users = jdbcTemplate.query(sql, new UserRowMapper());
        log.info("Found " + users.size() + " users in database!");
        users.forEach(user -> log.info("User in Database:: " + user));
        return users;
    }
    
}
