package com.footballscore.matchservice.Repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.footballscore.matchservice.Dto.CachedMatch;
import com.footballscore.matchservice.Exception.CachingMatchException;
import com.footballscore.matchservice.Exception.UnknowErrorGettingCachedMatchesException;
import com.footballscore.matchservice.Utils.Constants;
import lombok.AllArgsConstructor;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import redis.clients.jedis.CommandArguments;
import redis.clients.jedis.UnifiedJedis;
import redis.clients.jedis.exceptions.JedisDataException;
import redis.clients.jedis.search.Document;
import redis.clients.jedis.search.Query;
import redis.clients.jedis.search.SearchResult;

import java.util.ArrayList;
import java.util.List;

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

    public List<CachedMatch> findMatchesByDate(String day, String previousDay) {
        try {
            final String queryStr = String.format(NoSqlQueries.GET_MATCHES_TODAY, day, previousDay);
            final Query queryObj = new Query(queryStr);
            final SearchResult searchResult = jedis.ftSearch(Constants.REDIS_MATCH_INDEX, queryObj);

            if (searchResult == null || searchResult.getDocuments().isEmpty()) {
                LOGGER.warn("Not cached matches in date = [{}] on redis database", day);
                return List.of();
            }

            final List<Document> cachedMatch = searchResult.getDocuments();
            return mapDocumentsToCachedMatches(cachedMatch);
        } catch (RuntimeException e) {
            LOGGER.error("Unknow error on findMatchesByDate with date = [{}] on redis database", day, e);
            throw new UnknowErrorGettingCachedMatchesException();
        }
    }

    private List<CachedMatch> mapDocumentsToCachedMatches(List<Document> documents) {
        final List<CachedMatch> cachedMatches = new ArrayList<>();
        final ObjectMapper mapper = new ObjectMapper();

        documents.forEach(document -> {
            final JSONObject json = new JSONObject(document.get("$").toString());
            try {
                cachedMatches.add(mapper.readValue(json.toString(), CachedMatch.class));
            } catch (Exception e) {
                LOGGER.error("Error mapping json match to pojo match. It returns the rest", e);
            }
        });

        return cachedMatches;
    }

}
