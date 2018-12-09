package secondapp.weatherapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import secondapp.weatherapp.Data.ApiKey;
import secondapp.weatherapp.Data.DailyForecast;
import secondapp.weatherapp.Data.Day;
import secondapp.weatherapp.Data.Maximum;
import secondapp.weatherapp.Data.Minimum;
import secondapp.weatherapp.Data.Temperature;
import secondapp.weatherapp.Data.WeatherAPI;
import secondapp.weatherapp.Data.WeatherResponse;

public class MainActivity extends AppCompatActivity {


    private TextView textView1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
ApiKey api=new ApiKey();
        textView1 = (TextView) findViewById(R.id.textview);
        final Call<WeatherResponse> jsonData = WeatherAPI.getRedditService().getWeather(api.getApikey());
        jsonData.enqueue(new Callback<WeatherResponse>() {
            @Override
            public void onResponse(Call<WeatherResponse> call, Response<WeatherResponse> response) {
                StringBuilder strBuilder=new StringBuilder("");
               List<DailyForecast> dailyForecastList=  response.body().getDailyForecasts();
                for (DailyForecast df:dailyForecastList) {
                    Day day=df.getDay();
                    String date=df.getDate();
                    Temperature temp=df.getTemperature();
                    Maximum maximum=temp.getMaximum();
                    Minimum min=temp.getMinimum();
                    int minTemp,maxTemp;
                    minTemp= Integer.valueOf(min.getValue());
                    maxTemp= Integer.valueOf(maximum.getValue());
                    String morning=day.getIconPhrase();
                    String night=df.getNight().getIconPhrase();
                    strBuilder.append(date+"\n"+minTemp+" "+maxTemp+" \n"+morning+"\n\n");

                }
                textView1.setText(strBuilder) ;

            }

            @Override
            public void onFailure(Call<WeatherResponse> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Failed to get response", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
