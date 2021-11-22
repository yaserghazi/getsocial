package sa.edu.getsocial;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


public class WelcomeActivity extends AppCompatActivity {//static
    private static int SPLASH_SCREEN = 3000;
    public Button button;

    //variables for animations//
    Animation topAnim, bottomAnim;
    ImageView image;
    TextView welt, appnam, CoCo;
    Intent login;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.welcome_interface);

        // Animations
        topAnim = AnimationUtils.loadAnimation(this, R.anim.top_animation);
        bottomAnim = AnimationUtils.loadAnimation(this, R.anim.bottom_animation);

        //Hooks
        image = findViewById(R.id.PSAU_logo);
        welt = findViewById(R.id.Welocme);
        appnam = findViewById(R.id.Appname);
        CoCo = findViewById(R.id.CollegeCO);

        image.setAnimation(topAnim);
        welt.setAnimation(topAnim);
        appnam.setAnimation(bottomAnim);
        CoCo.setAnimation(bottomAnim);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(WelcomeActivity.this, login.class);
                startActivity(intent);

                finish();
            }
        }, SPLASH_SCREEN);
    }
}

