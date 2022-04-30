package org.qainsights.jmeter.validatetg.gui;

import org.apache.jmeter.gui.GuiPackage;
import org.apache.jmeter.gui.MainFrame;
import org.apache.jmeter.gui.action.ActionRouter;
import org.apache.jmeter.gui.util.JMeterToolBar;
import org.apache.jmeter.threads.JMeterContextService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;


public class ValidateTGMenuItem extends JMenuItem implements ActionListener {

    private static final Logger log = LoggerFactory.getLogger(ValidateTGMenuItem.class);
    private static final Action vtg = new ValidateTGAction();
    private static JButton toolbarButton;


    public ValidateTGMenuItem() {
        super(vtg);
        addActionListener(this);
        toolbarButton = getToolbarButton();
        addToolbarIcon(toolbarButton);
    }

    public static ImageIcon getButtonIcon(int pixelSize) {
        String sizedImage = String.format("/org/qainsights/jmeter/validatetg/validate-tg-icon-%2dx%2d.png", pixelSize, pixelSize);
        return new ImageIcon(Objects.requireNonNull(ValidateTGMenuItem.class.getResource(sizedImage)));
    }

    private void addToolbarIcon(JButton toolbarButton) {
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
                    toolbarButton.setSize(toolbar.getComponent(pos).getSize());
                    toolbar.add(toolbarButton, pos);
                }
            });
        }
    }
    private JButton getToolbarButton() {
        JButton button = new JButton(getButtonIcon(22));
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
//            toolbarButton.setEnabled(false);
            if(JMeterContextService.getNumberOfThreads() < 1){
                ActionRouter.getInstance().doActionNow(e);
            }else{
                log.info("JMeter run/validation already in progress");
            }
        }catch (Exception err) {
            log.error("Error while TG action performed: " + err);
        }
    }
}
