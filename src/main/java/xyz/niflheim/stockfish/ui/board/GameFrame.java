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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameFrame extends JFrame {
    private JLayeredPane layeredPane;
    private BoardPanel boardPanel;
    private MoveHistoryPanel moveHistoryPanel;
    private NamePanel whiteNamePanel;
    private NamePanel blackNamePanel;
    private JButton reverseBoardButton;
    private final GameDTO gameDTO;
    private final StockfishClient stockfishClient;
    private final Board board;

    public GameFrame(GameDTO gameDTO) {
        this.gameDTO = gameDTO;
        boardPanel = new BoardPanel(gameDTO);
        stockfishClient = gameDTO.getStockfishClient();
        moveHistoryPanel = boardPanel.getMoveHistoryPanel();
        board = boardPanel.getBoard();
        whiteNamePanel = new NamePanel(gameDTO.getWhitePlayer(),gameDTO.getBlackPlayer(),true,gameDTO.isBoardReserved());
        blackNamePanel = new NamePanel(gameDTO.getWhitePlayer(),gameDTO.getBlackPlayer(),false,gameDTO.isBoardReserved());
        setBackground(Color.decode("#302E2B"));
        initFrame();
    }

    private void initFrame() {
        setTitle("OOP Chess Game");
        setSize(12*BoardPanel.SQUARE_DIMENSION,8*BoardPanel.SQUARE_DIMENSION+160); // 새 프레임 크기 설정
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // 화면 중앙에 위치
        setResizable(false);

        boardPanel.setBounds(0, 60, 8*BoardPanel.SQUARE_DIMENSION, 8*BoardPanel.SQUARE_DIMENSION);  // 필요에 따라 크기를 조정
        layeredPane = new JLayeredPane();
        layeredPane.add(boardPanel,JLayeredPane.DEFAULT_LAYER);
        setContentPane(layeredPane);

        moveHistoryPanel.setBounds(8*BoardPanel.SQUARE_DIMENSION,0,4*BoardPanel.SQUARE_DIMENSION,4*BoardPanel.SQUARE_DIMENSION);
        layeredPane.add(moveHistoryPanel,JLayeredPane.DEFAULT_LAYER);

        blackNamePanel.setBounds(8*BoardPanel.SQUARE_DIMENSION-220,0,220,60);
        layeredPane.add(blackNamePanel,JLayeredPane.DEFAULT_LAYER);

        whiteNamePanel.setBounds(0,8*BoardPanel.SQUARE_DIMENSION+60,220,60);
        layeredPane.add(whiteNamePanel,JLayeredPane.DEFAULT_LAYER);

        ImageIcon icon = new ImageIcon(getClass().getResource("/image/boardReserve.png"));
        reverseBoardButton = new JButton(icon);
        reverseBoardButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boardPanel.reverseBoard();
                blackNamePanel.updateName(boardPanel.boardReversed);
                whiteNamePanel.updateName(boardPanel.boardReversed);
            }
        });
        reverseBoardButton.setBounds(8*BoardPanel.SQUARE_DIMENSION,8*BoardPanel.SQUARE_DIMENSION,50,50);
        layeredPane.add(reverseBoardButton);
    }



    public static void main(String[] args) throws StockfishInitException {
        Preference preference = new Preference("UserName");
        preference.setElo(Elo.BEGINNER);
        preference.setGameMode(GameMode.HUMAN_VS_MACHINE);
        GameDTO gameDTO1 = new GameDTO(preference);
        GameFrame gameFrame = new GameFrame(gameDTO1);
        gameFrame.setVisible(true);
    }

    public BoardPanel getBoardPanel() {
        return boardPanel;
    }
}
