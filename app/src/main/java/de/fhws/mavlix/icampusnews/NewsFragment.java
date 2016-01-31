package de.fhws.mavlix.icampusnews;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class NewsFragment extends Fragment {

    View inflatedView = null;
    News news = null;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        inflatedView = inflater.inflate(R.layout.fragment_news, container, false);
        initialViews(inflatedView);
        return inflatedView;
    }

    private void initialViews(View inflatedView) {
        if (news != null) {
            NewsViewAdapter adapter = new NewsViewAdapter(news);
            RecyclerView recyclerView = (RecyclerView) inflatedView.findViewById(R.id.recyclerViewNews);
            recyclerView.setLayoutManager(new GridLayoutManager(this.getActivity(), 1));
            recyclerView.setAdapter(adapter);
        } else {
            Snackbar.make(inflatedView, "Keine Nachricht übergeben", Snackbar.LENGTH_LONG).show();
        }
    }

    public NewsFragment LoadNews(News news) {
        this.news = news;
        return this;
    }
}
