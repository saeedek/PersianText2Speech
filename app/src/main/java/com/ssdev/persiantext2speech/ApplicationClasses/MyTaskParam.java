package com.ssdev.persiantext2speech.ApplicationClasses;

import com.ssdev.persiantext2speech.Interfaces.onApiRes;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapSerializationEnvelope;

/**
 * Created by saeed on 08-Oct-15.
 */
//Helper Classes
public class MyTaskParam{
    public String soap_action;
    public SoapSerializationEnvelope env;
    public String URL;
    public onApiRes lis;
    public Class dClass;

    public MyTaskParam(String soap_action, SoapSerializationEnvelope env,String u,onApiRes o,Class d) {
        this.env=new SoapSerializationEnvelope(SoapEnvelope.VER10);
        this.soap_action = soap_action;
        this.env = env;
        this.URL=u;
        this.lis=o;
        this.dClass=d;
    }
    public MyTaskParam(){

    }
}