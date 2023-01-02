package org.abodah.meeting.entities;

import org.apache.catalina.User;

public enum UserType {
    Admin("admin"), Normal("Normal"), Anonymous("Guest");

    private String code;

    private UserType(String code){
        this.code = code;
    }
}
