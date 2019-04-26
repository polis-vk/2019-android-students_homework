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
    private View prevStudent;

    public StudentAdapter(List<Student> list, Listener listener) {
        this.students = list;
        this.listener = listener;
        this.prevStudent = null;
    }

    @NonNull
    @Override
    public StudentViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.student_item, viewGroup, false);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onStudentClick((Student)v.getTag());
                if (prevStudent != null) {
                    prevStudent.setBackgroundResource(R.color.activity_color);
                }
                v.setBackgroundResource(R.color.current_student_color);
                prevStudent = v;
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
            firstNameTextView.setText(reflectTextInTextView(student.getFirstName()));
            if (firstNameTextView.getText().toString().equals("")) {
                firstNameTextView.setHint(R.string.default_firstname);
            }
            secondNameTextView.setText(reflectTextInTextView(student.getSecondName()));
            if (secondNameTextView.getText().toString().equals("")) {
                secondNameTextView.setHint(R.string.default_secondname);
            }
            photoTextView.setImageResource(student.getPhoto());
        }

        private String reflectTextInTextView(String text) {
            if (text.length() > 10) {
                return text.substring(0, 9) + ".";
            }
            else {
                return text;
            }
        }

    }

    interface Listener {
        void onStudentClick(Student student);
    }

}
