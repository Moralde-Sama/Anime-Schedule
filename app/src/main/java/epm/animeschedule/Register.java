package epm.animeschedule;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Register extends AppCompatActivity {

    private EditText Email;
    private EditText Password;

    private Button Register;
    private Button Login;

    private ProgressDialog progressDialog;

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_register);

        progressDialog = new ProgressDialog(this);
        firebaseAuth = FirebaseAuth.getInstance();

        Email = findViewById(R.id.txtEmail);
        Password = findViewById(R.id.txtPassword);
        Register = findViewById(R.id.btnRegister);


        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RegisterUser();
            }
        });

    }
    private void RegisterUser(){
        String email = Email.getText().toString().trim();
        String password = Password.getText().toString().trim();


        if(email.isEmpty()){
            Toast.makeText(epm.animeschedule.Register.this, "Invalid Email", Toast.LENGTH_LONG).show();
        }
        else if(password.isEmpty()){
            Toast.makeText(epm.animeschedule.Register.this, "Invalid Password", Toast.LENGTH_LONG).show();
        }
        else{
            progressDialog.setMessage("Registering Please Wait...");
            progressDialog.show();
            firebaseAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(epm.animeschedule.Register.this, "Registered Successful", Toast.LENGTH_LONG).show();
                                progressDialog.dismiss();
                            }
                            else{
                                Toast.makeText(epm.animeschedule.Register.this, "Could not main_register, please try again.", Toast.LENGTH_LONG).show();
                                progressDialog.dismiss();
                            }
                        }
                    });
        }
    }
}
