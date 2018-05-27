package projekat.sf272016.adapters;

import android.app.Activity;
import android.content.Context;
import android.preference.PreferenceManager;
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
import projekat.sf272016.remote.Remote;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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

        final Comment comment = comments.get(position);

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


        ImageView deleteButton = (ImageView)view.findViewById(R.id.activity_post_comment_item_delete);
        String loggedInUsername = PreferenceManager.getDefaultSharedPreferences(context).getString("loggedInUserUsername", "");
        if(loggedInUsername.equals(comment.getAuthor().getUsername())){
            deleteButton.setVisibility(View.VISIBLE);
            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Call<Void> call = Remote.commentRemote.deleteComment(comment.getId());
                    call.enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {
                            ((Activity)context).recreate();
                        }
                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {
                            t.printStackTrace();
                        }
                    });
                }
            });
        }
        else{
            deleteButton.setVisibility(View.INVISIBLE);
        }

        final View view2 = view;
        ((ImageView)view.findViewById(R.id.activity_post_comment_item_like_button)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Call<Integer> call = Remote.commentRemote.likeComment(comment.getId());
                call.enqueue(new Callback<Integer>() {
                    @Override
                    public void onResponse(Call<Integer> call, Response<Integer> response) {
                        if(response.code() == 200 && response.body() != null)
                            //((Activity)context).recreate();
                            ((TextView) view2.findViewById(R.id.activity_post_comment_item_likes)).setText(response.body().toString());
                    }
                    @Override
                    public void onFailure(Call<Integer> call, Throwable t) {
                        t.printStackTrace();
                    }
                });
            }
        });

        ((ImageView)view.findViewById(R.id.activity_post_comment_item_dislike_button)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Call<Integer> call = Remote.commentRemote.dislikeComment(comment.getId());
                call.enqueue(new Callback<Integer>() {
                    @Override
                    public void onResponse(Call<Integer> call, Response<Integer> response) {
                        if(response.code() == 200 && response.body() != null)
                            ((TextView) view2.findViewById(R.id.activity_post_comment_item_dislikes)).setText(response.body().toString());
                    }
                    @Override
                    public void onFailure(Call<Integer> call, Throwable t) {
                        t.printStackTrace();
                    }
                });
            }
        });

        return view;
    }

    @Override
    public boolean isEnabled(int position) {
        return false;
    }
}
