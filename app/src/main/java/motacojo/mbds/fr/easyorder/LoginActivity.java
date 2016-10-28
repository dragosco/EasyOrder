package motacojo.mbds.fr.easyorder;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import motacojo.mbds.fr.entities.Person;

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
                String txt_email = ((EditText)findViewById(R.id.input_Email_Login)).getText().toString();
                String txt_password = ((EditText)findViewById(R.id.input_Mdp_Login)).getText().toString();

                //enregistrer lutilisateur
                ValidateLogin validateLogin = new ValidateLogin();
                validateLogin.execute(txt_email, txt_password);

                //startActivity(new Intent(LoginActivity.this, WelcomeActivity.class));
                break;
        }
    }

    ProgressDialog progressDialog;
    public void showProgressDialog(boolean isVisible) {
        if (isVisible) {
            if(progressDialog==null) {
                progressDialog = new ProgressDialog(this);
                progressDialog.setMessage(this.getResources().getString(R.string.please_wait));
                progressDialog.setCancelable(false);
                progressDialog.setIndeterminate(true);
                progressDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        progressDialog = null;
                    }
                });
                progressDialog.show();
            }
        }
        else {
            if(progressDialog!=null) {
                progressDialog.dismiss();
            }
        }
    }

    class ValidateLogin extends AsyncTask<String,Void,String> {
        @Override
        protected String doInBackground(String... champs) {
            try{
                HttpClient client = new DefaultHttpClient();
                String url = "http://95.142.161.35:1337/person/login";
                HttpPost post = new HttpPost(url);

                post.setHeader("Content-Type", "application/json");
                /*
                * {
                    "email" : "eamosse@gmail.com",
                    "password" : "1234"
                * }
                */
                JSONObject obj = new JSONObject();
                obj.put("email", champs[0]);
                obj.put("password", champs[1]);

                StringEntity entity = new StringEntity(obj.toString());
                post.setEntity(entity);

                HttpResponse response = client.execute(post);
                Log.e("RegisterUser", "\nSending 'POST' request to URL : " + url);
                Log.e("RegisterUser", "Post parameters : " + post.getEntity());
                Log.e("RegisterUser", "Response Code : " + response.getStatusLine().getStatusCode());

                BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

                StringBuffer result = new StringBuffer();
                String line = "";
                while ((line = rd.readLine()) != null) {
                    result.append(line);
                }

                return result.toString();

                /*JSONObject resultJSON = new JSONObject(result.toString());


                    MESSAGE DERREUR
                    {
                        "success" : false,
                        "message" : "Nom d'utilisateur ou mot de passe incorrecte"
                    }
                    MESSAGE DE SUCCES
                    {
                      "success": true,
                      "user": {
                        "nom": "Edouard",
                        "prenom": "Amosse",
                        "sexe": "Masculin",
                        "telephone": "123456789",
                        "email": "eamosse@gmail.com",
                        "createdby": "Amosse",
                        "password": "1234",
                        "connected": true,
                        "createdAt": "2014-10-15T20:27:09.998Z",
                        "updatedAt": "2014-10-15T20:27:09.998Z",
                        "id": "543ed89ed9573e6e76a02490"
                      }
                    }

                Person p = null;
                if((resultJSON.getBoolean("success"))) {
                    p = new Person(
                            resultJSON.getString("nom"),
                            resultJSON.getString("prenom"),
                            resultJSON.getString("sexe"),
                            resultJSON.getString("telephone"),
                            resultJSON.getString("email"),
                            resultJSON.getString("password"));
                    p.setConnected(resultJSON.getBoolean("connected"));
                    p.setId(resultJSON.getString("id"));
                } else {
                    Toast.makeText(getApplicationContext(), resultJSON.getString("message"), Toast.LENGTH_LONG).show();
                }

                //"2014-10-15T20:19:20.365Z"
                /*SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");

                Date createdAt = formatter.parse((String)resultJSON.get("createdAt"));
                p.setCreadtedAt(createdAt);

                Date updatedAt = formatter.parse((String)resultJSON.get("updatedAt"));
                p.setCreadtedAt(updatedAt);

                return p;*/
            } catch (Exception e){
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Log.e("ValidateLogin", "onPreExecute");
            showProgressDialog(true);
        }

        @Override
            protected void onPostExecute(String result) {
            super.onPostExecute(result);
            Log.e("ValidateLogin", "onPostExecute");
            showProgressDialog(false);

            /*
                MESSAGE DERREUR
                {
                    "success" : false,
                    "message" : "Nom d'utilisateur ou mot de passe incorrecte"
                }
                MESSAGE DE SUCCES
                {
                  "success": true,
                  "user": {
                    "nom": "Edouard",
                    "prenom": "Amosse",
                    "sexe": "Masculin",
                    "telephone": "123456789",
                    "email": "eamosse@gmail.com",
                    "createdby": "Amosse",
                    "password": "1234",
                    "connected": true,
                    "createdAt": "2014-10-15T20:27:09.998Z",
                    "updatedAt": "2014-10-15T20:27:09.998Z",
                    "id": "543ed89ed9573e6e76a02490"
                  }
                }
             */

            try {
                JSONObject resultJSON = new JSONObject(result);

                //Traiter la person
                if (result != null) {
                    if((resultJSON.getBoolean("success"))) {
                        Toast.makeText(getApplicationContext(),R.string.inscription_ok, Toast.LENGTH_LONG).show();
                        JSONObject user = resultJSON.getJSONObject("user");
                        Person p = new Person(
                                user.getString("nom"),
                                user.getString("prenom"),
                                user.getString("sexe"),
                                user.getString("telephone"),
                                user.getString("email"),
                                user.getString("password"));
                        p.setConnected(user.getBoolean("connected"));
                        p.setId(user.getString("id"));

                        Intent intent = new Intent(LoginActivity.this, WelcomeActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("prenom",p.getPrenom());
                        bundle.putString("nom",p.getNom());
                        intent.putExtras(bundle);

                        startActivity(intent);
                    } else {
                        Toast.makeText(getApplicationContext(), resultJSON.getString("message"), Toast.LENGTH_LONG).show();
                    }

                    //startActivity(new Intent(LoginActivity.this, WelcomeActivity.class));
                }else{
                    Log.e("LoginActivity", "erreur");
                }
                //Renvoyer vers le login
                //Fermer l'activité login
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
