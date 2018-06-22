package gnu.kawa.swingviews;

import com.google.appinventor.components.common.PropertyTypeConstants;
import gnu.kawa.models.Button;
import gnu.kawa.models.Model;
import gnu.kawa.models.ModelListener;
import java.awt.Color;
import javax.swing.JButton;

public class SwingButton extends JButton implements ModelListener {
    Button model;

    public SwingButton(Button model) {
        super(model.getText());
        setModel(new SwModel(model));
        this.model = model;
        Object action = model.getAction();
        if (action != null) {
            addActionListener(SwingDisplay.makeActionListener(action));
        }
        model.addListener((ModelListener) this);
        Color fg = model.getForeground();
        if (fg != null) {
            super.setBackground(fg);
        }
        Color bg = model.getBackground();
        if (bg != null) {
            super.setBackground(bg);
        }
    }

    public void setText(String text) {
        if (this.model == null) {
            super.setText(text);
        } else {
            this.model.setText(text);
        }
    }

    public void setForeground(Color fg) {
        if (this.model == null) {
            super.setForeground(fg);
        } else {
            this.model.setForeground(fg);
        }
    }

    public void setBackground(Color bg) {
        if (this.model == null) {
            super.setBackground(bg);
        } else {
            this.model.setBackground(bg);
        }
    }

    public void modelUpdated(Model model, Object key) {
        if (key == PropertyTypeConstants.PROPERTY_TYPE_TEXT && model == this.model) {
            super.setText(this.model.getText());
        } else if (key == "foreground" && model == this.model) {
            super.setForeground(this.model.getForeground());
        } else if (key == "background" && model == this.model) {
            super.setBackground(this.model.getBackground());
        }
    }
}
