import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Arrays;
import java.util.Iterator;
import java.io.BufferedReader;
import java.io.FileReader;
// ai for adding to a csv file
import java.io.FileWriter;
import java.io.IOException;








public class Main{


    public static void main(String[] args){

        Accounts a1 = new Accounts("Admin","Password123!","Admin");

        ArrayList<Accounts> manager = new ArrayList<>();
        manager.add(a1);

        //Authentication (Ethan)

        System.out.println("--- Welcome to the Password Manager ---\n");

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
                    addAccount("Admin.csv"); // add a variable of the csv file
                    break;
                case 2:
                // Deleting accounts
                    removeAccount("Admin.csv");
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

    }

    private static void addAccount(String manager){
        Scanner in = new Scanner(System.in);

        System.out.println("what will your username be?");
        String username = in.nextLine();
        System.out.println("what will your password be? (must have 1 letter and special char and must be 8 characters long)");
        String password = makePassword();
        System.out.println("What category will your account fall into?");
        String category = in.nextLine();
        Accounts newAccount = new Accounts(username,password,category);
        // modifications from Chat Gpt to know how to add a new line to the file by using filewriter as a way of modifying the file thanks to the file name ex: admin.csv
        String newLine = newAccount.getUsername()+", "+newAccount.getPassword()+", "+newAccount.getCategory();

        try(FileWriter writer = new FileWriter(manager, true)){
        writer.append("\n").append(newLine);
        } catch(Exception e){
            e.printStackTrace();
        }






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

    //AI for password check by going through the selected list and checking if any of the list items are inside the string by using a for loop and index
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




    

    public static void removeAccount(String manager) {
        Scanner in = new Scanner(System.in);
        ArrayList<String> lines = new ArrayList<>();
        System.out.println();
        System.out.println("Which account do you want to delete?:");
        // chag gpt to grab each line from the csv file to show/remake by storing each line in a string array list
        try (BufferedReader br = new BufferedReader(new FileReader("data.csv"))) {
            String line;

            while ((line = br.readLine()) != null) {
                lines.add(line); // add entire line as a single item
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        for(String l: lines){
            System.out.println(lines);
        }


        System.out.println("Select your choice by Username then category (enter separately)");
        String deletedName = in.nextLine();
        String deletedCategory = in.nextLine();

        for(int i = 0; i<lines.size();i++){
            if(lines[i].contains(deletedName) && lines[i].contains(deletedCategory)){
                lines.remove(i);
                //co piolet on rewriting the entire content of the file to remove the unwanted account by removing it from the new text before reinserting it into the csv
                try(FileWriter writer = new FileWriter(manager,true)){
                    for(String line:lines){
                        writer.write(line);
                        writer.write(System.lineSeparator());
                    }
                } catch(Exception e){
                    System.out.print("error on deleting the item");
                }
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

        //AI to show and get each account username and category

        for (Accounts accounts : manager) {
            System.out.println("Username: " + accounts.getUsername() + " Category: " + accounts.getCategory());
        }
        System.out.println("Select your choice by Username then category (enter separately)");
        String deletedName = in.nextLine();
        String deletedCategory = in.nextLine();
        // ChatGPT for selecting the targeted items and deleting the object that contains those items by for loop to check all of them and delete the one that contains both variables
        // uses iterator to avoid a concurrent modifications execution
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

