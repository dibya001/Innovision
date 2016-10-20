package in.ac.nitrkl.innovisionr;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by surya on 19/10/16.
 */

public class SplashActivity extends AppCompatActivity {

    SharedPreferences sp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        sp=getSharedPreferences("demo_file",MODE_PRIVATE);
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

    }
}
