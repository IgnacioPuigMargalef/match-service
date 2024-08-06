package com.footballscore.matchservice.Repository;

import ch.qos.logback.classic.Logger;
import com.footballscore.matchservice.Controller.Request.NewMatchRequest;
import com.footballscore.matchservice.Exception.CreatingMatchException;
import com.footballscore.matchservice.Exception.GettingMatchException;
import com.footballscore.matchservice.Exception.NotFoundMatchException;
import com.footballscore.matchservice.Repository.Entity.MatchEntity;
import com.footballscore.matchservice.Repository.Mapper.MatchRowMapper;
import com.footballscore.matchservice.Utils.QueryUtil;
import lombok.AllArgsConstructor;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
@AllArgsConstructor
public class MatchSqlRepository {

    private final MatchRowMapper matchRowMapper;
    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final JdbcTemplate jdbc;

    private static final Logger LOGGER = (Logger) LoggerFactory.getLogger(MatchSqlRepository.class);

    public MatchEntity getById(Integer matchId) {
        LOGGER.info("MatchRepository - begin getById with match id [{}]", matchId);
        final MapSqlParameterSource params = new MapSqlParameterSource("match_id", matchId);

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

            if (matches.isEmpty()) {
                LOGGER.warn("Not found matches on [{}]", day);
                throw new NotFoundMatchException();
            }

            return matches;
        } catch (DataAccessException e) {
            LOGGER.error("Error in getByDay(day = [{}]) method", day, e);
            throw new GettingMatchException();
        }
    }

    public Integer createMatch(NewMatchRequest match) {
        LOGGER.info("MatchRepository - begin createMatch creating match [{}]", match);
        final KeyHolder keyHolder = new GeneratedKeyHolder();

        try {
             jdbc.update(connection -> QueryUtil.getInsertMatchSqlStatement(connection, match), keyHolder);
             Long longKey = (Long)keyHolder.getKeyList().getFirst().get("id");
             return longKey.intValue();
        } catch (Exception e) {
            LOGGER.error("Error in createMatch() method", e);
            throw new CreatingMatchException();
        }
    }

}
