package sg.edu.rp.c346.taskmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;

public class AddActivity extends AppCompatActivity {
        EditText etName,etDesc, etRemind;
        Button btnAdd, btnCancel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        etName = findViewById(R.id.etName);
        etDesc = findViewById(R.id.etDesc);
        etRemind = findViewById(R.id.etRemind);
        btnAdd = findViewById(R.id.btnAddTask);
        btnCancel = findViewById(R.id.btnCancel);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = etName.getText().toString();
                String desc = etDesc.getText().toString();
                int remind = Integer.parseInt(etRemind.getText().toString());

                if (!name.isEmpty() && !desc.isEmpty()) {
                    DBHelper dbhelper = new DBHelper(AddActivity.this);
                    Long inserted_id = dbhelper.insertTask(name, desc);

                    if (inserted_id != -1) {
                        Calendar calendar = Calendar.getInstance();
                        calendar.add(Calendar.SECOND,remind);
                        Toast.makeText(AddActivity.this, "Insert successful",
                                Toast.LENGTH_SHORT).show();

                        Intent i = new Intent(AddActivity.this, TaskNotificationReceiver.class);
                        int reqCode = 12345;
                        i.putExtra("name", name);
                        PendingIntent pendingIntent = PendingIntent.getBroadcast(AddActivity.this,reqCode,
                                i, PendingIntent.FLAG_CANCEL_CURRENT);

                        AlarmManager am = (AlarmManager)getSystemService(Activity.ALARM_SERVICE);
                        am.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
                        finish();

                    }
                    etName.setText("");
                    etDesc.setText("");
                    etRemind.setText("");
                }
                else {
                    Toast.makeText(AddActivity.this, "Content is empty",
                            Toast.LENGTH_SHORT).show();
                }

            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                finish();

            }
        });


    }
}
