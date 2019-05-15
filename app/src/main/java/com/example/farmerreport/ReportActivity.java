package com.example.farmerreport;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ReportActivity extends AppCompatActivity {

    public void addNotification()
    {
        NotificationCompat.Builder builder = (NotificationCompat.Builder) new NotificationCompat.Builder(this)
                .setSmallIcon(android.R.drawable.stat_notify_more)
                .setContentTitle("Farmer Report")
                .setContentText("Fire Alert Probability HIGH");

        Intent i = new Intent(this,MainActivity.class);

        PendingIntent j = PendingIntent.getActivity(this,0,i,PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(j);

        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(0,builder.build());
    }

    public void addNotification1()
    {
        NotificationCompat.Builder builder = (NotificationCompat.Builder) new NotificationCompat.Builder(this)
                .setSmallIcon(android.R.drawable.stat_notify_more)
                .setContentTitle("Farmer Report")
                .setContentText("It might be Raining");

        Intent i = new Intent(this,MainActivity.class);

        PendingIntent j = PendingIntent.getActivity(this,0,i,PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(j);

        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(0,builder.build());
    }

    private TextView Temp_val,Soil_Moisture_Val,Flame_Val,Smoke_Val,Water_Level_Val,Rain_Water_Val,Crop;
    private Button Refresh;
    private DatabaseReference reff;
    public String crop = "Cotton, Raddish, Rice, Corn, Wheat,Potato ,Tomato";
    public String crop1 = "Potato, Peanuts, Sweet Potato, Parsley, Raspberry";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        Temp_val=(TextView) findViewById(R.id.temp_val);
        Flame_Val=(TextView) findViewById(R.id.flame_val);
        Smoke_Val=(TextView) findViewById(R.id.smoke_val);
        Soil_Moisture_Val=(TextView) findViewById(R.id.soil_moisture_val);
        Water_Level_Val=(TextView) findViewById(R.id.water_level_val);
        Rain_Water_Val=(TextView) findViewById(R.id.rain_water_val);
        Refresh=(Button) findViewById(R.id.refresh);
        Crop=(TextView) findViewById(R.id.crop);
        Refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reff=FirebaseDatabase.getInstance().getReference().child("Report");
                reff.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        String temperature = dataSnapshot.child("temp_val").getValue().toString();
                        String soil_moi = dataSnapshot.child("soil_moi_val").getValue().toString();
                        String flame = dataSnapshot.child("flame_val").getValue().toString();
                        String smoke = dataSnapshot.child("smoke_val").getValue().toString();
                        String rain_water = dataSnapshot.child("rain_water").getValue().toString();
                        String water_level = dataSnapshot.child("water_level").getValue().toString();

                        Temp_val.setText(temperature);
                        Flame_Val.setText(flame);
                        Smoke_Val.setText(smoke);
                        Soil_Moisture_Val.setText(soil_moi);
                        Water_Level_Val.setText(water_level);
                        Rain_Water_Val.setText(rain_water);


                        try{
                            if(soil_moi.equals("6.5") || soil_moi.equals("6.4")|| soil_moi.equals("6.3")|| soil_moi.equals("6.2")|| soil_moi.equals("6.1")|| soil_moi.equals("6.0")|| soil_moi.equals("5.9")|| soil_moi.equals("5.8")|| soil_moi.equals("5.7") || soil_moi.equals("5.6") || soil_moi.equals("5.5"))
                            {
                                Crop.setText(crop);
                            }
                            else if(soil_moi.equals("4.5") || soil_moi.equals("4.6")|| soil_moi.equals("4.7")|| soil_moi.equals("4.8")|| soil_moi.equals("4.9")|| soil_moi.equals("5.0")|| soil_moi.equals("5.1")|| soil_moi.equals("5.2")|| soil_moi.equals("5.3") || soil_moi.equals("5.4"))
                            {
                                Crop.setText(crop1);
                            }


                        }catch(Exception e){
                            e.printStackTrace();
                        }

                        if(flame.equals("High")){
                            addNotification();
                        }

                        if(rain_water.equals("Raining"))
                        {
                            addNotification1();
                        }

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }

        });







    }

}
