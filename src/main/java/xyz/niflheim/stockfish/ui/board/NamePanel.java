package xyz.niflheim.stockfish.ui.board;

import javax.swing.*;

public class NamePanel extends JPanel {

    private String nickName;
    private JLabel nickNameDisplay;

    public NamePanel(String nickName) {
        this.nickName = nickName;
        nickNameDisplay = new JLabel(nickName);
        add(nickNameDisplay); // 패널에 jLabel 추가
        setBounds(100, 50, 522, 515); // 패널 크기와 위치 설정
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("test Frame"); // 테스트 코드
        NamePanel userNamePanel = new NamePanel("User Name");

        frame.add(userNamePanel);
        frame.setSize(800, 800);
        frame.setLocationRelativeTo(null); // 창을 화면 중앙에 배치
        frame.pack();

        // 프레임을 표시
        frame.setVisible(true);
    }
}
