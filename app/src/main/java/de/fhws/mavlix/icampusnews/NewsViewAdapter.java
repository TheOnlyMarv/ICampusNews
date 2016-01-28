package de.fhws.mavlix.icampusnews;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Marvin on 28.01.2016.
 */
public class NewsViewAdapter extends RecyclerView.Adapter<NewsViewAdapter.NewsViewHolder> {
    private static List<News> newsList;
    private int layout;
    public static Context context;

    public NewsViewAdapter(List<News> news, int layout, Context context){
        this.newsList = news;
        this.layout = layout;
        NewsViewAdapter.context = context;
    }

    @Override
    public NewsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);
        NewsViewHolder newsViewHolder = new NewsViewHolder(v);
        return newsViewHolder;
    }

    @Override
    public void onBindViewHolder(NewsViewHolder holder, int position) {
        final News news = this.newsList.get(position);
        holder.assignData(news);
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public static class NewsViewHolder extends RecyclerView.ViewHolder{

        private final TextView date, headline,description;

        public NewsViewHolder(View itemView) {
            super(itemView);
            date = (TextView)itemView.findViewById(R.id.date);
            headline = (TextView)itemView.findViewById(R.id.headline);
            description = (TextView)itemView.findViewById(R.id.card_view);
        }

        public void assignData(News news){
            this.date.setText(news.getPubDate());
            this.headline.setText(news.getTitle());
            this.description.setText(news.getDescription());
        }
    }
}
