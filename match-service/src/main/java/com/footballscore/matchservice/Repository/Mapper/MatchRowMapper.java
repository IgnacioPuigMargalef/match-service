package com.footballscore.matchservice.Repository.Mapper;

import com.footballscore.matchservice.Repository.Entity.MatchEntity;
import com.footballscore.matchservice.Repository.Entity.TeamEntity;
import com.footballscore.matchservice.Utils.MatchStatus;
import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
@AllArgsConstructor
public class MatchRowMapper implements RowMapper<MatchEntity> {

    private final ScorerRowMapper scorerRowMapper;

    @Override
    public MatchEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
        MatchEntity matchEntity = MatchEntity.builder()
                .id(rs.getInt("id"))
                .date(rs.getDate("date").toLocalDate())
                .time(rs.getTime("time").toLocalTime())
                .status(rs.getObject("status", MatchStatus.class))
                .build();
        setNestedObjects(rs, matchEntity);
        return matchEntity;
    }

    private void setNestedObjects(ResultSet rs, MatchEntity match) throws SQLException {
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

        match.setScore(scorerRowMapper.mapRow(rs, 1));
        match.setLocal_team(localTeam);
        match.setLocal_team(visitorTeam);
    }
}
