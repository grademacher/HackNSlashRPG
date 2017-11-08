import acm.graphics.*;
import acm.program.*;
import acm.util.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

/**
 * Write a description of class HackNSlashMain here.
 * 
 * @author Garrett Rademacher
 * @version 0.1
 */
public class HackNSlashMain extends GraphicsProgram
{
    //constants
    public static int
    APPLICATION_WIDTH = 1100,
    APPLICATION_HEIGHT = 823;

    private static final double SIZE = 32;

    // instance variables
    private boolean gameOver = false; //whether the game is over
    public Player player; //the player
    private double aimAngle; //direction the player clicks in
    private int currentFloor = 5; //floor the player is on
    public MapCell[][] cell = new MapCell[25][25]; //2D array of map cells, holds all map data
    public ArrayList<Monster> monsterList = new ArrayList<Monster>(); //keeps track of all the monsters
    public ArrayList<Chest> chestList = new ArrayList<Chest>(); //keeps trrack of all of the chests in the map
    public ArrayList<Collectible> lootList = new ArrayList<Collectible>(); //keeps track of all the loot on the floor
    
    private RandomGenerator rand = RandomGenerator.getInstance();

    //Interface labels - these display the players stats
    private GLabel lblHeader;
    private GLabel lblHealth;
    private GLabel lblMeleeWeaponStr;
    private GLabel lblMeleeBaseStr;
    private GLabel lblMagicWeaponStr;
    private GLabel lblMagicBaseStr;
    private GLabel lblLevel;
    private GLabel lblExp;
    private GLabel lblCoins;
    
    //Interface bars - visually display some of the player's stats
    private GRect outerExpBar;
    private GRect innerExpBar;
    private GRect outerHealthBar;
    private GRect innerHealthBar;
    
    private GImage fovHide = new GImage("mazeCover.png"); //limits the player's fov while in a maze
    
    /** run() adds event listeners, allows the player to choose a class, draws the initial graphics */
    public void run(){
        //add listeners
        addMouseListeners();
        addKeyListeners();

        //allow the player to select their class
        JFrame frame = new JFrame("Class Selection");

        String[] possibilities = {"Mage", "Knight"};
        String choice = (String)JOptionPane.showInputDialog(
                frame,
                "Choose your class:",
                "Class Selection",
                JOptionPane.PLAIN_MESSAGE,
                null,
                possibilities,
                possibilities[0]);

        //If a string was returned, set a class
        if ((choice != null) && (choice.length() > 0)) {
            if(choice.equals("Mage")){
                player = new Player(2, this);
            }else if(choice.equals("Knight")){
                player = new Player(1, this);
            }
        }else{//default to knight class
            player = new Player(1, this);
        }

        //draw menu
        //draw first map
        drawGraphics();
        drawRoom(0);
        //add one to current floor every time a player advances a floor
        //draw starting graphics
    }

