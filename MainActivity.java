package com.example.wangliyong.calculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button bt_0, bt_1, bt_2, bt_3, bt_4, bt_5, bt_6, bt_7, bt_8, bt_9, bt_multiply, bt_divide, bt_add, bt_minus, bt_point, bt_del, bt_equal,bt_left,bt_right, bt_clean,bt_ci,bt_sin,bt_cos,bt_tan,bt_lg,bt_gen;
    TextView text_input ;   //显示输入的表达式或运算结果
    boolean clear_flag=false;
    int point_flag=0;

    private  static final String TEXTVIEW_DATA = "data";    //对应旋转过程中保存text_input中的表达式的extra
    @Override
    public void onSaveInstanceState(Bundle sacedInstance){      //保存text_input的表达式
        super.onSaveInstanceState(sacedInstance);
        sacedInstance.putString(TEXTVIEW_DATA,text_input.getText().toString());
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getSupportActionBar() != null ){     //隐藏标题栏
            getSupportActionBar().hide();
        }

        setContentView(R.layout.calculator_act);
        bt_0 = (Button) findViewById(R.id.bt_0);
        bt_1 = (Button) findViewById(R.id.bt_1);
        bt_2 = (Button) findViewById(R.id.bt_2);
        bt_3 = (Button) findViewById(R.id.bt_3);
        bt_4 = (Button) findViewById(R.id.bt_4);
        bt_5 = (Button) findViewById(R.id.bt_5);
        bt_6 = (Button) findViewById(R.id.bt_6);
        bt_7 = (Button) findViewById(R.id.bt_7);
        bt_8 = (Button) findViewById(R.id.bt_8);
        bt_9 = (Button) findViewById(R.id.bt_9);
        bt_multiply = (Button) findViewById(R.id.bt_multiply);
        bt_divide = (Button) findViewById(R.id.bt_divide);
        bt_add = (Button) findViewById(R.id.bt_add);
        bt_minus = (Button) findViewById(R.id.bt_minus);
        bt_point = (Button) findViewById(R.id.bt_point);
        bt_del = (Button) findViewById(R.id.bt_del);
        bt_equal = (Button) findViewById(R.id.bt_equal);
        bt_clean = (Button) findViewById(R.id.bt_clean);
        bt_right = (Button)findViewById(R.id.bt_right);
        bt_left = (Button)findViewById(R.id.bt_left);
        bt_ci = (Button)findViewById(R.id.bt_ci);
        bt_lg = (Button)findViewById(R.id.bt_lg);
        bt_sin = (Button)findViewById(R.id.bt_sin);
        bt_cos = (Button)findViewById(R.id.bt_cos);
        bt_tan =(Button)findViewById(R.id.bt_tan);
        bt_gen = (Button)findViewById(R.id.bt_gen);

        text_input = (TextView) findViewById(R.id.textView);

        if(savedInstanceState != null ){
            text_input.setText(savedInstanceState.getString(TEXTVIEW_DATA));
        }

        bt_0.setOnClickListener(this);
        bt_1.setOnClickListener(this);
        bt_2.setOnClickListener(this);
        bt_3.setOnClickListener(this);
        bt_4.setOnClickListener(this);
        bt_5.setOnClickListener(this);
        bt_6.setOnClickListener(this);
        bt_7.setOnClickListener(this);
        bt_8.setOnClickListener(this);
        bt_9.setOnClickListener(this);
        bt_minus.setOnClickListener(this);
        bt_multiply.setOnClickListener(this);
        bt_del.setOnClickListener(this);
        bt_divide.setOnClickListener(this);
        bt_point.setOnClickListener(this);
        bt_add.setOnClickListener(this);
        bt_equal.setOnClickListener(this);
        bt_clean.setOnClickListener(this);
        bt_left.setOnClickListener(this);
        bt_right.setOnClickListener(this);
        bt_sin.setOnClickListener(this);
        bt_cos.setOnClickListener(this);
        bt_tan.setOnClickListener(this);
        bt_lg.setOnClickListener(this);
        bt_ci.setOnClickListener(this);
        bt_gen.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        String str = text_input.getText().toString();
        switch (v.getId()) {
            case R.id.bt_0:
            case R.id.bt_1:
            case R.id.bt_2:
            case R.id.bt_3:
            case R.id.bt_4:
            case R.id.bt_5:
            case R.id.bt_6:
            case R.id.bt_7:
            case R.id.bt_8:
            case R.id.bt_9:
                if (clear_flag) {       //如果清零标志为true则删除
                    clear_flag = false;
                    str="";
                    text_input.setText("");
                }
                text_input.setText(str  + ((Button) v).getText() );     //获得输入的数字
                break;
            case R.id.bt_left:
            case R.id.bt_right:
            case R.id.bt_gen:
            case R.id.bt_ci:
            case R.id.bt_lg:
            case R.id.bt_sin:
            case R.id.bt_cos:
            case R.id.bt_tan:
            case R.id.bt_add:
            case R.id.bt_minus:
            case R.id.bt_multiply:
            case R.id.bt_divide:
                try{
                    point_flag = point_flag+1;
                    if (clear_flag) {       //如果清零标志为true则删除
                        clear_flag = false;
                        text_input.setText(str+((Button)v).getText());
                    }
                    if (str != null && ((((Button) v).getText().equals("×"))||(((Button) v).getText().equals("-"))||
                            (((Button) v).getText().equals("+"))||
                            (((Button) v).getText().equals("÷")))&& ("÷".equals(Character.toString(str.charAt(str.length()-1))) ||
                            "×".equals(Character.toString(str.charAt(str.length()-1))) ||
                            "+".equals(Character.toString(str.charAt(str.length()-1))) ||
                            "-".equals(Character.toString(str.charAt(str.length()-1))))){
                        break;
                    }
                    text_input.setText(str  + ((Button) v).getText() );     //获得输入的运算符
                    break;
                }catch (Exception e){
                    str="";
                    text_input.setText("");
                    break;
                }


            case R.id.bt_del:
                if (clear_flag) {       //如果清零标志为true则删除
                    clear_flag = false;
                    str = "";
                    text_input.setText("");
                } else if (str != null && !str.equals("")) {
                    if(".".equals(Character.toString(str.charAt(str.length()-1)))){
                        point_flag = point_flag+1;
                    }
                    text_input.setText(str.substring(0, str.length() - 1));     //删除最后一个字符
                }
                break;

            case R.id.bt_clean:     //清空text_input
                point_flag = 0;
                clear_flag = false;
                str = "";

                text_input.setText("");
                break;

            case R.id.bt_equal:
                point_flag = 0;
                clear_flag = true;
                if("".equals(str)) {
                    str = "";
                    text_input.setText("");
                }else{
                    getResult();    //计算结果
                }

                break;

            case R.id.bt_point:
                if(point_flag>=0){
                    point_flag = point_flag-1;
                    if (clear_flag) {       //如果清零标志为true则删除
                        clear_flag = false;
                        str="";
                        text_input.setText("");
                    }
                    text_input.setText(str  + ((Button) v).getText() );     //获得输入的数字
                    break;
                }
        }
    }

    //运算结果
    private void getResult() {
        clear_flag = true;
        String s = text_input.getText().toString();
        PostfixExpression temp = new PostfixExpression(s);
        try{

                String str = Double.toString(temp.calculate());     //计算text_input中字符串的结果
                //正则表达式
                if(str.indexOf(".")>0) {
                    //去掉多余的0
                    str = str.replaceAll("0+?$","");
                    //去掉整数后面的小数点及0
                    str = str.replaceAll("[.]$","");
                }
                text_input.setText(str);

        }catch (Exception e){
            text_input.setText("错误");
        }
    }


}
