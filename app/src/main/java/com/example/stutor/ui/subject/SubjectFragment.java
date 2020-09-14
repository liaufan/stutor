package com.example.stutor.ui.subject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.stutor.MainActivity;
import com.example.stutor.R;
import com.example.stutor.ui.dashboard.DashboardFragment;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;

public class SubjectFragment extends Fragment {

    private SubjectViewModel subjectViewModel;
    private Spinner subject_spinner;
    private CheckBox primaryCheckBox, secondaryCheckBox, preUniversityCheckBox, higherEducationCheckBox, SKCheckBox, SJKCCheckBox, SJKTCheckBox, PT3CheckBox, SPMCheckBox, JUECCheckBox, SUECCheckBox, OLEVELCheckBox, ALEVELCheckBox, FoundationCheckBox, DiplomaCheckBox, DegreeCheckBox;
    private Button addButton;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        subjectViewModel =
                ViewModelProviders.of(this).get(SubjectViewModel.class);
        View root = inflater.inflate(R.layout.fragment_subject, container, false);

        SharedPreferences sp = this.getActivity().getSharedPreferences("login", MODE_PRIVATE);
        final String IC = sp.getString("ic","0");

        subject_spinner = root.findViewById(R.id.subject_spinner);
        primaryCheckBox = root.findViewById(R.id.primaryCheckBox);
        secondaryCheckBox = root.findViewById(R.id.secondaryCheckBox);
        preUniversityCheckBox = root.findViewById(R.id.preUniversityCheckBox);
        higherEducationCheckBox = root.findViewById(R.id.higherEducationCheckBox);
        SKCheckBox = root.findViewById(R.id.SKCheckBox);
        SJKCCheckBox = root.findViewById(R.id.SJKCCheckBox);
        SJKTCheckBox = root.findViewById(R.id.SJKTCheckBox);
        PT3CheckBox = root.findViewById(R.id.PT3CheckBox);
        SPMCheckBox = root.findViewById(R.id.SPMCheckBox);
        JUECCheckBox = root.findViewById(R.id.JUECChechBox);
        SUECCheckBox = root.findViewById(R.id.SUECCheckBox);
        OLEVELCheckBox = root.findViewById(R.id.OLEVELCheckBox);
        ALEVELCheckBox = root.findViewById(R.id.ALEVELCheckBox);
        FoundationCheckBox = root.findViewById(R.id.FoundationCheckBox);
        DiplomaCheckBox = root.findViewById(R.id.DiplomaCheckBox);
        DegreeCheckBox = root.findViewById(R.id.DegreeCheckBox);
        addButton = root.findViewById(R.id.addButton);

        primaryCheckBox.setVisibility(View.GONE);
        secondaryCheckBox.setVisibility(View.GONE);
        preUniversityCheckBox.setVisibility(View.GONE);
        higherEducationCheckBox.setVisibility(View.GONE);
        SKCheckBox.setVisibility(View.GONE);
        SJKCCheckBox.setVisibility(View.GONE);
        SJKTCheckBox.setVisibility(View.GONE);
        PT3CheckBox.setVisibility(View.GONE);
        SPMCheckBox.setVisibility(View.GONE);
        JUECCheckBox.setVisibility(View.GONE);
        SUECCheckBox.setVisibility(View.GONE);
        OLEVELCheckBox.setVisibility(View.GONE);
        ALEVELCheckBox.setVisibility(View.GONE);
        FoundationCheckBox.setVisibility(View.GONE);
        DiplomaCheckBox.setVisibility(View.GONE);
        DegreeCheckBox.setVisibility(View.GONE);

        final ArrayList<String> items = new ArrayList();
        items.add("-");
        
        final FirebaseDatabase firebase;
        firebase = FirebaseDatabase.getInstance("https://tutor-agent-fe351.firebaseio.com/");
        DatabaseReference dataRef = firebase.getReference("Subjects");
        dataRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                items.clear();
                items.add("-");
                Map<String, Map<String, String>> subjects = (HashMap) snapshot.getValue();
                for (Map.Entry<String, Map<String, String>> entry : subjects.entrySet()) {
                    items.add(entry.getValue().get("subject"));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, items);
        subject_spinner.setAdapter(adapter);

