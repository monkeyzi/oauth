package com.monkeyzi.oauth.im.packet;

import java.io.Serializable;

public class ImBaseBody extends MsgType implements Serializable {
    private static final Long serialVersionUID = 1L;

    private long timeStamp;


    public long getTimeStamp() {
        if (timeStamp==0){
            timeStamp=System.currentTimeMillis();
        }
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }
}
