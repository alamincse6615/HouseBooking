package com.wu.housebooking.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.wu.housebooking.R;
import com.wu.housebooking.model.SignUpModel;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class AllUsersAdapter extends RecyclerView.Adapter<AllUsersAdapter.ItemRowHolder>{
    private Activity mContext;

    private ArrayList<SignUpModel> signUpModelArrayList = new ArrayList<>();

    public AllUsersAdapter(Activity mContext, ArrayList<SignUpModel> signUpModelArrayList) {
        this.mContext = mContext;
        this.signUpModelArrayList = signUpModelArrayList;
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

        final SignUpModel signUpModel = signUpModelArrayList.get(position);
        holder.tv_name.setText(signUpModel.getStrFullname());
        holder.tv_phone.setText(signUpModel.getStrMobi());
        holder.tv_email.setText(signUpModel.getStrEmail());
        holder.tv_role.setText(signUpModel.getUserType());
    }

    @Override
    public int getItemCount() {
        return (null != signUpModelArrayList ? signUpModelArrayList.size() : 0);
    }

    public class ItemRowHolder extends RecyclerView.ViewHolder {
        private TextView tv_name, tv_phone, tv_email,tv_role;
        private LinearLayout lyt_parent;

        private ItemRowHolder(View itemView) {
            super(itemView);
            tv_name = itemView.findViewById(R.id.tv_name);
            tv_phone = itemView.findViewById(R.id.tv_phone);
            tv_email = itemView.findViewById(R.id.tv_email);
            lyt_parent = itemView.findViewById(R.id.lyt_parent);
            tv_role = itemView.findViewById(R.id.tv_role);
        }
    }
}
