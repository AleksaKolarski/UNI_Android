package projekat.sf272016.activities;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import projekat.sf272016.R;
import projekat.sf272016.misc.DrawerHelper;
import projekat.sf272016.misc.IDrawerClickHandler;
import projekat.sf272016.misc.ToolbarHelper;
import projekat.sf272016.model.misc.DrawerListItem;

public class ProfileActivity extends AppCompatActivity {

    DrawerHelper drawerHelper;
    ToolbarHelper toolbarHelper;

    private Button changePasswordButton;
    private LinearLayout changePasswordLayout;
    private EditText changePasswordBox1;
    private EditText changePasswordBox2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        /* Drawer */
        ArrayList<DrawerListItem> drawerListItems = new ArrayList<>();
        drawerListItems.add(new DrawerListItem("Posts", "See all posts", R.drawable.ic_launcher_foreground));
        drawerListItems.add(new DrawerListItem("Settings", "Change app settings", R.drawable.ic_launcher_foreground));
        drawerHelper = new DrawerHelper(this, drawerListItems, new DrawerClickHandler());
        drawerHelper.initialize();

        /* Toolbar */
        toolbarHelper = new ToolbarHelper(this);
        toolbarHelper.initialize();

        changePasswordButton = (Button)findViewById(R.id.profileActivityChangePasswordButton);
        changePasswordLayout = (LinearLayout)findViewById(R.id.profileActivityChangePasswordLayout);
        changePasswordBox1 = (EditText)findViewById(R.id.profileActivityChangePasswordBox1);
        changePasswordBox2 = (EditText)findViewById(R.id.profileActivityChangePasswordBox2);
    }

    private class DrawerClickHandler implements IDrawerClickHandler {
        @Override
        public void handleClick(View view, int position){
            Toast.makeText(getApplicationContext(), ((TextView)((RelativeLayout) view).getChildAt(1)).getText(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.profile_menu, menu);
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

    public void btnProfileChangePassword(View view) {

        // Sakrivamo dugme za promenu sifre
        changePasswordButton.setVisibility(View.INVISIBLE);

        // Otkrivamo polja za unos nove sifre i dugme za potvrdu
        changePasswordLayout.setVisibility(View.VISIBLE);

        // Praznimo polja za svaki slucaj
        changePasswordBox1.setText("");
        changePasswordBox2.setText("");
    }

    public void btnProfileChangePasswordConfirm(View view) {

        InputMethodManager inputManager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow((null == getCurrentFocus()) ? null : getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

        String password1 = changePasswordBox1.getText().toString();
        String password2 = changePasswordBox2.getText().toString();

        if(password1.equals(password2)){

            // TODO poziv rest servisa za izmenu sifre

            // Sakrivamo polja za unos nove sifre i dugme za potvrdu
            changePasswordLayout.setVisibility(View.INVISIBLE);

            // Otkrivamo dugme za promenu sifre
            changePasswordButton.setVisibility(View.VISIBLE);
        }
        else{
            error("Both passwords should match!");
        }
    }

    private void error(String message){
        ((TextView)findViewById(R.id.profileActivityError)).setText(message);
    }
}
