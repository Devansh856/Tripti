package com.example.tripti;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;
import com.shashank.sony.fancytoastlib.FancyToast;

import org.json.JSONObject;

public class donate_money extends AppCompatActivity implements PaymentResultListener {
    EditText amt;
    Button donate;
    String email;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        email=getIntent().getStringExtra("email");
        Checkout.preload(getApplicationContext());
        Checkout checkout = new Checkout();
        checkout.setKeyID("rzp_test_l4KSC6KznHNMPS,tCk3WVXQUfvcvJ1v1t1HHBU8");


        setContentView(R.layout.activity_donate_money);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        amt = findViewById(R.id.amount);
        donate = findViewById(R.id.donatebutton);


        donate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                donate.setVisibility(View.INVISIBLE);
                startPayment();

            }
        });


    }

    public void startPayment() {
        String amount = amt.getText().toString();
        Checkout checkout = new Checkout();
        checkout.setKeyID("rzp_test_l4KSC6KznHNMPS");
        /**
         * Instantiate Checkout
         */

        amount = String.valueOf(Integer.parseInt(amount) * 100);
        /**
         * Set your logo here
         */
        checkout.setImage(R.mipmap.splash1);

        /**
         * Reference to current activity
         */
        final Activity activity = this;

        /**
         * Pass your payment options to the Razorpay Checkout as a JSONObject
         */
        try {
            JSONObject options = new JSONObject();

            options.put("name", "Tripti Food Organisation");
            options.put("description", "You are paying to Tripti Food Donation Reference No. TR" + System.currentTimeMillis());
            options.put("image", "https://s3.amazonaws.com/rzp-mobile/images/rzp.jpg");
            //  options.put("order_id", "order_DBJOWzybf0sJbb");//from response of step 3.
            options.put("theme.color", "#b57b28");
            options.put("currency", "INR");
            options.put("amount", amount);//pass amount in currency subunits
            options.put("prefill.email", email);
            options.put("prefill.contact", "8279745113");
            JSONObject retryObj = new JSONObject();
            retryObj.put("enabled", true);
            retryObj.put("max_count", 4);
            options.put("retry", retryObj);

            checkout.open(activity, options);

        } catch (Exception e) {
            FancyToast.makeText(this,"Check your Internet Connection",FancyToast.LENGTH_SHORT,FancyToast.ERROR,false);

            Checkout.clearUserData(this);
        }
    }


    @Override
    public void onPaymentSuccess(String s) {
        donate.setVisibility(View.VISIBLE);
        AlertDialog builder = new AlertDialog.Builder(this).setTitle("Transaction")
                .setMessage("Payment Successfull" +
                        "Thank you for helping us \nPress OK Button")
                .setCancelable(false)

                .setIcon(R.drawable.baseline_payment_24)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent=new Intent(donate_money.this, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        finishAndRemoveTask();
                        finish();
                        startActivity(intent);
                    }
                }).show();


        //Toast.makeText(this, "Payment Successfull", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPaymentError(int i, String s) {
        if (i == Checkout.NETWORK_ERROR) {
            donate.setVisibility(View.VISIBLE);
            FancyToast.makeText(this,"Check your Internet Connection",FancyToast.LENGTH_SHORT,FancyToast.ERROR,false).show();
        } else {
            donate.setVisibility(View.VISIBLE);

            FancyToast.makeText(this,"Payment Cancelled",FancyToast.LENGTH_SHORT,FancyToast.ERROR,false).show();
        }
    }
}