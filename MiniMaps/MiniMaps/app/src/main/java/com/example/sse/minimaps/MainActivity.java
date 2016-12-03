//Ref: A. Porter, U. Maryland, with a few tweaks.
//https://github.com/aporter/coursera-android/blob/master/Examples/MapLocationFromContacts/src/course/examples/maplocationfromcontacts/MapLocationFromContactsActivity.java

package com.example.sse.minimaps;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends Activity {

    // It does get tiring typing ContactsContract.Data... over and over again.
    // These variables are shorthand aliases for data items in Contacts-related database tables
    // Java does not have a with clause. :(
//MINIMAPS START
    private static final String DATA_MIMETYPE = ContactsContract.Data.MIMETYPE;
    private static final Uri DATA_CONTENT_URI = ContactsContract.Data.CONTENT_URI;
    private static final String DATA_CONTACT_ID = ContactsContract.Data.CONTACT_ID;

    private static final String CONTACTS_ID = ContactsContract.Contacts._ID;
    private static final Uri CONTACTS_CONTENT_URI = ContactsContract.Contacts.CONTENT_URI;

    private static final String STRUCTURED_POSTAL_CONTENT_ITEM_TYPE = ContactsContract.CommonDataKinds.StructuredPostal.CONTENT_ITEM_TYPE;
    private static final String STRUCTURED_POSTAL_FORMATTED_ADDRESS = ContactsContract.CommonDataKinds.StructuredPostal.FORMATTED_ADDRESS;

    private static final int PICK_CONTACT_REQUEST = 9999;
//MINIMAPS END

    private String TAG = "BOSTON";

//MINIMAPS START
    private Button btnShowOnMap;
    private EditText edtLocation;
    private Button btnGetFromContacts;
//MINIMAPS END


//ANIMATION START
    private Button btnAnimate;
    private ImageView imgComic;
    private Animation bounceanim, wobbleranim, togetheranim, slideleftanim;
//ANIMATION END


//MISC INTENTS START
    private Button btnCallMom;
    private Button btnActuallyCallMom;
    private Button btnSMSMom;
    private Button btnSMSManagerMom;
    private EditText edtMomsPhoneNo;
//MISC INTENTS END

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//Setting up VIew References.
        edtLocation = (EditText) findViewById(R.id.edtLocation);
        btnShowOnMap = (Button) findViewById(R.id.btnShowOnMap);
        btnGetFromContacts = (Button) findViewById(R.id.btnGetFromContacts);
        btnAnimate = (Button) findViewById(R.id.btnAnimate);
        imgComic = (ImageView) findViewById(R.id.imgComic);
        bounceanim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.bounce);
        wobbleranim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.wobbler);   //ref: http://stackoverflow.com/questions/9448732/shaking-wobble-view-animation-in-android
        togetheranim= AnimationUtils.loadAnimation(getApplicationContext(), R.anim.together);

        slideleftanim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slideleft);
        btnCallMom = (Button) findViewById(R.id.btnCallMom);
        btnActuallyCallMom = (Button) findViewById(R.id.btnActuallyCallMom);
        btnSMSMom = (Button) findViewById(R.id.btnSMSMom);
        btnSMSManagerMom = (Button) findViewById(R.id.btnSMSManagerMom);
        edtMomsPhoneNo = (EditText) findViewById(R.id.edtMomsPhoneNo);

    // Called when user clicks the Show Map button
    // Opening Map Address specified in EditText.
        btnShowOnMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //"Vanilla flavored" Implicit Intent to send a location to an App, typically some mapping app.
                //But it really depends on who's listening.  It doesn't have to be a map that consumes
                //our intent!

                try {
                    String loc = edtLocation.getText().toString();
                    loc = loc.replace(' ', '+');
                    Intent geoLocationIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("geo:0,0?q=" + loc));  //A URI is just a consistent way of identifying a resource.  A URL is an example of a URI!
                    startActivity(geoLocationIntent);  //Broadcasting our implicit intent. Wait, that was it?  Yep, Android Framework makes it quick and easy to open other Apps.
                } catch (Exception e) {
                    Log.e(TAG, e.toString());
                }
            }
        });


//MINIMAPS START
// Let's work with a Conent Provider on our device.
// There are many Content Providers, we will work with Contacts.
// Routines to Open MAP Location from Contact's Address
        btnGetFromContacts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            try {
                //New intent to open Contacts.
                // Create Intent object for picking data from Contacts database
                Intent geoLocationFromAddressIntent = new Intent(Intent.ACTION_PICK, CONTACTS_CONTENT_URI);
                //Start the Activity to open contacts, and return result.  What result?  Success, Failure, something else,
                // Use intent to start Contacts application
                // Variable PICK_CONTACT_REQUEST identifies this operation, it's like dye.
                startActivityForResult(geoLocationFromAddressIntent, PICK_CONTACT_REQUEST);  //the 9999 represents an expected result code, best to use a constant.
            } catch (Exception e) {
                Log.e(TAG, e.toString());  //errors are in red.
            }
        }
    });



//MISC INTENTS -- USING TELEPHONY DEVICES START
        btnCallMom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phoneNo = edtMomsPhoneNo.getText().toString();
                Intent phoneCallMom = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+phoneNo));
//                Intent phoneCallMom = new Intent(Intent.ACTION_DIAL);  //or with two lines.
//                phoneCallMom.setData(Uri.parse("tel:16173582367"));
                startActivity(phoneCallMom);

            }
        });

        btnActuallyCallMom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phoneNo = edtMomsPhoneNo.getText().toString();
                Intent phoneCallMom = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"+phoneNo));
                startActivity(phoneCallMom);
            }
        });
