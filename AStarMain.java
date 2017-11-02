import acm.util.*;
import java.awt.*;
import java.util.*;

/**
 * searches to find the shortest path between two points
 * 
 * @author Garrett Rademacher
 * @version 1.0
 */
public class AStarMain
{
    // instance variables
    private ArrayList<AStarCell> open = new ArrayList<AStarCell>(); //list of open cells
    private ArrayList<AStarCell> closed = new ArrayList<AStarCell>();//list of closed cells
    public ArrayList<Character> path = new ArrayList<Character>(); //will hold the path once found
    public static AStarCell[][] grid = new AStarCell[25][25]; //grid of cells
    private int startRow, startCol; //staring point for the search
    private int endRow, endCol; //destination
    private boolean pathFound = false;
    private AStarCell current; //current cell
    private HackNSlashMain game; //instance of the game from which the class is being created
    
    /** each time the constructor is called, the shortest path is found and returned */
    public AStarMain(int startRow, int startCol, int endRow, int endCol, HackNSlashMain game){
        //save passed data
        pathFound = false;
        this.startRow = startRow;
        this.startCol = startCol;
        this.endRow = endRow;
        this.endCol = endCol;
        this.game = game;
        
        //populate grid
        populateGrid();
        
        //start main pathfinding method
        open.add(0, grid[startRow][startCol]); //add the starting cell to the open list
        
        //use a while loop to search for a path
        while(!pathFound && open.size()>0){
            current = open.get(0); //set a new open, at the beginning of the program this is the initial cell,
            //however later it just serves as a starting point for the while loop comparison
            
            //loop through the open array list, find the cell with the lowest cost
            for(int i = 0; i < open.size(); i++){
                if((open.get(i).getFCost() < current.getFCost()) && open.get(i).getFCost() != 0){
                    current = open.get(i);
                }
            }
            
            //path has been found
            if(current.getCol() == endCol && current.getRow() == endRow){
                constructPath();
                return;
            }
            
            //remove the found cell from the open list
            open.remove(current);
            //add the cell to the closed list
            closed.add(current);
            
            //consider each vertical and horizontal neighbor of the current cell
            //check if in bounds of array of cells
            if(checkInBounds(current.getRow()-1, current.getCol()) && !pathFound){
                consider(grid[current.getRow()-1][current.getCol()]);
            }
            if(checkInBounds(current.getRow()+1, current.getCol()) && !pathFound){
                consider(grid[current.getRow()+1][current.getCol()]);
            }
            if(checkInBounds(current.getRow(), current.getCol()-1) && !pathFound){
                consider(grid[current.getRow()][current.getCol()-1]);
            }
            if(checkInBounds(current.getRow(), current.getCol()+1) && !pathFound){
                consider(grid[current.getRow()][current.getCol()+1]);
            }
        }
        
        //if a path was not found, fill the path list with do nothing moves
        if(!pathFound){
             Character character = new Character('X');
             path.add(0,character);
        }
    }
    
    /** consider(AStarCell)computes esitmated path length, and decides if it is now the shortest path */
    private void consider(AStarCell passedCell){
        //if the neighbor cell is not passable or is already on the closed list, skip it
        if(!passedCell.getIsPassable() || closed.contains(passedCell)){
            return;
        }
        
        //calculate the g cost (add one to the g cost of the current cell)
        int holdingGCost = current.getGCost() + 1;
        
        //If a new has been discovered, add it to the open list
        if(!(open.contains(passedCell))){
            //add passedCell to open
            open.add(passedCell);
        }else if(holdingGCost >= passedCell.getGCost()){
            return;
        }
        
        //calculate the h cost of the cell
        int heuristic = (Math.abs(endRow-passedCell.getRow()) + Math.abs(endCol-passedCell.getCol()));
        passedCell.setHCost(heuristic);
        //set the gCost
        passedCell.setGCost(holdingGCost);
        //set the f cost to gCost + hCost
        passedCell.calcFCost();
        //set the parent cell of passedCell to current cell
        passedCell.setParent(current);
    }
    
    /** checkInBounds(int, int) checks to make sure the passed values are in the array */
    private boolean checkInBounds(int row, int col){
        if(row > -1 && row < 25 && col > -1 && col < 25){return true;} //cell is in bounds
        else{return false;}//cell is out of bounds
    }
    
    /** constructPath() traces the parent cells back to the start to construct the path */
    private void constructPath(){
        pathFound = true;
        //once a path has been found, trace it backwards adding each step to the path ArrayList
        //start at the end cell,
        AStarCell currentCell = grid[endRow][endCol];
        AStarCell parentCell;
        boolean finalCellFound = false;
        while(!finalCellFound){
            parentCell = currentCell.getParent(); //get the parent cell
            if((parentCell.getRow()) < (currentCell.getRow())){
                //add move up
                Character character = new Character('D');
                path.add(0,character);
            }else if((parentCell.getRow()) > (currentCell.getRow())){
                //add move down
               Character character = new Character('U');
                path.add(0,character);
            }else if((parentCell.getCol()) > (currentCell.getCol())){
                //add move left
                Character character = new Character('L');
                path.add(0,character);
            }else if((parentCell.getCol()) < (currentCell.getCol())){
                //add move right
                Character character = new Character('R');
                path.add(0,character);
            }
            if((parentCell.getRow() == startRow) && (parentCell.getCol() == startCol)){
                //path has been completed
                finalCellFound = true;
            }
            currentCell = parentCell;//update the current cell so the path traces backwards
        }
    }
    
    /** getMove() returns the first move the monster should take in the path */
    public Character getMove(){
        int pathSize = path.size();
        return path.get(0);
    }
    
    /** getSize() returns the length of the path */
    public int getSize(){
        return path.size();
    }
    
    /** populateGrid() sets up the grid with which cells are passable */
    private void populateGrid(){
        for(int i = 0; i < 25; i++){
            for(int j = 0; j < 25; j++){
                AStarCell cell = new AStarCell(i,j,game.cell[i][j].getIsPassable());
                grid[i][j] = cell;
                //cell.setPassable(game.cell[i][j].getIsPassable());
            }
        }
    }
}
