package org.bahmni.auth.smsplugin;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestOperations;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;


@RunWith(MockitoJUnitRunner.class)
public class SmsGatewayMeTest {

    private String sendMessageUrl = "https://smsgateway.me/api/v4/message/send";
    private String deviceListUrl = "https://smsgateway.me/api/v4/device/search";

    @Mock
    private RestOperations restOperations;

    @Mock
    private SmsGatewayMeConfig config;

    @InjectMocks
    private SmsGatewayMe smsGatewayMe;

    @Test
    public void shouldSendMessageToSmsGateWayWithUserCredentials(){

        String requestForSendMessageUrl = "[{" +
                " \"message\":OTP," +
                " \"phone_number\":1234," +
                " \"device_id\":10" +
                "}]";
        String requestForDeviceListUrl = String.format(deviceListUrl);

        String deviceDetail = "{ results : [{'id':10}]}";

        HttpHeaders headersForSendMessageUrl = new HttpHeaders();
        headersForSendMessageUrl.set("Authorization", "some token");
        headersForSendMessageUrl.setContentType(MediaType.APPLICATION_JSON);

        HttpHeaders headersForDeviceListUrl = new HttpHeaders();
        headersForDeviceListUrl.set("Authorization", "some token");
        headersForDeviceListUrl.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<String> entityForSendMessageUrl = new HttpEntity<>(requestForSendMessageUrl, headersForSendMessageUrl);
        HttpEntity<String> entityForDeviceListUrl = new HttpEntity<>(requestForDeviceListUrl, headersForDeviceListUrl);

        when(restOperations.postForObject(deviceListUrl, entityForDeviceListUrl, String.class)).thenReturn(deviceDetail);
        when(config.getToken()).thenReturn("some token");

        smsGatewayMe.sendSMS("91", "1234", "OTP");

        verify(restOperations, times(1)).postForObject(eq(deviceListUrl), eq(entityForDeviceListUrl), eq(String.class));
        verify(restOperations, times(1)).postForObject(eq(sendMessageUrl), eq(entityForSendMessageUrl), eq(String.class));
    }

}