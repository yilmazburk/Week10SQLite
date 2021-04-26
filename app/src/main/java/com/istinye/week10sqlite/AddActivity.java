package com.istinye.week10sqlite;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddActivity extends Activity {

    private EditText nameEditText;
    private EditText surnameEditText;
    private Button addButton;

    private DBManager dbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        setTitle(getString(R.string.add_activity_title));

        nameEditText = (EditText) findViewById(R.id.nameEditText);
        surnameEditText = (EditText) findViewById(R.id.surnameEditText);
        addButton = (Button) findViewById(R.id.add_button) ;

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (dbManager != null) {
                    String name = nameEditText.getText().toString();
                    String surname = surnameEditText.getText().toString();
                    if (name.isEmpty() || surname.isEmpty()) {
                        Toast.makeText(getApplicationContext(), "Name and surname should be entered.", Toast.LENGTH_SHORT).show();
                    } else {
                        dbManager.insert(name, surname);

                        Intent mainIntent = new Intent(AddActivity.this, MainActivity.class);
                        startActivity(mainIntent);
                    }
                }
            }
        });

        dbManager = new DBManager(this);
        dbManager.open();
    }

    @Override
    protected void onDestroy() {
        dbManager.close();
        super.onDestroy();
    }
}