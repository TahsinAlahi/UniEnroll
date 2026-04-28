package unienroll.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDateTime;

public class RegistrationWindow {
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;

    @JsonCreator
    public RegistrationWindow(
            @JsonProperty("startDateTime") LocalDateTime startDateTime,
            @JsonProperty("endDateTime") LocalDateTime endDateTime) {
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
    }

    public LocalDateTime getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(LocalDateTime startDateTime) {
        this.startDateTime = startDateTime;
    }

    public LocalDateTime getEndDateTime() {
        return endDateTime;
    }

    public void setEndDateTime(LocalDateTime endDateTime) {
        this.endDateTime = endDateTime;
    }

    public boolean isActive(LocalDateTime currentTime) {
        if (startDateTime == null || endDateTime == null) {
            return false;
        }
        return !currentTime.isBefore(startDateTime) && !currentTime.isAfter(endDateTime);
    }
}
