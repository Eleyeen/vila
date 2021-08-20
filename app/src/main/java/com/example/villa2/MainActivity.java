package com.example.villa2;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.Dialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.agrawalsuneet.dotsloader.loaders.CircularDotsLoader;
import com.example.villa2.interfaces.APIClient;
import com.example.villa2.interfaces.ApiInterface;
import com.example.villa2.models.CheckInResponse;
import com.example.villa2.utils.AlertUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    @BindView(R.id.btnCheckIn)
    TextView btnCheckIn;
    @BindView(R.id.date)
    TextView tvDate;
    Dialog dialog;
    @BindView(R.id.dlCircularDotsLoader)
    CircularDotsLoader circularDotsLoader;
    String year;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        iniListener();
    }



    private void iniListener() {
        ButterKnife.bind(this);
        btnCheckIn.setOnClickListener(this);
        dialog = AlertUtils.createProgressDialog(this);

        Calendar calendar = Calendar.getInstance();

        int currentYear = calendar.get(Calendar.YEAR);
        int currentMonth = calendar.get(Calendar.MONTH) + 1;
        int currentDay = calendar.get(Calendar.DAY_OF_MONTH);

        tvDate.setText(""+ currentDay +"/" + currentMonth+"/"+currentYear );


        CircularDotsLoader loader = new CircularDotsLoader(this);
        loader.setDefaultColor(ContextCompat.getColor(this, R.color.purple_500));
        loader.setSelectedColor(ContextCompat.getColor(this, R.color.purple_700));
        loader.setBigCircleRadius(80);
        loader.setRadius(24);
        loader.setAnimDur(300);
        loader.setShowRunningShadow(true);
        loader.setFirstShadowColor(ContextCompat.getColor(this, R.color.purple_500));
        loader.setSecondShadowColor(ContextCompat.getColor(this, R.color.purple_700));
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void CheckIn() {

        dialog.show();
        Toast.makeText(this, "checkin", Toast.LENGTH_SHORT).show();
        ApiInterface services = APIClient.getApiClient().create(ApiInterface.class);
        Call<CheckInResponse> checkIn = services.checkIn();

        checkIn.enqueue(new Callback<CheckInResponse>() {
            @Override
            public void onResponse(Call<CheckInResponse> call, Response<CheckInResponse> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(MainActivity.this, "Successful", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(MainActivity.this, Servicing_RequiredActivity.class));
                    dialog.dismiss();

                }
            }

            @Override
            public void onFailure(Call<CheckInResponse> call, Throwable t) {
                dialog.dismiss();
                Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();
            }
        });
    }
    public static String getDayFromDateString(String stringDate,String dateTimeFormat)
    {
        String[] daysArray = new String[] {"saturday","sunday","monday","tuesday","wednesday","thursday","friday"};
        String day = "";

        int dayOfWeek =0;
        //dateTimeFormat = yyyy-MM-dd HH:mm:ss
        SimpleDateFormat formatter = new SimpleDateFormat(dateTimeFormat);
        Date date;
        try {
            date = formatter.parse(stringDate);
            Calendar c = Calendar.getInstance();
            c.setTime(date);
            dayOfWeek = c.get(Calendar.DAY_OF_WEEK)-1;
            if (dayOfWeek < 0) {
                dayOfWeek += 7;
            }
            day = daysArray[dayOfWeek];
        } catch (Exception e) {
            e.printStackTrace();
        }

        return day;
    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.btnCheckIn:
                CheckIn();
                break;

        }
    }


}