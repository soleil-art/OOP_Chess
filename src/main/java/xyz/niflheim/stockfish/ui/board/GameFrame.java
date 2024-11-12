package xyz.niflheim.stockfish.ui.board;

import com.github.bhlangonijr.chesslib.Board;
import com.github.bhlangonijr.chesslib.game.GameMode;
import xyz.niflheim.stockfish.engine.StockfishClient;
import xyz.niflheim.stockfish.exceptions.StockfishInitException;
import xyz.niflheim.stockfish.util.Elo;
import xyz.niflheim.stockfish.util.GameDTO;
import xyz.niflheim.stockfish.util.Preference;

import javax.swing.*;
import java.awt.*;

public class GameFrame extends JFrame {
    private JLayeredPane layeredPane;
    private BoardPanel boardPanel;
    private MoveHistoryPanel moveHistoryPanel;
    private final GameDTO gameDTO;
    private final StockfishClient stockfishClient;
    private final Board board;

    public GameFrame(GameDTO gameDTO) {
        this.gameDTO = gameDTO;
        boardPanel = new BoardPanel(gameDTO);
        stockfishClient = gameDTO.getStockfishClient();
        moveHistoryPanel = boardPanel.getMoveHistoryPanel();
        board = boardPanel.getBoard();
        setBackground(Color.decode("#302E2B"));
        initFrame();
    }

    private void initFrame() {
        setTitle("OOP Chess Game");
        setSize(12*BoardPanel.SQUARE_DIMENSION,10*BoardPanel.SQUARE_DIMENSION); // 새 프레임 크기 설정
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // 화면 중앙에 위치
        setResizable(false);

        boardPanel.setBounds(0, 0, 8*BoardPanel.SQUARE_DIMENSION, 8*BoardPanel.SQUARE_DIMENSION);  // 필요에 따라 크기를 조정
        layeredPane = new JLayeredPane();
        layeredPane.add(boardPanel,JLayeredPane.DEFAULT_LAYER);
        setContentPane(layeredPane);

        moveHistoryPanel.setBounds(8*BoardPanel.SQUARE_DIMENSION,0,4*BoardPanel.SQUARE_DIMENSION,4*BoardPanel.SQUARE_DIMENSION);
        layeredPane.add(moveHistoryPanel,JLayeredPane.DEFAULT_LAYER);
    }


    public static void main(String[] args) throws StockfishInitException {
        Preference preference = new Preference("UserName");
        preference.setElo(Elo.BEGINNER);
        preference.setGameMode(GameMode.HUMAN_VS_MACHINE);
        GameDTO gameDTO1 = new GameDTO(preference);
        GameFrame gameFrame = new GameFrame(gameDTO1);
        gameFrame.setVisible(true);
    }
}
