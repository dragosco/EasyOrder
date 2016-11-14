package motacojo.mbds.fr.outils;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
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

    public PersonItemAdapter(Context context, List<Person> people) {
        this.context = context;
        this.people = people;
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
    public long getItemId(int arg0) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup arg2) {
        View v = convertView;

        PersonViewHolder viewHolder = null;
        if(v==null){
            v = View.inflate(context, R.layout.item_list_layout, null);
            viewHolder = new PersonViewHolder();
            viewHolder.nom_prenom= (TextView)v.findViewById(R.id.tv_user_list);
            viewHolder.connected = (TextView)v.findViewById(R.id.tv_status_list);
            v.setTag(viewHolder);
        }
        else{
            viewHolder = (PersonViewHolder) v.getTag();
        }
        Person person = people.get(position);
        viewHolder.nom_prenom.setText(person.getFullName());
        viewHolder.connected.setText(person.isConnected() ? R.string.online : R.string.offline);
        return v;
    }

    class PersonViewHolder{
        TextView nom_prenom;
        TextView connected;
    }

}
