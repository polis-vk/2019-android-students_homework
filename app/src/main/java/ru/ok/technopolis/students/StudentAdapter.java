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
    private final List<Student> list;
    private final Listener onStudentClickListener;

    public StudentAdapter(List<Student> list, Listener listener) {
        this.list = list;
        this.onStudentClickListener = listener;
    }

    @Override
    public void onBindViewHolder(@NonNull StudentViewHolder viewHolder, int i) {
        Student student = list.get(i);
        viewHolder.bind(student);
        viewHolder.itemView.setTag(student);
    }

    @NonNull
    @Override
    public StudentViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item, viewGroup, false);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onStudentClickListener.onStudentClick((Student) v.getTag());
            }
        });
        return new StudentViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static final class StudentViewHolder extends RecyclerView.ViewHolder {
        private final ImageView photo;
        private final TextView name;

        public StudentViewHolder(@NonNull View view) {
            super(view);
            photo = view.findViewById(R.id.item__photo);
            name = view.findViewById(R.id.item__text);
        }

        private void bind(@NonNull Student student) {
            photo.setImageResource(StudentList.getPhoto(student.getPhoto(), student.isMaleGender()));
            String name = student.getFirstName() + ' ' + student.getSecondName();
            this.name.setText(name);
        }
    }

    interface Listener {
        void onStudentClick(Student student);
    }
}
