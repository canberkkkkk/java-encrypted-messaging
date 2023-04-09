package view;

import java.util.ArrayList;
import java.util.Arrays;
import java.awt.Component;

import javax.swing.*;

public class MessageContentView {
  /**
   * Creates message content UI with given content
   * 
   * @param viewRenderer
   * @param content
   */
  public MessageContentView(ViewRenderer viewRenderer, String content) {
    JTextArea messageContent = new JTextArea("example message");
    JButton returnButton = new JButton("RETURN");
    JScrollPane scroll = new JScrollPane(messageContent, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
        JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

    messageContent.setText(content);
    messageContent.setEditable(false);
    messageContent.setLineWrap(true);
    scroll.setBounds(45, 30, 550, 300);
    returnButton.setBounds(270, 350, 120, 50);

    // Go back to home screen
    returnButton.addActionListener(e -> {
      new HomeView(viewRenderer);
    });

    viewRenderer.render("Message", 650, 450,
        new ArrayList<Component>(Arrays.asList(scroll, returnButton)));
  }
}
