package com.capgemini.brahma.util;

import com.capgemini.brahma.model.User;

public final class CamelBootUtil {
    
    private CamelBootUtil() {
        //restrict instantiation
    }
    
    public static final String QUESTION_MARK_SIGN = "?";
    public static final String AMPERSAND_SIGN = "&";
    public static final String SPACE = " ";

    public static final User getUser(String exchangeBody){
        String[] fullName = exchangeBody.split(SPACE);
        User user = new User();
        String firstName = fullName[0];
        user.setForeName(firstName);
        String lastName = fullName[1];
        user.setSurname(lastName);
        return user;
    }
    
}
