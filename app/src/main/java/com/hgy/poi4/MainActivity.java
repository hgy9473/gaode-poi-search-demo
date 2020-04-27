package com.hgy.poi4;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.services.help.Tip;
import com.amap.placesearch.InputTipsActivity;
import com.amap.placesearch.util.Constants;

public class MainActivity extends FragmentActivity implements OnClickListener {
    private TextView mKeywordsTextView;
    private ImageView mCleanKeyWords;

    public static final int REQUEST_CODE = 100;
    public static final int RESULT_CODE_INPUTTIPS = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mCleanKeyWords = (ImageView)findViewById(R.id.clean_keywords);
        mCleanKeyWords.setOnClickListener(this);
        init();
    }

    /**
     * 初始化AMap对象
     */
    private void init() {
        mKeywordsTextView = (TextView) findViewById(R.id.main_keywords);
        mKeywordsTextView.setOnClickListener(this);
    }


    /**
     * 输入提示activity选择结果后的处理逻辑
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_CODE_INPUTTIPS && data
                != null) {
            Tip tip = data.getParcelableExtra(Constants.EXTRA_TIP);

            Log.d("hgy", "onActivityResult: RESULT_CODE_INPUTTIPS->" + Constants.EXTRA_TIP
                    +"->"+ tip.toString() + "," + tip.getPoint());
            Toast.makeText(MainActivity.this, tip.getPoint() + "," + tip.getName(), Toast.LENGTH_LONG).show();
            mKeywordsTextView.setText(tip.getName());
            if(!tip.getName().equals("")){
                mCleanKeyWords.setVisibility(View.VISIBLE);
            }
        }
    }

    /**
     * 点击事件回调方法
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            // 调用搜索
            case R.id.main_keywords:
                Intent intent = new Intent(this, InputTipsActivity.class);
                startActivityForResult(intent, REQUEST_CODE);
                break;

                // 清理
            case R.id.clean_keywords:
                mKeywordsTextView.setText("");
                mCleanKeyWords.setVisibility(View.GONE);
            default:
                break;
        }
    }
}
