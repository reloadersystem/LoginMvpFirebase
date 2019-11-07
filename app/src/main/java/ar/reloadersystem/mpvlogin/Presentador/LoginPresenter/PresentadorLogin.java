package ar.reloadersystem.mpvlogin.Presentador.LoginPresenter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

import ar.reloadersystem.mpvlogin.Vista.LoginView.PrincipalView.VistaPrincipal;

public class PresentadorLogin {

    private Context mContext;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private String TAG = "PresentadorLogin";


    public PresentadorLogin(Context mContext, FirebaseAuth mAuth, DatabaseReference mDatabase) {
        this.mContext = mContext;
        this.mAuth = mAuth;
        this.mDatabase = mDatabase;
    }

    public void signInUser(String email, String password) {

        final ProgressDialog dialog = new ProgressDialog(mContext);
        dialog.setMessage("Ingresando..");
        dialog.setCancelable(false);
        dialog.show();

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:Exitoso");
                            dialog.dismiss();
                            mDatabase.child("Usuarios").setValue(task.getResult().getUser().getUid()); // usuario que se logeo guardando bd
                            Intent intent = new Intent(mContext, VistaPrincipal.class);
                            mContext.startActivity(intent);

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            dialog.dismiss();
                            Toast.makeText(mContext, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }
}
