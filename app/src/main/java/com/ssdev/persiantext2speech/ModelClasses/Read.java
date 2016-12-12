package com.ssdev.persiantext2speech.ModelClasses;

import java.util.HashMap;

/**
 * Created by saeed on 08-Oct-15.
 */
public class Read {
    String index,userName,userPassword;
    private HashMap<String, String> map;

    public Read(String index, String userName, String userPassword) {
        this.index = index;
        this.userName = userName;
        this.userPassword = userPassword;
        mapMaker();
    }

    private void mapMaker() {
        map=new HashMap<>();
        map.put("index",this.index);
    }

    public HashMap<String, String> getMap() {
        return map;
    }
}
