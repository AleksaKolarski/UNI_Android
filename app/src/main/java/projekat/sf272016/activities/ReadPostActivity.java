package projekat.sf272016.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import projekat.sf272016.R;
import projekat.sf272016.misc.DrawerHelper;
import projekat.sf272016.misc.IDrawerClickHandler;
import projekat.sf272016.misc.ToolbarHelper;
import projekat.sf272016.model.Post;
import projekat.sf272016.model.misc.DrawerListItem;
import projekat.sf272016.remote.Remote;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReadPostActivity extends AppCompatActivity {

    DrawerHelper drawerHelper;
    ToolbarHelper toolbarHelper;
    Integer postId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_post);

        /* Drawer */
        ArrayList<DrawerListItem> drawerListItems = new ArrayList<>();
        drawerListItems.add(new DrawerListItem("Posts", "See all posts", R.drawable.ic_launcher_foreground));
        drawerListItems.add(new DrawerListItem("Settings", "Change app settings", R.drawable.ic_launcher_foreground));
        drawerHelper = new DrawerHelper(this, drawerListItems, new DrawerClickHandler());
        drawerHelper.initialize();

        /* Toolbar */
        toolbarHelper = new ToolbarHelper(this);
        toolbarHelper.initialize("Post");

        // Ucitavanje posta
        postId = (Integer)getIntent().getIntExtra("postId", 0);

        Call<Post> call = Remote.postRemote.getPostById(postId);
        call.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                Post post = response.body();
                if(post != null){
                    TextView readPostTitle = (TextView) findViewById(R.id.readPostTitle);
                    TextView readPostDescription = (TextView) findViewById(R.id.readPostDescription);
                    ImageView readPostImage = (ImageView) findViewById(R.id.readPostImage);
                    TextView readPostTags = (TextView) findViewById(R.id.readPostTags);
                    TextView readPostAuthor = (TextView) findViewById(R.id.readPostAuthor);
                    TextView readPostDate = (TextView) findViewById(R.id.readPostDate);
                    TextView readPostLocation = (TextView) findViewById(R.id.readPostLocation);

                    readPostTitle.setText(post.getTitle());
                    readPostDescription.setText(post.getDescription());
                    readPostImage.setImageBitmap(post.getPhoto());
                    readPostAuthor.setText(post.getAuthor().getUsername());
                    readPostDate.setText(new SimpleDateFormat("dd.MM.yyyy.").format(post.getDate()));
                }
            }
            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                Toast.makeText(ReadPostActivity.this, "Error loading post", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private class DrawerClickHandler implements IDrawerClickHandler {
        @Override
        public void handleClick(View view, int position){
            Intent intent;
            switch ((String)view.getTag()){
                case "Posts":
                    intent = new Intent(ReadPostActivity.this, PostsActivity.class);
                    startActivity(intent);
                    break;
                case "Settings":
                    intent = new Intent(ReadPostActivity.this, SettingsActivity.class);
                    startActivity(intent);
                    break;
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.read_post_menu, menu);
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
