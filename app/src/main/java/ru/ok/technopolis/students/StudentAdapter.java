package ru.ok.technopolis.students;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.support.v7.widget.RecyclerView;

import java.util.List;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.StudentViewHolder> {
    private final Listener onStudentClickListener;
    private List<Student> students;

    StudentAdapter(List<Student> students, Listener onStudentClickListener) {
        this.students = students;
        this.onStudentClickListener = onStudentClickListener;
    }

    @NonNull
    @Override
    public StudentViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycler_view_item,
                viewGroup, false);
        view.setOnClickListener(v -> onStudentClickListener.onStudentClick(view, (Integer) v.getTag()));
        return new StudentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StudentViewHolder viewHolder, int i) {
        Student student = students.get(i);
        viewHolder.bind(student);
        viewHolder.itemView.setTag(i);
    }

    @Override
    public int getItemCount() {
        return students.size();
    }

    static final class StudentViewHolder extends RecyclerView.ViewHolder {

        private final TextView nameTextView;
        private final ImageView iconImageView;

        StudentViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.recycler_view_item_name);
            iconImageView = itemView.findViewById(R.id.recycler_view_item_icon);
        }

        private void bind(@NonNull Student student) {
            nameTextView.setText(String.format("%s %s", student.getFirstName(), student.getSecondName()));
            iconImageView.setImageResource(student.getPhoto());
        }
    }

    interface Listener {
        void onStudentClick(View view, int i);
    }

}