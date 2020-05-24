package gr.csd.plantsreminder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        ImageView imageView = findViewById(R.id.imageView);
        TextView textView = findViewById(R.id.textView);

        Animation fromLeft;
        fromLeft = AnimationUtils.loadAnimation(this, R.anim.from_left_delay);
        imageView.setAnimation(fromLeft);

        Animation fromRight;
        fromRight = AnimationUtils.loadAnimation(this, R.anim.from_right_delay);
        textView.setAnimation(fromRight);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                SharedPreferences preferences = getSharedPreferences("User", MODE_PRIVATE);
                if (!preferences.getBoolean("introduced", false))
                    changeIntent(MainActivity.class);
                else
                    changeIntent(MyPlantsActivity.class);
            }
        }, 2000);
    }

    private void changeIntent(Class mClass) {
        Intent intent = new Intent(SplashActivity.this, mClass);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }
}
