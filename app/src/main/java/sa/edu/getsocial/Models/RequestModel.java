package sa.edu.getsocial.Models;

import com.google.firebase.database.Exclude;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class RequestModel {
    String  id;
    String title;

    String details;
    String UID;
    String StudentName;

    String time;

    public RequestModel() {
    }

    public RequestModel(String id, String title, String details, String UID,String StudentName) {
        this.id = id;
        this.title = title;
        this.details = details;
        this.UID = UID;
        time = String.valueOf(System.currentTimeMillis());

        this.StudentName = StudentName;

    }

    public String getStudentName() {
        return StudentName;
    }

    public void setStudentName(String studentName) {
        StudentName = studentName;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getUID() {
        return UID;
    }

    public void setUID(String UID) {
        this.UID = UID;
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

    @Exclude
    public String getFormattedTime(String createdAt) {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd", Locale.US);
        Date date = new Date();
        date.setTime(Long.parseLong(createdAt));
        return sdf.format(date);
    }
}
