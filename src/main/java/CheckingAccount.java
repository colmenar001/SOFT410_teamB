/** A checking account. Uses the default withdraw rule from BankAccount. */
public class CheckingAccount extends BankAccount {

    @Override
    public String getName() {
        return "Checking";
    }
}
