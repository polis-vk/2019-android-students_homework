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

    private int selectedItem = -1;

    public StudentAdapter(List<Student> students, Listener onStudentClickListener) {
        this.students = students;
        this.onStudentClickListener = onStudentClickListener;
    }

    public void handleStudentRemoval() {
        selectedItem--;
    }

    public void handleStudentAddition() {
        selectedItem = students.size() - 1;
    }

    @NonNull
    @Override
    public StudentViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.student_list_item, viewGroup, false);
        return new StudentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StudentViewHolder viewHolder, int i) {
        Student student = students.get(i);
        boolean selected = selectedItem == i;
        viewHolder.bind(student, selected);
        viewHolder.itemView.setTag(student);
    }

    @Override
    public int getItemCount() {
        return students.size();
    }

    interface Listener {
        void onStudentClick(Student student);
    }

    final class StudentViewHolder extends RecyclerView.ViewHolder {

        private final TextView nameTextView;

        private final ImageView photoImageView;

        public StudentViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.student_item__name);
            photoImageView = itemView.findViewById(R.id.student_item__photo);

            itemView.setOnClickListener(v -> {
                selectedItem = getAdapterPosition();
                notifyDataSetChanged();
                onStudentClickListener.onStudentClick((Student) v.getTag());
            });
        }

        private void bind(@NonNull Student student, boolean isSelected) {
            nameTextView.setText(student.getSecondName() + " " + student.getFirstName());
            photoImageView.setImageResource(student.getPhoto());
            if (isSelected) {
                itemView.setPadding(1,1,1,1);
            } else {
                itemView.setPadding(0,0,0,0);
            }
        }
    }
}
