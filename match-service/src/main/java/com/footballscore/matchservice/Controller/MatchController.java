package com.footballscore.matchservice.Controller;

import com.footballscore.matchservice.Controller.Request.NewMatchRequest;
import com.footballscore.matchservice.Repository.Entity.MatchEntity;
import com.footballscore.matchservice.Repository.MatchSqlRepository;
import com.footballscore.matchservice.Service.MatchService;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/match")
@AllArgsConstructor
public class MatchController {

    private final MatchSqlRepository matchSqlRepository;
    private final MatchService matchService;

    @GetMapping("/get/{id}")
    public MatchEntity getById(@PathVariable Integer id) {
        return matchSqlRepository.getById(id);
    }

    @GetMapping("/getAll")
    public List<MatchEntity> getAllByDate(@RequestParam @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate date) {
        return matchSqlRepository.getByDay(date);
    }

    @PostMapping("/createMatch")
    public void createMatch(@RequestBody NewMatchRequest match) {
        matchService.createMatch(match);
    }

}
