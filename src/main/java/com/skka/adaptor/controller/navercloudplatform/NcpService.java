package com.skka.adaptor.controller.navercloudplatform;

import java.net.URI;
import java.nio.charset.Charset;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

public class NcpService {

    private String accessKey = "";
    private String secretKey = "";

    private RestTemplate restTemplate = new RestTemplate();

    public Object ncpLookForList(String timeStamp, HttpMethod method, String url) throws Exception {

        HttpHeaders httpHeaders = getNcloudUserApiHeader(method, url, timeStamp);

        URI uri = UriComponentsBuilder
            .fromUriString("https://sourcebuild.apigw.ntruss.com")
            .path("/api/v1/project")
            .encode().build()
            .toUri();

        HttpEntity request = new HttpEntity(httpHeaders);
        ResponseEntity<?> response = restTemplate.exchange(uri, HttpMethod.GET, request, String.class);

        return response.getBody();
    }

    private String makeSignature(String timeStamp, String method, String url) throws Exception {
        String message = new StringBuilder()
            .append(method)
            .append(" ")
            .append(url)
            .append("\n")
            .append(timeStamp)
            .append("\n")
            .append(accessKey)
            .toString();
        SecretKeySpec signingKey = new SecretKeySpec(secretKey.getBytes("UTF-8"), "HmacSHA256");
        Mac mac = Mac.getInstance("HmacSHA256");
        mac.init(signingKey);
        byte[] rawHmac = mac.doFinal(message.getBytes("UTF-8"));
        String encodeBase64String = Base64.encodeBase64String(rawHmac);
        return encodeBase64String;
    }

    private HttpHeaders getNcloudUserApiHeader(HttpMethod method, String url, String timeStamp) {
        try {
            MediaType mediaType = new MediaType("application", "json", Charset.forName("UTF-8"));

            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.add("x-ncp-apigw-timestamp", timeStamp);
            httpHeaders.add("x-ncp-iam-access-key", accessKey);
            httpHeaders.add("x-ncp-apigw-signature-v2", makeSignature(timeStamp, method.name(), url));
            httpHeaders.setContentType(mediaType);
            return httpHeaders;
        } catch (Exception ex) {
            return null;
        }
    }

    public Object ncpHookBuildingTrigger(String timeStamp, HttpMethod method, String url) throws Exception {

        HttpHeaders httpHeaders = getNcloudUserApiHeader(method, url, timeStamp);

        URI uri = UriComponentsBuilder
            .fromUriString("https://sourcebuild.apigw.ntruss.com")
            .path(url)
            .encode().build()
            .toUri();

        HttpEntity request = new HttpEntity(httpHeaders);
        ResponseEntity<?> response = restTemplate.exchange(uri, HttpMethod.POST, request, String.class);

        return response.getBody();
    }
}