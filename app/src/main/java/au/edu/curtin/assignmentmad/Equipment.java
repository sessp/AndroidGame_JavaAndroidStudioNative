package au.edu.curtin.assignmentmad;


public class Equipment extends Item
{
    /* Class for Equipment/Items. Structure as indicated on the Assignment Sheet. */
    private double mass;


    public Equipment(int p, String s, double m) {
        super(p, s);
        mass = m;
    }

    public double getMass() {return mass;}

     @Override
     public void useItem()
     { }

}

