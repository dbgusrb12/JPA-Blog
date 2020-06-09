package com.jpa.jpaBlog.core.config;

import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.cache.configuration.MutableConfiguration;
import javax.cache.expiry.CreatedExpiryPolicy;
import javax.cache.expiry.Duration;
import java.util.concurrent.TimeUnit;

@Configuration
public class CacheConfig {

    public static final Duration TEN_SECONDS = new Duration(TimeUnit.SECONDS, 10);

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            cm.createCache("blog.category", initConfiguration(TEN_SECONDS));
            cm.createCache("github.user", initConfiguration(Duration.ONE_HOUR));
        };
    }

    private MutableConfiguration<Object, Object> initConfiguration(Duration duration) {
        return new MutableConfiguration<>()
                .setStatisticsEnabled(true)
                .setExpiryPolicyFactory(CreatedExpiryPolicy.factoryOf(duration));
    }
}
