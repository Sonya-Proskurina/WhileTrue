package com.example.whiletrue.ui.home.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.example.whiletrue.R;
import com.example.whiletrue.ui.home.model.Reviews;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdapterReviews extends
        RecyclerView.Adapter<AdapterReviews.MyViewHolder> {
    private List<Reviews> list;
    Context context;

    public AdapterReviews(List<Reviews> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.reviews_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.name.setText(list.get(position).getName());
        holder.rev.setText(list.get(position).getReviews());
        Glide.with(context).load(list.get(position).getImage())
                .thumbnail(0.5f)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(holder.men);

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

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView star1, star2, star3, star4, star5;
        CircleImageView men;
        TextView name,rev;

        MyViewHolder(View view) {
            super(view);
            star1 = view.findViewById(R.id.star1);
            star2 = view.findViewById(R.id.star2);
            star3 = view.findViewById(R.id.star3);
            star4 = view.findViewById(R.id.star4);
            star5 = view.findViewById(R.id.star5);
            name = view.findViewById(R.id.title);
            rev = view.findViewById(R.id.rev);
            men = view.findViewById(R.id.men);
        }

    }

}