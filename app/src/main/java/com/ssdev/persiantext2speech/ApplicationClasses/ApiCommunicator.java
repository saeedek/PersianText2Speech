package com.ssdev.persiantext2speech.ApplicationClasses;

import android.content.Context;
import android.os.AsyncTask;


import com.ssdev.persiantext2speech.Interfaces.onApiRes;
import com.ssdev.persiantext2speech.ModelClasses.Insert;
import com.ssdev.persiantext2speech.ModelClasses.Read;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.HashMap;

/**
 * Created by saeed on 01-Oct-15.
 */
public class ApiCommunicator {

    public Context mContext;
    public String soap_action;

    public ApiCommunicator(Context applicationContext) {
        this.mContext = applicationContext;
    }

    public static String URL = "http://www.farsireader.com:3465/Atalkservice";
    public static String NAMESPACE = "http://tempuri.org/";
    public static int CurrentIndex;

    public void doRequest(Class dClass,String method, Object ob, final onApiRes onApiRes) {
        soap_action = "http://tempuri.org/IAtalkService/" + method;
        switch (method) {
            case "Insert":
                MyTaskParam c = new MyTaskParam();
                c.env = new MyHelperClass().InsertMaker(((Insert) ob).getMap());
                c.URL = this.URL;
                c.soap_action = soap_action;
                c.lis = onApiRes;
                c.dClass=dClass;
                new MyAsyncTask().execute(c);
                break;
            case "Read":
                MyTaskParam c2 = new MyTaskParam();
                c2.env = new MyHelperClass().ReadMaker(((Read) ob).getMap());
                c2.URL = this.URL;
                c2.soap_action = soap_action;
                c2.lis = onApiRes;
                c2.dClass=dClass;
                new MyAsyncTask().execute(c2);
                break;
        }
    }
}
