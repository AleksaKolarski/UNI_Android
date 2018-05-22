package projekat.sf272016.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import projekat.sf272016.R;
import projekat.sf272016.adapters.PostListAdapter;
import projekat.sf272016.misc.DatePreference;
import projekat.sf272016.misc.DrawerHelper;
import projekat.sf272016.misc.IDrawerClickHandler;
import projekat.sf272016.misc.ToolbarHelper;
import projekat.sf272016.model.Post;
import projekat.sf272016.model.misc.DrawerListItem;
import projekat.sf272016.remote.Remote;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostsActivity extends AppCompatActivity{

    private DrawerHelper drawerHelper;
    private ToolbarHelper toolbarHelper;

    private SharedPreferences sharedPreferences;
    private Date preferenceDate;
    private String preferenceSort;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_posts);

        /* Drawer */
        ArrayList<DrawerListItem> drawerListItems = new ArrayList<>();
        drawerListItems.add(new DrawerListItem("Settings", "Change app settings", R.drawable.ic_settings_black_24dp));
        drawerListItems.add(new DrawerListItem("Logout", "To login with other account", R.drawable.ic_log_out_24dp));
        drawerHelper = new DrawerHelper(this, drawerListItems, new DrawerClickHandler());
        drawerHelper.initialize();

        /* Toolbar */
        toolbarHelper = new ToolbarHelper(this);
        toolbarHelper.initialize("Posts");
    }

    @Override
    protected void onResume(){
        super.onResume();

        // Settings
        if(sharedPreferences == null) {
            sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        }
        consultPreferences();

        Call<ArrayList<Post>> call;
        if(preferenceSort.equals("Datum")){
            call = Remote.postRemote.getAllPostsOrderByDate(preferenceDate);
        }
        else{
            call = Remote.postRemote.getAllPostsOrderByLikes(preferenceDate);
        }
        call.enqueue(new Callback<ArrayList<Post>>(){
            @Override
            public void onResponse(Call<ArrayList<Post>> call, Response<ArrayList<Post>> response){
                ArrayList<Post> posts = response.body();
                if(posts != null) {
                    PostListAdapter postListAdapter = new PostListAdapter(PostsActivity.this, posts);
                    ListView postsListView = (ListView) findViewById(R.id.postsListView);
                    postsListView.setAdapter(postListAdapter);
                }
                else{
                    ((TextView) findViewById(R.id.testTextView)).setText("lista prazna");
                }
            }
            @Override
            public void onFailure(Call<ArrayList<Post>> call, Throwable t){
                ((TextView) findViewById(R.id.testTextView)).setText(t.getMessage() + "\n" + t.getCause().getMessage() + "\n" + t.getCause().getMessage());
                t.printStackTrace();
            }
        });
    }

    private void consultPreferences(){
        preferenceDate = DatePreference.getDateFor(sharedPreferences, "keyPreferenceDate").getTime();
        preferenceSort = sharedPreferences.getString("keyPreferenceSortPost", "Datum");
    }

    private class DrawerClickHandler implements IDrawerClickHandler {
        @Override
        public void handleClick(View view, int position){
            Intent intent;
            switch ((String)view.getTag()){
                case "Settings":
                    intent = new Intent(PostsActivity.this, SettingsActivity.class);
                    startActivity(intent);
                    break;
                case "Logout":
                    // Brisemo username i name iz SharedPreferences
                    SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(PostsActivity.this);
                    SharedPreferences.Editor sharedPreferencesEditor = sharedPreferences.edit();
                    sharedPreferencesEditor.remove("loggedInUserUsername");
                    sharedPreferencesEditor.remove("loggedInUserName");
                    sharedPreferencesEditor.commit();

                    intent = new Intent(PostsActivity.this, LoginActivity.class);
                    startActivity(intent);
                    PostsActivity.this.finish();
                    break;
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.posts_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.toolbar_action_new:
                Intent intent = new Intent(this, CreatePostActivity.class);
                startActivity(intent);
                return true;
            case R.id.toolbar_action_sync:
                Toast.makeText(getApplicationContext(), "refresh" , Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
