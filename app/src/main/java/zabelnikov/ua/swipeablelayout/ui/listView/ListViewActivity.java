package zabelnikov.ua.swipeablelayout.ui.listView;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import zabelnikov.ua.swipeablelayout.R;
import zabelnikov.ua.swipeablelayout.ui.listView.adapter.SwipeableAdapter;

public class ListViewActivity extends AppCompatActivity {

    private ListView listView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listview);

        listView = (ListView) findViewById(R.id.listView);

        List<String> textList = new ArrayList<>();
        textList.add("Test1");
        textList.add("Test2");
        textList.add("Test3");

        SwipeableAdapter adapter = new SwipeableAdapter(this, textList);
        listView.setAdapter(adapter);
    }
}
