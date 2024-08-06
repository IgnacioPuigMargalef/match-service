package com.footballscore.matchservice.Service;

import ch.qos.logback.classic.Logger;
import com.footballscore.matchservice.Repository.ScorerSqlRepository;
import lombok.AllArgsConstructor;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ScorerService {

    private final ScorerSqlRepository scorerSqlRepository;
    private static final Logger LOGGER = (Logger) LoggerFactory.getLogger(ScorerService.class);

    public void createScorer(Integer matchId) {
        LOGGER.info("ScorerService - Creating scorer for match = [{}]", matchId);
        scorerSqlRepository.createScorer(matchId);
    }
}
