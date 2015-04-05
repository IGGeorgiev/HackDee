package com.hackdee;

import java.util.ArrayList;
import java.util.List;
import com.estimote.sdk.Beacon;
import com.estimote.sdk.BeaconManager;
import com.estimote.sdk.Region;
import com.estimote.sdk.Utils;
import com.estimote.sdk.connection.BeaconConnection;
import com.estimote.sdk.connection.BeaconConnection.BeaconCharacteristics;
import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public class MainActivity extends ActionBarActivity {
    private static ArrayList<PersonalBeacon> m_arrPersonalBeacons;



    private int m_nWhiteColor = R.color.white_color;
    private int m_nDarkColor  = R.color.dark_color;
    private int m_nLightColor = R.color.light_color;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.init_layout);


    }

    protected void initLayout(){

        setContentView(R.layout.init_layout);
        for(PersonalBeacon beacon : m_arrPersonalBeacons){
            if(beacon.getLocalName() != null){
                String s = beacon.getLocalName();
                TextView newLocatorName = new TextView(MainActivity.this);
                newLocatorName.setText(s);
                newLocatorName.setTextSize(16);
                newLocatorName.setTextColor(m_nWhiteColor);
                m_arrPersonalBeacons.add((PersonalBeacon) beacon);
                if(m_arrPersonalBeacons.size()%2==1){
                    newLocatorName.setBackgroundColor(m_nDarkColor);
                }else{
                    newLocatorName.setBackgroundColor(m_nLightColor);
                };
                //TODO
                setListenerDetails(newLocatorName);
                ((ScrollView) findViewById(R.id.my_beacons_scroll)).addView(newLocatorName);
            }else{
                String s = beacon.getName();
                TextView newLocatorName = new TextView(MainActivity.this);
                newLocatorName.setText(s);
                newLocatorName.setTextSize(16);
                newLocatorName.setTextColor(m_nWhiteColor);
                m_arrPersonalBeacons.add((PersonalBeacon) beacon);
                if(m_arrPersonalBeacons.size()%2==1){
                    newLocatorName.setBackgroundColor(m_nDarkColor);
                }else{
                    newLocatorName.setBackgroundColor(m_nLightColor);
                };
                //TODO
                setListenerCreation(newLocatorName);
                ((ScrollView) findViewById(R.id.other_beacons_scroll)).addView(newLocatorName);
            }
        }
    };

    protected void setListenerDetails(final TextView textView){
        textView.setOnClickListener(new OnClickListener(){

            @Override
            public void onClick(View v) {
                setContentView(R.layout.details_layout);
                ScheduleLocatorUpdates(textView);

            }
        });

    }
    protected void setListenerCreation(final TextView newLocatorName){
        newLocatorName.setOnClickListener(new OnClickListener(){
            PersonalBeacon b = findByName(newLocatorName.getText().toString());
            @Override
            public void onClick(View v) {
                setContentView(R.layout.creation_layout);
                Button showButton = (Button) findViewById(R.id.show_button);
                final String s = ((TextView) findViewById(R.id.name_edit_text)).getText().toString();
                showButton.setOnClickListener(new OnClickListener(){

                    @Override
                    public void onClick(View v) {
                        // TODO Auto-generated method stub
                        b.setLocalName(s);
                        initLayout();

                    }

                });
            }
        });

    }

    protected static PersonalBeacon findByLocalName(String name){
        PersonalBeacon b = null;
        for(PersonalBeacon beacon : m_arrPersonalBeacons){
            if(beacon.getLocalName()==name)b = beacon;
        }
        return b;
    }

    protected static PersonalBeacon findByName(String name){
        PersonalBeacon b = null;
        for(PersonalBeacon beacon : m_arrPersonalBeacons){
            if(beacon.getName()==name)b = beacon;
        }
        return b;
    }




    void ScheduleLocatorUpdates(final TextView textView) {
        BeaconManager bm = new BeaconManager(this);
        bm.setRangingListener(new BeaconManager.RangingListener(){
            public void onBeaconsDiscovered(Region region, final List<Beacon> beacons){
                runOnUiThread(new Runnable() {
                    @Override
                    public void run(){
                        ArrayList<PersonalBeacon> k = new ArrayList<PersonalBeacon>();
                        for(Beacon b: beacons){
                            k.add(new PersonalBeacon(b));
                        }
                        updateBeacons(k);
                        PersonalBeacon b = findByLocalName(textView.getText().toString());
                        ((TextView) findViewById(R.id.name_of_beacon)).setText(b.getLocalName());
                        ((TextView) findViewById(R.id.distance_of_beacon)).setText(String.format("%.2f",Utils.computeAccuracy(b)));


                        //((TextView) findViewById(R.id.angle_to_north)).setText(b.));
                    }});
            }

        });
    }
    //setter recurring
    void updateBeacons(ArrayList<PersonalBeacon> beacons) {

        m_arrPersonalBeacons = beacons;
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu); //possible glitch
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == com.hackdee.R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
