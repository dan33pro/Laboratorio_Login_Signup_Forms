package unipiloto.edu.co;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class LoginFormActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_form);

        ActivityCompat.requestPermissions(LoginFormActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
    }

    public void btn_signupForm(View view) {
        startActivity(new Intent(getApplicationContext(), SignupFormActivity.class));
    }
}