package projekat.sf272016.activities;

import android.app.Activity;
import android.content.Intent;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.menu.MenuView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import projekat.sf272016.R;
import projekat.sf272016.adapters.CommentAdapter;
import projekat.sf272016.misc.DrawerHelper;
import projekat.sf272016.misc.IDrawerClickHandler;
import projekat.sf272016.misc.ToolbarHelper;
import projekat.sf272016.model.Comment;
import projekat.sf272016.model.Post;
import projekat.sf272016.model.User;
import projekat.sf272016.model.misc.DrawerListItem;
import projekat.sf272016.remote.Remote;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReadPostActivity extends AppCompatActivity {

    DrawerHelper drawerHelper;
    ToolbarHelper toolbarHelper;
    Integer postId;

    String loggedInUser;
    String postOwner;

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
        postId = (Integer) getIntent().getIntExtra("postId", 0);

        Call<Post> call = Remote.postRemote.getPostById(postId);
        call.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                final Post post = response.body();
                if (post != null) {
                    postOwner = post.getAuthor().getUsername();
                    invalidateOptionsMenu();

                    TextView readPostTitle = (TextView) findViewById(R.id.readPostTitle);
                    TextView readPostDescription = (TextView) findViewById(R.id.readPostDescription);
                    ImageView readPostImage = (ImageView) findViewById(R.id.readPostImage);
                    TextView readPostTags = (TextView) findViewById(R.id.readPostTags);
                    TextView readPostAuthor = (TextView) findViewById(R.id.readPostAuthor);
                    TextView readPostDate = (TextView) findViewById(R.id.readPostDate);
                    TextView readPostLocation = (TextView) findViewById(R.id.readPostLocation);
                    final TextView readPostLikes = (TextView) findViewById(R.id.activity_post_likes);
                    final TextView readPostDislikes = (TextView) findViewById(R.id.activity_post_dislikes);

                    readPostTitle.setText(post.getTitle());
                    readPostDescription.setText(post.getDescription());
                    ImageView imageView = ((ImageView) findViewById(R.id.readPostImage));
                    if (post.getPhoto() != null) {

                        imageView.setAdjustViewBounds(true);
                    } else {
                        imageView.setAdjustViewBounds(false);
                    }
                    readPostImage.setImageBitmap(post.getPhoto());
                    readPostAuthor.setText(post.getAuthor().getUsername());
                    readPostDate.setText(new SimpleDateFormat("dd.MM.yyyy.").format(post.getDate()));
                    readPostLikes.setText(((Integer) post.getLikes()).toString());
                    readPostDislikes.setText(((Integer) post.getDislikes()).toString());


                    ((ImageView) findViewById(R.id.activity_post_like_button)).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Call<Integer> call = Remote.postRemote.likePost(postId);
                            call.enqueue(new Callback<Integer>() {
                                @Override
                                public void onResponse(Call<Integer> call, Response<Integer> response) {
                                    if (response.code() == 200 && response.body() != null)
                                        readPostLikes.setText(response.body().toString());
                                }

                                @Override
                                public void onFailure(Call<Integer> call, Throwable t) {
                                    t.printStackTrace();
                                }
                            });
                        }
                    });

                    ((ImageView) findViewById(R.id.activity_post_dislike_button)).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Call<Integer> call = Remote.postRemote.dislikePost(postId);
                            call.enqueue(new Callback<Integer>() {
                                @Override
                                public void onResponse(Call<Integer> call, Response<Integer> response) {
                                    if (response.code() == 200 && response.body() != null)
                                        readPostDislikes.setText(response.body().toString());
                                }

                                @Override
                                public void onFailure(Call<Integer> call, Throwable t) {
                                    t.printStackTrace();
                                }
                            });
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                Toast.makeText(ReadPostActivity.this, "Error loading post", Toast.LENGTH_SHORT).show();
            }
        });


        Call<ArrayList<Comment>> call2 = Remote.commentRemote.getCommentsByPostId(postId);
        call2.enqueue(new Callback<ArrayList<Comment>>() {
            @Override
            public void onResponse(Call<ArrayList<Comment>> call, Response<ArrayList<Comment>> response) {
                ArrayList<Comment> comments = response.body();
                if (comments != null) {
                    CommentAdapter commentAdapter = new CommentAdapter(ReadPostActivity.this, comments);
                    ListView commentListView = (ListView) findViewById(R.id.readPostComments);
                    commentListView.setAdapter(commentAdapter);
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Comment>> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    public void btnNewComment(View view) {
        String commentTitle = ((EditText) findViewById(R.id.activity_post_comment_new_title)).getText().toString();
        String commentDescription = ((EditText) findViewById(R.id.activity_post_comment_new_description)).getText().toString();
        if (commentTitle.length() >= 3 && commentDescription.length() >= 3) {
            Comment comment = new Comment();
            User author = new User();
            String username = PreferenceManager.getDefaultSharedPreferences(this).getString("loggedInUserUsername", "");
            if (username.length() > 0) {
                author.setUsername(username);
                comment.setAuthor(author);
                comment.setTitle(commentTitle);
                comment.setDescription(commentDescription);
                Post post = new Post();
                post.setId(postId);
                comment.setPost(post);

                Call<Comment> call = Remote.commentRemote.createComment(comment);
                call.enqueue(new Callback<Comment>() {
                    @Override
                    public void onResponse(Call<Comment> call, Response<Comment> response) {
                        if (response.code() == 201) {
                            ((EditText) findViewById(R.id.activity_post_comment_new_title)).setText("");
                            ((EditText) findViewById(R.id.activity_post_comment_new_description)).setText("");
                            recreate();
                        }
                    }

                    @Override
                    public void onFailure(Call<Comment> call, Throwable t) {
                        t.printStackTrace();
                    }
                });
            }
        }
    }

    private class DrawerClickHandler implements IDrawerClickHandler {
        @Override
        public void handleClick(View view, int position) {
            Intent intent;
            switch ((String) view.getTag()) {
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
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem delete = menu.findItem(R.id.toolbar_action_delete);
        if (postOwner != null) {
            if (PreferenceManager.getDefaultSharedPreferences(ReadPostActivity.this).getString("loggedInUserUsername", "").equals(postOwner)) {
                delete.setVisible(true);
            } else {
                delete.setVisible(false);
            }
        } else {
            delete.setVisible(false);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.toolbar_action_reload:
                recreate();
                return true;
            case R.id.toolbar_action_delete:
                // TODO obrisi post
                Call<Void> call = Remote.postRemote.deletePost(postId);
                call.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if (response.code() == 200) {
                            Intent intent = new Intent(ReadPostActivity.this, PostsActivity.class);
                            startActivity(intent);
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        t.printStackTrace();
                    }
                });
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
