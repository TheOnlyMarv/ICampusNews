package de.fhws.mavlix.icampusnews;

import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Marvin on 04.02.2016.
 */
public class NewsParser {
    // Constants indicting XML element names that we're interested in
    private static final int TAG_ID = 1;
    private static final int TAG_TITLE = 2;
    private static final int TAG_DESCRIPTION = 3;
    private static final int TAG_CONTENT = 4;
    private static final int TAG_CATEGORY = 5;
    private static final int TAG_PUBLISHED = 6;
    private static final int TAG_LINK = 7;

    // We don't use XML namespaces
    private static final String ns = null;

    public List<News> parse(InputStream in)
            throws XmlPullParserException, IOException, ParseException {
        try {
            XmlPullParser parser = Xml.newPullParser();
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(in, null);
            parser.nextTag();
            return readFeed(parser);
        } finally {
            in.close();
        }
    }

    private List<News> readFeed(XmlPullParser parser)
            throws XmlPullParserException, IOException, ParseException {
        List<News> entries = new ArrayList<News>();

        parser.require(XmlPullParser.START_TAG, ns, "rss");
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            if (name.equals("item")) {
                entries.add(readEntry(parser));
            } else {
                skip(parser);
            }
        }
        return entries;
    }

    private News readEntry(XmlPullParser parser)
            throws XmlPullParserException, IOException, ParseException {
        parser.require(XmlPullParser.START_TAG, ns, "item");
        String id = null;
        String title = null;
        String description = null;
        String content = null;
        String category = null;
        String link = null;
        String publishedOn = null;

        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            if (name.equals("title")) {
                title = readTag(parser, TAG_TITLE);
            } else if (name.equals("link")) {
                link = readTag(parser, TAG_LINK);
            } else if(name.equals("description")) {
                description = readTag(parser, TAG_DESCRIPTION);
            } else if(name.equals("content:encoded")) {
                content = readTag(parser, TAG_CONTENT);
            } else if(name.equals("category")){
                category = readTag(parser, TAG_CATEGORY);
            } else if (name.equals("pubDate")) {
                publishedOn = readTag(parser, TAG_PUBLISHED);
            } else {
                skip(parser);
            }
        }
        return new News(title,link,description,content,category,publishedOn);
    }

    private String readTag(XmlPullParser parser, int tagType)
            throws IOException, XmlPullParserException {
        String tag = null;
        String endTag = null;

        switch (tagType) {
            case TAG_ID:
                return readBasicTag(parser, "id");
            case TAG_TITLE:
                return readBasicTag(parser, "title");
            case TAG_LINK:
                return readBasicTag(parser, "link");
            case TAG_DESCRIPTION:
                return readBasicTag(parser, "description");
            case TAG_CONTENT:
                return readBasicTag(parser, "content:encoded");
            case TAG_CATEGORY:
                return readBasicTag(parser, "category");
            case TAG_PUBLISHED:
                return readBasicTag(parser, "pubDate");
            default:
                throw new IllegalArgumentException("Unknown tag type: " + tagType);
        }
    }

    private String readBasicTag(XmlPullParser parser, String tag)
            throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, tag);
        String result = readText(parser);
        parser.require(XmlPullParser.END_TAG, ns, tag);
        return result;
    }

    private String readText(XmlPullParser parser) throws IOException, XmlPullParserException {
        String result = null;
        if (parser.next() == XmlPullParser.TEXT) {
            result = parser.getText();
            parser.nextTag();
        }
        return result;
    }

    private void skip(XmlPullParser parser) throws XmlPullParserException, IOException {
        if (parser.getEventType() != XmlPullParser.START_TAG) {
            throw new IllegalStateException();
        }
        int depth = 1;
        while (depth != 0) {
            switch (parser.next()) {
                case XmlPullParser.END_TAG:
                    depth--;
                    if (parser.getName().equals("lastBuildDate"))
                        return;
                    break;
                case XmlPullParser.START_TAG:
                    depth++;
                    break;
            }
        }
    }
}
