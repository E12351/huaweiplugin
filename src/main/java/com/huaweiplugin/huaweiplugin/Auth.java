package com.huaweiplugin.huaweiplugin;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Amila on 1/4/2018.
 */
public class Auth {
    @SuppressWarnings({ "resource", "unchecked" })
    public static void main(String args[]) throws Exception {

        // Two-Way Authentication
        com.huaweiplugin.Utils.HttpsUtil httpsUtil = new com.huaweiplugin.Utils.HttpsUtil();

        httpsUtil.initSSLConfigForTwoWay();

        String appId    = com.huaweiplugin.Utils.Constant.APPID;
        String secret   = com.huaweiplugin.Utils.Constant.SECRET;
        String urlLogin = com.huaweiplugin.Utils.Constant.APP_AUTH;

        Map<String, String> param = new HashMap<>();
        param.put("appId", appId);
        param.put("secret", secret);

        com.huaweiplugin.Utils.StreamClosedHttpResponse responseLogin = httpsUtil.doPostFormUrlEncodedGetStatusLine(urlLogin, param);

        System.out.println("app auth success,return accessToken:");
        System.out.print( responseLogin.getStatusLine() );
        System.out.println(responseLogin.getContent());
        System.out.println();

        //resolve the value of accessToken from responseLogin.
        Map<String, String> data = new HashMap<>();
        data = com.huaweiplugin.Utils.JsonUtil.jsonString2SimpleObj(responseLogin.getContent(), data.getClass());
        String accessToken = data.get("accessToken");

        System.out.println("accessToken : " + accessToken);
    }
}
