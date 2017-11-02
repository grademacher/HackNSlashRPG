
/**
 * ''W''rite a description of class Map here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Map
{
    // instance variables
    private char[][] currentFloor = new char[25][25];
    
    public Map (int floorNum){
        for(int i = 0; i < 25; i++){
            for(int j = 0; j < 25; j++){
                currentFloor[i][j] = masterFloor[floorNum][i][j];
            }
        }
    }
    
    public char getChar(int row, int col){
        return currentFloor[row][col];
    }
    
    private char masterFloor[][][]= new char[][][]{
        {
            //'W' = wall, 'U' = stairUp, 'D' = stairDown, 'O' = obstacle
            //'C' = chest, P = portal, G = goblin, 'S' = spider
            //M = mummy, K = skeleton, X = secret switch, '-' = empty
    
            //0
            {'W','W','W','W','W','W','W','W','W','W','W','W','W','W','W','W','W','W','W','W','W','W','W','W','W'},
            {'W','C','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','D','W'},
            {'W','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','W'},
            {'W','O','O','O','O','O','O','O','O','O','O','-','S','-','O','O','O','O','O','O','O','O','O','O','W'},
            {'W','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','W'},
            {'W','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','W'},
            {'W','-','-','-','-','-','-','-','-','-','-','-','O','-','-','-','-','-','-','-','-','-','-','-','W'},
            {'W','-','-','-','-','-','-','-','-','-','-','-','O','-','-','-','-','-','-','-','-','-','-','-','W'},
            {'W','-','-','-','-','-','-','-','-','-','-','-','O','-','-','-','-','-','-','-','-','-','-','-','W'},
            {'W','-','-','-','-','-','S','-','-','-','-','-','O','-','-','-','-','-','S','-','-','-','-','-','W'},
            {'W','-','-','-','-','-','-','-','-','-','-','-','O','-','-','-','-','-','-','-','-','-','-','-','W'},
            {'W','-','-','-','-','-','-','-','-','-','-','-','O','-','-','-','-','-','-','-','-','-','-','-','W'},
            {'W','-','-','-','-','-','-','-','-','-','-','-','O','-','-','-','-','-','-','-','-','-','-','-','W'},
            {'W','-','-','-','-','-','-','-','-','-','-','-','O','-','-','-','-','-','-','-','-','-','-','-','W'},
            {'W','-','-','-','-','-','-','-','-','-','-','C','O','C','-','-','-','-','-','-','-','-','-','-','W'},
            {'W','-','-','-','O','O','O','O','O','O','O','O','O','O','O','O','O','O','O','O','O','-','-','-','W'},
            {'W','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','W'},
            {'W','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','W'},
            {'W','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','W'},
            {'W','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','W'},
            {'W','-','-','-','-','-','-','-','-','-','-','-','S','-','-','-','-','-','-','-','-','-','-','-','W'},
            {'W','O','O','O','O','O','O','O','O','O','O','-','-','-','O','O','O','O','O','O','O','O','O','O','W'},
            {'W','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','W'},
            {'W','U','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','W'},
            {'W','W','W','W','W','W','W','W','W','W','W','W','W','W','W','W','W','W','W','W','W','W','W','W','W'}      
    },
    {       //1
            {'W','W','W','W','W','W','W','W','W','W','W','W','W','W','W','W','W','W','W','W','W','W','W','W','W'},
            {'W','C','-','-','-','-','-','-','-','-','-','-','G','-','-','-','-','-','-','-','-','-','-','D','W'},
            {'W','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','W'},
            {'W','O','O','O','O','O','O','O','O','O','O','R','R','R','O','O','O','O','O','O','O','O','O','O','W'},
            {'W','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','W'},
            {'W','-','-','-','-','-','-','-','-','-','-','-','X','-','-','-','-','-','-','-','-','-','-','-','W'},
            {'W','-','-','-','-','-','-','-','-','-','-','-','O','-','-','-','-','-','-','-','-','-','-','-','W'},
            {'W','-','-','-','-','-','-','-','-','-','-','-','O','-','-','-','-','-','-','-','-','-','-','-','W'},
            {'W','-','-','-','-','-','-','-','-','-','-','-','O','-','-','-','-','-','-','-','-','-','-','-','W'},
            {'W','-','-','-','-','-','S','-','-','-','-','-','O','-','-','-','-','-','-','S','-','-','-','-','W'},
            {'W','-','-','-','-','-','-','-','-','-','-','-','O','-','-','-','-','-','-','-','-','-','-','-','W'},
            {'W','-','-','-','-','-','-','-','-','-','-','-','O','-','-','-','-','-','-','-','-','-','-','-','W'},
            {'W','-','-','-','-','-','-','-','-','-','-','-','O','-','-','-','-','-','-','-','-','-','-','-','W'},
            {'W','-','-','-','-','-','-','-','-','-','-','-','O','-','-','-','-','-','-','-','-','-','-','-','W'},
            {'W','-','-','-','-','-','-','-','-','-','-','C','O','C','-','-','-','-','-','-','-','-','-','-','W'},
            {'W','-','-','-','O','O','O','O','O','O','O','O','O','O','O','O','O','O','O','O','O','-','-','-','W'},
            {'W','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','W'},
            {'W','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','W'},
            {'W','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','W'},
            {'W','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','W'},
            {'W','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','W'},
            {'W','O','O','O','O','O','O','O','O','O','O','-','-','-','O','O','O','O','O','O','O','O','O','O','W'},
            {'W','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','W'},
            {'W','U','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','W'},
            {'W','W','W','W','W','W','W','W','W','W','W','W','W','W','W','W','W','W','W','W','W','W','W','W','W'}

    },
    {       //2
            {'W','W','W','W','W','W','W','W','W','W','W','W','W','W','W','W','W','W','W','W','W','W','W','W','W'},
            {'W','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','D','W'},
            {'W','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','W'},
            {'W','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','W'},
            {'W','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','W'},
            {'W','-','-','-','-','-','-','-','-','O','-','-','O','-','-','-','O','-','-','-','-','-','-','-','W'},
            {'W','-','-','S','-','-','-','-','-','O','-','-','O','-','-','-','O','-','-','-','-','-','-','-','W'},
            {'W','-','-','-','-','-','-','-','-','O','-','-','O','-','-','-','O','-','-','-','-','-','-','-','W'},
            {'W','-','-','-','-','-','-','-','-','O','-','-','O','-','-','-','O','-','-','-','-','-','-','-','W'},
            {'W','-','-','-','-','-','-','-','-','O','-','-','O','-','-','-','O','-','-','-','-','-','-','-','W'},
            {'W','-','-','-','-','-','-','-','-','O','-','-','O','-','-','-','O','-','-','-','-','-','-','-','W'},
            {'W','-','-','-','-','-','-','-','-','O','-','-','O','-','K','-','O','-','-','-','-','-','-','-','W'},
            {'W','-','-','-','-','-','-','-','-','O','-','-','O','-','-','-','O','-','-','-','-','-','-','-','W'},
            {'W','-','-','-','-','-','-','-','-','O','-','-','O','-','-','-','O','O','O','-','-','-','-','-','W'},
            {'W','-','-','-','-','-','-','-','C','O','-','-','O','-','-','-','-','-','-','-','-','-','-','-','W'},
            {'W','O','O','O','O','O','O','O','O','O','-','-','O','-','-','-','-','S','-','-','-','-','-','-','W'},
            {'W','-','-','-','-','-','-','-','-','-','-','-','O','-','-','-','-','-','-','-','-','-','-','-','W'},
            {'W','-','-','-','-','-','-','-','-','-','-','-','O','-','-','-','-','-','-','-','-','-','-','-','W'},
            {'W','-','-','-','-','-','-','-','-','-','-','-','O','C','-','-','-','-','-','-','-','-','-','-','W'},
            {'W','-','-','-','-','-','-','-','-','-','-','-','O','O','O','O','O','O','O','-','-','-','-','-','W'},
            {'W','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','W'},
            {'W','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','W'},
            {'W','-','-','-','-','-','-','-','-','-','-','S','-','-','-','-','-','-','-','-','-','-','-','-','W'},
            {'W','U','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','W'},
            {'W','W','W','W','W','W','W','W','W','W','W','W','W','W','W','W','W','W','W','W','W','W','W','W','W'}

    },
    {       //3
            {'W','W','W','W','W','W','W','W','W','W','W','W','W','W','W','W','W','W','W','W','W','W','W','W','W'},
            {'W','-','-','-','-','C','O','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','D','W'},
            {'W','-','-','-','S','-','O','-','-','-','-','-','-','S','-','-','-','-','-','-','-','-','-','-','W'},
            {'W','-','-','-','-','-','O','-','-','-','-','-','-','-','-','-','-','-','O','O','O','O','O','O','W'},
            {'W','-','-','-','-','-','O','-','-','-','-','-','-','-','-','-','-','-','O','C','-','-','-','-','W'},
            {'W','-','-','-','-','-','O','-','-','-','-','-','-','-','-','-','-','-','O','-','-','-','-','-','W'},
            {'W','-','-','-','-','-','O','-','-','-','-','-','-','-','-','-','-','-','O','-','-','-','-','-','W'},
            {'W','-','-','-','-','-','O','-','-','-','-','-','-','-','-','-','-','-','O','-','-','-','-','-','W'},
            {'W','-','-','-','-','-','O','-','-','-','-','-','-','-','-','-','-','-','O','-','-','G','-','-','W'},
            {'W','-','-','-','-','S','O','C','-','-','-','-','-','-','-','-','-','-','O','-','-','-','-','-','W'},
            {'W','-','-','O','O','O','O','O','O','O','O','O','-','-','-','-','-','-','O','-','-','-','-','-','W'},
            {'W','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','O','-','-','-','-','-','W'},
            {'W','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','O','-','-','-','-','-','W'},
            {'W','-','-','-','-','-','K','-','-','-','-','-','-','-','O','O','O','O','O','-','-','-','-','-','W'},
            {'W','-','-','-','-','-','-','-','-','-','-','-','-','-','O','O','O','O','O','-','-','-','-','-','W'},
            {'W','-','-','-','-','-','-','-','-','-','-','-','-','-','O','O','O','O','-','-','-','-','-','-','W'},
            {'W','-','-','-','-','-','-','-','-','-','-','-','-','-','O','O','-','-','-','-','-','-','-','-','W'},
            {'W','-','-','-','-','-','-','-','-','-','-','-','-','-','O','-','-','-','-','-','-','-','-','-','W'},
            {'W','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','W'},
            {'W','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','W'},
            {'W','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','K','W'},
            {'W','-','-','-','-','-','-','-','-','-','-','-','-','S','-','-','-','-','-','O','O','O','O','O','W'},
            {'W','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','O','O','O','O','O','O','W'},
            {'W','U','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','O','O','O','O','O','O','O','W'},
            {'W','W','W','W','W','W','W','W','W','W','W','W','W','W','W','W','W','W','W','W','W','W','W','W','W'}

    },
    {       //4
            {'W','W','W','W','W','W','W','W','W','W','W','W','W','W','W','W','W','W','W','W','W','W','W','W','W'},
            {'W','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','G','O','D','W'},
            {'W','-','O','-','O','-','O','O','-','O','-','O','O','O','-','O','O','O','O','O','O','-','O','S','W'},
            {'W','-','O','G','O','-','O','-','-','O','-','O','-','-','-','-','O','-','O','-','-','-','O','-','W'},
            {'W','-','O','O','O','-','O','O','O','O','-','O','O','O','O','-','O','-','O','-','O','O','O','-','W'},
            {'W','-','O','-','-','-','-','-','O','-','-','-','-','-','O','-','-','-','O','-','O','-','-','-','W'},
            {'W','-','O','O','O','O','O','-','O','-','O','O','O','O','O','-','O','-','O','-','O','-','O','O','W'},
            {'W','-','O','-','-','-','-','-','O','-','-','-','-','-','O','-','O','-','O','-','O','-','-','-','W'},
            {'W','-','O','O','O','-','O','-','O','-','O','-','O','-','O','O','O','O','O','-','O','O','O','-','W'},
            {'W','-','O','-','-','-','O','-','O','-','O','C','O','-','O','-','-','-','-','-','-','-','O','-','W'},
            {'W','-','O','-','O','O','O','-','O','-','O','O','O','-','O','O','O','O','O','-','O','-','O','-','W'},
            {'W','-','O','-','-','-','O','-','O','K','O','-','-','-','-','-','-','-','O','-','O','-','O','-','W'},
            {'W','-','O','O','O','-','O','-','O','O','O','O','O','-','O','-','O','-','O','-','O','-','-','-','W'},
            {'W','-','-','-','O','-','O','-','O','-','O','-','-','-','O','-','O','-','O','-','O','-','O','O','W'},
            {'W','-','O','O','O','-','O','-','O','-','O','-','O','-','O','O','O','-','O','-','O','O','O','C','W'},
            {'W','-','O','-','-','-','O','-','O','-','-','-','O','-','O','-','-','-','O','G','-','-','O','-','W'},
            {'W','-','O','O','O','-','O','-','O','-','O','-','O','O','O','O','O','-','O','O','O','-','O','-','W'},
            {'W','S','-','C','O','-','O','C','O','-','O','-','O','-','-','-','-','-','-','-','O','-','O','-','W'},
            {'W','O','O','O','O','O','O','O','O','O','O','O','O','O','O','-','O','-','O','-','O','-','O','-','W'},
            {'W','-','-','-','-','-','O','-','-','-','O','-','-','-','-','-','O','-','O','-','O','-','O','-','W'},
            {'W','-','O','O','O','-','O','-','O','-','O','O','O','O','O','-','O','O','O','-','O','-','O','-','W'},
            {'W','-','-','-','O','-','-','-','O','-','-','-','-','-','-','-','O','-','-','-','O','-','O','-','W'},
            {'W','O','O','O','O','-','O','O','O','-','O','O','O','-','O','O','O','O','O','-','O','-','O','-','W'},
            {'W','U','-','-','-','-','-','-','O','-','O','-','-','-','O','-','-','-','-','-','O','-','-','S','W'},
            {'W','W','W','W','W','W','W','W','W','W','W','W','W','W','W','W','W','W','W','W','W','W','W','W','W'}

    },
    {       //5
            {'W','W','W','W','W','W','W','W','W','W','W','W','W','W','W','W','W','W','W','W','W','W','W','W','W'},
            {'W','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','M','-','-','D','W'},
            {'W','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','W'},
            {'W','-','-','-','S','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','W'},
            {'W','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','W'},
            {'W','-','-','C','-','-','-','-','-','-','-','-','-','-','C','-','-','-','-','-','G','-','-','M','W'},
            {'W','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','W'},
            {'W','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','W'},
            {'W','-','-','-','-','-','M','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','W'},
            {'W','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','W'},
            {'W','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','W'},
            {'W','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','W'},
            {'W','-','-','-','-','-','-','-','-','-','-','-','-','-','-','S','-','-','-','-','-','-','-','-','W'},
            {'W','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','W'},
            {'W','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','W'},
            {'W','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','W'},
            {'W','-','K','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','W'},
            {'W','-','-','-','-','-','-','C','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','W'},
            {'W','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','W'},
            {'W','-','-','-','-','-','-','-','-','-','-','-','-','-','K','-','-','-','-','-','-','-','-','-','W'},
            {'W','-','-','-','-','-','-','S','-','-','-','-','-','-','-','-','-','-','-','-','-','C','-','-','W'},
            {'W','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','W'},
            {'W','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','S','W'},
            {'W','U','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','W'},
            {'W','W','W','W','W','W','W','W','W','W','W','W','W','W','W','W','W','W','W','W','W','W','W','W','W'}

    },
    {       //6
            {'W','W','W','W','W','W','W','W','W','W','W','W','W','W','W','W','W','W','W','W','W','W','W','W','W'},
            {'W','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','O','-','-','-','-','-','W'},
            {'W','-','-','-','-','S','-','-','-','-','-','-','-','-','-','G','-','-','O','-','-','-','-','-','W'},
            {'W','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','O','-','-','-','-','-','W'},
            {'W','-','-','-','-','O','O','O','O','O','O','-','K','-','O','-','-','-','O','-','-','D','-','-','W'},
            {'W','-','-','-','-','O','-','-','-','-','-','-','-','-','O','-','-','-','O','-','-','-','-','-','W'},
            {'W','-','-','-','-','O','-','-','-','-','-','-','-','-','O','-','-','-','O','-','-','-','-','-','W'},
            {'W','-','-','-','-','O','-','-','-','-','-','-','-','-','O','-','-','-','O','-','-','-','-','-','W'},
            {'W','-','-','-','-','O','-','-','-','-','-','-','-','-','O','-','-','-','O','-','-','-','-','-','W'},
            {'W','-','-','-','-','O','-','-','-','-','C','-','-','-','O','-','K','-','O','O','O','G','-','G','W'},
            {'W','-','-','-','-','O','-','-','-','-','-','-','-','-','O','-','-','-','-','-','O','-','-','-','W'},
            {'W','-','M','-','O','O','O','-','-','-','-','-','-','-','O','-','-','-','-','-','O','-','-','-','W'},
            {'W','-','-','-','-','-','O','-','-','-','-','-','-','-','O','-','-','-','-','-','O','-','-','-','W'},
            {'W','-','-','-','-','-','O','-','-','-','-','-','-','-','O','-','-','-','-','-','O','O','-','-','W'},
            {'W','-','-','-','-','-','O','-','-','-','-','-','-','-','O','O','O','O','-','-','O','-','-','-','W'},
            {'W','-','-','C','-','-','O','-','-','-','-','-','-','-','O','-','-','-','-','-','O','-','-','-','W'},
            {'W','-','-','-','-','-','O','-','M','-','O','O','O','O','O','-','-','-','-','-','O','-','-','-','W'},
            {'W','-','-','-','-','-','O','-','-','-','O','-','-','-','O','-','-','-','-','-','O','-','-','-','W'},
            {'W','-','-','-','-','-','O','-','-','-','O','-','-','-','O','-','S','-','O','O','O','-','S','-','W'},
            {'W','-','-','-','-','-','O','-','-','-','O','-','C','-','O','-','-','-','-','O','-','-','-','-','W'},
            {'W','O','O','O','O','O','O','-','-','-','O','-','-','-','O','-','-','-','-','O','-','-','-','-','W'},
            {'W','-','-','-','-','-','-','-','-','-','-','-','-','-','O','-','-','-','-','-','-','-','-','-','W'},
            {'W','-','-','-','-','-','-','-','-','-','-','S','-','-','O','-','-','-','-','K','-','-','-','-','W'},
            {'W','U','-','-','-','-','-','-','-','-','-','-','-','-','O','-','-','-','-','-','-','-','-','-','W'},
            {'W','W','W','W','W','W','W','W','W','W','W','W','W','W','W','W','W','W','W','W','W','W','W','W','W'}

    },
    {       //7
            {'W','W','W','W','W','W','W','W','W','W','W','W','W','W','W','W','W','W','W','W','W','W','W','W','W'},
            {'W','-','O','-','-','-','-','-','-','-','-','-','O','-','-','-','-','-','-','-','-','-','O','D','W'},
            {'W','-','O','-','O','-','O','O','O','O','O','O','O','O','O','O','O','-','O','O','O','O','O','-','W'},
            {'W','-','-','-','O','-','-','-','-','-','-','-','O','-','-','-','O','-','-','-','-','-','O','-','W'},
            {'W','O','O','O','O','-','O','-','O','-','O','-','O','O','O','-','O','-','O','-','O','O','O','-','W'},
            {'W','-','O','-','-','-','O','-','O','-','O','-','O','-','-','-','-','-','O','-','-','-','O','-','W'},
            {'W','-','O','-','O','O','O','O','O','-','O','-','O','O','O','-','O','O','O','-','O','-','O','R','W'},
            {'W','-','-','-','O','-','-','-','O','-','O','-','-','-','-','-','O','-','-','-','O','-','O','-','W'},
            {'W','O','O','O','O','-','O','-','O','O','O','O','O','-','O','-','O','-','O','O','O','O','O','-','W'},
            {'W','-','O','X','O','-','O','-','-','-','-','-','-','-','O','-','O','-','-','-','-','-','-','-','W'},
            {'W','-','O','-','O','-','O','O','O','-','O','-','O','O','O','O','O','-','O','O','O','-','O','-','W'},
            {'W','-','-','-','O','-','O','-','-','-','O','-','-','-','O','-','-','-','O','-','-','-','O','-','W'},
            {'W','-','O','O','O','O','O','O','O','-','O','O','O','-','O','-','O','O','O','O','O','O','O','O','W'},
            {'W','-','-','-','O','-','-','-','-','-','O','-','-','-','O','-','-','-','O','-','O','C','C','C','W'},
            {'W','O','O','-','O','-','O','-','O','-','O','O','O','O','O','-','O','O','O','-','O','-','-','-','W'},
            {'W','-','-','-','-','-','O','-','O','-','O','-','-','-','O','-','O','-','-','-','O','M','-','M','W'},
            {'W','-','O','-','O','O','O','O','O','O','O','O','O','-','O','-','O','O','O','-','O','C','-','C','W'},
            {'W','-','O','-','O','-','-','-','-','-','-','-','-','-','O','-','-','-','-','-','O','-','-','-','W'},
            {'W','-','O','-','O','O','O','O','O','-','O','-','O','-','O','O','O','O','O','-','O','-','-','-','W'},
            {'W','-','O','-','O','-','O','-','O','-','O','-','O','-','-','-','-','-','-','-','O','-','-','-','W'},
            {'W','O','O','-','O','-','O','-','O','O','O','-','O','-','O','-','O','-','O','O','O','O','O','R','W'},
            {'W','-','-','-','O','-','-','-','-','-','-','-','O','-','O','-','O','-','-','-','-','-','-','K','W'},
            {'W','-','O','O','O','O','O','-','O','O','O','O','O','O','O','O','O','-','O','O','O','-','O','-','W'},
            {'W','U','-','-','-','-','O','-','-','-','-','-','M','-','O','-','-','-','-','-','O','-','-','-','W'},
            {'W','W','W','W','W','W','W','W','W','W','W','W','W','W','W','W','W','W','W','W','W','W','W','W','W'}

    },
    {       //8
            {'W','W','W','W','W','W','W','W','W','W','W','W','W','W','W','W','W','W','W','W','W','W','W','W','W'},
            {'W','O','O','O','O','O','O','O','O','-','-','-','-','-','-','-','-','-','-','-','-','-','-','D','W'},
            {'W','O','O','O','O','O','O','O','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','W'},
            {'W','O','O','O','O','O','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','W'},
            {'W','O','O','O','O','O','-','-','-','-','-','-','-','G','-','-','-','-','S','-','-','-','-','-','W'},
            {'W','O','O','O','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','W'},
            {'W','O','O','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','W'},
            {'W','O','O','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','W'},
            {'W','O','O','-','M','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','C','W'},
            {'W','O','O','-','-','-','-','-','-','-','-','-','O','O','-','-','-','-','-','-','O','O','O','O','W'},
            {'W','O','O','-','-','-','-','-','-','-','-','O','O','O','O','-','-','-','O','O','O','O','O','O','W'},
            {'W','O','O','-','-','-','-','K','-','-','-','O','O','O','O','-','-','O','O','O','O','O','O','O','W'},
            {'W','O','-','-','-','-','-','-','-','-','-','-','O','O','-','-','-','-','-','-','-','-','-','O','W'},
            {'W','O','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','O','W'},
            {'W','O','C','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','O','W'},
            {'W','O','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','O','W'},
            {'W','O','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','O','W'},
            {'W','O','O','-','-','-','O','-','-','-','-','-','-','-','O','O','O','O','O','O','O','-','-','-','W'},
            {'W','O','O','-','-','-','O','-','-','-','-','-','G','-','O','-','-','-','-','-','-','-','-','-','W'},
            {'W','O','O','-','-','-','O','-','-','-','-','-','-','-','O','-','M','-','-','-','-','-','-','-','W'},
            {'W','O','O','-','-','-','O','-','-','-','-','-','-','-','O','-','-','-','-','-','-','-','-','-','W'},
            {'W','O','-','-','-','-','O','-','-','-','-','-','-','-','O','-','-','-','-','-','-','-','-','-','W'},
            {'W','-','-','-','-','-','O','-','-','-','-','-','-','-','O','-','-','-','-','-','-','-','-','-','W'},
            {'W','U','-','-','-','-','O','-','-','-','C','-','-','-','O','-','-','-','C','-','-','-','-','-','W'},
            {'W','W','W','W','W','W','W','W','W','W','W','W','W','W','W','W','W','W','W','W','W','W','W','W','W'}

    },
    {       //9
            {'W','W','W','W','W','W','W','W','W','W','W','W','W','W','W','W','W','W','W','W','W','W','W','W','W'},
            {'W','M','O','-','-','-','-','-','-','-','-','-','-','-','-','-','-','S','-','-','-','-','O','D','W'},
            {'W','-','O','O','O','-','O','-','O','O','O','O','O','-','O','O','O','O','O','O','O','-','O','-','W'},
            {'W','-','O','-','-','-','O','G','O','-','-','-','-','-','-','-','O','-','M','-','O','-','O','-','W'},
            {'W','-','O','-','O','O','O','O','O','O','O','O','O','-','O','-','O','-','O','-','O','-','O','-','W'},
            {'W','-','-','-','O','-','-','-','-','-','-','-','-','-','O','-','O','-','O','-','O','-','O','-','W'},
            {'W','-','O','-','O','-','O','O','O','O','O','O','O','-','O','-','O','-','O','-','O','-','O','M','W'},
            {'W','C','O','-','O','-','-','K','O','-','-','-','-','-','O','-','O','-','O','-','O','-','O','-','W'},
            {'W','O','O','-','O','-','O','O','O','-','O','O','O','-','O','O','O','-','O','-','O','-','O','-','W'},
            {'W','-','O','-','O','-','-','-','O','G','O','-','-','-','-','K','O','-','O','-','-','-','O','-','W'},
            {'W','S','O','O','O','-','O','O','O','-','O','O','O','-','O','O','O','-','O','O','O','-','O','-','W'},
            {'W','-','-','-','O','-','O','-','-','-','O','-','-','-','O','-','O','-','-','-','O','O','O','-','W'},
            {'W','-','O','-','O','-','O','-','O','O','O','-','O','O','O','-','O','O','O','-','O','C','-','-','W'},
            {'W','-','O','-','-','-','O','-','O','-','-','-','-','-','O','-','-','-','O','-','O','O','O','-','W'},
            {'W','-','O','O','O','-','O','-','O','-','O','O','O','O','O','-','O','-','O','-','-','-','O','-','W'},
            {'W','-','O','K','O','-','O','-','O','-','O','-','-','-','-','-','O','-','O','-','O','-','O','-','W'},
            {'W','-','O','-','O','-','O','-','O','O','O','O','O','-','O','O','O','-','O','-','O','O','O','-','W'},
            {'W','-','-','-','O','-','O','-','-','-','-','-','O','C','O','-','-','-','O','-','-','-','O','-','W'},
            {'W','O','O','-','O','-','O','O','O','O','O','-','O','O','O','-','O','O','O','O','O','-','O','-','W'},
            {'W','-','O','-','O','-','O','-','-','C','O','-','-','-','-','-','-','-','O','C','O','C','O','-','W'},
            {'W','-','O','-','O','-','O','-','O','O','O','O','O','O','O','O','O','-','O','-','O','O','O','-','W'},
            {'W','-','-','-','O','-','O','-','-','-','-','-','M','-','O','G','O','-','O','-','O','-','-','-','W'},
            {'W','-','O','-','O','-','O','O','O','-','O','O','O','-','O','-','O','-','O','-','O','-','O','-','W'},
            {'W','U','O','-','O','-','-','-','-','-','O','-','-','-','O','-','-','-','-','-','-','-','O','-','W'},
            {'W','W','W','W','W','W','W','W','W','W','W','W','W','W','W','W','W','W','W','W','W','W','W','W','W'}

    },
    {       //10 - BOSS
            {'W','W','W','W','W','W','W','W','W','W','W','W','W','W','W','W','W','W','W','W','W','W','W','W','W'},
            {'W','-','-','-','-','-','-','-','-','-','O','-','D','-','O','-','-','-','-','-','-','-','-','-','W'},
            {'W','-','-','-','-','-','-','-','-','-','O','-','-','-','O','-','-','-','-','-','-','-','-','-','W'},
            {'W','-','-','-','-','-','-','-','-','-','O','-','-','-','O','-','-','-','-','-','-','-','-','-','W'},
            {'W','-','-','-','O','O','O','-','-','-','O','O','R','O','O','-','-','-','O','O','O','-','-','-','W'},
            {'W','-','-','C','O','O','O','-','-','-','-','-','-','-','-','-','-','-','O','O','O','C','-','-','W'},
            {'W','-','-','-','O','O','O','-','-','-','-','-','-','-','-','-','-','-','O','O','O','-','-','-','W'},
            {'W','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','W'},
            {'W','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','W'},
            {'W','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','W'},
            {'W','-','-','-','-','-','-','-','-','-','-','O','O','O','-','-','-','-','-','-','-','-','-','-','W'},
            {'W','-','-','-','-','-','-','-','-','-','-','O','O','O','-','-','-','-','-','-','-','-','-','-','W'},
            {'W','-','-','-','-','-','-','-','-','-','-','O','O','O','-','-','-','-','-','-','-','-','-','-','W'},
            {'W','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','W'},
            {'W','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','W'},
            {'W','-','-','-','-','-','-','-','-','-','-','-','B','-','-','-','-','-','-','-','-','-','-','-','W'},
            {'W','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','W'},
            {'W','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','W'},
            {'W','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','W'},
            {'W','-','-','O','O','O','O','O','-','-','-','-','-','-','-','-','-','O','O','O','O','O','-','-','W'},
            {'W','-','-','-','-','C','-','-','-','-','-','-','-','-','-','-','-','-','-','C','-','-','-','-','W'},
            {'W','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','W'},
            {'W','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','W'},
            {'W','U','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','W'},
            {'W','W','W','W','W','W','W','W','W','W','W','W','W','W','W','W','W','W','W','W','W','W','W','W','W'}

    },
    {       //11 -  FINAL CHEST ROOM    
            {'W','W','W','W','W','W','W','W','W','W','W','W','W','W','W','W','W','W','W','W','W','W','W','W','W'},
            {'W','-','-','-','-','-','-','-','-','-','-','-','C','-','-','-','-','-','-','-','-','-','-','D','W'},
            {'W','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','W'},
            {'W','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','W'},
            {'W','-','-','-','-','-','-','-','-','-','-','-','C','-','-','-','-','-','-','-','-','-','-','-','W'},
            {'W','-','-','-','-','-','-','-','-','-','-','-','C','-','-','-','-','-','-','-','-','-','-','-','W'},
            {'W','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','W'},
            {'W','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','W'},
            {'W','-','-','-','-','-','-','-','-','-','-','-','C','-','-','-','-','-','-','-','-','-','-','-','W'},
            {'W','-','-','-','-','-','-','-','-','-','-','-','C','-','-','-','-','-','-','-','-','-','-','-','W'},
            {'W','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','W'},
            {'W','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','W'},
            {'W','-','-','-','-','-','-','-','-','-','-','-','C','-','-','-','-','-','-','-','-','-','-','-','W'},
            {'W','-','-','-','-','-','-','-','-','-','-','-','C','-','-','-','-','-','-','-','-','-','-','-','W'},
            {'W','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','W'},
            {'W','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','W'},
            {'W','-','-','-','-','-','-','-','-','-','-','-','C','-','-','-','-','-','-','-','-','-','-','-','W'},
            {'W','-','-','-','-','-','-','-','-','-','-','-','C','-','-','-','-','-','-','-','-','-','-','-','W'},
            {'W','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','W'},
            {'W','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','W'},
            {'W','-','-','-','-','-','-','-','-','-','-','-','C','-','-','-','-','-','-','-','-','-','-','-','W'},
            {'W','-','-','-','-','-','-','-','-','-','-','-','C','-','-','-','-','-','-','-','-','-','-','-','W'},
            {'W','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','W'},
            {'W','U','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','-','W'},
            {'W','W','W','W','W','W','W','W','W','W','W','W','W','W','W','W','W','W','W','W','W','W','W','W','W'}

    }
    };
    
    
    
    
}
