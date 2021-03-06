package com.asiainfo.tulingrobot.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.asiainfo.tulingrobot.R;
import com.asiainfo.tulingrobot.adapter.ChatMessageAdapter;
import com.asiainfo.tulingrobot.bean.ChatMessage;
import com.asiainfo.tulingrobot.utils.HttpUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 机器人
 */

public class TulingRoBotActivity extends Activity  {

    private ListView mChatListview;
    private EditText mInputMsg;
    private Button mSendMsg;
    private ChatMessageAdapter mChatMessageAdapter;
    private List<ChatMessage> mDatas;

    protected Handler mHandler  = new Handler(){
        @Override
        public void handleMessage(Message msg) {

            //等待接受子线程数据返回
            ChatMessage fromMessage = (ChatMessage) msg.obj;
            mDatas.add(fromMessage);
            mChatMessageAdapter.notifyDataSetChanged();
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_tuling_ro_bot);
        initView();

        initDatas();

        initListener();

    }

    private void initListener() {
        mSendMsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String toMsg = mInputMsg.getText().toString();

                if (TextUtils.isEmpty(toMsg)){
                    Toast.makeText(TulingRoBotActivity.this,"发送消息不能为空",Toast.LENGTH_SHORT).show();
                    return;

                }

                ChatMessage toMessage = new ChatMessage();
                toMessage.setDate(new Date());
                toMessage.setMsg(toMsg);
                toMessage.setType(ChatMessage.Type.OUTCOMING);
                mDatas.add(toMessage);
                mChatMessageAdapter.notifyDataSetChanged();
                mInputMsg.setText("");

                new Thread(){
                    @Override
                    public void run() {

                        ChatMessage fromMessage = HttpUtils.sendMessage(toMsg);
                        Message m = Message.obtain();
                        m.obj = fromMessage;
                        mHandler.sendMessage(m);

                    }
                }.start();

            }
        });

    }

    private void initDatas() {

        mDatas = new ArrayList<>();
        mChatMessageAdapter = new ChatMessageAdapter(this,mDatas);

        mChatListview.setAdapter(mChatMessageAdapter);

    }

    private void initView() {

        mChatListview = (ListView) findViewById(R.id.mian_listview);
        mInputMsg = (EditText) findViewById(R.id.input_msg);
        mSendMsg = (Button) findViewById(R.id.send_msg);

    }

}

