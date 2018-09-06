package demo.model.database;


// links to database: links deals to merchant ID.
public class Deals {

    private int id;
    private int merchant_id;
    private String deal_description;
    private int deal_points_cap;
    private String deal_instructions;

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

    public String getDeal_description() {
        return deal_description;
    }

    public void setDeal_description(String deal_description) {
        this.deal_description = deal_description;
    }

    public int getDeal_points_cap() {
        return deal_points_cap;
    }

    public void setDeal_points_cap(int deal_points_cap) {
        this.deal_points_cap = deal_points_cap;
    }

    public String getDeal_instructions() {
        return deal_instructions;
    }

    public void setDeal_instructions(String deal_instructions) {
        this.deal_instructions = deal_instructions;
    }
}
