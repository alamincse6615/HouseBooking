package com.wu.housebooking.adapter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;
import com.wu.housebooking.R;
import com.wu.housebooking.model.ItemProperty;
import com.wu.housebooking.property.MyPropertyDetailsActivity;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class PropertyRequesteAdapter extends RecyclerView.Adapter<PropertyRequesteAdapter.ItemRowHolder> {
    DatabaseReference databaseReference;
    private ArrayList<ItemProperty> dataList;
    private Activity mContext;
    boolean isFavorite = false;

    String accept_result;

    public PropertyRequesteAdapter(Activity context, ArrayList<ItemProperty> dataList) {
        this.dataList = dataList;
        this.mContext = context;
    }

    @NonNull
    @NotNull
    @Override
    public ItemRowHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.pending_property_item, parent, false);
        return new ItemRowHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ItemRowHolder holder, int position) {
        databaseReference = FirebaseDatabase.getInstance().getReference("Propertys");
        final ItemProperty singleItem = dataList.get(position);
        holder.text.setText(singleItem.getPropertyName());
        holder.textPrice.setText(mContext.getString(R.string.currency_symbol) + singleItem.getPropertyPrice());
        holder.textAddress.setText(singleItem.getPropertyAddress());
        if (singleItem.getFeatured_image().length()>5)
        Picasso.get().load(singleItem.getFeatured_image()).placeholder(R.drawable.icon).into(holder.image);

        String[] propertyTypeItems = new String[] {"--select-->","Approved", "Delete"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(mContext,
                android.R.layout.simple_spinner_item, propertyTypeItems);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        holder.sp_approved_or_delete.setAdapter(adapter);
        holder.sp_approved_or_delete.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int posi, long id) {
                accept_result = propertyTypeItems[posi];
                if (posi==1){
                    databaseReference.child(dataList.get(position).getpId()).child("isApprovedByAdmin").setValue(true);
                    Toast.makeText(mContext, "Approved ", Toast.LENGTH_SHORT).show();
                }
                if (posi==1){
                    databaseReference.child(dataList.get(position).getpId()).child("isDeleteByAdmin").setValue(true);
                    Toast.makeText(mContext, "Approved ", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        holder.lyt_parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("propertyUid", String.valueOf(singleItem.getpId()));
                Intent intent = new Intent(mContext, MyPropertyDetailsActivity.class);
                intent.putExtras(bundle);
                //mContext.startActivity(intent);

                //PopUpAds.ShowInterstitialAds(mContext, singleItem.getPId());
            }
        });

        holder.txtPurpose.setText(singleItem.getPropertyPurpose());
        if (mContext.getResources().getString(R.string.isRTL).equals("true")) {
            holder.txtPurpose.setBackgroundResource(singleItem.getPropertyPurpose().equals("Rent") ? R.drawable.rent_right_button : R.drawable.sale_right_button);
        } else {
            holder.txtPurpose.setBackgroundResource(singleItem.getPropertyPurpose().equals("Rent") ? R.drawable.rent_left_button : R.drawable.sale_left_button);
        }

    }

    @Override
    public int getItemCount() {
        return (null != dataList ? dataList.size() : 0);
    }


    public class ItemRowHolder extends RecyclerView.ViewHolder {
        public ImageView image;
        public Spinner sp_approved_or_delete;
        private TextView text, textPrice, textAddress, txtPurpose;
        private LinearLayout lyt_parent;

        private ItemRowHolder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image);
            text = itemView.findViewById(R.id.text);
            textPrice = itemView.findViewById(R.id.textPrice);
            textAddress = itemView.findViewById(R.id.textAddress);
            txtPurpose = itemView.findViewById(R.id.textPurpose);
            lyt_parent = itemView.findViewById(R.id.rootLayout);
            sp_approved_or_delete = itemView.findViewById(R.id.sp_approved_or_delete);
        }
    }
}
