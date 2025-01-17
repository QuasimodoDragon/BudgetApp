import java.util.ArrayList;
import java.util.Scanner;

class Main {
  public static void main(String[] args) {

    // Creates income and expenses category objects
    Category income = new Category("Income");
    Category expenses = new Category("Expenses");
    // Creates a categories array list that holds each category. This allows for easier manipulation
    ArrayList<Category> categories = new ArrayList<Category>();
    categories.add(income);
    categories.add(expenses);
    Scanner input = new Scanner(System.in);

    System.out.println("This app will help you build your projected monthly budget.");

    // This loops through so the user doesn't have to run the program every time they finish adding or editing a line item
    while (true) {
      int choice = 0;
      // If there are no items in the income or expenses categories then these options will be shown
      if (income.amount.size() == 0 && expenses.amount.size() == 0) {
        System.out.println("Choose a category to begin adding items to:");
        System.out.println("Enter 1 for income");
        System.out.println("Enter 2 for expenses");

        choice = intRangeValidation(input, 1, 2);
        System.out.println();
      } 
      // If there are items in the income or expenses categories then these options will be shown
      else if (income.getTotal() > 0 || expenses.getTotal() > 0) {
        System.out.println("Choose a category to add another item to, choose to edit an existing item, or complete your budget:");
        System.out.println("Enter 1 for income");
        System.out.println("Enter 2 for expenses");
        System.out.println("Enter 3 to edit or delete an item");
        System.out.println("Enter 4 to clear your budget");
        System.out.println("Enter 5 to complete your budget");

        choice = intRangeValidation(input, 1, 5);
        System.out.println();
      }

      // Add income or expenses section
      if (choice == 1 || choice == 2) {
        String description;

        // Loops through until the user enters a description under 25 characters or is not empty
        do {
          System.out.println("Enter the description of the " + categories.get(choice - 1).getName() + " item. The maximum amount of characters is 25:");
          description = input.nextLine();
        } while (description.length() > 25 || description.isEmpty());
        System.out.println();

        System.out.println("Enter the amount of the " + categories.get(choice - 1).getName() + " item:");
        double amount = doubleValidation(input);
        System.out.println();

        categories.get(choice - 1).addItem(description, amount);
        printBudget(categories);
        System.out.println();
      } 
      // Edit or delete section
      else if (choice == 3) {
        System.out.println("Choose to edit or delete an item:");
        System.out.println("Enter 1 to edit");
        System.out.println("Enter 2 to delete");
        int editChoice = intRangeValidation(input, 1, 2);
        System.out.println();

        System.out.println("Choose an income or expense item:");
        System.out.println("Enter 1 for income");
        System.out.println("Enter 2 for expenses");

        // Checks if there is an item in the chosen category
        while (true) {
          choice = intRangeValidation(input, 1, 2);
          if (categories.get(choice - 1).amount.size() == 0) {
            System.out.println("There are no items in this category. Choose a category with an existing item.");
          } else {
            break;
          }
        }
        System.out.println();

        System.out.println("Enter the ID number of the line item:");
        int id = intRangeValidation(input, 1, categories.get(choice - 1).amount.size());
        System.out.println();

        // Edit line item
        if (editChoice == 1) {
          String description;
          do {
            System.out.println("Enter updated description. If nothing is entered, the description will remain the same. The maximum amount of characters is 25:");
            description = input.nextLine();
            if (description.length() > 0) {
              categories.get(choice - 1).setDescription(id - 1, description);
            }
          } while (description.length() > 25);
          System.out.println();

          String stringAmnt;
          double amnt = 0.00;
          // Loops through until the user enters a valid amount with double data type or an empty string
          while (true) {
            System.out.println("Enter updated amount. If nothing is entered, the amount will remain the same:");
            stringAmnt = input.nextLine();
            if (stringAmnt == "") {
              break;
            } else {
              try {
                amnt = Double.parseDouble(stringAmnt);
                categories.get(choice - 1).amount.set(id - 1, amnt);
                break;
              } catch (NumberFormatException e) {
                System.out.println("Invalid input.");
              }
            }
          }
          System.out.println();
          printBudget(categories);
        } 
        // Delete section
        else if (editChoice == 2) {
          categories.get(choice - 1).description.remove(id - 1);
          categories.get(choice - 1).amount.remove(id - 1);
          printBudget(categories);
        }
      } 
      // Clear budget section
      else if (choice == 4) {
        clearTable(income);
        clearTable(expenses);
        printBudget(categories);
      } 
      // Complete budget section
      else if (choice == 5) {
        // Closes the scanner to prevent memory leaks
        input.close();
        break;
      } 
      // If all else fails during the section selection this is printed to the console and the loop will start again.
      else {
        System.out.println("Please enter a valid choice.");
      }
    }

    System.out.println("Here is your completed budget:");
    System.out.println();
    printBudget(categories);

    System.out.println("Your budget is written in the Markdown markup language to increase formatting while still keeping it lightweight and simple. Having it written in Markdown allows you to take the text and edit it with any Markdown editor while maintaining formatting.");
  } // end main

