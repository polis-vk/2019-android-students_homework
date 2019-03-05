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
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements StudentAdderDialog.OnAdderStudentListener, StudentAdderDialog.OnUpdateStudentListener {

    private static final String DIALOG_TAG = "DialogAdder";
    private String[] mansFirstNames;
    private String[] womenFirstNames;
    private String[] lastNames;
    private int[] manPhotos;
    private int[] womanPhotos;
    private Random random;
    private int manPhotoIndex, womanPhotoIndex;
    private ArrayList<Student> students;
    private StudentsAdapter adapter;

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
                dialog.show(getSupportFragmentManager(), DIALOG_TAG);
            }
        });

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
    }

    private void showDialog(final Student student) {
        ImageView view = new ImageView(this);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(300, 300);
        view.setScaleType(ImageView.ScaleType.CENTER_CROP);
        view.setLayoutParams(layoutParams);
        view.setImageResource(student.getPhoto());

        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

        builder.setView(view);
        builder.setTitle(student.getFirstName() + " " + student.getSecondName());
        builder.setMessage(student.isMaleGender() ? R.string.guy : R.string.girl);
        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });

        builder.setNegativeButton(R.string.edit, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                DialogFragment dialog = new StudentAdderDialog();
                Bundle bundle = new Bundle();
                bundle.putSerializable(StudentAdderDialog.STUDENT_TAG, student);
                dialog.setArguments(bundle);
                dialog.show(getSupportFragmentManager(), "DialogAdder");
                dialogInterface.cancel();
            }
        });

        builder.setNeutralButton(R.string.delete, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                students.remove(student);
                adapter.notifyDataSetChanged();
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
        return students;
    }


    Student nextStudent(boolean maleGender) {
        if (maleGender) {
            int nameIndex = random.nextInt(mansFirstNames.length);
            int lastNameIndex = random.nextInt(mansFirstNames.length);
            return new Student(mansFirstNames[nameIndex], lastNames[lastNameIndex], true, manPhotos[manPhotoIndex++ % 3]);
        }
        int nameIndex = random.nextInt(womenFirstNames.length);
        int lastNameIndex = random.nextInt(womenFirstNames.length);
        return new Student(womenFirstNames[nameIndex], lastNames[lastNameIndex] + "a", false, womanPhotos[womanPhotoIndex++ % 3]);
    }


    @Override
    public void add(String firstName, String lastName, boolean maleGender) {
        Student student = new Student(firstName, lastName, maleGender, maleGender ? manPhotos[manPhotoIndex++ % 3] : womanPhotos[womanPhotoIndex++ % 3]);
        students.add(student);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void update(Student oldStudent, Student newStudent) {
        int index = students.indexOf(oldStudent);
        students.remove(oldStudent);
        students.add(index, newStudent);
        adapter.notifyDataSetChanged();
    }
}
