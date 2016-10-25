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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by surya on 19/10/16.
 */

public class SignupActivity extends AppCompatActivity {

    EditText name,email,password,contact,univ;
    RadioButton rb1,rb2;
    String code,userid;
    String name1,email1,password1,contact1,univ1,gender;
    RadioGroup rg;
    Button register;
    SharedPreferences sp;
    SharedPreferences.Editor edit;
    TextView login;
    String regsiterurl="http://innovision.nitrkl.ac.in/android/registration.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        //Toolbar tb = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(tb);
        //getSupportActionBar().setDisplayShowHomeEnabled(true);
        //getSupportActionBar().setIcon(R.mipmap.ic_icon);

        //ImageView iv= (ImageView) findViewById(R.id.img);
  //      iv.setTranslationY(-2000f);
//        iv.animate().translationYBy(2000f).setDuration(1000);*/
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
                finish();

            }
        });
        register=(Button)findViewById(R.id.btn_signup);
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

        if(validate()){
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
    public boolean validate() {
        boolean valid = true;

        name1=name.getText().toString();
        email1=email.getText().toString();
        password1=password.getText().toString();
        univ1=univ.getText().toString();
        contact1=contact.getText().toString();
        //   Toast.makeText(LoginActivity.this,_email+_password,Toast.LENGTH_LONG).show();
        Pattern pattern = Pattern.compile("[|#|%|&|*|(|)|'|\"|{|}|[|]|?]");
        Matcher matchername = pattern.matcher(name1);
        Matcher matcheremail = pattern.matcher(email1);
        Matcher matcherpass= pattern.matcher(password1);
        Matcher matcheruniv=pattern.matcher(univ1);
        Matcher matchernu=pattern.matcher(contact1);
        boolean bname = matchername.find();
        boolean bemail=matcheremail.find();
        boolean bpassword=matcherpass.find();
        boolean buniv=matcheruniv.find();
        boolean bnumber=matchernu.find();

        Log.d("d",bname+"");
        Log.d("d",""+bpassword);
        Log.d("d",""+bemail);
        Log.d("d",""+buniv);
        Log.d("d",""+bnumber);

        boolean bfinal=bname || bemail || buniv || bnumber||bpassword;
        boolean bcheck=name1.isEmpty() || email1.isEmpty() || password1.isEmpty() || univ1.isEmpty() || contact1.isEmpty();

        if ( bcheck==true || bfinal==true ) {
            Toast.makeText(this, "Enter valid credentials", Toast.LENGTH_SHORT).show();
            valid = false;
        } else {
            email.setError(null);
        }

        return valid;
    }

}
