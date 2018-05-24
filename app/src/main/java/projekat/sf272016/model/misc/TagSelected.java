package projekat.sf272016.model.misc;

import projekat.sf272016.model.Tag;

public class TagSelected{
    private Tag tag;
    private boolean selected;

    public TagSelected(){

    }


    public Tag getTag() {
        return tag;
    }

    public void setTag(Tag tag) {
        this.tag = tag;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}