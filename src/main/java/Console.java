import java.io.InputStream;
import java.io.PrintStream;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import static java.util.Collections.nCopies;

public final class Console {
    private final Scanner input;
    private final PrintStream output;

    public Console(InputStream in, PrintStream out){
        this.input = new Scanner(in);
        this.output = out;
    }

    private String getStringInput(String prompt){
        System.out.println(prompt);
        return input.nextLine();
    }

    private Integer getIntegerInput(String prompt){
        try {
            return Integer.parseInt(getStringInput(prompt));
        } catch (NumberFormatException n){
            println("That's not a number");
            return null;
        }
    }

    public void welcome(){
        println("\n:: Welcome to the Account Database ::\n");
    }

    public String mainMenu(){
        return getStringInput(">> select an option:" + " [ new account ] [ update ] [ query ] [ delete ]\n" +
                ">> or [ quit ] to close program");
    }

    public String getName(){
        return getStringInput(">> enter name:");
    }

    public String getId(){
        return getStringInput(">> enter id:");
    }

    public String getPassword(){
        return getStringInput(">> enter password:");
    }

    public String getBy(String crudCommand){
        return getStringInput(">> " + crudCommand + " by: [ name ] , [ id ] , or [ password ]\n>> or [ back ] to return");
    }

    public String getAllorBy(String crudCommand){
        return getStringInput(">> " + crudCommand + ": [ all ] or [ by ]\nor [ back ] to return");
    }

    public String areYouSure_delete(){
        return getStringInput(">> Are you sure you want to delete ALL of the accounts?" +
                "\n>> anything but [ YES ] will cancel this request");
    }

    public void println(String prompt){
        System.out.println(prompt);
    }

    public void print(String prompt){
        System.out.print(prompt);
    }

    public void returnToMain(){
        println("\n:: main menu ::\n");
    }

    public void accountDNE(){
        println("\n:: account does not exist ::\n");
    }

    public void printAccounts(List<Account> accountList){
        String five = "-----";
        String eightn = five + five + five + five + five;
        //header
        println(String.format("\n+ %5s-+-%25s-+-%25s +", five, eightn, eightn));
        println(String.format("| %5s | %25s | %25s |", "ID", "Name", "Password"));
        println(String.format("+ %5s-+-%25s-+-%25s +", five, eightn, eightn));
        //items
        accountList.forEach(e -> println(String.format("| %5s | %25s | %25s |", e.getId(), e.getName(), e.getPassword())));
        //footer
        println(String.format("+ %5s-+-%25s-+-%25s +\n", five, eightn, eightn));
    }

    public void goodBye(){
        println("[ peace ]");
    }
}
