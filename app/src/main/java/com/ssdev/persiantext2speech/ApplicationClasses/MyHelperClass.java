package com.ssdev.persiantext2speech.ApplicationClasses;

import com.ssdev.persiantext2speech.ModelClasses.InsertResult;
import com.ssdev.persiantext2speech.ModelClasses.ReadResult;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;

import java.lang.reflect.Field;
import java.util.HashMap;

/**
 * Created by saeed on 08-Oct-15.
 */
public class MyHelperClass {

    //Helper Methods
    public SoapSerializationEnvelope InsertMaker(HashMap<String,String> map){
        SoapSerializationEnvelope soapEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER10);

        PropertyInfo pmod = new PropertyInfo();
        pmod.setName("Punctuation");
        pmod.setNamespace("http://schemas.datacontract.org/2004/07/AtalkWCFService");
        pmod.setValue(map.get("punctuation"));

        PropertyInfo rmod = new PropertyInfo();
        rmod.setName("SampleRate");
        rmod.setNamespace("http://schemas.datacontract.org/2004/07/AtalkWCFService");
        rmod.setValue(map.get("samplerate"));


        SoapObject obj = new SoapObject("http://tempuri.org/", "punctuationLevel");
        obj.addProperty(pmod);

        SoapObject obj2 = new SoapObject("http://tempuri.org/", "rateSampleLevel");
        obj2.addProperty(rmod);

        SoapObject Request = new SoapObject("http://tempuri.org/", "Insert");
        Request.addProperty("inputText", map.get("Text"));
        Request.addProperty("EnSapiName", "Eloquence");
        Request.addProperty("volume",map.get("volume"));
        Request.addProperty("pitch", map.get("pitch"));
        Request.addProperty("rate", map.get("rate"));
        Request.addSoapObject(obj);
        Request.addSoapObject(obj2);
        Request.addProperty("userName", "saeedek");
        Request.addProperty("userPassword", "1478963");

        soapEnvelope.setOutputSoapObject(Request);
        soapEnvelope.dotNet = true;
        soapEnvelope.implicitTypes = true;
        soapEnvelope.setAddAdornments(false);

        return soapEnvelope;

    }

    public SoapSerializationEnvelope ReadMaker(HashMap<String, String> map) {
        SoapSerializationEnvelope soapEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER10);

        SoapObject Request = new SoapObject("http://tempuri.org/", "Read");
        Request.addProperty("index", PersianText2Speech.getInstance().getApiCommunicator().CurrentIndex);
        Request.addProperty("userName", "saeedek");
        Request.addProperty("userPassword", "1478963");

        soapEnvelope.setOutputSoapObject(Request);
        soapEnvelope.dotNet = true;
        soapEnvelope.implicitTypes = true;
        soapEnvelope.setAddAdornments(false);
        return soapEnvelope;
    }

    public Object ResultMaker(SoapObject obj,Class dClass) {
        if(dClass.equals(InsertResult.class)){
            InsertResult res=new InsertResult();
            try{
                res.ClientIndex=Integer.valueOf(obj.getPropertyAsString("ClientIndex"));
                res.haveError=obj.getPropertyAsString("haveError");
                res.ErrorString=obj.getPropertyAsString("ErrorString");
                return res;
            }
            catch (Exception e){
                String err=e.getLocalizedMessage();
            }

        }
        else{
            ReadResult res=new ReadResult();
            res.ErrorString=obj.getPropertyAsString("ErrorString");
            res.IsLocked=obj.getPropertyAsString("IsLocked");
            res.IsSynthesizeFinished=obj.getPropertyAsString("IsSynthesizeFinished");
            res.Result=obj.getPropertyAsString("Result");
            res.haveError= obj.getPropertyAsString("haveError");
            return res;
        }
        return null;
    }
}
