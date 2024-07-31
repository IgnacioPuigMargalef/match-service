package com.footballscore.matchservice.Repository.Entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ScorerEntity {
    private Integer match_id;
    private int local_goal;
    private int visitor_goal;
}
