package projekat.sf272016.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import projekat.sf272016.R;
import projekat.sf272016.activities.ReadPostActivity;
import projekat.sf272016.model.Post;

public class PostListAdapter extends BaseAdapter {
    Context context;
    ArrayList<Post> posts;

    public PostListAdapter(Context context, ArrayList<Post> posts){
        this.context = context;
        this.posts = posts;
    }

    @Override
    public int getCount(){ return posts.size(); }

    @Override
    public Object getItem(int position){
        return posts.get(position);
    }

    @Override
    public long getItemId(int position) {
        return posts.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        View view;

        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.activity_posts_post_item, null);
        }
        else{
            view = convertView;
        }

        ImageView iconView = (ImageView) view.findViewById(R.id.activity_posts_post_item_image);
        TextView titleView = (TextView) view.findViewById(R.id.activity_posts_post_item_title);
        TextView descriptionView = (TextView) view.findViewById(R.id.activity_posts_post_item_description);
        TextView likesView = (TextView) view.findViewById(R.id.activity_posts_post_item_likes);
        TextView dislikesView = (TextView) view.findViewById(R.id.activity_posts_post_item_dislikes);
        TextView dateView = (TextView) view.findViewById(R.id.activity_posts_post_item_date);

        Post post = posts.get(position);

        if(post.getPhoto() != null) {
            iconView.setImageBitmap(post.getPhoto());
        }
        else {
            iconView.setImageResource(R.drawable.ic_insert_photo_black_24dp);
        }
        titleView.setText(post.getTitle());
        descriptionView.setText(post.getDescription());
        likesView.setText(((Integer)post.getLikes()).toString());
        dislikesView.setText(((Integer)post.getDislikes()).toString());
        dateView.setText(new SimpleDateFormat("dd.MM.yyyy.").format(post.getDate()));

        view.setOnClickListener(new PostsClickListener());
        view.setTag(post.getId());

        return view;
    }

    private class PostsClickListener implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(v.getContext(), ReadPostActivity.class);
            intent.putExtra("postId", (Integer)v.getTag());
            v.getContext().startActivity(intent);
        }
    }
}
