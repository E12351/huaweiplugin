package com.huaweiplugin.huaweiplugin;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import com.huaweiplugin.Utils.Constant;
import com.huaweiplugin.services.requests;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledTasks {

    @Autowired
    private requests request;

    @Autowired
    private AuthHandle authHandle;

    private static final Logger log = LoggerFactory.getLogger(ScheduledTasks.class);

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    @Scheduled(fixedRate = 3500000, initialDelay = 3500000)
    public void reportCurrentTime() throws Exception {

        String refreshtoken = authHandle.getrefreshToken();
        HashMap datarefreshToken  = request.refreshToken(Constant.URL,Constant.PORT, Constant.APPID,Constant.SECRET,refreshtoken);

        String newrefreshToken  = String.valueOf(datarefreshToken.get("refreshToken"));
        String newaccessToken  = String.valueOf(datarefreshToken.get("accessToken"));


        authHandle.setrefreshToken(newrefreshToken);
        authHandle.setaccessToken(newaccessToken);

        log.info("{} oldrefreshToken : {} newrefreshToken : {}", dateFormat.format(new Date()),refreshtoken,newrefreshToken);
    }
}