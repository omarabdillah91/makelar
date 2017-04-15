package model;

/**
 * Created by omaabdillah on 4/2/17.
 */

public class TransactionHistory {
    public int post_id;
    public String transaction_date;
    public int user_id;
    public int qty;

    public TransactionHistory(int post_id, String transaction_date, int user_id, int qty) {
        this.post_id = post_id;
        this.transaction_date = transaction_date;
        this.user_id = user_id;
        this.qty = qty;
    }

    public int getPost_id() {
        return post_id;
    }

    public void setPost_id(int post_id) {
        this.post_id = post_id;
    }

    public String getTransaction_date() {
        return transaction_date;
    }

    public void setTransaction_date(String transaction_date) {
        this.transaction_date = transaction_date;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }
}
