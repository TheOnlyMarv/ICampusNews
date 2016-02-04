package de.fhws.mavlix.icampusnews;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.List;
/**
 * Created by Marvin on 14.01.2016.
 */
public class NewsHandler extends DefaultHandler {

    private List<News> newsList = null;
    private News news = null;

    public List<News> getNewsList() {
        return newsList;
    }

    boolean bTitle = false;
    boolean bLink = false;
    boolean bDescription = false;
    boolean bContent = false;
    boolean bCategory = false;
    boolean bPubDate = false;

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        //super.startElement(uri, localName, qName, attributes);
        if (qName.equalsIgnoreCase("item")) {
            int id = newsList == null ? 0 : newsList.size();
            news = new News();
            news.setId(id);
            if (newsList == null)
                newsList = new ArrayList<>();
        } else if(qName.equalsIgnoreCase("title")){
            bTitle = true;
        } else if (qName.equalsIgnoreCase("link")) {
            bLink = true;
        } else if (qName.equalsIgnoreCase("description")) {
            bDescription = true;
        } else if (qName.equalsIgnoreCase("content:encoded")) {
            bContent = true;
        } else if (qName.equalsIgnoreCase("category")) {
            bCategory = true;
        } else if(qName.equalsIgnoreCase("pubDate")){
            bPubDate = true;
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        //super.characters(ch, start, length);
        if(news == null){
            return;
        }

        if(bTitle){
            news.setTitle(new String(ch, start, length));
            bTitle=false;
        } else if (bLink) {
            news.setLink(new String(ch, start, length));
            bLink = false;
        } else if (bDescription) {
            news.setDescription(new String(ch, start, length));
            bDescription = false;
        } else if (bContent) {
            news.setContent(new String(ch, start, length));
            bContent = false;
        } else if (bCategory) {
            news.setCategory(new String(ch, start, length));
            bCategory = false;
        } else if (bPubDate){
            news.setPubDate(new String(ch, start, length));
            bPubDate = false;
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        //super.endElement(uri, localName, qName);
        if (qName.equalsIgnoreCase("item")) {
            newsList.add(news);
        }
    }
}
