package co.com.example.authentication_security.service;

import co.com.example.authentication_security.configuration.CaptchaSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import java.util.Map;



@Service
public class CaptchaService {

    @Autowired
    private CaptchaSettings captchaSettings;

    public boolean verifyCaptcha(String response) {
        RestTemplate restTemplate = new RestTemplate();
        String url = "https://www.google.com/recaptcha/api/siteverify?secret=" + captchaSettings.getSecret() + "&response=" + response;
        ResponseEntity<Map> responseEntity = restTemplate.postForEntity(url, null, Map.class);
        Map<String, Object> body = responseEntity.getBody();
        return (Boolean) body.get("success");
    }
}