    /** keyPressed() handles moving, spells, and several checks for the player in relation to map data */
    public void keyPressed(KeyEvent e){
        if(gameOver) return; //game is over, return
        if(player.isBlocking()) return; //if the player is blocking, dont let them move
        if(player.getActionBlocker()) return; //player is doing an action

        int key = e.getKeyCode();

        if(key == 87){ //'w' pressed
            if(player.getMoveBlock()) return; //player cannot move
            player.setMoveBlock(true);
            effectTimer MTimer = new effectTimer(player, 6, this);
            new Thread(MTimer).start();
            //move forward
            moveUp(player.getRow(), player.getCol(), player.getCharType(), player, player);
        }else if(key == 83){ //'s' pressed
            if(player.getMoveBlock()) return; //player cannot move
            player.setMoveBlock(true);
            effectTimer MTimer = new effectTimer(player, 6, this);
            new Thread(MTimer).start();
            //move back
            moveDown(player.getRow(), player.getCol(), player.getCharType(), player, player);
        }else if(key == 68){ //'d' pressed
            if(player.getMoveBlock()) return; //player cannot move
            player.setMoveBlock(true);
            effectTimer MTimer = new effectTimer(player, 6, this);
            new Thread(MTimer).start();
            //move right
            moveRight(player.getRow(), player.getCol(), player.getCharType(), player, player);
        }else if(key == 65){ //'a' pressed
            if(player.getMoveBlock()) return; //player cannot move
            player.setMoveBlock(true);
            effectTimer MTimer = new effectTimer(player, 6, this);
            new Thread(MTimer).start();
            //move left
            moveLeft(player.getRow(), player.getCol(), player.getCharType(), player, player);
        }else if(key == 49 && player.getCharType() == 2){ //'1' pressed
            if(!player.getSpellReady()) return;
            //create new ice spell
            Spell spell = new Spell(player.getRow(), player.getCol(), player.getDirection(), 1, this);
            //setThread(new Thread(spell).start());
            new Thread(spell).start();
            //create a cooldown for the spell
            player.setSpellReady(false);
            effectTimer spellTimer = new effectTimer(player, 5, this);
            new Thread(spellTimer).start();
        }else if(key == 50 && player.getCharType() == 2){ //'2' pressed
            if(!player.getSpellReady()) return;
            //create new fire spell
            Spell spell = new Spell(player.getRow(), player.getCol(), player.getDirection(), 2, this);
            new Thread(spell).start();
            //create a cooldown for the spell
            player.setSpellReady(false);
            effectTimer spellTimer = new effectTimer(player, 5, this);
            new Thread(spellTimer).start();
        }else if(key == 51 && player.getCharType() == 2){ //'3' pressed
            if(!player.getSpellReady()) return;
            //create new wind spell
            Spell spell = new Spell(player.getRow(), player.getCol(), player.getDirection(), 3, this);
            new Thread(spell).start();
            //create a cooldown for the spell
            player.setSpellReady(false);
            effectTimer spellTimer = new effectTimer(player, 5, this);
            new Thread(spellTimer).start();
        }else if(key == 52 && player.getCharType() == 2){ //'4' pressed
            if(!player.getSpellReady()) return;
            //create new curse spell
            Spell spell = new Spell(player.getRow(), player.getCol(), player.getDirection(), 4, this);
            new Thread(spell).start();
            //create a cooldown for the spell
            player.setSpellReady(false);
            effectTimer spellTimer = new effectTimer(player, 5, this);
            new Thread(spellTimer).start();
        }
        
        //check if the player has returned to the previos floor
        if(cell[player.getRow()][player.getCol()].isStairUp()){
            returnFloor();
        }
        //check if the player has advanced to the next floor
        if(cell[player.getRow()][player.getCol()].isStairDown()){
            advanceFloor();
        }
        
        //move the fov hider
        fovHide.setLocation( (16 + ((player.getCol())*32))-fovHide.getWidth()/2 , (16 + ((player.getRow())*32))- fovHide.getHeight()/2 );
        
        cell[player.getRow()][player.getCol()].setIsPassable(true);

        //check for any secret switches
        if(cell[player.getRow()][player.getCol()].isSwitch()){
            //loop through the grid, open any doors
            for(int i = 0; i < 25; i++){
                for(int j = 0; j < 25; j++){
                    if(cell[i][j].isDoor()){
                        cell[i][j].openDoor();
                    }
                }
            }
        }

        //check for any loot
        for(int i = 0; i<lootList.size(); i++){
            if(player.getRow() == lootList.get(i).getRow() && player.getCol() == lootList.get(i).getCol()){
                lootList.get(i).pickUp();
            }
        }
    }

