package unienroll.infrastructure.file;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import unienroll.domain.RegistrationWindow;
import unienroll.repository.RegistrationWindowRepository;

import java.io.File;

public class FileRegistrationWindowRepository implements RegistrationWindowRepository {
    private final ObjectMapper mapper;
    private RegistrationWindow currentWindow;
    private final File file = new File("data/registration-time.json");

    private static FileRegistrationWindowRepository instance;

    private FileRegistrationWindowRepository() {
        mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        
        try {
            if (file.exists()) {
                currentWindow = mapper.readValue(file, RegistrationWindow.class);
            } else {
                File parent = file.getParentFile();
                if (parent != null && !parent.exists()) {
                    parent.mkdirs();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static FileRegistrationWindowRepository getInstance() {
        if (instance == null) {
            instance = new FileRegistrationWindowRepository();
        }
        return instance;
    }

    @Override
    public RegistrationWindow getWindow() {
        return currentWindow;
    }

    @Override
    public void saveWindow(RegistrationWindow window) {
        this.currentWindow = window;
        saveToFile();
    }

    private void saveToFile() {
        try {
            mapper.writerWithDefaultPrettyPrinter().writeValue(file, currentWindow);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
