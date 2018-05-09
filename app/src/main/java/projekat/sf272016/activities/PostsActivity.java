package projekat.sf272016.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.ArrayList;
import java.util.Arrays;

import projekat.sf272016.R;
import projekat.sf272016.misc.DrawerInitializer;
import projekat.sf272016.model.misc.DrawerListItem;

public class PostsActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_posts);

        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));

        new DrawerInitializer(this, new ArrayList<>(Arrays.asList(
                new DrawerListItem("Settings", "Change app settings", R.drawable.ic_launcher_foreground, SettingsActivity.class)
        ))).initialize();
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