//MISC INTENTS -- USING TELEPHONY DEVICES END

//MISC INTENTS -- SENDING SMS MESSAGES TWO WAYS START
 //       REF: http://www.mkyong.com/android/how-to-send-sms-message-in-android/

        btnSMSMom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {

                    String phoneNo = edtMomsPhoneNo.getText().toString();
                    String message = "Hi Mom";

                    Intent sendIntent = new Intent(Intent.ACTION_VIEW);
                    sendIntent.putExtra("address", phoneNo);
                    sendIntent.putExtra("sms_body", message);
                    sendIntent.setType("vnd.android-dir/mms-sms");
                    startActivity(sendIntent);

                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(),
                            "SMS faild, please try again later!",
                            Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }
        });

        btnSMSManagerMom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {

                    String phoneNo = edtMomsPhoneNo.getText().toString();
                    String message = "Hi Mom";

                    SmsManager smsManager = SmsManager.getDefault();
                    smsManager.sendTextMessage(phoneNo, null, message, null, null);
                    Toast.makeText(getApplicationContext(), "SMS sent.", Toast.LENGTH_LONG).show();
                }

                catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "SMS failed, please try again.", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }
        });
//MISC INTENTS -- SENDING SMS MESSAGES TWO WAYS END

//ANIMATIONS START
        btnAnimate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//LET'S TRY EACH OF THESE APPROACHES, AD HOC.
//
//                imgComic.animate().setDuration(3000);
//                imgComic.animate().alpha(0.25f);
//                imgComic.animate().rotation(5);
 //               imgComic.animate().rotation(-360);
//
//                imgComic.animate().alpha(0.1f);

//SIMPLE TILT
//                    imgComic.animate().setDuration(500);
//                    imgComic.animate().rotation(10 * sgn);


                //simple wobble (q&d to illustrate what not to do.)
                //this won't work, because there is no delay in between calls,
                //new calls overwrite older calls, before the older calls can finish.
                //a handler can insert a delay, but this would still not be a good way
                //to code animation in Android.
                //Ase an animator instead.  Let's try it.
//                int sgn=1;
//                for(int i=0;i<10;i++)
//                {
//                    imgComic.animate().setDuration(500);
//                    imgComic.animate().rotation(10 * sgn);
//                    if (i%2==0)
//                        sgn=1;
//                    else
//                        sgn = -1;
//                }


 //               imgComic.startAnimation(wobbleranim);  //this is a custom anim.
                imgComic.startAnimation(bounceanim);  //this is a custom anim.
   //             imgComic.startAnimation(togetheranim);  //this is a custom anim.

    //            imgComic.startAnimation(slideleftanim);

//these are animations that come with android studio.
//          imgComic.startAnimation(AnimationUtils.loadAnimation(MainActivity.this, android.R.anim.slide_out_right));
//          imgComic.startAnimation(AnimationUtils.loadAnimation(MainActivity.this, android.R.anim.fade_out));

            }
        });

}
//ANIMATIONS END

//MINIMAPS START CALLBACK for StartActivityForResult -- NOTE THIS HANDLER IS OUTSIDE OF THE ONCREATE CONSTRUCTOR.
    //PART 2: Query your contacts to get the first available address associated with that contact.
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        // Ensure that this call is the result of a successful PICK_CONTACT_REQUEST request
        if (resultCode == Activity.RESULT_OK && requestCode == PICK_CONTACT_REQUEST) {  //if true, we know we got some valid data back, and that this corresponds to the right activity.
            // These details are covered in the lesson on ContentProviders
            ContentResolver cr = getContentResolver();  //just another android manager, eg, Fragment Manager, Sensor Manager, etc.
            Cursor cursor = cr.query(data.getData(), null, null, null, null);  //this is an iterator into the "table" of contacts.

            if (cursor != null && cursor.moveToFirst()) {
                String id = cursor.getString(cursor.getColumnIndex(CONTACTS_ID));
                String whereClause = DATA_CONTACT_ID + " = ? AND " + DATA_MIMETYPE + " = ?";
                String[] parms = new String[] { id, STRUCTURED_POSTAL_CONTENT_ITEM_TYPE };
                Cursor addrCur = cr.query(DATA_CONTENT_URI, null, whereClause, parms, null);

                if (addrCur != null && addrCur.moveToFirst()) {//checking that we got a result, AND moving to first of possible several addresses.
                    String formattedAddress = addrCur.getString(addrCur.getColumnIndex(STRUCTURED_POSTAL_FORMATTED_ADDRESS));

                    if (formattedAddress != null) {  //don't want to parse or open a non-existant address, so check for null first.

                        // Process text for network transmission
                        formattedAddress = formattedAddress.replace(' ', '+');  //google maps doesn't want spaces, it wants +'s.

                        // Create Intent object for starting Google Maps application
                        Intent geoIntent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse("geo:0,0?q=" + formattedAddress));
                        // Use the Intent to start Google Maps (or some other similar) application using Activity.startActivity()
                        startActivity(geoIntent);
                    }
                }
                if (null != addrCur)
                    addrCur.close();  //Clean up table cursors
            }
            if (null != cursor)
                cursor.close();  //Clean up table cursors (just like closing a file)
        }
    }
//MINIMAPS END

}