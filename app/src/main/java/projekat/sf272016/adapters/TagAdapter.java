package projekat.sf272016.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.ArrayList;

import projekat.sf272016.R;
import projekat.sf272016.model.misc.TagSelected;

public class TagAdapter extends BaseAdapter {

    Context context;
    ArrayList<TagSelected> tags;

    public TagAdapter(Context context, ArrayList<TagSelected> tags){
        this.context = context;
        this.tags = tags;
    }

    @Override
    public int getCount(){ return tags.size(); }

    @Override
    public Object getItem(int position){ return tags.get(position); }

    @Override
    public long getItemId(int position){ return 0; }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        View view;
        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.activity_create_post_tag_item, null);
        }
        else{
            view = convertView;
        }

        CheckBox checkBox = (CheckBox)view.findViewById(R.id.createPostActivityTagItemCheckBox);
        TextView nameView = (TextView)view.findViewById(R.id.createPostActivityTagItemName);

        final TagSelected tagSelected = tags.get(position);

        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    tagSelected.setSelected(true);
                }
                else{
                    tagSelected.setSelected(false);
                }
            }
        });

        nameView.setText(tagSelected.getTag().getName());

        return view;
    }
}
