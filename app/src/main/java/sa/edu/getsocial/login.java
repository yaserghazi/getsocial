package sa.edu.getsocial;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.widget.Button;
import android.view.View;
import android.content.Intent;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import sa.edu.getsocial.Faculty.FacultyMainActivity;

public class login extends AppCompatActivity {
    public Button button;
    CheckBox remember;
    Button b1;
    EditText ed1, ed2;
    TextView tx1, tx2;
    int counter = 10;
    boolean isValid = false;
    String userName = "";
    String userPassword = "";

    /* Class to hold credentials */
    class Credentials {
        String name = "Admin";
        String password = "123456";
    }

    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_interface);
        FirebaseApp.initializeApp(this);
        dialog = new ProgressDialog(this);
        dialog.setMessage("please wait ...");
        dialog.setIndeterminate(true);
        /* Bind the XML views to Java Code Elements */
        ed1 = findViewById(R.id.editTextUserName);
        ed2 = findViewById(R.id.editTextPassword);
        tx1 = findViewById(R.id.username);
        b1 = findViewById(R.id.button);
        remember = findViewById(R.id.checkBox);
        /* Describe the logic when the login button is clicked */
        SharedPreferences preferences = getSharedPreferences("checkbox", MODE_PRIVATE);
        String checkbox = preferences.getString("remember", " ");
        if (checkbox.equals("true")) {
            Intent intent = new Intent(login.this, FacultyMainActivity.class);
            startActivity(intent);
        } else if (checkbox.equals("false")) {
            Toast.makeText(this, "Please Sign in", Toast.LENGTH_SHORT).show();

        }
        remember.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (compoundButton.isChecked()) {
                    SharedPreferences preferences = getSharedPreferences("checkbox", MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("remember", "true");
                    editor.apply();
                    Toast.makeText(login.this, "Checked", Toast.LENGTH_SHORT).show();
                } else if (!compoundButton.isChecked()) {
                    SharedPreferences preferences = getSharedPreferences("checkbox", MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("remember", "false");
                    editor.apply();
                    Toast.makeText(login.this, "Unchecked", Toast.LENGTH_SHORT).show();
                }
            }
        });

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                /* Obtain user inputs */
                userName = ed1.getText().toString();
                userPassword = ed2.getText().toString();
                /* Check if the user inputs are empty */
                if (userName.isEmpty() || userPassword.isEmpty()) {
                    /* Display a message toast to user to enter the details */
                    Toast.makeText(login.this, "Please enter name and password!", Toast.LENGTH_LONG).show();

                } else {
                    isPressed = true;
                    dialog.show();
                    checkEmailAndPassword(userName.trim(), userPassword.trim());

                }
            }
        });
    }

    boolean isPressed = true;

    private void checkEmailAndPassword(String userName, String password) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance("https://getsocial-3f61c-default-rtdb.firebaseio.com/")
                .getReference().child("User");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists())
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        User user = snapshot.getValue(User.class);
                        if (user.getUsername().equalsIgnoreCase(userName) &&
                                user.getPassword().equalsIgnoreCase(password)) {

                            if(isPressed) {
                                dialog.dismiss();


                                if (user.getType() == 1) {
                                    SharedPreferences.Editor editor = getSharedPreferences("login", MODE_PRIVATE).edit();
                                    editor.putString("Name", user.getUsername());
                                    editor.putString("ID", user.getId());
                                    editor.putString("email", user.getEmail());
                                    editor.putInt("UserType", user.getType());

                                    editor.apply();

                                    Intent intent = new Intent(login.this, FacultyMainActivity.class);
                                    startActivity(intent);


                                } else if (user.getType() == 2) {
                                    SharedPreferences.Editor editor = getSharedPreferences("login", MODE_PRIVATE).edit();
                                    editor.putString("Name", user.getUsername());
                                    editor.putString("ID", user.getId());
                                    editor.putString("email", user.getEmail());
                                    editor.putInt("UserType", user.getType());
                                    //       Intent intent = new Intent(login.this, AcademicMainActivity.class);
                                    //       startActivity(intent);

                                }
                                isPressed = false;
                                finish();
                            }
                        }
                    }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                dialog.dismiss();
                Toast.makeText(login.this, "An error occurred during the login process", Toast.LENGTH_SHORT).show();
            }
        });

    }

    /* Validate the credentials */
    private boolean validate(String userName, String userPassword) {
        /* Get the object of Credentials class */
        Credentials credentials = new Credentials();

        /* Check the credentials */
        if (userName.equals(credentials.name) && userPassword.equals(credentials.password)) {
            return true;
        }

        return false;
    }
}


