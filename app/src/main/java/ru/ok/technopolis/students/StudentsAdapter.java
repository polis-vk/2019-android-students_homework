package ru.ok.technopolis.students;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class StudentsAdapter extends RecyclerView.Adapter<StudentsAdapter.StudentViewHolder> {

    private final StudentsRepository repository;
    private final View.OnClickListener onClick;

    public StudentsAdapter(StudentsRepository repository, View.OnClickListener onClick) {
        this.repository = repository;
        this.onClick = onClick;
    }

    @NonNull
    @Override
    public StudentViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.student_list_item, viewGroup, false);
        return new StudentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StudentViewHolder viewHolder, int i) {
        viewHolder.bind(repository.get(i));
        viewHolder.itemView.setTag(i);

        Integer active = repository.getActive();
        if (active != null && active.equals(i)) {
            viewHolder.itemView.setBackgroundResource(R.color.colorLightGray);
        } else {
            viewHolder.itemView.setBackgroundResource(R.color.colorWhite);
        }

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = (int) v.getTag();
                repository.setActive(position);
                onClick.onClick(v);
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return repository.size();
    }

    static final class StudentViewHolder extends RecyclerView.ViewHolder {

        private final ImageView photo;
        private final TextView name;

        StudentViewHolder(@NonNull View itemView) {
            super(itemView);
            photo = itemView.findViewById(R.id.students_list_item_photo);
            name = itemView.findViewById(R.id.students_list_item_name);
        }

        @SuppressLint("SetTextI18n")
        private void bind(@NonNull Student student) {
            photo.setImageResource(student.getPhoto());
            name.setText(student.getFirstName() + " " + student.getSecondName());
        }
    }

}
