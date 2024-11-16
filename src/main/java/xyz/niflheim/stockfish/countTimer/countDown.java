package countTimer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class countDown extends JFrame {
    private static final long serialVersionUID = 1L;
    private static int countdownTime; // 카운트다운 시간 (밀리초 단위)
    private JLabel countdownLabel; // 카운트다운 표시 라벨
    private Timer countdownTimer; // 카운트다운 타이머
    private boolean isRunning = false; // 타이머 상태

    public countDown() {
        setTitle("Countdown Timer");
        setSize(450, 300);  // 프레임 크기
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // 타이머 표시를 위한 라벨
        countdownLabel = new JLabel("00:00:00.0", SwingConstants.CENTER);
        countdownLabel.setFont(new Font("Arial", Font.BOLD, 40));
        add(countdownLabel, BorderLayout.CENTER);

        // 입력 패널 (시간, 분, 초 입력)
        JPanel timeInputPanel = new JPanel();
        timeInputPanel.add(new JLabel("Hours:"));
        JTextField hoursField = new JTextField(3);
        timeInputPanel.add(hoursField);

        timeInputPanel.add(new JLabel("Minutes:"));
        JTextField minutesField = new JTextField(3);
        timeInputPanel.add(minutesField);

        timeInputPanel.add(new JLabel("Seconds:"));
        JTextField secondsField = new JTextField(3);
        timeInputPanel.add(secondsField);

        add(timeInputPanel, BorderLayout.NORTH);

        // 버튼 패널
        JPanel buttonPanel = new JPanel();
        JButton startButton = new JButton("Countdown");
        JButton stopButton = new JButton("Stop");
        JButton resetButton = new JButton("Reset");

        stopButton.setEnabled(false); // 초기 상태에서는 Stop 버튼 비활성화
        resetButton.setEnabled(false); // 초기 상태에서는 Reset 버튼 비활성화

        buttonPanel.add(startButton);
        buttonPanel.add(stopButton);
        buttonPanel.add(resetButton);

        add(buttonPanel, BorderLayout.SOUTH);

        // Start Countdown 버튼 동작
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int hours = Integer.parseInt(hoursField.getText());
                    int minutes = Integer.parseInt(minutesField.getText());
                    int seconds = Integer.parseInt(secondsField.getText());

                    // 카운트다운 시간 설정 (밀리초 단위로 변환)
                    countdownTime = (hours * 3600 + minutes * 60 + seconds) * 1000;
                    startCountdown(countdownTime); // 카운트다운 시작

                    // 버튼 상태 변경
                    startButton.setEnabled(false); // Start 버튼 비활성화
                    stopButton.setEnabled(true);  // Stop 버튼 활성화
                    resetButton.setEnabled(true); // Reset 버튼 활성화
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(countDown.this, "올바른 시간을 입력해주세요!", "입력 오류", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Stop 버튼 동작
        stopButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                stopCountdown(); // 카운트다운 멈추기

                // 버튼 상태 변경
                startButton.setEnabled(true);  // Start 버튼 활성화
                stopButton.setEnabled(false);  // Stop 버튼 비활성화
                resetButton.setEnabled(true);  // Reset 버튼 활성화
            }
        });

        // Reset 버튼 동작
        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resetCountdown(); // 카운트다운 초기화

                // 버튼 상태 변경
                startButton.setEnabled(true);  // Start 버튼 활성화
                stopButton.setEnabled(false);  // Stop 버튼 비활성화
                resetButton.setEnabled(false); // Reset 버튼 비활성화
            }
        });

        // 카운트다운 타이머 로직
        countdownTimer = new Timer(100, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (countdownTime <= 0) {
                    countdownTimer.stop(); // 타이머 멈추기
                    countdownLabel.setText("00:00:00.0"); // "00:00:00.0" 표시
                    JOptionPane.showMessageDialog(countDown.this, "Time's up!", "Timer Finished", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    countdownTime -= 100; // 100ms씩 감소
                    int tenths = (countdownTime / 100) % 10;
                    int seconds = (countdownTime / 1000) % 60;
                    int minutes = (countdownTime / (1000 * 60)) % 60;
                    int hours = (countdownTime / (1000 * 60 * 60));

                    countdownLabel.setText(String.format("%02d:%02d:%02d.%d", hours, minutes, seconds, tenths));
                }
            }
        });
    }

    // 카운트다운 시작
    private void startCountdown(int timeInMillis) {
        countdownTime = timeInMillis; // 카운트다운 시간 설정
        countdownTimer.start(); // 타이머 시작
        isRunning = true;
    }

    // 카운트다운 멈추기
    private void stopCountdown() {
        countdownTimer.stop(); // 타이머 멈추기
        isRunning = false;
    }

    // 카운트다운 초기화
    private void resetCountdown() {
        countdownTimer.stop(); // 타이머 멈추기
        countdownTime = 0; // 타이머 시간 초기화
        countdownLabel.setText("00:00:00.0"); // 라벨 초기화
        isRunning = false;
    }
}
