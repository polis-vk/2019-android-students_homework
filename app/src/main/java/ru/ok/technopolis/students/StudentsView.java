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

public class StudentsView extends ConstraintLayout {
    private static final Random random = new Random(); // переменная генерирующая стучайное число
    private static final int[] malePhotos = {R.drawable.male_1,R.drawable.male_2,R.drawable.male_3}; // массив фоток м
    private static final int[] femalePhotos = {R.drawable.female_1,R.drawable.female_2,R.drawable.female_3};// массив фоток ж

    private Student student; // экземпляр класса Student, в нем хранятся свойства студента который необходимы в форме заполнения

    private TextView firstNameTv; // объект вьюшки имя формы заполнения
    private TextView secondNameTv;// объект вьюшки фамилия формы заполнения
    private ImageView photoIv;// объект вьюшки фото формы заполнения
    private CheckBox isMaleCb;// объект вьюшки пол формы заполнения

    private OnClickListener save; // обработчики отвечающий за сохранения или переопределение данных уже существующего студента   // обьекты интерфейса которым будут присвоены обработчики
    private OnClickListener create;// обработчик отвечающий за добавление нового студента
    private OnClickListener remove;// обработчик отвечающий за удаление студента
    private OnClickListener hide;// обработчик отвечающий скрытие формы заполенения


    public StudentsView(Context context) {
        super(context);
        init(context);
    }

    public StudentsView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public StudentsView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context contex){

        LayoutInflater inflater = (LayoutInflater) contex.getSystemService(Context.LAYOUT_INFLATER_SERVICE); // наполняем StudentsView формой заполнения
        View view = inflater.inflate(R.layout.student_view,this,true); // view это форма заполнения и ее элементы

        photoIv=view.findViewById(R.id.editView_photo);//фото в форме заполнения
        firstNameTv=view.findViewById(R.id.editText1);//имя в форме заполнения
        secondNameTv=view.findViewById(R.id.editText2);//фамилия в форме заполнения
        isMaleCb=view.findViewById(R.id.checkBox);//пол в форме заполнения

        final Button saveBtn = view.findViewById(R.id.student_view_save); // кнопка (сохранения/создания) нового студента
        saveBtn.setOnClickListener(new OnClickListener() {    // устанавливаем на кнопку (сохранения/создания) обработчик // выполняется после нажатия на эту кнопку
            @Override
            public void onClick(View v) { // v - это наша кнопка "сохранить"
                CharSequence firstName = firstNameTv.getText(); // после нажатия на кнопку считываем то что мы записали в поля ввода в эти переменные
                CharSequence secondName = secondNameTv.getText();
                if (firstName == null || firstName.length() == 0 || secondName == null || secondName.length() == 0) { // проверка что поля имя и фамилия заполнены
                    String text = getContext().getString(R.string.error_insert_first_name_and_second_name);
                    Toast.makeText(getContext(), text, Toast.LENGTH_LONG).show(); // вывод всплывающего текста с предупреждением
                } else { // если они заполнены то выполняется этот блок кода, который отвечает за создание или изменения студента
                    if (student != null) { // если мы нажали на существующего студента
                        student.setFirstName(firstName.toString()); // переопределяем даные которые введены в фоме заполнения или сохраняем прежние
                        student.setSecondName(secondName.toString());//

                        if ((student.isMaleGender()) != (isMaleCb.isChecked())) { // изменяем фото при смене пола
                            student.setMaleGender(isMaleCb.isChecked());
                            student.setPhoto(randomPhoto(isMaleCb.isChecked()));
                        }

                        if (save != null) { // сохраняем изменения
                            save.onClick(v); // мы вызываем метод которые определили и задали в активити (onClick выполняет именно то действие которое задано в объекте save) //обновляем список и скрываем клавиатуру
                            StudentsView.this.setVisibility(GONE);//скрываем форму заполнения
                        }
                    } else { // выполняется если мы создаем нового студента
                        boolean isMale = isMaleCb.isChecked(); // считываем пол
                        int photo = randomPhoto(isMale); // генерируем новое фото
                        photoIv.setImageResource(photo); // устанавливаем новое фото в форму заполнения
                        student = new Student(firstName.toString(), secondName.toString(), isMale, photo); // создаем экзмепляр класса студент
                        v.setTag(student); // устанавливаем таг в это конкретное вью (tag на вход принимает object)
                        if (create != null) {
                            create.onClick(v);// мы вызываем метод которые определили и задали в активити (onClick выполняет именно то действие которое задано в объекте create) //
                            StudentsView.this.setVisibility(GONE);//скрываем форму заполнения
                        }
                    }
                }
            }
        });

        Button hideBtn = view.findViewById(R.id.student_view_hide); // кнопка "Скрыть"
        hideBtn.setOnClickListener(new OnClickListener() { // устанавливаем обработчик
            @Override
            public void onClick(View v) {
                if(hide != null){
                    hide.onClick(v);
                }
                StudentsView.this.setVisibility(GONE);//скрываем форму заполнения
            }
        });
        Button removeBtn = view.findViewById(R.id.student_view_remove);// кнопка "Удалить"
        removeBtn.setOnClickListener(new OnClickListener(){ // устанавливаем обработчик

            @Override
            public void onClick(View v) {
                if(student != null ){
                    if(remove!=null){
                        remove.onClick(v);
                    }
                    StudentsView.this.setVisibility(GONE);//скрываем форму заполнения
                    clearFields();
                }
            }
        });


    }
    public void setOnRemove(OnClickListener remove) {// конструктор инициализации обработчика на кнопку "удалить"
        this.remove= remove;
    }

    public void setOnSave(OnClickListener save) {// конструктор инициализации обработчика на кнопку "Сохранить(переопределить)"
        this.save = save;
    }

    public void setOnCreate(OnClickListener create) {// конструктор инициализации обработчика на кнопку "Сохранить(создать нового)"
        this.create = create;
    }

    public void setOnHide(OnClickListener hide) {// конструктор инициализации обработчика на кнопку "Скрыть"
        this.hide = hide;
    }

    public void setStudent(Student student){ // Установка информации о студенте в форму создания
        this.student = student;
        if(student != null){ // если мы нажали на какого то студента, то выполнится этот блок кода и установит в форму уже существующие данные
            photoIv.setImageResource(student.getPhoto());
            firstNameTv.setText(student.getFirstName());
            secondNameTv.setText(student.getSecondName());
            isMaleCb.setChecked(student.isMaleGender());
        }else{
            clearFields(); // если мы создаем нового студента, то поля в форме добавления отчистяться
        }
        setVisibility(VISIBLE);// форма создания становится видимой
    }

    private void clearFields(){ // метод отчистки полей в форме
        photoIv.setImageResource(R.color.colorWhite); // фотография - белый фон
        firstNameTv.setText("");
        secondNameTv.setText("");
        isMaleCb.setChecked(false);
    }

    private static int randomPhoto(boolean isMale){ // возвращает рандомное фото
        return isMale ? malePhotos[random.nextInt(malePhotos.length)] : femalePhotos[random.nextInt(femalePhotos.length)];
    }

}
