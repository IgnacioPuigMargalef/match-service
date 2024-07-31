package com.footballscore.matchservice.Repository.Mapper;

import com.footballscore.matchservice.Repository.Entity.MatchEntity;
import com.footballscore.matchservice.Repository.Entity.ScorerEntity;
import com.footballscore.matchservice.Repository.Entity.TeamEntity;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@AllArgsConstructor
public class MatchExtractor implements ResultSetExtractor<List<MatchEntity>> {

    private final ScorerRowMapper scorerRowMapper;
    private final MatchRowMapper matchRowMapper;
    private final TeamRowMapper teamRowMapper;

    @Override
    public List<MatchEntity> extractData(ResultSet rs) throws SQLException, DataAccessException {
        final Map<Integer, MatchEntity> matches = new HashMap<>();

        while (rs.next()) {
            MatchEntity match = matches.get(rs.getInt("id"));
            if(match == null) {
                match = matchRowMapper.mapRow(rs, 1);
                matches.put(match.getId(), match);
            }
            setNestedObjects(rs, match);
        }

        return new ArrayList<>(matches.values());
    }

    private void setNestedObjects(ResultSet rs, MatchEntity match) throws SQLException {
        final ScorerEntity scorerEntity = ScorerEntity.builder()
                .match_id(rs.getInt("match_id"))
                .visitor_goal(rs.getInt("visitor_goal"))
                .local_goal(rs.getInt("local_goal"))
                .build();
        final TeamEntity localTeam = TeamEntity.builder()
                .id(rs.getInt("t1.id"))
                .name(rs.getString("t1.name"))
                .city(rs.getString("t1.city"))
                .emblem(rs.getBytes("t1.emblem"))
                .stadium(rs.getString("t1.stadium"))
                .build();

        final TeamEntity visitorTeam = TeamEntity.builder()
                .id(rs.getInt("t2.id"))
                .name(rs.getString("t2.name"))
                .city(rs.getString("t2.city"))
                .emblem(rs.getBytes("t2.emblem"))
                .stadium(rs.getString("t2.stadium"))
                .build();

        match.setScore(scorerEntity);
        match.setLocal_team(localTeam);
        match.setLocal_team(visitorTeam);
    }
}
