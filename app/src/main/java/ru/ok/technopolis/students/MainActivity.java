package ru.ok.technopolis.students;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerView recyclerView = findViewById(R.id.activity_main__students);
        StudentAdapter studentAdapter = new StudentAdapter(generateStudentList());
        recyclerView.setAdapter(studentAdapter);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);

        ImageView imageView = findViewById(R.id.activity_main__student_avatar);

        imageView.setImageResource(generateRandomAvatar(false));
    }

    private List<Student> generateStudentList() {
        List<Student> students = new ArrayList<>();
        students.add(new Student("Елена",
                "Еленовна",
                false,
                R.drawable.female_1));
        students.add(new Student("Иван",
                "Иванов",
                true,
                R.drawable.male_1));
        students.add(new Student("Петр",
                "Петров",
                true,
                R.drawable.male_2));
        students.add(new Student("Анна",
                "Анновна",
                false,
                R.drawable.female_2));
        students.add(new Student("Дмитрий",
                "Дмитриев",
                true,
                R.drawable.male_2));
        students.add(new Student("Генадий",
                "Генадьев",
                true,
                R.drawable.male_3));
        students.add(new Student("Настя",
                "Настевна",
                false,
                R.drawable.female_3));
        students.add(new Student("Захар",
                "Захаров",
                true,
                R.drawable.male_1));
        students.add(new Student("Александр",
                "Александров",
                true,
                R.drawable.male_2));
        return students;
    }

    private int generateRandomAvatar(boolean gender) {
        Random random = new Random();
        int i = random.nextInt(3) + 1;
        int result;
        switch (i) {
            case 1:
                result = gender? R.drawable.male_1 : R.drawable.female_1;
                break;
            case 2:
                result = gender? R.drawable.male_2 : R.drawable.female_2;
                break;
            case 3:
                result = gender? R.drawable.male_2 : R.drawable.female_2;
                break;
            default:
                result = R.drawable.male_1;
                break;
        }
        return result;
    }

    public void onCheckboxClicked(View view) {

    }
}
