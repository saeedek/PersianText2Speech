package com.ssdev.persiantext2speech.ApplicationClasses;

import android.os.AsyncTask;

import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

/**
 * Created by saeed on 08-Oct-15.
 */
public class MyAsyncTask extends AsyncTask<MyTaskParam, String, SoapObject> {

    @Override
    protected SoapObject doInBackground(MyTaskParam... params) {
        HttpTransportSE httpTransport = new HttpTransportSE(params[0].URL);
        httpTransport.debug = true;
        try {
            httpTransport.call(params[0].soap_action, params[0].env);
            SoapObject res = (SoapObject) params[0].env.getResponse();
            params[0].lis.onSuccess(new MyHelperClass().ResultMaker(res,params[0].dClass));
            return null;
        } catch (IOException e) {
            params[0].lis.onFailed(e.getMessage());
            return null;
        } catch (XmlPullParserException e) {
            params[0].lis.onFailed(e.getMessage());
            return null;
        }
    }

    @Override
    protected void onPostExecute(SoapObject soapObject) {
        super.onPostExecute(soapObject);

    }
}