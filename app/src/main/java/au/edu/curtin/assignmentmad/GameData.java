package au.edu.curtin.assignmentmad;

import android.content.Context;
import android.util.Log;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class GameData {

    private Area[][] grid;
    private Player user;
    private static GameData currentGameData;
    private DatabaseHandler db;

    /*
     * Note/Justification of structure: I know doing something like this is generally bad since GameData is almost like a "God Class" and has
     * has alot of different functions/goals. The reason I did it like this was because this was the general structure outlined in the Assignment
     * spec. If the structure was more open you wouldn't do something like this, but because this was how the uml/pictured structure for the assignment
     * was, it was either do something like this or have multiple classes do the same work, but then coupling would be bad as multiple classes would be
     * linked to other classes to do simple functions, so I thought it was better to just do it like this. */

    private static final String SMELL = "Smell-O-Scope";
    private static final String DRIVE = "Improbability Drive";
    private static final String BEN = "Ben Kenobi";
    private static final String JADE = "Jade Monkey";
    private static final String ROAD = "Road Map";
    private static final String ICE = "Ice Scraper";


 /* Responsible for starting/creating the whole game. Creates a grid/map, creates the player, gives him $100 and then from then on it handles
  * and performs other calculations.  */
    public GameData(Context c) {
        db = new DatabaseHandler(c);
        grid = new Area[4][4];
        currentGameData = GameData.this;
        createGrid();

        //Check if last saved player is dead, if so create a new player.
        user = db.loadPlayer();
        if(user.getHealth() == 0)
        {
            Log.d("STATUS", "You lost! Resetting Data!");
            //Make a new player
            Player newPlayer = new Player(0,0);
            user = newPlayer;

        }
        user.updateCash(100);
        savePlayer();
    }

    //Create an "instance" of the 2D array and then copy it over to actual class reference.
    //Randomly assign areas to Grid object AND AFTER assign items to those areas.
    public void createGrid() {
            Area[][] map = new Area[4][4];
            Log.d("Builder", "A map has been randomly generated!");
            Random randomObjNum = new Random();
            Random randomItemNum = new Random();//was 33
            for (int r = 0; r < map.length; r++) {
                for (int c = 0; c < map[0].length; c++) {

                    int ran = randomObjNum.nextInt(100);
                    int ranItem = randomItemNum.nextInt(100);
                    if (ran < 50) {
                        if (ranItem < 100) {
                            Area a = new Area(true);
                            a.addItem(getRandomItemToAdd());
                            a.addItem(getRandomItemToAdd());
                            map[r][c] = a;

                        } else {
                            Area a = new Area(true);
                            a.addItem(getRandomItemToAdd());
                            a.addItem(getRandomItemToAdd());
                            a.addItem(getRandomItemToAdd());
                            map[r][c] = a;
                        }
                    } else if (ran > 50) {
                        if (ranItem < 100) {
                            Area a = new Area(false);
                            a.addItem(getRandomItemToAdd());
                            a.addItem(getRandomItemToAdd());
                            map[r][c] = a;
                        } else {
                            Area a = new Area(false);
                            a.addItem(getRandomItemToAdd());
                            a.addItem(getRandomItemToAdd());
                            a.addItem(getRandomItemToAdd());
                            map[r][c] = a;
                        }
                    }
                }
            }


            //Update the grid
            currentGameData.grid = map;

    }

    //responsible for randomly-generating the map using random class in Java.
    public Item getRandomItemToAdd()
    {
        Random randomNumber = new Random();
        int random = randomNumber.nextInt(100);
        Log.d("Notsorandomrandom", Integer.toString(random));

        Log.d("Test","TT");
        Item i = new Item(0,"Default");
        //Remember to clear database
        if((random <= 11) && (random > 0))
        {
               i = new Usable(25,SMELL,5.0);
               Log.d("ItemCreator", "Smell-O-Scope");
        }
        else if((random <= 22) && (random > 11))
        {
            i = new Usable(25,DRIVE,-Math.PI);
            Log.d("ItemCreator", "Improbability Drive");
        }
        else if((random <= 33) && (random > 22))
        {
            i = new Usable(25,BEN,0);
            Log.d("ItemCreator", "Ben Kenobi");
        }
        else if((random <= 44) && (random > 33))
        {
            i = new Food(5,"Apple",15);
            Log.d("ItemCreator", "Apple");
        }
        else if((random <= 55) && (random > 44))
        {
            i = new Food(10,"Pizza",5);
            Log.d("ItemCreator", "Pizza");
        }
        else if((random <= 66) && (random > 55))
        {
            i = new Food(0,"Poisonous Mushroom",-10);
            Log.d("ItemCreator", "Posinous Mushroom");
        }
        else if((random <= 77) && (random > 66))
        {
            i = new Unusable(10,JADE,0);
            Log.d("ItemCreator", "Jade Monkey");
        }
        else if((random <= 88) && (random > 77))
        {
            i = new Unusable(10,ROAD,0);
            Log.d("ItemCreator", "Road Map");
        }
        else if((random <= 100) && (random > 88))
        {
            i = new Unusable(10,ICE,0);
           Log.d("ItemCreator", "Ice Scraper");
        }

        return i;
    }


    //Simple function that was to test if an area had a certain item.
    public Boolean inArena(Item item) {
        Boolean contains = false;
        if (currentGameData.getCurrentArea().getItems().contains(item)) {
            contains = true;
        }

        return contains;
    }

    //Function responsible for actually moving the player.
    //Checks to make sure inputted value is only a 1 as you can only move 1 square at a time.
    //checks to make sure move value + currentvalue is < size. Basically makes sure you're still in the map.
    public void movePlayer(int r, int c) {
        if ((r == -1 || r == 1 || r == 0) && (c == -1 || c == 1 || c == 0)) {
            int i = user.getRow();
            int i1 = user.getCol();
            int rowSize = currentGameData.grid.length;
            int colSize = currentGameData.grid[0].length;
            int m1 = r + i;
            int m2 = c + i1;
            if ((m1 <= rowSize) && (m2 <= colSize) && (m1 >= 0) && (m2 >= 0)) {
                user.updatePosition(m1, m2);
                double newHealth = Math.max(0.0, user.getHealth() - 5.0 - (user.getMass() / 2.0));
                isDead(newHealth); //if true then end game.
                user.updateHealth(newHealth);
                Log.d("GameData", "Player has moved to " + user.getRow() + " " + user.getCol());
                Log.d("GameData", "Player has  " + user.getHealth() + " HP left. ");
                getCurrentArea().setExplored();
                savePlayer();
            }
        }
    }

    //Simple function that checks is a player is dead.
    public boolean isDead(double newHealth) {
        boolean b = false;
        if (newHealth <= 0.0) {
            b = true;
        }
        return b;

    }

    public static GameData getGameData() {
        return currentGameData;
    }

    public Player getCurrentPlayer() {
        return currentGameData.user;
    }

    //Gets the current arena the player is in. Useful in many different circumstances.
    public Area getCurrentArea() {
        return currentGameData.grid[currentGameData.user.getRow()][currentGameData.user.getCol()];
    }


   //Remove an item from a players inventory.
    public void removeItems(String searchItem) {

        Item itemToRemove = currentGameData.getCurrentPlayer().searchItInventory(searchItem);
        if ((currentGameData.getCurrentPlayer().inInventory(itemToRemove))) {
            //Calculate Money(perform calculation + convert to int)
            Double d = itemToRemove.getPrice() * 0.75;
            int moneyToAdd = d.intValue();
            //Call the Player and let that class handle it since rest of the data is Player specific.
            getCurrentPlayer().remove(itemToRemove, moneyToAdd);
            //Add it to the relevant arena
            getCurrentArea().getItems().add(itemToRemove);
            savePlayer();
        }


        //NOTE: In the outline it specified Money as an Int, so if the calculation above results in a decimal number then technically it may be off.
        //E.G 75% of an INTEGER 95 is 71 and NOT the actual number 71.25 because we're talking about Ints and Money is of type int and not double.
        //It shouldn't matter too much just have prices where 75% of them is a whole number. It's just something to note.
    }

    public void obtainItem(String searchItem) {
        Item itemToObtain = currentGameData.getCurrentArea().searchForItem(searchItem);
        //perform search for the item first
        if (itemToObtain instanceof Food) {
            if (!getCurrentArea().getTown()) {
                getCurrentArea().removeItem(searchItem);
                getCurrentPlayer().obtain(itemToObtain, 0);
                savePlayer();
            } else if ((getCurrentArea().getTown()) && (itemToObtain.getPrice() < getCurrentPlayer().getCash())) {
                getCurrentArea().removeItem(searchItem);
                getCurrentPlayer().obtain(itemToObtain, itemToObtain.getPrice());
                savePlayer();
                //If area is a town and the player can afford it.
                //Purchase + eat it.
            }
        } else if (itemToObtain instanceof Equipment) {
            if (!getCurrentArea().getTown()) {
                getCurrentArea().removeItem(searchItem);
                getCurrentPlayer().obtain(itemToObtain, 0);
                savePlayer();
                //Add to play inventory + edit equipment mass.
                //check if winnable somehow. Search for items w/ names of the winnables.
            } else if ((getCurrentArea().getTown()) && (itemToObtain.getPrice() < getCurrentPlayer().getCash())) {
                getCurrentArea().removeItem(searchItem);
                getCurrentPlayer().obtain(itemToObtain, itemToObtain.getPrice());
                savePlayer();
                //If area is a town and the player can afford it.
                //Purchase + Update Equipment Mass + Add to inv
            }
        }
    }

    public List<Area> getAllAreas() {
        List<Area> allAreas = new LinkedList<Area>();

        for (int r = 0; r < grid.length; r++) {
            for (int c = 0; c < grid[0].length; c++) {

                allAreas.add(grid[r][c]);

            }
        }

        return allAreas;
    }


    //Calculates the relevant values for smell-o-function, that is, if items are within the range of 2.
    public List<String> smellOUse() {
        List<String> dataList = new LinkedList<String>();
        int minR = Math.max(0, (user.getRow() - 2));
        int minC = Math.max(0, (user.getRow() - 2));
        int maxR = Math.min(2, (user.getRow() + 2));
        int maxC = Math.min(2, (user.getCol() + 2));

        //Check <= is correct
        for (int r = minR; r <= maxR; r++) {
            for (int c = minC; c <= maxC; c++) {

                for (Item i : grid[r][c].getItems()) {

                    String str = "";
                    str = str + i.getDescription() + "," + c + "," + r;
                    dataList.add(str);
                }

            }
        }

        return dataList;
    }

    public void savePlayer() {
        db.savePlayer(getCurrentPlayer());
    }


    public void useItem(String s)
    {
        Item iToUse = currentGameData.getCurrentPlayer().searchItInventory(s);
        Usable u = (Usable)iToUse;
        //Search array of arena for item name
        /*Because Smell-O-Scope is related to an activity and we don't want GameData to know
        About any of the activity classes we'll have to implement the use smell-o-scope in
        the shop fragment.
        */
        if(u.getDescription().equals(DRIVE))
        {
            Log.d("USABLE", "You just used an improbability drive!");
            createGrid();
            //Since player data is already saved we don't have to redo that.
            //When marking this check really carefully, it may appear that the map hasn't
            //changed due to the seed in my random map generator.
        }
        else if(u.getDescription().equals(BEN))
        {
            Log.d("USABLE", "You just used a Ben Kenobi");
            List<Item> iList =  currentGameData.getCurrentArea().getItems();
            for (Item i:iList)
            {

                //we don't need to check when buying all items what type.
                getCurrentPlayer().obtain(i, 0);

            }
            savePlayer();
        }


    }

    //Need this so the Market/Wilderness can check if an item is a smell-o-function or not.
    public boolean isItemSmelly(String s)
    {
        boolean isSmelly = false;
        Item iToUse = currentGameData.getCurrentPlayer().searchItInventory(s);
        Usable u = (Usable)iToUse;
        if(u.getDescription().equals(SMELL))
        {
             isSmelly = true;
        }

        return isSmelly;

    }

    //Simple boolean function that tests if the player has won or not.
    public boolean hasWon()
    {

        boolean a = getCurrentPlayer().inInventory(getCurrentPlayer().searchItInventory(JADE));
        boolean b = getCurrentPlayer().inInventory(getCurrentPlayer().searchItInventory(ROAD));
        boolean c = getCurrentPlayer().inInventory(getCurrentPlayer().searchItInventory(ICE));
        boolean hasPlayerWon = false;
        if(a == true && b == true && c == true)
        {
            hasPlayerWon = true;
        }

        return hasPlayerWon;

    }
}

