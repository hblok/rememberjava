package com.rememberjava.ui;

import java.awt.AWTException;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;

public class TestTrayIcon {

  private PopupMenu popup;

  public static void main(String[] args) throws Exception {
    TestTrayIcon test = new TestTrayIcon();
    test.initMenu();
    test.initIcon();
  }

  private void initMenu() {
    popup = new PopupMenu();

    addMenuItem("item1");
    addMenuItem("item2");
  }

  private void addMenuItem(String label) {
    MenuItem item = new MenuItem(label);
    item.addActionListener(this::click);
    popup.add(item);
  }

  private void click(ActionEvent e) {
    System.out.println("Clicked: " + e.getSource());
  }

  private void initIcon() throws AWTException {
    if (!SystemTray.isSupported()) {
      System.err.println("System tray not supported.");
      return;
    }

    Image image = Toolkit.getDefaultToolkit().getImage(
        "com/rememberjava/ui/favicon_16.png");

    TrayIcon trayIcon = new TrayIcon(image, "RJ Tray Demo", popup);
    trayIcon.setImageAutoSize(true);

    SystemTray tray = SystemTray.getSystemTray();
    tray.add(trayIcon);
  }
}
