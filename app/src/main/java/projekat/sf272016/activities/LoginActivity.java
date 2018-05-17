package projekat.sf272016.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import projekat.sf272016.R;
import projekat.sf272016.model.User;
import projekat.sf272016.remote.LoginRemote;
import projekat.sf272016.remote.Remote;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private EditText usernameBox;
    private EditText passwordBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        usernameBox = (EditText) findViewById(R.id.loginActivityUsername);
        passwordBox = (EditText) findViewById(R.id.loginActivityPassword);
    }

    @Override
    protected void onResume(){
        super.onResume();

        // Ako je vec ulogovan
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        if(!sharedPreferences.getString("loggedInUserUsername", "").equals("")){
            Intent intent = new Intent(LoginActivity.this, PostsActivity.class);
            startActivity(intent);
            LoginActivity.this.finish();
        }
    }

    public void btnLogin(View view) {

        String username = usernameBox.getText().toString();
        String password = passwordBox.getText().toString();

        Call<User> call = Remote.loginRemote.login(username, password);

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                User user = response.body();
                if(user == null){
                    error("Wrong credentials");
                    return;
                }

                // Upisivanje podataka o ulogovanom korisniku u SharedPreferences
                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(LoginActivity.this);
                SharedPreferences.Editor sharedPreferencesEditor = sharedPreferences.edit();
                sharedPreferencesEditor.putString("loggedInUserUsername", user.getUsername());
                sharedPreferencesEditor.commit();

                // Pokretanje ReadPostActivity
                Intent intent = new Intent(LoginActivity.this, PostsActivity.class);
                startActivity(intent);
                LoginActivity.this.finish();
            }
            @Override
            public void onFailure(Call<User> call, Throwable t) {
                error("Something went wrong!");
            }
        });
    }

    private void error(String message){
        ((TextView)findViewById(R.id.loginActivityError)).setText(message);
    }

    public void btnRegister(View view) {
        Toast.makeText(this, "Register", Toast.LENGTH_SHORT).show();
    }
}
