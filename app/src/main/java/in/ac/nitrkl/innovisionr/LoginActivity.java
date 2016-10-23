package in.ac.nitrkl.innovisionr;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by surya on 19/10/16.
 */

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";
    private static final int REQUEST_SIGNUP = 0;
    EditText email, password;
    Button login;
    SharedPreferences.Editor edit;
    TextView _signupLink,skip;
    String loggedin;
    String _email, _password;
    SharedPreferences sp;
    String loginurl = "http://innovision.nitrkl.ac.in/android/login.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar tb = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(tb);
        ImageView iv= (ImageView) findViewById(R.id.img);
        iv.setTranslationY(-2000f);
        iv.animate().translationYBy(2000f).setDuration(1000);
        sp = getSharedPreferences("demo_file", MODE_PRIVATE);
        edit = sp.edit();
        email = (EditText) findViewById(R.id.email);
        skip= (TextView) findViewById(R.id.skip);
        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this,HomeActivity.class));
                finish();
                //Toast.makeText(LoginActivity.this,"HII",Toast.LENGTH_LONG).show();
                // finish();
            }
        });
        password = (EditText) findViewById(R.id.password);
        login = (Button) findViewById(R.id.btn_login);
        _signupLink = (TextView) findViewById(R.id.link_signup);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                senddata();
            }
        });
        _signupLink.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Start the Signup activity
                Intent intent = new Intent(getApplicationContext(), SignupActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    public boolean validate() {
        boolean valid = true;

        _email = email.getText().toString();
        _password = password.getText().toString();
        //   Toast.makeText(LoginActivity.this,_email+_password,Toast.LENGTH_LONG).show();
        if (_email.isEmpty()) {
            email.setError("enter a valid email address");
            valid = false;
        } else {
            email.setError(null);
        }

        if (_password.isEmpty()) {
            password.setError("cant be empty");
            valid = false;
        } else {
            password.setError(null);
        }

        return valid;
    }

    private void senddata() {
        if (validate()) {
            _email = email.getText().toString();
            _password = password.getText().toString();
            StringRequest stringRequest = new StringRequest(Request.Method.POST, loginurl,
                    new Response.Listener<String>() {

                        @Override

                        public void onResponse(String response) {
                            try {
                                Log.i("tins", response);

                                if (!response.equals("not")) {
                                    JSONObject obj = new JSONObject(response);
                                    edit.putString("userid", obj.getString("userid"));

                                    edit.commit();

                                    startActivity(new Intent(LoginActivity.this,HomeActivity.class));
                                    finish();


                                    //finish();
                                } else {
                                    Toast.makeText(LoginActivity.this, "account does not exists", Toast.LENGTH_LONG).show();

                                }
                                //Toast.makeText(ddctivity.this,code,Toast.LENGTH_LONG).show();

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }


                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(final VolleyError error) {


                    Log.i("eeee", error.toString());
                    login.setEnabled(true);
                }

            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("email", _email);
                    params.put("pass", _password);
                    Log.i("code",params.get("email"));
                    return params;
                }
            };

            MySingleTon.getInstance(LoginActivity.this).addtoRequestQueue(stringRequest);
        }
        else
        {
            Toast.makeText(LoginActivity.this,"fill",Toast.LENGTH_LONG).show();
        }
    }
}
