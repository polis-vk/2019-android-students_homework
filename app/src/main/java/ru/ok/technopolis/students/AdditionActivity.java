package ru.ok.technopolis.students;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class AdditionActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView secondName;
    private TextView firstName;
    private RadioButton isMale;

    int picID;

    private static final String TITLE = "Addition";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setTitle(TITLE);
        setContentView(R.layout.student_addition);
        Random random = new Random();
        secondName = (EditText) findViewById(R.id.student_add__et_second_name);
        firstName = (EditText) findViewById(R.id.student_add__et_first_name);
        String[] imageNames = getResources().getStringArray(R.array.image_names);
        ImageView photo = findViewById(R.id.student_add__iv_photo);
        isMale = findViewById(R.id.student_add__rb_male);
        Button saver = findViewById(R.id.student_add__b_save);
        picID = getResources().getIdentifier(imageNames[random.nextInt(5)], "drawable", getPackageName());
        photo.setImageResource(picID);
        saver.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        boolean male = isMale.isChecked();
        Intent intent = new Intent();
        if ((secondName.getText() != null) && (firstName.getText() != null)) {
            if ((secondName.getText().toString().trim().length() != 0) &&
                    (firstName.getText().toString().trim().length() != 0)) {
                intent.putExtra(MainActivity.SECOND_NAME_EXTRA, secondName.getText().toString());
                intent.putExtra(MainActivity.FIRST_NAME_EXTRA, firstName.getText().toString());
                intent.putExtra(MainActivity.MALE_EXTRA, male);
                intent.putExtra(MainActivity.PHOTO_EXTRA, picID);
                setResult(RESULT_OK, intent);
                finish();
            } else {
                setResult(RESULT_CANCELED, intent);
                Toast.makeText(this, "Please fill the FirstName and the SecondName fields!", Toast.LENGTH_LONG).show();
            }
        }
    }
}
