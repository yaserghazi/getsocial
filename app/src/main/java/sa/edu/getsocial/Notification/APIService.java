package sa.edu.getsocial.Notification;





import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import sa.edu.getsocial.Notification.Notifications.MyResponse;
import sa.edu.getsocial.Notification.Notifications.Sender;

public interface APIService {
    @Headers(
            {
                    "Content-Type:application/json",
                    "Authorization:key=" +
                            "AAAAvLoQHhI:APA91bFxIIGE60OEO2vBaSDmRIvv3nHMMUyxFdiAkHIsAWQHBvBuD9TLuHr-PI1k33hZcdJQROayu5u9ww3bo5pDGWuFbbx42P1t2tACaQuRqxEwTWi_CWxHrhYH6A9x09-m3ja9Zrav"
            }
    )

    @POST("fcm/send")
    Call<MyResponse> sendNotification(@Body Sender body);
}
