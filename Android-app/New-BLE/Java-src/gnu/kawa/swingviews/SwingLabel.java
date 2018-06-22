package gnu.kawa.swingviews;

import com.google.appinventor.components.common.PropertyTypeConstants;
import gnu.kawa.models.Label;
import gnu.kawa.models.Model;
import gnu.kawa.models.ModelListener;
import javax.swing.JLabel;

/* compiled from: SwingDisplay */
class SwingLabel extends JLabel implements ModelListener {
    Label model;

    public SwingLabel(Label model) {
        this.model = model;
        String text = model.getText();
        if (text != null) {
            super.setText(text);
        }
        model.addListener((ModelListener) this);
    }

    public void modelUpdated(Model model, Object key) {
        if (key == PropertyTypeConstants.PROPERTY_TYPE_TEXT && model == this.model) {
            super.setText(this.model.getText());
        }
    }

    public void setText(String text) {
        if (this.model == null) {
            super.setText(text);
        } else {
            this.model.setText(text);
        }
    }
}
