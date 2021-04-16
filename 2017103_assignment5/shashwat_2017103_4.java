package com.example.assignment5;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private static final int PERMISSIONS_REQUEST_CODE_ACCESS_COARSE_LOCATION =0 ;
    private static userLight userr;
    private static int k=3;
    private static int inp;
    WifiManager wifi;
    TextView status;
    Button scanBtn;
    Button wardriveon;
    Button submit;
    EditText enter;
    TextView result;
    Button wardriveoff;
    int wardriving;
    int size = 0;
    BarChart barChart;
    int chk=10;
    int uidd=0;
    int label=0;


    AppDatabase db;




    @RequiresApi(api = Build.VERSION_CODES.R)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        scanBtn=(Button) findViewById(R.id.scanBtn);
        submit=(Button) findViewById(R.id.submit);
        enter=(EditText) findViewById(R.id.enter);
        result=(TextView) findViewById(R.id.res);



        wifi = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        barChart = (BarChart) findViewById(R.id.barchart);
        wardriveon = (Button) findViewById(R.id.wardriveon);
//        wardriveoff = (Button) findViewById(R.id.wardriveoff);

        status = (TextView) findViewById(R.id.status);

         db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "database-name").allowMainThreadQueries().build();

//         db =
//                Room.databaseBuilder(getApplicationContext(),
//                        AppDatabase.class, "database-name").build();

        userr = db.userLight();
//        List<light> data=userr.getAll();








        scanBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){
                    requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                            PERMISSIONS_REQUEST_CODE_ACCESS_COARSE_LOCATION);
                    //After this point you wait for callback in onRequestPermissionsResult(int, String[], int[]) overriden method

                }else{

                    //do something, permission was previously granted; or legacy device
                }

                List<ScanResult> results = wifi.getScanResults();


                ArrayList<String> labels = new ArrayList<String>();
                ArrayList<BarEntry> entries = new ArrayList<>();
                for(int i=0;i<results.size();i++){
                    int rssi=results.get(i).level;

                    entries.add(new BarEntry(rssi, i));
                    String name=results.get(i).SSID;
                    labels.add(name);


                }
//                entries.add(new BarEntry(-10, 1));
//                labels.add("eds");

                BarDataSet bardataset = new BarDataSet(entries, "Cells");
                BarData data = new BarData(labels, bardataset);
                barChart.setData(data); // set the data and list of labels into chart
                barChart.setDescription("Set Bar Chart Description Here");  // set the description
                bardataset.setColors(ColorTemplate.COLORFUL_COLORS);
                barChart.animateY(5000);

//                Log.i("abc","outer  "+results.get(0).level);
            }

        });
        wardriveon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {




                String cloc="room"+label;

                   for(int i=0;i<chk;i++){
                        List<ScanResult> results = wifi.getScanResults();
                       Log.i("abc",results.size()+"");
                        ArrayList<Integer> ids=new   ArrayList<Integer>();
                        for(int j=0;j<results.size();j++){
                            ids.add(results.get(j).level);
                            Log.i("abc","outer  "+results.get(j).SSID);
                        }
                        record nrec=new record();
//                        nrec.val=ids;
//                        nrec.location=cloc;
//                        nrec.uid=(uidd);
//                        uidd=uidd+1;
                       light nlight=new light();
                       Random rd = new Random();
                       nlight.ts=rd.nextInt();
                       nlight.location=cloc;
                       nlight.val=ids;


                       userr.insert(nlight);

                       try {
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                    }
                   label=label+1;
                Toast.makeText(MainActivity.this, "Wardriving ended for "+cloc, Toast.LENGTH_SHORT).show();





            }

        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                 inp=Integer.parseInt(String.valueOf(enter.getText()));
                result.setText(knn(inp));
            }

        });

//        wardriveoff.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                chk=0;
//
//            }
//
//        });




    }
    public static String knn(int val){
        val=val-1;
        List<light> data=userr.getAll();
        Log.i("abc",data.size()+"sz");
        HashMap<String,Integer> hm=new     HashMap<String,Integer>();
        int mvar=0;
        String mloc="";
        for(int i=0;i<k;i++){
                     int tval=data.get(i).val.get(0)*(-1)-val;
            Log.i("abc",data.get(i).location);
                     if(tval<0){
                         tval=tval*(-1);
            }
                     int mini=tval;
                     int minpos=i;


                    for(int j=i+1;j<data.size();j++){

                        int jval=data.get(i).val.get(0)*(-1)-val;
                        if(jval<0){
                            jval=jval*(-1);
                        }
                        if(jval<mini){
                            mini=jval;
                            minpos=j;
                        }

                    }
            light temp=data.get(minpos);
            data.set(minpos,data.get(i));
            data.set(i,temp);

                    String lc=(data.get(minpos).location);

                    if(hm.containsKey(lc)==true){
                        int var=hm.get(lc)+1;
                        if(var>mvar){mvar=var;
                        mloc=lc;

                        }
                        hm.put(lc,var);


                    }
                    else{
                        int var=1;
                        if(var>mvar){
                            mvar=var;
                            mloc=lc;

                        }
                        hm.put(lc,var);
                    }



        }





        return (mloc);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        if (requestCode == PERMISSIONS_REQUEST_CODE_ACCESS_COARSE_LOCATION
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            // Do something with granted permission
        }
    }

}
