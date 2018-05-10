package projekat.sf272016.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import projekat.sf272016.R;
import projekat.sf272016.misc.DrawerHelper;
import projekat.sf272016.misc.IDrawerClickHandler;
import projekat.sf272016.misc.ToolbarHelper;
import projekat.sf272016.model.misc.DrawerListItem;

public class PostsActivity extends AppCompatActivity{

    DrawerHelper drawerHelper;
    ToolbarHelper toolbarHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_posts);

        /* Drawer */
        ArrayList<DrawerListItem> items = new ArrayList<>();
        items.add(new DrawerListItem("Settings", "Change app settings", R.drawable.ic_launcher_foreground, SettingsActivity.class));
        items.add(new DrawerListItem("Not", "bla", R.drawable.ic_launcher_foreground, SettingsActivity.class));
        drawerHelper = new DrawerHelper(this, items, new ClickHandler());
        drawerHelper.initialize();

        /* Toolbar */
        toolbarHelper = new ToolbarHelper(this);
        toolbarHelper.initialize();

    }

    private class ClickHandler implements IDrawerClickHandler {
        @Override
        public void handleClick(View view, int position){
            //Toast.makeText(getApplicationContext(), new Integer(position).toString() , Toast.LENGTH_SHORT).show();
            Toast.makeText(getApplicationContext(), ((TextView)((RelativeLayout) view).getChildAt(1)).getText(), Toast.LENGTH_SHORT).show();
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
