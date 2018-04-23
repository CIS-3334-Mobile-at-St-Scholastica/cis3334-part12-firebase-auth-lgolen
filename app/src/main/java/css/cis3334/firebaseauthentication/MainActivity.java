package css.cis3334.firebaseauthentication;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private TextView textViewStatus;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private Button buttonLogin;
    private Button buttonGoogleLogin;
    private Button buttonCreateLogin;
    private Button buttonSignOut;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewStatus = (TextView) findViewById(R.id.textViewStatus);
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        buttonLogin = (Button) findViewById(R.id.buttonLogin);
        buttonGoogleLogin = (Button) findViewById(R.id.buttonGoogleLogin);
        buttonCreateLogin = (Button) findViewById(R.id.buttonCreateLogin);
        buttonSignOut = (Button) findViewById(R.id.buttonSignOut);
        mAuth = FirebaseAuth.getInstance();

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.d("CIS3334", "normal login ");
                signIn(editTextEmail.getText().toString(), editTextPassword.getText().toString());
            }
        });

        buttonCreateLogin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.d("CIS3334", "Create Account ");
                createAccount(editTextEmail.getText().toString(), editTextPassword.getText().toString());
            }
        });

        buttonGoogleLogin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.d("CIS3334", "Google login ");
                googleSignIn();
            }
        });

        buttonSignOut.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.d("CIS3334", "Logging out - signOut ");
                signOut();
            }
        });


    }
    /*
     * Sets the current user variable to check if someone is already authenticated. Sets the status to signed in/signed out.
     */
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            // User is signed in
            textViewStatus.setText("Signed In");
        } else {
            // User is signed out
            textViewStatus.setText("Signed Out");
        }
    }
    /*
     * Calls the createUserWithEmailAndPassword method on the FirebaseAuth instance. If successful, set the current user.
     * If unsuccessful, log the exception.
     */
    private void createAccount(String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        // User is signed in
                        FirebaseUser user = mAuth.getCurrentUser();
                        // Set status to signed in
                        textViewStatus.setText("Signed In");
                    } else {
                        // If sign in fails, log the exception.
                        Log.w("CIS3334", "createUserWithEmail:failure", task.getException());
                        // Set status to signed out on failure.
                        textViewStatus.setText("Signed Out");
                    }

                }
            });
    }
    /*
     * Call the signInWithEmailAndPassword method on the FirebaseAuth instance. If successful, set the current user.
     * If unsuccessful, log the exception and display and error message.
     */
    private void signIn(String email, String password){
        mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        // User is signed in
                        FirebaseUser user = mAuth.getCurrentUser();
                        // Set status to signed in
                        textViewStatus.setText("Signed In");
                    } else {
                        // If sign in fails, log the exception.
                        Log.w("CIS3334", "signInWithEmail:failure", task.getException());
                        // Set status to signed out on failure.
                        textViewStatus.setText("Signed Out");
                    }

                }
            });
    }
    /*
     * Sign out the currently logged in user.
     */
    private void signOut () {
        // Sign out of FirebaseAuth.
        mAuth.signOut();
        //Set status to signed out.
        textViewStatus.setText("Signed Out");
    }

    private void googleSignIn() {

    }




}
