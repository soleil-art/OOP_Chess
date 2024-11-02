package xyz.niflheim.stockfish.ui;

import javax.swing.*;
import java.awt.*;

public class SquarePanel extends JPanel {
    private boolean highlight = false;

    public SquarePanel(LayoutManager layout) {
        super(layout);
    }

    public void setHighlight(boolean highlight) {
        this.highlight = highlight;
        repaint();
    }

    public boolean isHighlight() {
        return highlight;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g); // 기본 페인팅

        if(highlight) {
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            if (this.getBackground()==Color.decode("#7D945D")) {
                g2d.setColor(Color.decode("#F08080"));
            }
            else
                g2d.setColor(Color.decode("#F08080"));
            int diameter = Math.min(getWidth(), getHeight())-10;
            int x = (getWidth() - diameter) / 2;
            int y = (getHeight() - diameter) / 2;
            g2d.fillRect(x, y, diameter, diameter); // 네모 그리기
        }
    }
}
