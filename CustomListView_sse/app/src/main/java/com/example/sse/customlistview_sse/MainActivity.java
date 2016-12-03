package com.example.sse.customlistview_sse;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.PhoneNumberUtils;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import java.io.File;
import java.lang.reflect.Field;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {


    private static final int MY_PERMISSION_REQUEST_PHONE = 10;
    private
    ListView lvEpisodes;
    ListAdapter lvAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lvEpisodes = (ListView) findViewById(R.id.lvEpisodes);
        lvAdapter = new MyCustomAdapter(this.getBaseContext());  //instead of passing the boring default string adapter, let's pass our own, see class MyCustomAdapter below!
        lvEpisodes.setAdapter(lvAdapter);

        /*
        lvEpisodes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String[] links = getResources().getStringArray(R.array.episode_sites);
                Toast.makeText(getBaseContext(), links[position], Toast.LENGTH_LONG).show();
                //Toast.makeText(context, episodeLinks[position] , Toast.LENGTH_LONG).show();
                //Uri uri = Uri.parse(episodeLinks[position]);
                //Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            }
        });
*/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//        return super.onCreateOptionsMenu(menu);   //get rid of default behavior.

        // Inflate the menu; this adds items to the action bar
        getMenuInflater().inflate(R.menu.my_test_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.mnu_zero) {
            Toast.makeText(getBaseContext(), "Menu Zero.", Toast.LENGTH_LONG).show();
            String url = "http://www.reddit.com";
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(url));
            startActivity(i);
            return true;
        }

        if (id == R.id.mnu_one) {

//            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
//
//
//                ActivityCompat.requestPermissions(MainActivity.this,
//                        new String[]{Manifest.permission.CALL_PHONE}, MY_PERMISSION_REQUEST_PHONE);
//                // TODO: Consider calling
//                //    ActivityCompat#requestPermissions
//                // here to request the missing permissions, and then overriding
//                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//                //                                          int[] grantResults)
//                // to handle the case where the user grants the permission. See the documentation
//                // for ActivityCompat#requestPermissions for more details.
//                //return TODO;
//                Toast.makeText(getBaseContext(), "Ring ring, Hi Mom.", Toast.LENGTH_LONG).show();
//                Intent callIntent = new Intent(Intent.ACTION_CALL);
//                callIntent.setData(Uri.parse("tel:0377778888"));
//                startActivity(callIntent);
//                return true;
//            }
//            else{
//                return false;
//            }

        }

        if (id == R.id.mnu_two) {
            Toast.makeText(getBaseContext(), "Ring ring, Hi Mom.", Toast.LENGTH_LONG).show();
            return true;
        }

        if (id == R.id.mnu_three) {
            Toast.makeText(getBaseContext(), "Ring ring, Hi Mom.", Toast.LENGTH_LONG).show();
            return true;
        }

        if (id == R.id.mnu_four) {
            Toast.makeText(getBaseContext(), "Ring ring, Hi Mom.", Toast.LENGTH_LONG).show();
            return true;
        }




        return super.onOptionsItemSelected(item);  //if none of the above are true, do the default and return a boolean.
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSION_REQUEST_PHONE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.


                    return;

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }
}




//***************************************************************//
//create a  class that extends BaseAdapter
//you will be prompted to implement methods... choose yes.
//***************************************************************//


class MyCustomAdapter extends BaseAdapter {

    private
     String episodes[];      //this is the introductary way to store the List data.  The way it's usually done is by creating
     String episodeDescriptions[];  //the "better" way is to encapsulate the list items into an object, then create an arraylist of objects.
     String DrawableResources[];     //this approach is fine for now.
     String episodeLinks[];
     //float[] ratingBar;
     SharedPreferences preferences;
     SharedPreferences.Editor editor;


//    ArrayList<String> episodes;
//    ArrayList<String> episodeDescriptions;
    ArrayList<Integer> episodeImages;



    Context context;   //What does refer to?  Context enables access to application specific resources.  Eg, spawning & receiving intents, locating the various managers.

    public MyCustomAdapter(Context aContext) {

        context = aContext;  //saving the context we'll need it again.

//initializing our data in the constructor.
//        episodes = (ArrayList<String>) Arrays.asList(aContext.getResources().getStringArray(R.array.episodes));  //retrieving list of episodes predefined in strings-array "episodes" in strings.xml
//        episodeDescriptions = (ArrayList<String>) Arrays.asList(aContext.getResources().getStringArray(R.array.episode_descriptions));  //Also casting to a friendly ArrayList.

        episodes =aContext.getResources().getStringArray(R.array.episodes);  //retrieving list of episodes predefined in strings-array "episodes" in strings.xml
        episodeDescriptions = aContext.getResources().getStringArray(R.array.episode_descriptions);  //Also casting to a friendly ArrayList.
        episodeLinks = aContext.getResources().getStringArray(R.array.episode_sites);
        //ratingBar = new float[7];

        episodeImages = new ArrayList<Integer>();   //Could also use helper function below to autoextract drawable resources, but keeping things as simple as possible.
        episodeImages.add(R.drawable.st_spocks_brain);
        episodeImages.add(R.drawable.st_arena__kirk_gorn);
        episodeImages.add(R.drawable.st_this_side_of_paradise__spock_in_love);
        episodeImages.add(R.drawable.st_mirror_mirror__evil_spock_and_good_kirk);
        episodeImages.add(R.drawable.st_platos_stepchildren__kirk_spock);
        episodeImages.add(R.drawable.st_the_naked_time__sulu_sword);
        episodeImages.add(R.drawable.st_the_trouble_with_tribbles__kirk_tribbles);
        // add next 5
        episodeImages.add(R.drawable.st_beam_me_up);
        episodeImages.add(R.drawable.st_nuclear_wessels);
        episodeImages.add(R.drawable.st_phasers);
        episodeImages.add(R.drawable.st_live_long_and_prosper);
        episodeImages.add(R.drawable.st_khan);
    }

