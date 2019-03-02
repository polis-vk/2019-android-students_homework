package ru.ok.technopolis.students;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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

    public StudentAdapter(Context context, ArrayList<Student> students) {
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
        studentViewHolder.photo.setImageResource(student.photo);
        studentViewHolder.second_name.setText(students.get(i).secondName);
        studentViewHolder.first_name.setText(students.get(i).firstName);
        studentViewHolder.index = i;
        studentViewHolder.pic_id = student.photo;
        studentViewHolder.student = student;
    }

    @Override
    public int getItemCount() {
        return students.size();
    }

    class StudentViewHolder extends RecyclerView.ViewHolder {

        private final ImageView photo;
        private final TextView second_name;
        private final TextView first_name;
        private int pic_id;
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
            second_name = itemView.findViewById(R.id.student__tv_second_name);
            first_name = itemView.findViewById(R.id.student__tv_first_name);
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

        public void startInfoActivity(Student student) {
            Intent intent = new Intent(context, InfoActivity.class);
            intent.putExtra("second_name", student.secondName);
            intent.putExtra("first_name", student.firstName);
            intent.putExtra("male", student.isMale);
            intent.putExtra("photo", student.photo);
            intent.putExtra("index", this.index);
            intent.putExtra("pic_id", this.pic_id);

            ((MainActivity)context).startActivityForResult(intent, 2);
        }
    }
}
