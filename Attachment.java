import java.io.File;

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

    @Override
    public String toString() {
        return "Attachment{" +
                "file=" + file +
                ", name='" + name + '\'' +
                '}';
    }
}
