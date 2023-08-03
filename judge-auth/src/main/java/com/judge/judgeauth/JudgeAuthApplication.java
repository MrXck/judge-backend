package com.judge.judgeauth;

import com.spring4all.swagger.EnableSwagger2Doc;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.OkHttp3ClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@EnableSwagger2Doc
@SpringBootApplication
public class JudgeAuthApplication {

    public static void main(String[] args) {
        SpringApplication.run(JudgeAuthApplication.class, args);
    }

    @Bean
    RestTemplate restTemplate() {
        return new RestTemplate(new OkHttp3ClientHttpRequestFactory());
    }

}
