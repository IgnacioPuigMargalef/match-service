package com.footballscore.matchservice.Configuration;

import ch.qos.logback.classic.Logger;
import com.footballscore.matchservice.Utils.Constants;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import redis.clients.jedis.JedisPooled;
import redis.clients.jedis.UnifiedJedis;
import redis.clients.jedis.search.IndexDefinition;
import redis.clients.jedis.search.IndexOptions;
import redis.clients.jedis.search.Schema;

@org.springframework.context.annotation.Configuration
public class RedisConfig {

    @Value("${redis.host}")
    private String host;

    @Value("${redis.port}")
    private String port;

    private static final Logger LOGGER = (Logger) LoggerFactory.getLogger(RedisConfig.class);

    @Bean
    public UnifiedJedis jedisPool() {
        final JedisPooled jedisPooled = new JedisPooled(host, Integer.parseInt(port));
        /* The schema specifies the fields, their types, whether they should be indexed or stored,
         * and other additional configuration options (redis documentation on Schema definition)
         * */
        Schema sc = new Schema()
                .addTextField("$." + Constants.MATCH_DATE_FIELD, 1.0).as(Constants.MATCH_DATE_FIELD)
                .addTextField("$." + Constants.MATCH_STATUS_FIELD, 1.0).as(Constants.MATCH_STATUS_FIELD);

        /* The prefix defines the list of key prefix that will be look at for this index
         * A great explanation:
         * https://forum.redis.io/t/redisearch-2-0-what-exactly-is-prefix-count-prefix-in-ft-create/887/3 */
        IndexDefinition def = new IndexDefinition(IndexDefinition.Type.JSON)
                .setPrefixes(new String[]{Constants.REDIS_MATCH_PREFIX});

        try {
            jedisPooled.ftCreate(Constants.REDIS_MATCH_INDEX, IndexOptions.defaultOptions().setDefinition(def), sc);
            LOGGER.info("Index {} created on REDIS database", Constants.REDIS_MATCH_INDEX);
        } catch (Exception e) {
            LOGGER.warn("Index {} already exists on REDIS database", Constants.REDIS_MATCH_INDEX);
        }

        return jedisPooled;
    }


}
