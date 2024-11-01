package xyz.niflheim.stockfish;

import com.github.bhlangonijr.chesslib.Board;
import com.github.bhlangonijr.chesslib.game.GameMode;
import xyz.niflheim.stockfish.exceptions.StockfishInitException;
import xyz.niflheim.stockfish.util.GameDTO;
import xyz.niflheim.stockfish.util.Preference;

public class StockFishEnginTest {
    public static void main(String[] args) throws StockfishInitException {
        Preference preference = new Preference("UserName");
        preference.setGameMode(GameMode.HUMAN_VS_MACHINE);
        GameDTO gameDTO = new GameDTO(preference);
        Board board = gameDTO.getBoard();
    }
}
