package xyz.niflheim.stockfish.ui.launch;

import org.apache.log4j.Logger;
import xyz.niflheim.stockfish.repository.UserRepository;
import xyz.niflheim.stockfish.ui.preference.ChessModeSelectionPanel;
import xyz.niflheim.stockfish.util.Preference;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class LoginPanel extends JPanel {

    private static final Logger log = Logger.getLogger(LoginPanel.class);
    private ImageIcon usernameDefaultIcon;
    private ImageIcon usernameTypingIcon;
    private ImageIcon passwordDefaultIcon;
    private ImageIcon passwordTypingIcon;

    private ImageIcon eyeIcon;
    private ImageIcon eyeSlashIcon;
    private boolean isPasswordVisible = false;

    private boolean isUsernameTyping = false; // 입력 여부 확인
    private boolean isPasswordTyping = false; // 입력 여부 확인

    UserRepository userRepository;
    LaunchFrame launchFrame;

    public LoginPanel(LaunchFrame launchFrame) {
        this.launchFrame = launchFrame;
        setLayout(null); // 사용자 정의 레이아웃 설정
        setBounds(100, 50, 522, 515); // 패널 크기와 위치 설정

        userRepository  = new UserRepository();

        // 배경 이미지 JLabel 생성
        JLabel backgroundLabel = new JLabel(new ImageIcon(getClass().getResource("/image/hello.png")));
        backgroundLabel.setBounds(0, 0, 522, 515); // 배경 이미지 크기 설정
        add(backgroundLabel); // 배경 이미지를 패널에 추가

        // 기본 및 입력 중 상태의 아이콘 로드
        usernameDefaultIcon = new ImageIcon(getClass().getResource("/image/UsernameIcon.png"));
        usernameTypingIcon = new ImageIcon(getClass().getResource("/image/UsernameIconTyping.png"));
        passwordDefaultIcon = new ImageIcon(getClass().getResource("/image/PasswordIcon.png"));
        passwordTypingIcon = new ImageIcon(getClass().getResource("/image/PasswordIconTyping.png"));

        eyeIcon = new ImageIcon(getClass().getResource("/image/eye.png")); // 눈 아이콘
        eyeSlashIcon = new ImageIcon(getClass().getResource("/image/eye-slash.png")); // 눈 아이콘 숨김

        // Username 필드 (아이콘 포함 배경, 투명 텍스트 필드, 입력 시 배경 변경)
        JTextField usernameField = new JTextField() {
            @Override
            protected void paintComponent(Graphics g) {
                ImageIcon icon = isUsernameTyping ? usernameTypingIcon : usernameDefaultIcon;
                g.drawImage(icon.getImage(), 0, 0, 340, 40, this);
                setOpaque(false);
                super.paintComponent(g);
            }
        };
        usernameField.setBounds(85, 170, 340, 40); // 위치와 크기 설정
        usernameField.setForeground(Color.decode("#9E9D9C")); // 텍스트 색상 설정
        usernameField.setFont(new Font("Arial", Font.BOLD, 16)); // 폰트 설정
        usernameField.setBorder(BorderFactory.createEmptyBorder(10, 40, 10, 10)); // 왼쪽에서 40px의 패딩 적용

        // 입력 시 배경을 변경하기 위한 DocumentListener
        usernameField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                isUsernameTyping = true;
                usernameField.repaint();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                isUsernameTyping = usernameField.getText().length() > 0;
                usernameField.repaint();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                // Plain text components don't fire these events.
            }
        });

        backgroundLabel.add(usernameField);

        // Password 필드 (아이콘 포함 배경, 투명 텍스트 필드, 입력 시 배경 변경)
        JPasswordField passwordField = new JPasswordField() {
            @Override
            protected void paintComponent(Graphics g) {
                ImageIcon icon = isPasswordTyping ? passwordTypingIcon : passwordDefaultIcon;
                g.drawImage(icon.getImage(), 0, 0, 340, 40, this);
                setOpaque(false);
                super.paintComponent(g);
            }
        };
        passwordField.setBounds(85, 240, 300, 40); // 위치와 크기 설정
        passwordField.setForeground(Color.decode("#9E9D9C")); // 텍스트 색상 설정
        passwordField.setFont(new Font("Arial", Font.BOLD, 16)); // 폰트 설정
        passwordField.setBorder(BorderFactory.createEmptyBorder(10, 40, 10, 5)); // 왼쪽에서 40px의 패딩 적용

        // 입력 시 배경을 변경하기 위한 DocumentListener
        passwordField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                isPasswordTyping = true;
                passwordField.repaint();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                isPasswordTyping = passwordField.getPassword().length > 0;
                passwordField.repaint();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                // Plain text components don't fire these events.
            }
        });

        backgroundLabel.add(passwordField);

        // 비밀번호 표시/숨김 버튼 추가
        JLabel eyeLabel = new JLabel(eyeIcon); // 기본은 눈 아이콘
        eyeLabel.setBounds(385, 240, 40, 40); // 아이콘의 위치 설정
        eyeLabel.setCursor(new Cursor(Cursor.HAND_CURSOR)); // 커서를 손 모양으로 변경

        // 클릭 이벤트 추가
        eyeLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (isPasswordVisible) {
                    // 비밀번호 숨김 (마스킹 처리)
                    passwordField.setEchoChar('*');
                    eyeLabel.setIcon(eyeIcon); // 눈 아이콘
                    isPasswordVisible = false;
                } else {
                    // 비밀번호 보이기
                    passwordField.setEchoChar((char) 0); // 마스킹 해제
                    eyeLabel.setIcon(eyeSlashIcon); // 눈 숨김 아이콘
                    isPasswordVisible = true;
                }
            }
        });
        backgroundLabel.add(eyeLabel);

        // Remember me 체크박스 추가
        JCheckBox rememberMeCheckbox = new JCheckBox("Remember me");
        rememberMeCheckbox.setBounds(85, 300, 150, 30);
        rememberMeCheckbox.setForeground(Color.WHITE);
        rememberMeCheckbox.setOpaque(false); // 투명한 배경 설정
        backgroundLabel.add(rememberMeCheckbox);

        // Forgot Password 라벨 추가
        JLabel forgotPasswordLabel = new JLabel("Forgot Password?");
        forgotPasswordLabel.setBounds(245, 300, 150, 30);
        forgotPasswordLabel.setForeground(Color.WHITE);
        backgroundLabel.add(forgotPasswordLabel);

        // 로그인 버튼 추가 (아이콘 포함)
        JButton loginPanelButton = new JButton(new ImageIcon(getClass().getResource("/image/LoginButton.png")));
        loginPanelButton.setBounds(85, 350, 340, 50); // 위치와 크기 설정
        loginPanelButton.setContentAreaFilled(false); // 버튼 배경 투명화
        loginPanelButton.setFocusPainted(false); // 포커스 테두리 제거

        loginPanelButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                //게임 선호 설정
                Preference preference = new Preference(usernameField.getText());
                ChessModeSelectionPanel chessModeSelectionPanel = new ChessModeSelectionPanel(preference);
                JFrame frame = new JFrame("Chess Game Mode Selection");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setSize(800, 600);
                frame.add(chessModeSelectionPanel);
                frame.setResizable(false); // 창 크기 변경 불가
                frame.setVisible(true);
                frame.setLocationRelativeTo(null);
                SwingUtilities.getWindowAncestor(loginPanelButton).dispose();
            }
        });
        backgroundLabel.add(loginPanelButton);
    }

    public static void main(String[] args) {
        // 프레임 생성
        JFrame frame = new JFrame("Login");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(522, 515); // 패널 크기와 일치하도록 설정
        frame.setResizable(false); // 프레임 크기 고정
        frame.add(new LoginPanel(new LaunchFrame()));
        frame.setVisible(true);
    }
}