    /** mousePressed() handles attacking, opening chests, blocking, and changing direction */
    public void mousePressed(MouseEvent e){
        if(gameOver) return;
        if(player.getActionBlocker()) return; //player is doing an action

        //coordinates of the click
        double mouseX = e.getPoint().getX();
        double mouseY = e.getPoint().getY();

        //coordinate of the center of the player
        double playerX = (SIZE/2 + SIZE*player.getCol());
        double playerY = (SIZE/2 + SIZE*player.getRow());

        //calculate the angle from the player to the mouse click
        aimAngle = GMath.angle(playerX, playerY, mouseX, mouseY);
        //make sure the angle falls within a value in the upcoming if statement
        if(aimAngle < -45){
            aimAngle += 360;
        }

        //turn the player that direction
        if(45 <= aimAngle && aimAngle < 135){//facing north
            faceUp(player.getRow(), player.getCol(), player.getCharType(), player, player);
        }else if(225 <= aimAngle && aimAngle < 315){//facing south
            faceDown(player.getRow(), player.getCol(), player.getCharType(), player, player);
        }else if(135 <= aimAngle && aimAngle < 225){//facing left
            faceLeft(player.getRow(), player.getCol(), player.getCharType(), player, player);
        }else if(45 > aimAngle && aimAngle >= -45){//facing right
            faceRight(player.getRow(), player.getCol(), player.getCharType(), player, player);
        }

        //player action
        if (e.getButton() == MouseEvent.BUTTON1){ //left click
            //get player coords
            //check if coords infront of player have monster
            for(int i = 0; i < monsterList.size(); i++){
                if(player.getDirection() == 1){
                    if(monsterList.get(i).getRow() == player.getRow()-1 && monsterList.get(i).getCol() == player.getCol()){
                        player.attack(monsterList.get(i));
                        //animate sword in the up direction
                    }
                }else if(player.getDirection() == 2){
                    if(monsterList.get(i).getRow() == player.getRow()+1 && monsterList.get(i).getCol() == player.getCol()){
                        player.attack(monsterList.get(i));
                        //animate sword in the up direction
                    }
                }else if(player.getDirection() == 3){
                    if(monsterList.get(i).getRow() == player.getRow() && monsterList.get(i).getCol() == player.getCol()-1){
                        player.attack(monsterList.get(i));
                        //animate sword in the up direction
                    }
                }else if(player.getDirection() == 4){
                    if(monsterList.get(i).getRow() == player.getRow() && monsterList.get(i).getCol() == player.getCol()+1){
                        player.attack(monsterList.get(i));
                        //animate sword in the up direction
                    }
                }
            }

            //loop through the list of chests on the game board to see if the player opens any
            for(int i = 0; i < chestList.size(); i++){
                if(player.getDirection() == 1){
                    if(chestList.get(i).getRow() == player.getRow()-1 && chestList.get(i).getCol() == player.getCol()){
                        //open chest
                        chestList.get(i).openChest();
                    }
                }else if(player.getDirection() == 2){
                    if(chestList.get(i).getRow() == player.getRow()+1 && chestList.get(i).getCol() == player.getCol()){
                        //open chest
                        chestList.get(i).openChest();
                    }
                }else if(player.getDirection() == 3){
                    if(chestList.get(i).getRow() == player.getRow() && chestList.get(i).getCol() == player.getCol()-1){
                        //open chest
                        chestList.get(i).openChest();
                    }
                }else if(player.getDirection() == 4){
                    if(chestList.get(i).getRow() == player.getRow() && chestList.get(i).getCol() == player.getCol()+1){
                        //open chest
                        chestList.get(i).openChest();
                    }
                }
            }

        }else if(e.getButton() == MouseEvent.BUTTON3 && player.getCharType() == 1){ //right click
            //shield to true
            player.setIsBlocking(true);
            player.showShield();
        }
        cell[player.getRow()][player.getCol()].setIsPassable(true);
    }

    /** mouseReleased() stops the player blocking when mouse released */
    public void mouseReleased(MouseEvent e){
        //set shield to false
        player.setIsBlocking(false);
        player.hideShield();
    }

    /** moveUp() moves the passed creature up */
    public void moveUp(int row, int col, int charType, GObject Gcreature, Creature creature){
        if(row-1<25 && cell[row-1][col].getIsPassable()){
            //cell[row][col].hideChar(charType);
            cell[row][col].clear(Gcreature, creature);
            cell[row-1][col].showChar(Gcreature, creature); //show the characters back
            if(creature.getCharType() == 1 || creature.getCharType() == 2){
                spawnTimer sTimer = new spawnTimer(row,col, currentFloor, this); //chance to spawn monster in the players old position
                new Thread(sTimer).start();
            }
            //update creature's data and image
            creature.setRow(row-1);
            creature.setDirection(1);
            creature.setImage();
            if(cell[creature.getRow()][creature.getCol()].isDoor()){
                moveUp(creature.getRow(), creature.getCol(), creature.getCharType(), Gcreature, creature);
            }
        }else{
            faceUp(row, col, charType, Gcreature, creature);
        }
    }

