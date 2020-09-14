package com.example.stutor.ui.student;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import java.util.HashMap;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;

public class StudentFragment extends Fragment {

    private StudentViewModel studentViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        studentViewModel =
                ViewModelProviders.of(this).get(StudentViewModel.class);
        final View root = inflater.inflate(R.layout.fragment_student, container, false);
        final ListView listview = root.findViewById(R.id.listview);
        final ArrayList<HashMap<String, String>> listItems = new ArrayList<HashMap<String, String>>();
        final SimpleAdapter adapter = new SimpleAdapter(this.getActivity(), listItems, R.layout.students_list,
                new String[]{"student"},
                new int[]{R.id.student_name});

        SharedPreferences sp = this.getActivity().getSharedPreferences("login", MODE_PRIVATE);
        final String IC = sp.getString("ic","0");

        FirebaseDatabase firebase;
        firebase = FirebaseDatabase.getInstance("https://tutor-agent-fe351.firebaseio.com/");
        DatabaseReference dataRef = firebase.getReference("Students");
        dataRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listItems.clear();
                Map<String, Map<String, Map<String, String>>> response = (Map)snapshot.getValue();
                HashMap<String, String> student = new HashMap<>();
                for (Map.Entry<String, Map<String, Map<String, String>>> entry : response.entrySet()) {
                    student = new HashMap<>();
                    if(entry.getValue().get("tutors")!=null){
                        if(entry.getValue().get("tutors").get(IC)!=null) {
                            student.put("student", entry.getKey());
                            listItems.add(student);
                        }
                    }
                }

                listview.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return root;
    }
}