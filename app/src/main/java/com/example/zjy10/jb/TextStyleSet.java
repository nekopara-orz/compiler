package com.example.zjy10.jb;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.example.zjy10.jb.datatype.OptNode;

import java.util.ArrayList;
import java.util.HashMap;

//动态设置文本颜色类
public class TextStyleSet implements Runnable {
    MyEditText myEditText;
    private int cursor;
    ArrayList operationlist;
    Context context;
    Thread sthread;
    String textbefore;
    Activity activity;
    ArrayList<OptNode> optlist1;

    public TextStyleSet(MyEditText myEditText, Activity activity, Context context, ArrayList<OptNode> optlist, String textbefore) {
        this.myEditText = myEditText;
        this.context = context;
        this.optlist1 = optlist;
        this.textbefore = textbefore;
        this.activity = activity;
    }
    public static ArrayList<OptNode> opt(String text,Context context,ArrayList<OptNode> optlist1){
        SearchTree searchTree = new SearchTree(context, "hintcolor");
        searchTree.init_auto();
        for (int i = 0; i < text.length(); i++) {
            char now = text.charAt(i);
            if (i == text.length() - 1 && (now >= '0' && now <= '9' || now >= 'A' && now <= 'Z' || now >= 'a' && now <= 'z')) {
                searchTree.search(now, 1);
                if (searchTree.nstatus.color != 0) {
                    OptNode node = new OptNode();
                    node.bgn = i - searchTree.nstatus.stringnow.length() + 1;
                    node.ed = i;
                    node.color = searchTree.nstatus.color;
                    optlist1.add(node);
                    continue;
                }
            }
            if (now < '0' || now > '9' && now < 'A' || now > 'Z' && now < 'a' || now > 'z') {

                if (searchTree.nstatus.color != 0) {
                    Log.e("mmmmm", "thread");
                    int color = searchTree.nstatus.color;
                    int bgn = i - searchTree.nstatus.stringnow.length();
                    OptNode node = new OptNode();
                    node.bgn = bgn;
                    node.ed = i - 1;
                    node.color = color;
                    optlist1.add(node);
                }
                searchTree.init_auto();
                continue;
            }
            searchTree.search(now, 1);
        }
        return optlist1;
    }
    @Override
    public void run() {

        //    Log.e("inttt","thread");
        while (true) {


            while (textbefore.equals(myEditText.getText().toString())) {
                try {
                    Thread.sleep(1);//直到文本发生变化继续执行
                    Log.e("sleepnow", "sssssssss");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            if (optlist1 != null) optlist1.clear();
            final String text = myEditText.getText().toString();
            textbefore = text;
            //这里之后要改成多种创建方式，实现输入的函数也能识别。
            optlist1 = opt(text,context,optlist1);


            if (optlist1 != null)
                for (OptNode n : optlist1) {
                    Log.e("tsts", optlist1.size() + "");
                    Log.e("tsts", n.bgn + "|" + n.ed + "|" + n.color);
                }

            myEditText.requestFocus();


            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Log.e("main tttttt", optlist1.size() + "");

                    if (optlist1.size() > 0) {
                        Log.e("main tttttt", text);

                        SpannableString span = new SpannableString(text);
                        for (OptNode node : optlist1) {
                            span.setSpan(new ForegroundColorSpan(node.color), node.bgn, node.ed + 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                        }
                        cursor = myEditText.getSelectionStart();
                        Log.e("cursourrrrrr2", "" + cursor);
                        myEditText.setText(span);
                        myEditText.setSelection(cursor);//使光标指向之前的位置

//                        Toast.makeText(context, "s", Toast.LENGTH_LONG).show();
                    }
                }
            });
        }

    }
}