    /** faceUp() changes the passed creature's orientation */
    public void faceUp(int row, int col, int charType, GObject Gcreature, Creature creature){
        //cell[row][col].hideChar(charType);
        cell[row][col].clear(Gcreature, creature);
        cell[row][col].showChar(Gcreature, creature);
        creature.setDirection(1);
        creature.setImage();
    }

    /** moveDown() moves the passed creature down */
    public void moveDown(int row, int col, int charType, GObject Gcreature, Creature creature){
        if(row+1>=0 && cell[row+1][col].getIsPassable()){
            //cell[row][col].hideChar(charType);
            cell[row][col].clear(Gcreature, creature);
            cell[row+1][col].showChar(Gcreature, creature); //show the characters face
            if(creature.getCharType() == 1 || creature.getCharType() == 2){
                spawnTimer sTimer = new spawnTimer(row,col, currentFloor, this); //chance to spawn monster in the players old position
                new Thread(sTimer).start();
            }
            //update creature's data and image
            creature.setRow(row+1);
            creature.setDirection(2);
            creature.setImage();
            if(cell[creature.getRow()][creature.getCol()].isDoor()){
                moveDown(creature.getRow(), creature.getCol(), creature.getCharType(), Gcreature, creature);
            }
        }else{
            faceDown(row, col, charType, Gcreature, creature);
        }
    }

    /** faceDown() changes the passed creature's orientation */
    public void faceDown(int row, int col, int charType, GObject Gcreature, Creature creature){
        //cell[row][col].hideChar(charType);
        cell[row][col].clear(Gcreature, creature);
        cell[row][col].showChar(Gcreature, creature);
        creature.setDirection(2);
        creature.setImage();
    }

    /** moveleft() moves the passed creature left */
    public void moveLeft(int row, int col, int charType, GObject Gcreature, Creature creature){
        if(col-1>=0 && cell[row][col-1].getIsPassable()){
            //cell[row][col].hideChar(charType);
            cell[row][col].clear(Gcreature, creature);
            cell[row][col-1].showChar(Gcreature, creature);
            if(creature.getCharType() == 1 || creature.getCharType() == 2){
                spawnTimer sTimer = new spawnTimer(row,col, currentFloor, this); //chance to spawn monster in the players old position
                new Thread(sTimer).start();
            }
            //update creature's data and image
            creature.setCol(col-1);
            creature.setDirection(3);
            creature.setImage();
            if(cell[creature.getRow()][creature.getCol()].isDoor()){
                moveLeft(creature.getRow(), creature.getCol(), creature.getCharType(), Gcreature, creature);
            }
        }else{
            faceLeft(row, col, charType, Gcreature, creature);
        }
    }

    /** faceLeft() changes the passed creature's orientation */
    public void faceLeft(int row, int col, int charType, GObject Gcreature, Creature creature){
        //cell[row][col].hideChar(charType);
        cell[row][col].clear(Gcreature, creature);
        cell[row][col].showChar(Gcreature, creature);
        creature.setDirection(3);
        creature.setImage();
    }

    /** moveRight() moves the passed creature right */
    public void moveRight(int row, int col, int charType, GObject Gcreature, Creature creature){
        if(col+1<25 && cell[row][col+1].getIsPassable()){
            //cell[row][col].hideChar(charType);
            cell[row][col].clear(Gcreature, creature);
            cell[row][col+1].showChar(Gcreature, creature); 
            if(creature.getCharType() == 1 || creature.getCharType() == 2){
                spawnTimer sTimer = new spawnTimer(row,col, currentFloor, this); //chance to spawn monster in the players old position
                new Thread(sTimer).start();
            }
            //update creature's data and image
            creature.setCol(col+1);
            creature.setDirection(4);
            creature.setImage();
            if(cell[creature.getRow()][creature.getCol()].isDoor()){
                moveRight(creature.getRow(), creature.getCol(), creature.getCharType(), Gcreature, creature);
            }
        }else{
            faceRight(row, col, charType, Gcreature, creature);
        }
    }

