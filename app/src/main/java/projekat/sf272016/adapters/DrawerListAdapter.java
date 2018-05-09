package projekat.sf272016.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import projekat.sf272016.R;
import projekat.sf272016.model.misc.DrawerListItem;

public class DrawerListAdapter extends BaseAdapter {

    Context context;
    ArrayList<DrawerListItem> drawerItems;

    public DrawerListAdapter(Context context, ArrayList<DrawerListItem> drawerItems) {
        this.context = context;
        this.drawerItems = drawerItems;
    }

    @Override
    public int getCount(){
        return drawerItems.size();
    }

    @Override
    public Object getItem(int position){
        return drawerItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        View view;

        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.drawer_item, null);
        }
        else{
            view = convertView;
        }

        TextView titleView = (TextView) view.findViewById(R.id.title);
        TextView subtitleView = (TextView) view.findViewById(R.id.subTitle);
        ImageView iconView = (ImageView) view.findViewById(R.id.icon);

        titleView.setText( drawerItems.get(position).getItemTitle() );
        subtitleView.setText( drawerItems.get(position).getItemSubtitle() );
        iconView.setImageResource(drawerItems.get(position).getItemIcon());

        return view;
    }
}
