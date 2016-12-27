package motacojo.mbds.fr.entities;

import java.util.List;

/**
 * Created by cojoc on 22/12/2016.
 */

public class Order {

    private List<Product> items;
    private Person cooker;
    private Person waiter;
    private int totalAmount;
    private double totalDiscount;
    private double total;

    public Order(List<Product> items, Person cooker, Person waiter) {
        this.items = items;
        this.cooker = cooker;
        this.waiter = waiter;
        this.totalAmount = calculateTotalAmount();
        this.totalDiscount = calculateTotalDiscount();
        this.total = totalAmount - totalDiscount;
    }

    public List<Product> getItems() {
        return items;
    }

    public void setItems(List<Product> items) {
        this.items = items;
    }

    public Person getCooker() {
        return cooker;
    }

    public void setCooker(Person cooker) {
        this.cooker = cooker;
    }

    public Person getWaiter() {
        return waiter;
    }

    public void setWaiter(Person waiter) {
        this.waiter = waiter;
    }

    public int getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(int totalAmount) {
        this.totalAmount = totalAmount;
    }

    public double getTotalDiscount() {
        return totalDiscount;
    }

    public void setTotalDiscount(double totalDiscount) {
        this.totalDiscount = totalDiscount;
    }

    private int calculateTotalAmount() {
        int total = 0;
        for (Product p : items) {
            total += p.getPrice();
        }
        return total;
    }

    private double calculateTotalDiscount() {
        double total = 0;
        for (Product p : items) {
            total += p.getDiscount()*0.01*p.getPrice();
        }
        return total;
    }
}
