package filemanager.features;

import lombok.NonNull;

import java.time.Clock;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public interface DateTimeUtils {

    static ZonedDateTime getCurrentZonedDateTime() {
        return ZonedDateTime.now(Clock.systemDefaultZone());
    }

    static String getCurrentZonedTimestamp(@NonNull String timestampPattern) {
        return getCurrentZonedDateTime().format(DateTimeFormatter.ofPattern(timestampPattern));
    }
}
