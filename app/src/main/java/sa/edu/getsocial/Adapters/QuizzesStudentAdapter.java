package sa.edu.getsocial.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import sa.edu.getsocial.MessageActivity;
import sa.edu.getsocial.Models.QuizModel;
import sa.edu.getsocial.R;
import sa.edu.getsocial.Student.QuestionsActivity;


public class QuizzesStudentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<QuizModel> list;
    Context context;

    public QuizzesStudentAdapter(Context context, List<QuizModel> List1) {
        this.context = context;
        this.list = List1;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new Holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.card_ques, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final Holder holder1 = (Holder) holder;

        final QuizModel model = list.get(position);
        holder1.name.setText((position+1)+" "+model.getCourse_name() );

        holder1.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, QuestionsActivity.class);
                intent.putExtra("id", model.getId());
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
        TextView name;

        public Holder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.name);

        }
    }
}