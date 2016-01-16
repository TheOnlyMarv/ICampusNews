package de.fhws.mavlix.icampusnews;

import java.util.List;

/**
 * Created by Marvin on 16.01.2016.
 */
public interface NetworkEvents {
    void onLoadSuccessful(List<News> newsList);
    void onLoadError(String msg);
}
