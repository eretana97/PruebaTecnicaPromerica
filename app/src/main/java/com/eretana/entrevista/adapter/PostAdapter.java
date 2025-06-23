package com.eretana.entrevista.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.eretana.entrevista.R;
import com.eretana.entrevista.models.XPost;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder> {

    Context ctx;
    List<XPost> xposts;

    public PostAdapter(Context ctx, List<XPost> xposts) {
        this.ctx = ctx;
        this.xposts = xposts;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(ctx).inflate(R.layout.item_post,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        XPost post = xposts.get(position);

        if(post.getUsername() != null){
            holder.post_username.setText(post.getUsername());
            Log.e("USERNAME", post.getUsername());
        }


        holder.post_body.setText(post.getBody());
        holder.post_title.setText(post.getTitle());
        holder.post_time.setText("Hace " + post.getMinutes() + " m");



    }

    @Override
    public int getItemCount() {
        return xposts.size();
    }



    public static class ViewHolder extends RecyclerView.ViewHolder {
        CircleImageView post_picture;
        TextView post_username;
        TextView post_time;
        TextView post_title;
        TextView post_body;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            post_username = itemView.findViewById(R.id.post_username);
            post_time = itemView.findViewById(R.id.post_time);
            post_title = itemView.findViewById(R.id.post_title);
            post_body = itemView.findViewById(R.id.post_body);
        }
    }

}
