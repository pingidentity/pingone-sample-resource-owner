package com.pingidentity.spring.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Map;

@SpringBootApplication
public class FrontEndApplication extends GeneralSampleApplication {

    public static void main(String[] args) {
        SpringApplication.run(FrontEndApplication.class, args);
    }

    @Controller
    public class DefaultController {

        @Value("${ping.resourceServerUrl}")
        private String resourceServerUrl;

        @Autowired
        RestTemplate restTemplate;

        @GetMapping("/api/messages")
        public String getMessages(RedirectAttributes redirectAttrs) {
            Map<String, ?> response = restTemplate.getForEntity(resourceServerUrl + "/api/messages", Map.class)
                .getBody();
            response.entrySet().forEach(attr ->
                    redirectAttrs.addFlashAttribute(attr.getKey(), attr.getValue()));
            return "redirect:/";
        }
    }

}
