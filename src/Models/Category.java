package Models;

public class Category {
    private Category parentCategory;
    private String categoryName;
    private String categoryInfo;
    private Integer categoryId;

    public Category(Category parentCategory,String categoryName, String categoryInfo, Integer categoryId) {
        this.parentCategory = parentCategory;
        this.categoryName=categoryName;
        this.categoryInfo=categoryInfo;
        this.categoryId=categoryId;
    }

    public Category getParentCategory() {
        return parentCategory;
    }

    public void setParentCategory(Category parentCategory) {
        this.parentCategory = parentCategory;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryInfo() {
        return categoryInfo;
    }

    public void setCategoryInfo(String categoryInfo) {
        this.categoryInfo = categoryInfo;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }
}
