package com.example.villa2;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.villa2.interfaces.APIClient;
import com.example.villa2.interfaces.ApiInterface;
import com.example.villa2.models.CheckInResponse;
import com.example.villa2.utils.AlertUtils;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ThankYouActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.ivLogoThanks)
    ImageView btnCheckIn;
    @BindView(R.id.tvDateThanks)
    TextView tvDateThanks;

    Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thank_you);
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

        tvDateThanks.setText(""+ currentDay +"/" + currentMonth+"/"+currentYear );


    }

    private void Checkout() {
        dialog.show();
        ApiInterface services = APIClient.getApiClient().create(ApiInterface.class);
        Call<CheckInResponse> checkout = services.checkIn();

        checkout.enqueue(new Callback<CheckInResponse>() {
            @Override
            public void onResponse(Call<CheckInResponse> call, Response<CheckInResponse> response) {

                if (response.isSuccessful()) {
                    Toast.makeText(ThankYouActivity.this, "Successful", Toast.LENGTH_SHORT).show();
                    finishAffinity();
                    startActivity(new Intent(ThankYouActivity.this, Servicing_RequiredActivity.class));
                    dialog.dismiss();

                }
            }

            @Override
            public void onFailure(Call<CheckInResponse> call, Throwable t) {
                Log.d("zma error", String.valueOf(t));
                dialog.dismiss();
            }
        });
    }


    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.ivLogoThanks:
                startActivity(new Intent(this,MainActivity.class));
                break;

        }
    }

}