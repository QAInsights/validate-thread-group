package org.qainsights.jmeter.validatetg.gui;

import org.apache.jmeter.gui.GuiPackage;
import org.apache.jmeter.gui.MainFrame;
import org.apache.jmeter.gui.util.JMeterToolBar;
import org.apache.jmeter.gui.action.ActionRouter;
import org.apache.jmeter.util.JMeterUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.github.weisj.darklaf.icons.ThemedSVGIcon;

public class ValidateTGMenuItem extends JMenuItem implements ActionListener{
    private static final Logger log = LoggerFactory.getLogger(ValidateTGMenuItem.class);
    private static Action vtg = null;

    static {
        try {
            vtg = new ValidateTGAction();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public ValidateTGMenuItem() {
        super(vtg);
        addActionListener(this);
        addToolbarIcon();
    }

    public static Icon getButtonIcon() throws URISyntaxException {
        log.debug("Image");
        String svgResourcePath = "/org/qainsights/jmeter/validatetg/Check-Mark.svg";
        URL svgUrl = JMeterUtils.class.getResource(svgResourcePath);
        URI svgUri = null;
        svgUri = svgUrl.toURI();
        Icon icon = new ThemedSVGIcon(svgUri, 22, 22);

        return icon;
    }

    private void addToolbarIcon() {
        GuiPackage instance = GuiPackage.getInstance();
        if (instance != null) {
            final MainFrame mf = instance.getMainFrame();
            final ComponentFinder<JMeterToolBar> finder = new ComponentFinder<>(JMeterToolBar.class);
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    JMeterToolBar toolbar = null;
                    while (toolbar == null) {
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            log.debug("Couldn't add Validate Thread Group button to menubar", e);
                        }
                        log.debug("Searching for toolbar ... ");
                        toolbar = finder.findComponentIn(mf);
                    }
                    int pos = getPositionForIcon(toolbar.getComponents());
                    log.debug("validate rootPos: " + String.valueOf(pos));
                    Component toolbarButton = null;
                    try {
                        toolbarButton = getToolbarButton();
                    } catch (URISyntaxException e) {
                        e.printStackTrace();
                    }
                    toolbarButton.setSize(toolbar.getComponent(pos).getSize());
                    toolbar.add(toolbarButton, pos);
                }
            });
        }
    }
    private JButton getToolbarButton() throws URISyntaxException {
        JButton button = new JButton(getButtonIcon());
        button.setToolTipText("Validate Thread Group(s)");
        button.addActionListener(this);
        button.setActionCommand("validate_tg");
        return button;
    }

    private int getPositionForIcon(Component[] toolbarComponents) {
        int index = 0;
        for (Component item : toolbarComponents) {
            String itemClassName = item.getClass().getName();
            if(itemClassName.contains("javax.swing.JButton")) {
                String actionCommandText = ((JButton) item).getModel().getActionCommand();
                log.debug("Running for iteration: "+ index + ", " + actionCommandText);
                if (actionCommandText != null && actionCommandText.equals("start")){
                    break;
                }
            }
            index++;
        }
        return index;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        log.debug("Clicked" + e.getActionCommand());
        try {
            ActionRouter.getInstance().doActionNow(e);
        }
        catch (Exception err) {
            log.debug("Error while TG action performed: " + err);
        }
    }
}
