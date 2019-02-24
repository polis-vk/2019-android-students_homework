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

    public StudentAdapter(List<Student> studentList, Listener listener) {
        this.OnStudentClickListener =listener;
        this.studentList = studentList;
    }

    @NonNull
    @Override
    public StudentViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.student_item,viewGroup,false);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OnStudentClickListener.onStudentClick((Student) v.getTag());
            }
        });
        return new StudentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StudentViewHolder viewHolder, int i) {
        Student student = studentList.get(i);
        viewHolder.bind(student);
        viewHolder.itemView.setTag(student);

    }

    @Override
    public int getItemCount() {
        return studentList.size();
    }

    public static final class StudentViewHolder extends RecyclerView.ViewHolder{

        public final TextView fullNameTextView;
        public final ImageView avatarImageView;

        public StudentViewHolder(@NonNull View itemView) {
            super(itemView);
            fullNameTextView=itemView.findViewById(R.id.textView_studentItem);
            avatarImageView=itemView.findViewById(R.id.imageView_studentAvatarItem);
        }

        public void bind(@NonNull Student student){
            StringBuilder stringBuilder=new StringBuilder();
            stringBuilder.append(student.getFirstName());
            stringBuilder.append(" ");
            stringBuilder.append(student.getSecondName());
            fullNameTextView.setText(stringBuilder.toString());
            avatarImageView.setImageResource(student.getPhoto());
        }
    }

    interface Listener{
        void onStudentClick(Student student);
    }

}
