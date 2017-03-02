package com.test.www.myjpush;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashSet;
import java.util.Set;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;

public class MainActivity extends AppCompatActivity {

    private TextView tv_id;
    private EditText mAliasValue,mTagValue;
    private Button mSetAliasValue,mSetTagValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv_id = (TextView) findViewById(R.id.tv_id);
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);

        //推送方式一，通过RegistrationID推送
        String id = JPushInterface.getRegistrationID(MainActivity.this);
        tv_id.setText("RegistrationID的值是：" + id);

        //推送方式二，通过设置别名，接收推送消息
        mAliasValue = (EditText) findViewById(R.id.mAliasValue);
        mSetAliasValue = (Button) findViewById(R.id.mSetAliasValue);
        mSetAliasValue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mValues = mAliasValue.getText().toString().trim();
                if(TextUtils.isEmpty(mValues)){
                    Toast.makeText(MainActivity.this,"请输入别名",Toast.LENGTH_LONG).show();
                    return;
                }

                TagAliasCallback tagAliasCallback = new TagAliasCallback() {
                    @Override
                    public void gotResult(int i, String s, Set<String> set) {
                        if(i == 0){
                            Toast.makeText(MainActivity.this,"设置成功！",Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(MainActivity.this,"设置失败！",Toast.LENGTH_SHORT).show();
                        }
                    }
                };
                JPushInterface.setAlias(MainActivity.this,mValues,tagAliasCallback);
            }
        });

        //设置tag值
        mTagValue = (EditText) findViewById(R.id.mTagValue);
        mSetTagValue = (Button) findViewById(R.id.mSetTagValue);
        mSetTagValue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mValues = mTagValue.getText().toString().trim();
                Set<String> mTags = new HashSet<String>();
                mTags.add(mValues);
                Log.i("===","====:" + mTags.size());
                if(TextUtils.isEmpty(mValues)){
                    Toast.makeText(MainActivity.this,"请输Tag值",Toast.LENGTH_LONG).show();
                    return;
                }

                TagAliasCallback tagAliasCallback = new TagAliasCallback() {
                    @Override
                    public void gotResult(int i, String s, Set<String> set) {
                        if(i == 0){
                            Toast.makeText(MainActivity.this,"设置成功！",Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(MainActivity.this,"设置失败！",Toast.LENGTH_SHORT).show();
                        }
                    }
                };
                JPushInterface.setTags(MainActivity.this,mTags,tagAliasCallback);
            }
        });

        TTT t = new TTT();
        IntentFilter intentFilter = new IntentFilter("www.zhaoonline.com.gg");
        registerReceiver(t,intentFilter);
    }

    class TTT extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            Toast.makeText(MainActivity.this,"点击了通知栏",Toast.LENGTH_LONG).show();
        }
    }
}