    /** faceRight() changes the passed creature's orientation */
    public void faceRight(int row, int col, int charType, GObject Gcreature, Creature creature){
        //cell[row][col].hideChar(charType);
        cell[row][col].clear(Gcreature, creature);
        cell[row][col].showChar(Gcreature, creature);
        creature.setDirection(4);
        creature.setImage();
    }

    /** drawGraphics() draws the HUD interface, the map cells, and fov hider */
    private void drawGraphics(){
        //fill mapCell with new map cells
        for(int i = 0; i < 25; i++){
            for(int j = 0; j < 25; j++){
                cell[i][j] = new MapCell(i, j, this);
                add(cell[i][j], (SIZE/2 + SIZE*j), (SIZE/2 + SIZE*i) );
            }
        }
        
        //add fov hider
        add(fovHide);
        fovHide.setVisible(false);
        
        //add parchment to the bottom of the screen
        GImage parchment = new GImage("parchment.png");
        add(parchment);
        parchment.setLocation(800, 0);

        //add header
        lblHeader = new GLabel("PLAYER INFO");
        add(lblHeader);
        lblHeader.setColor(Color.BLACK);
        lblHeader.setFont(new Font("Sanserif", Font.BOLD, 30));
        lblHeader.setLocation(800 + 150-lblHeader.getWidth()/2, 50);

        //add level
        lblLevel = new GLabel("Level: " + player.getLvl());
        add(lblLevel);
        lblLevel.setColor(Color.BLACK);
        lblLevel.setFont(new Font("Sanserif", Font.BOLD, 20));
        lblLevel.setLocation(820,130);

        //add experience bar
        lblExp = new GLabel("Experience: " + player.getCurrentExp());
        add(lblExp);
        lblExp.setColor(Color.BLACK);
        lblExp.setFont(new Font("Sanserif", Font.BOLD, 20));
        lblExp.setLocation(820,180);

        innerExpBar = new GRect(820,200,260,20);
        innerExpBar.setColor(Color.GREEN);
        innerExpBar.setFilled(true);
        add(innerExpBar);

        outerExpBar = new GRect(820, 200, 260, 20);
        outerExpBar.setColor(Color.BLACK);
        outerExpBar.setFilled(false);
        add(outerExpBar);

        //add health
        lblHealth = new GLabel("Health: " + player.getHealth() + "/" + player.getInitHealth());
        add(lblHealth);
        lblHealth.setColor(Color.BLACK);
        lblHealth.setFont(new Font("Sanserif", Font.BOLD, 20));
        lblHealth.setLocation(820,250);

        innerHealthBar = new GRect(820,270,250,20);
        innerHealthBar.setColor(Color.RED);
        innerHealthBar.setFilled(true);
        add(innerHealthBar);

        outerHealthBar = new GRect(820, 270, 260, 20);
        outerHealthBar.setColor(Color.BLACK);
        outerHealthBar.setFilled(false);
        add(outerHealthBar);

        //add coins
        lblCoins = new GLabel("Coins: " + player.getCoins());
        add(lblCoins);
        lblCoins.setColor(Color.BLACK);
        lblCoins.setFont(new Font("Sanserif", Font.BOLD, 20));
        lblCoins.setLocation(820,320);

        //add melee weapon strength
        lblMeleeWeaponStr = new GLabel("Weapon Strength: " + player.getMeleeWeaponStr());
        add(lblMeleeWeaponStr);
        lblMeleeWeaponStr.setColor(Color.BLACK);
        lblMeleeWeaponStr.setFont(new Font("Sanserif", Font.BOLD, 20));
        lblMeleeWeaponStr.setLocation(820,370);

        //add base melee str
        lblMeleeBaseStr = new GLabel("Base Melee Strength: " + player.getMeleeBaseStr());
        add(lblMeleeBaseStr);
        lblMeleeBaseStr.setColor(Color.BLACK);
        lblMeleeBaseStr.setFont(new Font("Sanserif", Font.BOLD, 20));
        lblMeleeBaseStr.setLocation(820,420);

        //add magic weapon str
        lblMagicWeaponStr = new GLabel("Magic Weapon Strength: " + player.getMagicWeaponStr());
        add(lblMagicWeaponStr);
        lblMagicWeaponStr.setColor(Color.BLACK);
        lblMagicWeaponStr.setFont(new Font("Sanserif", Font.BOLD, 20));
        lblMagicWeaponStr.setLocation(820,470);

        //add base magic str
        lblMagicBaseStr = new GLabel("Base Magic Strength: " + player.getMagicBaseStr());
        add(lblMagicBaseStr);
        lblMagicBaseStr.setColor(Color.BLACK);
        lblMagicBaseStr.setFont(new Font("Sanserif", Font.BOLD, 20));
        lblMagicBaseStr.setLocation(820,520);

        //continuously update the HUD
        interfaceTimer ITimer = new interfaceTimer(this);
        new Thread(ITimer).start();
        
        
        //display game controls
        System.out.println("MOVING:");
        System.out.println("w - up");
        System.out.println("a - left");
        System.out.println("s - down");
        System.out.println("d - right \n");
        
        System.out.println("ATTACKING:");
        System.out.println("left click - melee attack");
        System.out.println("right click - block (knight only)");
        System.out.println("1 - ice spell (mage only)");
        System.out.println("2 - fire spell (mage only)");
        System.out.println("3 - wind spell (mage only)");
        System.out.println("4 - curse spell (mage only)");
        
    }

