package xyz.niflheim.stockfish;

import com.github.bhlangonijr.chesslib.Board;
import com.github.bhlangonijr.chesslib.Square;
import com.github.bhlangonijr.chesslib.game.GameMode;
import com.github.bhlangonijr.chesslib.move.Move;
import com.github.bhlangonijr.chesslib.move.MoveList;
import xyz.niflheim.stockfish.exceptions.StockfishInitException;
import xyz.niflheim.stockfish.ui.board.BoardPanel;
import xyz.niflheim.stockfish.util.GameDTO;
import xyz.niflheim.stockfish.util.Preference;

public class MoveHistoryLogicTest {
    public static void main(String[] args) throws StockfishInitException {
        Preference preference = new Preference("UserName");
        preference.setGameMode(GameMode.HUMAN_VS_HUMAN);

        GameDTO gameDTO = new GameDTO(preference);
        BoardPanel boardPanel = new BoardPanel(gameDTO);

        Board board = boardPanel.getBoard();
        MoveList moveHistory = new MoveList(board.getFen());

        Move move = new Move(Square.E2, Square.E4);
        boolean isMoveValid = board.doMove(move, true);
        if(isMoveValid) {
            moveHistory.add(move);
            String fen = moveHistory.getFen();
            System.out.println();
            System.out.println("moveList Fen : " + fen);
            System.out.println("Board Fen : " + board.getFen());
        }else {
            System.out.println("올바르지 않은 이동입니다.");
        }



    }
}
