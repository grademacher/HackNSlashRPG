import acm.graphics.*;
import acm.program.*;
import acm.util.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

/**
 * Write a description of class MapCell here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class MapCell extends GCompound
{
    private static final double SIZE = 32;

    // instance variables



    //ITEM IMAGES
    private GImage hitSplat = new GImage("hitSplat.png");

    //STRUCTURE IMAGES
    private GImage floor = new GImage("floor.png");
    private GImage obstacle = new GImage("obstacle.png");
    private GImage portal = new GImage("portal.png");
    private GImage stairDown = new GImage("stairDown.png");
    private GImage stairUp = new GImage("stairUp.png");
    private GImage wall1 = new GImage("wall1.png");
    private GImage wall2 = new GImage("wall2.png");
    private GImage wall3 = new GImage("wall3.png");
    private GImage doorOpen = new GImage("doorOpen.png");
    private GImage doorLocked = new GImage("doorLocked.png");

    //DATA
    private boolean isChar = false;
    private boolean isSkeleton = false;
    private boolean isGoblin = false;
    private boolean isSpider = false;
    private boolean isMummy = false;
    private boolean isBoss = false;
    private boolean isChestClosed = false;
    private boolean isChestOpen = false;
    private boolean isWall = false;
    private boolean isObstacle = false;
    private boolean isStairUp = false;
    private boolean isStairDown = false;
    private boolean isPortal = false;
    private boolean isSecretSwitch = false;
    private boolean isDoor = false;
    private boolean isSwitch = false;
    private boolean isPassable = true;

    private RandomGenerator rand = RandomGenerator.getInstance();

    //location of the cell in the map
    private int row;
    private int col;
    private HackNSlashMain game;

    /** Constructor, creates a basic cell with nothing in it */
    public MapCell(int row, int col, HackNSlashMain game){
        this.row = row;
        this.col = col;
        this.game = game;
    }

    /** showChar() displays a passed creature in the cell */
    public void showChar(GObject Gcreature, Creature creature){
        add(Gcreature, -SIZE/2,-SIZE/2);
        isPassable = false;
    }

    /** clear(GObject, Creature) removes the passed creature from the cell */
    public void clear(GObject Gcreature, Creature creature){
        remove(Gcreature);
        isPassable = true;

        if(creature.getCharType() == 1 || creature.getCharType() == 2){
            isChar = false;
        }else if(creature.getCharType() == 3){//skeleton
            isSkeleton = false;
        }else if(creature.getCharType() == 4){//goblin
            isGoblin = false;
        }else if(creature.getCharType() == 5){//spider
            isSpider = false;
        }else if(creature.getCharType() == 6){//mummy
            isMummy = false;
        }else if(creature.getCharType() == 7){
            isBoss = false;
        }else if(creature.getCharType() == 8){

        }else{//the object calling clear() mettthod is not a creature

        }
    }

    /** clear() completely clears the cell */
    public void clear(){
        removeAll();
        isChar = false;
        isSkeleton = false;
        isGoblin = false;
        isSpider = false;
        isMummy = false;
        isBoss = false;
        isChestClosed = false;
        isChestOpen = false;
        isWall = false;
        isObstacle = false;
        isStairUp = false;
        isStairDown = false;
        isPortal = false;
        isSecretSwitch = false;
        isPassable = true;
        isDoor = false;
        isSwitch = false;
    }

    /** addToScreen() adds the map structure from map data to draw the initial room */
    public void addToScreen(char character){
        add(floor, -SIZE/2,-SIZE/2);
        if(character == 'W'){ //wall
            //decide which wall to add of the 3
            int wallVar = rand.nextInt(15);
            if(wallVar == 15){
                add(wall3, -SIZE/2, -SIZE/2);
                wall3.setVisible(true);
            }else if(wallVar < 10){
                add(wall2, -SIZE/2, -SIZE/2);
                wall2.setVisible(true);
            }else{
                add(wall1, -SIZE/2, -SIZE/2);
                wall1.setVisible(true);
            }
        }else if(character == 'U'){ //stair up
            add(stairUp, -SIZE/2, -SIZE/2);
            stairUp.setVisible(true);
            isStairUp = true;
        }else if(character == 'D'){ //stair down
            add(stairDown, -SIZE/2, -SIZE/2);
            stairDown.setVisible(true);
            isStairDown = true;
        }else if(character == 'O'){ //obstacle
            add(obstacle, -SIZE/2, -SIZE/2);
            obstacle.setVisible(true);
        }else if(character == 'R'){
            add(doorLocked, -SIZE/2, -SIZE/2);
            doorOpen.setVisible(true);
            isPassable = false;
            isDoor = true;
        }else if(character == 'X'){ //secret switch
            isSwitch = true;
        }else if(character == 'P'){ //portal
            add(portal, -SIZE/2, -SIZE/2);
            portal.setVisible(true);
        }else{
            //whatever was passed was a character and those are taken care of seperately
        }
    }
    
    /** openDoor() opens the door in the cell */
    public void openDoor(){
        remove(doorLocked);
        add(doorOpen, -SIZE/2, -SIZE/2);
        isPassable = true;
    }
    
    /** addObject() adds a non creature object to the cell */
    public void addObject(GObject object){
        add(object);
    }

    /** removeObject() removes a non creature object from the cell */
    public void removeObject(GObject object){
        remove(object);
    }

    /** colorRed() not used, was used for testing pathfinding */
    public void colorRed(){
        GRect redCover = new GRect(-SIZE/2, -SIZE/2, SIZE, SIZE);
        add(redCover);
        redCover.setColor(Color.RED);
        redCover.setFilled(true);
        redCover.setVisible(true);
    }
    
    
    //helper methods for cell data
    public boolean isDoor(){
        return isDoor;
    }
    
    public boolean isSwitch(){
        return isSwitch;
    }

    public boolean getIsChar(){
        return isChar;
    }

    public boolean getIsSkeleton(){
        return isSkeleton;
    }

    public boolean getIsGoblin(){
        return isGoblin;
    }

    public boolean getIsSpider(){
        return isSpider;    
    }

    public boolean getIsMummy(){
        return isMummy; 
    }

    public boolean getIsPassable(){
        return isPassable;
    }

    public void setIsPassable(boolean isPassable){
        this.isPassable = isPassable;
    }

    public void setIsChar(boolean isChar){
        this.isChar = isChar;
    }

    public void setIsSkeleton(boolean isSkeleton){
        this.isSkeleton = isSkeleton;
    }

    public void setIsGoblin(boolean isGoblin){
        this.isGoblin = isGoblin;
    }

    public void setIsSpider(boolean isSpider){
        this.isSpider = isSpider;
    }

    public void setIsMummy(boolean isMummy){
        this.isMummy = isMummy;
    }

    public void setIsBoss(boolean isBoss){
        this.isBoss = isBoss;
    }

    public void setIsChestClosed(boolean isChestClosed){
        this.isChestClosed = isChestClosed;
    }

    public void setIsChestOpen(boolean isChestOpen){
        this.isChestOpen = isChestOpen;
    }

    public void setIsWall(boolean isWall){
        this.isWall = isWall;
    }

    public void setIsObstacle(boolean isObstacle){
        this.isObstacle = isObstacle;
    }

    public void setIsStairUp(boolean isStairUp){
        this.isStairUp = isStairUp;
    }

    public void setIsStairDown(boolean isStairDown){
        this.isStairDown = isStairDown;
    }

    public boolean isStairDown(){
        return isStairDown;
    }
    
    public boolean isStairUp(){
        return isStairUp;
    }

    public void setIsPortal(boolean isPortal){
        this.isPortal = isPortal;
    }

    public void setIsSwitch(boolean isSecretSwitch){
        this.isSecretSwitch = isSecretSwitch;
    }
}
