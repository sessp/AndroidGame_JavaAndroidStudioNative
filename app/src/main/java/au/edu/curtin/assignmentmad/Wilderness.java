package au.edu.curtin.assignmentmad;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Wilderness extends AppCompatActivity
{
    /* Wilderness class is responsible for creating the fragment responsible for the wild activity and has a back function. */
    private TextView t1;
    private Button backB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wilderness_layout);
        backB = (Button)findViewById(R.id.leave);

        backB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
               finish();
            }
        });

        FragmentManager fm = getSupportFragmentManager();
        MyWildFragment frag1 = (MyWildFragment) fm.findFragmentById(R.id.wildernessFrame);
        if(frag1 == null)
        {
            frag1 = new MyWildFragment();
            fm.beginTransaction().add(R.id.wildernessFrame, frag1).commit();
        }
    }

    public static Intent getIntent(Context c, String textToP)
    {
        Intent intent = new Intent(c,Wilderness.class);
        intent.putExtra("au.edu.curtin.tag",textToP);
        return intent;
    }

}

