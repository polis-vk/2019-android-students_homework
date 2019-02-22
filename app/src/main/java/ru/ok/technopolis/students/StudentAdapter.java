package ru.ok.technopolis.students;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.StudentsViewHolder> {

    private List<Student> students;

    public StudentAdapter(List<Student> students){
        this.students = students;
    }

    @NonNull
    @Override
    public StudentsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.students_item, viewGroup, false);
        return new StudentsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StudentsViewHolder studentsViewHolder, int i) {
        studentsViewHolder.bind(students.get(i));
    }

    @Override
    public int getItemCount() {
        return students.size();
    }

    static final class StudentsViewHolder extends RecyclerView.ViewHolder{

        private final ImageView avatarImageView;
        private final TextView nameTextView;


        public StudentsViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.students_item__student_name);
            avatarImageView = itemView.findViewById((R.id.students_item__student_avatar));
        }

        private void bind(@NonNull Student student){
            nameTextView.setText(student.getFirstName() + " " + student.getSecondName());
            avatarImageView.setImageResource(student.getPhoto());
        }
    }
}
