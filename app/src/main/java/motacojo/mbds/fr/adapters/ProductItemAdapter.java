package motacojo.mbds.fr.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import motacojo.mbds.fr.easyorder.R;
import motacojo.mbds.fr.entities.Product;
import motacojo.mbds.fr.outils.ImageDownloaderTask;

/**
 * Created by cojoc on 21/11/2016.
 */

public class ProductItemAdapter extends BaseAdapter {

    private Context context;
    public List<Product> products;

    public ProductItemAdapter(Context context, List<Product> products) {
        this.context = context;
        this.products = products;
    }

    @Override
    public int getCount() {
        return this.products.size();
    }

    @Override
    public Object getItem(int position) {
        return this.products.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;

        ProductViewHolder viewHolder = null;
        if(v == null){
            v = View.inflate(context, R.layout.item_product_list_layout, null);
            viewHolder = new ProductViewHolder();
            viewHolder.name         = (TextView)v.findViewById(R.id.tv_productName_itemList);
            viewHolder.description  = (TextView)v.findViewById(R.id.tv_productDescr_itemList);
            viewHolder.price        = (TextView)v.findViewById(R.id.tv_productPrice_itemList);
            viewHolder.calories     = (TextView)v.findViewById(R.id.tv_productCalories_itemList);
            viewHolder.type         = (TextView)v.findViewById(R.id.tv_productType_itemList);
            viewHolder.discount     = (TextView)v.findViewById(R.id.tv_productDiscount_itemList);
            viewHolder.picture      = (ImageView)v.findViewById(R.id.img_productImage_itemList);
            v.setTag(viewHolder);
        }
        else{
            viewHolder = (ProductViewHolder) v.getTag();
        }
        Product product = products.get(position);
        viewHolder.name.setText(product.getName());
        viewHolder.description.setText(product.getDescription());
        viewHolder.price.setText("Prix : " + product.getPrice() + " €");
        viewHolder.calories.setText("Calories : " + product.getCalories());
        viewHolder.type.setText("Type : " + product.getType());
        viewHolder.discount.setText("Discount : - "+product.getDiscount() + "%");
        if (viewHolder.picture != null) {
            new ImageDownloaderTask(viewHolder.picture).execute(product.getPicture());
        }
        return v;
    }

    class ProductViewHolder{
        TextView name;
        TextView description;
        TextView price;
        TextView calories;
        TextView type;
        TextView discount;
        ImageView picture;
    }
}
