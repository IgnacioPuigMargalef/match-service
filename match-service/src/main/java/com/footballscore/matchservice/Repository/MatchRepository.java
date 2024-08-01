package com.footballscore.matchservice.Repository;

import ch.qos.logback.classic.Logger;
import com.footballscore.matchservice.Exception.GettingMatchException;
import com.footballscore.matchservice.Exception.NotFoundMatchException;
import com.footballscore.matchservice.Repository.Entity.MatchEntity;
import com.footballscore.matchservice.Repository.Mapper.MatchRowMapper;
import lombok.AllArgsConstructor;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
@AllArgsConstructor
public class MatchRepository {

    private final MatchRowMapper matchRowMapper;
    private final NamedParameterJdbcTemplate jdbcTemplate;

    private static final Logger LOGGER = (Logger) LoggerFactory.getLogger(MatchRepository.class);

    public MatchEntity getById(Integer matchId) {
        LOGGER.info("MatchRepository - begin getById with match id [{}]", matchId);
        final MapSqlParameterSource params = new MapSqlParameterSource("matchId", matchId);

        try {
            return jdbcTemplate.queryForObject(Queries.GET_MATCH_BY_ID, params, matchRowMapper);
        } catch (EmptyResultDataAccessException e) {
            LOGGER.warn("Not found match with id [{}]", matchId);
            throw new NotFoundMatchException();
        } catch (DataAccessException e) {
            LOGGER.error("Error in getById(id = [{}]) method", matchId, e);
            throw new GettingMatchException();
        }
    }

    public List<MatchEntity> getByDay(LocalDate day) {
        LOGGER.info("MatchRepository - begin getByDay with day [{}]", day);
        final MapSqlParameterSource params = new MapSqlParameterSource("match_date", day);

        try {
            final List<MatchEntity> matches = jdbcTemplate.query(Queries.GET_MATCHES_BY_DATE, params, matchRowMapper);

            if(matches.isEmpty()) {
                LOGGER.warn("Not found matches on [{}]", day);
                throw new NotFoundMatchException();
            }

            return matches;
        } catch (DataAccessException e) {
            LOGGER.error("Error in getByDay(day = [{}]) method", day, e);
            throw new GettingMatchException();
        }
    }

}
