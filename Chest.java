import acm.graphics.*;
import acm.program.*;
import acm.util.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
/**
 * Write a description of class Chest here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Chest extends GCompound
{
    //Constants
    private static final int SIZE = 32;
    
    //chest images
    private GImage chestClosed = new GImage("chestClosed.png");
    private GImage chestOpened = new GImage("chestOpened.png");
    
    // instance variables
    private int floorNumber; //what floor number the chest resides on
    private boolean isHidden; //whether or not the chest was initially hiddden
    private boolean isOpen = false; //whether or not the chest has been opened
    private int chestValue = 0; //the quality of items inside the chest
    private int row; //coords of the chest
    private int col;
    private HackNSlashMain game; //instance of the game the chest is in
    private RandomGenerator rand = RandomGenerator.getInstance();

    /** Chest() constuctor for the class */
    public Chest(int floorNumber, boolean isHidden, int row, int col, HackNSlashMain game){
        //save passed data
        this.floorNumber = floorNumber;
        this.isHidden = isHidden;
        this.row = row;
        this.col = col;
        this.game = game;
        
        //give the chest a value number
        if(isHidden){
            chestValue += 5;
        }
        if(floorNumber < 3){
            chestValue += 4;
        }
        chestValue += floorNumber;
        
        //display a closed chest
        add(chestClosed, -SIZE/2, -SIZE/2);
        //add the chest to the map
        game.cell[row][col].addObject(this);
    }

    /** openChest() generate loot based on chest value */
    public void openChest(){
        if(isOpen) return;
        isOpen = true;
        //generate random values of coins and health
        int health = rand.nextInt(chestValue) + chestValue/4;
        int coins = rand.nextInt(chestValue) + chestValue/4;
        
        //add health and animate
        game.player.addHealth(health);
        
        //give the player the coins
        game.player.addCoins(coins);
        //display an opened chest
        removeAll();
        add(chestOpened, -SIZE/2, -SIZE/2);
        
        //randomly spawn monsters
        //monster type depends on the chest value
    }
    
    //helper methods for chest location
    public int getRow(){
        return row;
    }
    
    public int getCol(){
        return col;
    }
}
