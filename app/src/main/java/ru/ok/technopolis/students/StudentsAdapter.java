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
    private int focusedItem = -1;

    public StudentsAdapter(List<Student> students, Listener onStudentClickListener) {
        this.students = students;
        this.onStudentClickListener = onStudentClickListener;
    }

    @NonNull
    @Override
    public StudentViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.student_item, viewGroup, false);
        view.setOnClickListener(v -> {
            focusedItem = ((RecyclerView.LayoutParams) v.getLayoutParams()).getViewLayoutPosition();
            notifyDataSetChanged();
            onStudentClickListener.onStudentClick((Student) v.getTag());
        });
        return new StudentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StudentViewHolder viewHolder, final int i) {
        Student student = students.get(i);
        viewHolder.bind(student);
        viewHolder.itemView.setTag(student);
        viewHolder.itemView.setSelected(focusedItem == i);
    }

    @Override
    public int getItemCount() {
        return students.size();
    }

    static final class StudentViewHolder extends RecyclerView.ViewHolder {
        private final TextView nameView;
        private final ImageView photoView;

        private StudentViewHolder(@NonNull View itemView) {
            super(itemView);
            nameView = itemView.findViewById(R.id.student_item_name);
            photoView = itemView.findViewById(R.id.student_item_photo);
        }

        private void bind(@NonNull Student student) {
            nameView.setText(student.getFirstName() + " " + student.getSecondName());
            photoView.setImageResource(student.getPhoto());
        }
    }

    interface Listener {
        void onStudentClick(Student student);
    }
}