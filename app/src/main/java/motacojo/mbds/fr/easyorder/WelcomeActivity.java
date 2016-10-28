package motacojo.mbds.fr.easyorder;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class WelcomeActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        String prenom = bundle.getString("prenom");
        String nom = bundle.getString("nom");

        TextView t = (TextView)findViewById(R.id.tv_User_Welcome);
        t.setText(nom);
    }
}
