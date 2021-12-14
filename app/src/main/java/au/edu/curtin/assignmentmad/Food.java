package au.edu.curtin.assignmentmad;


public class Food extends Item
{
    /* Class for Food/Items. Structure as indicated on the Assignment Sheet. */
    private double health;
    public Food(int p, String s, double h)
    {
        super(p, s);
        health = h;
    }

    public double getHealth() {return health;}
}
