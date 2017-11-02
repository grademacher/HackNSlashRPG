
/**
 * Write a description of class spawnTimer here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class spawnTimer implements Runnable
{
    // instance variables
    private HackNSlashMain game;
    private int row;
    private int col;
    private int currentFloor;

    public spawnTimer(int row, int col, int currentFloor, HackNSlashMain game){
        //save passed data
        this.game = game;
        this.row = row;
        this.col = col;
        this.currentFloor = currentFloor;
    }

    public void run(){
        //wait half a second
        try{
            Thread.sleep(500);
        }catch(Exception e){}

        //spawn monster
        game.spawnMonster(row,col,currentFloor);
    }
}