    /** spawnMonster() spawns random monsters behind the player based on the current floor */
    public void spawnMonster(int row, int col, int passedFloor){
        //check if the cell is occupied
        for(int i = 0; i < monsterList.size(); i++){
            if(monsterList.get(i).getRow() == row && monsterList.get(i).getCol() == col){return;}
        }
        //check to make sure player has not changed floors 
        if(!(passedFloor == currentFloor)){return;}

        //spawn random monster
        int spawnChance = rand.nextInt(50);

        if(currentFloor < 3){//spawn spider
            if(spawnChance == 0){ // 1 in 10 chance to spawn
                Monster spider = new Monster(row, col, 5, this);
                new Thread(spider).start();
                cell[row][col].showChar(spider, spider);
            }
        }else if(currentFloor < 6){//spawn goblin or spider
            if(spawnChance == 0){ // 1 in 10 chance to spawn spider
                Monster spider = new Monster(row, col, 5, this);
                new Thread(spider).start();
                cell[row][col].showChar(spider, spider);
            }else if(spawnChance == 1){//1 in 10 chance to spawn goblin
                Monster goblin = new Monster(row, col, 4, this);
                new Thread(goblin).start();
                cell[row][col].showChar(goblin, goblin);
            }
        }else if(currentFloor < 9){//spawn skeleton
            if(spawnChance == 0){
                Monster skeleton = new Monster(row, col, 3, this);
                new Thread(skeleton).start();
                cell[row][col].showChar(skeleton, skeleton);
            }
        }else{//chance for skeleton or mummy
            if(spawnChance == 0){
                Monster skeleton = new Monster(row, col, 3, this);
                new Thread(skeleton).start();
                cell[row][col].showChar(skeleton, skeleton);
            }else if(spawnChance == 1){
                Monster mummy = new Monster(row, col, 6, this);
                new Thread(mummy).start();
                cell[row][col].showChar(mummy, mummy);
            }
        }
    }

