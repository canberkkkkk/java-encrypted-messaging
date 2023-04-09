package view;

import java.util.ArrayList;
import java.util.Arrays;
import java.awt.Component;

import javax.swing.*;

import controller.UserController;
import response.ControllerResponse;

public class RegisterUserView {
  /**
   * Creates register user UI
   * 
   * @param viewRenderer
   */
  public RegisterUserView(ViewRenderer viewRenderer) {
    UserController userController = new UserController();

    JTextField usernameInput = new JTextField();
    JPasswordField userPasswordInput = new JPasswordField();
    JLabel usernameLabel = new JLabel("Username:*");
    JLabel userPasswordLabel = new JLabel("Password:*");
    JSeparator secondSeperator = new JSeparator();
    JCheckBox showPasswordCheckbox = new JCheckBox("Show password");
    JButton registerButton = new JButton("REGISTER");
    JButton homeButton = new JButton("HOME");

    usernameInput.setBounds(160, 30, 210, 40);
    usernameLabel.setBounds(20, 30, 200, 40);

    userPasswordInput.setBounds(160, 80, 210, 40);
    userPasswordLabel.setBounds(20, 80, 200, 40);

    secondSeperator.setBounds(20, 185, 350, 40);
    showPasswordCheckbox.setBounds(15, 130, 350, 40);

    registerButton.setBounds(15, 210, 170, 40);
    homeButton.setBounds(200, 210, 170, 40);

    // Show password
    showPasswordCheckbox.addActionListener(e -> {
      if (showPasswordCheckbox.isSelected())
        userPasswordInput.setEchoChar((char) 0);
      else
        userPasswordInput.setEchoChar('*');
    });

    // Register User
    registerButton.addActionListener(e -> {
      String username = usernameInput.getText();
      String password = String.valueOf(userPasswordInput.getPassword());
      ControllerResponse response = userController.createUser(username, password);

      viewRenderer.createDialog(response.getMessage());

      // Go to home screen if user is created
      if (response.getStatus() == ControllerResponse.SUCCESS_STATUS)
        new HomeView(viewRenderer);
    });

    // Go to home screen
    homeButton.addActionListener(e -> {
      new HomeView(viewRenderer);
    });

    viewRenderer.render("Register", 390, 310,
        new ArrayList<Component>(Arrays.asList(usernameInput, userPasswordInput, usernameLabel, userPasswordLabel,
            secondSeperator, showPasswordCheckbox, registerButton, homeButton)));
  }
}
