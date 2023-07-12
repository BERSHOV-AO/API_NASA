import com.fasterxml.jackson.annotation.JsonProperty;

public class NASA {


//    {
//        "copyright": "Bill Peters",
//            "date": "2020-07-17",
//            "explanation": "After local midnight on July 14 comet NEOWISE was still above the horizon for Goldenrod, Alberta, Canada, just north of Calgary, planet Earth. In this snapshot it makes for an awesome night with dancing displays of the northern lights. The long-tailed comet and auroral displays are beautiful apparitions in the north these days. Both show the influence of spaceweather and the wind from the Sun. Skygazers have widely welcomed the visitor from the Oort cloud, though C/2020 F3 (NEOWISE) is in an orbit that is now taking it out of the inner Solar System.  Comet NEOWISE Images: July 16 | July 15 | July 14 | July 13 | July 12 | July 11 | July 10 & earlier",
//            "hdurl": "https://apod.nasa.gov/apod/image/2007/DSC1028_PetersNEOWISEAuroralSpike.jpg",
//            "media_type": "image",
//            "service_version": "v1",
//            "title": "NEOWISE of the North",
//            "url": "https://apod.nasa.gov/apod/image/2007/DSC1028_PetersNEOWISEAuroralSpike_800.jpg"
//    }


    private final String copyright;   // авторские права
    private final String date;
    private final String explanation; // объяснение
    private final String hdurl;
    private final String media_type;
    private final String service_version;
    private final String title;
    private final String url;


    //@JsonProperty("userId") int userId,
    public NASA(
            @JsonProperty("copyright") String copyright,
            @JsonProperty("date") String date,
            @JsonProperty("explanation") String explanation,
            @JsonProperty("hdurl") String hdurl,
            @JsonProperty("media_type") String media_type,
            @JsonProperty("service_version") String service_version,
            @JsonProperty("title") String title,
            @JsonProperty("url") String url
    ) {
        this.copyright = copyright;
        this.date = date;
        this.explanation = explanation;
        this.hdurl = hdurl;
        this.media_type = media_type;
        this.service_version = service_version;
        this.title = title;
        this.url = url;
    }

    @Override
    public String toString() {
        return "NASA: " +
                "\n copyright='" + copyright +
                "\n date='" + date +
                "\n explanation='" + explanation +
                "\n hdurl='" + hdurl +
                "\n media_type='" + media_type +
                "\n service_version='" + service_version +
                "\n title='" + title +
                "\n url='" + url;
    }

    public String getCopyright() {
        return copyright;
    }

    public String getDate() {
        return date;
    }

    public String getExplanation() {
        return explanation;
    }

    public String getHdurl() {
        return hdurl;
    }

    public String getMedia_type() {
        return media_type;
    }

    public String getService_version() {
        return service_version;
    }

    public String getTitle() {
        return title;
    }

    public String getUrl() {
        return url;
    }
}
