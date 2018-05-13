package projekat.sf272016.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import okhttp3.ResponseBody;
import projekat.sf272016.R;
import projekat.sf272016.adapters.PostListAdapter;
import projekat.sf272016.misc.DatePreference;
import projekat.sf272016.misc.DrawerHelper;
import projekat.sf272016.misc.IDrawerClickHandler;
import projekat.sf272016.misc.ToolbarHelper;
import projekat.sf272016.misc.Util;
import projekat.sf272016.model.Post;
import projekat.sf272016.model.misc.DrawerListItem;
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
        toolbarHelper.initialize();

        /* Generate posts */
        ArrayList<Post> posts = new ArrayList<>();

        Post post1 = new Post();
        post1.setId(0);
        post1.setTitle("Post 1");
        try {
            post1.setDate(new SimpleDateFormat("dd-MM-yyyy").parse("3-12-2016"));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        post1.setLikes(100);

        Post post2 = new Post();
        post2.setId(1);
        post2.setTitle("Post 2");
        try {
            post2.setDate(new SimpleDateFormat("dd-MM-yyyy").parse("3-12-2017"));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        post2.setLikes(50);

        posts.add(post1);
        posts.add(post2);

        PostListAdapter postsAdapter = new PostListAdapter(this, posts);
        ListView postsListView = (ListView) findViewById(R.id.postsListView);
        postsListView.setAdapter(postsAdapter);

        // Settings
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
    }

    @Override
    protected void onResume(){
        super.onResume();

        // Settings
        consultPreferences();

        Call<ResponseBody> call = Util.test.getTest();
        call.enqueue(new Callback<ResponseBody>(){
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response){
                try {
                    ((TextView) findViewById(R.id.testTextView)).setText(response.body().string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t){

            }
        });
        //((TextView)findViewById(R.id.testTextView)).setText(Util.test.getTest().);
    }

    private void consultPreferences(){
        preferenceDate = DatePreference.getDateFor(PreferenceManager.getDefaultSharedPreferences(this), "keyPreferenceDate").getTime();
        preferenceSort = sharedPreferences.getString("keyPreferenceSortPost", "Datum");
    }

    private class DrawerClickHandler implements IDrawerClickHandler {
        @Override
        public void handleClick(View view, int position){
            Toast.makeText(getApplicationContext(), ((TextView)((RelativeLayout) view).getChildAt(1)).getText(), Toast.LENGTH_SHORT).show();
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
                Toast.makeText(getApplicationContext(), "new" , Toast.LENGTH_SHORT).show();
                return true;
            case R.id.toolbar_action_sync:
                Toast.makeText(getApplicationContext(), "sync" , Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    public void btnStartCreatePostActivity(View view) {
        Intent intent = new Intent(this, CreatePostActivity.class);
        startActivity(intent);
    }

    public void btnReadPostActivity(View view) {
        Intent intent = new Intent(this, ReadPostActivity.class);
        startActivity(intent);
    }

    public void btnSettingsActivity(View view) {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }
}
