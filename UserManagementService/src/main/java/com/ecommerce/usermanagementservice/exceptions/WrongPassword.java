package com.ecommerce.usermanagementservice.exceptions;

public class WrongPassword extends Exception{
    public WrongPassword(String message){
        super(message);
    }
}
