package gateway;

import entities.Account;

import java.util.List;

public interface IAccountSorter {
    /**
     * Sort the given ArrayList of comments by mutation
     *
     * @param accounts list of comments to be sorted
     * @param targetUsername the username of the search query
     * @param limit the number of top related accounts to be returned
     */
    void sort(List<Account> accounts, String targetUsername, int limit);
}
