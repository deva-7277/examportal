package com.exam.model;

import org.springframework.security.core.GrantedAuthority;

import java.io.Serializable;

public class Authority implements GrantedAuthority , Serializable {

    private String authority;

    public Authority(String authority) {
        this.authority = authority;
    }

    @Override
    public String getAuthority() {
        return this.authority;
    }
}
