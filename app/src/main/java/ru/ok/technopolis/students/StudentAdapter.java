package ru.ok.technopolis.students;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.StudentViewHolder> {

    private final List<Student> students;
    private final Listener onStudentClickListener;
    public static final int SELECTED_COLOR = Color.parseColor("#F0F0F0");
    private View selectedStudent;

    public StudentAdapter(List<Student> students, Listener onStudentClickListener) {
        this.students = students;
        this.onStudentClickListener = onStudentClickListener;
    }

    @NonNull
    @Override
    public StudentViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.student_item, viewGroup, false);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onStudentClickListener.onStudentClick((Student) v.getTag());
                selectionSetup(v);
            }
        });
        return new StudentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StudentViewHolder holder, final int position) {
        Student student = students.get(position);
        holder.bind(student);
        holder.itemView.setTag(student);
    }

    @Override
    public int getItemCount() {
        return students.size();
    }

    public void selectionSetup(View v) {
        if (selectedStudent != null) {
            selectedStudent.setBackgroundColor(Color.TRANSPARENT);
        }
        if (v != null) {
            selectedStudent = v;
            selectedStudent.setBackgroundColor(SELECTED_COLOR);
        }
    }

    interface Listener {

        void onStudentClick(Student student);

    }

    static final class StudentViewHolder extends RecyclerView.ViewHolder {

        private final CircleImageView profileImage;
        private final TextView nameTextView;
        private final TextView familyNameTextView;

        public StudentViewHolder(@NonNull View itemView) {
            super(itemView);
            profileImage = itemView.findViewById(R.id.student_item__img);
            nameTextView = itemView.findViewById(R.id.student_item__name);
            familyNameTextView = itemView.findViewById(R.id.student_item__fname);
        }

        private void bind(@NonNull Student student) {
            nameTextView.setText(student.getFirstName());
            familyNameTextView.setText(student.getSecondName());
            profileImage.setImageResource(student.getPhoto());
        }

    }
}
