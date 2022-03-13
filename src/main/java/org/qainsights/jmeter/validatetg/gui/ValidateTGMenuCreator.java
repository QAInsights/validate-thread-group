package org.qainsights.jmeter.validatetg.gui;

import org.apache.jmeter.gui.plugin.MenuCreator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;

public class ValidateTGMenuCreator implements  MenuCreator {
    private static final Logger log = LoggerFactory.getLogger(ValidateTGMenuCreator.class);

    @Override
    public JMenuItem[] getMenuItemsAtLocation(MENU_LOCATION location) {
        if (location == MENU_LOCATION.RUN) {
            try {
                return new JMenuItem[]{new ValidateTGMenuItem()};
            } catch (Throwable e) {
                log.error("Failed to load validate thread group plugin", e);
                return new JMenuItem[0];
            }

        } else {
            return new JMenuItem[0];
        }
    }

    @Override
    public JMenu[] getTopLevelMenus() {
        return new JMenu[0];
    }

    @Override
    public boolean localeChanged(MenuElement menu) {
        return false;
    }

    @Override
    public void localeChanged() {
    }
}