    /** displayStore() displays the store and allows player to buy upgrades */
    private void displayStore(){
        boolean doneBuying = false;
        while(!doneBuying){
            //allow the player to select their class
            JFrame frame = new JFrame("Store");
            int gold = 0;
            int swordCost = (int)(1.5*player.getMeleeWeaponStr())+ 3;
            int wandCost = (int)(1.5*player.getMagicWeaponStr()) + 3;
            String[] possibilities = {"Sword upgrade - " + swordCost +" gold", "Wand upgrade - " + wandCost + " gold", 
                    "1/3 health - 10 gold", "1/2 health - 15 gold", 
                    "full health - 30 gold", "nothing"};
            String choice = (String)JOptionPane.showInputDialog(
                    frame,
                    "What would you like to buy?",
                    "Store Menu",
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    possibilities,
                    possibilities[5]);

            //If a string was returned, set a class
            if ((choice != null) && (choice.length() > 0)) {
                if(choice.equals("Sword upgrade - " + swordCost +" gold") && player.getCoins() >= swordCost){
                    player.setMeleeWeaponStr(2);
                    player.payCoins(swordCost);
                }else if(choice.equals("Wand upgrade - " + wandCost + " gold") && player.getCoins() >= wandCost){
                    player.setMagicWeaponStr(2);
                    player.payCoins(wandCost);
                }else if(choice.equals("1/3 health - 10 gold") && player.getCoins() >= 10){
                    player.addHealth((int)player.getInitHealth()/3);
                    player.payCoins(10);
                }else if(choice.equals("1/2 health - 15 gold") && player.getCoins() >= 15){
                    player.addHealth((int)player.getInitHealth()/2);
                    player.payCoins(15);
                }else if(choice.equals("full health - 30 gold") && player.getCoins() >= 30){
                    player.addHealth((int)player.getInitHealth());
                    player.payCoins(30);
                }else if(choice.equals("nothing")){//'nothing' was selected
                    doneBuying = true;
                }else{//player did not have enough money to pay or nothing was selected
                    //do nothing
                }
            }else{//default to 'nothing'
                doneBuying = true;
            }
        }
    }

    /** updateInterface() updates the HUD interface */
    public void updateInterface(){
        //update labels
        lblHealth.setLabel("Health: " + player.getHealth() + "/" + player.getInitHealth());
        lblMeleeWeaponStr.setLabel("Weapon Strength: " + player.getMeleeWeaponStr());
        lblMeleeBaseStr.setLabel("Base Melee Strength: " + player.getMeleeBaseStr());
        lblMagicWeaponStr.setLabel("Magic Weapon Strength: " + player.getMagicWeaponStr());
        lblMagicBaseStr.setLabel("Base Magic Strength: " + player.getMagicBaseStr());
        lblLevel.setLabel("Level: " + player.getLvl());
        lblExp.setLabel("Experience: " + player.getCurrentExp());
        lblCoins.setLabel("Coins: " + player.getCoins());

        //update visual bars
        innerHealthBar.setSize(260*(player.getHealth()/player.getInitHealth()), 20);
        innerExpBar.setSize(260*(player.getCurrentExp()/player.getNextExpLevel()), 20);
    }
    
    /** advaceFloor() advances the player to the next floor */
    private void advanceFloor(){
        //kill all monsters and remove them from the board
        for(int i = 0; i < monsterList.size(); i++){
            monsterList.get(i).setAlive(false);
        }
        //clear the lootList
        lootList.clear();
        currentFloor++;
        if(currentFloor%2 == 0){//allow the player to buy things every other floor
            displayStore();
        }
        if(currentFloor == 11){//player has beaten all floors
            gameOver(2);
            return;
        }
        
        drawRoom(currentFloor);
        if(currentFloor == 9 || currentFloor == 7 || currentFloor == 4){
            fovHide.setVisible(true);
        }else{
            fovHide.setVisible(false);
        }
    }
    
    /** returnFloor() returns the player to the previous floor */
    private void returnFloor(){
        if(currentFloor == 0){//player has beaten all floors
            return;
        }
        //kill all monsters and remove them from the board
        for(int i = 0; i < monsterList.size(); i++){
            monsterList.get(i).setAlive(false);
        }
        //clear the lootList
        lootList.clear();
        currentFloor--;
        
        drawRoom(currentFloor);
        if(currentFloor == 9 || currentFloor == 7 || currentFloor == 4){
            fovHide.setVisible(true);
        }else{
            fovHide.setVisible(false);
        }
    }

