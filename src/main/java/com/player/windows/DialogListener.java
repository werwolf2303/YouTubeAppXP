package com.player.windows;

import com.libdialog.events.EmptyDialogEvents;

import javax.swing.*;

public class DialogListener implements EmptyDialogEvents {
    @Override
    public void close(JDialog jDialog) {
        jDialog.dispose();
    }

    @Override
    public void open(JDialog jDialog) {

    }
}
