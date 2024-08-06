package com.footballscore.matchservice.Configuration;

import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPooled;
import redis.clients.jedis.UnifiedJedis;

@org.springframework.context.annotation.Configuration
@ConfigurationProperties(prefix = "jedis")
public class RedisConfig {

    @Value("${jedis.host}")
    private String host;

    @Value("${jedis.port}")
    private String port;

    @Bean
    public UnifiedJedis jedisPool() {
        final JedisPooled jedisPooled = new JedisPooled(host, Integer.parseInt(port));
        /* TODO Creating index: we will need to search by match date */
//        jedisPooled.ftCreate("");
        return jedisPooled;
    }

}
