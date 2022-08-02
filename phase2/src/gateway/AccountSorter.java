package gateway;

import entities.Account;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class AccountSorter implements IAccountSorter {
    private StringMatcher stringMatcher= new StringMatcher();

    private class AccountComparator implements Comparator<Account> {
        private String targetUsername;
        public AccountComparator(String targetUsername) {
            this.targetUsername = targetUsername;
        }

        @Override
        public int compare(Account a1, Account a2) {
            String username1 = a1.getUsername();
            String username2 = a2.getUsername();
            int editDistance1 = stringMatcher.editDistance(username1, targetUsername);
            int editDistance2 = stringMatcher.editDistance(username2, targetUsername);
            if (editDistance1 == editDistance2) {
                return 0;
            } else if (editDistance1 < editDistance2) {
                return -1;
            }
            return 1;
        }
    }

    /**
     * @inheritDoc
     */
    @Override
    public void sort(List<Account> accounts, String targetUsername, int limit) {
        Collections.sort(accounts, new AccountComparator(targetUsername));
    }
}
