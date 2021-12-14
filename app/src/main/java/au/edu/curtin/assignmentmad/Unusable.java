package au.edu.curtin.assignmentmad;

public class Unusable extends Equipment
{
    /* Used to determine if an item, or more specifically a piece of equipment, is usable or not. */
    boolean found; //Used later on for the win condition.
    public Unusable(int p, String s, double m) {
        super(p, s, m);
        found = false;
    }
    public void setFound() {found = true;}

    @Override
    public void useItem() {}

}
