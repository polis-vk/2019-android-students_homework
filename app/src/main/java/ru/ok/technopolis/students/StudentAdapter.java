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
    private Student chosenStudent;
    private View chosenStudentView;

    public StudentAdapter(List<Student> students, Listener onStudentClickListener) {
        this.students = students;
        this.onStudentClickListener = onStudentClickListener;
    }

    @NonNull
    @Override
    public StudentViewHolder onCreateViewHolder(@NonNull final ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item, viewGroup, false);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (chosenStudentView != null) chosenStudentView.setSelected(false);
                chosenStudent = (Student) v.getTag();
                onStudentClickListener.onStudentClick(chosenStudent);
                chosenStudentView = v;
                chosenStudentView.setSelected(true);
            }
        });
        return new StudentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StudentViewHolder studentViewHolder, int i) {
        Student student = students.get(i);
        studentViewHolder.bind(student);
        View studentView = studentViewHolder.itemView;
        studentView.setTag(student);
        if (student == chosenStudent) {
            studentView.setSelected(true);
            chosenStudentView = studentView;
        } else {
            studentView.setSelected(false);
        }
    }

    @Override
    public int getItemCount() {
        return students.size();
    }

    public void setChosenStudent(Student student) {
        chosenStudent = student;
        if (chosenStudentView != null) {
            chosenStudentView.setSelected(false);
            chosenStudentView = null;
        }
    }

    static final class StudentViewHolder extends RecyclerView.ViewHolder {
        private final ImageView photoImageView;
        private final TextView nameTextView;

        public StudentViewHolder(@NonNull View itemView) {
            super(itemView);
            photoImageView = itemView.findViewById(R.id.list_item_photo);
            nameTextView = itemView.findViewById(R.id.list_item_name);
        }

        private void bind(@NonNull Student student) {
            photoImageView.setImageResource(student.getPhoto());
            nameTextView.setText(student.getFirstName() + " " + student.getSecondName());
        }
    }

    interface Listener {
        void onStudentClick(Student student);
    }
}
