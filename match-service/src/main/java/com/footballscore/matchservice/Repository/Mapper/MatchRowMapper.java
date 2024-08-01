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
                .time(rs.getTime("hour").toLocalTime())
                .status(MatchStatus.valueOf(rs.getString("status")))
                .build();
        setNestedObjects(rs, matchEntity);
        return matchEntity;
    }

    private void setNestedObjects(ResultSet rs, MatchEntity match) throws SQLException {
        final TeamEntity localTeam = TeamEntity.builder()
                .id(rs.getInt("local_id"))
                .name(rs.getString("local_name"))
                .city(rs.getString("local_city"))
                .emblem(rs.getBytes("local_emblem"))
                .stadium(rs.getString("local_stadium"))
                .build();

        final TeamEntity visitorTeam = TeamEntity.builder()
                .id(rs.getInt("visitor_id"))
                .name(rs.getString("visitor_name"))
                .city(rs.getString("visitor_city"))
                .emblem(rs.getBytes("visitor_emblem"))
                .stadium(rs.getString("visitor_stadium"))
                .build();

        match.setScore(scorerRowMapper.mapRow(rs, 1));
        match.setLocal_team(localTeam);
        match.setVisitor_team(visitorTeam);
    }
}
