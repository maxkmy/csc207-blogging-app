package gateway;

import java.util.ArrayList;
import exception.ResultNotFoundException;
public interface ISearch {
    ArrayList<String> doSearch(String query) throws ResultNotFoundException;
}
