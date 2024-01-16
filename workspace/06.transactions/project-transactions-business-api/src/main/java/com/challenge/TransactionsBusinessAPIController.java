package com.challenge;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(value = "/")
public class TransactionsBusinessAPIController {

    @Value("${app.app_info.title}")
    private String APP_TITLE;

    @Value("${app.app_info.version}")
    private String APP_VERSION;

    @Value("${app.app_info.build}")
    private String APP_BUILD;

    @Value("${server.port}")
    private String actualPort;

    @Value("${eureka.instance.instance-id}")
    private String apiInstance;

    @GetMapping(value = "health")
    public String checkHealth() {
        return  APP_TITLE + " [version: " + APP_VERSION + "/build: " + APP_BUILD + "] it is running!";
    }

    @GetMapping(value = "actuator/info")
    public String actuatorInfo(@Value("${local.server.port}") String port) {
        return  "Actuator Info: " +  APP_TITLE +
                " [version: " + APP_VERSION +
                "/build: " + APP_BUILD + "] "+
                "it is running " +
                "in port [" + port + "] and " +
                "instance Id is [" + apiInstance + "].";
    }
    
}
