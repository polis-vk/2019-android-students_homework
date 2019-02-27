package ru.ok.technopolis.students;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

public class StudentView extends ConstraintLayout {

    private Student student;

    private TextView firstName;
    private TextView secondName;
    private ImageView photo;
    private CheckBox isMale;
    private Button save;
    private Button remove;

    public StudentView(Context context) {
        super(context);
        init(context);
    }

    public StudentView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public StudentView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.student_view, this, false);
        addView(view);

        photo = view.findViewById(R.id.student_view__photo);
        firstName = view.findViewById(R.id.student_view__first_name);
        secondName = view.findViewById(R.id.student_view__second_name);
        isMale = view.findViewById(R.id.student_view__is_male);

        save = view.findViewById(R.id.student_view__save);
        remove = view.findViewById(R.id.student_view__remove);
    }

    public void setOnRemove(final OnClickListener onRemove) {
        remove.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (student != null) {
                    photo.setImageResource(0);
                    firstName.setText("");
                    secondName.setText("");
                    isMale.setChecked(false);
                    onRemove.onClick(v);
                    StudentView.this.setVisibility(GONE);
                }
            }
        });
    }

    public void setOnSave(final OnClickListener onSave) {
        save.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (student != null) {
                    student.setFirstName(firstName.getText().toString());
                    student.setSecondName(secondName.getText().toString());
                    student.setMaleGender(isMale.isChecked());
                    onSave.onClick(v);
                }
            }
        });
    }

    public void setStudent(Student student) {
        this.student = student;
        photo.setImageResource(student.getPhoto());
        firstName.setText(student.getFirstName());
        secondName.setText(student.getSecondName());
        isMale.setChecked(student.isMaleGender());
        setVisibility(VISIBLE);

    }
}
