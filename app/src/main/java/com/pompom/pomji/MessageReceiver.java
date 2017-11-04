package com.pompom.pomji;

import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;

@SuppressWarnings("ParcelCreator")
public class MessageReceiver extends ResultReceiver {
    private main.Message message;

    public MessageReceiver(main.Message message){
        super(new Handler());
        this.message = message;
    }

    @Override
    protected void onReceiveResult(int resultCode, Bundle resultData){
        message.displayMessage(resultCode, resultData);
    }
}
