//package ru.scarletarrow.diplomv1.security;
//
//import lombok.Data;
//import lombok.NoArgsConstructor;
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.http.HttpHeaders;
//
//@NoArgsConstructor
//@Data
//@Configuration
//@ConfigurationProperties(prefix = "application.jwt")
//public class JwtConfig {
//    private String secretKey;
//    private String tokenPrefix;
//    private Integer tokenExpirationAfterDays;
//
//
//
//    public String getAuthHeader(){
//        return HttpHeaders.AUTHORIZATION;
//    }
//}
