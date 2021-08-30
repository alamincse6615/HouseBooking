package com.wu.housebooking;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class PaymentActivity extends AppCompatActivity {

    Spinner payment_type;
    TextView txv_number;
    EditText edt_amount;
    EditText edt_transaction_number;
    String bkash = "+8801773451990";
    String DBBL = "+88017734519904";
    String nogod = "+8801737309731";
    TextView tv_continue;
    String thisBookingId = "";
    DatabaseReference bookingRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        bookingRef = FirebaseDatabase.getInstance().getReference("Booking");
        //Extract the data…
        Bundle bundle = getIntent().getExtras();
        if (bundle!=null)
            thisBookingId = bundle.getString("thisBookingId");

        payment_type = findViewById(R.id.payment_type);
        txv_number = findViewById(R.id.txv_number);
        edt_amount = findViewById(R.id.edt_amount);
        edt_transaction_number = findViewById(R.id.edt_transaction_number);
        tv_continue = findViewById(R.id.tv_continue);

        String[] paymentType = new String[] {"Bkash", "DBBL","Nogdod"};
        ArrayAdapter<String> adapterQualityTypeties = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, paymentType);
        adapterQualityTypeties.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        payment_type.setAdapter(adapterQualityTypeties);
        payment_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0){
                    txv_number.setText("Bkash Personal "+bkash);
                }
                if (position == 1){
                    txv_number.setText("Rocket Personal "+DBBL);
                }
                if (position == 2){
                    txv_number.setText("Nogod Personal "+nogod);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        tv_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((Integer.parseInt(edt_amount.getText().toString().trim())>0) && (String.valueOf(edt_transaction_number.getText().toString().trim()).length()>10)){
                    bookingRef.child(thisBookingId).child("isPayment").setValue(true);
                    bookingRef.child(thisBookingId).child("paymentAmountByUser").setValue(edt_amount.getText().toString().trim());
                    bookingRef.child(thisBookingId).child("paymentMethod").setValue(txv_number.getText().toString().trim());
                    bookingRef.child(thisBookingId).child("paymentTransactionId").setValue(edt_transaction_number.getText().toString().trim());

                    new AlertDialog.Builder(PaymentActivity.this)
                            .setTitle("Payment Done")
                            .setCancelable(false)
                            .setIcon(R.drawable.ic_pata)
                            .setMessage("Your Payment is Successfully done done..")
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which){
                                    Intent intent = new Intent(PaymentActivity.this,DashboardActivity.class);
                                    startActivity(intent);
                                }
                            }).show();

                }else{
                    Toast.makeText(PaymentActivity.this, "File Can't Empty.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}