package sa.edu.getsocial.Adapters;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
 import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import sa.edu.getsocial.MessageActivity;
import sa.edu.getsocial.Models.RequestModel;
import sa.edu.getsocial.R;


public class RequestAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<RequestModel> list;
    Context context;

    public RequestAdapter(Context context, List<RequestModel> List1) {
        this.context = context;
        this.list = List1;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new Holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.card_request, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final Holder holder1 = (Holder) holder;

        final RequestModel model = list.get(position);
        holder1.date.setText(model.getFormattedTime(model.getTime()));
        holder1.title.setText(model.getTitle() + "");
        holder1.name.setText(model.getDetails() + "");
        SharedPreferences editor = context.getSharedPreferences("login", MODE_PRIVATE);
        String Name = editor.getString("Name", "");
        String UID = editor.getString("ID", "");

        if(model.getUID().equals(UID)){
            holder1.communications.setVisibility(View.GONE);
        }else {
            holder1.communications.setVisibility(View.VISIBLE);
        }
        holder1.communications.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, MessageActivity.class);
                intent.putExtra("userid", model.getUID());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }


    public class Holder extends RecyclerView.ViewHolder {
        TextView name, date, title;
        Button communications;

        public Holder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.title);
            name = (TextView) itemView.findViewById(R.id.name);
            date = (TextView) itemView.findViewById(R.id.date);
            communications = (Button) itemView.findViewById(R.id.communications);
        }
    }
}