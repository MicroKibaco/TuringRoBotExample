package com.asiainfo.tulingrobot.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.asiainfo.tulingrobot.R;
import com.asiainfo.tulingrobot.bean.ChatMessage;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * 消息适配器
 */
public class ChatMessageAdapter extends BaseAdapter {

    private LayoutInflater mLayoutInflater;
    private List<ChatMessage> data;

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public int getItemViewType(int position) {

        ChatMessage chatMessage = data.get(position);
        if (chatMessage.getType()== ChatMessage.Type.INCOMING){
            return  0 ;
        }
        return 1;
    }

    @Override
    public int getViewTypeCount() {
        return super.getViewTypeCount();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ChatMessage message = data.get(position);
        ViewHolder viewHolder = null;

        //通过ItemType设置不同的布局
        if (convertView == null){
            if (getItemViewType(position)==0){
                convertView =   mLayoutInflater.inflate(R.layout.item_from_msg,parent,false);
                viewHolder  = new ViewHolder();
                viewHolder.mDate = (TextView) convertView.findViewById(R.id.from_msg_date);
                viewHolder.mMsg = (TextView) convertView.findViewById(R.id.from_msg_info);
            }else {
                convertView =   mLayoutInflater.inflate(R.layout.item_to_msg,parent,false);
                viewHolder  = new ViewHolder();
                viewHolder.mDate = (TextView) convertView.findViewById(R.id.to_msg_date);
                viewHolder.mMsg = (TextView) convertView.findViewById(R.id.to_msg_info);
            }

            convertView.setTag(viewHolder);

        }else {
            viewHolder = (ViewHolder) convertView.getTag();

        }

        //设置数据
        SimpleDateFormat format = new SimpleDateFormat("yyy-MMM-dd HH:mm:ss");
        viewHolder.mDate.setText(format.format(message.getDate()));

        viewHolder.mMsg.setText(message.getMsg());

        return convertView;
    }

    public ChatMessageAdapter(Context context, List<ChatMessage> mDatas) {

        mLayoutInflater = LayoutInflater.from(context);
        this.data = mDatas;
    }

    private final  class  ViewHolder {

        TextView mDate;
        TextView mMsg;

    }
}
