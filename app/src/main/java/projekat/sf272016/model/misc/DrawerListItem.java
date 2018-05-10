package projekat.sf272016.model.misc;

public class DrawerListItem {
    private String itemTitle;
    private String itemSubtitle;
    private int itemIcon;


    public DrawerListItem(){

    }

    public DrawerListItem(String itemTitle, String itemSubtitle, int itemIcon) {
        this.itemTitle = itemTitle;
        this.itemSubtitle = itemSubtitle;
        this.itemIcon = itemIcon;
    }

    public String getItemTitle() {
        return itemTitle;
    }

    public void setItemTitle(String itemTitle) {
        this.itemTitle = itemTitle;
    }

    public String getItemSubtitle() {
        return itemSubtitle;
    }

    public void setItemSubtitle(String itemSubtitle) {
        this.itemSubtitle = itemSubtitle;
    }

    public int getItemIcon() {
        return itemIcon;
    }

    public void setItemIcon(int itemIcon) {
        this.itemIcon = itemIcon;
    }
}
