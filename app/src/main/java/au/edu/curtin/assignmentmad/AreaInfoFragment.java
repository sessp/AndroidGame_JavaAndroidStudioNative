package au.edu.curtin.assignmentmad;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class AreaInfoFragment extends Fragment
{
    private TextView type;
    private TextView description;
    private Button star;
    private GameData gameEngine;

    /*
     * Not going to go into too much description for this frag. Basically it creates the ArenaInfo function using the current instance of GameData.
     * Real simple stuff.
      * */
    @Override
    public View onCreateView(LayoutInflater i, ViewGroup ui, Bundle b) {
        View view = i.inflate(R.layout.areainfo_layout, ui, false);
        gameEngine = GameData.getGameData();
        type = (TextView) view.findViewById(R.id.areaType);
        description = (TextView) view.findViewById(R.id.descriptionDisplay);
        star = (Button) view.findViewById(R.id.starButton);

        star.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                gameEngine.getCurrentArea().setStarred();
                Log.d("GameData","isStarred: " + gameEngine.getCurrentArea().getStarred());
            }
        });

        return view;
        }


    public void updateDisplay(GameData g)
    {
        if(g.getCurrentArea().getTown())
        {
            type.setText("Town");
            description.setText(g.getCurrentArea().getDescription());
        }
        else
        {
            type.setText("Wilderness");
            description.setText(g.getCurrentArea().getDescription());
        }

    }

}
