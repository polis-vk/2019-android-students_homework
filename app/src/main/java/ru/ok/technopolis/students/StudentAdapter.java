package ru.ok.technopolis.students;

import android.net.sip.SipSession;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class StudentAdapter extends Adapter<StudentAdapter.StudentViewHolder> {

    private List<Student> studentList;
    private Listener onStudentClickListener;

    public StudentAdapter(List<Student> studentList, Listener onStudentClickListener) {
        this.studentList = studentList;
        this.onStudentClickListener = onStudentClickListener;
    }

    @NonNull
    @Override
    public StudentViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.student_item, viewGroup, false);
        view.setOnClickListener(v -> onStudentClickListener.onStudentClick(v));
        return new StudentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StudentViewHolder studentViewHolder, int i) {
        Student student = studentList.get(i);
        studentViewHolder.bind(student);
        studentViewHolder.itemView.setTag(student);
    }

    @Override
    public int getItemCount() {
        return studentList.size();
    }

    class StudentViewHolder extends RecyclerView.ViewHolder {

        private final ImageView photo;
        private final TextView name;

        StudentViewHolder(@NonNull View itemView) {
            super(itemView);
            photo = itemView.findViewById(R.id.student_item__photo);
            name = itemView.findViewById(R.id.student_item__name);
        }

        private void bind(@NonNull Student student) {
            photo.setImageResource(student.getPhoto());
            if (student.getSecondName().isEmpty() && student.getFirstName().isEmpty()) {
                name.setText(R.string.student_item_default_name);
            } else {
                name.setText(String.format("%s %s", student.getSecondName(), student.getFirstName()));
            }

        }
    }

    interface Listener {

        void onStudentClick(View view);

    }
}
