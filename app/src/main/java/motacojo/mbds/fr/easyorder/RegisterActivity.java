package motacojo.mbds.fr.easyorder;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
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
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import motacojo.mbds.fr.entities.Person;
import motacojo.mbds.fr.outils.FormValidator;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText nom;
    private EditText prenom;
    private EditText tel;
    private EditText email;
    private EditText mdp;
    private EditText mdpConfirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Button btnSign = (Button)findViewById(R.id.btn_SignUp_Register);
        btnSign.setOnClickListener(this);

        nom        = (EditText)findViewById(R.id.input_Nom_Register);
        prenom     = (EditText)findViewById(R.id.input_Prenom_Register);
        tel        = (EditText)findViewById(R.id.input_Tel_Register);
        email      = (EditText)findViewById(R.id.input_Email_Register);
        mdp        = (EditText)findViewById(R.id.input_Mdp_Register);
        mdpConfirm = (EditText)findViewById(R.id.input_MdpConfirm_Register);

        FormValidator fdNom         = new FormValidator(nom);
        FormValidator fdPrenom      = new FormValidator(prenom);
        FormValidator fdEmail       = new FormValidator(email);
        FormValidator fdTel         = new FormValidator(tel);
        FormValidator fdMdp         = new FormValidator(mdp);
        FormValidator fdMdpConfirm  = new FormValidator(mdpConfirm);
        fdMdpConfirm.identicalTo(mdp);
    }

    @Override
    public void onClick(View v) {
        if (isValide(nom) &&
                isValide(prenom) &&
                isValide(email) &&
                isValide(tel) &&
                isValide(mdp)) {
            switch(v.getId()) {
                case R.id.btn_SignUp_Register:
                    String txt_nom =  nom.getText().toString();
                    String txt_prenom = prenom.getText().toString();
                    String txt_sexe = "Masculin";
                    String txt_telephone = tel.getText().toString();
                    String txt_email = email.getText().toString();
                    String txt_pass = mdp.getText().toString();

                    Person person = new Person(txt_nom, txt_prenom, txt_sexe, txt_telephone, txt_email, txt_pass);

                    //enregistrer lutilisateur
                    RegisterUser ru = new RegisterUser();
                /*int corePoolSize = 60;
                int maximumPoolSize = 80;
                int keepAliveTime = 10;

                BlockingQueue<Runnable> workQueue = new LinkedBlockingQueue<Runnable>(maximumPoolSize);
                Executor threadPoolExecutor = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, TimeUnit.SECONDS, workQueue);

                if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.HONEYCOMB)
                    ru.executeOnExecutor(threadPoolExecutor, person);
                else*/
                    ru.execute(person);

                    //redirectionner vers la page daccueil
                    //startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                    break;
            }
        }
    }

    public boolean isValide(EditText editText) {
        Editable text = editText.getText();
        int inputType = editText.getInputType();
        Pattern pattern;
        Matcher matcher;
        switch (inputType) {
            case InputType.TYPE_CLASS_PHONE:
                pattern = Pattern.compile("^\\+(?:[0-9] ?){6,14}[0-9]$");
                matcher = pattern.matcher(text.toString());
                if (!matcher.matches()) {
                    editText.setError("Le numéro de téléphone n'est pas valide.");
                    return false;
                } else {
                    editText.setError(null);
                    return true;
                }
            case 33:
                pattern = Pattern.compile("(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])");
                matcher = pattern.matcher(text.toString());
                if (!matcher.matches()) {
                    editText.setError("L'adresse e-mail doit être de la forme exemple@exemple.com.");
                    return false;
                } else {
                    editText.setError(null);
                    return true;
                }
            case InputType.TYPE_TEXT_VARIATION_PASSWORD:
                pattern = Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{8,}$");
                matcher = pattern.matcher(text.toString());
                if (!matcher.matches()) {
                    editText.setError("Le mot de passe doit avoir au moins 8 caractères, dont au moins un minuscul, un majuscul et un chiffre.");
                    return false;
                } else {
                    editText.setError(null);
                    return true;
                }
            default:
                if (TextUtils.isEmpty(text)) {
                    editText.setError("Ce champ est obligatoire!");
                    return false;
                } else {
                    editText.setError(null);
                    return true;
                }
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

    class RegisterUser extends AsyncTask<Person,Void,Person> {
        @Override
        protected Person doInBackground(Person... people) {
            Person person = people[0];
            try{
                HttpClient client = new DefaultHttpClient();
                String url = "http://95.142.161.35:1337/person/";
                HttpPost post = new HttpPost(url);

                post.setHeader("Content-Type", "application/json");
                JSONObject obj = new JSONObject();
                obj.put("nom", person.getNom());
                obj.put("prenom", person.getPrenom());
                obj.put("sexe", person.getSexe());
                obj.put("telephone", person.getTelephone());
                obj.put("email", person.getEmail());
                obj.put("createdBy", "Thais & Dragos :D");
                obj.put("password", person.getPassword());

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

                JSONObject resultJSON = new JSONObject(result.toString());
                Person p = new Person(
                        resultJSON.getString("nom"),
                        resultJSON.getString("prenom"),
                        resultJSON.getString("sexe"),
                        resultJSON.getString("telephone"),
                        resultJSON.getString("email"),
                        resultJSON.getString("password"));
                p.setConnected(resultJSON.getBoolean("connected"));
                p.setId(resultJSON.getString("id"));

                //"2014-10-15T20:19:20.365Z"
                /*SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");

                Date createdAt = formatter.parse((String)resultJSON.get("createdAt"));
                p.setCreadtedAt(createdAt);

                Date updatedAt = formatter.parse((String)resultJSON.get("updatedAt"));
                p.setCreadtedAt(updatedAt);*/

                return p;
            } catch (Exception e){
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Log.e("RegisterUser", "onPreExecute");
            showProgressDialog(true);
        }

        @Override
        protected void onPostExecute(Person person) {
            super.onPostExecute(person);
            Log.e("RegisterUser", "onPostExecute");
            showProgressDialog(false);

            Toast.makeText(getApplicationContext(),R.string.inscription_ok, Toast.LENGTH_LONG).show();

            //Traiter la person
            if (person!=null) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            }else{
            }
            //Renvoyer vers le login
            //Fermer l'activité Enregistrer
        }
    }
}