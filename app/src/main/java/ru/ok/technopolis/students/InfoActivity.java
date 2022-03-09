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

public class InfoActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView secondName;
    private TextView firstName;

    private static final String TITLE = "Editor";
    private static final int SAVE = 1;
    private static final int DELETE = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setTitle(TITLE);
        setContentView(R.layout.student_info);
        secondName = (EditText) findViewById(R.id.student_info__et_second_name);
        firstName = (EditText) findViewById(R.id.student_info__et_first_name);
        ImageView photo = findViewById(R.id.student_info__iv_photo);
        RadioButton isMale = findViewById(R.id.student_info__rb_male);
        Button saver = findViewById(R.id.student_info__b_save);
        Button deleter = findViewById(R.id.student_info__b_delete);
        Intent data = getIntent();
        int picID = data.getIntExtra(MainActivity.PHOTO_EXTRA, 1);
        photo.setImageResource(picID);
        boolean male = data.getBooleanExtra(MainActivity.MALE_EXTRA, false);
        secondName.setText(data.getStringExtra(MainActivity.SECOND_NAME_EXTRA));
        firstName.setText(data.getStringExtra(MainActivity.FIRST_NAME_EXTRA));
        if (male) {
            isMale.setChecked(male);
        }
        saver.setOnClickListener(this);
        deleter.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.student_info__b_save:
                if ((secondName.getText() != null) && (firstName.getText() != null)) {
                if ((secondName.getText().toString().trim().length() != 0) &&
                        (firstName.getText().toString().trim().length() != 0)) {
                    intent.putExtra(MainActivity.SECOND_NAME_EXTRA, secondName.getText().toString());
                    intent.putExtra(MainActivity.FIRST_NAME_EXTRA, firstName.getText().toString());
                    intent.putExtra(MainActivity.BUTTON_CODE_EXTRA, SAVE);
                    intent.putExtra(MainActivity.STUDENT_INDEX_EXTRA, getIntent().getIntExtra
                            (MainActivity.STUDENT_INDEX_EXTRA, -1));
                    intent.putExtra(MainActivity.PHOTO_EXTRA, getIntent().getIntExtra
                            (MainActivity.PHOTO_EXTRA, 1));
                    intent.putExtra(MainActivity.MALE_EXTRA, false);
                    setResult(RESULT_OK, intent);
                    finish();
                } else {
                    setResult(RESULT_CANCELED, intent);
                    Toast.makeText(this, "Please fill the FirstName and the SecondName fields!",
                            Toast.LENGTH_LONG).show();
                }
                break;
        }
            case R.id.student_info__b_delete:
                intent.putExtra(MainActivity.BUTTON_CODE_EXTRA, DELETE);
                intent.putExtra(MainActivity.STUDENT_INDEX_EXTRA, getIntent().getIntExtra
                        (MainActivity.STUDENT_INDEX_EXTRA, -1));
                setResult(RESULT_OK, intent);
                finish();
                break;

            default:
                break;
        }
    }
}
