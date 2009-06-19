package example.domain.hibernate;

import example.domain.Identity;
import org.hibernate.HibernateException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

public class IdentityUserType extends ImmutableUserType {

    public int[] sqlTypes() {
        return new int[]{Types.VARCHAR};
    }

    public Class returnedClass() {
        return Identity.class;
    }

    public Object nullSafeGet(ResultSet rs, String[] names, Object owner) throws HibernateException, SQLException {
        String value = rs.getString(names[0]);
        if (value == null) {
            return null;
        }
        return Identity.fromValue(value);
    }

    public void nullSafeSet(PreparedStatement ps, Object value, int index) throws HibernateException, SQLException {
        if (value == null) {
            ps.setNull(index, Types.VARCHAR);
        } else {
            Identity id = (Identity) value;
            ps.setString(index, id.getValue());
        }
    }
}
