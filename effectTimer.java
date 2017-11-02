import acm.graphics.*;
import acm.program.*;
import acm.util.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

/**
 * Write a description of class healthTimer here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class effectTimer implements Runnable
{
    // instance variables
    private HackNSlashMain game;
    private Creature creature;
    private int effectType;

    public effectTimer(Creature creature, int effectType, HackNSlashMain game){
        //save passed data\
        this.game = game;
        this.creature = creature;
        this.effectType = effectType;
    }

    public void run(){
        //wait half a second

        //remove the health effect
        if(effectType == 1){//damage effect
            try{
                Thread.sleep(500);
            }catch(Exception e){}
            creature.removeDamage();
        }else if(effectType == 2){//add health effect
            try{
                Thread.sleep(500);
            }catch(Exception e){}
            creature.removeHealthEffect();
        }else if(effectType == 3){//action blocker
            try{
                Thread.sleep(500);
            }catch(Exception e){}
            creature.setActionBlocker(false);
        }else if(effectType == 4){ //spider web
            try{
                Thread.sleep(1500);
            }catch(Exception e){}
            creature.setMoveBlock(false);
            creature.removeSpiderWeb();
        }else if(effectType == 5){//spell cooldown
            try{
                Thread.sleep(1000);
            }catch(Exception e){}
            creature.setSpellReady(true);
        }else if(effectType == 6){//movement cooldown
            try{
                Thread.sleep(200);
            }catch(Exception e){}
            creature.setMoveBlock(false);
        }
    }
}
