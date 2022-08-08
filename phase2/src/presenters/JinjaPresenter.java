package presenters;
import com.hubspot.jinjava.Jinjava;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

public class JinjaPresenter {
    private Map<String, Object> context;
    private Jinjava jinja = new Jinjava();
    private String template;

    /**
     * Constructor for a JinjaPresenter
     *
     * @param context a mapping of strings to objects for reference in Jinja templates
     * @param templatePath a path to a Jinja template
     * @throws IOException templatePath leads to an non-existent file
     */
    public JinjaPresenter(Map<String, Object> context, String templatePath) throws IOException {
        this.context = context;
        try {
            template = Files.readString(Paths.get(templatePath));
        } catch(IOException e) {
            throw e;
        }
    }

    /**
     * Returns the HTML string after passing the context map into the template
     *
     * @return some HTML string
     */
    public String present() {
        return jinja.render(template, context);
    }
}
