package main;


import java.awt.*;
import java.awt.image.BufferedImage;


public class EndScreen {
    Gamepanel gp;
    private BufferedImage backgroundImage; // Hintergrundbild
    public EndScreen(Gamepanel gp) {
        this.gp = gp;


    }
    // Draw des endscreens
    public void draw(Graphics2D g2) {
        g2.setColor(Color.RED);
        String text = "YOU DIED";
        g2.setFont(new Font("Arial", Font.PLAIN, 100));
        int x = (gp.getWidth() - g2.getFontMetrics().stringWidth(text)) / 2;
        int y = gp.getHeight() / 2 -50;
        g2.drawString(text, x, y);
        g2.setColor(Color.white);
        g2.setFont(new Font("Arial", Font.PLAIN, 50));
        text = "Game Over";
        x = (gp.getWidth() - g2.getFontMetrics().stringWidth(text)) / 2;
        y = gp.getHeight() / 2 + 50;
        g2.drawString(text, x, y);

        text = "Score: " + gp.player.score; // Score display
        x = (gp.getWidth() - g2.getFontMetrics().stringWidth(text)) / 2;
        y += 50;
        g2.drawString(text, x, y);

        text = "Press R to Restart";
        x = (gp.getWidth() - g2.getFontMetrics().stringWidth(text)) / 2;
        y += 50;
        g2.drawString(text, x, y);

    }
}
