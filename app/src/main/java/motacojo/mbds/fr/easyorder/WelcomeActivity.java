package motacojo.mbds.fr.easyorder;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*String prenom = savedInstanceState.getString("prenom");
        String nom = savedInstanceState.getString("nom");*/
        setContentView(R.layout.activity_welcome);
        /*TextView t=(TextView)findViewById(R.id.tv_User_Welcome);
        t.setText(nom);*/
    }
}
