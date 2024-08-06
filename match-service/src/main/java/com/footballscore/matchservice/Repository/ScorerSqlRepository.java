package com.footballscore.matchservice.Repository;

import ch.qos.logback.classic.Logger;
import com.footballscore.matchservice.Exception.CreatingScorerException;
import lombok.AllArgsConstructor;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class ScorerSqlRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private static final Logger LOGGER = (Logger) LoggerFactory.getLogger(ScorerSqlRepository.class);

    public void createScorer(Integer matchId) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("match_id", matchId);
        try {
            jdbcTemplate.update(Queries.CREATE_SCORER, params);
        } catch (DataAccessException e) {
            LOGGER.error("Error in createScorer() method", e);
            throw new CreatingScorerException();
        }
    }
}
