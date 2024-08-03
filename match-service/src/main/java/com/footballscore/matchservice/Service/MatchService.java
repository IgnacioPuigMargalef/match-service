package com.footballscore.matchservice.Service;

import com.footballscore.matchservice.Controller.Request.NewMatchRequest;
import com.footballscore.matchservice.Repository.MatchRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class MatchService {

    private final MatchRepository matchRepository;

    @Transactional
    public void createMatch(NewMatchRequest match) {
        /*
        * En primer lugar, persistimos en bbdd. Iniciamos transaccion, y si no se completa cancelamos
        * En segundo lugar, guardamos en redis
        * Si el partido es hoy --> notificamos al websocket
        * */
        matchRepository.createMatch(match);

    }
}
