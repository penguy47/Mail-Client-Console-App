import java.util.List;
import java.util.StringJoiner;

public class EmailFormatter {
    public static String formatToHeader(List<String> emailAddresses) {
        StringJoiner joiner = new StringJoiner(", ");
        for (String email : emailAddresses) {
            joiner.add(email);
        }
        return joiner.toString();
    }
}
