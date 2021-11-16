package com.example.communityeats;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.communityeats.model.FoodDonationItem;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.FoodItemViewHolder> {

    Context context;
    ArrayList<FoodDonationItem> list;

    public MyAdapter(Context context, ArrayList<FoodDonationItem> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public FoodItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View v = LayoutInflater.from(context).inflate(R.layout.food_item_layout, parent, false);
        return new FoodItemViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodItemViewHolder holder, int position) {
        FoodDonationItem foodItem = list.get(position);


        holder.foodItemName.setText(foodItem.getFoodName());
        holder.foodItemQuantity.setText(foodItem.getFoodQuantity());
        holder.foodItemDescription.setText(foodItem.getFoodDescription());

        Glide.with(context).load(foodItem.getFoodImageUrl()).into(holder.foodImage);
        // System.out.println("IMAGE URL:" + foodItem.getFoodImageUrl());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class FoodItemViewHolder extends RecyclerView.ViewHolder
    {
        public TextView foodItemName, foodItemQuantity, foodItemDescription;
        public ImageView foodImage;


        public FoodItemViewHolder(@NonNull View itemView) {
            super(itemView);

            foodImage = (ImageView) itemView.findViewById(R.id.item_image);
            foodItemName = (TextView) itemView.findViewById(R.id.item_name);
            foodItemQuantity = (TextView) itemView.findViewById(R.id.item_quantity);
            foodItemDescription = (TextView) itemView.findViewById(R.id.item_description);


        }
    }
}


