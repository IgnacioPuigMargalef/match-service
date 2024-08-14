package com.footballscore.matchservice.Repository;

public class NoSqlQueries {

    /* MATCH QUERIES */
    /**
     * Get matches by today date and yesterday matches not finished yet
     */
    public static final String GET_MATCHES_TODAY =
            "(@match_day:(%s)) | (@match_day:(%s) @status:STARTED)";


}
