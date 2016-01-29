package de.fhws.mavlix.icampusnews;

import android.os.AsyncTask;

import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

/**
 * Created by Marvin on 16.01.2016.
 */
public class LoadNewsFromNetwork extends AsyncTask<String, Void, List<News>> implements NetworkEvents {

    private NetworkEvents contex;

    public LoadNewsFromNetwork(NetworkEvents contex){
        this.contex = contex;
    }

    @Override
    protected List<News> doInBackground(String... params) {
        if(params == null || params.length == 0 ){
            onLoadError("Keine URL gefunden");
        }
        else{
            HttpURLConnection urlConnection = null;
            try{
                URL url = new URL(params[0]);
                urlConnection = (HttpURLConnection) url.openConnection();
                InputStream is = urlConnection.getInputStream();

                SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
                SAXParser saxParser = saxParserFactory.newSAXParser();
                NewsHandler handler = new NewsHandler();
                saxParser.parse(is,handler);
                return handler.getNewsList();
            }
            catch (SAXException ex){
                onLoadError("Parserfehler");
            }
            catch (IOException ex){
                onLoadError("Keine Verbindung zum Internet");
            }
            catch (Exception ex){
                onLoadError("Unbekannter Fehler");
            }
            finally {
                urlConnection.disconnect();
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(List<News> newses) {
        if(newses != null){
            onLoadSuccessful(newses);
        }
    }

    @Override
    public void onLoadSuccessful(List<News> newsList) {
        contex.onLoadSuccessful(newsList);
    }

    @Override
    public void onLoadError(String msg) {
        contex.onLoadError(msg);
    }
}
