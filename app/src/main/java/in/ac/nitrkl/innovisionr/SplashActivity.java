package in.ac.nitrkl.innovisionr;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;

/**
 * Created by surya on 19/10/16.
 */

public class SplashActivity extends AppCompatActivity {

    SharedPreferences sp;
    ImageView img;
    RelativeLayout l;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(sp.getString("userid","-1").equals("-1"))
                    startActivity(new Intent(SplashActivity.this,LoginActivity.class));
                else
                {
                    startActivity(new Intent(SplashActivity.this,HomeActivity.class));
                }
                finish();
            }
        },3000);


    sp=getSharedPreferences("demo_file",MODE_PRIVATE);

        l= (RelativeLayout) findViewById(R.id.activity_main);

        l.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeActivity();

            }


        });
/*
        img= (ImageView) findViewById(R.id.splashlogo);
         Animation animation = AnimationUtils.loadAnimation(this, R.anim.rotate_around_center_point);
        img.startAnimation(animation);*/
//        changeActivity();
    }

    private void changeActivity() {
                if(sp.getString("userid","-1").equals("-1"))
                    startActivity(new Intent(SplashActivity.this,LoginActivity.class));
                else
                {
                    startActivity(new Intent(SplashActivity.this,HomeActivity.class));
                }
                finish();
            }

    }



