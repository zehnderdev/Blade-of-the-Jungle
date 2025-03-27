package Entitiy;

import main.Gamepanel;
import main.KeyHandler;


import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Player extends Entity {
    Gamepanel gp;
    KeyHandler key;
    public int count;
    // Bilder Arrays
    private BufferedImage[] idleL;
    private BufferedImage[] idleR;
    private BufferedImage[] left;
    private BufferedImage[] right;
    private BufferedImage[] attackL;
    private BufferedImage[] attackR;
    // Jump und gravity varibalen
    private boolean isJumping;
    private boolean isFalling;
    private boolean canDoubleJump; // Neue Variable für Doppelsprung
    private double jumpSpeed;
    private double currentJumpSpeed;
    private double currentFallSpeed;
    private double gravity;
    // Scores des Spielers
    public int highscore;
    public int score;
    // Invincible Werte
    public boolean invincible;
    private int invincibleCounter;
    private final int invincibleDuration = 60; // 60 Frames also 1 sec Invicibility

    public Player(Gamepanel gp, KeyHandler key) {
        this.gp = gp;
        this.key = key;
        solidArea = new Rectangle(25, 50, 50, 50); // (Hit)Box des Spielers/Schwerts
        swordArea = new Rectangle(-25, 25, 130, 30);

        setDefaultValues();
        loadPlayerImages();
    }
    // Anfangswerte des Spielers
    public void setDefaultValues() {
        x = 100;
        y = 100;
        speed = 4;
        direction = "right";
        count = 1;

        isJumping = false;
        isFalling = true; // Am Anfang starten wir in der Luft deswegen true erst false wenn wir den boden berühren(collision)
        canDoubleJump = true; // Initialisieren, um Doppelsprung zu ermöglichen
        jumpSpeed = 20;
        currentJumpSpeed = jumpSpeed;
        currentFallSpeed = 0;
        gravity = 1;
        lives = 3;
        invincible = false;
        invincibleCounter = 0;
    }

    private void loadPlayerImages() {
        idleL = new BufferedImage[9];
        idleR = new BufferedImage[9];
        left = new BufferedImage[6];
        right = new BufferedImage[6];
        attackL = new BufferedImage[12];
        attackR = new BufferedImage[12];
        // Bilder werden geladen
        try {
            for (int i = 0; i <= 8; i++) {
                idleL[i] = ImageIO.read(getClass().getResourceAsStream("/res/player/idle/NightBorne_idle" + (i + 1) + "_left.png"));
                idleR[i] = ImageIO.read(getClass().getResourceAsStream("/res/player/idle/NightBorne_idle" + (i + 1) + ".png"));
            }
            for (int i = 0; i < 6; i++) {
                left[i] = ImageIO.read(getClass().getResourceAsStream("/res/player/run/NightBorne_left_" + (i + 1) + ".png"));
                right[i] = ImageIO.read(getClass().getResourceAsStream("/res/player/run/NightBorne_right_" + (i + 1) + ".png"));
            }
            for (int i = 0; i < 12; i++) {
                attackL[i] = ImageIO.read(getClass().getResourceAsStream("/res/player/attack/NightBorne_attack" + (i + 1) + "_left.png"));
                attackR[i] = ImageIO.read(getClass().getResourceAsStream("/res/player/attack/NightBorne_attack" + (i + 1) + ".png"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void update() {
        lastdirection = direction; // Letzte direction für idle position
        if (x<0){
            x=0;
        }
        if (x>1400){
            x= 1400;
        }
        // Fall update
        if (isFalling) {
            y += currentFallSpeed;
            currentFallSpeed += gravity;
        }
        // Jump update
        if (isJumping) {
            y -= currentJumpSpeed;
            currentJumpSpeed -= gravity;
            if (currentJumpSpeed <= 0) {
                isJumping = false;
                isFalling = true;
                currentFallSpeed = 1;
            }

        }
        // Sprunglogik mit key
        if (key.up) {
            if (!isJumping && !isFalling) {
                isJumping = true;
                isFalling = false;
                currentJumpSpeed = jumpSpeed;
            } else if (!isJumping && isFalling && canDoubleJump) {
                isJumping = true;
                isFalling = false;
                currentJumpSpeed = jumpSpeed;
                canDoubleJump = false; //
            }
        }
        // Attacklogik mit key
        if (key.isattack && !isattacking) {

            isattacking = true;
            attackcount = 1;
        } else if (key.down) {
            y += speed;
            idleState = false;
        } else if (key.left) {
            x -= speed;
            direction = "left";
            idleState = false;
        } else if (key.right) {
            x += speed;
            direction = "right";
            idleState = false;
        } else if (key.up) {
            y -= speed;
            idleState = false;
        } else {
            idleState = true;
        }

        // Invincible state
        if (invincible) {
            invincibleCounter++;
            if (invincibleCounter >= invincibleDuration) {
                invincible = false;
                invincibleCounter = 0;
            }
        }

        collisionOn = false;

        // Boden kollision
        if (y >= gp.getHeight() - 240) {
            y = gp.getHeight() - 240;
            isFalling = false;
            canDoubleJump = true;
        }

        spriteCounter++;
        // Animations counter handler
        if (spriteNum >= 6) {
            spriteNum = 1;
        }
        if (attackcount >= 12) {
            isattacking = false;
            attackcount = 1;
        }

        if (isattacking && spriteCounter % 5 == 0) {
            attackcount++;
        } else if (!isattacking && spriteCounter % 10 == 0) {
            spriteNum++;
        }
    }

    public void draw(Graphics2D g2) {
        BufferedImage image = null;
        // Attack animation
        if (isattacking) {
            if (direction.equals("right")) {
                image = attackR[attackcount - 1];
            } else if (direction.equals("left")) {
                image = attackL[attackcount - 1];
            }
        } else if (idleState) { // idle Animation
            if (direction.equals("right")) {
                image = idleR[spriteNum];
            } else if (direction.equals("left")) {
                image = idleL[spriteNum];
            }
        } else { // Lauf animation
            if (direction.equals("right")) {
                image = right[spriteNum % 6];
            } else if (direction.equals("left")) {
                image = left[spriteNum % 6];
            }
        }
        g2.drawImage(image, x, y, 150, 150, null);

    }

    // Spieler nimmt schaden und geht dann in den invincible state
    public void takeDamage(int damage) {
        if (!invincible) {
            lives -= damage;
            invincible = true;
            invincibleCounter = 0;
        }
    }
    // Spieler attackiert/trifft Troll
    public void attack(Enemy enemy) {
        if (isattacking && attackcount > 9) {
            Rectangle swordRect = new Rectangle(x + swordArea.x, y + swordArea.y, swordArea.width, swordArea.height);
            Rectangle enemyRect = new Rectangle(enemy.x + enemy.solidArea.x, enemy.y + enemy.solidArea.y, enemy.solidArea.width, enemy.solidArea.height);
            if (swordRect.intersects(enemyRect)) {
                if (score % 2 == 0) {
                    enemy.setDefaultValues(-100, "right");
                } else {
                    enemy.setDefaultValues(1400, "left");
                }
                score++;
            }
        }
    }
}
