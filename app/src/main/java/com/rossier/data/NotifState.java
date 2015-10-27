package com.rossier.data;

import android.content.Context;

import com.orm.SugarRecord;

/**
 * Created by Florian on 26.10.2015.
 */
public class NotifState extends SugarRecord<NotifState> {

    String name;
    boolean state;

    public NotifState() {
    }


    public NotifState(String name, boolean state) {
        this.name = name;
        this.state = state;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }
}
