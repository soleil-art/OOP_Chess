package xyz.niflheim.stockfish.ui.board;

import xyz.niflheim.stockfish.util.GameDTO;

import javax.swing.*;

public class GameFrame extends JFrame {
    private JLayeredPane layeredPane;
    private BoardPanel boardPanel;
    private MoveHistoryPanel moveHistoryPanel;
    private GameDTO gameDTO;

    public GameFrame(GameDTO gameDTO) {
        this.gameDTO = gameDTO;
        initFrame();

    }

    private void initFrame() {
        setTitle("OOP Chess Game");
        setSize(800, 600); // 새 프레임 크기 설정
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // 화면 중앙에 위치
        setResizable(false);
    }
}
