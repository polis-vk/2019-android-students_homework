package ru.ok.technopolis.students;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.List;

import static ru.ok.technopolis.students.MainActivity.selectedPosition;


public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.StudentViewHolder> {

    private final List<Student> list;
    private final Listener onStudentClickListener;

    public StudentAdapter(List<Student> list, Listener onStudentClickListener) {
        this.list = list;
        this.onStudentClickListener = onStudentClickListener;
    }

    @NonNull
    @Override
    public StudentViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.student_item, viewGroup, false);
        view.setOnClickListener(v -> {
            onStudentClickListener.onStudentClick((Student) v.getTag());
            notifyDataSetChanged();
        });
        return new StudentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StudentViewHolder viewHolder, int i) {
        Student student = list.get(i);
        viewHolder.bind(student);
        viewHolder.itemView.setTag(student);
        viewHolder.itemView.setSelected(selectedPosition == i);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static final class StudentViewHolder extends RecyclerView.ViewHolder {

        private final ImageView avatarImageView;
        private final TextView nameTextView;

        public StudentViewHolder(@NonNull View view) {
            super(view);
            avatarImageView = view.findViewById(R.id.student_item__iv_avatar);
            nameTextView = view.findViewById(R.id.student_item__tv_name);
        }

        private void bind(@NonNull Student student) {
            avatarImageView.setImageResource(student.getPhoto());
            String name = student.getFirstName() + ' ' + student.getSecondName();
            nameTextView.setText(name);
        }
    }

    interface Listener {
        void onStudentClick(Student student);
    }
}
