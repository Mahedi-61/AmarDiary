package bd.org.fulkuri.amardiary;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class CreateAccount extends Activity implements View.OnClickListener {

    private EditText  etMobileNoCA, etNameCA, etPasswordCA, etRePasswordCA;
    private SharedPreferences profile;


    private void initialize() {
        etNameCA = (EditText) findViewById(R.id.etNameCA);
        etMobileNoCA = (EditText) findViewById(R.id.etMobileNoCA);
        etPasswordCA = (EditText) findViewById(R.id.etPasswordCA);
        etRePasswordCA = (EditText) findViewById(R.id.etRePasswordCA);

        Button bCreareAccountCA = (Button) findViewById(R.id.bCreareAccountCA);
        bCreareAccountCA.setOnClickListener(this);
    }


    public void onClick(View view) {
        if (etNameCA.getText().toString().trim().equals("")) {
            Toast.makeText(this, "Please enter your name", Toast.LENGTH_SHORT).show();
            return;
        }

        if (etMobileNoCA.getText().toString().trim().equals("")) {
            Toast.makeText(this, "Please enter your mobile number", Toast.LENGTH_SHORT).show();
            return;
        }

        if (etPasswordCA.getText().toString().trim().equals("")) {
            Toast.makeText(this, "Please enter your password", Toast.LENGTH_SHORT).show();
            return;
        }
        if (etRePasswordCA.getText().toString().trim().equals("")) {
            Toast.makeText(this, "Please re-enter your password", Toast.LENGTH_SHORT).show();
            return;
        }
        if (etPasswordCA.getText().toString().equals(etRePasswordCA.getText().toString())) {
            SharedPreferences.Editor edit = profile.edit();
            edit.putString("login_password", etPasswordCA.getText().toString());
            edit.putString("user_name", etNameCA.getText().toString());
            edit.putString("mobile_number", etMobileNoCA.getText().toString());
            edit.putInt("first_time", 1);
            edit.commit();
            startActivity(new Intent(this, MainActivity.class));
            return;
        } else {
            Toast.makeText(this, "Password doesn't match. Try again !!", Toast.LENGTH_SHORT).show();
            return;
        }
    }

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_create_account);
        profile = getSharedPreferences("profile", 0);
        initialize();
    }

    protected void onPause() {
        super.onPause();
        finish();
    }

}
