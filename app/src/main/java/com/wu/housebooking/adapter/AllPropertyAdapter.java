package com.wu.housebooking.adapter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.wu.housebooking.R;
import com.wu.housebooking.model.ItemProperty;
import com.wu.housebooking.property.PropertyDetailsActivity;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class AllPropertyAdapter extends RecyclerView.Adapter<AllPropertyAdapter.ItemRowHolder> implements Filterable {

    private ArrayList<ItemProperty> dataList;
    private Activity mContext;
    boolean isFavorite = false;
    private ArrayList<ItemProperty> dataListFiltered;

    public AllPropertyAdapter(Activity context, ArrayList<ItemProperty> dataList) {
        this.dataList = dataList;
        this.dataListFiltered = dataList;
        this.mContext = context;
    }

    @NonNull
    @NotNull
    @Override
    public ItemRowHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_property_item, parent, false);
        return new ItemRowHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ItemRowHolder holder, int position) {
        final ItemProperty singleItem = dataListFiltered.get(position);
        holder.text.setText(singleItem.getPropertyName());
        holder.textPrice.setText(mContext.getString(R.string.currency_symbol) + singleItem.getPropertyPrice());
        holder.textAddress.setText(singleItem.getPropertyAddress());
        if (singleItem.getFeatured_image().length()>10)
            Picasso.get().load(singleItem.getFeatured_image()).placeholder(R.drawable.icon).into(holder.image);

       /* if (singleItem.isFav()) {
            holder.ic_home_fav.setImageResource(R.drawable.ic_fav_hover);
        } else {
            holder.ic_home_fav.setImageResource(R.drawable.ic_fav);
        }
*/
        holder.ic_home_fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isFavorite){
                    isFavorite=false;
                    holder.ic_home_fav.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_fav));
                    // holder.ic_home_fav.setImageResource(R.drawable.ic_fav);

                }else {
                    isFavorite=true;
                    holder.ic_home_fav.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_fav_hover));
                    // holder.ic_home_fav.setImageResource(R.drawable.ic_fav_hover);
                }
            }
        });

        holder.lyt_parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("propertyUid", String.valueOf(singleItem.getpId()));
                Intent intent = new Intent(mContext, PropertyDetailsActivity.class);
                intent.putExtras(bundle);
                mContext.startActivity(intent);

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
        return (null != dataListFiltered ? dataListFiltered.size() : 0);
    }

    @Override
    public Filter getFilter() {

        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {

                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    dataListFiltered = dataList;
                } else {
                    ArrayList<ItemProperty> filteredList = new ArrayList<>();
                    for (ItemProperty row : dataList) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.getPropertyName().toLowerCase().contains(charString.toLowerCase()) || row.getPropertyAddress().contains(charSequence)) {
                            filteredList.add(row);
                        }
                    }

                    dataListFiltered = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = dataListFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                //dataListFiltered = (ArrayList<ItemProperty>) results.values;
                dataListFiltered = (ArrayList<ItemProperty>) results.values;
                notifyDataSetChanged();
            }
        };
    }

    public class ItemRowHolder extends RecyclerView.ViewHolder {
        public ImageView image, ic_home_fav;
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
            ic_home_fav = itemView.findViewById(R.id.ic_home_fav);
        }
    }
}
