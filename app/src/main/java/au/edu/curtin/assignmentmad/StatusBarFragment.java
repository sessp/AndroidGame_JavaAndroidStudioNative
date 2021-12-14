package au.edu.curtin.assignmentmad;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class StatusBarFragment extends Fragment {

    private TextView h;
    private TextView c;
    private TextView m;
    private TextView s;

    /* Status bar fragment. Responsible for the status bar. Not much else to add, basic concepts/implementation */
    @Override
    public View onCreateView(LayoutInflater i, ViewGroup ui, Bundle b) {
        View view = i.inflate(R.layout.statusbar_layout, ui, false);
        h = (TextView) view.findViewById(R.id.textView4);
        c = (TextView) view.findViewById(R.id.textView5);
        m = (TextView) view.findViewById(R.id.textView6);
        s = (TextView) view.findViewById(R.id.textView7);
        return view;
    }

    public void updateDisplay(GameData g)
    {
        if(g.isDead(g.getCurrentPlayer().getHealth()))
        {
            s.setText("Lost");
        }
        else if(g.hasWon())
        {
            s.setText("Won!");
        }
        else
            {
                String health = Double.toString(g.getCurrentPlayer().getHealth());
                String cash = Double.toString(g.getCurrentPlayer().getCash());
                String mass = Double.toString(g.getCurrentPlayer().getMass());
                h.setText(health);
                c.setText(cash);
                m.setText(mass);
        }

    }


}
