package ru.ok.technopolis.students;

import android.graphics.Color;
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
    private int selectedItem = RecyclerView.NO_POSITION;

    StudentAdapter(List<Student> students, Listener onStudentClickListener) {
        this.students = students;
        this.onStudentClickListener = onStudentClickListener;
    }

    @NonNull
    @Override
    public StudentAdapter.StudentViewHolder onCreateViewHolder(@NonNull final ViewGroup viewGroup, int i) {
        final View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.student_item, viewGroup, false);
        return new StudentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StudentAdapter.StudentViewHolder viewHolder, int i) {
        Student student = students.get(i);
        viewHolder.bind(student);
        if (selectedItem == i) {
            viewHolder.itemView.setBackgroundColor(Color.LTGRAY);
        } else {
            viewHolder.itemView.setBackgroundColor(Color.WHITE);
        }
        viewHolder.itemView.setTag(student);
    }

    @Override
    public int getItemCount() {
        return students.size();
    }

    void setSelectedItemNoPosition() {
        this.selectedItem = RecyclerView.NO_POSITION;
    }

    int getSelectedItem() {
        return selectedItem;
    }

    final class StudentViewHolder extends RecyclerView.ViewHolder {

        private final TextView nameTextView;
        private final TextView secondNameTextView;
        private final ImageView avatarImageView;

        StudentViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.student_item__name);
            secondNameTextView = itemView.findViewById(R.id.student_item__secondName);
            avatarImageView = itemView.findViewById(R.id.student_item__avatar);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Student student = students.get(getLayoutPosition());
                    onStudentClickListener.onStudentClick(student);
                    notifyItemChanged(selectedItem);
                    selectedItem = getLayoutPosition();
                    notifyItemChanged(selectedItem);
                }
            });
        }

        private void bind(@NonNull Student student) {
            nameTextView.setText(student.getFirstName());
            secondNameTextView.setText(student.getSecondName());
            avatarImageView.setImageResource(student.getPhoto());
        }
    }

    interface Listener {
        void onStudentClick(Student student);
    }
}
