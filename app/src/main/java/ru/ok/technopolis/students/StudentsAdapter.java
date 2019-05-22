package ru.ok.technopolis.students;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class StudentsAdapter extends RecyclerView.Adapter<StudentsAdapter.StudentViewHolder> {

    private final List<Student> students;
    private final Listener onStudentClickListener;

    public StudentsAdapter(List<Student> students, Listener onStudentClickListener) {
        this.students = students;
        this.onStudentClickListener = onStudentClickListener;
    }

    @NonNull
    @Override
    public StudentViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.view_student, viewGroup, false);
        view.setOnClickListener(v -> onStudentClickListener.onStudentClick((Student) v.getTag(), (String) v.getContentDescription(
        ), view));
        return new StudentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StudentViewHolder viewHolder, final int i) {
        Student student = students.get(i);
        viewHolder.bind(student);
        viewHolder.itemView.setTag(student);
        viewHolder.itemView.setContentDescription(Integer.toString(i));
    }

    @Override
    public int getItemCount() {
        return students.size();
    }

    static final class StudentViewHolder extends RecyclerView.ViewHolder {

        private final TextView nameTextView;
        private final ImageView posterImageView;

        private StudentViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.student_item__tv_Name);
            posterImageView = itemView.findViewById(R.id.student_item__iv_poster);
        }

        private void bind(@NonNull Student student) {
            nameTextView.setText(student.getFirstName() + " " + student.getSecondName());
            if (student.isMaleGender()) {
                posterImageView.setImageResource(student.getPhoto());
            } else {
                posterImageView.setImageResource(student.getPhoto());
            }
        }
    }

    interface Listener {
        void onStudentClick(Student student, String id, View v);
    }
}