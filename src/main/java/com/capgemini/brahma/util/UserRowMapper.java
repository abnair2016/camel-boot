package com.capgemini.brahma.util;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.capgemini.brahma.model.User;

public class UserRowMapper implements RowMapper<User>{

    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        User user = new User();
        user.setForeName((String)rs.getString("first_name"));
        user.setSurname((String)rs.getString("last_name"));
        return user;
    }
}
