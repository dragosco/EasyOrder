package motacojo.mbds.fr.easyorder;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import motacojo.mbds.fr.adapters.ProductItemAdapter;
import motacojo.mbds.fr.entities.Product;

public class ProductListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);

        Log.e("LoadProductList", "onCreate");

        ProductListActivity.LoadProductList ru = new ProductListActivity.LoadProductList();
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

    class LoadProductList extends AsyncTask<String,Void,String> {
        @Override
        protected String doInBackground(String... champs) {
            try{
                HttpClient client = new DefaultHttpClient();
                String url = "http://95.142.161.35:8080/product/";
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

            ListView lst = (ListView)findViewById(R.id.listView_ProductList);
            List<Product> products = new ArrayList<Product>();

            //Traiter la liste de produits
            try {

                JSONArray list = new JSONArray(result);

                for(int i = 0; i < list.length(); i++) {
                    JSONObject product = list.getJSONObject(i);
                    Product p = new Product(
                            product.optString("name", "Produit Inconnu"),
                            product.optString("description", "Pas de description"),
                            Integer.parseInt(product.optString("price", "0")),
                            Integer.parseInt(product.optString("calories", "0")),
                            product.optString("type", "Inconnu"),
                            product.optString("picture", "none"),
                            Integer.parseInt(product.optString("discount", "0")));
                    p.setId(product.optString("id", "99999999999999999999999"));
                    products.add(p);
                }
            } catch (JSONException e) {
                Log.e("LoadPeopleList", "erreur");
                e.printStackTrace();
            }

            ProductItemAdapter adapter = new ProductItemAdapter(ProductListActivity.this, products);
            Log.e("ProductListActivity", "products length " + products.size());
            lst.setAdapter(adapter);
        }
    }
}
