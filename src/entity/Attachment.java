package src.entity;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Base64;

public class Attachment {
    private File file;
    private String name;

    public Attachment(File file) {
        this.file = file;
        this.name = file.getName();
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBase64Encoded() throws IOException {
        try (FileInputStream fis = new FileInputStream(file)) {
            byte[] fileBytes = fis.readAllBytes();
            return Base64.getEncoder().encodeToString(fileBytes);
        }
    }

    @Override
    public String toString() {
        return "Attachment{" +
                "file=" + file +
                ", name='" + name + '\'' +
                '}';
    }
}
