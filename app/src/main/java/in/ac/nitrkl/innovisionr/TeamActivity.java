package in.ac.nitrkl.innovisionr;


<<<<<<< HEAD
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;

public class TeamActivity extends AppCompatActivity implements View.OnClickListener {

    LinearLayoutManager lLayout;
    LinearLayout a,b,c;
    RecyclerView allEvents;
    ArrayList<Team> eventDetails;

    private static final int PERMISSION_REQUEST_CODE = 1;

=======
        import android.graphics.Color;
        import android.os.Bundle;
        import android.support.v7.app.AppCompatActivity;
        import android.support.v7.widget.GridLayoutManager;
        import android.support.v7.widget.LinearLayoutManager;
        import android.support.v7.widget.RecyclerView;
        import android.support.v7.widget.Toolbar;

        import java.util.ArrayList;

public class TeamActivity extends AppCompatActivity {

    LinearLayoutManager lLayout;

    RecyclerView allEvents;
    ArrayList<Team> eventDetails;

>>>>>>> 17dc1fc2c2abfeb7eb7ac1e1fcb509f5e044ca45
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team);
        Toolbar tb= (Toolbar) findViewById(R.id.tb);
        setSupportActionBar(tb);
        tb.setTitleTextColor(Color.WHITE);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_icon);

<<<<<<< HEAD

        a=(LinearLayout)findViewById(R.id.mohit);
        b=(LinearLayout) findViewById(R.id.call2);
        c=(LinearLayout) findViewById(R.id.call3);

        a.setOnClickListener(this);
        b.setOnClickListener(this);
        c.setOnClickListener(this);

        // requestPermission();
    }

    private void requestPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && ContextCompat.checkSelfPermission(TeamActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED)
        {
            if (ActivityCompat.shouldShowRequestPermissionRationale(TeamActivity.this, Manifest.permission.CALL_PHONE ))
            {
                //This is called if user has denied the permission before
                //In this case I am just asking the permission again
                ActivityCompat.requestPermissions(TeamActivity.this, new String[]{Manifest.permission.CALL_PHONE}, PERMISSION_REQUEST_CODE);

            }else {
                ActivityCompat.requestPermissions(TeamActivity.this, new String[]{Manifest.permission.CALL_PHONE}, PERMISSION_REQUEST_CODE);
            }
        }
    }

    private boolean checkPermission(){
        int result = ContextCompat.checkSelfPermission(TeamActivity.this, Manifest.permission.CALL_PHONE);
        if (result == PackageManager.PERMISSION_GRANTED){
            return true;
        } else {
            return false;
        }
    }

    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.mohit:
                if (!checkPermission()) {
                    requestPermission();
                    Log.d("na","dont");
                } else {
                    Intent callIntent = new Intent(Intent.ACTION_DIAL);
                    callIntent.setData(Uri.parse("tel:8596049882"));
                    startActivity(callIntent);

                }
                break;

            case R.id.call2:
                if (!checkPermission()) {
                    requestPermission();
                    Log.d("na","dont");
                } else {
                    Intent callIntent = new Intent(Intent.ACTION_DIAL);
                    callIntent.setData(Uri.parse("tel:9439036279"));
                    startActivity(callIntent);

                }
                break;


            case R.id.call3:
                if (!checkPermission()) {
                    requestPermission();
                    Log.d("na","dont");
                } else {
                    Intent callIntent = new Intent(Intent.ACTION_DIAL);
                    callIntent.setData(Uri.parse("tel:7749805245"));
                    startActivity(callIntent);

                }
                break;


        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    //Snackbar.make(view,"Permission Granted, Now you can access location data.",Snackbar.LENGTH_LONG).show();

                } else {

                    //Snackbar.make(view,"Permission Denied, You cannot access location data.",Snackbar.LENGTH_LONG).show();

                }
                break;
        }
=======
>>>>>>> 17dc1fc2c2abfeb7eb7ac1e1fcb509f5e044ca45
    }

}