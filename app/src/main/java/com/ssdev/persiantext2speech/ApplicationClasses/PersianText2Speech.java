package com.ssdev.persiantext2speech.ApplicationClasses;

import android.app.Application;

/**
 * Created by saeed on 01-Oct-15.
 */
public class PersianText2Speech extends Application {
    private static PersianText2Speech sInstance;
    private ApiCommunicator mCommunicator;
    @Override
    public void onCreate() {
        super.onCreate();


        mCommunicator=new ApiCommunicator(this.getApplicationContext());

        sInstance = this;

    }
    public synchronized static PersianText2Speech getInstance() {
        return sInstance;
    }
    public ApiCommunicator getApiCommunicator() {
        return mCommunicator;
    }

}
