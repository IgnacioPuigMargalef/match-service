package com.footballscore.matchservice.Controller;

import com.footballscore.matchservice.Repository.Entity.MatchEntity;
import com.footballscore.matchservice.Repository.MatchRepository;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/match")
@AllArgsConstructor
public class MatchController {

    private final MatchRepository matchRepository;

    @GetMapping("/get/{id}")
    public MatchEntity getById(@PathVariable Integer id) {
        return matchRepository.getById(id);
    }

    @GetMapping("/getAll")
    public List<MatchEntity> getAllByDate(@RequestParam @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate date) {
        return matchRepository.getByDay(date);
    }

}
