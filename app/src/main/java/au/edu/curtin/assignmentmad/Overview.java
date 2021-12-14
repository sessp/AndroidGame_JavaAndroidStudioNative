package au.edu.curtin.assignmentmad;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class Overview extends AppCompatActivity
{
    /* Basic activity that's responsible for creating the Overview Fragment (MyGridLayout). Not much to comment on.  */
    private Button backB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.overview_layout);
        backB = (Button)findViewById(R.id.leaveButton);

        backB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                finish();
            }
        });


        FragmentManager fm = getSupportFragmentManager();
        MyGridFragment frag1 = (MyGridFragment) fm.findFragmentById(R.id.rcFrame);
        if(frag1 == null)
        {
            frag1 = new MyGridFragment();
            fm.beginTransaction().add(R.id.rcGrid, frag1).commit();
        }
    }

    public static Intent getIntent(Context c)
    {
        Intent intent = new Intent(c,Overview.class);
        return intent;
    }

}
