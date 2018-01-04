package com.huaweiplugin.huaweiplugin;

import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;
import org.json.JSONObject;

@RestController
public class Authorization {

    private final String IP     = "192.168.8.1";
    private final String Port   = "8080";
    private final String appId  = "app" ;
    private final String secret = "8080";

    @Autowired
    private requests request;

//    @PathVariable String Token
    @RequestMapping(value = "/generate/iotmifetokenviatoken", method = RequestMethod.GET)
    public String apiAuthorization(@RequestHeader("Accept-Encoding") HttpHeaders headers){

        System.out.println(headers.get("IotMife-Token"));
        System.out.println(headers.get("IotMife-RefreshToken"));

        return headers.toString();
    }

    @RequestMapping(value = "proxy/authenticate" ,  method = RequestMethod.POST, produces={"application/json"} )
    public String login( @RequestHeader("Accept-Encoding") HttpHeaders headers, @RequestBody String body) throws Exception {

        System.out.println(headers.get("IotMife-Token"));

        JSONObject json = new JSONObject(body);
//        System.out.println(json.get("user_name"));
//        System.out.println(json.get("password"));
        String user_name = (String) json.get("user_name");
        String password = (String) json.get("password");

//        System.out.println("passed. WTF");

        request.login(IP,Port,appId,secret);

        return body;
    }
}
