package xyz.niflheim.stockfish.respository;

import org.junit.jupiter.api.Test;

import java.util.List;

public class PGN {

    @Test
    void pgnTest() {
        PGNRepsitory pgnRepsitory = new PGNRepsitory();
        List<GamePGN> pgnList = pgnRepsitory.findByUserId("alex");

        pgnList.forEach(pgn -> {
            System.out.println(pgn.getUser_id());
            System.out.println(pgn.getGameId());
            System.out.println(pgn.getPGN());
        });
    }
}
