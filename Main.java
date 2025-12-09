import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;


public class Main{

    public static void main(String[] args){
        Accounts a1 = new Accounts("Admin", "Admin","Password123!","Admin");

        ArrayList<Accounts> manager = new ArrayList<>();
        manager.add(a1);

        System.out.println("--- Welcome to the Password Manager ---\n");


        Scanner startInput = new Scanner(System.in);
        System.out.println("Do you want to (1) Login or (2) Register a new account?");
        int firstChoice = startInput.nextInt();
        startInput.nextLine();

        if (firstChoice == 2) {
            registerBeforeLogin(manager);
        }


        int attempts = 0;
        while (!authenticate(manager)) {
            attempts++;
            if (attempts >= 3) {
                System.out.println("Too many failed attempts. Program shutting down.");
                System.exit(0);
            }
            System.out.println("Try again.\n");
        }       
        System.out.println("Login successful! Access granted.\n");


        System.out.println("--- Welcome to the Password Manager ---\n");

        File passwordList = new File("passwords.csv");

        if (passwordList.exists()){
            try(BufferedReader reader = new BufferedReader(new FileReader(passwordList))){
                String line;
                while ((line = reader.readLine()) != null){
                    String[] tokens = new String (Base64.getDecoder().decode(line), StandardCharsets.UTF_8).trim().split(",");
                    manager.add(new Accounts(tokens[0], tokens[1], tokens[2], tokens[3]));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }



        Scanner in = new Scanner(System.in);
        boolean running = true;
        while (running) {
            System.out.println("Choose an action:");
            System.out.println("1. Add a account");
            System.out.println("2. Delete a account");
            System.out.println("3. Show all accounts");
            System.out.println("4. Show one account");
            System.out.println("5. Modify an Account");
            System.out.println("6. Exit");
            System.out.print("Enter choice: ");


            int choice = in.nextInt();
            in.nextLine();

            switch (choice) {
                case 1:
                // Adding accounts
                    addAccount(manager);
                    break;
                case 2:
                // Deleting accounts
                    removeAccount(manager);
                    break;
                case 3:
                // Showing all user accounts
                    showAllAccounts(manager);
                    break;
                case 4:
                // Showing one user account
                    break;
                case 5:
                // Modifying accounts
                    modifyAccount(manager);
                    break;
                case 6:
                    running = false;
                    System.out.println("Exiting Manager. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid option, try again.");
            }
            System.out.println();

        }
        in.close();
        try (FileWriter writer = new FileWriter(passwordList)){
            for (Accounts accounts : manager) {
                writer.write(Base64.getEncoder().encodeToString((accounts.getName()+","+accounts.getUsername()+","+accounts.getPassword()+","+accounts.getCategory()).getBytes(StandardCharsets.UTF_8))+"\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }




public static void registerBeforeLogin(ArrayList<Accounts> manager) {
    Scanner in = new Scanner(System.in);

    System.out.println("\n--- Create an Account Before Login ---");

    System.out.print("Enter your name: ");
    String name = in.nextLine();

    System.out.print("Choose a username: ");
    String username = in.nextLine();

    // Check if username exists
    for (Accounts a : manager) {
        if (a.getUsername().equals(username)) {
            System.out.println("ERROR: That username already exists.");
            return;
        }
    }

    System.out.print("Choose a password: ");
    String password = makePassword();

    System.out.print("Enter category: ");
    String category = in.nextLine();

    Accounts newAcc = new Accounts(name, username, password, category);
    manager.add(newAcc);

    // Save to passwords.csv 
    try (FileWriter writer = new FileWriter("passwords.csv", true)) {
        writer.write(Base64.getEncoder().encodeToString(
                (name + "," + username + "," + password + "," + category)
                        .getBytes(StandardCharsets.UTF_8)
        ) + "\n");
    } catch (IOException e) {
        System.out.println("Error saving new account.");
    }

    System.out.println("Account created! You may now log in.\n");
}



public static boolean authenticate(ArrayList<Accounts> manager) {
    // Load Accounts from passwords.csv 
    File passwordList = new File("passwords.csv");

    ArrayList<Accounts> loginAccounts = new ArrayList<>();

    if (passwordList.exists()) {
        try (BufferedReader reader = new BufferedReader(new FileReader(passwordList))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String decoded = new String(Base64.getDecoder().decode(line), StandardCharsets.UTF_8);
                String[] tokens = decoded.trim().split(",");
                loginAccounts.add(new Accounts(tokens[0], tokens[1], tokens[2], tokens[3]));
            }
        } catch (IOException e) {
            System.out.println("ERROR: Could not read authentication data.");
            return false;
        }
    }

    Scanner in = new Scanner(System.in);

    System.out.print("Login Username: ");
    String username = in.nextLine();

    System.out.print("Login Password: ");
    String password = in.nextLine();


    for (Accounts a : loginAccounts) {
        if (a.getUsername().equals(username) && a.getPassword().equals(password)) {
            System.out.println("\nWelcome, " + a.getName() + "!\n");
            return true;
        }
    }

    System.out.println("\nERROR: Incorrect username or password.\n");
    return false;
}





    private static void addAccount(ArrayList<Accounts> manager){
        Scanner in = new Scanner(System.in);

        System.out.println("what is your name?");
        String name = in.nextLine();
        System.out.println("what will your username be?");
        String username = in.nextLine();
        System.out.println("what will your password be? (must have 1 letter and special char and must be 8 characters long)");
        String password = makePassword();
        System.out.println("What category will your account fall into?");
        String category = in.nextLine();
        Accounts newAccount = new Accounts(name,username,password,category);
        manager.add(newAccount);
    }


    public static String makePassword(){
        Scanner in = new Scanner(System.in);
        //AI on checking for special chars and numbers in string

        List<Character> characterList = Arrays.asList('!','@','#','$','^','&','*',')','(','<','>','/');
            
        List<Character> numbers = Arrays.asList('1','2','3','4','5','6','7','8','9','0');

        String password = "";
        boolean good = false;
        while (!good) {
            password = in.nextLine();
            if (!(password.length()>8 || containsAnyChar(password, numbers) || containsAnyChar(password, characterList))) {
                System.out.println("Error: Your password doesn't meet the requirements try again, it needs:\n 1. 8 characters\n2. 1 of both special characters and numbers");
            }
            else{
                good = true;
            }
        }
        
        return password;
    }

    public static boolean containsAnyChar(String text, List<Character> charsToCheck) {
        if (text == null || charsToCheck == null || charsToCheck.isEmpty()) {
            return false;
        }

        for (char c : charsToCheck) {
            if (text.indexOf(c) != -1) {
                return true; // Found a character from the list
            }
        }
        return false; // No character from the list found
    }

    public static void removeAccount(ArrayList<Accounts> manager) {
        Scanner in = new Scanner(System.in);
        System.out.println();
        System.out.println("Which account do you want to delete?:");

        for (Accounts accounts : manager) {
            System.out.println("Username: " + accounts.getUsername() + " Category: " + accounts.getCategory());
        }
        System.out.println("Select your choice by Username then category (enter separately)");
        String deletedName = in.nextLine();
        String deletedCategory = in.nextLine();

        for (Iterator<Accounts> iterator = manager.iterator(); iterator.hasNext(); ) {
            Accounts a = iterator.next();
            if (a.getCategory().equals(deletedCategory) && a.getUsername().equals(deletedName)) {
                iterator.remove();
                System.out.println("Account Removed Successfully!");
                break;
            }
        }
    }

    public static void showAllAccounts(ArrayList<Accounts> manager) {
        for (Accounts accounts : manager) {
            System.out.println(accounts);
        }
    }

    public static  void modifyAccount(ArrayList<Accounts> manager) {
        Scanner in = new Scanner(System.in);
        System.out.println();
        System.out.println("Which account do you want to modify?:");

        for (Accounts accounts : manager) {
            System.out.println("Username: " + accounts.getUsername() + " Category: " + accounts.getCategory());
        }
        System.out.println("Select your choice by Username then category (enter separately)");
        String deletedName = in.nextLine();
        String deletedCategory = in.nextLine();

        for (Accounts a : manager) {
            if (a.getCategory().equals(deletedCategory) && a.getUsername().equals(deletedName)) {
                System.out.println("What should the new name be? (Leave blank to keep unchanged)");
                String newName = in.nextLine();
                System.out.println("What should the new display name be? (Leave blank to keep unchanged)");
                String newUsername = in.nextLine();
                System.out.println("What should the new password be? (Changing the password is required)");
                String newPassword = makePassword();
                System.out.println("What should the new category be? (leave blank to keep unchanged)");
                String newCategory = in.nextLine();

                if (!newName.isBlank()) {a.setName(newName);}
                if (!newUsername.isBlank()) {a.setUsername(newUsername);}
                if (!newPassword.isBlank()) {a.setPassword(newPassword);}
                if (!newCategory.isBlank()) {a.setCategory(newCategory);}
                break;
            }
        }
    }


}
