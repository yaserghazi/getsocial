package sa.edu.getsocial.Faculty.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import sa.edu.getsocial.Adapters.AnnouncementAdapter;
import sa.edu.getsocial.Faculty.AddAnnouncementActivity;
import sa.edu.getsocial.Models.AnnouncementModel;
import sa.edu.getsocial.R;


public class AnnouncementFragment extends Fragment {

    List<AnnouncementModel> resultsList;
    AnnouncementAdapter nAdapter;
    RecyclerView recyclerView;
    ProgressBar progress_bar;

    public AnnouncementFragment() {
        // Required empty public constructor
    }

    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_ann, container, false);

        DatabaseReference databaseReference = FirebaseDatabase.
                getInstance("https://getsocial-3f61c-default-rtdb.firebaseio.com/")
                .getReference().child("Announcement");

        TextView no_data = (TextView) view.findViewById(R.id.no_data);

        recyclerView = view.findViewById(R.id.recycler);
        resultsList = new ArrayList<>();
        progress_bar = (ProgressBar) view.findViewById(R.id.progress_bar);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(true);

        nAdapter = new AnnouncementAdapter(getContext(), resultsList);
        recyclerView.setAdapter(nAdapter);

        progress_bar.setVisibility(View.VISIBLE);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                resultsList.clear();
                progress_bar.setVisibility(View.GONE);

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    AnnouncementModel model = snapshot.getValue(AnnouncementModel.class);
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
                Intent intent = new Intent(getContext(), AddAnnouncementActivity.class);
                startActivity(intent);

            }
        });
        return view;
    }
}