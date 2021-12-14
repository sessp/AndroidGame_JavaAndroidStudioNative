package au.edu.curtin.assignmentmad;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


/* Basic DBHelper class responsible for creating the Player Database. */
public class PlayerDbHelp extends SQLiteOpenHelper {

    public PlayerDbHelp(Context c)
    {
        super(c,PlayerSchema.PlayerTable.NAME,null,PlayerSchema.PlayerTable.VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL("CREATE TABLE " + PlayerSchema.PlayerTable.NAME +  "(" + " _id integer PRIMARY KEY AUTOINCREMENT , " + PlayerSchema.PlayerTable.Cols.JSON + ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {}
}
