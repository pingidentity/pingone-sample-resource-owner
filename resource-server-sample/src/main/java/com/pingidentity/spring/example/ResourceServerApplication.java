package com.pingidentity.spring.example;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@SpringBootApplication
public class ResourceServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ResourceServerApplication.class, args);
    }

    @EnableWebSecurity
    public class OAuth2ResourceServerSecurityConfiguration extends WebSecurityConfigurerAdapter {

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http
                    .authorizeRequests()
                    .antMatchers("/api/messages/**").hasAuthority("SCOPE_message")
                    .anyRequest().authenticated()
                    .and()
                    .oauth2ResourceServer()
                    .jwt();
        }
    }

    @Controller
    public class OAuth2ResourceServerController {

        @GetMapping("/api/messages")
        @ResponseBody
        public Map<String, Object> messages() {
            Map<String, Object> result = new HashMap<>();
            result.put("messages", Arrays.asList(
                    new Message("Hello, new world!", "John"),
                    new Message("Buy, old world!", "Jessica")
            ));

            return result;
        }
    }

    @Data
    @AllArgsConstructor
    public class Message {
        public String text;
        public String owner;
    }

}
