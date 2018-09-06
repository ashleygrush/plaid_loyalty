package demo.model.database;


// links to database; links Users, Merchants and deals by ID. "lookup table"
public class Loyalty {

    private int id;
    private int merchant_id;
    private int user_id;
    private int deal_id;
    private boolean redeemed;
    private boolean active;
    private int points_collected;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMerchant_id() {
        return merchant_id;
    }

    public void setMerchant_id(int merchant_id) {
        this.merchant_id = merchant_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getDeal_id() {
        return deal_id;
    }

    public void setDeal_id(int deal_id) {
        this.deal_id = deal_id;
    }

    public boolean isRedeemed() {
        return redeemed;
    }

    public void setRedeemed(boolean redeemed) {
        this.redeemed = redeemed;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public int getPoints_collected() {
        return points_collected;
    }

    public void setPoints_collected(int points_collected) {
        this.points_collected = points_collected;
    }
}
