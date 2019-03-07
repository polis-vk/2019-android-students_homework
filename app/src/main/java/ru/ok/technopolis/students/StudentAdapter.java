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
    private final List<Student> studentList;
    private final Listener OnStudentClickListener;
    private int selectedPos = RecyclerView.NO_POSITION;

    public StudentAdapter(List<Student> studentList, Listener listener) {
        this.OnStudentClickListener = listener;
        this.studentList = studentList;
    }

    public void refreshSelectedPos() {
        selectedPos = RecyclerView.NO_POSITION;
    }

    @NonNull
    @Override
    public StudentViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.student_item, viewGroup, false);
        StudentViewHolder studentViewHolder = new StudentViewHolder(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OnStudentClickListener.onStudentClick((Student) v.getTag());
                notifyItemChanged(selectedPos);
                selectedPos = studentViewHolder.getAdapterPosition();
                notifyItemChanged(selectedPos);
            }
        });
        return studentViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull StudentViewHolder viewHolder, int position) {
        Student student = studentList.get(position);
        viewHolder.bind(student);
        viewHolder.itemView.setTag(student);
        //viewHolder.itemView.setBackgroundColor(selectedPos==position?Color.GREEN:Color.WHITE);
        viewHolder.itemView.setSelected(selectedPos == position);

    }

    @Override
    public int getItemCount() {
        return studentList.size();
    }

    public static final class StudentViewHolder extends RecyclerView.ViewHolder {

        public final TextView fullNameTextView;
        public final ImageView avatarImageView;

        public StudentViewHolder(@NonNull View itemView) {
            super(itemView);
            fullNameTextView = itemView.findViewById(R.id.textView__student_Item);
            avatarImageView = itemView.findViewById(R.id.imageView__student_Avatar_Item);
        }


        public void bind(@NonNull Student student) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(student.getFirstName());
            stringBuilder.append(" ");
            stringBuilder.append(student.getSecondName());
            fullNameTextView.setText(stringBuilder.toString());
            avatarImageView.setImageResource(student.getPhoto());
        }
    }

    interface Listener {
        void onStudentClick(Student student);
    }

}