package projekat.sf272016.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import projekat.sf272016.R;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void btnLogin(View view){
        Intent intent = new Intent(this, PostsActivity.class);
        startActivity(intent);
        finish();
    }
}
