package projekat.sf272016.activities;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
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

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
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

    // TODO brisanje korisnika

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
        String username = PreferenceManager.getDefaultSharedPreferences(this).getString("loggedInUserUsername", "");
        Call<User> call = Remote.userRemote.getUserByUsername(username);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                user = response.body();
                if (user == null || response.code() != 200) {
                    Log.e("Callback", "could not find user");
                    return;
                }
                ((TextView) findViewById(R.id.profileActivityName)).setText(user.getName());
                ((TextView) findViewById(R.id.profileActivityUsername)).setText(user.getUsername());
                if(user.getPhoto() != null){
                    ((ImageView) findViewById(R.id.profileActivityImage)).setImageBitmap(user.getPhoto());
                }
                else{
                    ((ImageView) findViewById(R.id.profileActivityImage)).setImageResource(R.drawable.ic_person_black_24dp);
                }
            }
            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.e("profile getUser callback", t.getMessage());;
            }
        });

        if (changePasswordButton == null)
            changePasswordButton = (Button) findViewById(R.id.profileActivityChangePasswordButton);
        if (changePasswordLayout == null)
            changePasswordLayout = (LinearLayout) findViewById(R.id.profileActivityChangePasswordLayout);
        if (changePasswordBox1 == null)
            changePasswordBox1 = (EditText) findViewById(R.id.profileActivityChangePasswordBox1);
        if (changePasswordBox2 == null)
            changePasswordBox2 = (EditText) findViewById(R.id.profileActivityChangePasswordBox2);
    }


    static final int REQUEST_IMAGE_CAPTURE = 1;

    // Pokretanje kamere
    public void btnProfilePicture(View view) {
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
                if (user != null) {
                    user.setPhoto(imageBitmap);

                    Call<User> call = Remote.userRemote.editUser(user);
                    call.enqueue(new Callback<User>() {
                        @Override
                        public void onResponse(Call<User> call, Response<User> response) {
                            if (response.code() == 200) {
                                Toast.makeText(ProfileActivity.this, "Photo changed", Toast.LENGTH_SHORT).show();
                                ((ImageView) findViewById(R.id.profileActivityImage)).setImageBitmap(imageBitmap);
                            } else {
                                Toast.makeText(ProfileActivity.this, "Could not edit user", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<User> call, Throwable t) {
                            Toast.makeText(ProfileActivity.this, "Error editing user", Toast.LENGTH_SHORT).show();
                            t.printStackTrace();
                        }
                    });
                }
                else{
                    Toast.makeText(this, "user = null", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                Toast.makeText(this, "Could not load image.", Toast.LENGTH_SHORT).show();
            }
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
            case R.id.toolbar_action_delete:
                Call<Void> call = Remote.userRemote.deleteUser(PreferenceManager.getDefaultSharedPreferences(this).getString("loggedInUserUsername", ""));
                call.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if(response.code() == 200){
                            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(ProfileActivity.this);
                            SharedPreferences.Editor sharedPreferencesEditor = sharedPreferences.edit();
                            sharedPreferencesEditor.remove("loggedInUserUsername");
                            sharedPreferencesEditor.remove("loggedInUserName");
                            sharedPreferencesEditor.commit();

                            Intent intent = new Intent(ProfileActivity.this, LoginActivity.class);
                            startActivity(intent);
                            ProfileActivity.this.finish();
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

    private class DrawerClickHandler implements IDrawerClickHandler {
        @Override
        public void handleClick(View view, int position) {
            Intent intent;
            switch ((String) view.getTag()) {
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
                    changePasswordLayout.setVisibility(View.INVISIBLE);
                    changePasswordButton.setVisibility(View.VISIBLE);
                    Toast.makeText(ProfileActivity.this, "Password changed.", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {
                    Toast.makeText(ProfileActivity.this, "Could not change password.", Toast.LENGTH_SHORT).show();
                    t.printStackTrace();
                }
            });
        } else {
            Toast.makeText(this, "Passwords must match and be longer than 3.", Toast.LENGTH_SHORT).show();
        }
    }
}
