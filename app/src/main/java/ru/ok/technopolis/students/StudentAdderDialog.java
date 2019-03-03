package ru.ok.technopolis.students;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.io.IOException;


import static android.app.Activity.RESULT_OK;


public class StudentAdderDialog extends DialogFragment implements DialogInterface.OnShowListener {
    private Bitmap bitmap;
    private AlertDialog dialog;
    private static final int GALLERY_REQUEST = 1;
    public static final String STUDENT_TAG = "STUDENT_TAG";
    private EditText fnEdit, lnEdit;
    private ImageView imageView;
    private RadioGroup radioGroup;
    private Context context;
    private Student oldStudent;

    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        context = getContext();
        Bundle args = getArguments();
        if (args != null) {
            oldStudent = (Student) args.getSerializable(STUDENT_TAG);
            if (oldStudent != null) {
                bitmap = oldStudent.getBitmap();
            }
        }
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_student_adder, null);
        initView(view);
        AlertDialog.Builder adb = new AlertDialog.Builder(context)
                .setView(view).setTitle(R.string.student)
                .setMessage(R.string.trydroid)
                .setPositiveButton(R.string.ok, null)
                .setNegativeButton(R.string.back, null);
        dialog = adb.create();
        dialog.setOnShowListener(this);

        return dialog;
    }


    private void initView(View view) {
        fnEdit = view.findViewById(R.id.dialog_student_adder__first_name);
        lnEdit = view.findViewById(R.id.dialog_student_adder__last_name);
        imageView = view.findViewById(R.id.dialog_student_adder__avatar);
        radioGroup = view.findViewById(R.id.dialog_student_adder__radio_group);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, GALLERY_REQUEST);
            }
        });

        if (oldStudent != null) {
            fnEdit.setText(oldStudent.getFirstName());
            lnEdit.setText(oldStudent.getSecondName());
            if (bitmap == null) {
                imageView.setImageResource(oldStudent.getPhoto());
            } else {
                imageView.setImageBitmap(bitmap);
            }
            radioGroup.clearCheck();
            int index = oldStudent.isMaleGender() ? 0 : 1;
            ((RadioButton)radioGroup.getChildAt(index)).setChecked(true);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case GALLERY_REQUEST:
                if (resultCode == RESULT_OK) {
                    Uri selectedImage = data.getData();
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(), selectedImage);
                        int width = 1000;
                        int height = (int) ((double) width / bitmap.getWidth() * bitmap.getHeight());
                        bitmap = Bitmap.createScaledBitmap(bitmap, width, height, false);
                        assert selectedImage != null;
                        imageView.setImageBitmap(bitmap);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
        }
    }

    @Override
    public void onShow(DialogInterface dialogInterface) {
         dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 String name = fnEdit.getText().toString();
                 String lastName = lnEdit.getText().toString();
                 boolean maleGender = radioGroup.getCheckedRadioButtonId() == R.id.radioButton1;

                 if (name.length() == 0 || lastName.length() == 0 || radioGroup.getCheckedRadioButtonId() == -1) {
                     Toast.makeText(context, R.string.incorrect, Toast.LENGTH_SHORT).show();
                     return;
                 }

                 if (oldStudent == null) {
                     OnAdderStudentListener listener = (OnAdderStudentListener) context;
                     if (listener != null) {
                         listener.add(name, lastName, maleGender, bitmap);
                     }
                 } else {
                     OnUpdateStudentListener listener = (OnUpdateStudentListener) context;
                     if (listener != null) {
                         Student student = new Student(name, lastName, maleGender, oldStudent.getPhoto());
                         student.setBitmap(bitmap);
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
        void add(String firstName, String lastName, boolean maleGender, Bitmap bitmap);
    }


    interface OnUpdateStudentListener {
        void update(Student oldStudent, Student newStudent);
    }
}
