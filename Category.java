import java.util.ArrayList;

public class Category {
  String name;
  ArrayList<String> description = new ArrayList<String>();
  ArrayList<Double> amount = new ArrayList<Double>();

  public Category (String name) {
    this.name = name;
  }

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription(int index) {
    return this.description.get(index);
  }

  public void setDescription(int index, String description) {
    this.description.set(index, description);
  }

  public double getAmount(int index) {
    return this.amount.get(index);
  }

  public void addItem(String description, double amount) {
    this.description.add(description);
    this.amount.add(amount);
  }
  
  public double getTotal() {
    double total = 0;
    for (int i = 0; i < amount.size(); i++) {
      total += amount.get(i);
    }
    return total;
  }
}