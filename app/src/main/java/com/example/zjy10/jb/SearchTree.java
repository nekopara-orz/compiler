package com.example.zjy10.jb;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.zjy10.jb.datatype.DataNode;
import com.example.zjy10.jb.datatype.DisplayNode;

import java.util.ArrayList;
import java.util.Map;
/**
 * 对于下一个版本实现构造函数有多种类型，可以动过一个列表构造，也可以通过xml构造字典树。
 */

public class SearchTree {
    private ArrayList<DisplayNode> list;
    private Context context;
    private Map<String, ?> hashMap;
    public DataNode HeadNode;//字典树当前节点
    public DataNode nstatus;//自动机当前状态节点
    //创建字典树

    public ArrayList<DisplayNode> getList() {
        return list;
    }

    private void builtTree(String string, int color){
      //  Log.e("ssssssss",string);
      //  Log.e("ssssssss",string.length()+"");
        DataNode node = HeadNode;
        node.stringnow = "";
        for(int i = 0; i < string.length(); i++){
            char now = string.charAt(i);
            node.color = 0;
            if (!node.next.isEmpty() && node.next.containsKey(now)){
                node = node.next.get(now);
                if (i == string.length() - 1){
                    node.color = color;
                }
            }else {
              //  Log.e("ssssssss2",now+"");
             //   Log.e("ssssssss2","ssssssss");
                DataNode newnode = new DataNode();
                newnode.stringnow = node.stringnow + now;
                node.next.put(now,newnode);
                node = newnode;
                if (i == string.length() - 1){
                    node.color = color;
                }
            }
        }
    }



    public SearchTree(Context context,String wtext) {
        this.context = context;
       SharedPreferences sp =  context.getSharedPreferences(wtext,Context.MODE_PRIVATE);
       hashMap = sp.getAll();
       HeadNode = new DataNode();
        for (String key : hashMap.keySet()) {
            //System.out.println("key= "+key+" and value= "+hashMap.get(key));
            builtTree(key,Integer.parseInt(hashMap.get(key).toString()));
        }
    }
    public void init_auto(){
        nstatus = HeadNode;
        list = new ArrayList<DisplayNode>();
        list.clear();
    }
    public void dfsall(DataNode status){
        Log.e("indfs",status.stringnow);
        if (status.color != 0){
            DisplayNode node = new DisplayNode();
            node.name = status.stringnow;
            node.color = status.color;
            list.add(node);
        }
        for (char key : status.next.keySet()) {
            //System.out.println("key= "+key+" and value= "+hashMap.get(key));
            dfsall(status.next.get(key));
        }
    }
    public void search(char c){
        if (nstatus.next.containsKey(c)){
            list.clear();
            nstatus = nstatus.next.get(c);
            Log.e("insearch", nstatus.stringnow);
            dfsall(nstatus);
        }else {
            init_auto();
        }
    }
    //简单搜索
    public void search(char c,int i){
        if (nstatus.next.containsKey(c)){
            nstatus = nstatus.next.get(c);
            Log.e("insearch", nstatus.stringnow);
        }else {
            init_auto();
            return;
        }
    }
}
