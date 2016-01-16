package de.fhws.mavlix.icampusnews;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.List;

/**
 * Created by Marvin on 10.01.2016.
 */
public class TestFragment extends Fragment implements NetworkEvents {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View inflatedView = inflater.inflate(R.layout.fragment_test,container,false);
        initialElementEvent(inflatedView);
        return inflatedView;
    }

    public void initialElementEvent(View inflatedView){
        Button btn = (Button)inflatedView.findViewById(R.id.testButton);
        final NetworkEvents context = this;
        btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                new LoadNewsFromNetwork(context).execute("http://www.welearn.de/rss.xml");
            }
        });
    }

    @Override
    public void onLoadSuccessful(List<News> newsList) {
        for(News news : newsList){
            Log.d("News", news.toString());
        }

    }

    @Override
    public void onLoadError(String msg) {

    }
}
