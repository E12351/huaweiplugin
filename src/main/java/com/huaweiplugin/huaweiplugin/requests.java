package com.huaweiplugin.huaweiplugin;

import org.json.simple.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Map;


@Service
public class requests {

    public String login(String IP, String port,String appID, String secret){

        String walletBalanceUrl = "https://"+IP+":"+port+"/iocm/app/sec/v1.1.0/login";
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("Content-Type", "x-www-form-urlencoded");

        String dataParameter = "appID="+appID+"&secret="+secret;

        HttpEntity<String> httpEntity = new HttpEntity<String>(dataParameter, httpHeaders);
        System.out.println(httpEntity);
        RestTemplate restTemplate = new RestTemplate();
        String response = restTemplate.postForObject(walletBalanceUrl, httpEntity, String.class);
        System.out.println(response);
        return response;
    }

//    https://server:port/iocm/app/sec/v1.1.0/refreshToken
    public String refreshToken(String IP, String port, Object appID, Object secret, Object refreshToken){
        String walletBalanceUrl = "https://"+IP+":"+port+"/iocm/app/sec/v1.1.0/refreshToken";

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("Content-Type", "application/json");

        JSONObject json = new JSONObject();
        json.put("appID", appID);
        json.put("secret", secret);
        json.put("refreshToken", refreshToken);

        HttpEntity <String> httpEntity = new HttpEntity <String> (json.toString(), httpHeaders);

        RestTemplate restTemplate = new RestTemplate();
        String response = restTemplate.postForObject(walletBalanceUrl, httpEntity, String.class);

        return response;
    }

    public String refreshToken1(String IP, String port, String appID, String secret, String refreshToken){

        String walletBalanceUrl = "https://"+IP+":"+port+"/iocm/app/sec/v1.1.0/refreshToken";
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("Content-Type", "application/json");

        String dataParameter = "appID="+appID+"&secret="+secret+"&refreshToken="+refreshToken;

        HttpEntity<String> httpEntity = new HttpEntity<String>(dataParameter, httpHeaders);
        System.out.println(httpEntity);
        RestTemplate restTemplate = new RestTemplate();
        String response = restTemplate.postForObject(walletBalanceUrl, httpEntity, String.class);
        System.out.println(response);
        return response;
    }

    public String logout(String IP, String port, String accessToken){

        String walletBalanceUrl = "https://"+IP+":"+port+"/iocm/app/sec/v1.1.0/logout";
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("Content-Type", "application/json");

        JSONObject json = new JSONObject();
        json.put("accessToken"   , accessToken);


        HttpEntity <String> httpEntity = new HttpEntity <String> (json.toString(), httpHeaders);

        RestTemplate restTemplate = new RestTemplate();
        String response = restTemplate.postForObject(walletBalanceUrl, httpEntity, String.class);

        return response;
    }

    public String regDirectDevice(String IP, String port, String appID, String accessToken){

        String walletBalanceUrl = "https://"+IP+":"+port+"/iocm/app/sec/v1.1.0/devices?appId={"+appID+"}";
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("app_key",appID);
        httpHeaders.set("Authorization","Bearer {"+accessToken+"}");
        httpHeaders.set("Content-Type", "application/json");

        System.out.println(httpHeaders);

        JSONObject json = new JSONObject();
        json.put("verifyCode"   , "AE10-12424-12414");
        json.put("nodeId"       , "AE10-12424-12414");
        json.put("timeout"      ,                300);


        HttpEntity <String> httpEntity = new HttpEntity <String> (json.toString(), httpHeaders);

        RestTemplate restTemplate = new RestTemplate();
        String response = restTemplate.postForObject(walletBalanceUrl, httpEntity, String.class);

        return response;
    }

    public String regNoNDirectDevice(String IP, String port, String appID, String deviceId,String accessToken){

        String walletBalanceUrl = "https://"+IP+":"+port+"/iocm/app/sec/v1.1.0/devices/{"+deviceId+"}/services/Discovery/sendCommand?appId={"+appID+"}";

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("app_key",appID);
        httpHeaders.set("Authorization","Bearer {"+accessToken+"}");
        httpHeaders.set("Content-Type", "application/json");

        JSONObject json1 = new JSONObject();
        JSONObject json2 = new JSONObject();
        JSONObject json3 = new JSONObject();

        json1.put("mode"   , "ACK");
        json1.put("from"   , "xxxxxxxxxxxxxxxxx");
        json1.put("to"     , "null");
        json1.put("toType" , "null");
        json1.put("method" , "DISCOVERY");
        json1.put("requestId"  ,"xxxxxxxxxxx");
        json1.put("callbackURL","http://server:port/na/iocm/message/confirm");

        json3.put("header",json1);

        json2.put("from","xxxxxxxxxxxx");
        json2.put("sessionID","xxxxxxxxxxxx");
        json2.put("sdp","xxxxxxxxxxxx");

        json3.put("body",json2);

        HttpEntity <String> httpEntity = new HttpEntity <String> (json3.toString(), httpHeaders);
        System.out.println(httpEntity);
        RestTemplate restTemplate = new RestTemplate();
        String response = restTemplate.postForObject(walletBalanceUrl, httpEntity, String.class);

        return response;
    }

    public String activationStatus(String IP, String port, String deviceId, String appId, String accessToken){

        String walletBalanceUrl = "https://"+IP+":"+port+"/iocm/app/sec/v1.1.0/devices/{"+deviceId+"}?appId={"+appId+"}";


        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("app_key",appId);
        httpHeaders.set("Authorization","Bearer {"+accessToken+"}");
        httpHeaders.set("Content-Type", "application/json");

//        UriComponentsBuilder builder = UriComponentsBuilder
//                .fromUriString(transactionUrl)
//                // Add query parameter
//                .queryParam("appId", appId)
//                .queryParam("walletId", "2323JK")
//                .queryParam("pageSize", "10");

        HttpEntity <String> httpEntity = new HttpEntity <String> (httpHeaders);
        System.out.println(httpEntity);
        RestTemplate restTemplate = new RestTemplate();
        String response = restTemplate.getForObject(walletBalanceUrl, String.class, httpEntity);

        return response;
    }

    public void deleteDirectDevice(String IP, String port, String deviceId, String appId, String cascade, String accessToken){
//
        String walletUrl = "https://"+IP+":"+port+"/iocm/app/sec/v1.1.0/devices/{"+deviceId+"}?appId={"+appId+"}&cascade={"+cascade+"}";

        HttpHeaders httpHeaders = new HttpHeaders();

        httpHeaders.set("app_key",appId);
        httpHeaders.set("Authorization","Bearer {"+accessToken+"}");
        httpHeaders.set("Content-Type", "application/json");

        HttpEntity <String> httpEntity = new HttpEntity <String> (httpHeaders);
        System.out.println(httpEntity);
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.delete(walletUrl, null,httpEntity);

    }
}
