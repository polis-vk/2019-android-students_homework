package ru.ok.technopolis.students;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.StudentViewHolder> {

    private final ArrayList<Student> students;
    private Context context;

    StudentAdapter(Context context, ArrayList<Student> students) {
        this.students = students;
        this.context = context;
    }

    @NonNull
    @Override
    public StudentViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.student, viewGroup, false);
        return new StudentViewHolder(view, context, i);
    }

    @Override
    public void onBindViewHolder(@NonNull StudentViewHolder studentViewHolder, int i) {
        Student student = students.get(i);
        studentViewHolder.photo.setImageResource(student.getPhoto());
        studentViewHolder.secondName.setText(students.get(i).getSecondName());
        studentViewHolder.firstName.setText(students.get(i).getFirstName());
        studentViewHolder.index = i;
        studentViewHolder.picID = student.getPhoto();
        studentViewHolder.student = student;
    }

    @Override
    public int getItemCount() {
        return students.size();
    }

    class StudentViewHolder extends RecyclerView.ViewHolder {

        private final ImageView photo;
        private final TextView secondName;
        private final TextView firstName;
        private int picID;
        private int index;
        private View view;
        private ConstraintLayout mainLayout;
        private Context context;
        private Student student;

        private StudentViewHolder(@NonNull View itemView, Context context, int i) {
            super(itemView);
            view = itemView;
            photo = itemView.findViewById(R.id.student__iv_photo);
            this.index = i;
            secondName = itemView.findViewById(R.id.student__tv_second_name);
            firstName = itemView.findViewById(R.id.student__tv_first_name);
            mainLayout = view.findViewById(R.id.constraintLayout);
            this.context = context;
            mainLayout.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    switch (view.getId()) {
                        case R.id.constraintLayout:
                            startInfoActivity(student);
                            break;
                    }
                }
            });
        }

        void startInfoActivity(Student student) {
            Intent intent = new Intent(context, InfoActivity.class);
            intent.putExtra(MainActivity.SECOND_NAME_EXTRA, student.getSecondName());
            intent.putExtra(MainActivity.FIRST_NAME_EXTRA, student.getFirstName());
            intent.putExtra(MainActivity.MALE_EXTRA, student.isMale());
            intent.putExtra(MainActivity.PHOTO_EXTRA, student.getPhoto());
            intent.putExtra(MainActivity.STUDENT_INDEX_EXTRA, this.index);
            intent.putExtra(MainActivity.PHOTO_EXTRA, this.picID);

            ((MainActivity)context).startActivityForResult(intent, 2);
        }
    }
}
