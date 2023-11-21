package clients.customer;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class SimpleDocumentListener implements DocumentListener {

    @Override
    public void insertUpdate(DocumentEvent e) {
        onChange();
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        onChange();
    }

    @Override
    public void changedUpdate(DocumentEvent e) {
        onChange();
    }
    public void onChange(){

    }
}