  // Validates the user input is an integer and loops until the user enters an integer
  static int intValidation(Scanner input) {
    String strInput;
    int num = 0;

    while (true) {
      strInput = input.nextLine();
      try {
        num = Integer.parseInt(strInput);
        return num;
      } catch (NumberFormatException e) {
        System.out.println("Invalid input. Please enter a valid number.");
      }
    }
  }

  // Validates the user input is an integer and then checks if the number is within a certain range
  static int intRangeValidation(Scanner input, int lowerRange, int upperRange) {   
    while (true) {
      int num = intValidation(input);
      if (num >= lowerRange && num <= upperRange) {
        return num;
      } else {
        System.out.println("Invalid input. Please enter the number for the respective option.");
      }
    }
  }

  // Validates the user input is a double and loops until the user enters a double
  static double doubleValidation(Scanner input) {
    String strInput;
    double num = 0.00;

    while (true) {
      strInput = input.nextLine();
      try {
        num = Double.parseDouble(strInput);
        return num;
      } catch (NumberFormatException e) {
        System.out.println("Invalid input. Please enter a valid number.");
      }
    }
  }

  // Prints a budget table for each category. Loops through each item in the respective category and prints the item ID, description, and amount.
  static void printCategoryTable(Category category) {
    // Header
    System.out.println("# " + category.getName());
    System.out.println();
    System.out.printf("| %-3s | %-25s | %-10s |%n", "ID", "Description", "Amount");
    System.out.printf("| --- | ------------------------- | ---------- |%n");

    // Body
    for (int i = 0; i < category.amount.size(); i++) {
      System.out.printf("| %-3d | %-25s | $%-9.2f |%n", i + 1, category.getDescription(i), category.getAmount(i));
    }

    // Footer with total
    System.out.printf("| --- | ------------------------- | ---------- |%n");
    System.out.printf("| %-3s | %25s | $%-9.2f |%n", "", "Total", category.getTotal());
    System.out.println();
  }

  // Prints a table for each section passed through and then prints the difference table
  static void printBudget(ArrayList<Category> categories) {
    categories.forEach((e) -> printCategoryTable(e));

    System.out.println("# Difference");
    System.out.println();
    System.out.printf("| %-12s | %-10s |%n", "Category", "Total");
    System.out.printf("| ------------ | ---------- |%n");
    System.out.printf("| %-12s | $%-9.2f |%n", "Income", categories.get(0).getTotal());
    System.out.printf("| %-12s | $%-9.2f |%n", "Expenses", categories.get(1).getTotal());
    System.out.printf("| ------------ | ---------- |%n");
    System.out.printf("| %-12s | $%-9.2f |%n", "Difference", categories.get(0).getTotal() - categories.get(1).getTotal());
    System.out.println();
  }

  // Clears a table 
  static void clearTable(Category category) {
    category.description.clear();
    category.amount.clear();
  }
} // end Main