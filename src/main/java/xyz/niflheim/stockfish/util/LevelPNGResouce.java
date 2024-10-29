package xyz.niflheim.stockfish.util;

import javax.swing.*;
import java.awt.*;

public class LevelPNGResouce {

    public static final LevelPNGResouce levelPNG = new LevelPNGResouce();
    public static String[] levels = {"체스 초심자", "초심자", "중급", "고급"};
    public static String[] imageFiles = {
            "/image/level/chess_beginner.png",
            "/image/level/beginner.png",
            "/image/level/intermediate.png",
            "/image/level/advanced.png"
    };
    public static ImageIcon[] imageIcons;
    public static ImageIcon background;

    public LevelPNGResouce() {
        imageIcons = new ImageIcon[4];
        for (int i = 0; i < imageFiles.length; i++) {
            // getClass().getResource()가 null을 반환할 가능성이 있으므로 검사 필요
            try {
                imageIcons[i] = new ImageIcon(getClass().getResource(imageFiles[i]));
                if (imageIcons[i].getImageLoadStatus() != MediaTracker.COMPLETE) {
                    System.err.println("Failed to load image: " + imageFiles[i]);
                }
            } catch (Exception e) {
                System.err.println("Error loading image: " + imageFiles[i]);
                e.printStackTrace();
            }
        }

        // 배경 이미지 로드
        try {
            background = new ImageIcon(getClass().getResource("/image/level/background.png"));
            if (background.getImageLoadStatus() != MediaTracker.COMPLETE) {
                System.err.println("Failed to load background image.");
            }
        } catch (Exception e) {
            System.err.println("Error loading background image.");
            e.printStackTrace();
        }
    }
}
