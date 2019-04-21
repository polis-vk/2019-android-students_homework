package ru.ok.technopolis.students.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import ru.ok.technopolis.students.R;
import ru.ok.technopolis.students.model.Student;

public class MainActivityAdapter extends RecyclerView.Adapter <MainActivityAdapter.StudentViewHolder>{

    private final List<Student> students;
    private final OnStudentListener onStudentListener;

    public MainActivityAdapter(List <Student> students, OnStudentListener onStudentListener) {
        this.students = students;
        this.onStudentListener = onStudentListener;
    }

    @NonNull
    @Override
    public StudentViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.student_item, viewGroup, false);
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

    final class StudentViewHolder extends RecyclerView.ViewHolder {

        private TextView studentInitials;
        private ImageView studentPhoto;

        private StudentViewHolder(@NonNull View itemView) {
            super(itemView);
            studentInitials = itemView.findViewById(R.id.student_initials);
            studentPhoto = itemView.findViewById(R.id.student_photo);
        }

        private void bind(Student student) {
            studentInitials.setText(String.format("%s %s", student.getFirstName(), student.getSecondName()));
            studentPhoto.setImageResource(student.getPhoto());
        }
    }

    public interface OnStudentListener {
        void onStudentClick(Student student);
    }
}
