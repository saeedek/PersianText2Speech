package com.ssdev.persiantext2speech.ModelClasses;

import java.util.HashMap;

/**
 * Created by saeed on 08-Oct-15.
 */
public class Insert {
    public String inputText,EnSapiName,volume,pitch,rate,punctuationLevel,rateSampleLevel,userName,userPassword;
    public HashMap<String,String> map;
    public Insert(String inputText, String enSapiName, String volume, String pitch, String rate, String punctuationLevel, String rateSampleLevel, String userName, String userPassword) {
        this.inputText = inputText;
        EnSapiName = enSapiName;
        this.volume = volume;
        this.pitch = pitch;
        this.rate = rate;
        this.punctuationLevel = punctuationLevel;
        this.rateSampleLevel = rateSampleLevel;
        this.userName = userName;
        this.userPassword = userPassword;
        mapMaker();
    }

    private void mapMaker() {
        map=new HashMap<>();
        map.put("Text",this.inputText);
        map.put("volume",this.volume);
        map.put("pitch",this.pitch);
        map.put("rate",this.rate);
        map.put("punctuation",this.punctuationLevel);
        map.put("samplerate",this.rateSampleLevel);
    }

    public HashMap<String, String> getMap() {
        return map;
    }
}
