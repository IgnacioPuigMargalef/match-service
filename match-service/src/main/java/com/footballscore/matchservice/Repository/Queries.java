package com.footballscore.matchservice.Repository;

public class Queries {

    /* MATCH QUERIES */
    public static final String GET_MATCH_BY_ID =
            "select t1.id as local_id, t1.name as local_name, t1.emblem as local_emblem, t1.city as local_city, t1.stadium as local_stadium,\n" +
            "   t2.id as visitor_id, t2.name as visitor_name, t2.emblem as visitor_emblem, t2.city as visitor_city, t2.stadium as visitor_stadium,\n" +
            "   m.*, s.*\n" +
            "from match m \n" +
            "join team t1 on m.local_team_id = t1.id \n" +
            "join team t2 on m.visitor_team_id = t2.id\n" +
            "join scorer s on m.id = s.match_id\n" +
            "where m.id = :match_id";

    public static final String GET_MATCHES_BY_DATE =
            "select t1.id as local_id, t1.name as local_name, t1.emblem as local_emblem, t1.city as local_city, t1.stadium as local_stadium,\n" +
            "   t2.id as visitor_id, t2.name as visitor_name, t2.emblem as visitor_emblem, t2.city as visitor_city, t2.stadium as visitor_stadium,\n" +
            "   m.*, s.*\n" +
            "from match m \n" +
            "   join team t1 on m.local_team_id = t1.id \n" +
            "   join team t2 on m.visitor_team_id = t2.id\n" +
            "   join scorer s on m.id = s.match_id\n" +
            "where m.date = TO_DATE('31/07/2024', 'DD/MM/YYYY')\n" +
            "order by m.date desc\n";

    public static final String CREATE_MATCH =
            "INSERT INTO match (date, local_team_id, visitor_team_id, hour, status)\n" +
            "VALUES(?, ?, ?, ?, 'NOT_STARTED')";

    /* SCORER QUERIES */
    public static final String CREATE_SCORER =
            "INSERT INTO scorer (match_id, local_goal, visitor_goal)\n" +
            "VALUES(:match_id, 0, 0)";
}
