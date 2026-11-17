/**
 * Abstract base class for a bank account (Template Method pattern).
 *
 * withdraw() and deposit() are final template methods that define the
 * fixed steps of each transaction: validate the amount, check whether the
 * transaction is allowed, then update the balance. Subclasses customize
 * the behavior through the canWithdraw() hook and supply getName().
 */
public abstract class BankAccount {

    /** Outcome of a withdraw or deposit attempt. */
    public enum Result {
        OK,
        INVALID_AMOUNT,
        INSUFFICIENT_FUNDS
    }

    private double balance = 0;

    /** Template method: withdraws amount if it is valid and allowed. */
    public final Result withdraw(double amount) {
        if (amount <= 0) {
            return Result.INVALID_AMOUNT;
        }
        if (!canWithdraw(amount)) {
            return Result.INSUFFICIENT_FUNDS;
        }
        balance -= amount;
        return Result.OK;
    }

    /** Template method: deposits amount if it is valid. */
    public final Result deposit(double amount) {
        if (amount <= 0) {
            return Result.INVALID_AMOUNT;
        }
        balance += amount;
        return Result.OK;
    }

    /**
     * Hook: decides whether a (positive) amount may be withdrawn.
     * Default rule: the balance may not go below zero.
     */
    protected boolean canWithdraw(double amount) {
        return balance - amount >= 0;
    }

    public double getBalance() {
        return balance;
    }

    /** Name used in console prompts, e.g. "Checking" or "Saving". */
    public abstract String getName();
}
