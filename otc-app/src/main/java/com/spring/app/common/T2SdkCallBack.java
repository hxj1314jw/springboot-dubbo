package com.spring.app.common;

import com.hundsun.t2sdk.interfaces.ICallBackMethod;

public class T2SdkCallBack implements ICallBackMethod {
    public void execute(com.hundsun.t2sdk.interfaces.share.event.IEvent iEvent, com.hundsun.t2sdk.impl.client.ClientSocket clientSocket) {
        System.out.print("T2SdkCallBack execute!");
    };

}
