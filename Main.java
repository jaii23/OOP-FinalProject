import java.util.Scanner;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        UserManager manager = new UserManager();
        Scanner input = new Scanner(System.in);
        AssetSystem lib = new AssetSystem(); 
        BorrowingSystem system = new BorrowingSystem(lib.getMaterials(), manager);
        ReturnSystem returnSystem = new ReturnSystem(manager);

         
        while (true) {
            System.out.println("\n================================");
            System.out.println("             MAIN MENU          ");
            System.out.println("================================");
            System.out.println("1 - Account Management");
            System.out.println("2 - Available Literary Works");
            System.out.println("3 - Borrow a Literary Work");
            System.out.println("4 - Return a Literary Work");
            System.out.println("5 - Borrowing History");
            System.out.println("6 - Literary Work History");
            System.out.println("0 - Exit");
            System.out.print("Enter your choice: ");

            int choice;
            try {
                choice = Integer.parseInt(input.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input! Please enter a number between 0 and 6.");
                continue; // ask again
            }

            switch (choice) {
                case 1:
                    manager.startMenu(); // account management
                    break;
                case 2:
                    System.out.println("Available Literary Works:");
                    lib.assetManagement();
                    break;
                case 3:
                    System.out.println("Borrow a Literary Work:");
                    system.BorrowProcess();
                    break;
                case 4:
                    System.out.println("Return a Literary Work:");
                    returnSystem.returnMaterial();
                    break;
                case 5:
                    System.out.println("Borrowing History:");
                    // add code for history
                    break;
                case 6:
                    System.out.println("Literary Work History:");
                    // add code for work history
                    break;
                case 0:
                    System.out.println("Thank you for using the program. Goodbye!");
                    System.out.println("Group RamGPT: Palomo, Calonge, Manasis, Abit");
                    System.exit(0); // terminate program
                    break;
                default:
                    System.out.println("Invalid choice! Please enter a number between 0 and 6.");
            }
        }
    }
}