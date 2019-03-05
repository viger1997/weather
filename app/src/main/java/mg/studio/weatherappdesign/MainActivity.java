package mg.studio.weatherappdesign;

import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Calendar;
import java.util.Date;




public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    protected void onInitDate() {
        Calendar calendar = Calendar.getInstance();
        int month=calendar.get(Calendar.MONTH);
        month++;
        ((TextView) findViewById(R.id.tv_date)).setText(calendar.get(Calendar.YEAR)+"/"+month+"/"+calendar.get(Calendar.DATE));
        //((TextView) findViewById(R.id.temperature_of_the_day)).setText(temperature);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void btnClick(View view) {
        new DownloadUpdate().execute();
            onInitDate();
        Toast.makeText(this,"update finished",Toast.LENGTH_SHORT).show();
    }


    private class DownloadUpdate extends AsyncTask<String, Void, String> {


        @Override
        protected String doInBackground(String... strings) {
            String stringUrl = "http://t.weather.sojson.com/api/weather/city/101040100";
            HttpURLConnection urlConnection = null;
            BufferedReader reader;
            try {
                URL url = new URL(stringUrl);
                // Create the request to get the information from the server, and open the connection
                urlConnection = (HttpURLConnection) url.openConnection();

                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                // Read the input stream into a String
                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    // Nothing to do.
                    return null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    // Mainly needed for debugging
                    Log.d("TAG", line);
                    buffer.append(line + "\n");
                }

                if (buffer.length() == 0) {
                    // Stream was empty.  No point in parsing.
                    return null;
                }
                //The temperature
                return buffer.toString();

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

       @Override
       protected void onPostExecute(String jsondata) {
           String tem,date,wea1,wea2,wea3,wea4,wea5,week1,week2,week3,week4,week5;
           try {
               JSONObject root = new JSONObject(jsondata);
               Log.d("TAG","date=" + root.getString("date"));
               date=root.getString("date");

               Log.d("TAG","data=" + root.getString("data"));
               String data=root.getString("data");
               JSONObject root2 = new JSONObject(data);
               Log.d("TAG","wendu=" + root2.getString("wendu"));
               tem=root2.getString("wendu");

               JSONArray forecast = root2.getJSONArray("forecast");
               wea1=forecast.getJSONObject(0).getString("type");
               wea2=forecast.getJSONObject(1).getString("type");
               wea3=forecast.getJSONObject(2).getString("type");
               wea4=forecast.getJSONObject(3).getString("type");
               wea5=forecast.getJSONObject(4).getString("type");

               JSONArray Forecast = root2.getJSONArray("forecast");
               week1=Forecast.getJSONObject(0).getString("week");
               week2=Forecast.getJSONObject(1).getString("week");
               week3=Forecast.getJSONObject(2).getString("week");
               week4=Forecast.getJSONObject(3).getString("week");
               week5=Forecast.getJSONObject(4).getString("week");

               if(wea1.equals("小雨")||wea1.equals("大雨"))
                   ((ImageView) findViewById(R.id.img_weather_condition)).setImageResource(R.drawable.rainy_small);
               if(wea1.equals("晴"))
                   ((ImageView) findViewById(R.id.img_weather_condition)).setImageResource(R.drawable.sunny_small);
               if(wea1.equals("阴")||wea1.equals("多云"))
                   ((ImageView) findViewById(R.id.img_weather_condition)).setImageResource(R.drawable.windy_small);

               if(wea2.equals("小雨")||wea1.equals("大雨"))
                   ((ImageView) findViewById(R.id.imageView_today1)).setImageResource(R.drawable.rainy_small);
               if(wea2.equals("晴"))
                   ((ImageView) findViewById(R.id.imageView_today1)).setImageResource(R.drawable.sunny_small);
               if(wea2.equals("阴")||wea2.equals("多云"))
                   ((ImageView) findViewById(R.id.imageView_today1)).setImageResource(R.drawable.windy_small);

               if(wea3.equals("小雨")||wea3.equals("大雨"))
                   ((ImageView) findViewById(R.id.imageView_today2)).setImageResource(R.drawable.rainy_small);
               if(wea3.equals("晴"))
                   ((ImageView) findViewById(R.id.imageView_today2)).setImageResource(R.drawable.sunny_small);
               if(wea3.equals("阴")||wea3.equals("多云"))
                   ((ImageView) findViewById(R.id.imageView_today2)).setImageResource(R.drawable.windy_small);

               if(wea4.equals("小雨")||wea4.equals("大雨"))
                   ((ImageView) findViewById(R.id.imageView_today3)).setImageResource(R.drawable.rainy_small);
               if(wea4.equals("晴"))
                   ((ImageView) findViewById(R.id.imageView_today3)).setImageResource(R.drawable.sunny_small);
               if(wea4.equals("阴")||wea4.equals("多云"))
                   ((ImageView) findViewById(R.id.imageView_today3)).setImageResource(R.drawable.windy_small);

               if(wea5.equals("小雨")||wea5.equals("大雨"))
                   ((ImageView) findViewById(R.id.imageView_today4)).setImageResource(R.drawable.rainy_small);
               if(wea5.equals("晴"))
                   ((ImageView) findViewById(R.id.imageView_today4)).setImageResource(R.drawable.sunny_small);
               if(wea5.equals("阴")||wea5.equals("多云"))
                   ((ImageView) findViewById(R.id.imageView_today4)).setImageResource(R.drawable.windy_small);

               ((TextView) findViewById(R.id.tv_today)).setText(week1);
               ((TextView) findViewById(R.id.tv_today1)).setText(week2);
               ((TextView) findViewById(R.id.tv_today2)).setText(week3);
               ((TextView) findViewById(R.id.tv_today3)).setText(week4);
               ((TextView) findViewById(R.id.tv_today4)).setText(week5);
               ((TextView) findViewById(R.id.temperature_of_the_day)).setText(tem);
               ((TextView) findViewById(R.id.tv_date)).setText(date);
           } catch (JSONException e) {
               e.printStackTrace();
           }

       }


    }
}
