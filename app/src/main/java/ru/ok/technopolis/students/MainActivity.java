package ru.ok.technopolis.students;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import com.getbase.floatingactionbutton.FloatingActionButton;

import java.util.UUID;

import ru.ok.technopolis.students.adapter.MainActivityAdapter;
import ru.ok.technopolis.students.app.App;
import ru.ok.technopolis.students.bottom_sheet_fragment.StudentBottomSheet;
import ru.ok.technopolis.students.dao.StudentDAO;
import ru.ok.technopolis.students.model.Student;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, StudentBottomSheet.BottomSheetListener {

    private MainActivityAdapter mainActivityAdapter;
    private StudentDAO studentDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        studentDAO = App.getInstance().getStudentDAO();
        setupRecyclerView();
        setupAddButton();
    }

    private void setupRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.activity_main__recycler_view);
        mainActivityAdapter = new MainActivityAdapter(studentDAO.getStudents(), this::onStudentClick);
        recyclerView.setAdapter(mainActivityAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
    }

    private void setupAddButton() {
        FloatingActionButton addButton = findViewById(R.id.activity_main__add_button);
        addButton.setOnClickListener(this);
    }

    private void onStudentClick(Student student) {
        UUID studentId = student.getId();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        StudentBottomSheet.newInstance(studentId).show(transaction, StudentBottomSheet.BOTTOM_SHEET_TAG);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.activity_main__add_button:
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                StudentBottomSheet.newInstance(null).show(transaction, StudentBottomSheet.BOTTOM_SHEET_TAG);
                break;
        }
    }

    @Override
    public void onButtonClicked() {
        mainActivityAdapter.notifyDataSetChanged();
    }
}
