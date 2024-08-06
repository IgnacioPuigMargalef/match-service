package com.footballscore.matchservice.Utils;

import com.footballscore.matchservice.Controller.Request.NewMatchRequest;
import com.footballscore.matchservice.Repository.Queries;

import java.sql.*;

public class QueryUtil {

    public static PreparedStatement getInsertMatchSqlStatement(Connection connection, NewMatchRequest match) throws SQLException {
        PreparedStatement ps = connection.prepareStatement(Queries.CREATE_MATCH, Statement.RETURN_GENERATED_KEYS);
        ps.setDate(1, Date.valueOf(match.day()));
        ps.setInt(2, match.local_team());
        ps.setInt(3, match.visitor_team());
        ps.setTime(4, Time.valueOf(match.hour()));
        return ps;
    }
}
