package example.domain;

import example.utils.Strings;
import org.joda.time.LocalDateTime;

public class DocumentDetails {

    private static final String DATE_TIME_PATTERN = "dd/MM/yyyy HH:mm:ss";

    private Identity id;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public DocumentDetails(Identity id, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Identity getId() {
        return id;
    }

    public String getCreatedAt() {
        return createdAt.toString(DATE_TIME_PATTERN);
    }

    public String getUpdatedAt() {
        return updatedAt.toString(DATE_TIME_PATTERN);
    }

    @Override
    public String toString() {
        return Strings.toString(this);
    }
}
