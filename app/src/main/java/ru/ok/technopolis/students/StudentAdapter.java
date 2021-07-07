package ru.ok.technopolis.students;


import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.StudentViewHolder> {

    private final List<Student> students;
    private final Listener onStudentListener;

    StudentAdapter(List<Student> students, Listener onStudentListener) {
        this.onStudentListener = onStudentListener;
        this.students = students;
    }

    @NonNull
    @Override
    public StudentViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.student, viewGroup, false);
        view.setOnClickListener(v -> onStudentListener.onStudentClick((Student) v.getTag()));
        return new StudentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StudentViewHolder studentViewHolder, int i) {
        Student student = students.get(i);
        studentViewHolder.bind(students.get(i));
        studentViewHolder.itemView.setTag(student);
    }

    @Override
    public int getItemCount() {
        return students.size();
    }

    static final class StudentViewHolder extends RecyclerView.ViewHolder {

        private TextView studentInitials;
        private ImageView studentPhoto;


        private StudentViewHolder(@NonNull View itemView) {
            super(itemView);
            studentInitials = itemView.findViewById(R.id.student_initials);
            studentPhoto = itemView.findViewById(R.id.student_photo);
        }

        private void bind(@NonNull Student student) {
            studentInitials.setText(String.format("%s %s", student.getFirstName(), student.getSecondName()));
            studentPhoto.setImageResource(student.getPhoto());
        }
    }

    interface Listener {
        void onStudentClick(Student student);
    }
}
