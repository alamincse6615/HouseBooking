package com.wu.housebooking.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.wu.housebooking.R;
import com.wu.housebooking.model.BookingModel;
import com.wu.housebooking.model.ItemProperty;
import com.wu.housebooking.model.SignUpModel;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class BookingRequestToPOAdapter extends RecyclerView.Adapter<BookingRequestToPOAdapter.ItemRowHolder>{
    private Activity mContext;
    DatabaseReference bookingRef;
    private ArrayList<BookingModel> bookingModelArrayList = new ArrayList<>();

    public BookingRequestToPOAdapter(Activity mContext, ArrayList<BookingModel> bookingModelArrayList) {
        this.mContext = mContext;
        this.bookingModelArrayList = bookingModelArrayList;
    }

    @NonNull
    @NotNull
    @Override
    public ItemRowHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.property_request_item, parent, false);
        return new ItemRowHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ItemRowHolder holder, int position) {
        bookingRef = FirebaseDatabase.getInstance().getReference("Booking");
        final BookingModel signUpModel = bookingModelArrayList.get(position);
        holder.tv_name.setText(signUpModel.getUserName());
        holder.tv_phone.setText(signUpModel.getUserPhone());
        holder.tv_email.setText(signUpModel.getUserEmail());
        if (signUpModel.getUserAddress().equals("address") || signUpModel.getUserAddress()==null)
            //holder.tv_address.setText("");
        holder.tv_address.setVisibility(View.GONE);
        else
            holder.tv_address.setText(signUpModel.getUserAddress());
        //holder.tv_role.setVisibility(View.INVISIBLE);
        holder.tv_role.setText(" Approved ");
        holder.tv_role.setTextColor(mContext.getResources().getColor(R.color.colorAccent));
        holder.tv_role.setBackground(mContext.getResources().getDrawable(R.drawable.button_fb_bg));
        holder.tv_role.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bookingRef.child(bookingModelArrayList.get(position).getId()).child("isAcceptByPropertyOwner").setValue(true);
                Toast.makeText(mContext, "Approved"+bookingModelArrayList.get(position).getId(), Toast.LENGTH_SHORT).show();
                notify();

            }
        });

        holder.tv_delete.setText(" Delete ");
        holder.tv_delete.setTextColor(mContext.getResources().getColor(R.color.detail_circle_orange));
        holder.tv_delete.setBackground(mContext.getResources().getDrawable(R.drawable.button_google_bg));
        holder.tv_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bookingRef.child(bookingModelArrayList.get(position).getId()).child("isDeleteByPropertyOwner").setValue(true);
                Toast.makeText(mContext, "Delete", Toast.LENGTH_SHORT).show();

            }
        });
    }


    @Override
    public int getItemCount() {
        return (null != bookingModelArrayList ? bookingModelArrayList.size() : 0);
    }

    public class ItemRowHolder extends RecyclerView.ViewHolder {
        private TextView tv_name, tv_phone, tv_email,tv_address,tv_role,tv_delete;
        private LinearLayout lyt_parent;

        private ItemRowHolder(View itemView) {
            super(itemView);
            tv_name = itemView.findViewById(R.id.tv_name);
            tv_phone = itemView.findViewById(R.id.tv_phone);
            tv_email = itemView.findViewById(R.id.tv_email);
            lyt_parent = itemView.findViewById(R.id.lyt_parent);
            tv_role = itemView.findViewById(R.id.tv_role);
            tv_address = itemView.findViewById(R.id.tv_address);
            tv_delete = itemView.findViewById(R.id.tv_delete);
        }
    }
}
