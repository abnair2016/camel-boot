package com.capgemini.brahma.util;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import java.sql.ResultSet;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import com.capgemini.brahma.model.User;

public class UserRowMapperTest {

    @Mock
    private ResultSet mockResultset;
    
    private UserRowMapper userRowMapper; 
    
    
    @Before
    public void setUp() throws Exception {
        initMocks(this);
        userRowMapper = new UserRowMapper();
    }

    @Test
    public void testMapRow() throws Exception {
        when(mockResultset.getString("first_name")).thenReturn("John");
        when(mockResultset.getString("last_name")).thenReturn("Doe");
        
        User user = userRowMapper.mapRow(mockResultset, 1);
        assertNotNull(user);
        assertEquals("John", user.getForeName());
        assertEquals("Doe", user.getSurname());
        assertEquals("John Doe", user.getFullName());
    }
    
}
