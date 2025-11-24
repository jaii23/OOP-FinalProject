import java.util.ArrayList;
import java.util.Scanner;

public class UserManager {
    private ArrayList<Person> users;
    private Scanner input;
    private int nextID = 1; // auto increment ID

    public UserManager() {
        users = new ArrayList<>();
        input = new Scanner(System.in);
    }

    // -------------------- MAIN MENU LOOP --------------------
    public void startMenu() {
        boolean exit = false;
        while (!exit) {
            System.out.println("\n===== Account Management =====");
            System.out.println("1 - Add Account Information");
            System.out.println("2 - View Accounts");
            System.out.println("3 - Edit Accounts");
            System.out.println("4 - Delete Accounts");
            System.out.println("0 - Return to Main Menu");
            System.out.print("Enter your choice: ");

            int choice = readInt("");
            switch (choice) {
                case 1: addUser(); break;
                case 2: viewUsers(); break;
                case 3: editUserMenu(); break;
                case 4: deleteUser(); break;
                case 0: exit = true; break;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }

    // -------------------- ADD USER --------------------
private void addUser() {
    System.out.println("\n===== Enter Account Information =====");
    int id = nextID++; // auto-generated ID
    System.out.println("ID: " + id);

    while (true) {
        // Ask only for full name first
        String fname = readName("Enter First Name: ");
        String mname = readName("Enter Middle Name: ");
        String lname = readName("Enter Last Name: ");

        // Check full name uniqueness
        boolean duplicate = false;
        for (Person p : users) {
            if (p.getFname().equalsIgnoreCase(fname) &&
                p.getMname().equalsIgnoreCase(mname) &&
                p.getLname().equalsIgnoreCase(lname)) {
                duplicate = true;
                break;
            }
        }

        if (duplicate) {
            System.out.println("Error! A user with the same full name already exists. Please re-enter full name.");
        } else {
            // Full name is unique, proceed to other information
            String gender = readName("Enter Gender: ");
            String bday = readString("Enter Birthday (YYYY/MM/DD): ");
            long cnumber = readUniqueContact();
            String email = readUniqueEmail();

            Person person = new Person();
            person.setID(id);
            person.setFname(fname);
            person.setMname(mname);
            person.setLname(lname);
            person.setGender(gender);
            person.setBday(bday);
            person.setCnumber(cnumber);
            person.setEmail(email);

            users.add(person);
            System.out.println("\nAccount added successfully! Your ID is: " + id);
            break; // exit the while loop
        }
    }
}
    // -------------------- VIEW USERS --------------------
    private void viewUsers() {
        if (users.isEmpty()) {
            System.out.println("No accounts yet.");
            return;
        }

        System.out.println("\n===== ALL ACCOUNTS =====");
        for (Person p : users) {
            p.displayInfo();
            System.out.println("----------------------------");
        }
    }

    // -------------------- DELETE USER --------------------
    private void deleteUser() {
        if (users.isEmpty()) {
            System.out.println("No accounts to delete.");
            return;
        }

        int id = readInt("Enter ID to delete: ");
        Person found = findUser(id);

        if (found == null) {
            System.out.println("Account not found.");
            return;
        }

        users.remove(found);
        System.out.println("Account deleted successfully!");
    }

    // -------------------- EDIT USER MENU --------------------
    private void editUserMenu() {
        if (users.isEmpty()) {
            System.out.println("No accounts to edit.");
            return;
        }

        int id = readInt("Enter ID to edit: ");
        Person person = findUser(id);

        if (person == null) {
            System.out.println("Account not found.");
            return;
        }

        editUser(person);
    }

    // -------------------- EDIT USER --------------------
private void editUser(Person person) {
    System.out.println("\nEditing Account: " + person.getFname() + " " + person.getLname());

    // ----- Edit First Name -----
    while (true) {
        String fname = readString("New First Name (" + person.getFname() + "): ");
        if (fname.isEmpty()) break; // keep current
        if (fname.matches(".*\\d.*")) {
            System.out.println("Error! Name cannot contain numbers.");
            continue;
        }
        boolean duplicate = false;
        for (Person p : users) {
            if (p != person && p.getFname().equalsIgnoreCase(fname) &&
                p.getMname().equalsIgnoreCase(person.getMname()) &&
                p.getLname().equalsIgnoreCase(person.getLname())) {
                duplicate = true;
                break;
            }
        }
        if (duplicate) {
            System.out.println("Error! Full name already exists with this first name.");
        } else {
            person.setFname(fname);
            break;
        }
    }

    // ----- Edit Middle Name -----
    while (true) {
        String mname = readString("New Middle Name (" + person.getMname() + "): ");
        if (mname.isEmpty()) break;
        if (mname.matches(".*\\d.*")) {
            System.out.println("Error! Name cannot contain numbers.");
            continue;
        }
        boolean duplicate = false;
        for (Person p : users) {
            if (p != person && p.getFname().equalsIgnoreCase(person.getFname()) &&
                p.getMname().equalsIgnoreCase(mname) &&
                p.getLname().equalsIgnoreCase(person.getLname())) {
                duplicate = true;
                break;
            }
        }
        if (duplicate) {
            System.out.println("Error! Full name already exists with this middle name.");
        } else {
            person.setMname(mname);
            break;
        }
    }

    // ----- Edit Last Name -----
    while (true) {
        String lname = readString("New Last Name (" + person.getLname() + "): ");
        if (lname.isEmpty()) break;
        if (lname.matches(".*\\d.*")) {
            System.out.println("Error! Name cannot contain numbers.");
            continue;
        }
        boolean duplicate = false;
        for (Person p : users) {
            if (p != person && p.getFname().equalsIgnoreCase(person.getFname()) &&
                p.getMname().equalsIgnoreCase(person.getMname()) &&
                p.getLname().equalsIgnoreCase(lname)) {
                duplicate = true;
                break;
            }
        }
        if (duplicate) {
            System.out.println("Error! Full name already exists with this last name.");
        } else {
            person.setLname(lname);
            break;
        }
    }

    // ----- Edit Gender -----
    while (true) {
        String gender = readString("New Gender (" + person.getGender() + "): ");
        if (gender.isEmpty()) break;
        if (gender.matches(".*\\d.*")) {
            System.out.println("Error! Gender cannot contain numbers.");
            continue;
        }
        person.setGender(gender);
        break;
    }

    // ----- Edit Birthday -----
    String bday = readString("New Birthday (" + person.getBday() + "): ");
    if (!bday.isEmpty()) person.setBday(bday);

    // ----- Edit Contact Number -----
    while (true) {
        String cnumInput = readString("New Contact Number (" + person.getCnumber() + "): ");
        if (cnumInput.isEmpty()) break;
        if (!cnumInput.matches("\\d+")) {
            System.out.println("Error! Contact number must be numeric.");
            continue;
        }
        long contact;
        try {
            contact = Long.parseLong(cnumInput);
        } catch (NumberFormatException e) {
            System.out.println("Error! Number too large. Enter again.");
            continue;
        }
        boolean exists = false;
        for (Person p : users) {
            if (p != person && p.getCnumber() == contact) {
                exists = true;
                break;
            }
        }
        if (exists) {
            System.out.println("Error! Contact number already exists.");
        } else {
            person.setCnumber(contact);
            break;
        }
    }

    // ----- Edit Email -----
    while (true) {
        String emailInput = readString("New Email (" + person.getEmail() + "): ");
        if (emailInput.isEmpty()) break;
        if (!emailInput.contains("@")) {
            System.out.println("Error! Email must contain '@'.");
            continue;
        }
        boolean exists = false;
        for (Person p : users) {
            if (p != person && p.getEmail().equalsIgnoreCase(emailInput)) {
                exists = true;
                break;
            }
        }
        if (exists) {
            System.out.println("Error! Email already exists.");
        } else {
            person.setEmail(emailInput);
            break;
        }
    }

    System.out.println("Account updated successfully!");
}

    // -------------------- FIND USER --------------------
    private Person findUser(int id) {
        for (Person p : users) {
            if (p.getID() == id) return p;
        }
        return null;
    }

    public Person getUserByFullName(String fullName){
        for (Person p : users) {
            String name = p.getFname() + " " + p.getMname() + " " + p.getLname();
            if (name.equalsIgnoreCase(fullName)) 
                return p;
        }
        return null;
    }
    // -------------------- SAFE INPUT METHODS --------------------
    private int readInt(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return Integer.parseInt(input.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid! Enter a number.");
            }
        }
    }

    private String readString(String prompt) {
        System.out.print(prompt);
        return input.nextLine();
    }

    // -------------------- NAME VALIDATION (NO NUMBERS) --------------------
    private String readName(String prompt) {
        while (true) {
            System.out.print(prompt);
            String value = input.nextLine();

            if (value.matches(".*\\d.*")) {
                System.out.println("Error! Input cannot contain numbers.");
            } else {
                return value;
            }
        }
    }

    private String readUniqueName(String prompt, Person current, String field) {
        while (true) {
            System.out.print(prompt);
            String value = input.nextLine();
            if (value.isEmpty() && current != null) return "";
            if (value.matches(".*\\d.*")) {
                System.out.println("Error! Input cannot contain numbers.");
                continue;
            }

            boolean exists = false;
            for (Person p : users) {
                if (current != null && p == current) continue;

                if (field != null) {
                    switch (field) {
                        case "fname":
                            if (p.getFname().equalsIgnoreCase(value)) exists = true; break;
                        case "mname":
                            if (p.getMname().equalsIgnoreCase(value)) exists = true; break;
                        case "lname":
                            if (p.getLname().equalsIgnoreCase(value)) exists = true; break;
                    }
                }
            }
            if (exists) {
                System.out.println("Error! This value already exists.");
            } else {
                return value;
            }
        }
    }

    // -------------------- EMAIL VALIDATION --------------------
    private String readUniqueEmail() {
        while (true) {
            System.out.print("Enter Email Address: ");
            String email = input.nextLine();
            if (!email.contains("@")) {
                System.out.println("Invalid email! Must contain '@'.");
                continue;
            }
            boolean exists = false;
            for (Person p : users) {
                if (p.getEmail().equalsIgnoreCase(email)) {
                    System.out.println("Error! Email already exists.");
                    exists = true;
                    break;
                }
            }
            if (!exists) return email;
        }
    }

    // -------------------- CONTACT NUMBER VALIDATION --------------------
    private long readUniqueContact() {
        while (true) {
            System.out.print("Enter Contact Number: ");
            String num = input.nextLine();
            if (!num.matches("\\d+")) {
                System.out.println("Invalid! Contact number must be numbers only.");
                continue;
            }
            long contact;
            try {
                contact = Long.parseLong(num);
            } catch (NumberFormatException e) {
                System.out.println("Error! Number is too large. Try again.");
                continue;
            }

            boolean exists = false;
            for (Person p : users) {
                if (p.getCnumber() == contact) {
                    System.out.println("Error! Contact number already exists.");
                    exists = true;
                    break;
                }
            }
            if (!exists) return contact;
        }
    }
}