package com.moddakir.call.Adapters;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.widget.ImageViewCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.card.MaterialCardView;
import com.moddakir.call.view.widget.TextViewUniqueLight;
import com.moddakir.call.Model.ConnectingBanner;
import com.moddakir.call.R;

import java.util.List;

public class ConnectingScreenAdapter extends RecyclerView.Adapter<ConnectingScreenAdapter.ConnectingScreenViewHolder> {
    List<ConnectingBanner> connectingBanner;
    ConnectingScreenViewHolder holder;
    private Context context;
    int colorPrimary;
    private int Pos = 0;


    public ConnectingScreenAdapter(Context context, List<ConnectingBanner> connectingBanner,int colorPrimary) {
        this.connectingBanner = connectingBanner;
        this.context = context;
        this.colorPrimary = colorPrimary;
    }

    @NonNull
    @Override
    public ConnectingScreenViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.connecting_item, parent, false);
        holder = new ConnectingScreenViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ConnectingScreenViewHolder holder, int position) {
        holder.bind(connectingBanner.get(position));

    }

    @Override
    public int getItemCount() {
        return connectingBanner.size();
    }


    public class ConnectingScreenViewHolder extends RecyclerView.ViewHolder {
        ImageView connecting_iv;
        TextViewUniqueLight connecting_text;
        AppCompatImageView shadow;

        public ConnectingScreenViewHolder(@NonNull View itemView) {
            super(itemView);
            connecting_iv = itemView.findViewById(R.id.connecting_iv);
            connecting_text = itemView.findViewById(R.id.connecting_text);
            shadow = itemView.findViewById(R.id.shadow);
        }

        public void bind(ConnectingBanner connectingBanner) {
            try {
                Glide.with(context)
                        .load(connectingBanner.getUrl())
                        .into(connecting_iv);
            } catch (Exception e) {

            }
            if (colorPrimary!=0) {
                int alpha = 128; // 50% opacity (range: 0-255)
                int colorWithOpacity = Color.argb(alpha, Color.red(colorPrimary), Color.green(colorPrimary), Color.blue(colorPrimary));
                shadow.setBackgroundColor(colorWithOpacity);
            }
            Pos = connectingBanner.getIndex()-1;
            connecting_text.setText(connectingBanner.getTitle());
        }


    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    public int currentPos() {
        return Pos;
    }
}
