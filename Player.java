import acm.graphics.*;
import acm.program.*;
import acm.util.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

/**
 * Write a description of class Character here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Player extends GCompound implements Creature
{
    //CHARACTER IMAGES
    private GImage knightFront = new GImage("knightFront.png");
    private GImage knightBack = new GImage("knightBack.png");
    private GImage knightLeft = new GImage("knightLeft.png");
    private GImage knightRight = new GImage("knightRight.png");

    private GImage mageFront = new GImage("mageFront.png");
    private GImage mageBack = new GImage("mageBack.png");
    private GImage mageLeft = new GImage("mageLeft.png");
    private GImage mageRight = new GImage("mageRight.png");
    
    private GImage shield1 = new GImage("shield1.png");
    private GImage shield2 = new GImage("shield2.png");
    private GImage shield3 = new GImage("shield3.png");
    
    private GImage healthUp = new GImage("healthUp.png");
    private GImage hitSplat = new GImage("hitSplat.png");
    private GImage spiderWeb = new GImage("spiderWeb.png");
    private GLabel damageLabel;
    
    //constants
    private static final int SIZE = 32;
    
    // instance variables
    private int baseStr = 1; //melee strength
    private int weaponStr = 1; //melee strength from weapon
    private int defense = 0; //defense stat
    private int baseDef = 1; //base dfense stat
    private int mageAttack = 1; //mage strength from weapon
    private int mageBase = 1; //base mage str
    private double health = 20;
    private double initHealth = 20;
    private double currentExp = 1;
    private double nextExpLevel = 20;
    private int lvl = 1;
    private int shieldType = 2;
    private boolean isBlocking = false;
    private boolean actionBlocker = false; //whether or not th player is currently performing an action
    private boolean spellReady = true; //whether or not the spells have completed cooldown
    private boolean moveBlock = false; //whether or not the player can move
    
    private int charType; //1 = knight, 2 = mage
    private int row;
    private int col;
    private int direction = 2; //1 - up, 2 - down, 3 - left, 4 - right 
    private HackNSlashMain game;
    private int coins = 0;
    private RandomGenerator rand = RandomGenerator.getInstance();

    public Player(int charType, HackNSlashMain game){
        //save passed data
        this.charType = charType;
        this.game = game;
        //this.row = row;
        //this.col = col;
        //create character images
        setImage();
        //create character stats

    }
    
    /** setImage() chooses the currect image to display in the GCompound */
    public void setImage(){
        //remove character image (leave any other image)
        removeAll();
        remove(knightFront);
        remove(knightBack);
        remove(knightLeft);
        remove(knightRight);
        remove(mageFront);
        remove(mageBack);
        remove(mageLeft);
        remove(mageRight);
        //removeDamage();
        
        
        
        //add the new character image
        if(direction==1){//up
            if(charType==1){//knight
                add(knightBack);
                knightBack.sendToBack();
            }else if(charType==2){//mage
                add(mageBack);
                mageBack.sendToBack();
            }
        }else if(direction==2){//down
            if(charType==1){//knight
                add(knightFront);
                knightFront.sendToBack();
            }else if(charType==2){//mage
                add(mageFront);
                mageFront.sendToBack();
            }
        }else if(direction==3){//left
            if(charType==1){//knight
                add(knightLeft);
                knightLeft.sendToBack();
            }else if(charType==2){//mage
                add(mageLeft);
                mageLeft.sendToBack();
            }
        }else if(direction==4){//right
            if(charType==1){//knight
                add(knightRight);
                knightRight.sendToBack();
            }else if(charType==2){//mage
                add(mageRight);
                mageRight.sendToBack();
            }
        }
    }

    /** attack() calculates base damage for melee damage when attacking a monster */
    public void attack(Monster monster){
        double damage = 0;
        //calculate damage
        
        int accuracy = rand.nextInt(10);
        if(accuracy < 3){ //ya done wiffed it
            damage = 0;
        }else if(accuracy < 9){//regular hit
            damage = rand.nextInt(6) + 1 + baseStr + weaponStr;
        }else{//critical hit
            damage = rand.nextInt(10) + 1 + baseStr + weaponStr;
        }
        
        if(charType == 2){//handicap the mage's melee attack
            damage = damage/2;
        }
        monster.meleeDamage((int)damage);
        //calculate chance for effect? not sure if player will have effects
        
        //create an action blocker
        actionBlocker = true;
        effectTimer ATimer = new effectTimer(this, 3, game);
        new Thread(ATimer).start();
    }
    
    /** subtractHealth() calculates health lost when attacked based on monster base damage */
    public void subtractHealth(int damage){
        //calculate real damage
        defense = 0;
        defense = rand.nextInt(6) + baseDef;
        double realDamage = damage-defense;
        if(isBlocking){
            if(shieldType==1){
                realDamage = realDamage*.8;
            }else if(shieldType==2){
                realDamage = realDamage*.65;
            }else if(shieldType==3){
                realDamage = realDamage*.5;
            }
        }
        if(charType == 1){//knight's armor soaks damage
            realDamage = realDamage*.65;
        }
        if(realDamage>0){//if damage is negative, set it to zero
            //subtract damage from health
            this.health -= (int)realDamage;
        }else{
            realDamage = 0;
        }
        //animate
        //show the hitsplat
        this.showDamage((int)realDamage);
        
        //get rid of the hitsplat
        effectTimer timer = new effectTimer(this, 1, game);
        new Thread(timer).start();
        
        //check if game over - player is dead
        if(health <= 0){
            game.gameOver(1);
        }
    }
    
    
    //helper methods for the player class
    public void setMoveBlock(boolean moveBlock){
        this.moveBlock = moveBlock;
    }
    
    public boolean getMoveBlock(){
        return moveBlock;
    }
    
    public void setSpellReady(boolean ready){
        spellReady = ready;
    }
    
    public boolean getSpellReady(){
        return spellReady;
    }
    
    public double getHealth(){
        return health;
    }
    
    public double getInitHealth(){
        return initHealth;
    }
    
    public int getMeleeWeaponStr(){
        return weaponStr;
    }
    
    public int getMeleeBaseStr(){
        return baseStr;
    }
    
    public void setMeleeWeaponStr(int mod){
        weaponStr += mod;
    }
    
    public void setMagicWeaponStr(int mod){
        mageAttack += mod;
    }
    
    public int getMagicWeaponStr(){
        return mageAttack;
    }
    
    public int getMagicBaseStr(){
        return mageBase;
    }
    
    public int getLvl(){
        return lvl;
    }
    
    public double getCurrentExp(){
        return currentExp;
    }
    
    public double getNextExpLevel(){
        return nextExpLevel;
    }
    
    public int getCoins(){
        return coins;
    }
    
    public void payCoins(int coins){
        this.coins -= coins;
    }
    
    public boolean isBlocking(){
        return isBlocking;
    }
    
    public void setActionBlocker(boolean actionBlocker){
        this.actionBlocker = actionBlocker;
    }
    
    public boolean getActionBlocker(){
        return actionBlocker;
    }
    
    public void setIsBlocking(boolean isBlocking){
        this.isBlocking = isBlocking;
    }
    
    public int getRow(){
        return row;
    }

    public int getCol(){
        return col;
    }

    public void setRow(int row){
        this.row = row;
    }

    public void setCol(int col){
        this.col = col;
    }

    public int getCharType(){
        return charType;
    }

    public void setCharType(int charType){
        this.charType = charType;
    }

    public int getDirection(){
        return direction;
    }

    public void setDirection(int direction){
        this.direction = direction;
    }
    
    public void addCoins(int coins){
        this.coins += coins;
    }
    
    public void addHealth(int health){
        this.health += health;
        if(this.health >= initHealth){
            this.health = initHealth;
        }
        add(healthUp);
        effectTimer timer = new effectTimer(this, 2, game);
        new Thread(timer).start();
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
    
    public void showShield(){
        if(shieldType==1){
            add(shield1);
        }else if(shieldType==2){
            add(shield2);
        }else if(shieldType==3){
            add(shield3);
        }
    }
    
    public void hideShield(){
        if(shieldType==1){
            remove(shield1);
        }else if(shieldType==2){
            remove(shield2);
        }else if(shieldType==3){
            remove(shield3);
        }
    }
    
    public void addExp(int exp){
        currentExp += exp;
        while(currentExp> nextExpLevel){
            currentExp -= nextExpLevel;
            nextExpLevel = (int)(nextExpLevel*2);
            lvl++;
            
            //increase player stats
            mageBase += 1;
            baseDef += 1;
            baseStr += 1;
            initHealth += 5;
            //health = initHealth;
        }
    }
    
    public void addSpiderWeb(){
        add(spiderWeb);
    }
    
    public void removeSpiderWeb(){
        remove(spiderWeb);
    }
}
