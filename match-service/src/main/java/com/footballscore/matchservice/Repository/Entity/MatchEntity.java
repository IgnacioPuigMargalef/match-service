package com.footballscore.matchservice.Repository.Entity;

import com.footballscore.matchservice.Utils.MatchStatus;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Builder
@Data
public class MatchEntity {
    private Integer id;
    private LocalDate date;
    private LocalTime time;
    private TeamEntity local_team;
    private TeamEntity visitor_team;
    private MatchStatus status;
    private ScorerEntity score;
}
