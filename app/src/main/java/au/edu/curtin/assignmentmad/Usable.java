package au.edu.curtin.assignmentmad;

public class Usable extends Equipment
{
    /* Used to determine if an item, or more specifically a piece of equipment, is usable or not. */
    boolean used;
    public Usable(int p, String s, double m) {
        super(p, s, m);
        used = false;
    }

    public void setUsed(){used = true;}

    @Override
    public void useItem() {}

}
