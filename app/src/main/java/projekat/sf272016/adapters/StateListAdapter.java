package projekat.sf272016.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import projekat.sf272016.R;
import projekat.sf272016.activities.ReadPostActivity;
import projekat.sf272016.model.Post;
import projekat.sf272016.model.State;

public class StateListAdapter extends BaseAdapter {

    Context context;
    ArrayList<State> states;

    public StateListAdapter(Context context, ArrayList<State> states){
        this.context = context;
        this.states = states;
    }

    @Override
    public int getCount(){ return states.size(); }

    @Override
    public Object getItem(int position){
        return states.get(position);
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

        titleView.setText(states.get(position).getStatename());

        view.setOnClickListener(new PostsClickListener());
        view.setTag(states.get(position).getAbbreviation());

        return view;
    }

    private class PostsClickListener implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(v.getContext(), ReadPostActivity.class);
            intent.putExtra("stateId", (String)v.getTag());
            v.getContext().startActivity(intent);
        }
    }

}
