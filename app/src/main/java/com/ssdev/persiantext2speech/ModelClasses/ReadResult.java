package com.ssdev.persiantext2speech.ModelClasses;

/**
 * Created by saeed on 08-Oct-15.
 */
public class ReadResult {
    public String ErrorString,IsLocked,IsSynthesizeFinished,Result,haveError;

    public String getIsLocked() {
        return IsLocked;
    }

    public String getIsSynthesizeFinished() {
        return IsSynthesizeFinished;
    }

    public String getResult() {
        return Result;
    }

    public String getHaveError() {
        return haveError;
    }

    public ReadResult(){

    }
    public int getCount(){
        return 5;
    }
    public String getParam(int i){
        switch (i){
            case 0:
                return this.ErrorString;
            case 1:
                return this.IsLocked;
            case 2:
                return this.IsSynthesizeFinished;
            case 3:
                return this.Result;
            case 4:
                return this.haveError;
            default:
                return null;
        }
    }
}
