package pl.edu.s30853.s30853tpo10.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Link {
    @Id
    @Column(length = 10)
    private String id;
    @Column(name = "Name")
    private String name;
    @Column(name = "TargetUrl")
    private String targetUrl;
    @Column(name = "Password")
    private String password;
    @Column(name = "Visits")
    private long visitsCount;

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
