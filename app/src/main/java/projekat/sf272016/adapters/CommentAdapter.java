package projekat.sf272016.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import projekat.sf272016.R;
import projekat.sf272016.model.Comment;

public class CommentAdapter extends BaseAdapter {
    Context context;
    ArrayList<Comment> comments;

    public CommentAdapter(Context context, ArrayList<Comment> comments) {
        this.context = context;
        this.comments = comments;
    }

    @Override
    public int getCount() { return comments.size(); }

    @Override
    public Object getItem(int position) { return comments.get(position); }

    @Override
    public long getItemId(int position) { return comments.get(position).getId(); }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        View view;
        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.activity_read_post_comment_item, null);
        }
        else{
            view = convertView;
        }

        Comment comment = comments.get(position);

        if(comment.getAuthor().getPhoto() != null){
            ((ImageView)view.findViewById(R.id.activity_post_comment_item_image)).setImageBitmap(comment.getAuthor().getPhoto());
        }
        else{
            ((ImageView)view.findViewById(R.id.activity_post_comment_item_image)).setImageResource(R.drawable.ic_person_black_24dp);
        }
        ((TextView)view.findViewById(R.id.activity_post_comment_item_date)).setText(new SimpleDateFormat("dd.MM.yyyy.").format(comment.getDate()));
        ((TextView)view.findViewById(R.id.activity_post_comment_item_owner)).setText(comment.getAuthor().getUsername());
        ((TextView)view.findViewById(R.id.activity_post_comment_item_title)).setText(comment.getTitle());
        ((TextView)view.findViewById(R.id.activity_post_comment_item_description)).setText(comment.getDescription());
        ((TextView)view.findViewById(R.id.activity_post_comment_item_likes)).setText(((Integer)comment.getLikes()).toString());
        ((TextView)view.findViewById(R.id.activity_post_comment_item_dislikes)).setText(((Integer)comment.getDislikes()).toString());

        return view;
    }
}
