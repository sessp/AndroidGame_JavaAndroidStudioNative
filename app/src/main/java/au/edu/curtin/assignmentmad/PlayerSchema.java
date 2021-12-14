package au.edu.curtin.assignmentmad;


public class PlayerSchema
{

    /* Basic schema class associated with the player database. */
    public static class PlayerTable
    {
        public static final String NAME = "PlayerTable";
        public static final int VERSION = 1;
        public static class Cols
        {
            public static final String JSON = "json";
        }
    }

}
