import java.util.Scanner;
import java.util.ArrayList;

public class BorrowingSystem{
    private ArrayList<Material> materials;
    private UserManager userManager;

    public BorrowingSystem(ArrayList<Material> materials, UserManager userManager){ 
        this.materials = materials;
        this.userManager = userManager;
    }
    
    //to display avaialable materials
    public void displayAvailableMaterials(){
        System.out.println("\n================================");
        System.out.println("        AVAILABLE MATERIALS        ");
        System.out.println("================================");
        if(materials.isEmpty()){
            System.out.println("No materials available.");
        } else{
            for (Material m : materials){
                System.out.println(m.toString());
            }
        }
    }
    //borrower class
    static class Borrower {
        String fullName;
        boolean isRegistered;
        int strikes;
        boolean hasBorrowed;
        String dueDate;

        public Borrower(String fullName, boolean isRegistered, int strikes, String dueDate) {
            this.fullName = fullName;
            this.isRegistered = isRegistered;
            this.strikes = strikes;
            this.hasBorrowed = false;
            this.dueDate = dueDate;
        }
    }

    //borrowing process
    public void BorrowProcess(){
        
        Scanner input = new Scanner(System.in);
        String fullName;
        boolean isRegistered = true;;
        int strikes=0;
        String dueDate="";
        boolean hasBorrowed;

        //show materials first
        displayAvailableMaterials();
        if(materials.isEmpty()) return;

        //get borrower info 
        System.out.println("\n To process borrowing, please enter the necessary information. Thank you!");
        System.out.print("Please enter your full name: ");
        fullName = input.nextLine();

        //checking if user is registered
        Person user = userManager.getUserByFullName(fullName);
        if (user == null){
            System.out.println("Borrower is not registered. Please register first.");
            return;
        }

        //check number of strikes
        if (user.getNumOfStrikes() >= 3){
            System.out.println("Borrower cannot borrow anymore. You have reached 3 strikes.");
            return;

        }

        //check if user already borrowed a meterial
        if (user.isHasBorrowed()){
            System.out.println("Borrower already has a borrowed material. Please return it first before borrowing again.");
            return;
        }
        
        Borrower borrower = new Borrower(fullName,true, 0, "");
        
        //user must enter material id of the material to be borrowed
        System.out.print("Enter Material ID of the material to be borrowed: ");
        int materialId = input.nextInt();

        Material chosen = null; //???

        //look for the material by id 
        for(Material m : materials){
            if(m.getMaterialId() == materialId){
                chosen = m;
                break;
            }
        }

        if (chosen == null){
            System.out.println("Material with ID " + materialId + " cannot found.");
            return;
        }

        //borrowing the material na
        borrowMaterial(user, borrower, chosen);
        
    }

    //borrowing function
    public void borrowMaterial (Person user, Borrower borrower, Material material){
        user.setHasBorrowed(true);
        user.setBorrowedMaterial(material);

        try {
            //RULE 1:borrower must be registered
            if(!borrower.isRegistered){
                throw new Exception("Borrower is not registered. Please register first.");
            }

            //RULE 2:borrower must not have more than 3 strikes 
            if(borrower.strikes >= 3){
                throw new Exception("Borrower cannot borrow anymore. You have reached 3 strikes.");
            }

            //RULE 3:borrower can borrow only 1 material at a time
            if (borrower.hasBorrowed){
                throw new Exception("Borrower already has a borrowed material. Return it first before borrowing another material.");
            }

            //RULE 4:check if material have available copies 
            if(material.getCopies() <= 0){
                throw new Exception("No more available copies for this material.");
            }

            //once passed all sa rules, proceed with borrowing
            borrower.hasBorrowed = true;
            material.copies -= 1;

            //storea borrowed material in Person
            user.setHasBorrowed(true);
            user.setBorrowedMaterial(material);

            //declare materialType sa material class
            String dueDate = getdueDate(material.getMaterialType());
            borrower.dueDate = dueDate;
            System.out.println("Successfully borrowed!");
            System.out.println("Borrwer Name:" + borrower.fullName);
            System.out.println("Borrowed Material Title: " + material.getTitleOrName());
            System.out.println("Due Date:" + borrower.dueDate);
        }
        catch (Exception e) {
            //displays this message if rules were not met
            System.out.println("Borrowing failed: " + e.getMessage());
        }
    }

    //displaying return date massage based on material typw
    public String getdueDate(String materialType){
        
        switch (materialType) {
            case "Book":
                return "Please return within 7 days.";
            
            case "Journal":
                return "Please return within 3 days.";
            
            case "Magazine":
                return "Please return within the same day it is borrowed.";
            
            case "ThesisBook":
                return "Please return within 2 days.";
            
            default:
                return "Unkown material type. Please enter only the material type available on this library type.";
        }
    }

    
}