    /** drawRoom() draws the passed number of room */
    private void drawRoom(int roomNumber){

        Map map = new Map(roomNumber);

        for(int i = 0; i < 25; i++){
            for(int j = 0; j < 25; j++){
                //clear the old cell
                cell[i][j].clear();
                cell[i][j].addToScreen(map.getChar(i,j));

                if(map.getChar(i,j) == 'W'){//wall
                    cell[i][j].setIsWall(true);
                    cell[i][j].setIsPassable(false);
                }else if(map.getChar(i,j) == 'U'){//stair up
                    //cell[i][j].setIsStairUp(true);
                    cell[i][j].setIsChar(true);
                    player.setRow(i);
                    player.setCol(j);
                    cell[i][j].showChar(player, player);
                }else if(map.getChar(i,j) == 'D'){//stair down
                    cell[i][j].setIsStairDown(true);
                }else if(map.getChar(i,j) == 'O'){//obstacle
                    cell[i][j].setIsObstacle(true);
                    cell[i][j].setIsPassable(false);
                }else if(map.getChar(i,j) == 'C'){//chest
                    cell[i][j].setIsChestClosed(true);
                    cell[i][j].setIsPassable(false);
                    Chest chest = new Chest(roomNumber, false, i, j, this);
                    cell[i][j].addObject(chest);
                    chestList.add(chest);
                }else if(map.getChar(i,j) == 'P'){//portal
                    cell[i][j].setIsPortal(true);
                }else if(map.getChar(i,j) == 'G'){//goblin
                    cell[i][j].setIsGoblin(true);
                    cell[i][j].setIsPassable(false);
                    Monster goblin = new Monster(i,j,4, this);
                    new Thread(goblin).start();
                }else if(map.getChar(i,j) == 'S'){//spider
                    cell[i][j].setIsSpider(true);
                    cell[i][j].setIsPassable(false);
                    Monster spider = new Monster(i,j,5, this);
                    new Thread(spider).start();
                }else if(map.getChar(i,j) == 'M'){//mummy
                    cell[i][j].setIsMummy(true); 
                    cell[i][j].setIsPassable(false);
                    Monster mummy = new Monster(i,j,6, this);
                    new Thread(mummy).start();
                }else if(map.getChar(i,j) == 'K'){//skeleton
                    cell[i][j].setIsSkeleton(true); 
                    cell[i][j].setIsPassable(false);
                    Monster skeleton = new Monster(i,j,3, this);
                    new Thread(skeleton).start();
                }else if(map.getChar(i,j) == 'B'){//boss
                    cell[i][j].setIsBoss(true); 
                    cell[i][j].setIsPassable(false);
                    Monster boss = new Monster(i,j,7, this);
                    new Thread(boss).start();
                }else if(map.getChar(i,j) == 'X'){//switch
                    cell[i][j].setIsSwitch(true); 
                }

            }
        }
        //loop through the array of cells, and the array from the map class
        //fill the cells of main array depending on value in map class array
        //create new instances as needed

        //loop through the monsterList and add the monsters to the screen
        for(int i = 0; i < monsterList.size(); i++){
            cell[monsterList.get(i).getRow()][monsterList.get(i).getCol()].showChar(monsterList.get(i),monsterList.get(i));
        }
    }

    /** gameOver() handles events when the game is over */
    public void gameOver(int endType){
        for(int i = 0; i < monsterList.size(); i++){
            monsterList.get(i).setAlive(false);
        }
        if(endType==1){//player died
            gameOver = true;
            GImage endMsg = new GImage("bloodDeath.png");
            add(endMsg);
            endMsg.setLocation(400 - endMsg.getWidth()/2, getHeight()/2 - endMsg.getHeight()/2);
        }else if(endType==2){//game was won
            gameOver = true;
            GImage endMsg = new GImage("youWon.png");
            add(endMsg);
            endMsg.setLocation(400 - endMsg.getWidth()/2, getHeight()/2 - endMsg.getHeight()/2);
        }
    }
    
    //helper methods for data in the main class
    public boolean getGameOver(){
        return gameOver;
    }

    public void addMonster(Monster monster){
        monsterList.add(monster);
    }

    public void removeMonster(Monster monster){
        monsterList.remove(monster);
    }

    public void addLoot(Collectible loot){
        lootList.add(loot);
    }

    public void removeLoot(Collectible loot){
        lootList.remove(loot);
    }
}
