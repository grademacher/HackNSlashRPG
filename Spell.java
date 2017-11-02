import acm.graphics.*;
import acm.program.*;
import acm.util.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

/**
 * Write a description of class Spell here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Spell extends GCompound implements Runnable
{
    //constants
    private static final int DELAY = 25;
    private static final int SIZE = 32;
    
    //SPELL IMAGES
    private GImage iceSpell = new GImage("iceSpell.png");
    private GImage fireSpell = new GImage("fireSpell.png");
    private GImage windSpell = new GImage("windSpell.png");
    private GImage curseSpell = new GImage("curseSpell.png");
    
    // instance variables
    private int spellType; //1 = ice, 2 = fire, 3 = wind, 4 = curse 
    private HackNSlashMain game;
    private boolean isAlive = true;
    private int direction; //1 = up, 2 = down, 3 = left, 4 = right
    private int row;
    private int col;

    public Spell(int row, int col, int direction, int spellType, HackNSlashMain game){
        //save passed data
        this.row = row;
        this.col = col;
        this.direction = direction;
        this.spellType = spellType;
        this.game = game;
        
        if(spellType==1){
            add(iceSpell, -SIZE/2, -SIZE/2);
        }else if(spellType==2){
            add(fireSpell, -SIZE/2, -SIZE/2);
        }else if(spellType==3){
            add(windSpell, -SIZE/2, -SIZE/2);
        }else if(spellType==4){
            add(curseSpell, -SIZE/2, -SIZE/2);
        }
        
        if(direction == 1){this.row--;} //move up   
        else if(direction == 2){this.row++;} //move down   
        else if(direction == 3){this.col--;} //move left   
        else if(direction == 4){this.col++;} //move right
        //check if spawn in wall
        if(!game.cell[this.row][this.col].getIsPassable()){
            //kill the current thread
            this.isAlive = false;
            return;
        }
        //add the spell to the game grid
        game.cell[this.row][this.col].addObject(this);
    }

    /** run() controls animation */
    public void run(){
        while(isAlive){
            for(int i = 0; i < 10; i++){
                checkCollision();
                try{
                    Thread.sleep(DELAY);
                }catch(Exception e){}
            }
            oneTimeStep();       
        }
        //kill the spell image
        game.cell[row][col].removeObject(this);
    }

    /** oneTimeStep() moves the spell */
    private void oneTimeStep(){
        //move
        game.cell[row][col].removeObject(this); //clear old spell location
        if(direction == 1){row--;} //move up   
        else if(direction == 2){row++;} //move down   
        else if(direction == 3){col--;} //move left   
        else if(direction == 4){col++;} //move right
        
        //check for a collision with the player or monster
        checkCollision();
        
        //check if the spell has hit an obstacle --> kill it, else --> keep moving the spell
        if(!game.cell[row][col].getIsPassable()){
            isAlive = false;
        }else{
            game.cell[row][col].addObject(this); //display spell in new location
        }
    }
    
    /** checkCollision() checks for collisions with monsters */
    private void checkCollision(){
        //check if the spell has hit a monster
        for(int i = 0; i < game.monsterList.size(); i++){
            if((row == game.monsterList.get(i).getRow()) && (col == game.monsterList.get(i).getCol())){
                game.monsterList.get(i).recieveSpell(spellType, direction);
                //kill the current thread
                isAlive = false;
            }
        }

        //check for collision with character
    }

}
