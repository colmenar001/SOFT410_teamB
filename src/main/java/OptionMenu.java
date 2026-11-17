import java.text.DecimalFormat; // Import for formatting balance outputs as currency
import java.util.HashMap; // Import for storing customer number and PIN pairs
import java.util.Scanner; // Import for capturing user input from the console

public class OptionMenu extends Account {

    Scanner menuInput = new Scanner(System.in); // Scanner object for reading user input
    DecimalFormat moneyFormat = new DecimalFormat("'$'###,##0.00"); // Currency formatter

    HashMap<Integer, Integer> data = new HashMap<>(); // Stores customer number as key, PIN as value

    // Composition: the menu holds one account object of each type
    private final BankAccount checking = new CheckingAccount();
    private final BankAccount saving = new SavingAccount();

    // Handles login process
    public void getLogin() {
        int x = 1;
        do {
            try {
                // Predefined dummy accounts
                data.put(952141, 191904);
                data.put(989947, 717976);

                System.out.println("Welcome to ATM");
                System.out.println("Enter your Customer Number");
                setCustomerNumber(menuInput.nextInt()); // Reads and sets customer number

                System.out.println("Enter your PIN Number");
                setPinNumber(menuInput.nextInt()); // Reads and sets PIN
            }
            catch(Exception e) {
                // Handles non-numeric input
                System.out.println("\nInvalid Characters Only Numbers Allowed\n" + e);
                x = 2; // Prevents loop continuation on error
            }

            int cn = getCustomerNumber();
            int pn = getPinNumber();

            // Validates login credentials
            if (data.containsKey(cn) && data.get(cn) == pn) {
                getAccountType(); // Proceed to account menu
            }
            else {
                System.out.println("\nWrong Customer Number or Wrong PIN Number\n\n");
            }
        } while (x == 1); // Loop continues until valid login or exception
    }

    // Displays account type options
    public void getAccountType() {
        System.out.println("Select Account Type you want to Access");
        System.out.println("Type 1 - Checking Account");
        System.out.println("Type 2 - Savings Account");
        System.out.println("Type 3 - Exit");

        int selection = menuInput.nextInt();

        // Handles user selection
        switch (selection) {
            case 1:
                getChecking(); // Redirects to checking account menu
                break;
            case 2:
                getSaving();   // Redirects to saving account menu
                break;
            case 3:
                System.out.println("Thank you for using ATM, BYE\n"); // Exit message
                break;
            default:
                System.out.println("\n Invalid Choice \n"); // Handles invalid input
                break;
        }
    }

    // Menu for checking account options
    public void getChecking() {
        System.out.println("Checking Account");
        System.out.println("Type 1 - View Balance");
        System.out.println("Type 2 - Withdraw Money");
        System.out.println("Type 3 - Deposit Funds");
        System.out.println("Type 4 - Exit");

        int selection = menuInput.nextInt();

        // Handles user selection
        switch (selection) {
            case 1:
                // Display balance
                System.out.println("Checking Account Balance: " + moneyFormat.format(checking.getBalance()));
                getAccountType(); // Return to main menu
                break;
            case 2:
                getWithdrawInput(checking); // Perform withdrawal
                getAccountType();
                break;
            case 3:
                getDepositInput(checking); // Perform deposit
                getAccountType();
                break;
            case 4:
                System.out.println("Thank you for using ATM, Bye"); // Exit message
                break;
            default:
                System.out.println("\nInvalid Choice\n");
                getChecking(); // Re-prompt on invalid input
                break;
        }
    }

    // Menu for saving account options
    public void getSaving() {
        System.out.println("Saving Account");
        System.out.println("Type 1 - View Balance");
        System.out.println("Type 2 - Withdraw Money");
        System.out.println("Type 3 - Deposit Funds");
        System.out.println("Type 4 - Exit");
        System.out.print("Choice: ");

        int selection = menuInput.nextInt();

        // Handles user selection
        switch (selection) {
            case 1:
                // Display balance
                System.out.println("Saving Account Balance: " + moneyFormat.format(saving.getBalance()));
                getAccountType(); // Return to main menu
                break;
            case 2:
                getWithdrawInput(saving); // Perform withdrawal
                getAccountType();
                break;
            case 3:
                getDepositInput(saving); // Perform deposit
                getAccountType();
                break;
            case 4:
                System.out.println("Thank you for using ATM, Bye\n"); // Exit message
                break;
            default:
                System.out.println("\nInvalid Choice\n");
                getChecking(); // Re-prompt (note: this may be intended to redirect to saving, consider reviewing)
                break;
        }
    }

    // Shared withdraw prompt for any account type
    public void getWithdrawInput(BankAccount account) {
        String name = account.getName();
        System.out.println(name + " Account Balance: " + moneyFormat.format(account.getBalance()));
        System.out.print("Amount to withdraw from " + name + " Account: ");
        double amount = menuInput.nextDouble();

        switch (account.withdraw(amount)) {
            case INVALID_AMOUNT:
                System.out.println("Invalid amount. Please enter a positive number.");
                break;
            case INSUFFICIENT_FUNDS:
                System.out.println("Not enough funds.");
                break;
            case OK:
                System.out.println("New " + name + " Account Balance: " + moneyFormat.format(account.getBalance()));
                break;
        }
    }

    // Shared deposit prompt for any account type
    public void getDepositInput(BankAccount account) {
        String name = account.getName();
        System.out.println(name + " Account Balance: " + moneyFormat.format(account.getBalance()));
        System.out.print("Amount to deposit to " + name + " Account: ");
        double amount = menuInput.nextDouble();

        if (account.deposit(amount) == BankAccount.Result.INVALID_AMOUNT) {
            System.out.println("Invalid amount. Please enter a positive number.");
        } else {
            System.out.println("New " + name + " Account Balance: " + moneyFormat.format(account.getBalance()));
        }
    }
}
