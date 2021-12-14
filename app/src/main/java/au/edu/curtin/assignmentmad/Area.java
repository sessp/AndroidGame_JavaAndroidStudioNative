package au.edu.curtin.assignmentmad;

import android.util.Log;

import java.util.LinkedList;
import java.util.List;

public class Area {
    private boolean isTown;
    private List<Item> items;
    private String description;
    private boolean starFlag;
    private boolean exploredFlag;


    //Alternate Constructor, just takes in a boolean that indicates if it's a town or not.
    public Area(boolean town) {
        items = new LinkedList<>();
        isTown = town;
        description = "";
        starFlag = false;
        exploredFlag = false;
    }

    //Add an Item to the list in the arena.
    public void addItem(Item i) {
        items.add(i);
    }

    //Display items in the List. Mostly used for Testing purposes/Logging in console.
    public void displayList() {
        for (Item i : items) {
            String s = i.getDescription();
            System.out.println(s);
        }
    }

    //Gets the entire list that has the items in the area. Used in Wilderness/Market Activities to add to adapter ect.
    public List<Item> getItems() {
        return items;
    }

    //Searches the areas list for an item called searchDescription. Used to establish Player Inv items and Area Items since in the adapter they are both
    //Combined together, to make things simpler, so later on we need this method.
    public Item searchForItem(String searchDescription) {
        Item i1 = new Unusable(1, "test", 1.0);
        for (Item i : items) {
            if (i.getDescription().equals(searchDescription)) {
                i1 = i;

            }
        }
        return i1;
    }

    //Removes Item  after searching for it. Used in the sell button stuff.
    public void removeItem(String searchDescription)
    {
        Item i = searchForItem(searchDescription);
        Log.d("GameData", "Found Item: " + i.getDescription());
        items.remove(i);
    }

    //Basic Getters + Setters
    public String getDescription() {return description;}
    public boolean getTown() {return isTown;}
    public boolean getStarred() {return starFlag;}
    public boolean getExplored() {return exploredFlag;}

    public void setDescription(String s) {description = s;}
    public void setExplored() {exploredFlag = true;}
    public void setStarred()
    {
        if(starFlag == false)
        {
            starFlag = true;
        }
        else if(starFlag == true)
        {
            starFlag = false;
        }
    }
}
