package sa.edu.getsocial.Adapters;

import static android.content.Context.MODE_PRIVATE;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.List;

import sa.edu.getsocial.Models.AnnouncementModel;
import sa.edu.getsocial.R;


public class AnnouncementAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<AnnouncementModel> list;
    Context context;

    public AnnouncementAdapter(Context context, List<AnnouncementModel> List1) {
        this.context = context;
        this.list = List1;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new Holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.card_announcement, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final Holder holder1 = (Holder) holder;

        final AnnouncementModel model = list.get(position);
        holder1.name.setText(model.getAnnouncement() + "");
        holder1.date.setText(model.getFormattedTime(model.getTime()) + "");
        holder1.title.setText(" " + model.getTitle() + "");
        holder1.link.setText(" " + model.getLink() + "");

        SharedPreferences sharedPreferences = context.getSharedPreferences("login", MODE_PRIVATE);
        if (sharedPreferences.getInt("UserType", 0) == 2) {
            holder1.delete.setVisibility(View.VISIBLE);
            holder1.delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog myQuittingDialogBox = new AlertDialog.Builder(context)
                            // set message, title, and icon
                            .setTitle("Delete")
                            .setMessage("Are you sure of Delete?")
                            .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int whichButton) {
                                    //your deleting code
                                    dialog.dismiss();
                                    holder1.dialog1.show();
                                    holder1.mdatabase.child(model.getId())
                                            .removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            holder1.dialog1.dismiss();
                                            Toast.makeText(context, "Deleted", Toast.LENGTH_SHORT).show();
                                            notifyDataSetChanged();

                                        }
                                    });


                                }

                            })
                            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {

                                    dialog.dismiss();

                                }
                            })
                            .create();
                    myQuittingDialogBox.show();
                }
            });

        }else {
            holder1.delete.setVisibility(View.GONE);
        }

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
        TextView name,date,title,link;
        DatabaseReference mdatabase;
        ProgressDialog dialog1;
        Button delete;
        public Holder(View itemView) {
            super(itemView);
            title= (TextView) itemView.findViewById(R.id.title);
            name = (TextView) itemView.findViewById(R.id.name);
            date = (TextView) itemView.findViewById(R.id.date);
            link = (TextView) itemView.findViewById(R.id.link);
            delete = (Button) itemView.findViewById(R.id.delete);
            mdatabase = FirebaseDatabase.getInstance("https://getsocial-3f61c-default-rtdb.firebaseio.com/")
                    .getReference().child("Announcement");

            dialog1 = new ProgressDialog(context);
            dialog1.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            dialog1.setMessage("Plz Wait...");
            dialog1.setIndeterminate(true);
            dialog1.setCanceledOnTouchOutside(false);
        }
    }
}