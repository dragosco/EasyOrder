package motacojo.mbds.fr.easyorder;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Button btnLog = (Button)findViewById(R.id.btn_Login_Login);
        btnLog.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.btn_Login_Login:
                startActivity(new Intent(LoginActivity.this, WelcomeActivity.class));
                break;
        }
    }
}
