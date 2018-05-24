package projekat.sf272016.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

import projekat.sf272016.R;
import projekat.sf272016.adapters.PostListAdapter;
import projekat.sf272016.adapters.TagAdapter;
import projekat.sf272016.misc.DrawerHelper;
import projekat.sf272016.misc.IDrawerClickHandler;
import projekat.sf272016.misc.ToolbarHelper;
import projekat.sf272016.model.Post;
import projekat.sf272016.model.Tag;
import projekat.sf272016.model.User;
import projekat.sf272016.model.misc.DrawerListItem;
import projekat.sf272016.model.misc.TagSelected;
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
        toolbarHelper.initialize("Create new post");
    }

    @Override
    protected void onResume(){
        super.onResume();

        Call<ArrayList<Tag>> call = Remote.tagRemote.getAllTags();
        call.enqueue(new Callback<ArrayList<Tag>>() {
            @Override
            public void onResponse(Call<ArrayList<Tag>> call, Response<ArrayList<Tag>> response) {
                if(response.code() == 200){
                    if(response.body() != null){
                        ArrayList<Tag> tags = response.body();
                        ArrayList<TagSelected> tagsSelected = new ArrayList<>();
                        for(Tag tag: tags){
                            TagSelected tagSelected = new TagSelected();
                            tagSelected.setTag(tag);
                            tagSelected.setSelected(false);
                            tagsSelected.add(tagSelected);
                        }
                        TagAdapter tagAdapter = new TagAdapter(CreatePostActivity.this, tagsSelected);
                        ListView tagListView = (ListView) findViewById(R.id.createPostActivityTags);
                        tagListView.setAdapter(tagAdapter);
                    }
                }
            }
            @Override
            public void onFailure(Call<ArrayList<Tag>> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    public void btnCreate(View view) {

        if(((EditText) findViewById(R.id.createPostActivityTitle)).getText().toString().length() < 3){
            return;
        }
        if(((EditText) findViewById(R.id.createPostActivityDescription)).getText().toString().length() < 3){
            return;
        }

        Post post = new Post();
        post.setTitle(((EditText) findViewById(R.id.createPostActivityTitle)).getText().toString());
        post.setDescription(((EditText) findViewById(R.id.createPostActivityDescription)).getText().toString());
        User user = new User();
        user.setUsername(PreferenceManager.getDefaultSharedPreferences(this).getString("loggedInUserUsername", ""));
        post.setAuthor(user);

        BitmapDrawable drawable = (BitmapDrawable)((ImageView)findViewById(R.id.createPostActivityPhoto)).getDrawable();
        if(drawable != null){
            post.setPhoto(drawable.getBitmap());
        }

        ListView tagListView = (ListView) findViewById(R.id.createPostActivityTags);
        TagAdapter tagAdapter = (TagAdapter) tagListView.getAdapter();

        ArrayList<Tag> tagList = new ArrayList<>();
        for(int i = 0; i < tagAdapter.getCount(); i++){
            TagSelected tagSelected = (TagSelected) tagAdapter.getItem(i);
            if(tagSelected.isSelected()){
                tagList.add(tagSelected.getTag());
            }
        }
        post.setTags(tagList);

        Call<Post> call = Remote.postRemote.createPost(post);
        call.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                Post post1 = response.body();
                if (response.code() == 201 && post1 != null) {
                    Toast.makeText(CreatePostActivity.this, "Post created.", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(CreatePostActivity.this, ReadPostActivity.class);
                    intent.putExtra("postId", post1.getId());
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(CreatePostActivity.this, "Could not create post.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }


    static final int REQUEST_IMAGE_CAPTURE = 1;

    public void btnPhoto(View view) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }


    // Rezultat kamere
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            try {
                final Bitmap imageBitmap = (Bitmap) extras.get("data");
                ((ImageView)findViewById(R.id.createPostActivityPhoto)).setImageBitmap(imageBitmap);
            } catch (Exception e) {
                Toast.makeText(this, "Could not load image.", Toast.LENGTH_SHORT).show();
            }
        }
    }


    private class DrawerClickHandler implements IDrawerClickHandler {
        @Override
        public void handleClick(View view, int position) {
            Intent intent;
            switch ((String) view.getTag()) {
                case "Posts":
                    intent = new Intent(CreatePostActivity.this, PostsActivity.class);
                    startActivity(intent);
                    break;
                case "Settings":
                    intent = new Intent(CreatePostActivity.this, SettingsActivity.class);
                    startActivity(intent);
                    break;
            }
        }
    }
}
