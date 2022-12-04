import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
//import java.util.*;
//import java.sql.*;

public class SeatingPage implements ActionListener {
  public static void main(String args[]) 
  {
    new SeatingPage();
  }

  GridBagConstraints gbc = new GridBagConstraints();

  JFrame mainFrame = new JFrame("Depressed SENG Student Theatres"); //This class will return a panel for GUI to display not a new Jframe
  JPanel seatingPage = new JPanel(new GridBagLayout());
  JPanel seatingTable = new JPanel(new GridLayout(7, 10));
  JButton main = new JButton("Main Page");
  DefaultTableModel tableModel;
  JTable movieTable;
  JButton checkout = new JButton("Checkout");

  JLabel spacer = new JLabel();

  SeatingPage()
  {
    mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    seatingPageSetup();

    mainFrame.add(seatingPage);

    mainFrame.setExtendedState(JFrame.MAXIMIZED_BOTH); 
    mainFrame.setResizable(false);
    mainFrame.setVisible(true);
  }

  private void tableSet() {
    JToggleButton[] array = new JToggleButton[70];
    for (int i = 0; i < array.length; i++) {
      array[i] = new JToggleButton(String.valueOf(i+1));
      array[i].addActionListener(listener);
      seatingTable.add(array[i]);
    }

    String[] columnNames = {"Movie", "Date", "Time"};
    Object[][] data = {{"Selected Movie", "Date", "Time"}};

    tableModel = new DefaultTableModel(data, columnNames);
    movieTable = new JTable(tableModel);
    movieTable.getColumnModel().getColumn(0).setMinWidth(200);
  }
  

  private void seatingPageSetup() {
    gbc.anchor = GridBagConstraints.NORTH; 
    gbc.fill = GridBagConstraints.HORIZONTAL;

    tableSet();

    gbc.insets = new Insets(0, 5, 15, 5);
    gbc.gridwidth = 1;
    gbc.gridx = 0;
    gbc.gridy = 0;
    main.addActionListener(this);
    main.setPreferredSize(new Dimension(150, 30));
    seatingPage.add(main, gbc);

    gbc.insets = new Insets(15, 5, 15, 5);
    gbc.gridwidth = 3;
    gbc.gridx = 1;
    gbc.gridy = 0;
    spacer.setPreferredSize(new Dimension(150, 30));
    seatingPage.add(spacer, gbc);

    gbc.insets = new Insets(15, 5, 15, 5);
    gbc.gridwidth = 4;
    gbc.gridx = 0;
    gbc.gridy = 1;
    movieTable.setPreferredSize(new Dimension(600, 30));
    seatingPage.add(movieTable, gbc);  

    gbc.insets = new Insets(15, 5, 15, 5);
    gbc.gridwidth = 4;
    gbc.gridx = 0;
    gbc.gridy = 2;
    seatingTable.setPreferredSize(new Dimension(600, 400));
    seatingPage.add(seatingTable, gbc);  
    
    gbc.insets = new Insets(15, 5, 15, 5);
    gbc.gridwidth = 3;
    gbc.gridx = 0;
    gbc.gridy = 3;
    spacer.setPreferredSize(new Dimension(150, 30));
    seatingPage.add(spacer, gbc);  

    gbc.insets = new Insets(15, 5, 15, 5);
    gbc.gridwidth = 1;
    gbc.gridx = 3;
    gbc.gridy = 3;
    checkout.addActionListener(this);
    checkout.setPreferredSize(new Dimension(50, 30));
    seatingPage.add(checkout, gbc);
  }

  public void actionPerformed(ActionEvent e)
  {
    if (e.getSource() == main)
    {
      JOptionPane.showMessageDialog(mainFrame, "Return to main page");
    }
    else if (e.getSource() == checkout)
    {
      JOptionPane.showMessageDialog(mainFrame, "Checkout");
    }
  }

  ActionListener listener = new ActionListener() 
  {
    @Override
    public void actionPerformed(ActionEvent e) {
      if (e.getSource() instanceof JToggleButton) {
        String seatNum = ((JToggleButton) e.getSource()).getText();
        JOptionPane.showMessageDialog(mainFrame, "Selected seat: " + seatNum);
      }
    }
  };
}