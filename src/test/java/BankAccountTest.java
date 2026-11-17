import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

/**
 * Tests the BankAccount template methods through both subclasses
 * (CheckingAccount and SavingAccount).
 */
class BankAccountTest {

    private static final double DELTA = 1e-9;

    /** Supplies a fresh account of each subclass to every test. */
    static Stream<Arguments> accounts() {
        return Stream.of(
                Arguments.of(new CheckingAccount()),
                Arguments.of(new SavingAccount()));
    }

    @ParameterizedTest
    @MethodSource("accounts")
    @DisplayName("New account starts with a zero balance")
    void newAccountHasZeroBalance(BankAccount account) {
        assertEquals(0.0, account.getBalance(), DELTA);
    }

    @ParameterizedTest
    @MethodSource("accounts")
    @DisplayName("Valid deposit returns OK and increases the balance")
    void validDeposit(BankAccount account) {
        assertEquals(BankAccount.Result.OK, account.deposit(100.50));
        assertEquals(100.50, account.getBalance(), DELTA);
    }

    @ParameterizedTest
    @MethodSource("accounts")
    @DisplayName("Valid withdraw returns OK and decreases the balance")
    void validWithdraw(BankAccount account) {
        account.deposit(200);
        assertEquals(BankAccount.Result.OK, account.withdraw(75.25));
        assertEquals(124.75, account.getBalance(), DELTA);
    }

    @ParameterizedTest
    @MethodSource("accounts")
    @DisplayName("Withdrawing the exact balance is allowed")
    void withdrawExactBalance(BankAccount account) {
        account.deposit(50);
        assertEquals(BankAccount.Result.OK, account.withdraw(50));
        assertEquals(0.0, account.getBalance(), DELTA);
    }

    @ParameterizedTest
    @MethodSource("accounts")
    @DisplayName("Withdrawing more than the balance returns INSUFFICIENT_FUNDS")
    void withdrawMoreThanBalance(BankAccount account) {
        account.deposit(100);
        assertEquals(BankAccount.Result.INSUFFICIENT_FUNDS, account.withdraw(100.01));
    }

    @ParameterizedTest
    @MethodSource("accounts")
    @DisplayName("Failed withdraw does not change the balance")
    void failedWithdrawLeavesBalanceUnchanged(BankAccount account) {
        account.deposit(100);
        account.withdraw(500);
        assertEquals(100.0, account.getBalance(), DELTA);
        account.withdraw(0);
        account.withdraw(-20);
        assertEquals(100.0, account.getBalance(), DELTA);
    }

    @ParameterizedTest
    @ValueSource(doubles = {0.0, -0.01, -50.0})
    @DisplayName("Zero or negative deposit returns INVALID_AMOUNT (both accounts)")
    void zeroOrNegativeDeposit(double amount) {
        for (BankAccount account : new BankAccount[] {new CheckingAccount(), new SavingAccount()}) {
            account.deposit(10);
            assertEquals(BankAccount.Result.INVALID_AMOUNT, account.deposit(amount), account.getName());
            assertEquals(10.0, account.getBalance(), DELTA, account.getName());
        }
    }

    @ParameterizedTest
    @ValueSource(doubles = {0.0, -0.01, -50.0})
    @DisplayName("Zero or negative withdraw returns INVALID_AMOUNT (both accounts)")
    void zeroOrNegativeWithdraw(double amount) {
        for (BankAccount account : new BankAccount[] {new CheckingAccount(), new SavingAccount()}) {
            account.deposit(10);
            assertEquals(BankAccount.Result.INVALID_AMOUNT, account.withdraw(amount), account.getName());
            assertEquals(10.0, account.getBalance(), DELTA, account.getName());
        }
    }

    @ParameterizedTest
    @MethodSource("accounts")
    @DisplayName("Invalid amount is reported before insufficient funds")
    void invalidAmountCheckedFirst(BankAccount account) {
        // balance is 0, so a negative withdraw must be INVALID_AMOUNT, not INSUFFICIENT_FUNDS
        assertEquals(BankAccount.Result.INVALID_AMOUNT, account.withdraw(-1));
    }

    @Test
    @DisplayName("Subclasses report the names used in console prompts")
    void accountNames() {
        assertEquals("Checking", new CheckingAccount().getName());
        assertEquals("Saving", new SavingAccount().getName());
    }
}
