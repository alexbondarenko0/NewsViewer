package com.alexbondarenko.newsviewer.adapters;

import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alexbondarenko.newsviewer.R;
import com.alexbondarenko.newsviewer.activities.NewsDetailActivity;
import com.alexbondarenko.newsviewer.pojo.news.ArticlesItem;

import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {

    private final LayoutInflater inflater;
    private final List<ArticlesItem> articles;

    public NewsAdapter(Context context, List<ArticlesItem> articles){
        this.inflater = LayoutInflater.from(context);
        this.articles = articles;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = inflater.inflate(R.layout.news_list_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        ArticlesItem article = articles.get(i);
        viewHolder.tvAuthor.setText(article.getAuthor());
        viewHolder.tvTitle.setText(article.getTitle());
        viewHolder.tvPublishedAt.setText(article.getPublishedAt());

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(inflater.getContext(), NewsDetailActivity.class);
                intent.putExtra(ArticlesItem.class.getSimpleName(), article);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                inflater.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return articles.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        final TextView tvAuthor, tvPublishedAt, tvTitle;
        public ViewHolder(View itemView) {
            super(itemView);
            tvAuthor = itemView.findViewById(R.id.tvAuthor);
            tvPublishedAt = itemView.findViewById(R.id.tvPublishedAt);
            tvTitle = itemView.findViewById(R.id.tvTitle);

        }
    }
}
