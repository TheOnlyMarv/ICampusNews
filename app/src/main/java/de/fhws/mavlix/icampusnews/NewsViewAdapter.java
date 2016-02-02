package de.fhws.mavlix.icampusnews;

import android.support.v7.widget.RecyclerView;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Marvin on 31.01.2016.
 */
public class NewsViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static News news;

    public NewsViewAdapter(News news) {
        this.news = news;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == 0) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_headline, parent, false);
            return new HeadlineViewHolder(v);
        } else if (viewType == 1) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_text, parent, false);
            return new TextViewHolder(v);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (position == 0) {
            ((HeadlineViewHolder) holder).assignData(news);
        } else if (position == 1) {
            ((TextViewHolder) holder).assignData(news);
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public static class HeadlineViewHolder extends RecyclerView.ViewHolder {

        private final TextView date, title;

        News news;

        public HeadlineViewHolder(View itemView) {
            super(itemView);
            date = (TextView) itemView.findViewById(R.id.newsDate);
            title = (TextView) itemView.findViewById(R.id.newsTitle);
        }

        public void assignData(News news) {
            this.news = news;
            date.setText(news.getPubDate());
            title.setText(news.getTitle());
        }
    }

    public static class TextViewHolder extends RecyclerView.ViewHolder {

        private final TextView text;

        News news;

        public TextViewHolder(View itemView) {
            super(itemView);
            text = (TextView) itemView.findViewById(R.id.newsText);
        }

        public void assignData(News news) {
            this.news = news;
            this.text.setText(news.getContent());
        }
    }
}
