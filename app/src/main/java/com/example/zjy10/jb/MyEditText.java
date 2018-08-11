package com.example.zjy10.jb;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.Editable;
import android.text.Layout;
import android.text.Selection;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.WindowManager;
import android.widget.EditText;

import com.example.zjy10.jb.datatype.DisplayNode;

import java.util.ArrayList;

public class MyEditText extends EditText implements TextWatcher{

    private ArrayList<DisplayNode> list;
    private SearchTree searchTree;
    private Paint line;
    Context context;
    private int Textsize = 50;
    private int vline_x = 150;
    MyAdapter adapter;
    public void setAdapter(MyAdapter adapter){
        this.adapter = adapter;
    }
    public MyEditText(Context context,AttributeSet as) {

        super(context,as);
        searchTree = new SearchTree(context,"hintcolor");
        this.context = context;
        setFocusable(true);
        line = new Paint();
        line.setColor(Color.GRAY);
        line.setStrokeWidth(2);
        setPadding(vline_x + 5, 0, 0, 0);
        setGravity(Gravity.TOP);

    }
    private int getCurrentCursorLine(EditText editText) {
        int selectionStart = Selection.getSelectionStart(editText.getText());
        Layout layout = editText.getLayout();
        if (selectionStart != -1) {
                return layout.getLineForOffset(selectionStart) + 1;



        }
        return -1;
    }

    private void drawnumber(Canvas canvas){
        if (getText().toString().length() == 0) {
            float y = 0;
            Paint p = new Paint();
            p.setColor(Color.GRAY);
            p.setTextSize(Textsize);
            p.setTextAlign(Paint.Align.RIGHT);
            y = ((1) * getLineHeight()) - (getLineHeight() / 4);
            canvas.drawText(String.valueOf(1), 0 + vline_x - 5, y + 5, p);
            canvas.save();
        }
        if (getText().toString().length() != 0) {
            float y = 0;
            Paint p = new Paint();
            p.setColor(Color.GRAY);
            p.setTextAlign(Paint.Align.RIGHT);
            p.setTextSize(Textsize);
            for (int l = 0; l < getLineCount(); l++) {
                y = ((l + 1) * getLineHeight()) - (getLineHeight() / 4);
                canvas.drawText(String.valueOf(l + 1), 0 + vline_x - 5, y + 5, p);
                canvas.save();
            }
        }
    }

    private void drawseletedline(Canvas canvas){
        int l = getCurrentCursorLine(this);
        int h = getLineHeight();
        Paint paintR = new Paint();
        Color color = new Color();

        paintR.setColor(Color.argb(30,0,0,128));
        WindowManager wm1 = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        int widths = wm1.getDefaultDisplay().getWidth();
        canvas.drawRect((float) vline_x + 5, (float)h*(l - 1),(float)widths - 20, (float)h*l + 5,paintR);
        canvas.save();
    }

    @Override
    protected void onDraw(final Canvas canvas) {
// TODO: Implement this method

        drawseletedline(canvas);//改变当前行的颜色
        drawnumber(canvas);//绘制左侧行数

        int k = getLineHeight();
        int i = getLineCount();
        canvas.drawLine(vline_x, 0, vline_x, getHeight() + (i * k), line);
        //int y = (getLayout().getLineForOffset(getSelectionStart()) + 1) * k;
        //canvas.drawLine(0, y, getWidth(), y, line);
        canvas.save();
        canvas.restore();
        super.onDraw(canvas);
    }

    @Override
    public int getInputType() {
        return 0;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        Log.e("ssss","down");
        return super.onKeyDown(keyCode, event);
    }


    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }



    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        int x = getSelectionStart();
        //不用i的原因是，如果删除更改位置就不存在了，那么就会使下标越界

        Log.e("xxxxxxx",""+x + " ");

        if (charSequence.length() == 0){
            if (adapter != null){
                list = new ArrayList<>();
                adapter.update(list);
            }
            return;
        }

        //Log.e("xxxxxxx",charSequence.charAt(x - 1) + "");
        int rem = -1;
        for (int pos = x - 1;pos >= 0; pos--){
            char c = charSequence.charAt(pos);
            if (c == ' '|| c == '\n'||c == ';'){
                rem = pos + 1;
                break;
            }
            if (pos == 0)
                rem = 0;
        }
         //rem = findbegin(x,charSequence); //与上方的方法等价

        SearchTree searchTree = new SearchTree(context,"hintcolor");
        searchTree.init_auto();

        if(rem != -1) {
            for (int pos = rem; pos <= x - 1; pos++) {
                searchTree.search(charSequence.charAt(pos));
                list = searchTree.getList();
                if (list.size() == 0){ // 日过到当前节点都没有就不用再搜索下去了（搜索下去也会使自动机从新的点开始这时候自动机开始位置就不正确了）
                    break;
                }
            }
            //adapter.notifyDataSetChanged();
            list = searchTree.getList();
            adapter.update(list);

        }
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }

    public int findend(int x, CharSequence charSequence) {
        for (int pos = x;pos < charSequence.length(); pos++){
            char c = charSequence.charAt(pos);
            if (c == ' '|| c == '\n'||c == ';'){
                return pos;
            }
            if (pos == charSequence.length() - 1)
                return charSequence.length();
        }
        return charSequence.length();
    }


    public int findbegin(int x,CharSequence charSequence){
        for (int pos = x - 1;pos >= 0; pos--){
            char c = charSequence.charAt(pos);
            if (c == ' '|| c == '\n'||c == ';'){
                return pos + 1;
            }
            if (pos == 0)
                return 0;
        }
        return 0;
    }
}
