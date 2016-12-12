package com.ssdev.persiantext2speech.ModelClasses;

/**
 * Created by saeed on 08-Oct-15.
 */
public class InsertResult {
    public String ErrorString,haveError;
    public int ClientIndex;

    public InsertResult(){}

    public int getClientIndex() {
        return ClientIndex;
    }

    public String getErrorString() {
        return ErrorString;
    }

    public String getHaveError() {
        return haveError;
    }

    public int getCount(){
        return 3;
    }
    public Object getParam(int i){
        switch (i){
            case 0:
                return this.ClientIndex;
            case 1:
                return this.ErrorString;
            case 2:
                return this.haveError;
            default:
                return null;
        }
    }
}
