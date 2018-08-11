package com.example.zjy10.jb.datatype;

import java.util.HashMap;

public class DataNode {
    public String stringnow;
    public char att;
    public int color;
    public HashMap<Character,DataNode> next;
    public DataNode() {
        next = new HashMap<Character,DataNode>();
    }
}
