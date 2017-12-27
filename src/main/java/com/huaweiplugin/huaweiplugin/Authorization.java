package com.huaweiplugin.huaweiplugin;

import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Amila on 12/27/2017.
 */

@RestController
public class Authorization {

//    @PathVariable String Token
    @RequestMapping(value = "/generate/iotmifetokenviatoken", method = RequestMethod.GET)
    public String apiAuthorization(@RequestHeader("Accept-Encoding") HttpHeaders headers){

        System.out.println(headers.get("IotMife-Token"));
        System.out.println(headers.get("IotMife-RefreshToken"));

        return headers.toString();
    }
}
