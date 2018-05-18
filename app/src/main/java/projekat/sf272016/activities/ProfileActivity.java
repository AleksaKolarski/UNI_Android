package projekat.sf272016.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;

import projekat.sf272016.R;
import projekat.sf272016.misc.DrawerHelper;
import projekat.sf272016.misc.IDrawerClickHandler;
import projekat.sf272016.misc.ToolbarHelper;
import projekat.sf272016.model.User;
import projekat.sf272016.model.misc.DrawerListItem;
import projekat.sf272016.remote.Remote;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.HTTP;

public class ProfileActivity extends AppCompatActivity {

    DrawerHelper drawerHelper;
    ToolbarHelper toolbarHelper;

    private Button changePasswordButton;
    private LinearLayout changePasswordLayout;
    private EditText changePasswordBox1;
    private EditText changePasswordBox2;

    private User user;

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
        toolbarHelper.initialize("Profile");
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Ucitavanje korisnika
        if (user == null) {
            String username = PreferenceManager.getDefaultSharedPreferences(this).getString("loggedInUserUsername", "");
            Call<User> call = Remote.userRemote.getUserByUsername(username);
            call.enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    user = response.body();
                    if (user == null || response.code() != 200) {
                        Toast.makeText(ProfileActivity.this, "Could not find user.", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    ((ImageView) findViewById(R.id.profileActivityImage)).setImageBitmap(user.getPhoto());
                    ((TextView) findViewById(R.id.profileActivityName)).setText(user.getName());
                    ((TextView) findViewById(R.id.profileActivityUsername)).setText(user.getUsername());
                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {
                    Toast.makeText(ProfileActivity.this, "Could not find user.", Toast.LENGTH_SHORT).show();
                }
            });
        }

        if (changePasswordButton == null)
            changePasswordButton = (Button) findViewById(R.id.profileActivityChangePasswordButton);
        if (changePasswordLayout == null)
            changePasswordLayout = (LinearLayout) findViewById(R.id.profileActivityChangePasswordLayout);
        if (changePasswordBox1 == null)
            changePasswordBox1 = (EditText) findViewById(R.id.profileActivityChangePasswordBox1);
        if (changePasswordBox2 == null)
            changePasswordBox2 = (EditText) findViewById(R.id.profileActivityChangePasswordBox2);
    }

    private class DrawerClickHandler implements IDrawerClickHandler {
        @Override
        public void handleClick(View view, int position) {
            Intent intent;
            switch ((String)view.getTag()){
                case "Posts":
                    intent = new Intent(ProfileActivity.this, PostsActivity.class);
                    startActivity(intent);
                    break;
                case "Settings":
                    intent = new Intent(ProfileActivity.this, SettingsActivity.class);
                    startActivity(intent);
                    break;
            }
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

        InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow((null == getCurrentFocus()) ? null : getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

        final String password1 = changePasswordBox1.getText().toString();
        String password2 = changePasswordBox2.getText().toString();

        if (password1.equals(password2) && password1.length() >= 3) {

            user.setPassword(password1);

            Call<User> call = Remote.userRemote.editUser(user);
            call.enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {

                    user = response.body();

                    if (user == null || response.code() != 200) {
                        Toast.makeText(ProfileActivity.this, "Could not change password.", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    // Sakrivamo polja za unos nove sifre i dugme za potvrdu
                    changePasswordLayout.setVisibility(View.INVISIBLE);
                    // Otkrivamo dugme za promenu sifre
                    changePasswordButton.setVisibility(View.VISIBLE);

                    Toast.makeText(ProfileActivity.this, "Password changed.", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {
                    Toast.makeText(ProfileActivity.this, "Could not change password.", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(this, "Passwords must match and be longer than 3.", Toast.LENGTH_SHORT).show();
        }
    }
}
