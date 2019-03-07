package ru.ok.technopolis.students;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class StudentsAdapter extends RecyclerView.Adapter<StudentsAdapter.StudentViewHolder>{

    private final List<Student> students;
    private final Listener onStudentClickListener;

    private int selectedStudent = -1;

    public StudentsAdapter(List<Student> students, Listener onStudentClickListener) {
        this.students = students;
        this.onStudentClickListener = onStudentClickListener;
    }

    public void removeHighlight() {
        selectedStudent = -1;
        notifyItemChanged(selectedStudent);
    }

    @NonNull
    @Override
    public StudentViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.student_item, viewGroup, false);
        return new StudentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StudentViewHolder viewHolder, int i) {
        Student movie = students.get(i);
        viewHolder.bind(movie);
        viewHolder.itemView.setTag(movie);

        viewHolder.itemView.setSelected(selectedStudent == i);

        if(selectedStudent == i){
            viewHolder.itemView.setBackgroundColor(ContextCompat.getColor(viewHolder.itemView.getContext(), R.color.lightGray));
        } else {
            viewHolder.itemView.setBackgroundColor(Color.WHITE);
        }
    }

    @Override
    public int getItemCount() {
        return students.size();
    }

    final class StudentViewHolder extends RecyclerView.ViewHolder{

        private final TextView nameTextView;
        private final ImageView avatarImageView;

        private StudentViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.student_item__full_name);
            avatarImageView = itemView.findViewById(R.id.student_item__avatar);

            itemView.setOnClickListener(v -> {
                onStudentClickListener.onStudentClick((Student) v.getTag());
                notifyItemChanged(selectedStudent);
                selectedStudent = getLayoutPosition();
                notifyItemChanged(selectedStudent);
            });
        }

        private void bind(@NonNull Student student) {
            String fullName = student.getSecondName() + " " + student.getFirstName();
            nameTextView.setText(fullName);
            avatarImageView.setImageResource(student.getPhoto());
        }
    }

    interface Listener {
        void onStudentClick(Student student);
    }
}
