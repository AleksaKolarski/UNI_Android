package projekat.sf272016.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import java.util.ArrayList;
import java.util.Arrays;

import projekat.sf272016.R;
import projekat.sf272016.misc.DTInitializer;
import projekat.sf272016.model.misc.DrawerListItem;

public class CreatePostActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_post);

        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));

        new DTInitializer(this, new ArrayList<>(Arrays.asList(
                new DrawerListItem("Posts", "See all posts", R.drawable.ic_launcher_foreground, PostsActivity.class),
                new DrawerListItem("Settings", "Change app settings", R.drawable.ic_launcher_foreground, SettingsActivity.class)
        ))).initialize();
    }
}
