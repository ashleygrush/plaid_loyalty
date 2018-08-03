package demo.model.database;

public class DBSearch {
    Users users;
    Merchants merchants;
    Loyalty loyalty;
    Deals deals;

    public Users getUsers() {
        return users;
    }

    public void setUsers(Users users) {
        this.users = users;
    }

    public Merchants getMerchants() {
        return merchants;
    }

    public void setMerchants(Merchants merchants) {
        this.merchants = merchants;
    }

    public Loyalty getLoyalty() {
        return loyalty;
    }

    public void setLoyalty(Loyalty loyalty) {
        this.loyalty = loyalty;
    }

    public Deals getDeals() {
        return deals;
    }

    public void setDeals(Deals deals) {
        this.deals = deals;
    }
}
