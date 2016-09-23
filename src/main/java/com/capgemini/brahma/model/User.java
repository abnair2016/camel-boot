package com.capgemini.brahma.model;

import org.springframework.stereotype.Component;

@Component
public class User {

    private String foreName;
  
    private String surname;
    
    public String getForeName() {
        return foreName;
    }

    public void setForeName(String foreName) {
        this.foreName = foreName;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }
   
    public String toString(){
        return new StringBuilder().append(getForeName()).append(" ").append(getSurname()).toString();
    }
    
}