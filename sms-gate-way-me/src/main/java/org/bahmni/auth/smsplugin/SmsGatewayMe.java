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

    private String sendMessageUrl = "https://smsgateway.me/api/v3/messages/send";
    private String deviceListUrl = "https://smsgateway.me/api/v3/devices";

    @Autowired
    private RestOperations restOperations;

    @Autowired
    private SmsGatewayMeConfig config;

    @Override
    public void sendSMS(String countryCode, String mobileNumber, String message) {
        String request = String.format("email=%s&password=%s&message=%s&number=%s&device=%s", config.getEmail(), config.getPassword(), message, mobileNumber, getDeviceId());
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        HttpEntity<String> entity = new HttpEntity<>(request, headers);

        restOperations.postForObject(sendMessageUrl, entity, String.class);
    }

    private String getDeviceId() {
        String request = String.format(deviceListUrl + "?email=%s&password=%s", config.getEmail(), config.getPassword());
        String json = restOperations.getForObject(request, String.class);
        return JsonPath.read(json, "$.result.data[0].id");
    }
}
