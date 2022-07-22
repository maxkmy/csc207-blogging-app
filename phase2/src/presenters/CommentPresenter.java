package presenters;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;

public class CommentPresenter {
    /**
     * a formatter to format LocalDateTime
     */
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

    /**
     * Display a comment to the user
     *
     * @param comment a comment to be displayed to the user
     */
    public void printComment(HashMap<String, String> comment) {
        System.out.println(comment.get("author") + ": " + comment.get("content"));
        LocalDateTime newDate = LocalDateTime.parse(comment.get("timePosted"));
        System.out.println("Time posted: " + formatter.format(newDate));
        System.out.println();
    }

    /**
     * Displays comments to users
     *
     * @param comments comments that need to be displayed to users
     */
    public void printComments(ArrayList<HashMap<String, String>> comments) {
        for (HashMap<String, String> comment : comments) {
            printComment(comment);
        }
    }
}
