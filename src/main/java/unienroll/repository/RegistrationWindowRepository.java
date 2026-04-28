package unienroll.repository;

import unienroll.domain.RegistrationWindow;

public interface RegistrationWindowRepository {
    RegistrationWindow getWindow();
    void saveWindow(RegistrationWindow window);
}
