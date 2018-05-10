package projekat.sf272016.misc;

import android.content.Intent;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.ArrayList;

import projekat.sf272016.R;
import projekat.sf272016.adapters.DrawerListAdapter;
import projekat.sf272016.model.misc.DrawerListItem;

public class DTInitializer {

    AppCompatActivity activity;

    private DrawerLayout drawerLayout;
    private ListView drawerListView;
    private ActionBarDrawerToggle drawerToggle;
    private RelativeLayout drawerPane;
    private ArrayList<DrawerListItem> drawerListItems;

    public DTInitializer(AppCompatActivity activity, ArrayList<DrawerListItem> drawerListItems){
        this.activity = activity;
        this.drawerListItems = drawerListItems;
    }

    public void initialize(){
        this.drawerLayout = (DrawerLayout) activity.findViewById(R.id.drawerLayout);
        this.drawerListView = (ListView) activity.findViewById(R.id.drawerList);
        this.drawerPane = (RelativeLayout) activity.findViewById(R.id.drawerPane);
        DrawerListAdapter adapter = new DrawerListAdapter(activity, drawerListItems);
        drawerListView.setOnItemClickListener(new DrawerItemClickListener());
        drawerListView.setAdapter(adapter);


        Toolbar toolbar = (Toolbar) activity.findViewById(R.id.toolbar);
        activity.setSupportActionBar(toolbar);
        final android.support.v7.app.ActionBar actionBar = activity.getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_notifications_black_24dp);
            actionBar.setHomeButtonEnabled(true);
        }
        drawerToggle = new ActionBarDrawerToggle(
                activity,                  /* host Activity */
                drawerLayout,         /* DrawerLayout object */
                toolbar,  /* nav drawer image to replace 'Up' caret */
                R.string.drawer_open,  /* "open drawer" description for accessibility */
                R.string.drawer_close  /* "close drawer" description for accessibility */
        );
    }

    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectItemFromDrawer(position);
        }
    }

    private void selectItemFromDrawer(int position) {
        //Toast.makeText(activity.getApplicationContext(), new Integer(position).toString() , Toast.LENGTH_SHORT).show();
        DrawerListItem pressedItem = drawerListItems.get(position);
        if(pressedItem != null){
            if(pressedItem.getCls() != null){
                Intent toRun = new Intent(activity, pressedItem.getCls());
                activity.startActivity(toRun);
            }
        }
        drawerLayout.closeDrawer(drawerPane);
    }
}
