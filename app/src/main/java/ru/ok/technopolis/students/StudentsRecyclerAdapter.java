package ru.ok.technopolis.students;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class StudentsRecyclerAdapter extends RecyclerView.Adapter<StudentsRecyclerAdapter.StudentViewHolder> {

    public interface OnActionInRecyclerViewListener {
        void onClearFields();

        void onActionStudent(String fName, String sName, boolean gender, int photo);
    }

    public static final int NO_ACTION_STUDENT = -1;

    private Context context;
    private OnActionInRecyclerViewListener onActionInRecyclerViewListener;

    private List<Student> studentList = new ArrayList<>();
    private int actionStudent = NO_ACTION_STUDENT;

    //TODO сделать разделители

    public StudentsRecyclerAdapter(Context context) {
        this.context = context;
        this.onActionInRecyclerViewListener = (OnActionInRecyclerViewListener) context;
    }

    public void saveStudent(Student newStudent) {
        if (actionStudent == NO_ACTION_STUDENT) {
            studentList.add(newStudent);
            newStudent.setActive(true);
            notifyItemInserted(studentList.size() - 1);
            actionStudent = studentList.size() - 1;
        } else {
            Student student = studentList.get(actionStudent);
            student.setFirstName(newStudent.getFirstName());
            student.setSecondName(newStudent.getSecondName());
            student.setMaleGender(newStudent.isMaleGender());
            student.setPhoto(newStudent.getPhoto());
            notifyItemChanged(actionStudent);
        }
    }

    public void deleteStudent() {
        if (actionStudent != NO_ACTION_STUDENT) {
            studentList.remove(actionStudent);
            notifyItemRemoved(actionStudent);
            actionStudent = NO_ACTION_STUDENT;
            onActionInRecyclerViewListener.onClearFields();
        } else {
            onActionInRecyclerViewListener.onClearFields();
        }
    }

    public void setActionStudent(int actionStudent) {
        if (this.actionStudent != NO_ACTION_STUDENT)
            studentList.get(this.actionStudent).setActive(false);
        this.actionStudent = actionStudent;
        if (this.actionStudent != NO_ACTION_STUDENT)
            studentList.get(this.actionStudent).setActive(true);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public StudentViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.recycler_item, viewGroup, false);
        return new StudentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StudentViewHolder studentViewHolder, int position) {
        studentViewHolder.bind(studentList.get(position));
    }

    @Override
    public int getItemCount() {
        return studentList.size();
    }

    class StudentViewHolder extends RecyclerView.ViewHolder {

        private ImageView avatar;
        private TextView name;
        private LinearLayout layout;

        StudentViewHolder(@NonNull View itemView) {
            super(itemView);
            layout = itemView.findViewById(R.id.recycler_item__main_layout);
            avatar = itemView.findViewById(R.id.recycler_item__image);
            name = itemView.findViewById(R.id.recycler_item__text);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (actionStudent != NO_ACTION_STUDENT)
                        studentList.get(actionStudent).setActive(false);
                    actionStudent = getAdapterPosition();
                    Student student = studentList.get(getAdapterPosition());
                    student.setActive(true);
                    onActionInRecyclerViewListener.onActionStudent(
                            student.getFirstName(),
                            student.getSecondName(),
                            student.isMaleGender(),
                            student.getPhoto()
                    );
                    notifyDataSetChanged();
                }
            });
        }

        private void bind(Student student) {
            if (student.isActive()) {
                layout.setBackgroundColor(context.getResources().getColor(R.color.colorActive));
            } else {
                layout.setBackgroundColor(context.getResources().getColor(R.color.colorNotActive));
            }
            avatar.setImageDrawable(context.getResources().getDrawable(student.getPhoto()));
            String fsName = student.getFirstName() +
                    " " +
                    student.getSecondName();
            name.setText(fsName);
        }
    }
}
