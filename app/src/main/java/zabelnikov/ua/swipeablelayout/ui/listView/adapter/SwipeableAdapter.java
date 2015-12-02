package zabelnikov.ua.swipeablelayout.ui.listView.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import zabelnikov.ua.swipeablelayout.R;

public class SwipeableAdapter extends BaseAdapter {

    private final Context context;
    private final List<String> textList;


    public SwipeableAdapter(Context context, List<String> textList) {
        this.context = context;
        this.textList = textList;
    }

    @Override
    public int getCount() {
        return textList.size();
    }

    @Override
    public Object getItem(int position) {
        return textList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_list_view, null);
        }

        TextView textView = (TextView) convertView.findViewById(R.id.textView);

        textView.setText(textList.get(position));

        return convertView;
    }
}
