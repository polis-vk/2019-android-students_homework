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
    private final Listener listener;

    public StudentAdapter(List<Student> list, Listener listener) {
        this.students = list;
        this.listener = listener;
    }

    @NonNull
    @Override
    public StudentViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.student_item, viewGroup, false);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onStudentClick((Student)v.getTag());
            }
        });
        return new StudentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StudentViewHolder holder, int position) {
        Student student = students.get(position);
        holder.bind(student);
        holder.itemView.setTag(student);
    }

    @Override
    public int getItemCount() {
        return students.size();
    }

    static final class StudentViewHolder extends RecyclerView.ViewHolder{
        private final TextView firstNameTextView;
        private final TextView secondNameTextView;
        private final ImageView photoTextView;


        public StudentViewHolder(@NonNull View itemView) {
            super(itemView);
            firstNameTextView = itemView.findViewById(R.id.firstname);
            secondNameTextView = itemView.findViewById(R.id.secondname);
            photoTextView = itemView.findViewById(R.id.small_photo);
        }

        private void bind(Student student) {
            firstNameTextView.setText(student.getFirstName());
            secondNameTextView.setText(student.getSecondName());
            photoTextView.setImageResource(student.getPhoto());
        }

    }

    interface Listener {
        void onStudentClick(Student student);
    }

}
