package example.domain.hibernate;

import org.hibernate.HibernateException;
import org.joda.time.LocalDateTime;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;

public class LocalDateTimeUserType extends ImmutableUserType {

    public int[] sqlTypes() {
        return new int[]{Types.TIMESTAMP};
    }

    public Class returnedClass() {
        return LocalDateTime.class;
    }

    public Object nullSafeGet(ResultSet rs, String[] names, Object owner) throws HibernateException, SQLException {
        Timestamp ts = rs.getTimestamp(names[0]);
        if (ts == null) {
            return null;
        }
        return new LocalDateTime(ts);
    }

    public void nullSafeSet(PreparedStatement ps, Object value, int index) throws HibernateException, SQLException {
        if (value == null) {
            ps.setNull(index, Types.TIMESTAMP);
        } else {
            LocalDateTime dt = (LocalDateTime) value;
            long millis = dt.toDateTime().getMillis();
            ps.setTimestamp(index, new Timestamp(millis));
        }
    }
}
