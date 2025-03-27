package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener; // listener um auf keys zu reagieren

public class KeyHandler implements KeyListener {

    public boolean up, down, left, right, isattack, restart;// Booleans für key presses

    @Override
    public void keyTyped(KeyEvent e) {}
    // Key logic
    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_SPACE) {
            up = true;
        }
        if (key == KeyEvent.VK_S) {
            down = true;
        }
        if (key == KeyEvent.VK_A) {
            left = true;
        }
        if (key == KeyEvent.VK_D) {
            right = true;
        }
        if (key == KeyEvent.VK_Q) {
            isattack = true;
        }
        if (key == KeyEvent.VK_R) {
            restart = true;
        }
    }
    // key logicd
    @Override
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_SPACE) {
            up = false;
        }
        if (key == KeyEvent.VK_S) {
            down = false;
        }
        if (key == KeyEvent.VK_A) {
            left = false;
        }
        if (key == KeyEvent.VK_D) {
            right = false;
        }
        if (key == KeyEvent.VK_Q) {
            isattack = false;
        }
        if (key == KeyEvent.VK_R) {
            restart = false;
        }
    }
}
