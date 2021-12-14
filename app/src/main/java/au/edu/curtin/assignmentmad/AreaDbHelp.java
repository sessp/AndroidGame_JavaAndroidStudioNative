package au.edu.curtin.assignmentmad;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class AreaDbHelp extends SQLiteOpenHelper {

    public AreaDbHelp(Context c)
    {
        super(c,AreaSchema.AreaTable.NAME,null,AreaSchema.AreaTable.VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL("CREATE TABLE " + AreaSchema.AreaTable.NAME +  "(" + " _id integer PRIMARY KEY AUTOINCREMENT, "+ AreaSchema.AreaTable.Cols.JSON1  + ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {}
}
