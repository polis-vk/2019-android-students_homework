package ru.ok.technopolis.students;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import java.util.Random;

public class StudentCreator {
    private final MainActivity view;
    private final EditText name;
    private final EditText surname;
    private final ImageView avatar;
    private final Button btnAddStudent;
    private final Button btnSave;
    private final Button btnDelete;
    private final RadioButton btnMale;
    private final RadioButton btnFemale;
    private Student currentStudent=null;
    Random random=new Random();

    public StudentCreator(MainActivity view) {
        this.view = view;
        name=view.findViewById(R.id.editText_name);
        surname=view.findViewById(R.id.editText_surname);
        avatar=view.findViewById(R.id.imageView_studentAvatar);
        btnAddStudent=view.findViewById(R.id.button_addStudent);
        btnSave=view.findViewById(R.id.button_saveStudent);
        btnDelete=view.findViewById(R.id.button_deleteStudent);
        btnMale=view.findViewById(R.id.radioButton_male);
        btnFemale=view.findViewById(R.id.radioButton_female);

    }

    void setAddListener(final Listener onClickAdd){
        btnAddStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Student student=createNewStudent();
                if (student==null){
                    Toast.makeText(view,R.string.errorOfAdd,Toast.LENGTH_LONG).show();
                }else{
                    onClickAdd.onClick(student);
                    avatar.setImageResource(student.getPhoto());
                    Toast.makeText(view,R.string.wasAdded,Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    void setDeleteListener(final Listener onClickDelete){
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentStudent==null){
                    Toast.makeText(view,R.string.errorOfDeleteNotSelected,Toast.LENGTH_LONG).show();
                }else{
                    onClickDelete.onClick(currentStudent);
                    name.setText("");
                    surname.setText("");
                    currentStudent=null;
                    ((RadioGroup) view.findViewById(R.id.radioGroup_gender)).clearCheck();
                    avatar.setImageResource(R.color.cardview_light_background);
                    Toast.makeText(view,R.string.wasDeleted,Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    void setSaveListener(Listener onClickSave){
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentStudent==null){
                    Toast.makeText(view,R.string.errorOfSaveNotSelected,Toast.LENGTH_LONG).show();
                    return;
                }
                if(name.getText().toString().length()==0||surname.getText().toString().length()==0){
                    Toast.makeText(view,R.string.errorOfSave,Toast.LENGTH_LONG).show();
                }else{
                    currentStudent.setFirstName(name.getText().toString());
                    currentStudent.setSecondName(surname.getText().toString());
                    if (btnMale.isChecked()){
                        currentStudent.setGender(Student.Gender.MALE);
                    }else{
                        currentStudent.setGender(Student.Gender.FEMALE);
                    }
                    onClickSave.onClick(currentStudent);
                    Toast.makeText(view,R.string.wasSaved,Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void displayStudent(Student student){
        currentStudent=student;
        name.setText(student.getFirstName());
        surname.setText(student.getSecondName());
        avatar.setImageResource(student.getPhoto());
        if (student.getGender()== Student.Gender.MALE){
            btnMale.toggle();
        }else{
            btnFemale.toggle();
        }
    }

    public Student createNewStudent(){
        String name = this.name.getText().toString();
        String surname=this.surname.getText().toString();
        Student.Gender gender=null;
        int avatar=0;
        if (this.btnMale.isChecked()){
            gender= Student.Gender.MALE;
            avatar=getRandomAvatar(R.drawable.male_1,R.drawable.male_2,R.drawable.male_3);
        }else if(this.btnFemale.isChecked()){
            gender= Student.Gender.FEMALE;
            avatar=getRandomAvatar(R.drawable.female_1,R.drawable.female_3);
        }
        if (name.length()==0||surname.length()==0||gender==null){
            currentStudent=null;
            return null;
        }
        currentStudent =new Student(name,surname,gender,avatar);
        return currentStudent;
    }

    private int getRandomAvatar(int... avatars) {
        return avatars[random.nextInt(avatars.length)];
    }

    interface Listener{
        void onClick(Student student);
    }
}