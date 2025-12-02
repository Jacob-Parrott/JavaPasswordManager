import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Arrays;
import java.util.Iterator;


public class Main{

    public static void main(String[] args){

        Accounts a1 = new Accounts("Admin", "Admin","password","Admin");

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
                // adding (Ryland)
                addAcount(manager);
                    break;
                case 2:
                // deleting (Ryland)
                removeAccount(manager);
                    break;
                case 3:
                //showing all (Jacob)
                    break;
                case 4:
                //showing one (Ethan)
                    break;
                case 5:
                // modifying (Jacob)
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

    private static void addAcount(ArrayList<Accounts> manager){
        Scanner in = new Scanner(System.in);

        System.out.println("what is your name?");
        String name = in.nextLine();
        System.out.println("what will your username be?");
        String username = in.nextLine();
        System.out.println("what will your password be? (must have 1 letter and special char and must be 8 characters long)");
        String password = makePassword();
        System.out.println("What catagory will your account fall into?");
        String catagory = in.nextLine();
        Accounts newAccount = new Accounts(name,username,password,catagory);
        manager.add(newAccount);
    }


    public static String makePassword(){
        Scanner in = new Scanner(System.in);
        //ai on checking for special chars and numbers in string

        List<Character> specialchar = Arrays.asList('!','@','#','$','^','&','*',')','(','<','>','/');
            
        List<Character> numbers = Arrays.asList('1','2','3','4','5','6','7','8','9','0');

        String password = "";
        boolean good = false;
        while (good == false) {
            password = in.nextLine();
            if (!(password.length()>8 || containsAnyChar(password, numbers) || containsAnyChar(password, specialchar))) {
                System.out.println("Error your password dosent meet the requierments try again, it needs:\n 1. 8 characters\n2. 1 of both special characters and numbers");
            }
            else{
                good = true;
            }
        }
        
        return password;
    }

    //ai for password check by going through the selected list and checking if any of the list items are inside the string by useing a for loop and index
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




    

    public static void removeAccount(ArrayList<Accounts> manager){
        Scanner in = new Scanner(System.in);
        System.out.println();
        System.out.println("Which account do you want to delete?:");

        //ai to show and get each account username and catagory

        for(Accounts accounts: manager){
            System.out.println("Username: "+accounts.getUsername() + " Catagory: " + accounts.getCatagory());
        }
        System.out.println("Select your choice by Username then catagory (enter seperatly)");
        String delname = in.nextLine();
        String delcatag = in.nextLine();
        // chat gpt for selecting the targeted items and deleting the object that contains those items by for loop to check all of them and delete the one that contains both variables
        // uses iterator to avoid a concurrent modifications execption     
        for(Iterator<Accounts> iterator = manager.iterator(); iterator.hasNext();){
            Accounts a = iterator.next();
            if (a.getCatagory().equals(delcatag) && a.getUsername().equals(delname)) {
                iterator.remove();
                break;
            }
        }    



        }


    }

