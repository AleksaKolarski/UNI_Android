package projekat.sf272016.activities;

import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

import projekat.sf272016.R;
import projekat.sf272016.misc.DrawerHelper;
import projekat.sf272016.misc.IDrawerClickHandler;
import projekat.sf272016.misc.ToolbarHelper;
import projekat.sf272016.model.Post;
import projekat.sf272016.model.User;
import projekat.sf272016.model.misc.DrawerListItem;
import projekat.sf272016.remote.Remote;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreatePostActivity extends AppCompatActivity {

    private DrawerHelper drawerHelper;
    private ToolbarHelper toolbarHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_post);

        /* Drawer */
        ArrayList<DrawerListItem> drawerListItems = new ArrayList<>();
        drawerListItems.add(new DrawerListItem("Posts", "See all posts", R.drawable.ic_launcher_foreground));
        drawerListItems.add(new DrawerListItem("Settings", "Change app settings", R.drawable.ic_launcher_foreground));
        drawerHelper = new DrawerHelper(this, drawerListItems, new DrawerClickHandler());
        drawerHelper.initialize();

        /* Toolbar */
        toolbarHelper = new ToolbarHelper(this);
        toolbarHelper.initialize();
    }

    public void btnCreate(View view) {
        // TODO Call remote server and create new post
        Post post = new Post();
        post.setTitle(((EditText)findViewById(R.id.createPostActivityTitle)).getText().toString());
        post.setDescription(((EditText)findViewById(R.id.createPostActivityDescription)).getText().toString());
        User user = new User();
        user.setUsername(PreferenceManager.getDefaultSharedPreferences(this).getString("loggedInUserUsername", ""));
        post.setAuthor(user);

        Call<Post> call = Remote.postRemote.createPost(post);
        call.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                Toast.makeText(CreatePostActivity.this, ((Integer)response.code()).toString(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                Toast.makeText(CreatePostActivity.this, "Error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void btnPhoto(View view) {
        // TODO Photo chooser
    }

    private class DrawerClickHandler implements IDrawerClickHandler {
        @Override
        public void handleClick(View view, int position){
            //Toast.makeText(getApplicationContext(), new Integer(position).toString() , Toast.LENGTH_SHORT).show();
            Toast.makeText(getApplicationContext(), ((TextView)((RelativeLayout) view).getChildAt(1)).getText(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.create_post_menu, menu);
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
}
