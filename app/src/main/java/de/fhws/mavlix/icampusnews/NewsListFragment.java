package de.fhws.mavlix.icampusnews;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.List;

public class NewsListFragment extends Fragment implements NetworkEvents {

    NewsViewAdapter newsViewAdapter;
    View inflatedView = null;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        inflatedView = inflater.inflate(R.layout.fragment_news_list,container,false);
        initialViews(inflatedView);
        return inflatedView;
    }

    public void initialViews(View inflatedView){
        ProgressBar progressBar = (ProgressBar)inflatedView.findViewById(R.id.progressbar);
        progressBar.setVisibility(View.VISIBLE);
        RecyclerView recyclerView = (RecyclerView)inflatedView.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this.getActivity(),1));
        final NetworkEvents context = this;
        new LoadNewsFromNetwork(context).execute("http://www.welearn.de/rss.xml");
    }

    @Override
    public void onLoadSuccessful(List<News> newsList) {
        Log.d("Success", "onLoadSuccessful: Liste fertig runtergeladen");
        newsViewAdapter = new NewsViewAdapter(newsList, R.layout.cardview_news, getActivity()) ;
        RecyclerView recyclerView = (RecyclerView)inflatedView.findViewById(R.id.recyclerView);
        recyclerView.setAdapter(newsViewAdapter);
        ProgressBar progressBar = (ProgressBar)inflatedView.findViewById(R.id.progressbar);
        progressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onLoadError(String msg) {
        //Toast.makeText(getContext(), msg, Toast.LENGTH_LONG).show();
        Log.e("Error", "onLoadError: " + msg);
    }
}
