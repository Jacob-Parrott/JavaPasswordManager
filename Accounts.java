import java.util.Scanner;

public class Accounts {
    Scanner ui = new Scanner(System.in);
    private String catagory, username, password, name;
    public Accounts(String name, String username, String password,String catagory){
        this.catagory = catagory;
        this.username = username;
        this.password = password;
        this.name = name;
    }


    public String getCatagory(){
        return this.catagory;
    }
    public void setCatagory(String newCatagory){
        this.catagory = newCatagory;
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
        out += "Catagory: "+ this.catagory +"\n";
        out += "---------------------------------";
        return out;
    }    

}
