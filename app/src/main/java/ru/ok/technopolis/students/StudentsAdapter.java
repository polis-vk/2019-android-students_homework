package ru.ok.technopolis.students;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class StudentsAdapter extends RecyclerView.Adapter<StudentsAdapter.StudentViewHolder> {

        private final List<Student> students;
        private final Listener onStudentClickListener;

        public StudentsAdapter(List<Student> students, Listener onStudentClickListener) {
            this.students = students;
            this.onStudentClickListener = onStudentClickListener;
        }

        @NonNull
        @Override
        public StudentViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.student_item, viewGroup, false);
            view.setOnClickListener(v -> onStudentClickListener.onStudentClick((Student) v.getTag()));
            return new StudentViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull StudentViewHolder viewHolder, final int i) {
            Student student = students.get(i);
            viewHolder.bind(student);
            viewHolder.itemView.setTag(student);
        }

        @Override
        public int getItemCount() {
            return students.size();
        }

        static final class StudentViewHolder extends RecyclerView.ViewHolder {

            private final TextView nameEditView;
            private final ImageView photoImageView;

            private StudentViewHolder(@NonNull View itemView) {
                super(itemView);
                nameEditView = itemView.findViewById(R.id.student_item_name);
                photoImageView = itemView.findViewById(R.id.student_item_photo);
            }

            private void bind(@NonNull Student student) {
                nameEditView.setText(student.getFirstName() + " " + student.getSecondName());
                photoImageView.setImageResource(student.getPhoto());
            }

        }

        interface Listener {

            void onStudentClick(Student student);

        }


}
