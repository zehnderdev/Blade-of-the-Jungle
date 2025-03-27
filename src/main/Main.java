package main;

import javax.swing.*;
import java.awt.*;

public class  Main{

    // Main datei hier wird unser window erstellt und damit
    public static void main(String[] args){
        JFrame window = new JFrame();
        window.setIconImage(Toolkit.getDefaultToolkit().getImage(Main.class.getResource("/res/Icon/bomba.jpg")));// icon setzter
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Wenn wir das window schließen wird auch das Spiel geschlossen
        window.setResizable(false); // kann das window nicht kleiner/größer machen
        window.setTitle("Blade of the Jungle"); // Titel
        Gamepanel gamepanel = new Gamepanel();
        window.add(gamepanel);
        window.pack();
        window.setLocationRelativeTo(null);
        window.setVisible(true);
        gamepanel.startgame();// start des Game panels
    }
}