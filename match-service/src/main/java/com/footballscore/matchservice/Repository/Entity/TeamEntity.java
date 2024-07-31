package com.footballscore.matchservice.Repository.Entity;

import lombok.Builder;

@Builder
public record TeamEntity(
      Integer id,
      String name,
      byte[] emblem,
      String city,
      String stadium
){
}
