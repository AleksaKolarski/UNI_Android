package projekat.sf272016.misc;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;


import java.util.ArrayList;

import projekat.sf272016.R;
import projekat.sf272016.activities.ProfileActivity;
import projekat.sf272016.adapters.DrawerListAdapter;
import projekat.sf272016.model.misc.DrawerListItem;

public class DrawerHelper {

    private AppCompatActivity activity;
    private ArrayList<DrawerListItem> drawerListItems;
    private DrawerLayout drawerLayout;
    private RelativeLayout drawerPane;

    Object clickHandler;

    public DrawerHelper(AppCompatActivity activity, ArrayList<DrawerListItem> drawerListItems, Object clickHandler){
        this.activity = activity;
        this.drawerListItems = drawerListItems;
        this.clickHandler = clickHandler;
    }

    public void initialize(){
        drawerLayout = (DrawerLayout) activity.findViewById(R.id.drawerLayout);
        ListView drawerListView = (ListView) activity.findViewById(R.id.drawerList);
        drawerPane = (RelativeLayout) activity.findViewById(R.id.drawerPane);
        DrawerListAdapter adapter = new DrawerListAdapter(activity, drawerListItems);
        drawerListView.setAdapter(adapter);
        drawerListView.setOnItemClickListener(new DrawerItemClickListener());

        /* Profile box init */
        RelativeLayout profileBox = (RelativeLayout) activity.findViewById(R.id.drawerProfileBox);
        profileBox.setOnClickListener(new DrawerProfileBoxClickListener());


        /* TEMP */
        ImageView profileImage = (ImageView) activity.findViewById(R.id.drawerProfileImage);
        profileImage.setImageDrawable((Drawable)activity.getResources().getDrawable(R.drawable.ic_notifications_black_24dp));
        TextView profileUsername = (TextView) activity.findViewById(R.id.drawerProfileUsername);
        profileUsername.setText("Username1");
    }

    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            ((IDrawerClickHandler) clickHandler).handleClick(view, position);
            drawerLayout.closeDrawer(drawerPane);
        }
    }

    private class DrawerProfileBoxClickListener implements  RelativeLayout.OnClickListener{
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(activity, ProfileActivity.class);
            activity.startActivity(intent);
            drawerLayout.closeDrawer(drawerPane);
        }
    }
}
