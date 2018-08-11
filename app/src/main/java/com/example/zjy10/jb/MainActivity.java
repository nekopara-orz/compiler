package com.example.zjy10.jb;


import android.app.Activity;

import android.os.Bundle;

import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;

import com.example.zjy10.jb.datatype.DisplayNode;
import com.example.zjy10.jb.datatype.OptNode;

import java.util.ArrayList;
import java.util.Iterator;

public class MainActivity extends Activity {
    Thread stheard;
    String textbefore = "";
    ArrayList<OptNode> optlist;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        optlist = new ArrayList<>();
        initxmlstyle();//设置样式表
        initview();//初始化view
    }
    public void  initxmlstyle(){
        InitHintcolor initHintcolor = new InitHintcolor(this);
        initHintcolor.initcolor();
    }

    public void initview(){
        View view = LayoutInflater.from(this).inflate(R.layout.layout,null);

        //final ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, data);
        ArrayList<DisplayNode> list = new ArrayList<>();
        final MyAdapter adapter = new MyAdapter(this,list);
        final MyEditText myEditText = findViewById(R.id.text);
        myEditText.setAdapter(adapter);
        //设置listview相关属性

        ListView listView = view.findViewById(R.id.showlist);
        listView.setAdapter(adapter);
        TextStyleSet ts = new TextStyleSet(myEditText,this,this,optlist,textbefore);
        stheard = new Thread(ts);
        stheard.start();
        final ArrayList<OptNode> optlist = new ArrayList<>();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                TextView tv = view.findViewById(R.id.showtext);
                Log.e("inmain",tv.getText().toString());

                String mid = tv.getText().toString();
                String left,right;

                String text = myEditText.getText().toString();

                int pos = myEditText.getSelectionStart();
                int beg = myEditText.findbegin(pos,text);
                int end = myEditText.findend(pos,text);
                if (beg - 1 < 0){
                    left = "";
                }else {
                    left = text.substring(0,beg);
                }

                if (end>text.length()){
                    right = "";
                }else {
                    right = text.substring(end,text.length());
                }
                String newText = left + mid + right;

                //防止如何点击提示后，因为此时已经输入完成，所以文本并没有发生改变，未出现文本改变的情况下，整个文本不变色
                if (optlist != null) optlist.clear();
                TextStyleSet.opt(newText,MainActivity.this,optlist);
                SpannableString span = new SpannableString(newText);
                for (OptNode node : optlist) {
                    span.setSpan(new ForegroundColorSpan(node.color), node.bgn, node.ed + 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                }
                myEditText.setText(span);



                adapter.update(new ArrayList<DisplayNode>());
                //点击时edittext失去焦点了
                myEditText.requestFocus();
                myEditText.setSelection(beg + mid.length());
                Log.e("cursourrrrrrget","" + myEditText.getSelectionStart());
                //实现将光标放置在正确的地方
                Log.e("cursourrrrrr","" + (beg + mid.length()));


            }
        });


        Log.e("111111111","wochulaile");
        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(
                400, 400);
        lp.gravity = Gravity.BOTTOM;
        this.addContentView(listView,lp);
    }

}
