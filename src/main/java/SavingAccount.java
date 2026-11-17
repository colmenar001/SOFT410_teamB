/** A saving account. Uses the default withdraw rule from BankAccount. */
public class SavingAccount extends BankAccount {

    @Override
    public String getName() {
        return "Saving";
    }
}
