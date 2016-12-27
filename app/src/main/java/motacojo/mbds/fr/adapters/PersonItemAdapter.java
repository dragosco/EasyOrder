package motacojo.mbds.fr.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;

import motacojo.mbds.fr.easyorder.R;
import motacojo.mbds.fr.entities.Person;

/**
 * Created by thais on 14/11/2016.
 */

public class PersonItemAdapter extends BaseAdapter {

    private Context context;
    public List<Person> people;
    private View.OnClickListener listener;
    public PersonItemAdapter(Context context, List<Person> people, View.OnClickListener listener) {
        this.context = context;
        this.people = people;
        this.listener = listener;
    }

    @Override
    public int getCount() {
        return people.size();
    }

    @Override
    public Object getItem(int arg0) {
        return people.get(arg0);
    }

    @Override
    public long getItemId(int arg0) { return 0; }

    @Override
    public View getView(int position, View convertView, ViewGroup arg2) {
        View v = convertView;

        PersonViewHolder viewHolder = null;
        if(v==null){
            v = View.inflate(context, R.layout.item_list_layout, null);
            viewHolder = new PersonViewHolder();
            viewHolder.nom_prenom= (TextView)v.findViewById(R.id.tv_user_list);
            viewHolder.connected = (TextView)v.findViewById(R.id.tv_status_list);
            viewHolder.imageButton = (ImageButton) v.findViewById(R.id.imageButton5);
            v.setTag(viewHolder);
        }
        else{
            viewHolder = (PersonViewHolder) v.getTag();
        }
        Person person = people.get(position);
        viewHolder.nom_prenom.setText(person.getFullName());
        viewHolder.connected.setText(person.isConnected() ? R.string.online : R.string.offline);
        viewHolder.imageButton.setOnClickListener(listener);
        viewHolder.imageButton.setTag(person.getId());
        return v;
    }

    class PersonViewHolder{
        TextView nom_prenom;
        TextView connected;
        ImageButton imageButton;
    }

}
