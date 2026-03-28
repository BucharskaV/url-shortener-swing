package pl.edu.s30853.s30853tpo10.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.URL;
import pl.edu.s30853.s30853tpo10.constraint.Password;
import pl.edu.s30853.s30853tpo10.constraint.UniqueUrl;

public class LinkDTO {
    private String id;

    @NotBlank(message = "{msg.name.notBlank}")
    @Size(min = 5, max = 20, message = "{msg.name.size}")
    private String name;

    @NotBlank(message = "{msg.targetUrl.notBlank}")
    @URL(protocol = "https", message = "{msg.targetUrl.protocol}")
    @UniqueUrl
    private String targetUrl;
    private String redirectUrl;
    @Password
    private String password;
    private long visitsCount;

    public LinkDTO() {}
    public LinkDTO(String id, String name, String targetUrl, String redirectUrl, String password, long visitsCount) {
        this.id = id;
        this.name = name;
        this.targetUrl = targetUrl;
        this.redirectUrl = redirectUrl;
        this.password = password;
        this.visitsCount = visitsCount;
    }

    public String getRedirectUrl() {
        return redirectUrl;
    }

    public void setRedirectUrl(String redirectUrl) {
        this.redirectUrl = redirectUrl;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public long getVisitsCount() {
        return visitsCount;
    }

    public void setVisitsCount(long visitsCount) {
        this.visitsCount = visitsCount;
    }
}
