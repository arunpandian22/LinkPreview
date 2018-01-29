package nfnlab.in.linkpreview;

/**
 * Created by arun on 24/1/18.
 */

public class LinkData {
    public String imageUrl="", title, desc,baseUrl,mediaType,webSite;
    public LinkData(){

    }

    public LinkData(String imageUrl, String title, String desc, String baseUrl,String mediaType,String webSite){
        this.imageUrl=imageUrl;
        this.baseUrl=baseUrl;
        this.desc = desc;
        this.title = title;
        this.mediaType=mediaType;
        this.webSite=webSite;
    }

    public String getMediaType(){
      return mediaType;
    }
    public String getWebSite(){
        return webSite;
    }

    public void setWebSite(String webSite){
        this.webSite=webSite;
    }

    public void setMediaType(String mediaType){
        this.mediaType=mediaType;
    }
    public String getBaseUrl() {
        return baseUrl;
    }

    public String getDesc() {
        return desc;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
