package com.huaweiplugin.huaweiplugin;

import org.json.JSONException;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;
import org.json.JSONObject;

@RestController
public class Authorization {

//    @PathVariable String Token
    @RequestMapping(value = "/generate/iotmifetokenviatoken", method = RequestMethod.GET)
    public String apiAuthorization(@RequestHeader("Accept-Encoding") HttpHeaders headers){

        System.out.println(headers.get("IotMife-Token"));
        System.out.println(headers.get("IotMife-RefreshToken"));

        return headers.toString();
    }

    @RequestMapping(value = "proxy/authenticate" ,  method = RequestMethod.POST, produces={"application/json"} )
    public String login( @RequestHeader("Accept-Encoding") HttpHeaders headers, @RequestBody String body) throws JSONException {

        System.out.println(headers.get("IotMife-Token"));

        JSONObject json = new JSONObject(body);
        System.out.println(json.get("user_name"));
        System.out.println(json.get("password"));

        return body;
    }
}
