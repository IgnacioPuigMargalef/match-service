package com.footballscore.matchservice.Repository;

public class Queries {

    /* MATCH QUERIES */
    public static final String GET_MATCH_BY_ID =
            "select * \n" +
            "from match m \n" +
            "join team t1 on m.local_team_id = t1.id \n" +
            "join team t2 on m.visitor_team_id = t2.id\n" +
            "join scorer s on m.id = s.match_id\n" +
            "where m.id = :match_id";

    public static final String GET_MATCHES_BY_DATE =
            "select * \n" +
            "from match m \n" +
            "join team t1 on m.local_team_id = t1.id \n" +
            "join team t2 on m.visitor_team_id = t2.id\n" +
            "join scorer s on m.id = s.match_id\n" +
            "where m.date = :match_date";
}
