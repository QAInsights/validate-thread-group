package org.qainsights.jmeter.validatetg.gui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import javax.swing.AbstractAction;

class ValidateTGAction extends AbstractAction {

    ValidateTGAction() {
        super("Validate Thread Group");
        putValue(Action.ACTION_COMMAND_KEY, "validate_tg");
    }

    public void actionPerformed(ActionEvent actionEvent) {
        System.out.println("");
    }
}
