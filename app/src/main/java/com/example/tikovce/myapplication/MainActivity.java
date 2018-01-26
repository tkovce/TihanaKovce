package com.example.tikovce.myapplication;

import android.Manifest;
import android.app.ListActivity;
import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.CursorLoader;
import android.support.v4.widget.CursorAdapter;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends ActionBarActivity{

    private int MY_PERM=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        DBAdapter db = new DBAdapter(this);

        db.open();
        db.delete();
        //db.delete2();

        long id = db.insertCake("Ice-cake", "zimska", "voce");
        id = db.insertCake("Ice-cake2", "opet_zimska", "slag");
        id = db.insertCake("Ice-cake3", "mozda_zimska", "secer");
        id = db.insertCake("Ice-cake4", "ova_nije_zimska", "bademi");
        id = db.insertCake("Ice-cake_zadnja", "LJETNA", "bademi");
        id = db.insertPrice("10", "1");
        id = db.insertPrice("20", "2");
        id = db.insertPrice("30", "3");
        id = db.insertPrice("30", "4");
        id = db.insertPrice("60", "1");
        db.close();



        TextView text1 =(TextView) findViewById(R.id.contactID);
        text1.setText("Torte i cijene:\n");
        db.open();
        Cursor c = db.getAllCakes();
        if (c.moveToFirst()) {
            do {
                text1.append("NAZIV:  " + c.getString(1) + ", VRSTA:  " + c.getString(2) + ", GL. SASTOJAK: " + c.getString(3) + "\n");
            } while (c.moveToNext());
        }
        db.close();

        ///bademi
        EditText et= (EditText)findViewById(R.id.bademi);

        db.open();
        c = db.getAllCakes();
        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            if(c.getString(2).equals("bademi")){
                et.append(c.getString(0) + "\n");
            }
        }
        db.close();

        db.open();
        Cursor c2 = db.getAllPrices();
        if (c2.moveToFirst())
        {
            do {
                text1.append(c2.getString(0) + " " + c2.getString(1) + "\n");
            } while (c2.moveToNext());
        }
        db.close();
*/
    }

    private void callList(String slovo, boolean flag){
            ContentResolver cr = getContentResolver();
            Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI,
                    null, null, null, null);

             TextView text1 =(TextView) findViewById(R.id.contactName);
            text1.setText("Kontakti: \n");

            if ((cur != null ? cur.getCount() : 0) > 0) {
                while (cur != null && cur.moveToNext()) {
                    String id = cur.getString(
                            cur.getColumnIndex(ContactsContract.Contacts._ID));
                    String name = cur.getString(cur.getColumnIndex(
                            ContactsContract.Contacts.DISPLAY_NAME));

                    if (cur.getInt(cur.getColumnIndex(
                            ContactsContract.Contacts.HAS_PHONE_NUMBER)) > 0) {
                        Cursor pCur = cr.query(
                                ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                                null,
                                ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                                new String[]{id}, null);
                        while (pCur.moveToNext()) {
                            String phoneNo = pCur.getString(pCur.getColumnIndex(
                                    ContactsContract.CommonDataKinds.Phone.NUMBER));
                            if(flag  && slovo.equals(name.substring(0,1))){
                                text1 =(TextView) findViewById(R.id.contactName);
                                text1.append(name + ", " + phoneNo + "\n");
                            }
                            else if(!flag && !slovo.equals(name.substring(0,1))){
                                text1 =(TextView) findViewById(R.id.contactName);
                                text1.append(name + ", " + phoneNo + "\n");
                            }
                        }
                        pCur.close();
                    }
                }
            }
            if(cur!=null){
                cur.close();
            }



    }

    public void onClickButtonOne(View view) {
        EditText text1 =(EditText)findViewById(R.id.text_input);
        String inputText = text1.getText().toString();


        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_CONTACTS},MY_PERM);
        }
        if(inputText.length() == 1){
            callList(inputText, true);
        }


    }

    public void onClickButtonTwo(View view) {
        EditText text1 =(EditText)findViewById(R.id.text_input);
        String inputText = text1.getText().toString();


        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_CONTACTS},MY_PERM);
        }
        if(inputText.length() == 1){
            callList(inputText, false);
        }


    }
}
