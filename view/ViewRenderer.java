package view;

import java.util.ArrayList;
import java.awt.Component;

import javax.swing.*;

public class ViewRenderer {
  JFrame frame;
  JPanel panel;

  public ViewRenderer() {
    frame = new JFrame();
    panel = new JPanel();
  }

  public JFrame getFrame() {
    return frame;
  }

  public JPanel getPanel() {
    return panel;
  }

  /**
   * Delete previous component and render new UI with JPanel
   * 
   * @param width
   * @param height
   * @param components
   */
  public void renderUI(int width, int height, ArrayList<Component> components) {
    panel.removeAll();

    for (int i = 0; i < components.size(); i++) {
      panel.add(components.get(i));
    }

    panel.setSize(width, height);
    panel.setLayout(null);
  }

  /**
   * Render general window
   * 
   * @param title
   * @param width
   * @param height
   */
  public void renderWindow(String title, int width, int height) {
    frame.setTitle(title);
    frame.setResizable(false);
    frame.setSize(width, height);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.getContentPane().add(panel);
    frame.repaint();
    frame.setVisible(true);
  }

  /**
   * Creates dialog with given message
   * 
   * @param message
   * @param type
   */
  public void createDialog(String message) {
    JDialog dialog = new JDialog(frame, "Information Dialog", true);
    JLabel dialogText = new JLabel(message);
    JButton dialogButton = new JButton("Return");

    dialog.setLayout(null);
    dialog.setBounds(frame.getX() + frame.getWidth() / 2 - 165, frame.getY() + frame.getHeight() / 2 - 75, 330, 150);
    dialog.setResizable(false);

    dialogText.setBounds(15, 10, 280, 50);
    dialogButton.setBounds(10, 55, 305, 35);

    dialogButton.addActionListener(e -> {
      dialog.setVisible(false);
    });

    dialog.add(dialogText);
    dialog.add(dialogButton);
    dialog.setVisible(true);
  }

  /**
   * Renders a UI component
   * 
   * @param title
   * @param width
   * @param height
   * @param components
   */
  public void render(String title, int width, int height, ArrayList<Component> components) {
    renderUI(width, height, components);
    renderWindow(title, width, height);
  }
}
