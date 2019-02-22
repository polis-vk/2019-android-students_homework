package ru.ok.technopolis.students;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.io.IOException;

import javax.crypto.spec.OAEPParameterSpec;

import static android.app.Activity.RESULT_OK;


public class StudentAdderDialog extends DialogFragment implements DialogInterface.OnClickListener {
    private Bitmap bitmap;

    private static final int GALLERY_REQUEST = 1;
    final String STUDENT_TAG = "STUDENT_TAG";
    EditText fnEdit, lnEdit;
    ImageView imageView;
    RadioGroup radioGroup;
    Context context;

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        context = getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_student_adder, null);
        initView(view);
        AlertDialog.Builder adb = new AlertDialog.Builder(context).setView(view).setTitle("Студент").setMessage("Попробуйте нажать на Android").setPositiveButton(R.string.yes, this).setNegativeButton(R.string.no, this);
        return adb.create();
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
    }

    public void onClick(DialogInterface dialog, int which) {
        switch (which) {
            case Dialog.BUTTON_POSITIVE:
                String name = fnEdit.getText().toString();
                String lastName = lnEdit.getText().toString();
                boolean maleGender = radioGroup.getCheckedRadioButtonId() == R.id.radioButton1;

                if (name.length() == 0 || lastName.length() == 0) {
                    Toast.makeText(context, "Данные введены некорректно", Toast.LENGTH_SHORT).show();
                    return;
                }

                OnAdderStudentListener listener = (OnAdderStudentListener) context;
                if (listener != null) {
                  listener.add(name, lastName, maleGender, bitmap);
                }
                break;
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
                        assert selectedImage != null;
                        imageView.setImageBitmap(bitmap);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
        }
    }

    interface OnAdderStudentListener {
        void add(String firstname, String lastname, boolean maleGender, Bitmap bitmap);
    }
}
