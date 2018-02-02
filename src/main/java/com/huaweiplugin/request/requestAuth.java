package com.huaweiplugin.request;

import com.huaweiplugin.Dto.AuthHandle;
import com.huaweiplugin.Utils.Constant;
import com.huaweiplugin.Utils.JsonUtil;
import com.huaweiplugin.Utils.StreamClosedHttpResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class requestAuth {

    @Autowired
    private AuthHandle authHandle;

    public HashMap login(String IP, String port, String appID, String secret) throws Exception {

        String urlLogin = "https://"+IP+":"+port+"/iocm/app/sec/v1.1.0/login";

        com.huaweiplugin.Utils.HttpsUtil httpsUtil = new com.huaweiplugin.Utils.HttpsUtil();

        httpsUtil.initSSLConfigForTwoWay();

        Map<String, String> param = new HashMap<>();
        param.put("appId", appID);
        param.put("secret", secret);

        com.huaweiplugin.Utils.StreamClosedHttpResponse responseLogin = httpsUtil.doPostFormUrlEncodedGetStatusLine(urlLogin, param);

        //resolve the value of accessToken from responseLogin.
        Map<String, String> data = new HashMap<>();
        data = com.huaweiplugin.Utils.JsonUtil.jsonString2SimpleObj(responseLogin.getContent(), data.getClass());

        String state = String.valueOf(responseLogin.getStatusLine());
        data.put("state",state);


        return (HashMap) data;
    }

    public HashMap refreshToken(String IP, String port, Object appID, Object secret, Object refreshToken) throws Exception {

        String urlRefreshToken = "https://"+IP+":"+port+"/iocm/app/sec/v1.1.0/refreshToken";
        com.huaweiplugin.Utils.HttpsUtil httpsUtil = new com.huaweiplugin.Utils.HttpsUtil();
        httpsUtil.initSSLConfigForTwoWay();

        Map<String, Object> param_reg = new HashMap<>();
        param_reg.put("appId", appID);
        param_reg.put("secret", secret);
        param_reg.put("refreshToken", refreshToken);

        String jsonRequest = JsonUtil.jsonObj2Sting(param_reg);
        StreamClosedHttpResponse bodyRefreshToken = httpsUtil.doPostJsonGetStatusLine(urlRefreshToken, jsonRequest);
        Map<String, String> data = new HashMap<>();
        data = com.huaweiplugin.Utils.JsonUtil.jsonString2SimpleObj(bodyRefreshToken.getContent(), data.getClass());

        String state = String.valueOf(bodyRefreshToken.getStatusLine());
        data.put("state",state);

        return (HashMap) data;
    }

    public void logout() throws Exception {

        String IP = Constant.URL;
        String port = Constant.PORT;
        String accessToken = authHandle.getaccessToken();

        String url = "https://"+IP+":"+port+"/iocm/app/sec/v1.1.0/logout";

        com.huaweiplugin.Utils.HttpsUtil httpsUtil = new com.huaweiplugin.Utils.HttpsUtil();
        httpsUtil.initSSLConfigForTwoWay();

        Map<String, String> hedder = new HashMap<>();
        hedder.put("Content-Type", "application/json");

        Map<String, Object> param_reg = new HashMap<>();
        param_reg.put("accessToken",accessToken);

        String jsonRequest = JsonUtil.jsonObj2Sting(param_reg);

        StreamClosedHttpResponse bodyRefreshToken = httpsUtil.doPostJsonGetStatusLine(url, hedder, jsonRequest);

        System.out.println(bodyRefreshToken);
    }

}
