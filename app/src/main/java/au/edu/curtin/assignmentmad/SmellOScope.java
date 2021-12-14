package au.edu.curtin.assignmentmad;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class SmellOScope extends AppCompatActivity
{
    /* Another activity thats main function is to create a fragment and have a back button function.  */
    Button backB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.smell_layout);

        backB = (Button)findViewById(R.id.bb);

        backB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                finish();
            }
        });


        FragmentManager fm = getSupportFragmentManager();
        SmellFragment frag1 = (SmellFragment) fm.findFragmentById(R.id.rcSmell);
        if(frag1 == null)
        {
            frag1 = new SmellFragment();
            fm.beginTransaction().add(R.id.rcSmell, frag1).commit();
        }
    }

    public static Intent getIntent(Context c)
    {
        Intent intent = new Intent(c,SmellOScope.class);
        return intent;
    }

}
