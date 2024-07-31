package com.footballscore.matchservice.Repository.Mapper;

import com.footballscore.matchservice.Repository.Entity.TeamEntity;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class TeamRowMapper implements RowMapper<TeamEntity> {
    @Override
    public TeamEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
        return TeamEntity.builder()
                .id(rs.getInt("id"))
                .name(rs.getString("name"))
                .city(rs.getString("city"))
                .stadium(rs.getString("stadium"))
                .emblem(rs.getBytes("emblem"))
                .build();
    }
}
