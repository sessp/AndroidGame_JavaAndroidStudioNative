package au.edu.curtin.assignmentmad;

import android.content.Context;

import java.util.List;

public class DatabaseHandler
{
    /* This class is suppose to act as a middle man between the Databases and GameData as I didn't want GameData to also have direct access to the DB's
     * because it would be too much of a "God" class and wouldn't help Coupling ect.
      * So the Role of this class is to basically, once GameData has given it the data, it Updates the databases, creates them, ect all of that. */
    PlayerModel pDatabase;
    AreaModel aDatabase;
    Context databaseContext;
    public DatabaseHandler(Context c)
    {
        databaseContext = c;
        pDatabase = new PlayerModel(databaseContext);
        aDatabase = new AreaModel(databaseContext);
        //Create Area one later
    }


    //Because we know the structure of the DB is only 1 row we can be a bit sneaky and don't need to perform validation.
    public Player loadPlayer()
    {
        return pDatabase.grabData();
    }
    public void savePlayer(Player currentUser)
    {
        pDatabase.addPlayer(currentUser);
    }
}
