package sa.edu.getsocial.Student;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import sa.edu.getsocial.Models.QuestionModel;
import sa.edu.getsocial.Models.QuizModel;
import sa.edu.getsocial.R;

public class QuestionsActivity extends AppCompatActivity {
    TextView tv;
    Button submitbutton, quitbutton;
    RadioGroup radio_g;
    RadioButton rb1, rb2, rb3, rb4;


    List<QuestionModel> question_list;

    int flag = 0;
    public static int marks = 0, correct = 0, wrong = 0;
    TextView score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions);
        Intent intent = getIntent();
        String id_quiz = intent.getStringExtra("id");
        SharedPreferences editor = getSharedPreferences("login", MODE_PRIVATE);
        String Name = editor.getString("Name", "");
        String UID = editor.getString("ID", "");
        question_list = new ArrayList<>();

        score = (TextView) findViewById(R.id.textView4);
        TextView textView = (TextView) findViewById(R.id.DispName);


        if (Name.trim().equals(""))
            textView.setText("Hello User");
        else
            textView.setText("Hello " + Name);

        submitbutton = (Button) findViewById(R.id.button3);
        quitbutton = (Button) findViewById(R.id.buttonquit);
        tv = (TextView) findViewById(R.id.tvque);

        radio_g = (RadioGroup) findViewById(R.id.answersgrp);
        rb1 = (RadioButton) findViewById(R.id.radioButton);
        rb2 = (RadioButton) findViewById(R.id.radioButton2);
        rb3 = (RadioButton) findViewById(R.id.radioButton3);
        rb4 = (RadioButton) findViewById(R.id.radioButton4);

        DatabaseReference databaseReference = FirebaseDatabase.
                getInstance("https://getsocial-3f61c-default-rtdb.firebaseio.com/")
                .getReference().child("Quizzes").child(id_quiz);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                QuizModel model = dataSnapshot.getValue(QuizModel.class);
                question_list.clear();
                question_list.addAll(model.getQuestion_list());
                method();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                finish();
            }
        });


    }

    public void method() {
        tv.setText(question_list.get(flag).getQ());
        rb1.setText(question_list.get(flag).getA1());
        rb2.setText(question_list.get(flag).getA2());
        rb3.setText(question_list.get(flag).getA3());
        rb4.setText(question_list.get(flag).getA4());
        submitbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //int color = mBackgroundColor.getColor();
                //mLayout.setBackgroundColor(color);

                if (radio_g.getCheckedRadioButtonId() == -1) {
                    Toast.makeText(getApplicationContext(), "Please select one choice", Toast.LENGTH_SHORT).show();
                    return;
                }
                RadioButton uans = (RadioButton) findViewById(radio_g.getCheckedRadioButtonId());
                String ansText = uans.getText().toString();
                String SuccessP = "";
                if (question_list.get(flag).getSuccess().toLowerCase().equals("a")) {
                    SuccessP = question_list.get(flag).getA1();
                } else if (question_list.get(flag).getSuccess().toLowerCase().equals("b")) {
                    SuccessP = question_list.get(flag).getA2();
                } else if (question_list.get(flag).getSuccess().toLowerCase().equals("c")) {
                    SuccessP = question_list.get(flag).getA3();
                } else if (question_list.get(flag).getSuccess().toLowerCase().equals("d")) {
                    SuccessP = question_list.get(flag).getA4();
                }
//                Toast.makeText(getApplicationContext(), ansText, Toast.LENGTH_SHORT).show();
                if (ansText.equals(SuccessP)) {
                    correct++;
                    Toast.makeText(getApplicationContext(), "Correct", Toast.LENGTH_SHORT).show();
                } else {
                    wrong++;
                    Toast.makeText(getApplicationContext(), "Wrong", Toast.LENGTH_SHORT).show();
                }

                flag++;

                if (score != null)
                    score.setText("" + correct);

                if (flag < question_list.size()) {
                    tv.setText(question_list.get(flag).getQ());
                    rb1.setText(question_list.get(flag).getA1());
                    rb2.setText(question_list.get(flag).getA2());
                    rb3.setText(question_list.get(flag).getA3());
                    rb4.setText(question_list.get(flag).getA4());
                } else {
                    marks = correct;
                    Intent in = new Intent(getApplicationContext(), ResultActivity.class);
                    startActivity(in);
                    finish();
                }
                radio_g.clearCheck();
            }
        });

        quitbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ResultActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}