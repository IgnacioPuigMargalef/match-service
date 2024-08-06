package com.footballscore.matchservice.Repository;

import com.footballscore.matchservice.Dto.CachedMatch;
import com.footballscore.matchservice.Exception.CachingMatchException;
import com.footballscore.matchservice.Utils.Constants;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import redis.clients.jedis.UnifiedJedis;
import redis.clients.jedis.exceptions.JedisDataException;

@Repository
@AllArgsConstructor
public class MatchRedisRepository {

    private final UnifiedJedis jedis;
    private final Logger LOGGER = LoggerFactory.getLogger(MatchRedisRepository.class);

    public void createMatch(CachedMatch cachedMatch) {
        try {
            jedis.jsonSetWithEscape(Constants.REDIS_MATCH_PREFIX + cachedMatch.match_id(), cachedMatch);
        } catch (JedisDataException e) {
            LOGGER.error("Not cached match with id = [{}] on redis database", cachedMatch.match_id());
            throw new CachingMatchException();
        }
    }

}
