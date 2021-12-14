package au.edu.curtin.assignmentmad;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.CursorWrapper;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.LinkedList;
import java.util.List;

//Try just area and if not working try storing AreaArray as a List
public class AreaModel {

    public class AreaCursor extends CursorWrapper {
        public AreaCursor(Cursor c) {
            super(c);
        }

        public GameData getGameData() {
            String j = getString(getColumnIndex(AreaSchema.AreaTable.Cols.JSON1));
            Gson gson1 = new Gson();
            GameData g = gson1.fromJson(j, GameData.class);
            //have 2 fields, 1 for list another for pft:D

            return g;
        }
    }

    private SQLiteDatabase areaDatabase;

    public AreaModel(Context context) {
        this.areaDatabase = new AreaDbHelp(context.getApplicationContext()).getWritableDatabase();
    }

    public void addArea(GameData g) {
        //clearAll(); //May want to remove?
        //We only want one row so clear the data first :D
        ContentValues cv = new ContentValues();
        //do packing here
        Gson gson = new Gson();
        //changing to list, see if that works.
        //try different gson?
        String j = gson.toJson(g, GameData.class);
        cv.put(AreaSchema.AreaTable.Cols.JSON1, j);
        areaDatabase.insert(AreaSchema.AreaTable.NAME, null, cv);
        //viewDatabase();
    }

    public void clearAll() {
        areaDatabase.delete(AreaSchema.AreaTable.NAME, null, null);
    }

    //We can be sneaky with this query call since we know there is only one row, so we don't need to do any validation/conditional statements.
    public GameData loadIntoList()
    {
        AreaModel.AreaCursor ac = new AreaModel.AreaCursor(areaDatabase.query(AreaSchema.AreaTable.NAME,null,null,null,null,null,null,null));
        GameData g = ac.getGameData();
        while(!ac.isAfterLast())
        {
            g = ac.getGameData();
            ac.moveToNext();
        }
        ac.close();

        return g;
    }

    public boolean isMapEmpty()
    {
        boolean isEmpty = true;
        AreaModel.AreaCursor ac = new AreaModel.AreaCursor(areaDatabase.query(AreaSchema.AreaTable.NAME,null,null,null,null,null,null,null));
        if(ac.getCount() > 0)
        {
            isEmpty = false;
        }
        return isEmpty;
    }

    //noUpdateStatement

}

