package Entitiy;

import java.awt.*;


public class Entity {
    public int x,y;
    public int speed;
    // In welche Richtung der Enitity schaut
    public String direction;
    public String lastdirection;
    // Animations counter
    public int spriteCounter = 0;
    public int spriteNum = 1;
    public boolean idleState= true;// Boolean für idle animation
    public boolean isattacking = false;// Boolean für attack von Entity(Player)
    public int attackcount=0;
    public boolean leftlauf;
    public int lives;// Leben
    public Rectangle solidArea;// HitBox der Enititys
    public Rectangle swordArea;// Box des Schwerts für intersects
    public boolean collisionOn = false; // Collision boolean für invisible state
}
