import acm.graphics.*;
import acm.program.*;
import acm.util.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
/**
 * Write a description of class Collectible here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Collectible extends GCompound
{
    //constants
    private static final int SIZE = 32;

    // instance variables
    private boolean isCoins = false;
    private boolean isHealth = false;
    private int coins;
    private int health;
    private int row;
    private int col;
    private HackNSlashMain game;
    private boolean pickedUp = false;
    private GImage coin = new GImage("coin.png");;
    private GImage healthPot = new GImage("healthPotion.png");;

    public Collectible(boolean isCoins, boolean isHealth, int coins, int health, int row, int col, HackNSlashMain game){
        //save passed data
        this.isCoins = isCoins;
        this.isHealth = isHealth;
        this.coins = coins;
        this.health = health;
        this.row = row;
        this.col = col;
        this.game = game;

        //add the currect image
        if(isCoins){
            add(coin, -SIZE/2, -SIZE/2);
        }else if(isHealth){
            add(healthPot, -SIZE/2, -SIZE/2);
        }
    }

    /** add the health or coins to the player */
    public void pickUp(){
        if(!pickedUp){
            game.player.addCoins(coins);
            //game.player.addHealth(health);
            
            //make the collectible disapear
            remove(coin);
            remove(healthPot);
            //dont let it be picked up again
            pickedUp = true;
            game.removeLoot(this);
            game.cell[row][col].removeObject(this);
        }
        
    }

    //helper methods for the collectibles location
    public int getRow(){
        return row;
    }

    public int getCol(){
        return col;
    }
}
