package epm.animeschedule;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {

    private EditText Email;
    private EditText Password;
    private Button Login;

    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_login);

        firebaseAuth = FirebaseAuth.getInstance();

        if(firebaseAuth.getCurrentUser() != null){
            finish();
            startActivity(new Intent(getApplicationContext(), Main.class));
        }

        progressDialog = new ProgressDialog(this);

        Email = findViewById(R.id.txtEmailL);
        Password = findViewById(R.id.txtPasswordL);
        Login = findViewById(R.id.btnLogin);

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userLogin();
            }
        });

    }
    private void userLogin(){
        String email = Email.getText().toString().trim();
        String password = Password.getText().toString().trim();

        Log.d("UserLogin", email+" and "+password);

        progressDialog.setMessage("Please Wait...");
        progressDialog.show();
        if(email.isEmpty()){
            Toast.makeText(Login.this, "Invalid Email", Toast.LENGTH_LONG).show();
            progressDialog.dismiss();
        }
        else if(password.isEmpty()){
            Toast.makeText(Login.this, "Invalid Password", Toast.LENGTH_LONG).show();
            progressDialog.dismiss();
        }
        else{
            firebaseAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                finish();
                                startActivity(new Intent(getApplicationContext(), Main.class));
                                Toast.makeText(Login.this, "Login Successful", Toast.LENGTH_LONG).show();
                            }
                            else{
                                Toast.makeText(Login.this, "Invalid Credentials", Toast.LENGTH_LONG).show();
                            }
                            progressDialog.dismiss();
                        }
                    });
        }
    }
}


