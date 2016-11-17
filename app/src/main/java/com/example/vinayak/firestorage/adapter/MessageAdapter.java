package com.example.vinayak.firestorage.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.vinayak.firestorage.model.Message;
import com.example.vinayak.firestorage.R;
import com.google.firebase.auth.FirebaseAuth;

import org.ocpsoft.prettytime.PrettyTime;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by Vinayak on 11/17/2016.
 */
public class MessageAdapter extends ArrayAdapter<Message> {

    List<Message> mData;
    Context mContext;
    int mResource;
    private FirebaseAuth mAuth;

    public MessageAdapter(Context context, int resource, List<Message> objects) {
        super(context, resource, objects);
        this.mContext = context;
        this.mData = objects;
        this.mResource = resource;
        this.mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(mResource,parent,false);
        }

        TextView msgText = (TextView) convertView.findViewById(R.id.textViewMsgText);
        TextView msgDate = (TextView) convertView.findViewById(R.id.textViewDate);

        Message message = mData.get(position);
        //Log.d("demo", "In adapter " + mAuth.getCurrentUser().getUid() + " " + message.toString());
        if(!mAuth.getCurrentUser().getUid().equals(message.getReceiverId())) {
            msgText.setText("You: " + message.getMsgText());
        } else {
            msgText.setText(message.getMsgText());
        }
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd-hh.mm.ss");
        PrettyTime prettyTime = new PrettyTime(new Locale("DEFAULT"));
        try {
            Date date = formatter.parse(message.getMsgDate());
            msgDate.setText(prettyTime.format(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return convertView;
    }
}
