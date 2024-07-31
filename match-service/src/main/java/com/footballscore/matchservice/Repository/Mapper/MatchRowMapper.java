package com.footballscore.matchservice.Repository.Mapper;

import com.footballscore.matchservice.Repository.Entity.MatchEntity;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class MatchRowMapper implements RowMapper<MatchEntity> {

    @Override
    public MatchEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
        return null;
    }
}
