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
    private final Listener studentListener;


    StudentAdapter(List<Student> students, Listener studentListener) {
        this.students = students;
        this.studentListener = studentListener;
    }

    @NonNull
    @Override
    public StudentViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.student_item, viewGroup, false);
        view.setOnClickListener(v -> studentListener.onStudentClick(view, (Integer) v.getTag()));
        return new StudentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StudentViewHolder viewHolder, final int i) {
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
        private final ImageView avatarImageView;

        private StudentViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.student_item_text);
            avatarImageView = itemView.findViewById(R.id.student_item_avatar);
        }

        private void bind(@NonNull Student student) {
            nameTextView.setText(student.getFirstName().concat(" ").concat(student.getSecondName()));
            avatarImageView.setImageResource(student.getPhoto());
        }

    }

    interface Listener {

        void onStudentClick(View view, int i);

    }

}
