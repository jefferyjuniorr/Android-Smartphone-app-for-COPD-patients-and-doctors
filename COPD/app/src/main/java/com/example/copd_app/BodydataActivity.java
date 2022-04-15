package com.example.copd_app;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.TextClock;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class BodydataActivity extends AppCompatActivity {
    TextClock textclock;
    JSONArray jsonhr;
    JSONArray jsonfour;
    JSONArray jsonaecopd;
    JSONObject jsonair;
    JSONObject jsontem;
    TextView locatext;
    LocationListener locationListener;
    private LocationManager locationManager;
    private String commadStr;
    private String ptlong, ptlongcut, ptlat, ptlatcut;
    private String sitelatlong;
    private String sitelat;
    private String sitelong;
    private String sitelatlongtem;
    private String sitelattem;
    private String sitelongtem;
    private String finalsiteword;
    private String finalsitewordtem;
    private String responseairdata, responsetemdata;
    private String userid, today;
    private float sitelatf, sitelongf, ptlongcutf, ptlatcutf;
    private float sitelatftem, sitelongftem;
    public static final int MY_PERMISSION_ACESS_COARSE_LOCATION = 11;
    private static final double EARTH_RADIUS = 6378137;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_body_data);
        textclock = (TextClock) findViewById(R.id.textclock);
        textclock.setFormat12Hour("yyyy-MM-dd hh:mm");

        readSiteData();
        readSiteDataTem();
        caldis();
        caldistem();

        //run the Api
        getheartrate();
        getfour();
        getaecopd();
        getair();
        gettem();

        final SharedPreferences prefuserid = this.getSharedPreferences("Useridstore",MODE_PRIVATE);
        userid = prefuserid.getString("userid","");

        Calendar mCal = Calendar.getInstance();
        String dateformat = "yyyy-MM-dd";
        SimpleDateFormat df = new SimpleDateFormat(dateformat);
        today = df.format(mCal.getTime());

        //Handle the Get Strings
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable(){
            @Override
            public void run(){
        try {
            updatehr();
            updatefour();
            updateair();
            updateair();
            getlocation();
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
                }
        }, 5000);//delay 5 seconds
    }

    private List<SiteSample> siteSamples = new ArrayList<>();
    private void readSiteData() {
        InputStream is = getResources().openRawResource(R.raw.site);
        BufferedReader reader = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
        String line;
        try {
            //step over header
            reader.readLine();
            while ((line = reader.readLine()) != null) {
                //split by ,
                String[] tokens = line.split(",");

                //read site.csv
                SiteSample sample = new SiteSample();
                sample.setSite(tokens[0]);
                sample.setLongtitude(Float.parseFloat(tokens[1]));
                sample.setLatitude(Float.parseFloat(tokens[2]));
                siteSamples.add(sample);

//                Log.d("BodyData", "Just created: " + sample);
            }
        } catch (IOException e){
            e.printStackTrace();
        }

//        Log.d("print", String.valueOf(siteSamples.get(0)));
    }

    private List<SiteSample> weathersiteSamples = new ArrayList<>();
    private void readSiteDataTem() {
        InputStream is = getResources().openRawResource(R.raw.weather_site);
        BufferedReader reader = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
        String line;
        try {
            //step over header
            reader.readLine();
            while ((line = reader.readLine()) != null) {
                //split by ,
                String[] tokens = line.split(",");

                //read site.csv
                SiteSample samplew = new SiteSample();
                samplew.setSite(tokens[0]);
                samplew.setLongtitude(Float.parseFloat(tokens[1]));
                samplew.setLatitude(Float.parseFloat(tokens[2]));
                weathersiteSamples.add(samplew);

                Log.d("BodyData", "Just created: " + samplew);
            }
        } catch (IOException e){
            e.printStackTrace();
        }

//        Log.d("print", String.valueOf(weathersiteSamples.get(0)));
    }


    public void getlocation(){
        commadStr = LocationManager.GPS_PROVIDER;
        TextView locatext = (TextView)findViewById(R.id.location);
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION},
                    MY_PERMISSION_ACESS_COARSE_LOCATION);
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Location location = locationManager.getLastKnownLocation(commadStr);
        ptlong = String.valueOf(location.getLongitude());
        ptlongcut = ptlong.substring(1,8);
        ptlat = String.valueOf(location.getLatitude());
        ptlatcut = ptlat.substring(0,6);
        ptlongcutf = Float.parseFloat(ptlongcut);
        ptlatcutf = Float.parseFloat(ptlatcut);
        if (location != null)
            locatext.setText("經度 " + ptlongcut + " 緯度 " + ptlatcut);
        else
            locatext.setText("定位中");
    }


    private static double rad(double d) {
        return d * Math.PI / 180.0;
    }
    public static double DistanceOfTwoPoints(double lat1,double lng1,
                                             double lat2,double lng2) {
        double radLat1 = rad(lat1);
        double radLat2 = rad(lat2);
        double a = radLat1 - radLat2;
        double b = rad(lng1) - rad(lng2);
        double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2)
                + Math.cos(radLat1) * Math.cos(radLat2)
                * Math.pow(Math.sin(b / 2), 2)));
        s = s * EARTH_RADIUS;
        s = Math.round(s * 10000) / 10000;
