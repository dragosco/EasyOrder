package motacojo.mbds.fr.easyorder;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class WelcomeActivity extends AppCompatActivity implements View.OnClickListener {
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

        Button btn_versListUtilisateurs = (Button)findViewById(R.id.btn_versListUtilisateurs);
        btn_versListUtilisateurs.setOnClickListener(this);
        Button btn_versListeProduits = (Button)findViewById(R.id.btn_versListeProduits);
        btn_versListeProduits.setOnClickListener(this);
        Button btn_versListeCommandes = (Button)findViewById(R.id.btn_versListeCommandes);
        btn_versListeCommandes.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.btn_versListUtilisateurs:
                Log.e("WelcomeActivity", "onCreate");
                startActivity(new Intent(WelcomeActivity.this, PersonListActivity.class));
                break;
            case R.id.btn_versListeProduits:
                Log.e("WelcomeActivity", "onCreate");
                startActivity(new Intent(WelcomeActivity.this, ProductListActivity.class));
                break;
            case R.id.btn_versListeCommandes:
                Log.e("WelcomeActivity", "onCreate");
                startActivity(new Intent(WelcomeActivity.this, OrderListActivity.class));
                break;
        }
    }
}
