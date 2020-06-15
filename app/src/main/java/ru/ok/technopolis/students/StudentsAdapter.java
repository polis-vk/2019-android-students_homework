package ru.ok.technopolis.students;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.jetbrains.annotations.NotNull;

public class StudentsAdapter extends RecyclerView.Adapter<StudentsAdapter.StudentViewHolder> {

    private final StudentsRepository repository;
    private final View.OnClickListener onClick; // устанавливает в поля формы создания студента нужные данные

    public StudentsAdapter(StudentsRepository repository, View.OnClickListener onClick) {
        this.repository = repository;
        this.onClick = onClick;
    }

    @NonNull
    @Override
    public StudentViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {  //возвращает объект ViewHolder, который будет хранить данные по одному объекту Student
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.student_list_item,viewGroup,false); // "надуваем" список студентов ячейкой
        return new StudentViewHolder(view); // возвращаем конкретную ячейку студента с определенной в ней вьюшкой
    }

    @Override
    public void onBindViewHolder(@NonNull StudentViewHolder studentViewHolder, int i) {//выполняет привязку объекта ViewHolder к объекту Phone по определенной позиции;
        studentViewHolder.bind(repository.get(i) /*возвращает конкретного студента из хранилища*/);// Устанавливает информацию о студенте в его свойства
        studentViewHolder.itemView.setTag(i); // устанавливаем tag(позиция) ранвый номеру студента в хранилище

        Integer active = repository.getActive(); //получаем студента на которого мы нажали(i), либо null

        if(active!=null && active.equals(i)){   // Отвечает за подстветку ячейки студента в зависимости от нажатия
            studentViewHolder.itemView.setBackgroundResource(R.color.colorLightGray);
        }else{
            studentViewHolder.itemView.setBackgroundResource(R.color.colorWhite);
        }

        studentViewHolder.itemView.setOnClickListener(new View.OnClickListener(){ // (itemView это объект класса View который наследуется от класса ViewHolder) в нашем случае это ячейка, которая определена в xml файле
            @Override                                                             //Устанавливаем способность нажатия на конкретную ячейку(элементы списка студент) с последующим показыванием формы заполнения
            public void onClick(View v) {
                int position = (int) v.getTag();                   // позиция студента в хранилище
                repository.setActiveStudent(position);               // устанавливаем номер активного студента на которого нажали в хранилище
                onClick.onClick(v);// устанавливаем в поля формы студента нужные данные и показываем форму

                notifyDataSetChanged();                   // ОБНОВЛЯЕТ данные и вид списка студентов
            }
        });

    }

    @Override
    public int getItemCount() {    //возвращает количество объектов в списке и в хранилище соответственно.
        return repository.size();
    }


    static final class StudentViewHolder extends RecyclerView.ViewHolder { //Класс отвечающий за конкретную ячейку студента в списке (RecyclerView)
        private final ImageView photo; //объект вьюшки
        private final TextView name;//объект вьюшки

        StudentViewHolder(@NonNull View itemView) { // конструктор на вход которого передаем вид ячейки (view) который был создан в xml
            super(itemView);
            this.photo = itemView.findViewById(R.id.item_photo); // присваиваем объектам вью конкретный элемент визуального интерфейса
            this.name =itemView.findViewById(R.id.item_name);
        }

        @SuppressLint("SetTextI18n")
        private void bind(@NotNull Student student){ // Устанавливает информацию о студенте в его вьюшки
            photo.setImageResource(student.getPhoto());
            name.setText(student.getFirstName()+ " " + student.getSecondName());

        }

    }
}
