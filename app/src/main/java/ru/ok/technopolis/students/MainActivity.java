package ru.ok.technopolis.students;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Toast;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements StudentsRecyclerAdapter.OnActionInRecyclerViewListener {

    private StudentsRecyclerAdapter studentsRecyclerAdapter;

    private ImageView imgPhoto;
    private int currentPhotoId;
    private EditText edtFirstName, edtSecondName;
    private RadioGroup radioGroup;

    private final int[] imgMale = {R.drawable.male_1, R.drawable.male_2, R.drawable.male_3};
    private final int[] imgFemale = {R.drawable.female_1, R.drawable.female_2, R.drawable.female_3};


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupRecyclerView();

        imgPhoto = findViewById(R.id.activity_main__image);
        edtFirstName = findViewById(R.id.activity_main__first_name);
        edtSecondName = findViewById(R.id.activity_main__second_name);

        radioGroup = findViewById(R.id.activity_main__gender);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int i = new Random().nextInt(3);
                switch (radioGroup.getCheckedRadioButtonId()) {
                    case R.id.activity_main__gender_male:
                        imgPhoto.setImageDrawable(getResources().getDrawable(imgMale[i]));
                        currentPhotoId = imgMale[i];
                        break;
                    case R.id.activity_main__gender_female:
                        imgPhoto.setImageDrawable(getResources().getDrawable(imgFemale[i]));
                        currentPhotoId = imgFemale[i];
                        break;
                    default:
                        currentPhotoId = 0;
                        break;
                }
            }
        });

        Button btnSave = findViewById(R.id.activity_main__btn_save);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Editable tempFN = edtFirstName.getText();
                Editable tempSN = edtSecondName.getText();
                String firstName = null;
                String secondName = null;
                if (tempFN != null)
                    firstName = tempFN.toString();
                if (tempSN != null)
                    secondName = tempSN.toString();

                Boolean gender = null;
                switch (radioGroup.getCheckedRadioButtonId()) {
                    case R.id.activity_main__gender_male:
                        gender = true;
                        break;
                    case R.id.activity_main__gender_female:
                        gender = false;
                        break;
                }

                if (gender == null || firstName == null || secondName == null
                        || firstName.equals("") || secondName.equals("")){
                    Toast.makeText(MainActivity.this, "Заполните все поля.", Toast.LENGTH_SHORT).show();
                    return;
                }

                Student student = new Student(firstName, secondName, gender, currentPhotoId);
                studentsRecyclerAdapter.saveStudent(student);
            }
        });

        Button btnDelete = findViewById(R.id.activity_main__btn_delete);
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                studentsRecyclerAdapter.deleteStudent();
            }
        });

    }

    private void setupRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.activity_main__recycler);
        studentsRecyclerAdapter = new StudentsRecyclerAdapter(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(studentsRecyclerAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.top_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu__add) {
            onClearFields();
            studentsRecyclerAdapter.setActionStudent(StudentsRecyclerAdapter.NO_ACTION_STUDENT);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClearFields() {
        radioGroup.clearCheck();
        imgPhoto.setImageDrawable(null);
        edtFirstName.setText("");
        edtSecondName.setText("");
    }

    @Override
    public void onActionStudent(String fName, String sName, boolean gender, int photo) {
        edtFirstName.setText(fName);
        edtSecondName.setText(sName);
        if (gender) {
            radioGroup.check(R.id.activity_main__gender_male);
        } else {
            radioGroup.check(R.id.activity_main__gender_female);
        }
        imgPhoto.setImageDrawable(getResources().getDrawable(photo));
    }
}
