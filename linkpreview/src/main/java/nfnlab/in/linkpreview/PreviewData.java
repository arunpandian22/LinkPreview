package nfnlab.in.linkpreview;

import android.os.AsyncTask;
import android.util.Log;
import android.webkit.URLUtil;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;


/**
 * Created by arun on 24/1/18.
 */

public class PreviewData {

    LinkData linkData;
    MetaDataResponse responseListener;
    String url;
    String TAG="PreviewData";

    public PreviewData(String url,MetaDataResponse responseListener) {
        this.responseListener = responseListener;
        linkData = new LinkData();
        this.url=url;
        new getData().execute();
    }



    private class getData extends AsyncTask<Void , Void , Void> {


        @Override
        protected Void doInBackground(Void... params) {
            Document doc = null;
            try {
                Log.d(TAG, "doInBackground: url "+url);
                doc = Jsoup.connect(url).header("Accept-Encoding", "gzip, deflate")
                        .userAgent("Mozilla/5.0 (Windows NT 6.1; WOW64; rv:23.0) Gecko/20100101 Firefox/23.0")
                        .maxBodySize(0)
                        .timeout(600000)
                        .get();


                Elements elements = doc.getElementsByTag("meta");

                Log.d(TAG, "doInBackground: "+doc.toString());
                // getTitle doc.select("meta[property=og:title]")
                String title = doc.select("meta[property=og:title]").attr("content");

                if(title == null || title.isEmpty()) {
                    title = doc.title();
                }
                linkData.setTitle(title);

                //getDescription
                String description = doc.select("meta[name=description]").attr("content");
                if (description.isEmpty() || description == null) {
                    description = doc.select("meta[name=Description]").attr("content");
                }
                if (description.isEmpty() || description == null) {
                    description = doc.select("meta[property=og:description]").attr("content");
                }
                if (description.isEmpty() || description == null) {
                    description = "";
                }
                linkData.setDesc(description);


                // getMediaType
                Elements mediaTypes = doc.select("meta[name=medium]");
                String type = "";
                if (mediaTypes.size() > 0) {
                    String media = mediaTypes.attr("content");

                    type = media.equals("image") ? "photo" : media;
                } else {
                    type = doc.select("meta[property=og:type]").attr("content");
                }
                linkData.setMediaType(type);


                //getImages
                Elements imageElements = doc.select("meta[property=og:image]");
                if(imageElements.size() > 0) {
                    String image = imageElements.attr("content");
                    if(!image.isEmpty()) {
                        linkData.setImageUrl(resolveURL(url, image));
                    }
                }
                if(linkData.getImageUrl().isEmpty()) {
                    String src = doc.select("link[rel=image_src]").attr("href");
                    if (!src.isEmpty()) {
                        linkData.setImageUrl(resolveURL(url, src));
                    } else {
                        src = doc.select("link[rel=apple-touch-icon]").attr("href");
                        if(!src.isEmpty()) {
                            linkData.setBaseUrl(resolveURL(url, src));
                        } else {
                            src = doc.select("link[rel=icon]").attr("href");
                            if(!src.isEmpty()) {
                                linkData.setBaseUrl(resolveURL(url, src));
                            }
                        }
                    }
                }

                for(Element element : elements) {
                    if(element.hasAttr("property")) {
                        String str_property = element.attr("property").toString().trim();
                        if(str_property.equals("og:url")) {
                            linkData.setBaseUrl(element.attr("content").toString());
                        }
                        if(str_property.equals("og:site_name")) {
                            linkData.setWebSite(element.attr("content").toString());
                        }
                    }
                }

                if(linkData.getBaseUrl().equals("") || linkData.getBaseUrl().isEmpty()) {
                    URI uri = null;
                    try {
                        uri = new URI(url);
                    } catch (URISyntaxException e) {
                        e.printStackTrace();
                    }
                    if(url == null) {
                        linkData.setBaseUrl(url);
                    } else {
                        linkData.setBaseUrl(uri.getHost());
                    }
                }

            } catch (IOException e) {
                e.printStackTrace();
                responseListener.Error(e.getMessage());
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            responseListener.linkDataResponse(linkData);
        }
    }

    private String resolveURL(String url, String part) {
        if(URLUtil.isValidUrl(part)) {
            return part;
        } else {
            URI base_uri = null;
            try {
                base_uri = new URI(url);
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
            base_uri = base_uri.resolve(part);
            return base_uri.toString();
        }
    }
}
