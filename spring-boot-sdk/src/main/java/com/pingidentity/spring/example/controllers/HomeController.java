package com.pingidentity.spring.example.controllers;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

@Controller
public class HomeController {

  @Autowired
  private OAuth2AuthorizedClientService authorizedClientService;
  @Autowired
  private RestTemplate restTemplate;

  @GetMapping("/")
  public String home(OAuth2AuthenticationToken authentication, Model model) {
    model.addAttribute("details", authentication.getPrincipal().getAttributes());

    // Get the client for the authorized user
    OAuth2AuthorizedClient client = authorizedClientService
        .loadAuthorizedClient(
            authentication.getAuthorizedClientRegistrationId(),
            authentication.getName());

    // Get the user information
    Map userInfo = null;
    if (client != null) {
      String userInfoEndpointUri = client.getClientRegistration()
          .getProviderDetails().getUserInfoEndpoint().getUri();
      // Make a request to get the user info
      if (!StringUtils.isEmpty(userInfoEndpointUri)) {
        userInfo = restTemplate.exchange(userInfoEndpointUri, HttpMethod.GET, null, Map.class).getBody();
      }
    }
    model.addAttribute("userInfo", userInfo);

    return "oauthLogin";
  }

  @GetMapping("favicon.ico")
  @ResponseBody
  public void returnNoFavicon() {
  }
}
