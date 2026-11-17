import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AccountSmokeTest {
    @Test
    void depositIncreasesCheckingBalance() {
        Account account = new Account();

        account.calcCheckingDeposit(50.00);

        assertEquals(50.00, account.getCheckingBalance());
    }

    @Test
    void withdrawalDecreasesSavingsBalance() {
        Account account = new Account();
        account.calcSavingDeposit(100.00);

        account.calcSavingWithdraw(25.00);

        assertEquals(75.00, account.getSavingBalance());
    }
}
