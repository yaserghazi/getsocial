package sa.edu.getsocial.Faculty;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import sa.edu.getsocial.Adapters.AnnouncementAdapter;
import sa.edu.getsocial.Adapters.QuestionsAdapter;
import sa.edu.getsocial.Models.AnnouncementModel;
import sa.edu.getsocial.Models.QuestionModel;
import sa.edu.getsocial.Models.QuizModel;
import sa.edu.getsocial.R;


public class AddQuizActivity extends AppCompatActivity {

    EditText course_name, date, grade;
    EditText QuestionE, answer1E, answer2E, answer3E, answer4E, answer_success;
    ProgressDialog dialogM;
    DatabaseReference database;

    List<QuestionModel> questionModelList;
    RecyclerView recyclerView;
    QuestionsAdapter questionsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_quiz);
        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        dialogM = new ProgressDialog(this);
        dialogM.setMessage("Please Wait ...");
        dialogM.setIndeterminate(true);

        questionModelList = new ArrayList<>();
        database = FirebaseDatabase.getInstance("https://getsocial-3f61c-default-rtdb.firebaseio.com/").getReference().
                child("Quizzes");
        course_name = (EditText) findViewById(R.id.course_name);
        date = (EditText) findViewById(R.id.date);
        grade = (EditText) findViewById(R.id.grade);
        getDate(date);
        QuestionE = (EditText) findViewById(R.id.QuestionE);
        answer1E = (EditText) findViewById(R.id.answer1E);
        answer2E = (EditText) findViewById(R.id.answer2E);
        answer3E = (EditText) findViewById(R.id.answer3E);
        answer4E = (EditText) findViewById(R.id.answer4E);
        answer_success = (EditText) findViewById(R.id.answer_success);

        recyclerView = findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(true);

        questionsAdapter = new QuestionsAdapter(this, questionModelList);
        recyclerView.setAdapter(questionsAdapter);

        findViewById(R.id.addQuestion).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (ValidationQ()) {
                    questionModelList.add(new QuestionModel(questionModelList.size() + "", QuestionE.getText().toString(), answer1E.getText().toString(),
                            answer2E.getText().toString(), answer3E.getText().toString(), answer4E.getText().toString(), answer_success.getText().toString()
                    ));
                    QuestionE.setText("");
                    answer1E.setText("");
                    answer2E.setText("");
                    answer3E.setText("");
                    answer4E.setText("");
                    answer_success.setText("");
                    questionsAdapter.notifyDataSetChanged();
                }


            }
        });

        findViewById(R.id.buttonSave).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Validation()) {
                    dialogM.show();
                    String key = database.push().getKey();

                    QuizModel quizModel = new QuizModel(key, course_name.getText().toString(),
                            date.getText().toString(), grade.getText().toString(), questionModelList);
                    database.child(key).setValue(quizModel).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            dialogM.dismiss();

                            Toast.makeText(AddQuizActivity.this, "Added successfully", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            dialogM.dismiss();
                            Toast.makeText(AddQuizActivity.this, "an error occurred " + e.getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    });
                }

            }
        });

    }

    public boolean ValidationQ() {
        if (QuestionE.getText().toString().equals("")) {
            QuestionE.setError("needed *");
            QuestionE.requestFocus();
            return false;
        }
        if (answer1E.getText().toString().equals("")) {
            answer1E.setError("needed *");
            answer1E.requestFocus();
            return false;
        }
        if (answer2E.getText().toString().equals("")) {
            answer2E.setError("needed *");
            answer2E.requestFocus();
            return false;
        }
        if (answer3E.getText().toString().equals("")) {
            answer3E.setError("needed *");
            answer3E.requestFocus();
            return false;
        }
        if (answer4E.getText().toString().equals("")) {
            answer4E.setError("needed *");
            answer4E.requestFocus();
            return false;
        }
        String success = answer_success.getText().toString().toLowerCase().trim();
        if (success.equals("a")) {

            return true;
        } else if (success.equals("b")) {

            return true;
        }else if (success.equals("c")) {

            return true;
        } else if (success.equals("d")) {

            return true;
        } else {
            answer_success.setError("just a b c d *");
            answer_success.requestFocus();
            return false;
        }
    }

    public boolean Validation() {
        if (course_name.getText().toString().equals("")) {
            course_name.setError("needed *");
            course_name.requestFocus();
            return false;
        }
        if (date.getText().toString().equals("")) {
            date.setError("needed *");
            date.requestFocus();
            return false;
        }
        if (grade.getText().toString().equals("")) {
            grade.setError("needed *");
            grade.requestFocus();
            return false;
        }
        if (questionModelList.size() == 0) {

            Toast.makeText(AddQuizActivity.this, "PLz add Questions", Toast.LENGTH_SHORT).show();
            return false;

        }

        return true;
    }

    public void getDate(final EditText editText) {

        final Calendar currentDate = Calendar.getInstance();
        final Calendar date = Calendar.getInstance();
        final DatePickerDialog datePickerDialog = new DatePickerDialog(AddQuizActivity.this, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {

                date.set(year, monthOfYear, dayOfMonth);
                String myFormat = "dd/MM/yyyy";
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                editText.setText(sdf.format(date.getTime()));
                editText.setError(null);

            }
        }, currentDate.get(Calendar.YEAR), currentDate.get(Calendar.MONTH), currentDate.get(Calendar.DATE));
        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                datePickerDialog.show();
            }
        });
        // datePickerDialog.getDatePicker().setMaxDate(currentDate.getTimeInMillis());

    }
}