package projekat.sf272016.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import projekat.sf272016.R;

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

    public void btnLogin(View view) {

        String username = usernameBox.getText().toString();
        String password = passwordBox.getText().toString();

        // TODO poslati kredencijale na rest servis i na osnovu toga delovati dalje
        String result; // = tryToLogIn(username, password);

        result = "success"; // TODO obrisati lol

        if(result.equals("success")){
            Intent intent = new Intent(this, PostsActivity.class);
            startActivity(intent);
            finish();
        }
        else{
            error("Wrong credentials");
        }
    }

    private void error(String message){
        ((TextView)findViewById(R.id.loginActivityError)).setText(message);
    }
}
