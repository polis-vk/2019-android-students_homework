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
    private final Listener onStudentClickListener;

    StudentAdapter(List<Student> students, Listener onStudentClickListener) {
        this.students = students;
        this.onStudentClickListener = onStudentClickListener;
    }

    @NonNull
    @Override
    public StudentViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.student_item, viewGroup, false);
        view.setOnClickListener(v -> onStudentClickListener.onStudentClick(view, (Integer) v.getTag()));
        return new StudentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StudentViewHolder studentViewHolder, int i) {
        Student student = students.get(i);
        studentViewHolder.bind(student);
        studentViewHolder.itemView.setTag(i);
    }

    @Override
    public int getItemCount() {
        return students.size();
    }

    static final class StudentViewHolder extends RecyclerView.ViewHolder {
        private final TextView nameTextView;
        private final ImageView avatarImageView;

        StudentViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.student_item__tv_name);
            avatarImageView = itemView.findViewById(R.id.movie_item__studentPicture);
        }

        private void bind(@NonNull Student student) {
            nameTextView.setText(String.valueOf(student.getSecondName() + " " + student.getFirstName()));
            avatarImageView.setImageResource(student.getPhoto());
        }
    }

    interface Listener {

        void onStudentClick(View view, int i);

    }
}