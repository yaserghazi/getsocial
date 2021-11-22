package sa.edu.getsocial.Faculty;

import android.app.ProgressDialog;
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


import sa.edu.getsocial.Models.AnnouncementModel;
import sa.edu.getsocial.R;


public class AddAnnouncementActivity extends AppCompatActivity {

    EditText title,details,link;
    ProgressDialog dialogM;
    DatabaseReference database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_announcement);
        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        dialogM = new ProgressDialog(this);
        dialogM.setMessage("Please Wait ...");
        dialogM.setIndeterminate(true);


        database = FirebaseDatabase.getInstance("https://getsocial-3f61c-default-rtdb.firebaseio.com/").getReference().
                child("Announcement");
        title = (EditText) findViewById(R.id.title);
        details = (EditText) findViewById(R.id.details);
        link = (EditText) findViewById(R.id.link);

        findViewById(R.id.buttonSave).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!title.getText().toString().trim().equals("")&&!details.getText().toString().trim().equals("") ) {
                    dialogM.show();
                    String key = database.push().getKey();

                    AnnouncementModel announcementModel=new  AnnouncementModel(key,title.getText().toString(),
                            details.getText().toString(), link.getText().toString());
                    database.child(key).setValue(announcementModel).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            dialogM.dismiss();

                            Toast.makeText(AddAnnouncementActivity.this, "Added successfully", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            dialogM.dismiss();
                            Toast.makeText(AddAnnouncementActivity.this, "an error occurred " + e.getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    });
                }else {
                    Toast.makeText(AddAnnouncementActivity.this, "Please see who entered the data correctly", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }


}