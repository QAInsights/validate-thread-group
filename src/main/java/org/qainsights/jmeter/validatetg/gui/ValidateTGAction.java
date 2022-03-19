package org.qainsights.jmeter.validatetg.gui;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import javax.swing.AbstractAction;

class ValidateTGAction extends AbstractAction {

    public static final KeyStroke VALIDATE_TG   = KeyStroke.getKeyStroke(KeyEvent.VK_V, InputEvent.ALT_DOWN_MASK);
    private static final Logger log = LoggerFactory.getLogger(ValidateTGAction.class);

    ValidateTGAction() {
        super("Validate Thread Group(s)");
        putValue(Action.ACTION_COMMAND_KEY, "validate_tg");
        putValue(Action.ACCELERATOR_KEY, VALIDATE_TG);
        putValue(Action.SMALL_ICON, ValidateTGMenuItem.getButtonIcon(12));
    }

    public void actionPerformed(ActionEvent actionEvent) {
        log.debug("Validate Thread Groups Action performed from Run menu!");
    }
}
