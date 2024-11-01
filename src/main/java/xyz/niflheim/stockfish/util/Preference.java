package xyz.niflheim.stockfish.util;

import com.github.bhlangonijr.chesslib.game.GameMode;
import xyz.niflheim.stockfish.engine.enums.Option;

public class Preference {
    private GameMode gameMode; // ex) machine vs human 기계(흑) 사람(백) // human vs machine 사람(흑) 기계(백)
    private Elo elo;
    private String userName;

    public Preference(String userName) {
        this.userName = userName;
    }

    public Preference(GameMode gameMode, String userName) {
        this.gameMode = gameMode;
        this.userName = userName;
    }

    public Elo getElo() {
        return elo;
    }

    public GameMode getGameMode() {
        return gameMode;
    }

    public String getUserName() {
        return userName;
    }

    public void setElo(Elo elo) {
        this.elo = elo;
    }

    public void setGameMode(GameMode gameMode) {
        this.gameMode = gameMode;
    }
}
