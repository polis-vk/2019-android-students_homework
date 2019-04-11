package ru.ok.technopolis.students;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.StudentViewHolder> {

    public static final int NO_STUDENT = -1;
    private MainActivity context;
    private List<Student> students = new ArrayList<>();
    private int activeStudent = NO_STUDENT;

    public StudentAdapter(Context context) {
        this.context = (MainActivity) context;
    }

    public void saveStudent(Student student) {
        if (activeStudent == NO_STUDENT) {
            students.add(student);
            activeStudent = students.size() - 1;
            notifyItemInserted(activeStudent);
        } else {
            Student oldStudent = students.get(activeStudent);
            oldStudent.copy(student);
            notifyItemChanged(activeStudent);
        }
    }

    public void deleteStudent() {
        if (activeStudent != NO_STUDENT) {
            students.remove(activeStudent);
            notifyItemRemoved(activeStudent);
            activeStudent = NO_STUDENT;
        }
        context.clearFields();
    }

    public void setActiveStudent(int activeStudent) {
        this.activeStudent = activeStudent;
    }

    @NonNull
    @Override
    public StudentViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater
                .from(viewGroup.getContext())
                .inflate(R.layout.recycler_item, viewGroup, false);
        return new StudentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StudentViewHolder studentViewHolder, int i) {
        studentViewHolder.bind(students.get(i));
    }

    @Override
    public int getItemCount() {
        return students.size();
    }

    public class StudentViewHolder extends RecyclerView.ViewHolder {
        private ImageView face;
        private TextView fullName;

        public StudentViewHolder(@NonNull View itemView) {
            super(itemView);
            face = itemView.findViewById(R.id.recycler_item__image);
            fullName = itemView.findViewById(R.id.recycler_item__text);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    activeStudent = getAdapterPosition();

                    Student student = students.get(getAdapterPosition());
                    context.setActiveStudent(student);
                }
            });
        }

        private void bind(Student student) {
            face.setImageDrawable(context.getResources().getDrawable(student.getPhoto()));
            fullName.setText(student.getFirstName() + " " + student.getSecondName());
        }
    }
}
