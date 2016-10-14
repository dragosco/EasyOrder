package motacojo.mbds.fr.easyorder;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btnReg = (Button)findViewById(R.id.btn_SignUp_Main);
        Button btnLog = (Button)findViewById(R.id.btn_Login_Main);
        btnReg.setOnClickListener(this);
        btnLog.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.btn_SignUp_Main:
                startActivity(new Intent(MainActivity.this, RegisterActivity.class));
                break;
            case R.id.btn_Login_Main:
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                break;
        }
    }
}
