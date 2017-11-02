import acm.graphics.*;
import acm.program.*;
import acm.util.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

/**
 * Write a description of class Monster here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Monster extends GCompound implements Runnable, Creature
{
    //CHARACTER IMAGES

    private GImage skeletonFront = new GImage("skeletonFront.png");
    private GImage skeletonBack = new GImage("skeletonBack.png");
    private GImage skeletonLeft = new GImage("skeletonLeft.png");
    private GImage skeletonRight = new GImage("skeletonRight.png");

    private GImage mummyFront = new GImage("mummyFront.png");
    private GImage mummyBack = new GImage("mummyBack.png");
    private GImage mummyLeft = new GImage("mummyLeft.png");
    private GImage mummyRight = new GImage("mummyRight.png");

    private GImage spiderFront = new GImage("spiderFront.png");
    private GImage spiderBack = new GImage("spiderBack.png");
    private GImage spiderLeft = new GImage("spiderLeft.png");
    private GImage spiderRight = new GImage("spiderRight.png");

    private GImage goblinFront = new GImage("goblinFront.png");
    private GImage goblinBack = new GImage("goblinBack.png");
    private GImage goblinLeft = new GImage("goblinLeft.png");
    private GImage goblinRight = new GImage("goblinRight.png");

    private GImage bossFront = new GImage("bossFront.png");
    private GImage bossBack = new GImage("bossBack.png");
    private GImage bossLeft = new GImage("bossLeft.png");
    private GImage bossRight = new GImage("bossRight.png");

    private GImage healthUp = new GImage("healthUp.png");
    private GImage hitSplat = new GImage("hitSplat.png");
    private GImage spiderWeb = new GImage("spiderWeb.png");
    private GLabel damageLabel;

    //constants
    private static final int DELAY = 600;
    private static final int SIZE = 32;

    // instance variables
    private HackNSlashMain game;
    private RandomGenerator rand = RandomGenerator.getInstance();
    private int charType; //4 = goblin, 5 = spider, 6 = mummy, 3 = skeleton, 7 = boss
    private int direction = 2;//1 - up, 2 - down, 3 - left, 4 - right 
    private boolean isAlive = true;
    private int row;
    private int col;
    private boolean isFrozen = false;
    private boolean onFire = false;
    private boolean isCursed = false;
    private boolean actionBlocker = false; //whether or not the monster is currently performing an action
    private int health = 100;
    private int initHealth = health;
    private int baseDef;
    private int defense;
    private int attackStr;
    private int attackTurnCount = 0; 

    private boolean spellReady = true; //whether or not the spells have completed cooldown
    private boolean moveBlock = false; //wether or not the monster can move

    /** Monster() constructor for the monster class */
    public Monster(int row, int col, int charType, HackNSlashMain game){
        this.row =row;
        this.col = col;
        this.charType = charType;
        this.game = game;

        generateStats();

        game.addMonster(this);
        setImage();
    }

    /** run() controls animation */
    public void run(){
        while(isAlive){
            oneTimeStep();
            try{
                Thread.sleep(DELAY);
            }catch(Exception e){}
            if(isFrozen){
                try{
                    Thread.sleep(1500);
                }catch(Exception e){}
                isFrozen = false;
            }
        }

        if(health <= 0){//check to make sure the player actually killed the monster
            die();
        }else{//the floor was wiped
            //remove from monster list
            game.removeMonster(this);
            //remove from the game board
            game.cell[row][col].clear(this,this);
        }

    }

    /** oneTimeStep() generates the shortest path and moves along it, attacks the player if within range */
    private void oneTimeStep(){
        if(health <= 0){
            isAlive = false;
        }
        //if the player is within 10 moves of the monster
        if((Math.abs(row-game.player.getRow()) + Math.abs(col-game.player.getCol())) < 11 &&
        !((Math.abs(row-game.player.getRow()) + Math.abs(col-game.player.getCol())) <2)){
            //pathfind
            AStarMain shortestPath = new AStarMain(row, col, game.player.getRow(), game.player.getCol(), game);
            if(shortestPath.getSize() > 10){return;} //if the path length is greater than 10, dont move
            //move
            if(shortestPath.getMove().toString().equals("U")){
                game.moveUp(row, col, charType, this, this);
            }else if(shortestPath.getMove().toString().equals("D")){
                game.moveDown(row, col, charType, this, this);
            }else if(shortestPath.getMove().toString().equals("L")){
                game.moveLeft(row, col, charType, this, this);
            }else if(shortestPath.getMove().toString().equals("R")){
                game.moveRight(row, col, charType, this, this);
            }else{ //a path was not found

            }
        }else if((Math.abs(row-game.player.getRow()) + Math.abs(col-game.player.getCol())) == 1){

            //if the monster is already facing the player, do nothing
            if((direction == 1 && game.player.getDirection() == 2) || 
            (direction == 2 && game.player.getDirection() == 1) || 
            (direction == 3 && game.player.getDirection() == 4) || 
            (direction == 4 && game.player.getDirection() == 3)){}
            else{
                //turn and face player
                if(row < game.player.getRow()){
                    game.faceDown(row, col, charType, this, this);
                }else if(row > game.player.getRow()){
                    game.faceUp(row, col, charType, this, this);
                }else if(col > game.player.getCol()){
                    game.faceLeft(row, col, charType, this, this);
                }else if(col < game.player.getCol()){
                    game.faceRight(row, col, charType, this, this);
                }
            }
            //else

            //attack player

            if(attackTurnCount == 2){
                attack();
                attackTurnCount = 0;
            }
            attackTurnCount++;
        }
        //fire damage
        int turnsOnFire = 0;
        if(onFire){
            //apply fire damage
            //only let the monster be on fire for 3 turns
            turnsOnFire++;
            if(turnsOnFire == 2){
                onFire = false;
                turnsOnFire = 0;
            }
        }

        //if the monster is cursed, the curse disapears after 3 turns
        int turnsCursed = 0;
        if(isCursed){
            turnsCursed++;
            if(turnsCursed == 2){
                isCursed = false;
            }
        }
    }

    /** setImage() controlls which image should be displayed */
    public void setImage(){
        removeAll(); //clear the monster GCompound
        if(direction==1){//up
            if(charType==3){//skeleton
                add(skeletonBack);
            }else if(charType==4){//goblin
                add(goblinBack);
            }else if(charType==5){//spider
                add(spiderBack);
            }else if(charType==6){//mummy
                add(mummyBack);
            }else if(charType==7){//boss
                add(bossBack);
            }
        }else if(direction==2){//down
            if(charType==3){//skeleton
                add(skeletonFront);
            }else if(charType==4){//goblin
                add(goblinFront);
            }else if(charType==5){//spider
                add(spiderFront);
            }else if(charType==6){//mummy
                add(mummyFront);
            }else if(charType==7){//boss
                add(bossFront);
            }
        }else if(direction==3){//left
            if(charType==3){//skeleton
                add(skeletonLeft);
            }else if(charType==4){//goblin
                add(goblinLeft);
            }else if(charType==5){//spider
                add(spiderLeft);
            }else if(charType==6){//mummy
                add(mummyLeft);
            }else if(charType==7){//boss
                add(bossLeft);
            }
        }else if(direction==4){//right
            if(charType==3){//skeleton
                add(skeletonRight);
            }else if(charType==4){//goblin
                add(goblinRight);
            }else if(charType==5){//spider
                add(spiderRight);
            }else if(charType==6){//mummy
                add(mummyRight);
            }else if(charType==7){//boss
                add(bossRight);
            }
        }
    }

    /** attack() preforms the damage calculations while attacking the player */
    private void attack(){
        //calculate damage (TAKING THE CURSE INTO ACCOUNT)
        double damage = 0;
        int accuracy = rand.nextInt(10);
        if(accuracy < 3){ //ya done wiffed it
            damage = 0;
        }else if(accuracy < 9){//regular hit
            damage = rand.nextInt(6) + 1 + attackStr;
        }else{//critical hit
            damage = rand.nextInt(10) + 1 + attackStr;
        }

        //lower damage if cursed
        if(isCursed){
            damage = (damage/1.5);
        }
        game.player.subtractHealth((int)damage);

        //calculate chance for special effect
        int effectChance = rand.nextInt(5);
        if(effectChance == 0){
            if(charType==3){//skeleton
                //not sure
            }else if(charType==4){//goblin
                //not sure
            }else if(charType==5){//spider
                //freeze player
                game.player.setMoveBlock(true);
                game.player.addSpiderWeb();
                effectTimer ETimer = new effectTimer(game.player, 4, game);
                new Thread(ETimer).start();
            }else if(charType==6){//mummy
                //not sure
            }else if(charType==7){//boss
                //summon skeletons and mummies
            }
        }

    }

    /** recieveSpell() handles a spell collision */
    public void recieveSpell(int spellType, int direction){
        if(spellType == 1){//ice
            //freeze the monster
            isFrozen = true;
            //chance to apply damage
            if(rand.nextInt(2) == 1){
                mageDamage(1);
            }
        }else if(spellType == 2){//fire
            //apply instant damage
            mageDamage(3);
            //chance to set the monster on fire
            if(rand.nextInt(4) == 0){
                //set the monster on fire
                onFire = true;
            }

        }else if(spellType == 3){//wind
            //move the monster back X spaces
            if(direction == 1){
                //move up 3 spaces
                for(int i = 0; i < 3; i++){game.moveUp(row,col,charType,this, this);}
            }else if(direction == 2){
                //move back 3 spaces
                for(int i = 0; i < 3; i++){game.moveDown(row,col,charType,this, this);}
            }else if(direction == 3){
                //move left 3 spaces
                for(int i = 0; i < 3; i++){game.moveLeft(row,col,charType,this, this);}
            }else if(direction == 4){
                //move righ 3 spaces
                for(int i = 0; i < 3; i++){game.moveRight(row,col,charType,this, this);}
            }
        }else if(spellType == 4){//curse
            //weaken the monster
            isCursed = true;
        }
    }

    /** die() handles the death of a monster */
    private void die(){
        //remove from monster list
        game.removeMonster(this);
        //remove from the game board
        game.cell[row][col].clear(this,this);
        //generate coins depending on monster stats
        Collectible coin = new Collectible(true, false, rand.nextInt(initHealth/3), 0, row, col, game);
        game.cell[row][col].addObject(coin);
        game.addLoot(coin);
        //give the player exp
        game.player.addExp(initHealth/3);
        
        if(charType == 7){//boss died
            for(int i = 0; i < 25; i++){
                for(int j = 0; j < 25; j++){
                    if(game.cell[i][j].isDoor()){
                        game.cell[i][j].openDoor();
                    }
                }
            }
        }
    }

    /** mageDamage() calculates damage from a spell */
    private void mageDamage(int spellStr){ //strength of the mage attack
        int baseMageStr = game.player.getMagicBaseStr(); //base magic strength
        int weaponMageStr = game.player.getMagicWeaponStr(); //strength of magic weapon

        //calculate the base damage
        double damage;
        int accuracy = rand.nextInt(10);
        if(accuracy < 3){ //ya done wiffed it
            damage = 0;
        }else if(accuracy < 9){//regular hit
            damage = rand.nextInt(6) + 1 + baseMageStr + weaponMageStr;
        }else{//critical hit
            damage = rand.nextInt(10) + 1 + baseMageStr + weaponMageStr;
        }

        if(spellStr==1){//weak attack
            damage = damage/3;
        }else if(spellStr==2){//medium attack
            damage = damage*.6;
        }//strong attack is normal damage

        //generate a base defense value
        if(charType==3){//skeleton
            defense = rand.nextInt(6) + 4;
        }else if(charType==4){//goblin
            defense = rand.nextInt(6) + 2;
        }else if(charType==5){//spider
            defense = rand.nextInt(6);
        }else if(charType==6){//mummy
            defense = rand.nextInt(6) + 6;
        }else if(charType==7){//boss
            defense = rand.nextInt(6) + 10;
        }

        double realDamage = damage - (rand.nextInt(defense) + baseDef);
        if(isCursed){//a curse means damage is multiplied by 1.5
            realDamage = (int)(realDamage*1.5);
        }

        if(realDamage>0){//if damage is negative, set it to zero
            this.health -= realDamage;
        }else{
            realDamage = 0;
        }
        //animate
        //show the hitsplat
        this.showDamage((int)realDamage);

        //get rid of the hitsplat
        effectTimer timer = new effectTimer(this, 1, game);
        new Thread(timer).start();
    }

    /** meleeDamage() calculates damage from a melee attack */
    public void meleeDamage(int damageBase){
        if(charType==3){//skeleton
            defense = rand.nextInt(6) + 4;
        }else if(charType==4){//goblin
            defense = rand.nextInt(6) + 2;
        }else if(charType==5){//spider
            defense = rand.nextInt(6);
        }else if(charType==6){//mummy
            defense = rand.nextInt(6) + 6;
        }else if(charType==7){//boss
            defense = rand.nextInt(6) + 10;
        }
        double realDamage = damageBase - (rand.nextInt(defense) + baseDef);
        if(isCursed){//a curse means damage is multiplied by 1.5
            realDamage = (int)(realDamage*1.5);
        }

        if(realDamage>0){//if damage is negative, set it to zero
            this.health -= realDamage;
        }else{
            realDamage = 0;
        }
        //animate
        //show the hitsplat
        this.showDamage((int)realDamage);

        //get rid of the hitsplat
        effectTimer timer = new effectTimer(this, 1, game);
        new Thread(timer).start();

    }

    /** generateStats() generates the monsters stats randomly based on initial data*/
    private void generateStats(){
        if(charType==3){//skeleton
            baseDef = rand.nextInt(5) + 4;
            attackStr = 9;
            initHealth = 50;
        }else if(charType==4){//goblin
            baseDef = rand.nextInt(4) + 2;
            attackStr = 6;
            initHealth = 30;
        }else if(charType==5){//spider
            baseDef = rand.nextInt(3);
            attackStr = 3;
            initHealth = 15;
        }else if(charType==6){//mummy
            baseDef = rand.nextInt(6) + 6;
            attackStr = 13;
            initHealth = 75;
        }else if(charType==7){//boss
            baseDef = rand.nextInt(10) + 10;
            attackStr = 16;
            initHealth = 300;
        }
        health = initHealth;
    }

    //helper methods for small tasks and data within the class
    public void setSpellReady(boolean ready){
        spellReady = ready;
    }

    public void setMoveBlock(boolean moveBlock){
        this.moveBlock = moveBlock;
    }

    public boolean getSpellReady(){
        return spellReady;
    }

    public void setActionBlocker(boolean actionBlocker){
        this.actionBlocker = actionBlocker;
    }

    public boolean getActionBlocker(){
        return actionBlocker;
    }

    public void setHealth(){
        health = 0;
    }

    public int getRow(){
        return row;
    }

    public int getCol(){
        return col;
    }

    public int getCharType(){
        return charType;
    }

    public void setRow(int row){
        this.row = row;
    }

    public void setCol(int col){
        this.col = col;
    }

    public void setDirection(int direction){
        this.direction = direction;
    }

    public void addHealth(int health){
        this.health += health;
        add(healthUp);
    }

    public void removeHealthEffect(){
        remove(healthUp);
    }

    public void showDamage(int damage){
        add(hitSplat);
        damageLabel = new GLabel("" + damage);
        add(damageLabel);
        damageLabel.setColor(Color.WHITE);
        damageLabel.setFont(new Font("Sanserif", Font.BOLD, 10));
        damageLabel.setLocation(getWidth()/2-damageLabel.getWidth()/2, getHeight()/2 - 3);
    }

    public void removeDamage(){
        remove(hitSplat);
        remove(damageLabel);
    }

    public void addSpiderWeb(){
        add(spiderWeb);
    }

    public void removeSpiderWeb(){
        remove(spiderWeb);
    }

    public void setAlive(boolean isAlive){
        this.isAlive = isAlive;
    }
}
