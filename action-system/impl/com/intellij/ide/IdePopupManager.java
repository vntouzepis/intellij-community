package com.intellij.ide;

import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.ui.popup.IdePopup;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public final class IdePopupManager implements IdeEventQueue.EventDispatcher {
  private static final Logger LOG = Logger.getInstance("com.intellij.ide.IdePopupManager");

  private IdePopup myActivePopup;

  boolean isPopupActive() {
    if (myActivePopup != null) {
      final Component component = myActivePopup.getComponent();
      if (component == null || !component.isShowing()) {
        myActivePopup = null;
        LOG.error("Popup is set up as active but not showing");
      }
    }
    return myActivePopup != null;
  }

  public boolean dispatch(AWTEvent e) {
    LOG.assertTrue(isPopupActive());

    if (e instanceof KeyEvent || e instanceof MouseEvent) {
      return myActivePopup.dispatch(e);
    }

    return false;
  }

  public void setActivePopup(IdePopup popup) {
    myActivePopup = popup;
  }

  public void resetActivePopup() {
    myActivePopup = null;
  }
}
