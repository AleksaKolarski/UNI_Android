package projekat.sf272016.activities;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import projekat.sf272016.R;

public class ProfileActivity extends AppCompatActivity {
    private Button changePasswordButton;
    private LinearLayout changePasswordLayout;
    private EditText changePasswordBox1;
    private EditText changePasswordBox2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        changePasswordButton = (Button)findViewById(R.id.profileActivityChangePasswordButton);
        changePasswordLayout = (LinearLayout)findViewById(R.id.profileActivityChangePasswordLayout);
        changePasswordBox1 = (EditText)findViewById(R.id.profileActivityChangePasswordBox1);
        changePasswordBox2 = (EditText)findViewById(R.id.profileActivityChangePasswordBox2);
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
