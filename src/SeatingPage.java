import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
//import java.util.*;
//import java.sql.*;

public class SeatingPage implements ActionListener 
{
  GridBagConstraints gbc = new GridBagConstraints();

  JFrame mainFrame;
  User user;
  JPanel seatingPage = new JPanel(new GridBagLayout());
  JPanel seatingTable = new JPanel(new GridLayout(7, 10));
  JButton main = new JButton("Main Page");
  DefaultTableModel tableModel;
  JTable movieTable;
  JButton checkout = new JButton("Checkout");
  ArrayList<String> purchased1 = new ArrayList<>();

  JLabel spacer = new JLabel();

  SeatingPage(JFrame mainFrame, User user)
  {
    this.mainFrame = mainFrame;
    this.user = user;

    seatingPageSetup();

    mainFrame.getContentPane().removeAll(); 
    mainFrame.add(seatingPage);
    mainFrame.validate();
  }

  private void tableSet() 
  {
    int[] purchased = {1, 4, 6, 23, 52, 54};
    JToggleButton[] array = new JToggleButton[70];
    for(int i = 0; i < array.length; i++) {
      if(!check(purchased, i)) 
      {
        array[i] = new JToggleButton(String.valueOf(i+1), false);
        array[i].addActionListener(listener);
        seatingTable.add(array[i]);
      }
      else 
      {
        seatingTable.add(new JLabel("x"));
      }
    }

    String[] columnNames = {"Movie", "Date", "Time"};
    Object[][] data = {{"Selected Movie", "Date", "Time"}};

    tableModel = new DefaultTableModel(data, columnNames);
    movieTable = new JTable(tableModel);
    movieTable.getColumnModel().getColumn(0).setMinWidth(200);
  }
  
  private void seatingPageSetup() 
  {
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
    if(e.getSource() == main)
    {
      new MainMenu(mainFrame, user);
    }
    else if(e.getSource() == checkout)
    {
      new CheckoutPage(mainFrame, purchased1, user);
    }
  }

  ActionListener listener = new ActionListener() 
  {
    @Override
    public void actionPerformed(ActionEvent e) {
      if (e.getSource() instanceof JToggleButton) {
        String seatNum = ((JToggleButton) e.getSource()).getText();
        if(((JToggleButton) e.getSource()).isSelected())
        {
          JOptionPane.showMessageDialog(mainFrame, "Selected seat: " + seatNum);
          purchased1.add(seatNum);
        }
        else 
        {
          purchased1.remove(seatNum);
          JOptionPane.showMessageDialog(mainFrame, "Removed: " + seatNum);
        }
      }
    }
  };

  private static boolean check(int[] arr, int toCheckValue)
  {
    for (int element : arr) {
      if (element == toCheckValue) 
      {
        return true;
      }
    }
    return false;
  }
}