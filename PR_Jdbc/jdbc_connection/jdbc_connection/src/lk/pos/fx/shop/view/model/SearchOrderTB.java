package lk.pos.fx.shop.view.model;

import java.time.LocalDate;

public class SearchOrderTB {
    private String oid;
    private LocalDate date;
    private String customerId;
    private String name;
    private double total;

    public SearchOrderTB() {
    }

    public SearchOrderTB(String oid, LocalDate date, String customerId, String name, double total) {
        this.setOid(oid);
        this.setDate(date);
        this.setCustomerId(customerId);
        this.setName(name);
        this.setTotal(total);
    }

    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "SearchOrderTB{" +
                "oid='" + oid + '\'' +
                ", date=" + date +
                ", customerId='" + customerId + '\'' +
                ", name='" + name + '\'' +
                ", total=" + total +
                '}';
    }
}
