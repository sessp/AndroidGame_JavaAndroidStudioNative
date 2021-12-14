package au.edu.curtin.assignmentmad;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class Player
{
    /* Player class as documented in the Assignment Specification.  */
    //Fields
    private int rowLocation;
    private int colLocation;
    private int cash;
    private double health;
    private double equipMass;
    private List<Equipment> equipment;

    //Alternate Constructor
    public Player(int r, int c)
    {
        rowLocation = r;
        colLocation = c;
        health = 100.0;
        equipMass = 0.0;
        equipment = new ArrayList<Equipment>();
    }

    //Getters/Setters
    public int getCash() {return cash;}
    public int getRow() {return rowLocation;}
    public int getCol() {return colLocation;}
    public List<Equipment> getInventory() {return equipment;}
    public double getHealth() {return health;}
    public double getMass() {return equipMass;}

    public void removeEquipment(Equipment e)
    {
        equipment.remove(e);
    }
    public void addEquipment(Equipment e)
    {
        equipment.add(e);
        System.out.println(e.getDescription() + "" + " was added to the list!"); //testing purposes.
    }

    public void updateCash(int c)
    {
        cash = c;
    }

    public void updateHealth(double h)
    {
        health = h;
    }

    //When updating position check that its still in range.
    public void updatePosition(int r, int c)
    {
        if((r<4) && (c<4))
        {

            rowLocation = r;
            colLocation = c;

        }
    }

    public void editMass(double m)
    {
        equipMass = m;
        //where to do the mass calculations?!?!?
    }

    //Checks if an item is in the Players inventory. Used for the Market/Wilderness Stuff.
    public Boolean inInventory(Item item)
    {
        Boolean contains = false;
        if(getInventory().contains(item))
        {
            contains = true;
        }

        return contains;
    }

    //Again, just like the Area search function, it searches the equipment/inventory for a description of an item.
    public Item searchItInventory(String searchDescription)
    {
        Item i1 = new Unusable(1, "test",1.0);
        for(Item i: equipment)
        {
            if(i.getDescription().equals(searchDescription))
            {
                i1 = i;

            }
        }
        return i1;
    }

    //Used to buy/pickup an item. Had all the data manipulation with the players fields here because I didn't want GameData to do too much.
    public void obtain(Item i,int price)
    {
        if(i instanceof Food)
        {
            updateCash(cash - price);
            Log.d("GameData","You have bought an food for " + price + " .You're current balance is: " + getCash());
            health = getHealth() + ((Food) i).getHealth();
        }
        else if(i instanceof Equipment)
        {
            updateCash(cash - price);
            Log.d("GameData","You have obtained an Equipment for " + price + " .You're current balance is: " + getCash());
            editMass((((Equipment) i).getMass() + getMass()));
            Log.d("GameData","You're mass is now: " + getMass());
            Equipment e = (Equipment)i;
            addEquipment(e);
        }
    }

    public void remove(Item itemToRemove, int moneyToAdd)
    {
        Equipment eq = (Equipment)itemToRemove;
         double newMass = getMass() - eq.getMass();
        updateCash(moneyToAdd + getCash());
        editMass(newMass);
        removeEquipment(eq);
        Log.d("GameData", "Item: " + itemToRemove.getDescription() + "has been removed from your INV. You're balance is now: " + getCash() + " .You're equipMass is now: " + getMass());

    }

}
