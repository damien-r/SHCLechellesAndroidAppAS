package com.rossier.data;

import com.orm.SugarRecord;

/**
 * Created by Florian on 29.10.2015.
 */
public class Token extends SugarRecord<Token> {
    String token;

    public Token() {
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
