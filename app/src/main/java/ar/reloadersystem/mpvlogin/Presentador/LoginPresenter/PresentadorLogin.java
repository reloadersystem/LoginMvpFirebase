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
import com.google.firebase.auth.SignInMethodQueryResult;
import com.google.firebase.database.DatabaseReference;

import ar.reloadersystem.mpvlogin.Vista.LoginView.PrincipalView.VistaPrincipal;

public class PresentadorLogin {

    private Context mContext;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private String TAG = "PresentadorLogin";
    private FirebaseAuth.AuthStateListener firebaseAuthListener;


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

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {

                            Log.d(TAG, "createUserWithEmail:Exitoso");
                            // dialog.dismiss();
                            //mDatabase.child("Usuarios").setValue(task.getResult().getUser().getUid()); // usuario que se logeo guardando bd
                            //dar una raiz dentro de usuarios
                            mDatabase.child("Usuarios").child(task.getResult().getUser().getUid()).setValue("FirebaseMVP"); // usuario que se logeo guardando bd
                            Intent intent = new Intent(mContext, VistaPrincipal.class);
                            mContext.startActivity(intent);

                            // Sign in success, update UI with the signed-in user's information


                        } else {
                            // If sign in fails, display a message to the user.
                            Log.i(TAG, "createUserWithEmail:failure", task.getException());
                            //dialog.dismiss();
                            Toast.makeText(mContext, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }


    public void checkEmail(String checkEmail) {
        mAuth.fetchSignInMethodsForEmail(checkEmail).addOnCompleteListener(new OnCompleteListener<SignInMethodQueryResult>() {
            @Override
            public void onComplete(@NonNull Task<SignInMethodQueryResult> task) {
                boolean Existeix = !task.getResult().getSignInMethods().isEmpty();

                if (Existeix) {
                    Toast.makeText(mContext, "EXIST", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(mContext, "NO EXIST", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
