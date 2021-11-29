package sa.edu.getsocial.Student;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import sa.edu.getsocial.Adapters.RequestAdapter;
import sa.edu.getsocial.Models.RequestModel;
import sa.edu.getsocial.R;


public class MyRequestActivity extends AppCompatActivity {

    List<RequestModel> resultsList;
    RequestAdapter nAdapter;
    RecyclerView recyclerView;
    ProgressBar progress_bar;

    public MyRequestActivity() {
        // Required empty public constructor
    }

    View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_request);
        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        SharedPreferences editor = getSharedPreferences("login", MODE_PRIVATE);

        DatabaseReference databaseReference = FirebaseDatabase
                .getInstance("https://getsocial-3f61c-default-rtdb.firebaseio.com/")
                .getReference().child("Request");

        TextView no_data = (TextView) view.findViewById(R.id.no_data);
        view.findViewById(R.id.my_requst_btn).setVisibility(View.VISIBLE);
        view.findViewById(R.id.my_requst_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        recyclerView = view.findViewById(R.id.recycler);
        resultsList = new ArrayList<>();
        progress_bar = (ProgressBar) view.findViewById(R.id.progress_bar);
        recyclerView.setLayoutManager(new LinearLayoutManager(MyRequestActivity.this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(true);

        nAdapter = new RequestAdapter(MyRequestActivity.this, resultsList);
        recyclerView.setAdapter(nAdapter);

        progress_bar.setVisibility(View.VISIBLE);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                resultsList.clear();
                progress_bar.setVisibility(View.GONE);

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    RequestModel model = snapshot.getValue(RequestModel.class);
                    if (model.getUID().equals(editor.getString("ID", "")))
                        resultsList.add(model);
                    nAdapter.notifyDataSetChanged();
                }
                if (resultsList.size() == 0) {
                    no_data.setVisibility(View.VISIBLE);
                } else {
                    no_data.setVisibility(View.GONE);

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        view.findViewById(R.id.add_fab).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MyRequestActivity.this, AddRequestActivity.class);
                startActivity(intent);

            }
        });
    }
}