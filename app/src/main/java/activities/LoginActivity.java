package activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.annotation.SuppressLint;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.btl_android.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.List;

import api.UserServices;
import fragments.FragmentHome;
import models.Login;
import models.User;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    private EditText username;
    private EditText password;
    private Button btnLogin;
    private Login login;
    private User user;
    private TextView error;
    private LinearLayout loginGg;
    private GoogleSignInOptions gso;
    private GoogleSignInClient gsc;
    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);

        initView();
        error = findViewById(R.id.error);

        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        gsc = GoogleSignIn.getClient(this,gso);
        loginGg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = gsc.getSignInIntent();
                startActivityForResult(intent,1000);
            }

        });

        username.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void afterTextChanged(Editable editable) {
                String un= username.getText().toString();
                String pw = password.getText().toString();

                validate(un,pw);
            }
        });

        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String un= username.getText().toString();
                String pw = password.getText().toString();

                validate(un,pw);
            }
        });
    }
    public void initView(){
        username = (EditText) findViewById(R.id.etUsername);
        password = (EditText)findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btLogin);
        error = findViewById(R.id.error);
        loginGg = findViewById(R.id.loginGg);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode ==1000){
            Task<GoogleSignInAccount> task= GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                task.getResult(ApiException.class);
                finish();

                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
            } catch (ApiException e) {
                e.printStackTrace();
            }
        }

    }

    public  void validate(String un, String pw){
        if(!TextUtils.isEmpty(un) && !TextUtils.isEmpty(pw)){
            btnLogin.setBackground(getResources().getDrawable(R.drawable.btn_login));
            btnLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    login = new Login();
                    login.setUsername(un);
                    login.setPassword(pw);
                    error.setText("");

                    user = new User();
                    UserServices.userServices.login(un,pw)
                            .enqueue(new Callback<User>() {
                                @Override
                                public void onResponse(Call<User> call, Response<User> response) {
                                    user = response.body();
                                    if(user == null){
                                        error.setText("Username or password incorrect");
                                    }
                                    else{
                                        error.setText("");
                                        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                                        intent.putExtra("user",user);
                                        startActivity(intent);

                                    }
                                }

                                @Override
                                public void onFailure(Call<User> call, Throwable t) {
                                    t.printStackTrace();
                                }
                            });
                }
            });
        }
        else {
            btnLogin.setBackground(getResources().getDrawable(R.drawable.btn_login_pastel));
        }
    }
}