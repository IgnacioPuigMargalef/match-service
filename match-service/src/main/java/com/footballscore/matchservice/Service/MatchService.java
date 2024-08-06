package com.footballscore.matchservice.Service;

import ch.qos.logback.classic.Logger;
import com.footballscore.matchservice.Controller.Request.NewMatchRequest;
import com.footballscore.matchservice.Dto.CachedMatch;
import com.footballscore.matchservice.Repository.Entity.MatchEntity;
import com.footballscore.matchservice.Repository.MatchRedisRepository;
import com.footballscore.matchservice.Repository.MatchSqlRepository;
import lombok.AllArgsConstructor;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class MatchService {

    private final MatchSqlRepository matchSqlRepository;
    private final MatchRedisRepository matchRedisRepository;
    private final ScorerService scorerService;
    private final static Logger LOGGER = (Logger) LoggerFactory.getLogger(MatchService.class);

    @Transactional
    public void createMatch(NewMatchRequest match) {
        LOGGER.info("MatchService - Creating a new match between team = [{}] and team = [{}]", match.local_team(), match.visitor_team());
        Integer matchId = matchSqlRepository.createMatch(match);
        scorerService.createScorer(matchId);
        MatchEntity matchEntity = matchSqlRepository.getById(matchId);
        CachedMatch cachedMatch = mapMatchEntityToCachedMatch(matchEntity);
        matchRedisRepository.createMatch(cachedMatch);
    }

    private CachedMatch mapMatchEntityToCachedMatch(MatchEntity match) {
        return CachedMatch.builder()
                .match_id(match.getId())
                .local_emblem(match.getLocal_team().emblem())
                .visitor_emblem(match.getVisitor_team().emblem())
                .status(match.getStatus().name())
                .local_goal(match.getScore().getLocal_goal())
                .visitor_goal(match.getScore().getVisitor_goal())
                .match_day(match.getDate().toString())
                .match_time(match.getTime().toString())
                .build();
    }

}
