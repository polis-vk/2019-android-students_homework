package ru.ok.technopolis.students;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import static ru.ok.technopolis.students.BuildConfig.LOG_TAG;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.StudentsViewHolder> {

    private List<Student> students;
    private Listener onItemClickListener;
    private Integer activeId = null;

    StudentAdapter(List<Student> students, Listener onItemClickListner) {
        this.students = students;
        this.onItemClickListener = onItemClickListner;
    }

    @NonNull
    @Override
    public StudentsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.students_item, viewGroup, false);
        view.setOnClickListener(v -> onItemClickListener.onItemClick((Student) v.getTag()));
        return new StudentsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StudentsViewHolder studentsViewHolder, int i) {

        Student student = students.get(i);
        studentsViewHolder.bind(students.get(i));
        studentsViewHolder.itemView.setTag(student);

        Integer active = activeId;
        if (active != null && active.equals(i)) {
            studentsViewHolder.itemView.setBackgroundResource(R.color.colorPrimaryDark);
        } else {
            studentsViewHolder.itemView.setBackgroundResource(R.color.colorPrimary);
        }

        studentsViewHolder.itemView.setOnClickListener(v -> {
            Student position = (Student) v.getTag();
            //position.setActive(true);
            setActiveId(students.indexOf(position));
            onItemClickListener.onItemClick(position);
            notifyDataSetChanged();
        });

    }

    @Override
    public int getItemCount() {
        return students.size();
    }

    static final class StudentsViewHolder extends RecyclerView.ViewHolder {

        private final ImageView avatarImageView;
        private final TextView nameTextView;


        StudentsViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.students_item__student_name);
            avatarImageView = itemView.findViewById((R.id.students_item__student_avatar));
        }

        private void bind(@NonNull Student student) {
            if (BuildConfig.LOG)
                Log.d(LOG_TAG, "Student info in Adapter " + student.getFirstName() + " " + student.getSecondName() + " " + student.isMaleGender() + " " + student.getPhoto());

            avatarImageView.setImageResource(student.getPhoto());
            nameTextView.setText(String.format("%s %s", student.getFirstName(), student.getSecondName()));
            if (BuildConfig.LOG) {
                Log.d(LOG_TAG, "bind ok");
            }
        }


    }

    void setActiveId(Integer activeId) {
        this.activeId = activeId;
    }

    interface Listener {

        void onItemClick(Student student);

    }


}
