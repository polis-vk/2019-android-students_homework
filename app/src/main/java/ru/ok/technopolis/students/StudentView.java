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
import android.widget.Toast;

import java.util.Random;

public class StudentView extends ConstraintLayout {

    private static final Random rand = new Random();
    private static final int[] malePhotos = {R.drawable.male_1, R.drawable.male_2, R.drawable.male_3};
    private static final int[] femalePhotos = {R.drawable.female_1, R.drawable.female_2, R.drawable.female_3};

    private Student student;

    private TextView firstNameTv;
    private TextView secondNameTv;
    private ImageView photoIv;
    private CheckBox isMaleCb;

    private OnClickListener onSave;
    private OnClickListener onCreate;
    private OnClickListener onRemove;
    private OnClickListener onHide;

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
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.student_view, this, true);

        photoIv = view.findViewById(R.id.student_view__photo);
        firstNameTv = view.findViewById(R.id.student_view__first_name);
        secondNameTv = view.findViewById(R.id.student_view__second_name);
        isMaleCb = view.findViewById(R.id.student_view__is_male);

        Button saveBtn = view.findViewById(R.id.student_view__save);
        saveBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                CharSequence firstName = firstNameTv.getText();
                CharSequence secondName = secondNameTv.getText();
                if (firstName == null || firstName.length() == 0 || secondName == null || secondName.length() == 0) {
                    String text = getContext().getString(R.string.error_insert_first_name_and_second_name);
                    Toast.makeText(getContext(), text, Toast.LENGTH_LONG).show();
                    return;
                }
                if (student != null) {
                    student.setFirstName(firstName.toString());
                    student.setSecondName(secondName.toString());
                    student.setMaleGender(isMaleCb.isChecked());
                    if (onSave != null) {
                        onSave.onClick(v);
                    }
                } else {
                    boolean isMale = isMaleCb.isChecked();
                    int photo = randomPhoto(isMale);
                    photoIv.setImageResource(photo);
                    student = new Student(firstName.toString(), secondName.toString(), isMale, photo);
                    v.setTag(student);
                    if (onCreate != null) {
                        onCreate.onClick(v);
                    }
                }
            }
        });
        Button hideBtn = view.findViewById(R.id.student_view__hide);
        hideBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onHide != null) {
                    onHide.onClick(v);
                }
                StudentView.this.setVisibility(GONE);
            }
        });
        Button removeBtn = view.findViewById(R.id.student_view__remove);
        removeBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (student != null) {
                    if (onRemove != null) {
                        onRemove.onClick(v);
                    }
                    StudentView.this.setVisibility(GONE);
                    clearFields();
                }
            }
        });
    }

    public void setOnRemove(OnClickListener onRemove) {
        this.onRemove = onRemove;
    }

    public void setOnSave(OnClickListener onSave) {
        this.onSave = onSave;
    }

    public void setOnCreate(OnClickListener onCreate) {
        this.onCreate = onCreate;
    }

    public void setOnHide(OnClickListener onHide) {
        this.onHide = onHide;
    }

    public void setStudent(Student student) {
        this.student = student;
        if (student != null) {
            photoIv.setImageResource(student.getPhoto());
            firstNameTv.setText(student.getFirstName());
            secondNameTv.setText(student.getSecondName());
            isMaleCb.setChecked(student.isMaleGender());
        } else {
            clearFields();
        }
        setVisibility(VISIBLE);
    }

    private void clearFields() {
        photoIv.setImageResource(R.color.colorWhite);
        firstNameTv.setText("");
        secondNameTv.setText("");
        isMaleCb.setChecked(false);
    }

    private static int randomPhoto(boolean isMale) {
        return isMale
                ? malePhotos[rand.nextInt(malePhotos.length)]
                : femalePhotos[rand.nextInt(femalePhotos.length)];
    }
}
