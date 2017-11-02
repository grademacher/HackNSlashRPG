
/**
 * Write a description of class interfaceTimer here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class interfaceTimer implements Runnable
{
    // instance variables
    private HackNSlashMain game;

    public interfaceTimer(HackNSlashMain game){
        //save passed data
        this.game = game;
    }

    public void run(){
        //wait half a fifth of a second
        while(!game.getGameOver()){
            try{
                Thread.sleep(200);
            }catch(Exception e){}
            //remove moveBlock
            game.updateInterface();
        }
    }
}
