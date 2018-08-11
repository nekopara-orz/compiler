package com.example.zjy10.jb;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.util.Log;


/*  数据型关键字
(1) char ：声明字符型变量或函数
(2) double ：声明双精度变量或函数
(3) enum ：声明枚举类型
(4) float：声明浮点型变量或函数
(5) int： 声明整型变量或函数
(6) long ：声明长整型变量或函数
(7) short ：声明短整型变量或函数
(8) signed：声明有符号类型变量或函数
(9) struct：声明结构体变量或函数
(10) union：声明联合数据类型
(11) unsigned：声明无符号类型变量或函数
(12) void
**/
/*
(1) for：一种循环语句(可意会不可言传）
(2) do ：循环语句的循环体
(3) while ：循环语句的循环条件
(4) break：跳出当前循环
(5) continue：结束当前循环，开始下一轮循环
B条件语句
(1)if: 条件语句
(2)else ：条件语句否定分支（与 if 连用）
(3)goto：无条件跳转语句
C开关语句
(1)switch :用于开关语句
(2)case：开关语句分支
(3)default：开关语句中的“其他”分支
D
return ：子程序返回语句（可以带参数，也看不带参数）
 */
public class InitHintcolor {
    private  Context context;
    public InitHintcolor(Context context) {
        this.context = context;
    }
    public void initcolor(){
        SharedPreferences sharedPreferences = context.getSharedPreferences("hintcolor",context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        String[] dataword = {"int","double","enum","float","char","long","short","signed","struct","union","unsigned","void"};
        String[] controlword = {"for","do","while","break","continue","if","else","goto","switch","case","default","return"};

        for (int i = 0; i < dataword.length; i++){
            editor.putInt(dataword[i],Color.GREEN);
        }
        for (int i = 0; i < controlword.length; i++){
            editor.putInt(controlword[i],Color.RED);
        }
        editor.commit();
    }
}
