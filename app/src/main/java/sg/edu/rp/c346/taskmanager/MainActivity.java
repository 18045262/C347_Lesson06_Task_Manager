package sg.edu.rp.c346.taskmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ListView lvTask;
    Button btnAdd;

    ArrayList<Task> al;
    ArrayAdapter<Task> ca;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lvTask = findViewById(R.id.lvTask);
        btnAdd = findViewById(R.id.btnAdd);

        DBHelper db = new DBHelper(MainActivity.this);
        al = db.getAllTasks();
        db.close();

        ca = new TaskAdapter(MainActivity.this, R.layout.row,al);
        lvTask.setAdapter(ca);

        lvTask.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Task n = al.get(position);
                Intent i = new Intent(MainActivity.this, AddActivity.class);
                i.putExtra("task", n);
                startActivityForResult(i,9);
                lvTask.deferNotifyDataSetChanged();
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              Intent i = new Intent(MainActivity.this,AddActivity.class);
              startActivity(i);
            }
        });

    }



}
