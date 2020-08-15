package net.larntech.user;

import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class LoginActivity extends AppCompatActivity {
     Button btnLogin;
     EditText edUsername, edPassword;
     TextView noAccount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnLogin = findViewById(R.id.btnLogin);
        edUsername = findViewById(R.id.et_email);
        edPassword = findViewById(R.id.et_password);
        noAccount = findViewById(R.id.tvCreateAccount);

        noAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(TextUtils.isEmpty(edUsername.getText().toString()) || TextUtils.isEmpty(edPassword.getText().toString()) ) {
                    String message = "All inputs required ..";
                    Toast.makeText(LoginActivity.this,message,Toast.LENGTH_LONG).show();
                }else{
                    LoginRequest loginRequest = new LoginRequest();
                    loginRequest.setUsername(edUsername.getText().toString());
                    loginRequest.setPassword(edPassword.getText().toString());

                    loginUser(loginRequest);
                }

            }
        });

    }

    public void loginUser(LoginRequest loginRequest){
        Call<LoginResponse> loginResponseCall = ApiClient.getService().loginUser(loginRequest);
        loginResponseCall.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if(response.isSuccessful()){
                    LoginResponse loginResponse = response.body();
                    startActivity(new Intent(LoginActivity.this,MainActivity.class).putExtra("data",loginResponse));
                    finish();


                }else{
                    String message = "An error occurred please try again later ...";
                    Toast.makeText(LoginActivity.this,message,Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {

                String message = t.getLocalizedMessage();
                Toast.makeText(LoginActivity.this,message,Toast.LENGTH_LONG).show();
            }
        });
    }
}