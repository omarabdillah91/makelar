package model;

/**
 * Created by omaabdillah on 4/2/17.
 */

public class PostMaster {
    public int post_id;
    public String image_url;
    public String product_name;
    public String roduct_category;
    public int price;
    public int stock;
    public String description;

    public PostMaster(int post_id, String image_url, String product_name, String roduct_category, int price, int stock, String description) {
        this.post_id = post_id;
        this.image_url = image_url;
        this.product_name = product_name;
        this.roduct_category = roduct_category;
        this.price = price;
        this.stock = stock;
        this.description = description;
    }

    public int getPost_id() {
        return post_id;
    }

    public void setPost_id(int post_id) {
        this.post_id = post_id;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getRoduct_category() {
        return roduct_category;
    }

    public void setRoduct_category(String roduct_category) {
        this.roduct_category = roduct_category;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
