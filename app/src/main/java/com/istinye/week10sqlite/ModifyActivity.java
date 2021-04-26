package com.istinye.week10sqlite;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ModifyActivity extends Activity {

    private EditText nameTextView, surnameTextView;
    private Button modify, delete;

    private long _id;
    private DBManager dbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify);

        setTitle(getString(R.string.modify_record));

        nameTextView = findViewById(R.id.modifyNameEditText);
        surnameTextView = findViewById(R.id.modifySurnameEditText);

        Intent modifyIntent = getIntent();
        String id = modifyIntent.getStringExtra("ID");
        String name = modifyIntent.getStringExtra("NAME");
        String surname = modifyIntent.getStringExtra("SURNAME");

        _id = Long.valueOf(id);

        nameTextView.setText(name);
        surnameTextView.setText(surname);

        dbManager = new DBManager(this);
        dbManager.open();

        modify = findViewById(R.id.modify_button);
        modify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = nameTextView.getText().toString();
                String surname = surnameTextView.getText().toString();
                if (name.isEmpty() || surname.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Name and surname should be entered.", Toast.LENGTH_SHORT).show();
                } else {
                    dbManager.update(_id, name, surname);
                    returnToMain();
                }
            }
        });

        delete = findViewById(R.id.delete_button);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbManager.delete(_id);
                returnToMain();
            }
        });

    }

    private void returnToMain() {
        Intent mainIntent = new Intent(ModifyActivity.this, MainActivity.class);
        startActivity(mainIntent);
    }


    @Override
    protected void onDestroy() {
        dbManager.close();
        super.onDestroy();
    }
}