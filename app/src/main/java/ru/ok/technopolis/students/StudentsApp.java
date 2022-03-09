package ru.ok.technopolis.students;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;


public class StudentsApp extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       setContentView(R.layout.student_app);
       final StudentsRepository repository = new StudentsRepository(studentsBegin()); //Хранилище всех студентов
       final RecyclerView studentListRv = findViewById(R.id.rv_student);     // Список студентов
       final StudentsView studentsView = findViewById(R.id.students_app_view); // Форма заполнения студента
       final Button createStudentBtn = findViewById(R.id.add_student); //Кнопка добавления студента
       final StudentsAdapter adapter = new StudentsAdapter(repository ,new View.OnClickListener(){ // объявляем адаптер

            @Override
            public void onClick(View v) {
                studentsView.setStudent(repository.getActiveStudent() /*возвращает активного студента*/); // устанавливает в поля формы создания студента нужные данные(существующего студента) и делает видимой форму
                createStudentBtn.setVisibility(View.GONE);// делаем кнопку добавления студента невидимой
            }
        });

        studentListRv.setAdapter(adapter);                                                  // установка адаптера
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);            // Отвечает за вид отображения RecyclerView
        studentListRv.setLayoutManager(layoutManager);                                         // Отвечает за вид отображения RecyclerView

        createStudentBtn.setOnClickListener(new View.OnClickListener() { // ставим на кнопку "добавить студента "  обработчик
            @Override
            public void onClick(View v) {
                studentsView.setStudent(null); // передаем в методе null, чтобы показать что добавляется новый студент
                repository.setActiveStudent(null); // активный студент null чтобы убрать сервый фон у прошлого добавленного и сделать у нового
                adapter.notifyDataSetChanged();// обновляем список чтобы убрать серый фон у прошлого студента
                createStudentBtn.setVisibility(View.GONE);// делаем кнопку добавления студента невидимой

            }
        });

        setupStudentViewListeners(studentsView,repository,adapter,createStudentBtn); // устанавливаем обработчики на кнопки в форме добавления

    }

    private void setupStudentViewListeners(StudentsView studentsView, final StudentsRepository repository, final StudentsAdapter adapter, final Button createStudentBtn) { //метод устанавливающий обработчики на кнопки в форме добавления студента
        studentsView.setOnRemove(new View.OnClickListener() { // устанавливаем обработчки на кнопку "удалить"
            @Override
            public void onClick(View v) {
                repository.removeActiveStudent();
                adapter.notifyDataSetChanged(); // Обновляет данные списка студентов(тут реализован через точку тк мы находимся вне класса адаптер)
                createStudentBtn.setVisibility(View.VISIBLE);// делаем кнопку добавления студента видимой
                hideKeyBoard();
            }
        });
        studentsView.setOnSave(new View.OnClickListener() { // определяем обработчик кнопки сохранения измененных данных существующего студента
            @Override
            public void onClick(View v) {
                adapter.notifyDataSetChanged();
                createStudentBtn.setVisibility(View.VISIBLE);// делаем кнопку добавления студента видимой
                hideKeyBoard();
            }
        });
        studentsView.setOnCreate(new View.OnClickListener() {// устанавливаем обработчки на кнопку "Сохранить нового студента или пересохранить измененные данные у старого студента"
            @Override
            public void onClick(View v) {
                Student student= (Student) v.getTag(); // получаем tag  в данном случае это экземпляр класса студент
                repository.addStudent(student); // добавляем студента которого ввели в форме заполнения в хранилище
                createStudentBtn.setVisibility(View.VISIBLE);// делаем кнопку добавления студента видимой
                hideKeyBoard();
            }
        });
        studentsView.setOnHide(new View.OnClickListener() {// устанавливаем обработчки на кнопку "скрыть (форму заполнения)"
            @Override
            public void onClick(View v) {
                repository.setActiveStudent(null); // активный студент становится null тк мы вышли в основной список студентов
                adapter.notifyDataSetChanged(); // Обновляет данные списка студентов (тут реализован через точку тк мы находимся вне класса адаптер)
                createStudentBtn.setVisibility(View.VISIBLE);// делаем кнопку добавления студента видимой
                hideKeyBoard();
            }
        });
    }
    private void hideKeyBoard(){// скрытин клавиатуры
        View view = this.getCurrentFocus();
        if(view != null){
            InputMethodManager imm= (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(),0);
            view.clearFocus();
        }
    }
    static List<Student> studentsBegin(){// первоначальные студенты
         List<Student> students = new ArrayList<>();
         students.add(new Student("Георгий","Евстигнеев",true, R.drawable.male_3));
        students.add(new Student("Ксения","Василенко",false, R.drawable.female_2));
        students.add(new Student("Наташа","Медведева",false, R.drawable.female_1));
         return students;
    }
}
