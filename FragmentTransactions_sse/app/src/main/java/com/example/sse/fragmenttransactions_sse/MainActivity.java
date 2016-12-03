package com.example.sse.fragmenttransactions_sse;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;


/**
 *  Comments added by the A-Team explaining the changes made will be in documentation comments
 *  like this one.
 *
 *  Changed the class to "extends Activity"
 * */
public class MainActivity extends Activity{


    //Two basic ways of working with fragments.
    //
    //1. Just include them in the Activity's layout.
    //
    //2. Instantatiate and work with them in code.
    // in code you have much more control.

    //3. create objects to reference the views, including fragments.
private
    Frag_One  f1;
    Frag_Two  f2;
    Frag_Three  f3;

    FragmentManager fm;  // we will need this later.

    private Button btnFrag1;
    private Button btnFrag2;
    private Button btnFrag3;
    private LinearLayout FragLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    //4. get references for our views.

        btnFrag1 = (Button) findViewById(R.id.btnFrag1);
        btnFrag2 = (Button) findViewById(R.id.btnFrag2);
        btnFrag3 = (Button) findViewById(R.id.btnFrag3);
        FragLayout = (LinearLayout) findViewById(R.id.FragLayout);

        //f1 = (Frag_One) findViewById(R.id.frag1);  //Hey, why won't this work for fragments?  Wait does the fragment even exist?

    //5a.  We actually have to create the fragments ourselves.  Where is our friend, R?
        f1 = new Frag_One();
/**
 *  Initializing f2 & f3 here is unnecessary, so I commented them out.  No major reason,
 *  beyond my personal preference to not instantiate before they're needed.
 */
        //f2 = new Frag_Two();
        //f3 = new Frag_Three();

    //5b. Grab a reference to the Activity's Fragment Manager, Every Activity has one!
       fm = getFragmentManager ();  //that was easy.
         //fm = getSupportFragmentManager();  // **//use this call instead, if your activity extends AppCompatActivity


    //5c. Now we can "plop" fragment(s) into our container.
        FragmentTransaction ft = fm.beginTransaction ();  //Create a reference to a fragment transaction.
        ft.add(R.id.FragLayout, f1, "myFrag1");  //now we have added our fragement to our Activity programmatically.  The other fragments exist, but have not been added yet.
        ft.addToBackStack ("myFrag1");  //why do we do this?
        ft.commit ();  //don't forget to commit your changes.  It is a transaction after all.

        btnFrag1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFrag1();
            }
        });

        btnFrag2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFrag2();
            }
        });

        btnFrag3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFrag3();
            }
        });

    }

    public void showFrag1() {
        //f1 = (Frag_One) fm.findFragmentByTag("tag1");   //what should we do if f1 doesn't exist anymore?  How do we check and how do we fix?
        FragmentTransaction ft = fm.beginTransaction ();  //Create a reference to a fragment transaction.
/**
 *  I used the hide() & show() methods for all 3 fragments.
 *
 *  Instead of simply trying to hide the other fragments, you need to check to make
 *  sure they aren't null before trying to hide them.
 *
 *  Check that the fragment you want is not null before trying to show it.
 *  If it is null, instantiate it.
 *
 **/
        if (f2 != null) { ft.hide(f2); }
        if (f3 != null) { ft.hide(f3); }
        if (f1 != null) {
            f1 = (Frag_One) fm.findFragmentByTag("myFrag1");
            ft.show(f1);
        } else {
            f1 = new Frag_One();
            ft.add(R.id.FragLayout, f1, "myFrag1");
            ft.addToBackStack("myFrag1");
        }
        ft.commit();
    }

    public void showFrag2() {
        FragmentTransaction ft = fm.beginTransaction ();  //Create a reference to a fragment transaction.
        if (f1 != null) { ft.hide(f1); }
        if (f3 != null) { ft.hide(f3); }
        if (f2 != null) {
            f2 = (Frag_Two) fm.findFragmentByTag("myFrag2");
            ft.show(f2);
        } else {
            f2 = new Frag_Two();
            ft.add(R.id.FragLayout, f2, "myFrag2");
            ft.addToBackStack("myFrag2");
        }
        ft.commit();
    }


    public void showFrag3() {
        FragmentTransaction ft = fm.beginTransaction ();  //Create a reference to a fragment transaction.
        if (f1 != null) { ft.hide(f1); }
        if (f2 != null) { ft.hide(f2); }
        if (f3 != null) {
            f3 = (Frag_Three) fm.findFragmentByTag("myFrag3");
            ft.show(f3);
        } else {
            f3 = new Frag_Three();
            ft.add(R.id.FragLayout, f3, "myFrag3");
            ft.addToBackStack("myFrag3");
        }
        ft.commit();
    }
}
