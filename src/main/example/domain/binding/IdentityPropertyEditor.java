package example.domain.binding;

import example.domain.Identity;

import java.beans.PropertyEditorSupport;

public class IdentityPropertyEditor extends PropertyEditorSupport {

    private boolean createOnNew;

    public IdentityPropertyEditor(boolean createOnNew) {
        this.createOnNew = createOnNew;
    }

    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        Identity id = Identity.fromValue(text);
        if (createOnNew && id.isNew()) {
            id = new Identity();
        }
        setValue(id);
    }

    @Override
    public String getAsText() {
        Identity id = (Identity) getValue();
        return id.getValue();
    }
}
