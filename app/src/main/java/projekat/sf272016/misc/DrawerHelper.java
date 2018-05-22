package projekat.sf272016.misc;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.preference.PreferenceManager;
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
import projekat.sf272016.model.User;
import projekat.sf272016.model.misc.DrawerListItem;
import projekat.sf272016.remote.Remote;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DrawerHelper {

    private AppCompatActivity activity;
    private ArrayList<DrawerListItem> drawerListItems;
    private DrawerLayout drawerLayout;
    private RelativeLayout drawerPane;

    private Object clickHandler;

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

        /* Profile box click listener */
        RelativeLayout profileBox = (RelativeLayout) activity.findViewById(R.id.drawerProfileBox);
        profileBox.setOnClickListener(new DrawerProfileBoxClickListener());

        /* Populate profile box */
        SharedPreferences sharedPreferences;
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(activity);
        String username = sharedPreferences.getString("loggedInUserUsername", "");
        String name = sharedPreferences.getString("loggedInUserName", "");
        ((TextView)activity.findViewById(R.id.drawerProfileName)).setText(name);
        ((TextView)activity.findViewById(R.id.drawerProfileUsername)).setText(username);

        Call<User> call = Remote.userRemote.getUserByUsername(username);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(response.code() == 200){
                    if(response.body() != null){
                        User user = response.body();
                        if(user.getPhoto() != null){
                            ((ImageView)activity.findViewById(R.id.drawerProfileImage)).setImageBitmap(user.getPhoto());
                        }
                        else{
                            ((ImageView)activity.findViewById(R.id.drawerProfileImage)).setImageResource(R.drawable.ic_person_black_24dp);
                        }
                    }
                }
            }
            @Override
            public void onFailure(Call<User> call, Throwable t) {
                t.printStackTrace();
            }
        });
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
