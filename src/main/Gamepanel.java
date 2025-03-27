package main;

import Entitiy.Enemy;
import Entitiy.Player;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Gamepanel extends JPanel implements Runnable {
    // Größe unseres gamepanels
    private final int screenwidth = 1600;
    private final int screenheight = 900;
    // FPS
    int fps = 60;




    KeyHandler key = new KeyHandler();
    Thread gameThread;
    public CollisionChecker cChecker = new CollisionChecker(this);
    Player player = new Player(this, key);
    Enemy enemy = new Enemy(this);
    public UI ui = new UI(this);
    private EndScreen endScreen = new EndScreen(this);

    private BufferedImage backgroundImage;
    private boolean gameOver = false;

    public Gamepanel() {
        this.setPreferredSize(new Dimension(screenwidth, screenheight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(key);
        this.setFocusable(true);


        // Lade das Hintergrundbild
        try {
            backgroundImage = ImageIO.read(getClass().getResourceAsStream("/res/Background/background.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    // Spiel starten (thread erstellen und laufen lassen)
    public void startgame() {
        gameThread = new Thread(this);
        gameThread.start();

    }

    @Override
    public void run() {
        // Intervall damit wir 60 Fps bekommen
        long drawInterval = 1000000000 / fps;
        double nextDrawTime = System.nanoTime() + drawInterval;
        // Nexter draw zeitpunkt
        // Endlosschleife, wenn wir den thread(das spiel) gestartet haben
        while (gameThread != null) {
            update(); // update
            repaint(); // Sachen painten
            // Thread schlafen legen für die Zeit wo wir kein
            // update/repaint benutzen
            try {
                double remainingTime = nextDrawTime - System.nanoTime();
                remainingTime = remainingTime / 1000000;

                if (remainingTime < 0) {
                    remainingTime = 0;
                }
                Thread.sleep((long) remainingTime);
                nextDrawTime += drawInterval;
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    // Update des Gamepanels
    // 60 mal die sekunde
    public void update() {
        if (!gameOver) {
            player.update(); // Spieler wird aktualiesiert
            enemy.update(); // Enemy(troll) wird aktualisiert
            cChecker.checkCollision(player, enemy);
            // Collision wird gecheckt
            // Fall wenn der spieler stirbt(0 leben hat)
            if (player.lives <= 0) {
                gameOver = true;
                if(player.score> player.highscore){
                    player.highscore = player.score;
                }

            }
        }else{
            if(key.restart){ // Restart funktion
                // Es wird, game over auf false gesetzt und
                // die werte unseres Spielers und trolls
                // werden wieder zurückgesetzt
                gameOver = false;
                player.setDefaultValues();
                enemy.setDefaultValues(1400,"left");
                player.score =0;
            }

        }

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);// super funktion um zu drawen
        Graphics2D g2 = (Graphics2D) g; // 2d grafik initialisierung

        // Zeichne das Hintergrundbild
        if (backgroundImage != null) {
            g2.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), null);
        }
        // Draw falls wir kein gameover haben
        if (!gameOver) {
            player.draw(g2);
            enemy.draw(g2);
            ui.draw(g2);
        } else {
            // Sonst endscreen
            endScreen.draw(g2);
        }

        g2.dispose();// dispose um nicht verwaltete Ressourcen freizugeben
    }
}
