package pl.edu.s30853.s30853tpo10.model;

public class LinkResponseDTO {
    private String id;
    private String name;
    private String targetUrl;
    private String redirectUrl;
    private long visits;

    public LinkResponseDTO() {}
    public LinkResponseDTO(String id, String name, String targetUrl, String redirectUrl, long visits) {
        this.id = id;
        this.name = name;
        this.targetUrl = targetUrl;
        this.redirectUrl = redirectUrl;
        this.visits = visits;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTargetUrl() {
        return targetUrl;
    }

    public void setTargetUrl(String targetUrl) {
        this.targetUrl = targetUrl;
    }

    public String getRedirectUrl() {
        return redirectUrl;
    }

    public void setRedirectUrl(String redirectUrl) {
        this.redirectUrl = redirectUrl;
    }

    public long getvisits() {
        return visits;
    }

    public void setvisits(long visits) {
        this.visits = visits;
    }

    @Override
    public String toString() {
        return "Link{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", targetUrl='" + targetUrl + '\'' +
                ", redirectUrl='" + redirectUrl + '\'' +
                ", visits=" + visits +
                "}";
    }
}
