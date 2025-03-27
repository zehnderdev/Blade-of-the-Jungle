package main;


import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;


public class UI {
    Gamepanel gp;
    Font arial_40;
    BufferedImage[] heartImage = new BufferedImage[4];
    public UI(Gamepanel gp)  {
        this.gp = gp;
        arial_40 = new Font("Arial",Font.PLAIN,40); // Font für unser schrift
        try { // Laden der heart images
            for (int i = 3; i >=0 ; i--) {
                assert false;
                heartImage[i] = ImageIO.read(getClass().getResourceAsStream("/res/health/heart/heart"+i+".png"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public void draw(Graphics2D g2){
       g2.setFont(arial_40); // Font gesetzt
       g2.setColor(Color.yellow); // schrift wird gelb gemacht
       g2.drawImage(heartImage[gp.player.lives],50,50,300,94,null); // heart image wird gedrawed
       g2.drawString("Highscore = "+gp.player.highscore,1250,100); // highscore wird gedrawed
       g2.setColor(Color.white);     // Schriftfarbe zu weiß geändert
        g2.drawString("Score = "+gp.player.score,1250,150); // score wird gedrawed

    }
}
