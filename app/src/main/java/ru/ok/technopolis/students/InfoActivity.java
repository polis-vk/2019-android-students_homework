package ru.ok.technopolis.students;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.Random;

public class InfoActivity extends AppCompatActivity implements View.OnClickListener {
    TextView secondName;
    TextView firstName;
    ImageView photo;
    RadioButton isFemale;
    RadioButton isMale;
    Button saver;
    Button deleter;

    private String[] image_names;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setTitle("Editor");
        setContentView(R.layout.student_info);
        secondName = (EditText) findViewById(R.id.student_info__et_second_name);
        firstName = (EditText) findViewById(R.id.student_info__et_first_name);
        image_names = getResources().getStringArray(R.array.image_names);
        photo = findViewById(R.id.student_info__iv_photo);
        isFemale = findViewById(R.id.student_info__rb_female);
        isMale = findViewById(R.id.student_info__rb_male);
        saver = findViewById(R.id.student_info__b_save);
        deleter = findViewById(R.id.student_info__b_delete);
        Intent data = getIntent();
        int pic_id = data.getIntExtra("photo", 1);
//        int id = getResources().getIdentifier(image_names[pic_id], "drawable", getPackageName());
        photo.setImageResource(pic_id);
        boolean male;
        boolean female;
        male = data.getBooleanExtra("male", false);
        female = !male;
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
                intent.putExtra("secondName", secondName.getText().toString());
                intent.putExtra("firstName", firstName.getText().toString());
                intent.putExtra("button_code", 1);
                intent.putExtra("index", getIntent().getIntExtra("index", -1));
                intent.putExtra("photo", getIntent().getIntExtra("photo", 1));
                intent.putExtra("isMale", false);
//                intent.putExtra("pic_id", 1);
                break;
            case R.id.student_info__b_delete:
                intent.putExtra("button_code", 2);
                intent.putExtra("index", getIntent().getIntExtra("index", -1));
                break;
            default:
                break;
        }
        setResult(RESULT_OK, intent);
        finish();
    }
}
