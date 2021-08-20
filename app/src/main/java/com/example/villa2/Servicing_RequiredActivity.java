package com.example.villa2;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


import com.example.villa2.interfaces.APIClient;
import com.example.villa2.interfaces.ApiInterface;
import com.example.villa2.models.CheckoutModel;
import com.example.villa2.models.ServicingModel;
import com.example.villa2.utils.AlertUtils;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Servicing_RequiredActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.btnServicing)
    TextView btnServicing;
    @BindView(R.id.tvServicing)
    TextView tvServicingH;
    @BindView(R.id.tvServicingdes)
    TextView tvServicingDes;
    @BindView(R.id.btnCheckoutServicing)
    TextView btnCheckout;
    @BindView(R.id.tvDateService)
    TextView tvDateService;

    Context context;

    Dialog dialog;
    private boolean doublePressedOnce = false;
    private Handler mHandler = new Handler();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_servicing__required);

        iniListener();
    }

    private void iniListener() {
        ButterKnife.bind(this);
        btnCheckout.setOnClickListener(this);
        btnServicing.setOnClickListener(this);
        dialog = AlertUtils.createProgressDialog(this);
        Calendar calendar = Calendar.getInstance();

        int currentYear = calendar.get(Calendar.YEAR);
        int currentMonth = calendar.get(Calendar.MONTH) + 1;
        int currentDay = calendar.get(Calendar.DAY_OF_MONTH);

        tvDateService.setText(""+ currentDay +"/" + currentMonth+"/"+currentYear );


    }




    private void Checkout() {
        dialog.show();
        ApiInterface services = APIClient.getApiClient().create(ApiInterface.class);
        Call<CheckoutModel> checkout = services.checkout();

        checkout.enqueue(new Callback<CheckoutModel>() {
            @Override
            public void onResponse(Call<CheckoutModel> call, Response<CheckoutModel> response) {
                startActivity(new Intent(Servicing_RequiredActivity.this,ThankYouActivity.class));
                dialog.dismiss();
            }

            @Override
            public void onFailure(Call<CheckoutModel> call, Throwable t) {
                Toast.makeText(Servicing_RequiredActivity.this, "error", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });

    }


    private void Servicing() {
        dialog.show();
        ApiInterface services = APIClient.getApiClient().create(ApiInterface.class);
        Call<ServicingModel> servicing = services.servicing();

        servicing.enqueue(new Callback<ServicingModel>() {
            @Override
            public void onResponse(Call<ServicingModel> call, Response<ServicingModel> response) {
                btnServicing.setBackgroundResource(R.drawable.servicing_green);
                btnServicing.setText("Servicing Requested");
                dialog.dismiss();
            }

            @Override
            public void onFailure(Call<ServicingModel> call, Throwable t) {
                Toast.makeText(Servicing_RequiredActivity.this, "error", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });

    }


    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.btnCheckoutServicing:
                Checkout();
                break;

            case R.id.btnServicing:

                if (doublePressedOnce==false) {
                    tvServicingH.setText("Request Send");

                    tvServicingDes.setText("  ");
                    Servicing();
                    this.doublePressedOnce = true;

                }else if(doublePressedOnce ==true){
                    btnServicing.setBackgroundResource(R.drawable.servicing_red);
                    this.doublePressedOnce = false;
                    btnServicing.setText("Request Servicing");
                    tvServicingH.setText(" Request Servicing");
                    tvServicingDes.setText("If you require servicing please notify us the night before by pressing the button below Servicing is undertaken from 10 am on stays 3 nights or longer.");


                }

                break;

        }
    }
}