//        Log.i("距離",s+"");
        return s;
    }

    private List<Double> alldistance = new ArrayList<Double>();
    public void caldis(){
        for(int i = 0; i <= 76; i++ ){
            sitelatlong = String.valueOf(siteSamples.get(i));
            sitelong = sitelatlong.substring(14,22);
            sitelat = sitelatlong.substring(23,29);
//            Log.d("print", sitelong + "," + sitelat);
            sitelongf = Float.parseFloat(sitelong);
            sitelatf = Float.parseFloat(sitelat);
            alldistance.add(DistanceOfTwoPoints(ptlatcutf, ptlongcutf, sitelatf, sitelongf));
//            DistanceOfTwoPoints(ptlatcutf, ptlongcutf, sitelatf, sitelongf);
        }
//        Log.d("distance", String.valueOf(alldistance));
        int minima = alldistance.indexOf(Collections.min(alldistance));
        String mini = String.valueOf(siteSamples.get(minima));
        finalsiteword = mini.substring(11,13);
//        Log.d("nearest", finalsiteword);
    }

    private List<Double> alldistancetem = new ArrayList<Double>();
    public void caldistem(){
        for(int i = 0; i <= 55; i++ ){
            sitelatlongtem = String.valueOf(weathersiteSamples.get(i));
            String sitelatlongtem2 = sitelatlongtem.substring(14);
//            Log.d("printppppp", sitelatlongtem2);
            Integer latpo = sitelatlongtem2.indexOf(",");
            Integer lastdo = sitelatlongtem2.indexOf(")");
            sitelongtem = sitelatlongtem2.substring(0,latpo);
            sitelattem = sitelatlongtem2.substring(latpo+1, lastdo);
            Log.d("printppppp", sitelongtem + "," + sitelattem);
            sitelongftem = Float.parseFloat(sitelongtem);
            sitelatftem = Float.parseFloat(sitelattem);
            alldistancetem.add(DistanceOfTwoPoints(ptlatcutf, ptlongcutf, sitelatftem, sitelongftem));
//            DistanceOfTwoPoints(ptlatcutf, ptlongcutf, sitelatf, sitelongf);
        }
        Log.d("distance", String.valueOf(alldistancetem));
        int minimatem = alldistancetem.indexOf(Collections.min(alldistancetem));
        String minitem = String.valueOf(siteSamples.get(minimatem));
        finalsitewordtem = minitem.substring(11,13);
        Log.d("nearest", finalsitewordtem);
    }

    //Api of Heartrate
    public JSONArray getheartrate(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient client = new OkHttpClient().newBuilder()
                        .build();
                Request request = new Request.Builder()
                        .url("https://ntu-med-god.ml/api/getHeartRateMeanByRange?id="+userid+"&start="+today+"&end="+today+"")
                        .method("GET", null)
                        .addHeader("Cookie", "connect.sid=s%3AivhWWzzmpA3EvvtmzDXnmSdUiGr14HWD.G7h3iqUA9URxjXTXAIWFnNnhiEvbkUxLOY4Dpom%2Fn0c; connect.sid=s%3Ag7lg1dYKE72JwJ5sz3bf07w-6H_mIya8.vjQREVRfhJ%2Bi4GlSkhJNHe3AXIsa1%2FYyZHwvzPPtaFg")
                        .build();
                try {
                    Response response = client.newCall(request).execute();
                    String responseDatahr = response.body().string();
                    jsonhr = new JSONArray(responseDatahr);
                    //Log.d("BodydataActivity", String.valueOf(jsonhr));
                } catch (IOException | JSONException e){
                    e.printStackTrace();
                }

            }
        }).start();
        return jsonhr;
    }

    //Api of cal, dis, floor, step
    public JSONArray getfour(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient client = new OkHttpClient().newBuilder()
                        .build();
                Request request = new Request.Builder()
                        .url("https://ntu-med-god.ml/api/getUserFitbitByRange?id="+userid+"&start="+today+"&end="+today+"")
                        .method("GET", null)
                        .addHeader("Cookie", "connect.sid=s%3AivhWWzzmpA3EvvtmzDXnmSdUiGr14HWD.G7h3iqUA9URxjXTXAIWFnNnhiEvbkUxLOY4Dpom%2Fn0c; connect.sid=s%3AypiO6GaTNnM8bkP75l5Z1JjPUekrN0Mk.xs0p45DIxtLbuAarqk7DWxpvUvXo6qu5Vosi4DTR7Xo")
                        .build();
                try {
                    Response response = client.newCall(request).execute();
                    String responseDatafour = response.body().string();
                    jsonfour = new JSONArray(responseDatafour);
                    //Log.d("BodydataActivity", String.valueOf(jsonfour));
                } catch (IOException | JSONException e){
                    e.printStackTrace();
                }

            }
        }).start();
        return jsonfour;
    }

    //Api of aecopd
    public JSONArray getaecopd(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient client = new OkHttpClient().newBuilder()
                        .build();
                Request request = new Request.Builder()
                        .url("https://ntu-med-god.ml/api/getUserAECOPDRate?id="+userid+"")
                        .method("GET", null)
                        .addHeader("Cookie", "connect.sid=s%3AivhWWzzmpA3EvvtmzDXnmSdUiGr14HWD.G7h3iqUA9URxjXTXAIWFnNnhiEvbkUxLOY4Dpom%2Fn0c; connect.sid=s%3A1nzMUB94bBhFDLIJPw0ddkxr1BwYblSI.ZgG3aJLuU%2BmdPzEJPoEvn88crCmfmICbMzudrSYa2ro")
                        .build();
                try {
                    Response response = client.newCall(request).execute();
                    String responseDataaecopd = response.body().string();
                    jsonaecopd = new JSONArray(responseDataaecopd);
                    //Log.d("BodydataActivity", String.valueOf(jsonaecopd));
                } catch (IOException | JSONException e){
                    e.printStackTrace();
                }

            }
        }).start();
        return jsonaecopd;
    }

    public JSONObject getair(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient client = new OkHttpClient().newBuilder()
                        .build();
                Request request = new Request.Builder()
                        .url("https://data.epa.gov.tw/api/v1/aqx_p_432?limit=1000&api_key=bc53fb6e-d2ad-460d-a9f4-8df1eaa999a2")
                        .method("GET", null)
                        .build();
                try {
                    Response response = client.newCall(request).execute();
                    responseairdata = response.body().string();
                    jsonair = new JSONObject(responseairdata);
                    //Log.d("BodydataActivity", String.valueOf(jsonair));
                } catch (IOException | JSONException e){
                    e.printStackTrace();
                }

            }
        }).start();
        return jsonair;
    }

    public JSONObject gettem(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient client = new OkHttpClient().newBuilder()
                        .build();
                Request request = new Request.Builder()
                        .url("https://opendata.cwb.gov.tw/api/v1/rest/datastore/O-A0001-001?Authorization=CWB-76CAC058-E86F-4915-AEF5-0170A2E044D7&obsTime=\"+時間+\"&elementName=TEMP,HUMD")
                        .method("GET", null)
                        .addHeader("Cookie", "TS01dbf791=0107dddfef3cd577acbec9c2745adaa2b0e9efa913f506dd017a47ed8b06171cb7d8bc7dc25feb8a6c7fa909a9d27d27234d8c8ac3")
                        .build();
                try {
                    Response response = client.newCall(request).execute();
                    responsetemdata = response.body().string();
                    jsontem = new JSONObject(responsetemdata);
                    //Log.d("BodydataActivity", String.valueOf(jsontem));
                } catch (IOException | JSONException e){
                    e.printStackTrace();
                }

            }
        }).start();
        return jsontem;
    }

    //
    public void updatehr() throws JSONException, ParseException {
        TextView Hrval = (TextView) findViewById(R.id.meanhrdata);
        //Log.d("BodyDataActivity", String.valueOf(jsonhr));
        if (jsonhr != null) {
            //Get heartrate
            JSONObject jsonObject = jsonhr.getJSONObject(0);
            String meanheartrate = jsonObject.getString("mean");
            String submeanheartrate = meanheartrate.substring(0,5);
            //Log.d("BodyDataActivity", submeanheartrate);
            Hrval.setText(String.valueOf(submeanheartrate));
        }
    }

    //
    public void updatefour() throws JSONException, ParseException {
        //Set up Textview
        TextView Caldata = (TextView) findViewById(R.id.caloriesdata);
        TextView Disdata = (TextView) findViewById(R.id.distancedata);
        TextView Floordata = (TextView) findViewById(R.id.floorsdata);
        TextView Stepdata = (TextView) findViewById(R.id.stepsdata);
        //Log.d("BodyDataActivity", String.valueOf(jsonfour));
        if (jsonfour != null) {
            //Get calories
            JSONObject jsonObjectcal = jsonfour.getJSONObject(0);
            JSONArray jsonArraycal = jsonObjectcal.getJSONArray("value");
            JSONObject jsonObjectcallast = jsonArraycal.getJSONObject(0);
            String calories = jsonObjectcallast.getString("sum");
            String subcalories = calories.substring(0,7);
            //Log.d("BodyDataActivity", subcalories);
            Caldata.setText(String.valueOf(subcalories));
            //Get distances
            JSONObject jsonObjectdis = jsonfour.getJSONObject(1);
            JSONArray jsonArraydis = jsonObjectdis.getJSONArray("value");
            JSONObject jsonObjectdislast = jsonArraydis.getJSONObject(0);
            String distances = jsonObjectdislast.getString("sum");
            String subdistances = distances.substring(0,4);
            //Log.d("BodyDataActivity", subdistances);
            Disdata.setText(String.valueOf(subdistances));
            //Get floors
            JSONObject jsonObjectfloor = jsonfour.getJSONObject(2);
            JSONArray jsonArrayfloor = jsonObjectfloor.getJSONArray("value");
            JSONObject jsonObjectfloorlast = jsonArrayfloor.getJSONObject(0);
            String floors = jsonObjectfloorlast.getString("sum");
            //Log.d("BodyDataActivity", floors);
            Floordata.setText(String.valueOf(floors));
            //Get steps
            JSONObject jsonObjectstep = jsonfour.getJSONObject(3);
            JSONArray jsonArraystep = jsonObjectstep.getJSONArray("value");
            JSONObject jsonObjectsteplast = jsonArraystep.getJSONObject(0);
            String steps = jsonObjectsteplast.getString("sum");
            //Log.d("BodyDataActivity", steps);
            Stepdata.setText(String.valueOf(steps));
        }
    }

    public void updateair() throws JSONException, ParseException {
        TextView pm25 = (TextView) findViewById(R.id.pm25data);
        TextView pollu = (TextView) findViewById(R.id.pollutantsdata);
        TextView aqi = (TextView) findViewById(R.id.aqidata);
        TextView status = (TextView) findViewById(R.id.airstatusdata);
        TextView temp = (TextView) findViewById(R.id.tempdata);
        TextView humi = (TextView) findViewById(R.id.humiddata);
        if (jsonair != null) {
            //取正確site&all資料
            Integer finalsite = responseairdata.indexOf(finalsiteword);
//            Log.d("Air", String.valueOf(finalsite));
            String aftersite =  responseairdata.substring(finalsite-12);
//            Log.d("Air", aftersite);
            Integer finalimport = aftersite.indexOf("ImportDate");
            String truesite = aftersite.substring(0, finalimport-2);
//            Log.d("Air", aftersite.substring(0, finalimport-2));
            String[] strair = truesite.split(",");
            List<String> listair = new ArrayList<String>();
            for(String list : strair){
                String liststr = String.format(list);
                listair.add(liststr);
            }

            //Get pm2.5
            String PM25be = listair.get(11);
            PM25be = PM25be.substring(8);
            PM25be = PM25be.replace("'", "");
//            Log.d("PM25", PM25be);
            pm25.setText(PM25be);

            //Get Pollutants
            String Pollu = listair.get(3);
            Pollu = Pollu.substring(12);
            Pollu = Pollu.replace("'", " ");
            pollu.setText(Pollu);

            //Get AQI
            String AQI = listair.get(2);
            AQI = AQI.substring(6);
            AQI = AQI.replace("'", "");
            aqi.setText(AQI);

            //Get Status
            String Status = listair.get(4);
            Status = Status.substring(9);
            Status = Status.replace("'", "");
            status.setText(Status);
        }
        if (jsontem != null){
//            Log.d("TEM", responsetemdata);
            Integer temloca = responsetemdata.indexOf("福山");
            String responsetemdatacut = responsetemdata.substring(temloca);
            Integer temTEM = responsetemdatacut.indexOf("TEMP");
            Integer temHUMI = responsetemdatacut.indexOf("HUMD");
            String Temcut = responsetemdatacut.substring(temTEM+22, temTEM+26);
            String HUMIcut = responsetemdatacut.substring(temHUMI+22, temHUMI+26);

            temp.setText(Temcut);

            humi.setText(HUMIcut);
        }
    }


}