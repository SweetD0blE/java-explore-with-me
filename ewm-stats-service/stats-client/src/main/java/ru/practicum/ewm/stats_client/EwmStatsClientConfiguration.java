package ru.practicum.ewm.stats_client;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class EwmStatsClientConfiguration {

    @Bean
    public RestTemplate rest() {
        return new RestTemplate();
    }
}