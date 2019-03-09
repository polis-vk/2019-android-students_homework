package ru.ok.technopolis.students;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import static ru.ok.technopolis.students.BuildConfig.LOG_TAG;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.StudentsViewHolder> {

    private List<Student> students;
    private Listener onItemClickListner;

    StudentAdapter(List<Student> students, Listener onItemClickListner) {
        this.students = students;
        this.onItemClickListner = onItemClickListner;
    }

    @NonNull
    @Override
    public StudentsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.students_item, viewGroup, false);
        view.setOnClickListener(v -> onItemClickListner.onItemClick((Student) v.getTag()));
        return new StudentsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StudentsViewHolder studentsViewHolder, int i) {

        Student student = students.get(i);
        studentsViewHolder.bind(students.get(i));
        studentsViewHolder.itemView.setTag(student);

    }

    @Override
    public int getItemCount() {
        return students.size();
    }

    static final class StudentsViewHolder extends RecyclerView.ViewHolder {

        private final ImageView avatarImageView;
        private final TextView nameTextView;
        LinearLayout rowLinearLayout;


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

    interface Listener {

        void onItemClick(Student student);

    }


}
