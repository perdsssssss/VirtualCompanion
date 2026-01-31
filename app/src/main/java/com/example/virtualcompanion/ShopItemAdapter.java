package com.example.virtualcompanion;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ShopItemAdapter
        extends RecyclerView.Adapter<ShopItemAdapter.ViewHolder> {

    // Preview images (shop)
    private final int[] shopImages;

    // Equip images (on pet)
    private final int[] equipImages;

    // Prices (unused for now)
    private final String[] prices;

    private final OnItemClickListener listener;


    // CLICK INTERFACE
    public interface OnItemClickListener {
        void onItemClick(int equipResId);
    }


    // CONSTRUCTOR
    public ShopItemAdapter(
            int[] shopImages,
            int[] equipImages,
            String[] prices,
            OnItemClickListener listener
    ) {

        this.shopImages = shopImages;
        this.equipImages = equipImages;
        this.prices = prices;
        this.listener = listener;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(
            @NonNull ViewGroup parent,
            int viewType
    ) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_shop_item, parent, false);

        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(
            @NonNull ViewHolder holder,
            int position
    ) {

        holder.itemImage.setImageResource(
                shopImages[position]
        );

        holder.itemPrice.setText(prices[position]);


        holder.itemView.setOnClickListener(v -> {

            int equipRes = equipImages[position];

            if (listener != null) {
                listener.onItemClick(equipRes);
            }
        });
    }


    @Override
    public int getItemCount() {
        return shopImages.length;
    }


    static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView itemImage;
        TextView itemPrice;

        ViewHolder(View itemView) {
            super(itemView);

            itemImage = itemView.findViewById(R.id.itemImage);
            itemPrice = itemView.findViewById(R.id.itemPrice);
        }
    }
}
