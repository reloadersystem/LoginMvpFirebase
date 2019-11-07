package ar.reloadersystem.mpvlogin.Vista.LoginView;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import ar.reloadersystem.mpvlogin.Presentador.LoginPresenter.PresentadorLogin;
import ar.reloadersystem.mpvlogin.R;

public class VistaLogin extends AppCompatActivity implements View.OnClickListener {


    private EditText mEtxtEmail, mEtxtPasword;

    private PresentadorLogin presentadorLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mEtxtEmail = findViewById(R.id.edtEmail);
        mEtxtPasword = findViewById(R.id.edtPassword);
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();

        presentadorLogin = new PresentadorLogin(this, mAuth, mDatabase);
        Button mBtnLogin = findViewById(R.id.btnIngresar);
        mBtnLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.btnIngresar:
                String email = mEtxtEmail.getText().toString().trim();
                String pass = mEtxtPasword.getText().toString().trim();

                presentadorLogin.signInUser(email, pass);
                break;
        }
    }
}
