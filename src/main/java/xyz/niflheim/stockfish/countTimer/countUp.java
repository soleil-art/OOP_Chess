package xyz.niflheim.stockfish.countTimer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class countUp {
    private static int startTime; // 타이머 시작 시간
    private static Timer swingTimer; // Swing 타이머
    private static boolean isRunning = false; // 타이머 상태

    public static void main(String[] args) {
        // 메인 프레임 생성
        JFrame frame = new JFrame("Timer");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 250);  // 높이를 조금 더 키움
        frame.setLayout(new BorderLayout());

        // 타이머 표시를 위한 라벨
        JLabel timerLabel = new JLabel("00:00:00.0", SwingConstants.CENTER);
        timerLabel.setFont(new Font("Arial", Font.BOLD, 40));
        frame.add(timerLabel, BorderLayout.CENTER);

        // 버튼 패널
        JPanel buttonPanel = new JPanel();
        JButton startButton = new JButton("Start");
        JButton stopButton = new JButton("Stop");
        JButton resetButton = new JButton("Reset");
        JButton countdownButton = new JButton("Countdown");
        
        stopButton.setEnabled(false); // 초기 상태에서는 Stop 버튼 비활성화
        resetButton.setEnabled(false); // 초기 상태에서는 Reset 버튼 비활성화
        countdownButton.setEnabled(true); // 카운트다운 버튼 활성화
        
        buttonPanel.add(startButton);
        buttonPanel.add(stopButton);
        buttonPanel.add(resetButton);
        buttonPanel.add(countdownButton);
        frame.add(buttonPanel, BorderLayout.SOUTH);

        // 타이머 로직 (카운트업)
        swingTimer = new Timer(100, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int elapsedTime = (int) (System.currentTimeMillis() - startTime);  // 경과 시간 계산
                int tenths = (elapsedTime / 100) % 10;  // 100ms 단위
                int seconds = (elapsedTime / 1000) % 60;  // 초 단위
                int minutes = (elapsedTime / (1000 * 60)) % 60;  // 분 단위
                int hours = (elapsedTime / (1000 * 60 * 60));  // 시간 단위

                // 타이머 표시 업데이트
                String timeText = String.format("%02d:%02d:%02d.%d", hours, minutes, seconds, tenths);
                timerLabel.setText(timeText);
            }
        });

        // Start 버튼 동작
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!isRunning) {
                    startTime = (int) System.currentTimeMillis();  // 타이머 시작 시간 설정 (밀리초 단위)
                    swingTimer.start(); // 타이머 시작
                    isRunning = true;
                    startButton.setEnabled(false); // Start 버튼 비활성화
                    stopButton.setEnabled(true); // Stop 버튼 활성화
                    resetButton.setEnabled(true); // Reset 버튼 활성화
                }
            }
        });

        // Stop 버튼 동작
        stopButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (isRunning) {
                    swingTimer.stop(); // 타이머 중지
                    isRunning = false;
                    startButton.setEnabled(true); // Start 버튼 활성화
                    stopButton.setEnabled(false); // Stop 버튼 비활성화
                    resetButton.setEnabled(true); // Reset 버튼 활성화
                }
            }
        });

        // Reset 버튼 동작
        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                swingTimer.stop(); // 타이머 중지
                isRunning = false;
                timerLabel.setText("00:00:00.0"); // 타이머 초기화
                startButton.setEnabled(true); // Start 버튼 활성화
                stopButton.setEnabled(false); // Stop 버튼 비활성화
                resetButton.setEnabled(false); // Reset 버튼 비활성화
            }
        });

        // Countdown 버튼 동작
        countdownButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 카운트다운 타이머 UI로 이동
                countDown countdownGUI = new countDown();
                countdownGUI.setVisible(true); // 카운트다운 프레임 표시
                frame.dispose(); // 현재 메인 타이머 UI 종료
            }
        });

        // 프레임 표시
        frame.setVisible(true);
    }
}
