package motacojo.mbds.fr.easyorder;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import motacojo.mbds.fr.entities.Person;
import motacojo.mbds.fr.adapters.PersonItemAdapter;

public class PersonListActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        Log.e("LoadPeopleList", "onCreate");

        LoadPeopleList ru = new LoadPeopleList();
        ru.execute();
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

    @Override
    public void onClick(View v) {
        System.out.println("TEST " + v.getTag());
        DeleteWaiter deleteWaiter = new DeleteWaiter();
        deleteWaiter.execute((String)v.getTag());
    }

    class LoadPeopleList extends AsyncTask<String,Void,String> {
        @Override
        protected String doInBackground(String... champs) {
            try{
                HttpClient client = new DefaultHttpClient();
                String url = "http://95.142.161.35:8080/person/";
                HttpGet get = new HttpGet(url);

                get.setHeader("Content-Type", "application/json");

                HttpResponse response = client.execute(get);

                BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

                StringBuffer result = new StringBuffer();
                String line = "";
                while ((line = rd.readLine()) != null) {
                    result.append(line);
                }

                return result.toString();
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Log.e("LoadPeopleList", "onPreExecute");
            showProgressDialog(true);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            Log.e("LoadPeopleList", "onPostExecute");
            showProgressDialog(false);

            ListView lst = (ListView)findViewById(R.id.listView);
            List<Person> people = new ArrayList<Person>();

            //Traiter la liste de personnes
            try {

                JSONArray list = new JSONArray(result);

                for(int i = 0; i < list.length(); i++) {
                    JSONObject person = list.getJSONObject(i);
                    Log.e("ActivityList", person.toString());
                    Person p = new Person(
                            person.optString("nom", "John"),
                            person.optString("prenom", "John"),
                            person.optString("sexe", "John"),
                            person.optString("telephone", "John"),
                            person.optString("email", "John"),
                            person.optString("password", "John"));
                    p.setConnected(person.optBoolean("connected", false));
                    p.setId(person.optString("id", "99999999999999999999999"));

                    people.add(p);
                }
            } catch (JSONException e) {
                Log.e("LoadPeopleList", "erreur");
                e.printStackTrace();
            }

            PersonItemAdapter adapter = new PersonItemAdapter(PersonListActivity.this, people, PersonListActivity.this);
            Log.e("PersonListActivity", "people length " + people.size());
            lst.setAdapter(adapter);
        }
    }

    class DeleteWaiter extends AsyncTask<String,Void,String> {

        @Override
        protected String doInBackground(String... params) {
            try{
                HttpClient client = new DefaultHttpClient();
                String url = "http://95.142.161.35:8080/person/" + params[0];
                HttpDelete delete = new HttpDelete(url);

                delete.setHeader("Content-Type", "application/json");

                HttpResponse response = client.execute(delete);

                BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

                StringBuffer result = new StringBuffer();
                String line = "";
                while ((line = rd.readLine()) != null) {
                    result.append(line);
                }

                return result.toString();
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Log.e("DeleteWaiter", "onPreExecute");
            showProgressDialog(true);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            Log.e("DeleteWaiter", "onPostExecute");
            showProgressDialog(false);

            try {
                JSONObject resultJSON = new JSONObject(result);
                if (result != null) {
                    if(resultJSON.has("id")) {
                        Toast.makeText(getApplicationContext(),R.string.delete_waiter_ok, Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getApplicationContext(),"Une erreur s'est produite", Toast.LENGTH_LONG).show();
                    }
                }
            } catch (JSONException e) {
                Log.e("LoadPeopleList", "erreur");
                e.printStackTrace();
            }
        }
    }
}
