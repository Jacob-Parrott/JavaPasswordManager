import java.util.Scanner;

public class Accounts {
    Scanner ui = new Scanner(System.in);
    private String category, username, password, name;
    public Accounts(String name, String username, String password,String category){
        this.category = category;
        this.username = username;
        this.password = password;
        this.name = name;
    }

public Accounts(String username, String password,String category){
        this.category = category;
        this.username = username;
        this.password = password;
    }

    public String getCategory(){
        return this.category;
    }
    public void setCategory(String newCategory){
        this.category = newCategory;
    }

    public String getUsername(){
        return this.username;
    }

    public void setUsername(String newUsername){
        this.username = newUsername;
    }



    public String getPassword(){
        return this.password;
    }

    public void setPassword(String newPassword){
       this.password = newPassword;
    }

    public String getName(){
        return this.name;
    }
    public void setName(String newName){
        this.name = newName;
    }

        @Override
    public String toString(){
        String out = "";
        out += "\nAccount: "+ this.name +"\n";
        out += "Username: "+ this.username +"\n";
        out += "Password: "+ this.password +"\n";
        out += "Category: "+ this.category +"\n";
        out += "---------------------------------";
        return out;
    }    

}
