package sa.edu.getsocial.Adapters;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import sa.edu.getsocial.Models.Chat;
import sa.edu.getsocial.R;


public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {

    public static  final int MSG_TYPE_LEFT = 0;
    public static  final int MSG_TYPE_RIGHT = 1;

    private Context mContext;
    private List<Chat> mChat;


    public MessageAdapter(Context mContext, List<Chat> mChat){
        this.mChat = mChat;
        this.mContext = mContext;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == MSG_TYPE_RIGHT) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.chat_item_right, parent, false);
            return new ViewHolder(view);
        } else {
            View view = LayoutInflater.from(mContext).inflate(R.layout.chat_item_left, parent, false);
            return new ViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Chat chat = mChat.get(position);

        holder.show_message.setText(chat.getMessage());
        if(chat.getTime()!=null && !chat.getTime().trim().equals("")) {
            holder.time_tv.setText(holder.convertTime(chat.getTime()));
        }

            holder.profile_image.setImageResource(R.drawable.female);

        if (position == mChat.size()-1){
            if (chat.isIsseen()){
                holder.txt_seen.setText("Seen");
            } else {
                holder.txt_seen.setText("Delivered");
            }
        } else {
            holder.txt_seen.setVisibility(View.GONE);
        }

    }

    @Override
    public int getItemCount() {
        return mChat.size();
    }

    public  class ViewHolder extends RecyclerView.ViewHolder{

        public TextView show_message;
        public ImageView profile_image;
        public TextView txt_seen;
        public TextView time_tv;
        public ViewHolder(View itemView) {
            super(itemView);

            show_message = itemView.findViewById(R.id.show_message);
            profile_image = itemView.findViewById(R.id.profile_image);
            txt_seen = itemView.findViewById(R.id.txt_seen);
            time_tv = itemView.findViewById(R.id.time_tv);
        }

        public String convertTime(String time){
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd h:mm a", Locale.US);
            String dateString = formatter.format(new Date(Long.parseLong(time)));
            return dateString;
        }
    }

    @Override
    public int getItemViewType(int position) {
        SharedPreferences editor = mContext.getSharedPreferences("login", MODE_PRIVATE);

        if (mChat.get(position).getSender().equals(editor.getString("ID", ""))){
            return MSG_TYPE_RIGHT;
        } else {
            return MSG_TYPE_LEFT;
        }
    }
}