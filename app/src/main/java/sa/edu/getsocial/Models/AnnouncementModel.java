package sa.edu.getsocial.Models;

import com.google.firebase.database.Exclude;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class AnnouncementModel {
    String  id;
    String title;

    String announcement;
    String link;
    long createdAt;

    public AnnouncementModel() {
    }

    public AnnouncementModel(String id, String title, String announcement, String link) {
        this.id = id;
        this.title = title;
        this.announcement = announcement;
        this.link = link;
        createdAt = new Date().getTime();

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAnnouncement() {
        return announcement;
    }

    public void setAnnouncement(String announcement) {
        this.announcement = announcement;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    @Exclude
    public String getFormattedTime() {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd", Locale.US);
        Date date = new Date();
        date.setTime(createdAt);
        return sdf.format(date);
    }
}
