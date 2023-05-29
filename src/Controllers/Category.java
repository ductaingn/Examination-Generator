package Controllers;

public class Category {
    private int category_id;
    private String name;
    private String info;
    private int lft;
    private int rgt;

    public Category(int category_id, String name, String info, int lft, int rgt) {
        this.category_id = category_id;
        this.name = name;
        this.info = info;
        this.lft = lft;
        this.rgt = rgt;
    }

    public int getCategory_id() {
        return category_id;
    }

    public String getName() {
        return name;
    }

    public String getInfo() {
        return info;
    }

    public int getLft() {
        return lft;
    }

    public int getRgt() {
        return rgt;
    }
}
