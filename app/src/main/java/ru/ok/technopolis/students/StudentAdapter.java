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
    private static final int SELECTED_COLOR = Color.parseColor("#F0F0F0");
    private int selectedPosition;

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
                selectedPosition = ((RecyclerView.LayoutParams) v.getLayoutParams()).getViewLayoutPosition();
                notifyDataSetChanged();
            }
        });
        return new StudentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StudentViewHolder holder, final int position) {
        Student student = students.get(position);
        holder.bind(student);
        holder.itemView.setTag(student);
        selectionSetup(holder);
    }

    @Override
    public int getItemCount() {
        return students.size();
    }

    private void selectionSetup(StudentViewHolder holder) {
        if (selectedPosition == holder.getLayoutPosition()) {
            holder.itemView.setBackgroundColor(SELECTED_COLOR);
        } else {
            holder.itemView.setBackgroundColor(Color.TRANSPARENT);
        }
    }

    public void resetSelectionPos() {
        selectedPosition = RecyclerView.NO_POSITION;
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
