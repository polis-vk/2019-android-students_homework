package ru.ok.technopolis.students;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

public class InfoActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView secondName;
    private TextView firstName;
    private ImageView photo;
    private RadioButton isFemale;
    private RadioButton isMale;
    private Button saver;
    private Button deleter;

    private String[] imageNames;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setTitle("Editor");
        setContentView(R.layout.student_info);
        secondName = (EditText) findViewById(R.id.student_info__et_second_name);
        firstName = (EditText) findViewById(R.id.student_info__et_first_name);
        imageNames = getResources().getStringArray(R.array.image_names);
        photo = findViewById(R.id.student_info__iv_photo);
        isFemale = findViewById(R.id.student_info__rb_female);
        isMale = findViewById(R.id.student_info__rb_male);
        saver = findViewById(R.id.student_info__b_save);
        deleter = findViewById(R.id.student_info__b_delete);
        Intent data = getIntent();
        int pic_id = data.getIntExtra("photo", 1);
        photo.setImageResource(pic_id);
        boolean male;
        male = data.getBooleanExtra("male", false);
        secondName.setText(data.getStringExtra("second_name"));
        firstName.setText(data.getStringExtra("first_name"));
        if (male)
            isMale.setChecked(true);
        else
            isFemale.setChecked(true);
        saver.setOnClickListener(this);
        deleter.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.student_info__b_save:
                if ((secondName.getText().toString().trim().length() != 0) &&
                        (firstName.getText().toString().trim().length() != 0)) {
                    intent.putExtra("secondName", secondName.getText().toString());
                    intent.putExtra("firstName", firstName.getText().toString());
                    intent.putExtra("button_code", 1);
                    intent.putExtra("index", getIntent().getIntExtra("index", -1));
                    intent.putExtra("photo", getIntent().getIntExtra("photo", 1));
                    intent.putExtra("isMale", false);
                    setResult(RESULT_OK, intent);
                    finish();
                } else {
                    setResult(RESULT_CANCELED, intent);
                    Toast.makeText(this, "Please fill the FirstName and the SecondName fields!", Toast.LENGTH_LONG).show();
                }
                break;

            case R.id.student_info__b_delete:
                intent.putExtra("button_code", 2);
                intent.putExtra("index", getIntent().getIntExtra("index", -1));
                setResult(RESULT_OK, intent);
                finish();
                break;

            default:
                break;
        }
    }
}
