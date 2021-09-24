package com.wu.housebooking;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class PaymentActivity extends AppCompatActivity {

    Spinner payment_type;
    TextView txv_number;
    TextView tv_ragular_cal;
    TextView edt_amount;
    TextView tv_date;
    ImageView image;
    TextView tv_property_name;
    TextView textAddress;
    TextView tv_discount;
    TextView tv_ragular_price;
    TextView tv_persentange_price;
    EditText edt_transaction_number;
    String bkash = "+8801773451990";
    String DBBL = "+88017734519904";
    String nogod = "+8801737309731";
    TextView tv_continue;
    String thisBookingId = "";
    String propertyId = "";
    String imgUrl = "";
    String propertyName = "";
    String propertyLoc = "";
    String discount = "";
    String price = "";
    String PayableAmount = "";

    String bookingStartDate = "";
    String bookingEndDate = "";
    DatabaseReference bookingRef,propertyRef;
    public static final String DATE_FORMAT = "d/M/yyyy";  //or use "M/d/yyyy"


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        bookingRef = FirebaseDatabase.getInstance().getReference("Booking");



        //Extract the data…
        Bundle bundle = getIntent().getExtras();
        if (bundle!=null) {
            thisBookingId = bundle.getString("thisBookingId");
            propertyId = bundle.getString("propertyId");
            imgUrl = bundle.getString("imgUrl");
            propertyName = bundle.getString("propertyName");
            propertyLoc = bundle.getString("propertyLoc");
            discount = bundle.getString("discount");
            price = bundle.getString("price");
        }

        image = findViewById(R.id.image);
        tv_property_name = findViewById(R.id.tv_property_name);
        textAddress = findViewById(R.id.textAddress);

        tv_ragular_cal = findViewById(R.id.tv_ragular_cal);
        tv_date = findViewById(R.id.tv_date);
        tv_discount = findViewById(R.id.tv_discount);
        tv_ragular_price = findViewById(R.id.tv_ragular_price);
        tv_persentange_price = findViewById(R.id.tv_persentange);
        payment_type = findViewById(R.id.payment_type);
        txv_number = findViewById(R.id.txv_number);
        edt_amount = findViewById(R.id.edt_amount);
        edt_transaction_number = findViewById(R.id.edt_transaction_number);
        tv_continue = findViewById(R.id.tv_continue);

        Picasso.get().load(imgUrl).into(image);
        tv_property_name.setText(propertyName);
        textAddress.setText(propertyLoc);
        tv_discount.setText("Discount : "+discount +"% off");
        tv_ragular_price.setText("1  day  = 1*"+price+" = "+getString(R.string.currency_symbol)+price);

        //tv_ragular_price.setPaintFlags(tv_ragular_price.getPaintFlags()| Paint.STRIKE_THRU_TEXT_FLAG);


        bookingRef.child(thisBookingId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                bookingStartDate = String.valueOf(snapshot.child("booking_date").getValue());
                bookingEndDate = String.valueOf(snapshot.child("return_date").getValue());
                tv_date.setText(bookingStartDate+" ----> "+bookingEndDate +" total "+getDaysBetweenDates(bookingStartDate,bookingEndDate)+" days" );
                tv_ragular_cal.setText(getDaysBetweenDates(bookingStartDate,bookingEndDate)+" days = "+getDaysBetweenDates(bookingStartDate,bookingEndDate)+" * "+price+" = "+getString(R.string.currency_symbol)+discountedPrice(Integer.parseInt(price),getDaysBetweenDates(bookingStartDate,bookingEndDate)));
                tv_persentange_price.setText("Total Payable Price = "+getString(R.string.currency_symbol)+""+discountedPrice(Integer.parseInt(price),Integer.parseInt(discount),getDaysBetweenDates(bookingStartDate,bookingEndDate)));
                PayableAmount = String.valueOf(discountedPrice(Integer.parseInt(price),Integer.parseInt(discount),getDaysBetweenDates(bookingStartDate,bookingEndDate)));
                edt_amount.setText(getString(R.string.currency_symbol)+""+discountedPrice(Integer.parseInt(price),Integer.parseInt(discount),getDaysBetweenDates(bookingStartDate,bookingEndDate)));
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

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
                if ((String.valueOf(edt_transaction_number.getText().toString().trim()).length()>10)){
                    bookingRef.child(thisBookingId).child("isPayment").setValue(true);
                    bookingRef.child(thisBookingId).child("paymentAmountByUser").setValue(PayableAmount);
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


    public static long getDaysBetweenDates(String start, String end) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT, Locale.ENGLISH);
        Date startDate, endDate;
        long numberOfDays = 0;
        try {
            startDate = dateFormat.parse(start);
            endDate = dateFormat.parse(end);
            numberOfDays = getUnitBetweenDates(startDate, endDate, TimeUnit.DAYS);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return numberOfDays;
    }

    private static long getUnitBetweenDates(Date startDate, Date endDate, TimeUnit unit) {
        long timeDiff = endDate.getTime() - startDate.getTime();
        return unit.convert(timeDiff, TimeUnit.MILLISECONDS);
    }

    public double discountedPrice(int price,int percentage,long day){

        return day*(price - ((price * percentage) / 100));
    }
    public double discountedPrice(int price,long day){

        return (day*price);
    }
}