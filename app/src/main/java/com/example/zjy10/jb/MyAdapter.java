package com.example.zjy10.jb;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.zjy10.jb.datatype.DisplayNode;

import java.util.ArrayList;

public class MyAdapter extends BaseAdapter {
    Context context;

    ArrayList<DisplayNode> list = new ArrayList();//displaynode 类里面是接受到的提示结果。
    public MyAdapter(Context context,ArrayList<DisplayNode> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }


    @Override
    public View getView(int i, View convertView, ViewGroup parent) {


        Log.e("size",list.size()+"");
       // if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.layout_text, parent,false);
//这里暂时未对代码进行优化
//            MyAdapter.ViewHolder vh = new MyAdapter().ViewHolder();
//            vh.imageView = (ImageView) convertView.findViewById(R.id.pic);
//            vh.imageView.setVisibility(View.GONE);
//            vh.tvtittle = (TextView)convertView.findViewById(R.id.tittle);
//            vh.tvdescribe = (TextView)convertView.findViewById(R.id.describe);
//            convertView.setTag(vh);
            TextView view = convertView.findViewById(R.id.showtext);
            String type = list.get(i).name;
            int color = list.get(i).color;
            view.setText(type);
            view.setTextColor(color);
      //  }

        return convertView;
    }

    public void update(ArrayList<DisplayNode> list) {
        this.list = list;
        this.notifyDataSetChanged();
    }

}