    @Override
    public int getCount() {
//        return episodes.size();  //all of the arrays are same length, so return length of any... ick!  But ok for now. :)
        return episodes.length;  //all of the arrays are same length, so return length of any... ick!  But ok for now. :)

    }

    @Override
    public Object getItem(int position) {
//        return episodes.get(position);  //In Case you want to use an ArrayList
        return episodes[position];  //really should be retuning entire object... Crash?!?

    }

    @Override
    public long getItemId(int position) {
        return position;  //don't really use this, but have to do something since we had to implement.
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {  //convertView is Row, parent is the layout that has the row Views.
        //THIS IS WHERE THE ACTION HAPPENS.  Let's optimize a bit...
        View row;  //this will refer to the row to be inflated or displayed if it's already been displayed. (listview_row.xml)
        if (convertView == null){  //indicates this is the first time we are creating this row.
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);  //CRASH
            row = inflater.inflate(R.layout.listview_row, parent, false);
        }
        else
        {
            row = convertView;
        }
        // 2. Now that we have a valid row instance, we need to get references to the views within that row.
        ImageView imgEpisode = (ImageView) row.findViewById(R.id.imgEpisode);  //notice we prefixed findViewByID with row, why?  row, is the container.
        TextView tvEpisodeTitle = (TextView) row.findViewById(R.id.tvEpisodeTitle);
        TextView tvEpisodeDescription = (TextView) row.findViewById(R.id.tvEpisodeDescription);

        RatingBar rbEpisode = (RatingBar)row.findViewById(R.id.rbEpisode);
        //rbEpisode.setOnRatingBarChangeListener((RatingBar.OnRatingBarChangeListener) context);
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        float rating = preferences.getFloat(String.valueOf(position), 0f);
        rbEpisode.setRating(rating);
        rbEpisode.setStepSize(0.5f);
        System.out.println(position + " : set");

//        tvEpisodeTitle.setText(episodes.get(position));  //puts the predefined titles in the textview.
//
        tvEpisodeTitle.setText(episodes[position]);
        tvEpisodeDescription.setText(episodeDescriptions[position]);
        //rbEpisode.setRating(ratingBar[position]);

        imgEpisode.setImageResource(episodeImages.get(position).intValue());
//TODO rating bar needs fixed.  Currently all ratinbars are linked

        rbEpisode.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                if(fromUser) {
                    final int numStars = ratingBar.getNumStars();
                    editor = preferences.edit();
                    editor.putFloat(String.valueOf(position), rating);
                    System.out.println(position);
                    editor.commit();
                }
            }
        });




        if (position == 8) {
            row.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String num = PhoneNumberUtils.convertKeypadLettersToDigits(episodeLinks[8]);
                    Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel: " + num));
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
            });
        }else if (position == 9) {
            row.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String text = episodeLinks[9];
                   Intent sendIntent = new Intent(Intent.ACTION_SENDTO);
                        sendIntent.setData(Uri.parse("smsto:"));
                    sendIntent.putExtra("sms_body", text);
                    //sendIntent.putExtra("address","6176526415");
                   // sendIntent.setType("vnd.android-dir/mms-sms");
                    sendIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(sendIntent);
                   /* String defaultApplication = Settings.Secure.getString(context.getContentResolver(), "sms_default_application");
                    PackageManager pm = context.getPackageManager();
                    Intent intent = pm.getLaunchIntentForPackage(defaultApplication );
                    if (intent != null) {
                        intent.putExtra("sms_body", text);
                        context.startActivity(intent);
                    }*/

                }
            });
        }else if (position == 10) {
            row.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MediaPlayer player = MediaPlayer.create(context, R.raw.au_live_long);
                    player.start();
                }
            });
        }else if (position == 11) {
            row.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //TODO video does not work = gives notice "can't play this video"
                   // String path = "android.resource://" + context.getPackageName() + "/raw/vd_kahn.mp4";

                   /* Uri uri = Uri.parse(path);
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    intent.setDataAndType(uri, "video*//*");
                    //intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);*/

                    // context.startActivity(intent);



                       // Uri fileUri = Uri.fromFile(videoFile);
                        Intent intent = new Intent(context , VideoPlayerActivity.class);
                       // intent.setDataAndType(fileUri, URLConnection.guessContentTypeFromName(fileUri.toString()));
                        context.startActivity(intent);

                }
            });
        } else {
            row.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Toast.makeText(context, episodeLinks[position] , Toast.LENGTH_LONG).show();
                    Uri uri = Uri.parse(episodeLinks[position]);
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
            });
        }




        return row;  //once the row is fully constructed, return it.  Hey whatif we had buttons, can we target onClick Events within the rows, yep!
    }



    ///Helper method to get the drawables...///
    ///this might prove useful later...///

//    public ArrayList<Drawable> getDrawables() {
//        Field[] drawablesFields =com.example.sse.customlistview_sse.R.drawable.class.getFields();
//        ArrayList<Drawable> drawables = new ArrayList<Drawable>();
//
//        String fieldName;
//        for (Field field : drawablesFields) {
//            try {
//                fieldName = field.getName();
//                Log.i("LOG_TAG", "com.your.project.R.drawable." + fieldName);
//                if (fieldName.startsWith("animals_"))  //only add drawable resources that have our prefix.
//                    drawables.add(context.getResources().getDrawable(field.getInt(null)));
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//    }
}