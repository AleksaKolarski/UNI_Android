package projekat.sf272016.misc;

import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import projekat.sf272016.R;

public class ToolbarHelper {

    private AppCompatActivity activity;
    private ActionBarDrawerToggle drawerToggle;

    public ToolbarHelper(AppCompatActivity activity){
        this.activity = activity;
    }

    public void initialize(){
        Toolbar toolbar = (Toolbar) activity.findViewById(R.id.toolbar);
        DrawerLayout drawerLayout = (DrawerLayout) activity.findViewById(R.id.drawerLayout);
        activity.setSupportActionBar(toolbar);
        final android.support.v7.app.ActionBar actionBar = activity.getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_notifications_black_24dp);
            actionBar.setHomeButtonEnabled(true);
        }
        drawerToggle = new ActionBarDrawerToggle(
                activity,                  /* host Activity */
                drawerLayout,         /* DrawerLayout object */
                toolbar,  /* nav drawer image to replace 'Up' caret */
                R.string.drawer_open,  /* "open drawer" description for accessibility */
                R.string.drawer_close  /* "close drawer" description for accessibility */
        );
    }
}
