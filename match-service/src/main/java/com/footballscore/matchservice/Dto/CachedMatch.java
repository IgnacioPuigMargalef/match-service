package com.footballscore.matchservice.Dto;

import lombok.Builder;

@Builder
public record CachedMatch(
        Integer match_id,
        byte[] local_emblem,
        byte[] visitor_emblem,
        int local_goal,
        int visitor_goal,
        String match_day,
        String match_time,
        String status
) {
}
