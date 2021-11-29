package sa.edu.getsocial.Student.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

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
import sa.edu.getsocial.Faculty.AddAnnouncementActivity;
import sa.edu.getsocial.MessageActivity;
import sa.edu.getsocial.Models.RequestModel;
import sa.edu.getsocial.R;
import sa.edu.getsocial.Student.AddRequestActivity;
import sa.edu.getsocial.Student.MyRequestActivity;


public class RequestFragment extends Fragment {

    List<RequestModel> resultsList;
    RequestAdapter nAdapter;
    RecyclerView recyclerView;
    ProgressBar progress_bar;

    public RequestFragment() {
        // Required empty public constructor
    }

    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_ann, container, false);

        DatabaseReference databaseReference = FirebaseDatabase
                .getInstance("https://getsocial-3f61c-default-rtdb.firebaseio.com/")
                .getReference().child("Request");

        TextView no_data = (TextView) view.findViewById(R.id.no_data);

        view.findViewById(R.id.my_requst_btn).setVisibility(View.VISIBLE);
        view.findViewById(R.id.my_requst_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), MyRequestActivity.class);
                startActivity(intent);
            }
        });
        recyclerView = view.findViewById(R.id.recycler);
        resultsList = new ArrayList<>();
        progress_bar = (ProgressBar) view.findViewById(R.id.progress_bar);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(true);

        nAdapter = new RequestAdapter(getContext(), resultsList);
        recyclerView.setAdapter(nAdapter);

        progress_bar.setVisibility(View.VISIBLE);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                resultsList.clear();
                progress_bar.setVisibility(View.GONE);

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    RequestModel model = snapshot.getValue(RequestModel.class);
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
                Intent intent = new Intent(getContext(), AddRequestActivity.class);
                startActivity(intent);

            }
        });
        return view;
    }
}