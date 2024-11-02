package xyz.niflheim.stockfish.study.hw1;

import javax.swing.*;
import java.awt.*;
import java.util.List;


public class JLabelWithString extends JLabel{
    private List<String> numberList = List.of("1", "2", "3", "4", "5", "6", "7", "8", "9", "10");

    public JLabelWithString() {
        setFont(new Font("Arial", Font.BOLD, 32)); // 큰 글씨 설정
        setHorizontalAlignment(SwingConstants.CENTER); // 가운데 정렬
    }

    // [1단계 예시코드] : 1초마다 label의 문자열이 변경되는 로직
    public void startUpdatingLabel() {
        for(String num : numberList) {
            try {
                Thread.sleep(1000); // 1초 정지
                setText(num); //텍스트 설정
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
    //[2단계 미션] : 1초마다 label의 기존의 문자열이 사라지는 것이 아닌, 더해지는 방식으로 나타내보기 ex)  [1] [1 2] [1 2 3] [1 2 3 4]
    public void startUpdateLabel2() {
        for(String num : numberList) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public static void main(String[] args) {
        JLabelWithString jLabelWithString = new JLabelWithString();
        JFrame frame = new JFrame("Number Display");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(jLabelWithString);
        frame.setSize(400, 200);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);


        jLabelWithString.startUpdatingLabel(); // 1단계
    }
}
