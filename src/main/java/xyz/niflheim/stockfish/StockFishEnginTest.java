package xyz.niflheim.stockfish;

import com.github.bhlangonijr.chesslib.Board;
import com.github.bhlangonijr.chesslib.game.GameMode;
import xyz.niflheim.stockfish.engine.StockfishClient;
import xyz.niflheim.stockfish.engine.enums.Query;
import xyz.niflheim.stockfish.engine.enums.QueryType;
import xyz.niflheim.stockfish.exceptions.StockfishInitException;
import xyz.niflheim.stockfish.util.Elo;
import xyz.niflheim.stockfish.util.GameDTO;
import xyz.niflheim.stockfish.util.Preference;

public class StockFishEnginTest {
    public static void main(String[] args) throws StockfishInitException {
        Preference preference = new Preference("UserName");
        preference.setGameMode(GameMode.HUMAN_VS_MACHINE);
        preference.setElo(Elo.BEGINNER);
        GameDTO gameDTO = new GameDTO(preference);
        Board board = gameDTO.getBoard();
        StockfishClient stockfishClient = gameDTO.getStockfishClient();

        Query query = new Query(QueryType.Best_Move, board.getFen(), -1, 10, 10);
        stockfishClient.submit(query,fen -> {
            System.out.println(fen);
        } );
    }
}
