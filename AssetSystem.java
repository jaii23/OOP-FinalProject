import java.util.Scanner;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class AssetSystem {
    private ArrayList<Material> materials = new ArrayList<>();
    private Scanner input = new Scanner(System.in);
    private int nextId = 1;
    
    //para madisplay yung list outside assetsystem
    public ArrayList<Material> getMaterials() {
        return materials;
    }

    public void assetManagement() {
        int choice2;
        while (true) {
            System.out.println("===== ASSET MANAGEMENT =====");
            System.out.println("1 - Add Material");
            System.out.println("2 - Edit Material");
            System.out.println("3 - Delete Material");
            System.out.println("4 - View all Materials");
            System.out.println("0 - back");
            System.out.println("Enter Your Choice: ");

            choice2 = input.nextInt();
            input.nextLine(); 

            switch (choice2) {
                case 1:
                    addMaterial();
                    break;
                case 2:
                    editMaterial();
                    break;
                case 3:
                    deleteMaterial();
                    break;
                case 4:
                    viewAllMaterials();
                    break;
                case 0:
                    return;
                default: System.out.println("Invalid Choice");
            }
        }   
    }

    // ADD MATERIAL
    private void addMaterial() {

    try {
        System.out.println("\nChoose Material Type");
        System.out.println("1 - Book");
        System.out.println("2 - Journal");
        System.out.println("3 - Magazine");
        System.out.println("4 - Thesis Book");
        int t;
        while (true) {
            System.out.print("Enter your Choice: ");
            if (input.hasNextInt()) {
                t = input.nextInt();
                input.nextLine();
                if (t >= 1 && t <= 4) break;
            } else input.nextLine();
            System.out.println("Invalid type. Please try again.");
        }

        String type = switch (t) {
            case 1 -> "Book";
            case 2 -> "Journal";
            case 3 -> "Magazine";
            case 4 -> "ThesisBook";
            default -> "";
        };

        int id = nextId;
        System.out.println("Generated ID: " + id);

        

        String title;
        while (true) {
            System.out.print("Title/Name: ");
            title = input.nextLine().trim();
            if (!title.isEmpty()) break;
            System.out.println("Title cannot be empty.");
        }

        String author;
        while (true) {
            System.out.print("Author/Journal Name: ");
            author = input.nextLine().trim();
            if (!author.matches(".*\\d.*")) break;
            System.out.println("Author cannot contain numbers.");
        }

        int year;
        while (true) {
            System.out.print("Year Published (1000 - 2025): ");
            if (input.hasNextInt()) {
                year = input.nextInt();
                input.nextLine();
                if (year >= 1000 && year <= 2025) break;
            } else input.nextLine();
            System.out.println("Invalid year. Try again.");
        }

        String publisher;
        while (true) {
            System.out.print("Publisher: ");
            publisher = input.nextLine().trim();
            if (!publisher.matches(".*\\d.*")) break;
            System.out.println("Publisher cannot contain numbers.");
        }

        int copies;
        while (true) {
            System.out.print("Copies (must be >= 1): ");
            if (input.hasNextInt()) {
                copies = input.nextInt();
                input.nextLine();
                if (copies >= 1) break;
            } else input.nextLine();
            System.out.println("Copies must be at least 1.");
        }

            // CREATE MATERIAL
            Material m = switch (type) {
            case "Book" -> new Book(id, "Book", title, author, year, publisher, copies);
            case "Journal" -> new Journal(id, "Journal",title, author, year, publisher, copies);
            case "Magazine" -> new Magazine(id,"Magazine", title, author, year, publisher, copies);
            case "ThesisBook" -> new ThesisBook(id, "ThesisBook",title, author, year, publisher, copies);
            default -> null;
            };

            materials.add(m);
            nextId++; 
            saveMaterials();
            System.out.println("Material added successfully!");
        
        } catch (Exception e) {
            System.out.println("Unexpected error. Resetting input...");
            input.nextLine(); 
        }
    }

    // EDIT MATERIAL
    private void editMaterial() {
    System.out.print("Enter Material ID to edit: ");
    int editId = input.nextInt();
    input.nextLine(); 

    Material m = findMaterialById(editId);

    if (m == null) {
        System.out.println("Material not found!");
        return;
    }

    System.out.println("Editing Material: " + m.getTitleOrName());

    // Ask user for new values (press ENTER to skip)

    System.out.print("New Title/Name (leave blank to keep \"" + m.getTitleOrName() + "\"): ");
    String newTitle = input.nextLine().trim();
    if (!newTitle.isEmpty()) m.titleOrName = newTitle;

    System.out.print("New Author/Journal Name (leave blank to keep \"" + m.getAuthorOrPublisher() + "\"): ");
    String newAuthor = input.nextLine().trim();
    if (!newAuthor.isEmpty()) m.authorOrPublisher = newAuthor;

    System.out.print("New Year Published (0 to keep " + m.getYearPublished() + "): ");
    int newYear = input.nextInt();
    input.nextLine();
    if (newYear != 0) m.yearPublished = newYear;

    System.out.print("New Publisher (leave blank to keep \"" + m.getPublisher() + "\"): ");
    String newPublisher = input.nextLine().trim();
    if (!newPublisher.isEmpty()) m.publisher = newPublisher;

    System.out.print("New Copies (0 to keep " + m.getCopies() + "): ");
    int newCopies = input.nextInt();
    input.nextLine();
    if (newCopies != 0) m.copies = newCopies;

    System.out.println("Material updated successfully!");
}

    //DELETE MATERIAL
    public void deleteMaterial() {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter Material ID to delete: ");
        int deleteId = sc.nextInt();

        Material toDelete = null;

        // Search in the ArrayList
        for (Material m : materials) {
            if (m.getMaterialId() == deleteId) {
                toDelete = m;
                break;
            }
        }

        if (toDelete == null) {
            System.out.println("Material with ID " + deleteId + " not found!");
            return;
        }

        materials.remove(toDelete);
        System.out.println("Material deleted successfully!");
    }

    //VIEW MATERIAL
    private void viewAllMaterials() {
    System.out.println("\n===== Materials ======");
    System.out.println("\nID|  Title  |  Author  |Date of Publication| Publisher |  Copies  |");

        if (materials.size() == 0) {
            System.out.println("No materials.");
        } else {
            for (int i = 0; i < materials.size(); i++) {
                Material m = materials.get(i);
                System.out.println(m.toString());
            }
        }
    }
    
    //SEARCH MATERIAL
    private Material findMaterialById(int id) {

        for (int i = 0; i < materials.size(); i++) {
            Material m = materials.get(i);
            if (m.getMaterialId() == id) {
                return m; 
            }
        }

        return null; 
    }

    private void saveMaterials() {
    // Save to file 
    }
}