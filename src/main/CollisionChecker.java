package main;

import Entitiy.Enemy;
import Entitiy.Player;

import java.awt.*;

public class CollisionChecker {
    // brauchen immer das gamepanel damit wir auf die werte von z.B dem Spieler zugreifen können
    Gamepanel gp;

    public CollisionChecker(Gamepanel gp) {
        this.gp = gp;
    }
    // Collision funktion
    public void checkCollision(Player player, Enemy enemy) {
        // Erst checken wir ob der spieler den troll trifft
        player.attack(enemy);

        // Danach wenn wir den troll nicht getroffen/attackiert haben, dann checken wir ob wir mit der hitbox vom troll kollidiert sind
        if (!player.invincible) {
            Rectangle playerRect = new Rectangle(player.x + player.solidArea.x, player.y + player.solidArea.y, player.solidArea.width, player.solidArea.height);
            Rectangle enemyRect = new Rectangle(enemy.x + enemy.solidArea.x, enemy.y + enemy.solidArea.y, enemy.solidArea.width, enemy.solidArea.height);
            // wenn ja wird ein leben abgezogen
            if (playerRect.intersects(enemyRect)) {
                player.takeDamage(1);
            }
        }
    }
}
