package ru.ok.technopolis.students;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.telephony.RadioAccessSpecifier;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.Random;

public class AdditionActivity extends AppCompatActivity implements View.OnClickListener {
    TextView secondName;
    TextView firstName;
    ImageView photo;
    RadioButton isFemale;
    RadioButton isMale;
    Button saver;

    private String[] image_names;
    int pic_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setTitle("Addition");
        setContentView(R.layout.student_addition);
        Random random = new Random();
        Intent data = getIntent();
        secondName = (EditText) findViewById(R.id.student_add__et_second_name);
        firstName = (EditText) findViewById(R.id.student_add__et_first_name);
        image_names = getResources().getStringArray(R.array.image_names);
        photo = findViewById(R.id.student_add__iv_photo);
        isFemale = findViewById(R.id.student_add__rb_female);
        isMale = findViewById(R.id.student_add__rb_male);
        saver = findViewById(R.id.student_add__b_save);
        pic_id = getResources().getIdentifier(image_names[random.nextInt(5)], "drawable", getPackageName());
        photo.setImageResource(pic_id);
        saver.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        boolean male = isMale.isChecked();
        Intent intent = new Intent();
        intent.putExtra("secondName", secondName.getText().toString());
        intent.putExtra("firstName", firstName.getText().toString());
        intent.putExtra("sex", male);
        intent.putExtra("photo", pic_id);
        setResult(RESULT_OK, intent);
        finish();
    }
}
