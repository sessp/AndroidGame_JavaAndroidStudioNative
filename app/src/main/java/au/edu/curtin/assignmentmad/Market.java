package au.edu.curtin.assignmentmad;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class Market extends AppCompatActivity
{
    /*
    * Since we're mostly doing the required functionality in a Fragment this activity is basically just used for starting that fragment, and I added a back
    * button since it had to do something else.
    * */

    private TextView t1;
    private Button backB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.market_layout);
        backB = (Button)findViewById(R.id.backB);

        //Back button function is the same in all activities. It just called finish(), which tells the activity to finish/stop and the system
        //reverts back to the previous activity.
        backB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
               finish();
            }
        });

        FragmentManager fm = getSupportFragmentManager();
        MyOtherFragment frag1 = (MyOtherFragment) fm.findFragmentById(R.id.rcFrame);
        if(frag1 == null)
        {
            frag1 = new MyOtherFragment();
            fm.beginTransaction().add(R.id.rcFrame, frag1).commit();
        }
    }

    public static Intent getIntent(Context c, String textToP)
    {
        Intent intent = new Intent(c,Market.class);
        intent.putExtra("au.edu.curtin.tag",textToP);
        return intent;
    }

}

