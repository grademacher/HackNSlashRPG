import acm.graphics.*;
import acm.program.*;
import acm.util.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

/**
 * Write a description of interface Creature here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public interface Creature
{
    /**
     * The Creature interface allows both an instance of the player and the creature to be passed to a method
     * 
     * @param  y    a sample parameter for a method
     * @return        the result produced by sampleMethod 
     */
    
    public void setRow(int row);
    
    public void setCol(int col);
    
    public int getRow();
    
    public int getCol();
    
    public void setDirection(int direction);
    
    public int getCharType();
    
    public void setImage();
    
    public void removeDamage();
    
    public void removeHealthEffect();
    
    public void setActionBlocker(boolean actionBlocker);
    
    public void setSpellReady(boolean ready);
    
    public void setMoveBlock(boolean moveBlock);
    
    public void removeSpiderWeb();
}
