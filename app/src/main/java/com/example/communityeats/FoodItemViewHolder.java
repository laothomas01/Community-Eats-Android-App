package com.example.communityeats;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class FoodItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
{
    public TextView foodItemName, foodItemQuantity, foodItemDescription;
    public ImageView foodImage;


    public FoodItemViewHolder(@NonNull View itemView) {
        super(itemView);

        foodImage = (ImageView) itemView.findViewById(R.id.item_image);
        foodItemName = (TextView) itemView.findViewById(R.id.item_name);
        foodItemQuantity = (TextView) itemView.findViewById(R.id.item_quantity);
        foodItemDescription = (TextView) itemView.findViewById(R.id.item_description);

        itemView.setOnClickListener(this);
        
    }

    @Override
    public void onClick(View v) {

    }
}
