package com.istinye.week10sqlite;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private ListView myListView;
    private LinearLayout noDataLayout;
    private List<String> recordList;

    private DBManager dbManager;
    private SimpleCursorAdapter adapter;

    private final int[] to_viewIDS = new int[] {R.id.studentNoTextView, R.id.studentNameTextView, R.id.studentSurNameTextView};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recordList = new ArrayList<>();
        noDataLayout = findViewById(R.id.noDataView);
        myListView = findViewById(R.id.myListView);
        myListView.setEmptyView(noDataLayout);

        dbManager = new DBManager(this);
        dbManager.open();
        Cursor cursor = dbManager.fetch();

        adapter = new SimpleCursorAdapter(this, R.layout.layout_list_item, cursor, DBManager.columnNames, to_viewIDS,0);
        myListView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                TextView idTextView = (TextView) view.findViewById(R.id.studentNoTextView);
                TextView nameTextView = (TextView) view.findViewById(R.id.studentNameTextView);
                TextView surnameTextView = (TextView) view.findViewById(R.id.studentSurNameTextView);

                String id = idTextView.getText().toString();
                String name = nameTextView.getText().toString();
                String surname = surnameTextView.getText().toString();

                Intent modifyIntent = new Intent(MainActivity.this, ModifyActivity.class);
                modifyIntent.putExtra("ID", id);
                modifyIntent.putExtra("NAME", name);
                modifyIntent.putExtra("SURNAME", surname);

                startActivity(modifyIntent);
            }
        });

    }

    @Override
    protected void onDestroy() {
        dbManager.close();
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.add_record) {
            Intent addRecordIntent = new Intent(this, AddActivity.class);
            startActivity(addRecordIntent);
        }

        return super.onOptionsItemSelected(item);
    }
}



/*myListView.setAdapter(new BaseAdapter() {
        @Override
        public int getCount() {
            if (recordList.size() == 0) {
                myListView.setVisibility(View.GONE);
                noDataLayout.setVisibility(View.VISIBLE);
            } else {
                myListView.setVisibility(View.VISIBLE);
                noDataLayout.setVisibility(View.GONE);
            }
            return recordList.size();
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            return null;
        }
});*/