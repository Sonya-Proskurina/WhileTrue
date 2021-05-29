package com.example.whiletrue.ui.home.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.whiletrue.R;
import com.example.whiletrue.ui.home.BigTopIndActivity;
import com.example.whiletrue.ui.home.model.TopModel;

import java.util.List;

public class AdapterTop extends
        RecyclerView.Adapter<AdapterTop.MyViewHolder> {
    private List<TopModel> list;
    Context context;
    int likes=1;


    public AdapterTop(List<TopModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.top_model_card, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.title.setText(list.get(position).getTitle());
        holder.please.setText(list.get(position).getPlease());

        if (list.get(position).getNum() == 1) {
            holder.num.setBackgroundResource(R.drawable.ic_one);
        } else if (position == 2) {
            holder.num.setBackgroundResource(R.drawable.ic_two);
        } else {
            holder.num.setBackgroundResource(R.drawable.ic_free);
        }

        switch (list.get(position).getStar()){
            case 1:
                holder.star1.setBackgroundResource(R.drawable.ic_star);
                break;
            case 2:
                holder.star1.setBackgroundResource(R.drawable.ic_star2);
                holder.star2.setBackgroundResource(R.drawable.ic_star2);
                break;
            case 3:
                holder.star1.setBackgroundResource(R.drawable.ic_star2);
                holder.star2.setBackgroundResource(R.drawable.ic_star2);
                holder.star3.setBackgroundResource(R.drawable.ic_star2);
                break;
            case 4:
                holder.star1.setBackgroundResource(R.drawable.ic_star2);
                holder.star2.setBackgroundResource(R.drawable.ic_star2);
                holder.star3.setBackgroundResource(R.drawable.ic_star2);
                holder.star4.setBackgroundResource(R.drawable.ic_star2);
                break;
            case 5:
                holder.star1.setBackgroundResource(R.drawable.ic_star2);
                holder.star2.setBackgroundResource(R.drawable.ic_star2);
                holder.star3.setBackgroundResource(R.drawable.ic_star2);
                holder.star4.setBackgroundResource(R.drawable.ic_star2);
                holder.star5.setBackgroundResource(R.drawable.ic_star2);
                break;
        }

        holder.like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (likes==1){
                    holder.like.setBackgroundResource(R.drawable.ic_baseline_favorite_24);
                    likes=0;
                }
                else {
                    likes=1;
                    holder.like.setBackgroundResource(R.drawable.ic_baseline_favorite_border_24);
                }
            }
        });

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, BigTopIndActivity.class));
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView star1, star2, star3, star4, star5, like, num;
        TextView title, please;
        CardView cardView;

        MyViewHolder(View view) {
            super(view);
            star1 = view.findViewById(R.id.star1);
            star2 = view.findViewById(R.id.star2);
            star3 = view.findViewById(R.id.star3);
            star4 = view.findViewById(R.id.star4);
            star5 = view.findViewById(R.id.star5);
            like = view.findViewById(R.id.like);
            title = view.findViewById(R.id.title);
            please = view.findViewById(R.id.please);
            num = view.findViewById(R.id.num);
            cardView = view.findViewById(R.id.root);
        }

    }

}