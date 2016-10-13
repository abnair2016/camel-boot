package com.capgemini.brahma.model;

import org.apache.camel.dataformat.bindy.annotation.CsvRecord;
import org.apache.camel.dataformat.bindy.annotation.DataField;
import org.springframework.stereotype.Component;

@Component
@CsvRecord(separator=",", skipFirstLine=true, crlf="WINDOWS")
public class User{
    
    @DataField(pos=1, required=true, trim=true, defaultValue="")
    private String foreName;
  
    @DataField(pos=2, required=true, trim=true, defaultValue="")
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
   
    public String getFullName(){
        return new StringBuilder().append(this.foreName)
                                    .append(" ")
                                    .append(this.surname)
                                    .toString();
    }
    
    @Override
    public String toString(){
        return new StringBuilder().append("[USER:: First Name: ")
                                    .append(getForeName())
                                    .append("; Last Name: ")
                                    .append(getSurname())
                                    .append("]")
                                    .toString();
    }
}