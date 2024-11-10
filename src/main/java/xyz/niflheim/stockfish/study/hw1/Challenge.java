package xyz.niflheim.stockfish.study.hw1;

import com.github.bhlangonijr.chesslib.Board;
import com.github.bhlangonijr.chesslib.Square;
import com.github.bhlangonijr.chesslib.game.GameMode;
import com.github.bhlangonijr.chesslib.move.Move;
import com.github.bhlangonijr.chesslib.move.MoveList;
import xyz.niflheim.stockfish.exceptions.StockfishInitException;
import xyz.niflheim.stockfish.ui.board.BoardPanel;
import xyz.niflheim.stockfish.util.GameDTO;
import xyz.niflheim.stockfish.util.Preference;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

//MoveHistoryLogicTest class 참고
// move할때마다 jlabel 없데이트 구현해보기
//예시 ♙d4 ♞f6 ♙c4 ♟e6 ♘g3 이런식으로 출력되야함
public class Challenge extends JLabel {
    Preference preference;
    List<Move> moveList;
    GameDTO gameDTO;
    Board board;
    MoveList moveHistory; // 이동 정보가 담겨있음

    public Challenge() throws StockfishInitException {
        initGameSetting();
        setFont(new Font("Plain", Font.BOLD, 32)); // 큰 글씨 설정
        setHorizontalAlignment(SwingConstants.CENTER); // 가운데 정렬
    }

    public void updateLabel() throws InterruptedException { //doMove메서드가 호출될때마다 moveHistory에 이동이 저장됨

        for(Move move : moveList) {
            boolean isMoveValid = board.doMove(move, true);
            String[] moveHistoryDisplay = moveHistory.toFanArray();
            setText(String.join(",",moveHistoryDisplay));

            Thread.sleep(1000);
            if(!isMoveValid) {
                throw new RuntimeException("기물 이동 오류");
            }
            System.out.println("------------------------------------");
        }
    }

    public static void main(String[] args) throws StockfishInitException, InterruptedException {
        Challenge challenge = new Challenge();
        JFrame frame = new JFrame("MoveHistory DisPlay");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(challenge);
        frame.setSize(400, 200);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);


        challenge.updateLabel();

    }

    private void initGameSetting() throws StockfishInitException {
        preference = new Preference("UserName");
        preference.setGameMode(GameMode.HUMAN_VS_HUMAN);
        gameDTO = new GameDTO(preference);

        BoardPanel boardPanel = new BoardPanel(gameDTO);

        board = boardPanel.getBoard();
        moveHistory = gameDTO.getMoveHistory();

        moveList = new ArrayList<>(List.of(new Move(Square.D2,Square.D4),new Move(Square.G8,Square.F6),new Move(Square.C2,Square.C4),
                new Move(Square.E7,Square.E6),new Move(Square.G1,Square.G3)));
    }
}
