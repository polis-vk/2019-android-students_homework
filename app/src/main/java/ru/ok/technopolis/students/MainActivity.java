package ru.ok.technopolis.students;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private final Random random = new Random();
    private List<Student> studentList;
    private StudentAdapter studentAdapter;
    private Student selectedStudent;

    private int[] malePhotos = {
            R.drawable.male_1,
            R.drawable.male_2,
            R.drawable.male_3
    };
    private int[] femalePhotos = {
            R.drawable.female_1,
            R.drawable.female_2,
            R.drawable.female_3
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        studentList = generateStudentList();
        setContentView(R.layout.activity_main);
        setupRecyclerView();
    }

    private void setupRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.main__student_recycler);
        studentAdapter = new StudentAdapter(studentList);
        recyclerView.setAdapter(studentAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
    }

    private int getRandomPhoto(Gender gender) {
        switch (gender) {
            case MALE: {
                int i = random.nextInt(malePhotos.length);
//                return malePhotos[i];
                return R.drawable.ic_launcher_background;
            }
            case FEMALE: {
                int i = random.nextInt(femalePhotos.length);
//                return femalePhotos[i];
                return R.drawable.ic_launcher_background;
            }
            default: {
                return R.drawable.ic_launcher_background;
            }
        }
    }

    private void addStudent() {
        studentList.add(new Student("<Новый ", "студент>", Gender.MALE, getRandomPhoto(Gender.MALE)));
        studentAdapter.notifyItemInserted(studentList.size() - 1);
    }

    private List<Student> generateStudentList() {
        List<Student> studentList = new ArrayList<>();
        studentList.add(new Student("Иван", "Иванов", Gender.MALE, getRandomPhoto(Gender.MALE)));
        studentList.add(new Student("Анастасия", "Медведева", Gender.FEMALE, getRandomPhoto(Gender.FEMALE)));
        studentList.add(new Student("Петр", "Петров", Gender.MALE, getRandomPhoto(Gender.MALE)));
        studentList.add(new Student("Екатерина", "Зайцева", Gender.FEMALE, getRandomPhoto(Gender.FEMALE)));
        studentList.add(new Student("Антон", "Котов", Gender.MALE, getRandomPhoto(Gender.MALE)));
        studentList.add(new Student("Анна", "Фёдорова", Gender.FEMALE, getRandomPhoto(Gender.FEMALE)));
        studentList.add(new Student("Евгений", "Беляев", Gender.MALE, getRandomPhoto(Gender.MALE)));
        studentList.add(new Student("Кристина", "Ветрова", Gender.FEMALE, getRandomPhoto(Gender.FEMALE)));
        studentList.add(new Student("Андрей", "Андреев", Gender.MALE, getRandomPhoto(Gender.MALE)));
        return studentList;
    }
}
