package ru.ok.technopolis.students;

import android.content.Intent;
import android.nfc.Tag;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    static List<Student> students;
    StudentAdapter studentAdapter;
    final String TAG = "lifecycle";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG,"MainActivity Create");
        setContentView(R.layout.activity_main);
        setupRecycleView();

        findViewById(R.id.activity_main__add_button).setOnClickListener(this);
    }

    private void setupRecycleView() {
        RecyclerView recyclerView = findViewById(R.id.activity_main__students);
        studentAdapter = new StudentAdapter(generateStudentList(), new StudentAdapter.Listener() {
            @Override
            public void onStudentClick(Student student) {
                Intent myintent=new Intent(MainActivity.this, CreateStudentActivity.class).putExtra("student", student);
                startActivity(myintent);
            }
        });
        recyclerView.setAdapter(studentAdapter);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
    }

    private List<Student> generateStudentList() {
        students = new ArrayList<>();
        students.add(new Student("Елена",
                "Петрова",
                false,
                R.drawable.female_1));
        students.add(new Student("Иван",
                "Иванов",
                true,
                R.drawable.male_1));
        students.add(new Student("Петр",
                "Проворов",
                true,
                R.drawable.male_2));
        students.add(new Student("Анна",
                "Трусцова",
                false,
                R.drawable.female_2));
        students.add(new Student("Дмитрий",
                "Смотров",
                true,
                R.drawable.male_2));
        students.add(new Student("Генадий",
                "Букин",
                true,
                R.drawable.male_3));
        students.add(new Student("Настя",
                "Талькова",
                false,
                R.drawable.female_3));
        students.add(new Student("Захар",
                "Чернов",
                true,
                R.drawable.male_1));
        students.add(new Student("Александр",
                "Шац",
                true,
                R.drawable.male_2));
        return students;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.activity_main__add_button) {
            Intent intent = new Intent(MainActivity.this, CreateStudentActivity.class);
            startActivity(intent);
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG,"MainActivity Restart");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "MainActivity Start");
    }

    @Override
    protected void onResume() {
        super.onResume();
        studentAdapter.notifyDataSetChanged();
        Log.d(TAG,"MainActivity Resume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "MainActivity Pause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "MainActivity Stop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "MainActivity Destroy");
    }
}
