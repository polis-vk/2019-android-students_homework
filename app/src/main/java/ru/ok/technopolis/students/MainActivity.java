package ru.ok.technopolis.students;

import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements OnClickListener{
    private List<Student> students;
    private StudentsAdapter studentsAdapter;
    public Button btnAddStudent;
    public Button btnDelStudent;
    public Button btnSaveStudent;
    private final Random random = new Random();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        students=generateStudenList();
        setContentView(R.layout.activity_main);
        setupRecyclerView();

        btnAddStudent =  findViewById(R.id.button6);
        btnDelStudent =  findViewById(R.id.button4);
        btnSaveStudent = findViewById(R.id.button5);

        btnAddStudent.setOnClickListener(this);
        btnDelStudent.setOnClickListener(this);
        btnSaveStudent.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button6:
                onAddClick();
                setupRecyclerView();
                break;
            case R.id.button4:
                onDellClick();
                setupRecyclerView();
                break;
            case R.id.button5:
                onSaveStudent();
                setupRecyclerView();
                break;
            default:
                onAddClick();
                setupRecyclerView();
                break;
        }
    }

    private void setupRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.activity_main__rv_list);
        studentsAdapter = new StudentsAdapter(students, this::onStudentClick);
        recyclerView.setAdapter(studentsAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
    }

    private void onStudentClick(Student student, String id) {
        TextInputEditText firstname = findViewById(R.id.texInputEdit4);
        TextInputEditText secondname = findViewById(R.id.texInputEdit5);
        ImageView posterImageView = findViewById(R.id.imageView4);
        CheckBox male =findViewById(R.id.checkBox2);
        firstname.setText(student.getFirstName());
        secondname.setText(student.getSecondName());
        posterImageView.setImageResource(student.getPhoto());
        male.setChecked(student.isMaleGender());
        Button dellId = findViewById(R.id.button4);
        dellId.setContentDescription(id);
    }

    private void onDellClick() {
        String str= (String) btnDelStudent.getContentDescription();
        TextInputEditText firstname = findViewById(R.id.texInputEdit4);
        TextInputEditText secondname = findViewById(R.id.texInputEdit5);
        ImageView posterImageView = findViewById(R.id.imageView4);
        CheckBox male =findViewById(R.id.checkBox2);
        firstname.setText("");
        secondname.setText("");
        posterImageView.setImageResource(R.mipmap.ic_launcher);
        male.setChecked(false);
        btnDelStudent.setContentDescription("1000");
        int id = Integer.parseInt(str);

        if (students.get(id).getSecondName().length()>0) students.remove(id);
    }

    private  void onAddClick(){
        TextInputEditText firstname = findViewById(R.id.texInputEdit4);
        TextInputEditText secondname = findViewById(R.id.texInputEdit5);
        ImageView posterImageView = findViewById(R.id.imageView4);
        CheckBox male =findViewById(R.id.checkBox2);
        firstname.setText("");
        secondname.setText("");
        posterImageView.setImageResource(R.mipmap.ic_launcher);
        male.setChecked(false);
        btnDelStudent.setContentDescription("1000");
    }

    private void onSaveStudent() {
        int id = Integer.parseInt((String) btnDelStudent.getContentDescription());

        TextInputEditText firstname = findViewById(R.id.texInputEdit4);
        TextInputEditText secondname = findViewById(R.id.texInputEdit5);
        ImageView posterImageView = findViewById(R.id.imageView4);
        CheckBox male = findViewById(R.id.checkBox2);
        int imgSrc= genImgSrc(male.isChecked());

        if (id == 1000) {
            if (firstname.getText().length()>1 && secondname.getText().length() > 1)
                students.add(new Student(firstname.getText().toString(), secondname.getText().toString(), male.isChecked(), imgSrc));
        } else {
            Student student;
            student = students.get(id);
            student.setFirstName(firstname.getText().toString());
            student.setSecondName(secondname.getText().toString());
            student.setMaleGender(male.isChecked());
            student.setPhoto(imgSrc);
        }
        firstname.setText("");
        secondname.setText("");
        posterImageView.setImageResource(R.mipmap.ic_launcher);
        male.setChecked(false);
        btnDelStudent.setContentDescription("1000");
    }

    private int genImgSrc(boolean male){
        int imgSrc= R.mipmap.ic_launcher;
        int i = (random.nextInt(3))+1;
        if (male) {
            switch (i) {
                case 1:
                    imgSrc = R.drawable.male_1;
                    break;
                case 2:
                    imgSrc = R.drawable.male_2;
                    break;
                case 3:
                    imgSrc = R.drawable.male_3;
                    break;
                default:
                    imgSrc = R.mipmap.ic_launcher;
                    break;
            }
        } else {
            switch (i) {
                case 1:
                    imgSrc = R.drawable.female_1;
                    break;
                case 2:
                    imgSrc = R.drawable.female_2;
                    break;
                case 3:
                    imgSrc = R.drawable.female_3;
                    break;
                default:
                    imgSrc = R.mipmap.ic_launcher;
                    break;
            }
        }
        return imgSrc;
    }

    private List<Student> generateStudenList() {
        List<Student> student = new ArrayList<>();
        student.add(new Student("Иванов","Иван",true , R.drawable.male_1));
        student.add(new Student("Петров","Петр",true , R.drawable.male_2));
        student.add(new Student("Сидорова","Мария",false , R.drawable.female_1));
        return student;
    }

}

