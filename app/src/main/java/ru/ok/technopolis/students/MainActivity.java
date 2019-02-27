package ru.ok.technopolis.students;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;

public class MainActivity extends AppCompatActivity implements StudentsRecyclerAdapter.OnActionInRecyclerViewListener {

    private StudentsRecyclerAdapter studentsRecyclerAdapter;

    private EditText edtFirstName, edtSecondName;
    private RadioGroup radioGroup;

    //TODO поработать со стилем

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_main);

        setupRecyclerView();

        edtFirstName = findViewById(R.id.activity_main__first_name);
        edtSecondName = findViewById(R.id.activity_main__second_name);
        radioGroup = findViewById(R.id.activity_main__gender);

        Button btnSave = findViewById(R.id.activity_main__btn_save);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String firstName = edtFirstName.getText().toString(); //TODO проверка на пустоту данных
                String secondName = edtSecondName.getText().toString(); //TODO проверка на пустоту данных

                boolean gender;
                switch (radioGroup.getCheckedRadioButtonId()) {
                    case R.id.activity_main__gender_male:
                        gender = true;
                        break;
                    case R.id.activity_main__gender_female:
                        gender = false;
                        break;
                    default:
                        return; //TODO проверка на пустоту данных, сообщение ошибки добавить
                        //break;
                }

                Student student = new Student(firstName, secondName, gender, 0);
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
        if (item.getItemId() == R.id.menu__add){
            onClearFields();
            studentsRecyclerAdapter.setActionStudent(StudentsRecyclerAdapter.NO_ACTION_STUDENT);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClearFields() {
        edtFirstName.setText("");
        edtSecondName.setText("");
        radioGroup.clearCheck();
    }

    @Override
    public void onActionStudent(String fName, String sName, boolean gender, int photo) {
        edtFirstName.setText(fName);
        edtSecondName.setText(sName);
        if (gender){
            radioGroup.check(R.id.activity_main__gender_male);
        } else {
            radioGroup.check(R.id.activity_main__gender_female);
        }
    }
}
