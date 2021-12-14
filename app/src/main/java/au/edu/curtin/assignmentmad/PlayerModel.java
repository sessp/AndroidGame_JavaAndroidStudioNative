package au.edu.curtin.assignmentmad;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.CursorWrapper;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.google.gson.Gson;

/* The reason I didn't add individual columns for each field and do it like that is because
 * Dave said he didn't care too much on how it was implemented and ontop of that it would be easier as I wouldn't have to rebuild the object
  *
  * So the way I opted to do it was, using Gson, serialize the Player object, add the string to database, then when getting it
  * get the string and then rebuild it. Alot easier than having to strip down a player object field by field and then having to rebuild it.
  * */
public class PlayerModel {
    public class PlayerCursor extends CursorWrapper {
        public PlayerCursor(Cursor c) {
            super(c);
        }

        public Player getPlayer() {
            String j = getString(getColumnIndex(PlayerSchema.PlayerTable.Cols.JSON));
            Gson gson = new Gson();
            Player p = gson.fromJson(j, Player.class);

            return p;
        }
    }

    private SQLiteDatabase playerDatabase;

    public PlayerModel(Context context) {
        this.playerDatabase = new PlayerDbHelp(context.getApplicationContext()).getWritableDatabase();
    }

    /*The reason I'm clearing before I add is because if I do this the add function can act as an update function, making things less redundant
     and also because I had issues implementing the update statement. But it makes sense since there is only one row. */
    public void addPlayer(Player p) {
        clearAll();
        ContentValues cv = new ContentValues();
        Gson gson = new Gson();
        String j = gson.toJson(p, Player.class);
        cv.put(PlayerSchema.PlayerTable.Cols.JSON, j);
        playerDatabase.insert(PlayerSchema.PlayerTable.NAME, null, cv);
        viewDatabase();
    }

    public void clearAll() {
        playerDatabase.delete(PlayerSchema.PlayerTable.NAME, null, null);
    }

    //We can be sneaky with this query call since we know there is only one row, so we don't need to do any validation/conditional statements.
    public Player grabData()
    {
        Player playerData = new Player(0,0);
        PlayerCursor pc = new PlayerCursor(playerDatabase.query(PlayerSchema.PlayerTable.NAME,null,null,null,null,null,null,null));
        try
        {
            pc.moveToFirst();
            while(!pc.isAfterLast())
            {
                playerData = pc.getPlayer();
                pc.moveToNext();
            }
        }
        finally
        {
            pc.close();
        }

        return playerData;
    }

    //Used for testing purposes/logging.
    public void viewDatabase()
    {
        PlayerCursor pc = new PlayerCursor(playerDatabase.query(PlayerSchema.PlayerTable.NAME,null,null,null,null,null,null,null));
        try
        {
            pc.moveToFirst();
            while(!pc.isAfterLast())
            {
                Player p = pc.getPlayer();
                Log.d("DatabaseReading", "" + " Health: " + p.getHealth() + " Cash: " + p.getCash());
                pc.moveToNext();
            }
        }
        finally
        {
            pc.close();
        }

    }

}
