package windows;

import java.awt.*;

public abstract class NotificationServise {
    public static void showNotification(String title, String body) throws AWTException {
        if (SystemTray.isSupported()) {
            SystemTray tray = SystemTray.getSystemTray();
            Image image = Toolkit.getDefaultToolkit().getImage("images/tray.gif");
            TrayIcon trayIcon = new TrayIcon(image);
            tray.add(trayIcon);
            trayIcon.displayMessage(title, body, TrayIcon.MessageType.NONE);
        }
    }
}
