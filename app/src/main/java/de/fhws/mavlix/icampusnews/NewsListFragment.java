package de.fhws.mavlix.icampusnews;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import java.util.List;

public class NewsListFragment extends Fragment implements NetworkEvents {

    NewsListViewAdapter newsListViewAdapter;
    View inflatedView = null;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        inflatedView = inflater.inflate(R.layout.fragment_news_list,container,false);
        loadNewsList();
        return inflatedView;
    }

    public void loadNewsList(){
        ProgressBar progressBar = (ProgressBar)inflatedView.findViewById(R.id.progressbar);
        progressBar.setVisibility(View.VISIBLE);
        RecyclerView recyclerView = (RecyclerView)inflatedView.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this.getActivity(),1));
        new LoadNewsFromNetwork(this).execute("http://www.welearn.de/rss.xml");
    }

    @Override
    public void onLoadSuccessful(List<News> newsList) {
        Log.d("Success", "onLoadSuccessful: Liste fertig runtergeladen");
        newsListViewAdapter = new NewsListViewAdapter(newsList, R.layout.cardview_news, (NewsListViewAdapter.Events) getActivity());
        RecyclerView recyclerView = (RecyclerView)inflatedView.findViewById(R.id.recyclerView);
        recyclerView.setAdapter(newsListViewAdapter);
        ProgressBar progressBar = (ProgressBar)inflatedView.findViewById(R.id.progressbar);
        progressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onLoadError(final String msg) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Snackbar.make(inflatedView, msg, Snackbar.LENGTH_LONG).show();
                ProgressBar progressBar = (ProgressBar)inflatedView.findViewById(R.id.progressbar);
                progressBar.setVisibility(View.INVISIBLE);
            }
        });
        Log.e("Error", "onLoadError: " + msg);
    }

}
