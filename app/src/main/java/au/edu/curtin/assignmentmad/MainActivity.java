package au.edu.curtin.assignmentmad;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {


    private Button b1;
    private Button b2;
    private Button b3;
    private Button b4;
    private TextView t1;
    private TextView t2;
    private StatusBarFragment frag;
    private AreaInfoFragment frag1;
    private Button optionsB;
    private Button overviewBs;

    /* Main/Navigation Activity. Responsible for displaying status bar, going to options, overview and is the main activity.
     * Basic structure is GameData figuring stuff out and then the activities, including this activity, getting those calculations
      * and displaying/doing other functions with those calculations. If you want justification as to why I've done it that way look in the GameData
      * class. */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        t1 = (TextView) findViewById(R.id.textView);
        t2 = (TextView) findViewById(R.id.textView2);
        b1 = (Button) findViewById(R.id.button);
        b2 = (Button) findViewById(R.id.button2);
        b3 = (Button) findViewById(R.id.button3);
        b4 = (Button) findViewById(R.id.button4);
        optionsB = (Button) findViewById(R.id.optionsB);
        overviewBs = (Button)findViewById(R.id.overviewB);

        final GameData gameEngine = new GameData(MainActivity.this);

        FragmentManager fm = getSupportFragmentManager();
        frag = (StatusBarFragment) fm.findFragmentById(R.id.bar);
        frag1 = (AreaInfoFragment) fm.findFragmentById(R.id.info);
        if(frag == null)
        {
            frag = new StatusBarFragment();
            fm.beginTransaction().add(R.id.bar, frag).commit();
        }
        if(frag1 == null)
        {
            frag1 = new AreaInfoFragment();
            fm.beginTransaction().add(R.id.info, frag1).commit();
        }

        /* Button to "Move" the player. There are 4 of them. When moving, it also tells the AreaInfo + Status bar to update with
         * the relevant values.  */
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if(gameEngine.getCurrentPlayer().getHealth() > 0) {
                    gameEngine.movePlayer(1, 0);
                    double h = (gameEngine.getCurrentPlayer().getHealth());
                    String s = Double.toString(h);
                    String s1 = (Integer.toString(gameEngine.getCurrentPlayer().getRow())) + (Integer.toString(gameEngine.getCurrentPlayer().getCol()));
                    t1.setText(s);
                    t2.setText(s1);
                    frag.updateDisplay(gameEngine);
                    frag1.updateDisplay(gameEngine);
                }
            }
        });

        /* Start market/wilderness activity, based on if the current area is a town or not.  */
        optionsB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
               if(gameEngine.getCurrentArea().getTown())
               {
                   Log.d("GameData","Starting market activity!");
                   startActivity(Market.getIntent(MainActivity.this,"Launched Market Activity!"));
               }
               else if(!gameEngine.getCurrentArea().getTown())
               {
                   startActivity(Wilderness.getIntent(MainActivity.this,"Launched Wildness Activity!"));
                   Log.d("GameData","Not a town! Launching Wilderness");
               }
            }
        });

        /* Start overview activity */
        overviewBs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                    startActivity(Overview.getIntent(MainActivity.this));
            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if(gameEngine.getCurrentPlayer().getHealth() > 0) {
                    gameEngine.movePlayer(0, 1);
                    double h = gameEngine.getCurrentPlayer().getHealth();
                    String s = Double.toString(h);
                    String s1 = (Integer.toString(gameEngine.getCurrentPlayer().getRow())) + (Integer.toString(gameEngine.getCurrentPlayer().getCol()));
                    t1.setText(s);
                    t2.setText(s1);
                    frag.updateDisplay(gameEngine);
                    frag1.updateDisplay(gameEngine);
                }
            }
        });

        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if(gameEngine.getCurrentPlayer().getHealth() > 0) {
                    gameEngine.movePlayer(-1, 0);
                    double h = gameEngine.getCurrentPlayer().getHealth();
                    String s = Double.toString(h);
                    String s1 = (Integer.toString(gameEngine.getCurrentPlayer().getRow())) + (Integer.toString(gameEngine.getCurrentPlayer().getCol()));
                    t1.setText(s);
                    t2.setText(s1);
                    frag.updateDisplay(gameEngine);
                    frag1.updateDisplay(gameEngine);
                }
            }
        });

        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if(gameEngine.getCurrentPlayer().getHealth() > 0) {
                    gameEngine.movePlayer(0, -1);
                    double h = gameEngine.getCurrentPlayer().getHealth();
                    String s = Double.toString(h);
                    String s1 = (Integer.toString(gameEngine.getCurrentPlayer().getRow())) + (Integer.toString(gameEngine.getCurrentPlayer().getCol()));
                    t1.setText(s);
                    t2.setText(s1);
                    frag.updateDisplay(gameEngine);
                    frag1.updateDisplay(gameEngine);
                }
            }
        });
    }
}
