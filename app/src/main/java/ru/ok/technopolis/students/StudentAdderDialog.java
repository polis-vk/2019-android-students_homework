package ru.ok.technopolis.students;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Toast;


public class StudentAdderDialog extends DialogFragment implements DialogInterface.OnShowListener {
    private AlertDialog dialog;
    public static final String STUDENT_TAG = "STUDENT_TAG";
    private EditText editTextFirstName;
    private EditText editTextLastName;
    private RadioGroup radioGroup;
    private Context context;
    private Student oldStudent;

    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        context = getContext();
        Bundle args = getArguments();
        if (args != null) {
            oldStudent = (Student) args.getSerializable(STUDENT_TAG);
        }
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_student_adder, null);
        initView(view);
        AlertDialog.Builder builder = new AlertDialog.Builder(context).setView(view).setTitle(R.string.student).setMessage(R.string.trydroid).setPositiveButton(R.string.ok, null).setNegativeButton(R.string.back, null);
        dialog = builder.create();
        dialog.setOnShowListener(this);
        return dialog;
    }

    private void initView(View view) {
        editTextFirstName = view.findViewById(R.id.dialog_student_adder__first_name);
        editTextLastName = view.findViewById(R.id.dialog_student_adder__last_name);
        ImageView imageView = view.findViewById(R.id.dialog_student_adder__avatar);
        radioGroup = view.findViewById(R.id.dialog_student_adder__radio_group);

        if (oldStudent != null) {
            editTextFirstName.setText(oldStudent.getFirstName());
            editTextLastName.setText(oldStudent.getSecondName());
            imageView.setImageResource(oldStudent.getPhoto());
            radioGroup.check(oldStudent.isMaleGender() ? R.id.dialog_student_adder__male : R.id.dialog_student_adder__female);
        }
    }


    @Override
    public void onShow(DialogInterface dialogInterface) {
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Editable textFirstName = editTextFirstName.getText();
                Editable textLastName = editTextLastName.getText();

                if (TextUtils.isEmpty(textFirstName) || TextUtils.isEmpty(textLastName) || radioGroup.getCheckedRadioButtonId() == -1) {
                    Toast.makeText(context, R.string.incorrect, Toast.LENGTH_SHORT).show();
                    return;
                }

                String name = textFirstName.toString();
                String lastName = textLastName.toString();
                boolean maleGender = radioGroup.getCheckedRadioButtonId() == R.id.dialog_student_adder__male;

                if (oldStudent == null) {
                    OnAdderStudentListener listener = (OnAdderStudentListener) context;
                    if (listener != null) {
                        listener.add(name, lastName, maleGender);
                    }
                } else {
                    OnUpdateStudentListener listener = (OnUpdateStudentListener) context;
                    if (listener != null) {
                        Student student = new Student(name, lastName, maleGender, oldStudent.getPhoto());
                        listener.update(oldStudent, student);
                    }
                }

                dialog.cancel();
            }
        });

        dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
            }
        });
    }

    interface OnAdderStudentListener {
        void add(String firstName, String lastName, boolean maleGender);
    }

    interface OnUpdateStudentListener {
        void update(Student oldStudent, Student newStudent);
    }
}
