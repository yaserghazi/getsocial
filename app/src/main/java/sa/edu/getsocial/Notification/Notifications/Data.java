package sa.edu.getsocial.Notification.Notifications;

public class Data {
    private String user;
    private String title;
    private String body;

    public Data(String user, String body, String title) {
        this.user = user;
        this.body = body;
        this.title = title;
    }

    public Data() {
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


}
