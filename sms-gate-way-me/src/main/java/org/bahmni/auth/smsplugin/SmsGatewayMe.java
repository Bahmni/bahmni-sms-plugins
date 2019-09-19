package org.bahmni.auth.smsplugin;

import com.jayway.jsonpath.JsonPath;
import org.bahmni.auth.smsinterface.SmsGateWay;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestOperations;

@Service
public class SmsGatewayMe implements SmsGateWay {

    private String sendMessageUrl = "https://smsgateway.me/api/v4/message/send";
    private String deviceListUrl = "https://smsgateway.me/api/v4/device/search";

    @Autowired
    private RestOperations restOperations;

    @Autowired
    private SmsGatewayMeConfig config;

    @Override
    public void sendSMS(String countryCode, String mobileNumber, String message) {
        String request = "[{" +
                " \"message\":"+message+"," +
                " \"phone_number\":"+mobileNumber+"," +
                " \"device_id\":"+getDeviceId()+
                "}]";
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", config.getToken());
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(request, headers);

        restOperations.postForObject(sendMessageUrl, entity, String.class);
    }

    private Integer getDeviceId() {
        String request = String.format(deviceListUrl);
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", config.getToken());
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        HttpEntity<String> entity = new HttpEntity<>(request, headers);
        String json = restOperations.postForObject(deviceListUrl, entity, String.class);
        return JsonPath.read(json, "$.results[0].id");
    }
}
