package sa.edu.getsocial.Student;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import sa.edu.getsocial.Models.RequestModel;
import sa.edu.getsocial.R;


public class AddRequestActivity extends AppCompatActivity {

    EditText title,details;
    ProgressDialog dialogM;
    DatabaseReference database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_request);
        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        dialogM = new ProgressDialog(this);
        dialogM.setMessage("Please Wait ...");
        dialogM.setIndeterminate(true);
        SharedPreferences editor = getSharedPreferences("login", MODE_PRIVATE);


        database = FirebaseDatabase.getInstance("https://getsocial-3f61c-default-rtdb.firebaseio.com/").getReference().
                child("Request");
        title = (EditText) findViewById(R.id.title);
        details = (EditText) findViewById(R.id.details);


        findViewById(R.id.buttonSave).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!title.getText().toString().trim().equals("")&&!details.getText().toString().trim().equals("") ) {
                    dialogM.show();
                    String key = database.push().getKey();

                    RequestModel requestModel=new  RequestModel(key,title.getText().toString(),
                            details.getText().toString(), editor.getString("ID",""),editor.getString("Name", ""));
                    database.child(key).setValue(requestModel).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            dialogM.dismiss();
                            Toast.makeText(AddRequestActivity.this, "Added successfully", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            dialogM.dismiss();
                            Toast.makeText(AddRequestActivity.this, "an error occurred " + e.getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    });
                }else {
                    Toast.makeText(AddRequestActivity.this, "Please see who entered the data correctly", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }


}