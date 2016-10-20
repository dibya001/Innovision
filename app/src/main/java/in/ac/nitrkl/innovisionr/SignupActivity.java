package in.ac.nitrkl.innovisionr;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import org.json.JSONArray;
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

public class SignupActivity extends AppCompatActivity {

    EditText name,email,password,contact,univ;
    RadioButton rb1,rb2;
    String code,userid;
    String name1,email1,password1,contact1,univ1,gender;
    RadioGroup rg;
    AppCompatButton register;
    SharedPreferences sp;
    SharedPreferences.Editor edit;
    TextView login;
    String regsiterurl="http://192.168.43.103/inno_final/registration.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        Toolbar tb = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(tb);
        ImageView iv= (ImageView) findViewById(R.id.img);
        iv.setTranslationY(-2000f);
        iv.animate().translationYBy(2000f).setDuration(1000);
        sp = getSharedPreferences("demo_file", MODE_PRIVATE);
        edit = sp.edit();
        email= (EditText) findViewById(R.id.input_email);
        password= (EditText) findViewById(R.id.input_password);
        contact= (EditText) findViewById(R.id.input_contact);
        name= (EditText) findViewById(R.id.input_name);
        univ= (EditText) findViewById(R.id.input_univername);
        rg= (RadioGroup) findViewById(R.id.radio);
        login= (TextView) findViewById(R.id.link_login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignupActivity.this,LoginActivity.class));

            }
        });
        register=(AppCompatButton)findViewById(R.id.btn_signup);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                senddata();
            }
        });


    }

    private void senddata() {

        name1=name.getText().toString();
        email1=email.getText().toString();
        password1=password.getText().toString();
        univ1=univ.getText().toString();
        contact1=contact.getText().toString();
        if(rg.getCheckedRadioButtonId()==R.id.male)
            gender="male";
        else
            gender="female";




        if(name1.equals("")||email1.equals("")||univ1.equals("")||password1.equals("")||contact1.equals(""))
        {
            AlertDialog.Builder builder =new AlertDialog.Builder(SignupActivity.this);
            builder.setTitle("signup failed");
            builder.setMessage("please fill all the details");
            builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            });
            AlertDialog al=builder.create();
            al.show();

        }
        else
        {
            // Log.i("code","else");
            StringRequest stringRequest=new StringRequest(Request.Method.POST,regsiterurl,
                    new Response.Listener<String>() {

                        @Override

                        public void onResponse(String response) {
                            try {

                                JSONArray jsonArray=new JSONArray(response);
                                JSONObject aa=jsonArray.getJSONObject(0);
                                code=aa.getString("code");
                                userid=aa.getString("userid");
                                if(code.equals("yes") && Integer.parseInt(userid)!=-1) {
                                    edit.putString("userid",userid);
                                    edit.commit();

                                    startActivity(new Intent(SignupActivity.this,HomeActivity.class));
                                    finish();
                                }

                                Log.i("eeee",response.toString()+"aa");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }


                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(final VolleyError error) {


                    Log.i("eeee",error.toString());
                }

            })
            {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String,String> params=new HashMap<String, String>();
                    params.put("name",name1);
                    params.put("email",email1);
                    params.put("pass",password1);
                    params.put("contact",contact1);
                    params.put("gender",gender);
                    params.put("univ",univ1);
                    Log.i("code",params.get("name"));
                    return params;
                }
            };

            MySingleTon.getInstance(SignupActivity.this).addtoRequestQueue(stringRequest);
        }



    }
}
