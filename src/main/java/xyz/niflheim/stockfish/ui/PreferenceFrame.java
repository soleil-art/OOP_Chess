package xyz.niflheim.stockfish.ui;

import com.github.bhlangonijr.chesslib.game.GameMode;
import xyz.niflheim.stockfish.exceptions.StockfishInitException;
import xyz.niflheim.stockfish.util.Elo;
import xyz.niflheim.stockfish.util.GameDTO;
import xyz.niflheim.stockfish.util.Preference;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;

public class PreferenceFrame extends JFrame { //PreferenceFrame -> GameFrame
    private Preference preference;
    private GameDTO gameDTO;

    private JLayeredPane layeredPane;
    private ModeSelectionPanel modeSelectionPanel;
    private LevelSelectionPanel levelSelectionPanel;

    public PreferenceFrame(Preference preference)  {
        init(preference);
        initFrame();

        layeredPane = new JLayeredPane();
        layeredPane.setPreferredSize(new Dimension(800, 600));
        setContentPane(layeredPane); // layeredPane을 프레임의 content pane으로 설정

        // 예시) ok버튼 클릭시 => GameFrame으로 창전환
        JButton button = new JButton("ok");
        button.setBounds(350, 250, 100, 40); // 버튼의 위치와 크기 설정
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GameFrame gameFrame = new GameFrame(gameDTO);
                gameFrame.setVisible(true);
                dispose(); // 기존의 창 닫기
            }
        });
        /*preference.setElo(Elo.ADVANCED);
        preference.setElo(Elo.BEGINNER);
        preference.setGameMode(GameMode.HUMAN_VS_HUMAN);
        preference.setGameMode(GameMode.MACHINE_VS_HUMAN);*/
        layeredPane.add(button);
    }

    private void initFrame() {
        setTitle("Preference");
        setSize(800, 600); // 새 프레임 크기 설정
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // 화면 중앙에 위치
        setResizable(false);
    }

    private void init(Preference preference) {
        this.preference = preference;
        try {
            gameDTO = new GameDTO(preference);
        } catch (StockfishInitException e) {
            throw new RuntimeException("StockFishEngine 생성 오류");
        }

    }


}