        subject_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(!subject_spinner.getSelectedItem().toString().equals("-")){
                    DatabaseReference syllabusRef = firebase.getReference("Subjects").child(subject_spinner.getSelectedItem().toString());
                    syllabusRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            Map<String, Map<String, Boolean>> response = (HashMap)snapshot.getValue();
                            if(response.get("level").get("primary")){
                                primaryCheckBox.setVisibility(View.VISIBLE);
                            } else {
                                primaryCheckBox.setVisibility(View.GONE);
                                primaryCheckBox.setSelected(false);
                            }
                            if(response.get("level").get("secondary")){
                                secondaryCheckBox.setVisibility(View.VISIBLE);
                            } else {
                                secondaryCheckBox.setVisibility(View.GONE);
                                secondaryCheckBox.setSelected(false);
                            }
                            if(response.get("level").get("preUniversity")){
                                preUniversityCheckBox.setVisibility(View.VISIBLE);
                            } else {
                                preUniversityCheckBox.setVisibility(View.GONE);
                                preUniversityCheckBox.setSelected(false);
                            }
                            if(response.get("level").get("higherEducation")){
                                higherEducationCheckBox.setVisibility(View.VISIBLE);
                            } else {
                                higherEducationCheckBox.setVisibility(View.GONE);
                                higherEducationCheckBox.setSelected(false);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                } else {
                    primaryCheckBox.setVisibility(View.GONE);
                    secondaryCheckBox.setVisibility(View.GONE);
                    preUniversityCheckBox.setVisibility(View.GONE);
                    higherEducationCheckBox.setVisibility(View.GONE);
                    primaryCheckBox.setSelected(false);
                    secondaryCheckBox.setSelected(false);
                    preUniversityCheckBox.setSelected(false);
                    higherEducationCheckBox.setSelected(false);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        primaryCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    DatabaseReference primaryRef = firebase.getReference("Subjects").child(subject_spinner.getSelectedItem().toString()).child("syllabus").child("primary");
                    primaryRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            Map<String, Boolean> response = (Map)snapshot.getValue();
                            if(response.get("SK"))
                                SKCheckBox.setVisibility(View.VISIBLE);
                            if(response.get("SJKC"))
                                SJKCCheckBox.setVisibility(View.VISIBLE);
                            if(response.get("SJKT"))
                                SJKTCheckBox.setVisibility(View.VISIBLE);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                } else {
                    SKCheckBox.setVisibility(View.GONE);
                    SJKCCheckBox.setVisibility(View.GONE);
                    SJKTCheckBox.setVisibility(View.GONE);
                    SKCheckBox.setSelected(false);
                    SJKCCheckBox.setSelected(false);
                    SJKTCheckBox.setSelected(false);
                }
            }
        });

        secondaryCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    DatabaseReference secondaryRef = firebase.getReference("Subjects").child(subject_spinner.getSelectedItem().toString()).child("syllabus").child("secondary");
                    secondaryRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            Map<String, Boolean> response = (Map)snapshot.getValue();
                            if(response.get("PT3"))
                                PT3CheckBox.setVisibility(View.VISIBLE);
                            if(response.get("SPM"))
                                SPMCheckBox.setVisibility(View.VISIBLE);
                            if(response.get("JUEC"))
                                JUECCheckBox.setVisibility(View.VISIBLE);
                            if(response.get("SUEC"))
                                SUECCheckBox.setVisibility(View.VISIBLE);
                            if(response.get("OLEVEL"))
                                OLEVELCheckBox.setVisibility(View.VISIBLE);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                } else {
                    PT3CheckBox.setVisibility(View.GONE);
                    SPMCheckBox.setVisibility(View.GONE);
                    JUECCheckBox.setVisibility(View.GONE);
                    SUECCheckBox.setVisibility(View.GONE);
                    OLEVELCheckBox.setVisibility(View.GONE);
                    PT3CheckBox.setSelected(false);
                    SPMCheckBox.setSelected(false);
                    JUECCheckBox.setSelected(false);
                    SUECCheckBox.setSelected(false);
                    OLEVELCheckBox.setSelected(false);
                }
            }
        });

        preUniversityCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    DatabaseReference preURef = firebase.getReference("Subjects").child(subject_spinner.getSelectedItem().toString()).child("syllabus").child("preUniversity");
                    preURef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            Map<String, Boolean> response = (Map)snapshot.getValue();
                            if(response.get("ALEVEL"))
                                ALEVELCheckBox.setVisibility(View.VISIBLE);
                            if(response.get("Foundation"))
                                FoundationCheckBox.setVisibility(View.VISIBLE);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                } else {
                    ALEVELCheckBox.setVisibility(View.GONE);
                    FoundationCheckBox.setVisibility(View.GONE);
                    ALEVELCheckBox.setSelected(false);
                    FoundationCheckBox.setSelected(false);
                }
            }
        });

        higherEducationCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    DatabaseReference higherRef = firebase.getReference("Subjects").child(subject_spinner.getSelectedItem().toString()).child("syllabus").child("higherEducation");
                    higherRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            Map<String, Boolean> response = (Map)snapshot.getValue();
                            if(response.get("Diploma"))
                                DiplomaCheckBox.setVisibility(View.VISIBLE);
                            if(response.get("Degree"))
                                DegreeCheckBox.setVisibility(View.VISIBLE);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                } else {
                    DiplomaCheckBox.setVisibility(View.GONE);
                    DegreeCheckBox.setVisibility(View.GONE);
                    DiplomaCheckBox.setSelected(false);
                    DegreeCheckBox.setSelected(false);
                }
            }
        });

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!subject_spinner.getSelectedItem().equals("-")){
                    DatabaseReference addRef = firebase.getReference("Tutors").child(IC).child("Subjects").child(subject_spinner.getSelectedItem().toString());
                    addRef.child("name").setValue(subject_spinner.getSelectedItem().toString());
                    String level = "";
                    String syllabus = "";
                    if(primaryCheckBox.isChecked()){
                        level += "primary";
                        if(SKCheckBox.isChecked()) {
                            syllabus += "SK";
                        }
                        if(SJKCCheckBox.isChecked()){
                            if(!syllabus.equals(""))
                                syllabus+=", ";
                            syllabus+="SJKC";
                        }
                        if(SJKTCheckBox.isChecked()){
                            if(!syllabus.equals(""))
                                syllabus+=", ";
                            syllabus+="SJKT";
                        }
                    }
                    if(secondaryCheckBox.isChecked()){
                        if(!level.equals(""))
                            level+=", ";
                        level += "secondary";
                        if(PT3CheckBox.isChecked()) {
                            if(!syllabus.equals(""))
                                syllabus+=", ";
                            syllabus += "PT3";
                        }
                        if(SPMCheckBox.isChecked()){
                            if(!syllabus.equals(""))
                                syllabus+=", ";
                            syllabus+="SPM";
                        }
                        if(JUECCheckBox.isChecked()){
                            if(!syllabus.equals(""))
                                syllabus+=", ";
                            syllabus+="JUEC";
                        }
                        if(SUECCheckBox.isChecked()){
                            if(!syllabus.equals(""))
                                syllabus+=", ";
                            syllabus+="SUEC";
                        }if(OLEVELCheckBox.isChecked()){
                            if(!syllabus.equals(""))
                                syllabus+=", ";
                            syllabus+="OLEVEL";
                        }

                    }
                    if(preUniversityCheckBox.isChecked()){
                        if(!level.equals(""))
                            level+=", ";
                        level += "preUniversity";
                        if(ALEVELCheckBox.isChecked()) {
                            if(!syllabus.equals(""))
                                syllabus+=", ";
                            syllabus += "ALEVEL";
                        }
                        if(FoundationCheckBox.isChecked()){
                            if(!syllabus.equals(""))
                                syllabus+=", ";
                            syllabus+="Foundation";
                        }
                    }
                    if(higherEducationCheckBox.isChecked()){
                        if(!level.equals(""))
                            level+=", ";
                        level += "higherEducation";
                        if(DiplomaCheckBox.isChecked()) {
                            if(!syllabus.equals(""))
                                syllabus+=", ";
                            syllabus += "Diploma";
                        }
                        if(DegreeCheckBox.isChecked()){
                            if(!syllabus.equals(""))
                                syllabus+=", ";
                            syllabus+="Degree";
                        }
                    }
                    addRef.child("level").setValue(level);
                    addRef.child("syllabus").setValue(syllabus);
                    addRef.child("price").setValue("RM50/hour");

                    startActivity(new Intent(getActivity(), MainActivity.class));
                }

            }
        });
        return root;
    }
}