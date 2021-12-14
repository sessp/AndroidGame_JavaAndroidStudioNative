package au.edu.curtin.assignmentmad;


public class Item
{
    /* Class for Items. Structure as indicated on the Assignment Sheet. */
    private String description;
    private int price;

    //see if this works

    public Item(int p, String s)
    {
        price = p;
        description = s;
    }

    public int getPrice() {return price;}
    public String getDescription() {return description;}

    public void useItem(){}

}
