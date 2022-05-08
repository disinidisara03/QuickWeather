package com.codetek.distributedsystem;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.codetek.distributedsystem.Models.User;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Email;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Login extends AppCompatActivity  implements Validator.ValidationListener {

    TextView loginGoRegister;

    @NotEmpty
    @Email
    EditText email;

    @NotEmpty
    EditText password;

    Button login;
    float v=0;

    Validator validator;

    ProgressDialog loading;

    RequestQueue queue;

    public static int loggedUserid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initProcess();
    }

    private void initProcess() {

        queue=Volley.newRequestQueue(Login.this);;

        email = findViewById(R.id.login_email);
        password = findViewById(R.id.login_password);

        loginGoRegister=findViewById(R.id.loginGoRegister);

        loginGoRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Login.this, Signup.class));
            }
        });

        login = findViewById(R.id.login_button);

        validator = new Validator(this);
        validator.setValidationListener(this);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validator.validate();
            }
        });

        loading = new ProgressDialog(Login.this);
        loading.setMessage("Loading..");
        loading.setTitle("Please wait");

        email.setText("test@gmail.com");
        password.setText("Aries@123");
    }

    @Override
    public void onBackPressed() {

    }

    @Override
    public void onValidationSucceeded() {
        try {
            loading.show();
            StringRequest stringRequest = new StringRequest(Request.Method.POST, getString(R.string.loginUrl ) ,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            loading.hide();
                            try {
                                JSONObject data=new JSONObject(response);
                                JSONObject user= (JSONObject) data.get("data");
                                System.out.println(user.get("id"));
                                Login.loggedUserid=Integer.parseInt(user.get("id")+"");
                                startActivity(new Intent(Login.this,Dashboard.class));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    loading.hide();
                    Toast.makeText(Login.this, "Something wrong", Toast.LENGTH_SHORT).show();
                }
            }){
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("email", email.getText().toString());
                    params.put("password", password.getText().toString());
                    return params;
                }
            };
            queue.add(stringRequest);
        }catch(Exception ex){
            ex.printStackTrace();
            Toast.makeText(Login.this, "Something wrong", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        for (ValidationError error : errors) {
            View view = error.getView();
            if (view instanceof EditText) {
                ((EditText) view).setError(error.getCollatedErrorMessage(Login.this));
            }
        }
    }
}