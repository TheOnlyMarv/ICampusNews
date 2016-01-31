package de.fhws.mavlix.icampusnews;

import android.text.Html;
import android.util.Log;
import android.util.Xml;

/**
 * Created by Marvin on 14.01.2016.
 */
public class News {
    private int id;
    private String title;
    private String link;
    private String description;
    private String content;
    private String category;
    private String pubDate;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = Html.fromHtml(content).toString();
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getPubDate() {
        return pubDate;
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }

    @Override
    public String toString() {
        return "News{" +
                "title='" + title + '\'' +
                ", link='" + link + '\'' +
                ", description='" + description + '\'' +
                ", content='" + content + '\'' +
                ", category='" + category + '\'' +
                ", pubDate='" + pubDate + '\'' +
                '}';
    }
}
