package com.footballscore.matchservice.Controller.Request;

import java.time.LocalDate;
import java.time.LocalTime;

public record NewMatchRequest(
    LocalDate day,
    LocalTime hour,
    Integer local_team,
    Integer visitor_team
) { }
