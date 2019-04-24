package ru.ok.technopolis.students.bottom_sheet_fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.NoSuchElementException;
import java.util.UUID;

import ru.ok.technopolis.students.R;
import ru.ok.technopolis.students.app.App;
import ru.ok.technopolis.students.dao.StudentDAO;
import ru.ok.technopolis.students.dao.StudentsPhotoDAO;
import ru.ok.technopolis.students.model.Student;

public class StudentBottomSheet extends BottomSheetDialogFragment implements View.OnClickListener {

    public static final String BOTTOM_SHEET_TAG = "BOTTOM_SHEET";

    private static final String STUDENT_TAG_ID = "STUDENT_TAG_ID";
    private BottomSheetListener bottomSheetListener;
    private EditText firstNameEditText;
    private EditText secondNameEditText;
    private ImageView studentPhoto;
    private CheckBox genderCheckbox;
    private Student student;
    private StudentDAO studentDAO;
    private int photo;
    private StudentsPhotoDAO studentsPhotoDAO;
    private boolean createStudent = false;

    public static BottomSheetDialogFragment newInstance (UUID studentId) {
        final BottomSheetDialogFragment result = new StudentBottomSheet();
        final Bundle args = new Bundle();
        args.putSerializable(STUDENT_TAG_ID, studentId);
        result.setArguments(args);
        return result;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        assert getArguments() != null;
        UUID argsStudentId = (UUID) getArguments().getSerializable(STUDENT_TAG_ID);
        if(argsStudentId != null) {
            student = studentDAO.getStudentById(argsStudentId);
            createStudent = false;
        } else {
            createStudent = true;
            student = new Student();
        }
    }

    private void setupExistingStudent() {
        photo = student.getPhoto();
        firstNameEditText.setText(student.getFirstName());
        secondNameEditText.setText(student.getSecondName());
        if(student.isMaleGender()) {
            genderCheckbox.setChecked(true);
        }
        studentPhoto.setImageResource(photo);
    }


    private void createStudent() throws NoSuchElementException {
        String firstName = firstNameEditText.getText().toString();
        String secondName = secondNameEditText.getText().toString();
        if(genderCheckbox.isChecked()) {
            photo = studentsPhotoDAO.getMalePhoto();
            student.setMaleGender(true);
        }
        else {
            photo = studentsPhotoDAO.getFemalePhoto();
        }
        student.setFirstName(firstName);
        student.setSecondName(secondName);
        student.setPhoto(photo);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof BottomSheetListener) {
            bottomSheetListener = (BottomSheetListener) context;
        }
        studentDAO = App.getInstance().getStudentDAO();
        studentsPhotoDAO = App.getInstance().getStudentsPhotoDAO();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.student_bottom_sheet, container, false);
        genderCheckbox = view.findViewById(R.id.checkBox__gender);
        studentPhoto = view.findViewById(R.id.student_bottom_sheet__photo);
        Button saveStudentButton = view.findViewById(R.id.student_bottom_sheet__save_button);
        Button deleteStudentButton = view.findViewById(R.id.student_bottom_sheet__delete_button);
        firstNameEditText = view.findViewById(R.id.set_text__first_name_student);
        secondNameEditText = view.findViewById(R.id.set_text__second_name_student);
        if(createStudent) {
            deleteStudentButton.setVisibility(View.GONE);
        } else {
            setupExistingStudent();
            genderCheckbox.setOnCheckedChangeListener(onCheckedChangeListener);
        }
        saveStudentButton.setOnClickListener(this);
        deleteStudentButton.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        if (firstNameEditText != null && secondNameEditText != null) {
            if (createStudent) {
                createStudent();
            }
            switch (v.getId()) {
                case R.id.student_bottom_sheet__delete_button:
                    studentDAO.delete(student);
                    break;
                case R.id.student_bottom_sheet__save_button:
                    if (firstNameEditText.getText().toString().equals("") || secondNameEditText.getText().toString().equals("")) {
                        Toast.makeText(getActivity(), R.string.bottom_sheet_fragment__toast_fill, Toast.LENGTH_LONG).show();
                        return;
                    }
                    if (createStudent) {
                        studentDAO.insert(student);
                    } else {
                        updateStudent();
                        studentDAO.update(student);
                    }
                    break;
            }
            bottomSheetListener.onButtonClicked();
            dismiss();
        }
    }

    private void updateStudent() {
        student.setSecondName(secondNameEditText.getText().toString());
        student.setFirstName(firstNameEditText.getText().toString());
        student.setPhoto(photo);
    }

    public interface BottomSheetListener {
        void onButtonClicked();
    }

    private CompoundButton.OnCheckedChangeListener onCheckedChangeListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if(isChecked) {
                photo = studentsPhotoDAO.getMalePhoto();
            } else {
                photo = studentsPhotoDAO.getFemalePhoto();
            }
            studentPhoto.setImageResource(photo);
        }
    };
}
