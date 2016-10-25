package in.ac.nitrkl.innovisionr;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import uk.co.senab.photoview.PhotoViewAttacher;

public class MapActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        ImageView im= (ImageView) findViewById(R.id.map);
        PhotoViewAttacher atatcher=new PhotoViewAttacher(im);
        Glide.with(this)
                .load("http://innovision.nitrkl.ac.in/android/map.jpg")
                .error(R.drawable.map)
                .into(im);
    }
}
