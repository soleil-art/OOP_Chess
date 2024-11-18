package xyz.niflheim.stockfish.ui.board;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SurrenderPanel extends JPanel {
    public SurrenderPanel(JFrame frame) {
        setLayout(new FlowLayout());

        JButton surrenderButton = new JButton("항복");
        surrenderButton.setFont(new Font("맑은 고딕", Font.BOLD, 20));
        surrenderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // VictoryPanel로 전환
                frame.getContentPane().removeAll();
                VictoryPanel victoryPanel = new VictoryPanel(); // VictoryPanel은 별도 구현
                frame.getContentPane().add(victoryPanel);

                // VictoryPanel에서 권장하는 크기로 프레임 크기 변경
                Dimension preferredSize = victoryPanel.getPreferredSize();
                frame.setSize(preferredSize.width, preferredSize.height);
                frame.setLocationRelativeTo(null); // 화면 중앙 정렬

                frame.revalidate();
                frame.repaint();
            }
        });

        add(surrenderButton);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Surrender Example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);

        // SurrenderPanel 추가
        frame.add(new SurrenderPanel(frame));

        frame.setVisible(true);
    }
}
