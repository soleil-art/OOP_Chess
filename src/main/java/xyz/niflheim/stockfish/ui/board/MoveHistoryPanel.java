package xyz.niflheim.stockfish.ui.board;

import com.github.bhlangonijr.chesslib.Board;
import com.github.bhlangonijr.chesslib.Square;
import com.github.bhlangonijr.chesslib.game.GameMode;
import com.github.bhlangonijr.chesslib.move.Move;
import com.github.bhlangonijr.chesslib.move.MoveList;
import xyz.niflheim.stockfish.exceptions.StockfishInitException;
import xyz.niflheim.stockfish.util.GameDTO;
import xyz.niflheim.stockfish.util.Preference;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
// 패널 크기,배경색 및 ,updateLabel 메서드 구현
//GameFrame에 MoveHistoryPanel 추가할 예정
//MoveHistory 패널안에 JLabel이 들어가있는 구조
public class MoveHistoryPanel extends JPanel{

    private MoveList moveHistory; // move가 발생하면 로직에 의해서 moveList에 move가 추가됨
    private JLabel moveHistoryDisplay;

    public MoveHistoryPanel(GameDTO gameDTO) {
        moveHistory = gameDTO.getMoveHistory();
        moveHistoryDisplay = new JLabel();
        moveHistoryDisplay.setText("Hello World");
        moveHistoryDisplay.setBounds(100, 50, 522, 515);
        add(moveHistoryDisplay); // 패널에 jLabel 추가
        setBounds(100, 50, 522, 515); // 패널 크기와 위치 설정

        
    }
    //기물 이동이 발생하면 외부에서 이함수를 호출할 예정 => 메서드가 호출될때마다 JLabel을 갱신시켜야함.
    public void updateLabel() {

    }
    //테스트 코드
    public static void main(String[] args) throws StockfishInitException, InterruptedException {
        //기본 세팅
        Preference preference = new Preference("UserName");
        preference.setGameMode(GameMode.HUMAN_VS_HUMAN);
        GameDTO gameDTO = new GameDTO(preference);
        BoardPanel boardPanel = new BoardPanel(gameDTO);
        Board board = boardPanel.getBoard();
        List<Move> moveList = new ArrayList<>(List.of(new Move(Square.D2,Square.D4),new Move(Square.G8,Square.F6),new Move(Square.C2,Square.C4),
                new Move(Square.E7,Square.E6),new Move(Square.G1,Square.G3)));

        // 테스트를 위한 임시 프레임 생성
        JFrame frame = new JFrame("test Frame");
        MoveHistoryPanel moveHistoryPanel = new MoveHistoryPanel(gameDTO); // moveHistory패널 생성
        frame.add(moveHistoryPanel);
        frame.setSize(800, 800);
        frame.setLocationRelativeTo(null); // 창을 화면 중앙에 배치
        frame.pack();

        // 프레임을 표시
        frame.setVisible(true);

        for(Move move : moveList) {
            Thread.sleep(1000);
            boolean isMoveValid = board.doMove(move, true);
            moveHistoryPanel.updateLabel();
            if(!isMoveValid) {
                throw new RuntimeException("기물 이동 오류");
            }
            System.out.println("------------------------------------");
        }


    }
}
