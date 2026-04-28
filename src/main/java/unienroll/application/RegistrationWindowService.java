package unienroll.application;

import unienroll.domain.RegistrationWindow;
import unienroll.exception.ValidationException;
import unienroll.repository.RegistrationWindowRepository;

import java.time.LocalDateTime;

public class RegistrationWindowService {
    private final RegistrationWindowRepository repository;

    public RegistrationWindowService(RegistrationWindowRepository repository) {
        this.repository = repository;
    }

    public RegistrationWindow getRegistrationWindow() {
        return repository.getWindow();
    }

    public void setRegistrationWindow(LocalDateTime startDateTime, LocalDateTime endDateTime) {
        if (startDateTime == null || endDateTime == null) {
            throw new ValidationException("Start date and end date must not be null.");
        }
        if (startDateTime.isAfter(endDateTime)) {
            throw new ValidationException("Start date cannot be after end date.");
        }
        RegistrationWindow window = new RegistrationWindow(startDateTime, endDateTime);
        repository.saveWindow(window);
    }

    public boolean isRegistrationActive() {
        RegistrationWindow window = repository.getWindow();
        if (window == null) {
            return false;
        }
        return window.isActive(LocalDateTime.now());
    }
}
