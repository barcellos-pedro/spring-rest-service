package com.pedro.rest.configuration;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(name = "feature.env.logging", havingValue = "true", matchIfMissing = false)
public class Properties {

    @Value("${DB_URL}")
    private String dbUrl;

    @Value("${DB_USERNAME}")
    private String dbUsername;

    @Value("${DB_PASSWORD}")
    private String dbPassword;

    @Value("${DB_NAME}")
    private String dbName;

    @Value("${PGADMIN_MAIL}")
    private String pgAdminMail;

    @Value("${PGADMIN_PASSWORD}")
    private String pgAdminPassord;

    @PostConstruct
    public void printProperties() {
        System.out.println("#### [Env Values] ####");
        System.out.println("DB_URL: " + dbUrl);
        System.out.println("DB_USERNAME: " + dbUsername);
        System.out.println("DB_PASSWORD: " + dbPassword);
        System.out.println("DB_NAME: " + dbName);
        System.out.println("PGADMIN_MAIL: " + pgAdminMail);
        System.out.println("PGADMIN_PASSWORD: " + pgAdminPassord);
    }
}