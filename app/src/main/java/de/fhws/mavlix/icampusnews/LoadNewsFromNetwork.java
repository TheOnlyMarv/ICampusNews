package de.fhws.mavlix.icampusnews;

import android.os.AsyncTask;
import android.util.Log;

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

    private NetworkEvents context;

    public LoadNewsFromNetwork(NetworkEvents context){
        this.context = context;
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

                NewsParser parser = new NewsParser();
                return parser.parse(is);
                /*SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
                SAXParser saxParser = saxParserFactory.newSAXParser();
                NewsHandler handler = new NewsHandler();
                saxParser.parse(is,handler);*/
                //return handler.getNewsList();
            }
            /*catch (SAXException ex){
                onLoadError("Parserfehler");
            }*/
            catch (IOException ex){
                onLoadError("Keine Verbindung zum Internet");
            }
            catch (Exception ex){
                Log.e("ParserError", ex.toString());
                onLoadError("Fehler beim Parser");
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
        context.onLoadSuccessful(newsList);
    }

    @Override
    public void onLoadError(String msg) {
        context.onLoadError(msg);
    }
}
