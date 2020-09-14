package com.example.stutor.ui.dashboard;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.stutor.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.security.auth.Subject;

import static android.content.Context.MODE_PRIVATE;

public class DashboardFragment extends Fragment {

    private DashboardViewModel dashboardViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel =
                ViewModelProviders.of(this).get(DashboardViewModel.class);
        final View root = inflater.inflate(R.layout.fragment_dashboard, container, false);

        final TextView name, age, gender, location;
        name = root.findViewById(R.id.nameView);
        age = root.findViewById(R.id.ageView);
        gender = root.findViewById(R.id.genderView);
        location = root.findViewById(R.id.locationView);

        SharedPreferences sp = this.getActivity().getSharedPreferences("login", MODE_PRIVATE);
        String IC = sp.getString("ic","0");
        final ArrayList<HashMap<String, String>> listItems = new ArrayList<HashMap<String, String>>();
        final ListView listview = root.findViewById(R.id.listview);

        final SimpleAdapter adapter = new SimpleAdapter(this.getActivity(), listItems, R.layout.teaching_subject_list,
                new String[]{"subject1", "subject2", "level1", "level2", "price1", "price2"},
                new int[]{R.id.subject1, R.id.subject2, R.id.level1, R.id.level2, R.id.price1, R.id.price2});


        FirebaseDatabase firebase;
        firebase = FirebaseDatabase.getInstance("https://tutor-agent-fe351.firebaseio.com/");
        DatabaseReference dataRef = firebase.getReference("Tutors").child(IC);
        dataRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Map<String,String> response = (Map)snapshot.getValue();
                name.setText(response.get("name"));
                String DoB = response.get("dateOfBirth");
                int ageInt = Calendar.getInstance().get(Calendar.YEAR) - Integer.parseInt(DoB.subSequence(0,4).toString());
                age.setText(String.valueOf(ageInt));
                gender.setText(response.get("gender"));
                location.setText(response.get("location"));
                if(snapshot.child("Subjects").exists()) {

                    Map<String, Map<String, String>> subjects = (HashMap) snapshot.child("Subjects").getValue();
                    int counter = 0;
                    HashMap<String, String> subject = new HashMap<>();
                    for (Map.Entry<String, Map<String, String>> entry : subjects.entrySet()) {
                        if (counter % 2 == 0) {
                            subject = new HashMap<String, String>();
                            subject.put("subject1", entry.getValue().get("name"));
                            subject.put("level1", entry.getValue().get("syllabus"));
                            subject.put("price1", entry.getValue().get("price"));
                        } else {
                            subject.put("subject2", entry.getValue().get("name"));
                            subject.put("level2", entry.getValue().get("syllabus"));
                            subject.put("price2", entry.getValue().get("price"));
                        }
                        counter++;
                        if (counter % 2 == 0) {
                            listItems.add(subject);
                        }
                    }

                    if (counter % 2 != 0) {

                        subject.put("subject2", "");
                        subject.put("level2", "");
                        subject.put("price2", "");
                        listItems.add(subject);
                    }
                    Log.i("listItems", listItems.toString());
                    listview.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });







        return root;
    }
}