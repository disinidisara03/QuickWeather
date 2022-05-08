package com.codetek.distributedsystem;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.ConfirmPassword;
import com.mobsandgeeks.saripaar.annotation.Email;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Password;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Signup extends AppCompatActivity implements Validator.ValidationListener {

    @NotEmpty
    private EditText registerName;

    @NotEmpty
    @Email
    private EditText registerEmail;

    @Password(min = 6, scheme = Password.Scheme.ALPHA_NUMERIC_MIXED_CASE_SYMBOLS)
    private EditText registerPassword;

    @ConfirmPassword
    private EditText registerRetypePassword;

    private Spinner registerDistrict;

    private TextView registerBackLogin;

    private Button registerSignupButton;

    private Validator validator;

    private ProgressDialog loading;

    private int cityValue;

    ArrayAdapter districtsAdapter ;

    String [] dataList;

    RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        initProcess();
    }

    private void initProcess() {
        queue= Volley.newRequestQueue(Signup.this);;
        registerName=findViewById(R.id.registerName);
        registerEmail=findViewById(R.id.registerEmail);
        registerPassword=findViewById(R.id.registerPassword);
        registerRetypePassword=findViewById(R.id.registerPasswordConfirmation);

        registerBackLogin=findViewById(R.id.registerBackLogin);
        registerBackLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        registerSignupButton=findViewById(R.id.registerButton);
        registerSignupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validator.validate();
            }
        });

        validator = new Validator(this);
        validator.setValidationListener(this);


        loading = new ProgressDialog(Signup.this);
        loading.setMessage("Loading..");
        loading.setTitle("Please wait, Registration in progress.");

    }

    @Override
    public void onValidationSucceeded() {
        loading.show();
        try {
            loading.show();
            StringRequest stringRequest = new StringRequest(Request.Method.POST, getString(R.string.registerUrl ) ,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            loading.hide();
                            registerName.setText("");
                            registerEmail.setText("");
                            registerPassword.setText("");
                            registerRetypePassword.setText("");
                            Toast.makeText(Signup.this, "", Toast.LENGTH_SHORT).show();
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    loading.hide();
                    System.out.println(error.getMessage());
                    Toast.makeText(Signup.this, "Something wrong", Toast.LENGTH_SHORT).show();
                }
            }){
                @Override
                protected Map<String, String> getParams() {
                    System.out.println(registerEmail.getText().toString());
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("name", registerName.getText().toString());
                    params.put("email", registerEmail.getText().toString());
                    params.put("password", registerPassword.getText().toString());
                    params.put("password_confirmation", registerRetypePassword.getText().toString());
                    return params;
                }

                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String>  params = new HashMap<String, String>();
                    params.put("Accept", "application/json");

                    return params;
                }
            };
            queue.add(stringRequest);
        }catch(Exception ex){
            ex.printStackTrace();
            Toast.makeText(Signup.this, "Something wrong", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        for (ValidationError error : errors) {
            View view = error.getView();
            if (view instanceof EditText) {
                ((EditText) view).setError(error.getCollatedErrorMessage(Signup.this));
            }
        }
    }

}