package example.domain.hibernate;

import org.hibernate.HibernateException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

public class EnumUserType extends ImmutableUserType {

    private final Class type;

    public EnumUserType(Class<? extends Enum> type) {
        this.type = type;
    }

    public int[] sqlTypes() {
        return new int[]{Types.VARCHAR};
    }

    public Class returnedClass() {
        return type;
    }

    @SuppressWarnings({"unchecked"})
    public Object nullSafeGet(ResultSet rs, String[] names, Object owner) throws HibernateException, SQLException {
        String value = rs.getString(names[0]);
        if (value == null) {
            return null;
        }
        return Enum.valueOf(type, value);
    }

    public void nullSafeSet(PreparedStatement ps, Object value, int index) throws HibernateException, SQLException {
        if (value == null) {
            ps.setNull(index, Types.VARCHAR);
        } else {
            Enum instance = (Enum) value;
            ps.setString(index, instance.name());
        }
    }
}