package com.example.levon.jsonproject.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.levon.jsonproject.R;
import com.example.levon.jsonproject.activities.MainActivity;
import com.example.levon.jsonproject.fragments.BigPictureDialog;
import com.example.levon.jsonproject.models.JsonModel;
import com.squareup.picasso.Picasso;

import java.util.List;

public class UserItemsAdapter extends RecyclerView.Adapter<UserItemsAdapter.ItemViewHolder> {

    private Context context;
    private List<JsonModel> list;

    public UserItemsAdapter(Context context, List<JsonModel> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.user_item_style, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        Picasso.get().load(list.get(position).getThumbnailUrl()).into(holder.imageView);
        holder.textView.setText(list.get(position).getTitle());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView;

        ItemViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.user_image);
            textView = itemView.findViewById(R.id.user_title);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ((MainActivity) context).setJsonModel(list.get(getAdapterPosition()));
                    BigPictureDialog bigPictureDialog = new BigPictureDialog();
                    bigPictureDialog.show(((MainActivity) context).getSupportFragmentManager(), "tag");
                }
            });
        }
    }
}
