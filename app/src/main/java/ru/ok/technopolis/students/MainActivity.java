package ru.ok.technopolis.students;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements StudentAdderDialog.OnAdderStudentListener {

    private String[] mansFirstNames;
    private String[] womenFirstNames;
    private String[] lastNames;
    private int[] manPhotos;
    private int[] womanPhotos;
    Random random;
    int manPhotoIndex, womanPhotoIndex;
    ArrayList<Student> students;
    StudentsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        random = new Random();
        manPhotoIndex = womanPhotoIndex = 0;
        mansFirstNames = getResources().getStringArray(R.array.mans_first_names);
        womenFirstNames = getResources().getStringArray(R.array.women_first_names);
        lastNames = getResources().getStringArray(R.array.last_names);
        manPhotos = new int[]{R.drawable.male_01, R.drawable.male_02, R.drawable.male_03};
        womanPhotos = new int[]{R.drawable.female_01, R.drawable.female_02, R.drawable.female_03};


        RecyclerView recyclerView = findViewById(R.id.activity_main__recycler_view);
        FloatingActionButton fabAdd = findViewById(R.id.activity_main__fab_add);

        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment dialog = new StudentAdderDialog();
                dialog.show(getSupportFragmentManager(), "DialogAdder");
            }
        });

        Log.d("MainActivity", "START");
        students = createStudents();
        adapter = new StudentsAdapter(students, new StudentsAdapter.OnStudentClickListener() {
            @Override
            public void onStudentClick(Student student) {
                showDialog(student);
            }
        });
        recyclerView.setAdapter(adapter);
        LinearLayoutManager mLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(mLayoutManager);
        Log.d("MainActivity", "END");
    }

    private void showDialog(Student student) {
        ImageView view  = (ImageView) LayoutInflater.from(MainActivity.this).inflate(R.layout.image_student, null);
        if(student.getBitmap() == null){

            view.setImageResource(student.getPhoto());
        } else {
            view.setImageBitmap(student.getBitmap());
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

        builder.setView(view);
        builder.setTitle(student.getFirstName() + " " + student.getSecondName());
        builder.setMessage(student.isMaleGender() ? "Парень" : "Девушка");
        builder.setPositiveButton("Ок", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });

        builder.create().show();
    }


    ArrayList<Student> createStudents() {
        ArrayList<Student> students = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            students.add(nextStudent(i % 2 == 0));
        }
        Log.d("MainActivity", "STUDENTS = " + Arrays.toString(students.toArray()));
        return students;
    }


    Student nextStudent(boolean maleGender) {
        manPhotoIndex %= 3;
        womanPhotoIndex %= 3;
        if (maleGender) {
            int nameIndex = random.nextInt(mansFirstNames.length);
            int lastNameIndex = random.nextInt(mansFirstNames.length);
            return new Student(mansFirstNames[nameIndex], lastNames[lastNameIndex], true, manPhotos[manPhotoIndex++]);
        }
        int nameIndex = random.nextInt(womenFirstNames.length);
        int lastNameIndex = random.nextInt(womenFirstNames.length);
        return new Student(womenFirstNames[nameIndex], lastNames[lastNameIndex] + "a", false, womanPhotos[womanPhotoIndex++]);
    }


    @Override
    public void add(String firstname, String lastname, boolean maleGender, Bitmap bitmap) {
        manPhotoIndex %= 3;
        womanPhotoIndex %= 3;
        Student student = new Student(firstname, lastname, maleGender, maleGender ? manPhotos[manPhotoIndex++] : womanPhotos[womanPhotoIndex++]);
        if (bitmap != null) {
            student.setBitmap(bitmap);
        }
        students.add(student);
        adapter.notifyDataSetChanged();
    }
}
