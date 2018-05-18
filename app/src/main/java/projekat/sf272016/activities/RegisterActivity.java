package projekat.sf272016.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import projekat.sf272016.R;
import projekat.sf272016.model.User;
import projekat.sf272016.remote.Remote;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    private EditText nameBox;
    private EditText usernameBox;
    private EditText passwordBox1;
    private EditText passwordBox2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
    }

    @Override
    protected void onResume(){
        super.onResume();

        if(nameBox == null)
            nameBox = (EditText) findViewById(R.id.registerActivityName);
        if(usernameBox == null)
            usernameBox = (EditText) findViewById(R.id.registerActivityUsername);
        if(passwordBox1 == null)
            passwordBox1 = (EditText) findViewById(R.id.registerActivityPassword1);
        if(passwordBox2 == null)
            passwordBox2 = (EditText) findViewById(R.id.registerActivityPassword2);
    }

    public void btnRegister(View view) {
        final String name = nameBox.getText().toString();
        final String username = usernameBox.getText().toString();
        final String password1 = passwordBox1.getText().toString();
        String password2 = passwordBox2.getText().toString();

        if(name.length() >= 3 && username.length() >= 3){
            if(password1.equals(password2) && password1.length() >= 3){
                Call<User> call = Remote.userRemote.getUserByUsername(username);
                call.enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        if(response.code() == 404){
                            // User not found. Continue with registration
                            User user = new User();
                            user.setName(name);
                            user.setUsername(username);
                            user.setPassword(password1);
                            Call<User> callRegister = Remote.userRemote.createUser(user);
                            callRegister.enqueue(new Callback<User>() {
                                @Override
                                public void onResponse(Call<User> call, Response<User> response) {
                                    if(response.body() != null && response.code() == 201){
                                        Toast.makeText(RegisterActivity.this, "Registered!", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                        startActivity(intent);
                                    }
                                    else{
                                        Toast.makeText(RegisterActivity.this, "Error.", Toast.LENGTH_SHORT).show();
                                    }
                                }
                                @Override
                                public void onFailure(Call<User> call, Throwable t) {
                                    Toast.makeText(RegisterActivity.this, "Cold not connect to server.", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                        else{
                            Toast.makeText(RegisterActivity.this, "User with same username already exists!", Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
                        Toast.makeText(RegisterActivity.this, "Could not connect to server.", Toast.LENGTH_SHORT).show();
                    }
                });
            }
            else{
                Toast.makeText(this, "Password must match and be at least 3 chars long.", Toast.LENGTH_SHORT).show();
            }
        }
        else{
            Toast.makeText(this, "Name and username must be at least 3 chars long", Toast.LENGTH_SHORT).show();
        }
    }
}
