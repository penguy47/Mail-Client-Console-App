package src.entity;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

public class Attachment {
    private File file;
    private String name;

    public Attachment(File file) {
        this.file = file;
        this.name = file.getName();
    }

    public Attachment(String name) { // Object with no source File
        this.file = null;
        this.name = name;
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

    public List<String> getBase64Encoded() throws IOException {
        final int CHUNK_SIZE = 10000;

        try (FileInputStream fis = new FileInputStream(file)) {
            byte[] fileBytes = fis.readAllBytes();
            String base64Encoded = Base64.getEncoder().encodeToString(fileBytes);

            List<String> base64Chunks = new ArrayList<>();
            for (int i = 0; i < base64Encoded.length(); i += CHUNK_SIZE) {
                int end = Math.min(i + CHUNK_SIZE, base64Encoded.length());
                base64Chunks.add(base64Encoded.substring(i, end));
            }

            for (int i = 0; i < base64Chunks.size(); i++) {
                base64Chunks.set(i, base64Chunks.get(i) + "\r\n");
            }
            
            return base64Chunks;
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
