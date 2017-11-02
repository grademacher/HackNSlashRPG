
/**
 * Write a description of class AStarCell here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class AStarCell
{   
    // instance variables
    private int hCost = 0;//distance to the ending point
    private int gCost = 0;//distance from the starting point
    private int fCost = 0; //G+H, this is basically the estimated distance of the path that travelling through this cell produces
    private int row, col; //location in grid of cells
    private AStarCell parent; //parent cell
    private boolean isPassable = true;
    
    
    AStarCell(int row, int col, boolean isPassable){
        this.row = row;
        this.col = col;
        this.isPassable = isPassable;
    }
    
    public void setGCost(int gCost){
        this.gCost = gCost;
    }
    
    public void setHCost(int hCost){
        this.hCost = hCost;
    }
    
    public void calcFCost(){
        fCost = gCost + hCost;
    }
    
    public void setParent(AStarCell parentCell){
        this.parent = parentCell;
    }
    
    public void setPassable(boolean isPassable){
        this.isPassable = isPassable;
    }
    
    public int getGCost(){
        return this.gCost;
    }
    
    public int getFCost(){
        return fCost;
    }
    
    public boolean getIsPassable(){
        return isPassable;
    }
    
    public int getCol(){
        return col;
    }
    
    public int getRow(){
        return row;
    }
    
    public AStarCell getParent(){
        return this.parent;
    }
    
}
