package ru.ok.technopolis.students;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.ListPopupWindow;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.StudentViewHolder> {


    private final List<Student> students;
    private Context context;

    public StudentAdapter(Context context,List<Student> students) {
        this.students = students;
        this.context = context;
    }

    @NonNull
    @Override
    public StudentViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.student, viewGroup, false);
        return new StudentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StudentViewHolder studentViewHolder, int i) {
        studentViewHolder.second_name.setText(students.get(i).secondName);
        studentViewHolder.first_name.setText(students.get(i).firstName);
    }

    @Override
    public int getItemCount() {
        return students.size();
    }

    static final class StudentViewHolder extends RecyclerView.ViewHolder {

        //        private final ImageView photo;
        public final TextView second_name;
        public final TextView first_name;


        private StudentViewHolder(@NonNull View itemView) {
            super(itemView);
//            photo = itemView.findViewById(R.id.student__iv_photo);
            second_name = itemView.findViewById(R.id.student__tv_second_name);
            first_name = itemView.findViewById(R.id.student__tv_first_name);
        }

/*        private void bind(@NonNull Student student) {
//            photo.setImageResource(student.photo);
//            second_name.setText(student.secondName);
//            first_name.setText(student.firstName);
        }*/
    }
}
