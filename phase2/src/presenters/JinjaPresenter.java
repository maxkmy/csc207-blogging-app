package presenters;
import com.hubspot.jinjava.Jinjava;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

public class JinjaPresenter {
    Map<String, Object> context;
    Jinjava jinja = new Jinjava();
    String template;
    // context should contain field -> List[Field], submitBtnName -> String
    public JinjaPresenter(Map<String, Object> context, String templatePath) throws IOException {
        this.context = context;
        try {
            template = Files.readString(Paths.get(templatePath));
        } catch(IOException e) { // TODO: handlers should redirect users to some error status code
            throw e;
        }
    }

    public String present() {
        return jinja.render(template, context);
    }
}
