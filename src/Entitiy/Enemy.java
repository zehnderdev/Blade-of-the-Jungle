package Entitiy;

import main.Gamepanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

// Enemy Klasse
public class Enemy extends Entity {
    Gamepanel gp;
    public int troll_num = 0;
    private BufferedImage[] attackRight;
    private BufferedImage[] attackLeft;

    public Enemy(Gamepanel gp) {
        this.gp = gp;
        solidArea = new Rectangle(0, 60, 130, 100); // Rectangle für hitbox
        setDefaultValues(1400,"left");
        loadEnemyImages();
    }
    //Normale Werte und Spawnpunkt
    public void setDefaultValues(int pos, String dir) {
        x = pos;
        y = 610;
        speed = 2;
        direction = dir;
        lives = 1;
    }
    // Animation des Trolls laden
    private void loadEnemyImages() {
        attackRight = new BufferedImage[9];
        attackLeft = new BufferedImage[9];

        try {
            for (int i = 0; i < 9; i++) {
                String number = String.format("%03d", i); //00x
                attackRight[i] = ImageIO.read(getClass().getResourceAsStream("/Enemy/Attack_right/Attack_right_" + number + ".png"));
                attackLeft[i] = ImageIO.read(getClass().getResourceAsStream("/Enemy/Attack_left/Attack_left_" + number + ".png"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void update() {
        if (x < -50) {
            leftlauf = false;
            direction = "right";
        } else if (x > 1200) {
            leftlauf = true;
            direction = "left";
        }

        if (leftlauf) {
            x -= speed;
        } else {
            x += speed;
        }
        spriteCounter++;

        if (spriteCounter % 5 == 0) {
            troll_num = (troll_num + 1) % 9; // Animations counter
        }
    }

    public void draw(Graphics2D g2) {
        BufferedImage image = null;
        // Bilder ausgeben
        if (direction.equals("right")) {
            image = attackRight[troll_num];
        } else if (direction.equals("left")) {
            image = attackLeft[troll_num];
        }

        if (image == null) {
            System.err.println("Image not found for troll_num: " + troll_num + ", direction: " + direction);
        } else {
            g2.drawImage(image, x, y, 200, 200, null);
        }
    }
}
