package projekat.sf272016.model.misc;

public class DrawerListItem {
    private String itemTitle;
    private String itemSubtitle;
    private int itemIcon;
    private Class<?> cls;


    public DrawerListItem(){

    }

    public DrawerListItem(String itemTitle, String itemSubtitle, int itemIcon, Class<?> cls) {
        this.itemTitle = itemTitle;
        this.itemSubtitle = itemSubtitle;
        this.itemIcon = itemIcon;
        this.cls = cls;
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

    public Class<?> getCls() {
        return cls;
    }

    public void setCls(Class<?> cls) {
        this.cls = cls;
    }
}
