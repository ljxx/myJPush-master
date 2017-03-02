package com.test.www.myjpush;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class TestJpusActivity extends AppCompatActivity {
    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_jpus);
        String mData = getIntent().getStringExtra("Custome_data");
        mTextView = (TextView) findViewById(R.id.mTextView);
        mTextView.setText(mData);
    }
}
