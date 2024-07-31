package com.footballscore.matchservice.Repository.Mapper;

import com.footballscore.matchservice.Repository.Entity.ScorerEntity;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class ScorerRowMapper implements RowMapper<ScorerEntity> {

    @Override
    public ScorerEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
        return ScorerEntity.builder()
                .match_id(rs.getInt("match_id"))
                .local_goal(rs.getInt("local_goal"))
                .visitor_goal(rs.getInt("visitor_goal"))
                .build();
    }
}
