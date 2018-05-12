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
        return 0;
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

        iconView.setImageBitmap(posts.get(position).getPhoto());
        titleView.setText(posts.get(position).getTitle());

        view.setOnClickListener(new PostsClickListener());
        view.setTag(posts.get(position).getId());

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
