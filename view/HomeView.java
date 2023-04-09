package view;

import java.util.ArrayList;
import java.util.Arrays;
import java.awt.Component;
import java.awt.Font;

import javax.swing.*;

public class HomeView {
  /**
   * Creates home screen UI, does navigation between UI components
   * 
   * @param viewRenderer
   */
  public HomeView(ViewRenderer viewRenderer) {
    JLabel welcomeText = new JLabel("Welcome to MessageBox");
    JButton registerButton = new JButton("Register as User");
    JButton accessButton = new JButton("Access Message");
    JButton sendMessageButton = new JButton("Leave message");

    welcomeText.setFont(new Font("sans-serif", Font.BOLD, 30));
    welcomeText.setBounds(55, 30, 400, 50);
    registerButton.setBounds(150, 120, 200, 50);
    accessButton.setBounds(150, 180, 200, 50);
    sendMessageButton.setBounds(150, 240, 200, 50);

    registerButton.addActionListener(e -> {
      new RegisterUserView(viewRenderer);
    });

    accessButton.addActionListener(e -> {
      new AccessMessageView(viewRenderer);
    });

    sendMessageButton.addActionListener(e -> {
      new RegisterMessageView(viewRenderer);
    });

    viewRenderer.render("Main Page", 500, 400,
        new ArrayList<Component>(Arrays.asList(welcomeText, registerButton, accessButton, sendMessageButton)));
  }
}
