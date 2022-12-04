import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.*;
//import java.sql.*;

public class CheckoutPage implements ActionListener {  
  GridBagConstraints gbc = new GridBagConstraints();

  JFrame mainFrame;
  ArrayList<String> seats;
  User user;
  JPanel checkoutPage = new JPanel(new GridBagLayout());
  JButton main = new JButton("Main Page");
  JLabel ticketsLabel = new JLabel("Tickets");
  DefaultTableModel tableModel;
  JTable movieTable;
  JScrollPane movieScroll;
  JLabel creditLabel = new JLabel("Credit: ");
  JTextField credit = new JTextField(32);
  JLabel priceLabel = new JLabel("Price: ");
  JTextField price = new JTextField(32);
  JButton purchase = new JButton("Purchase");

  JLabel spacer = new JLabel();
  
  CheckoutPage(JFrame mainFrame, ArrayList<String> seats, User user)
  {
    this.mainFrame = mainFrame;
    this.seats = seats;
    this.user = user;

    checkoutPageSetup();

    mainFrame.getContentPane().removeAll(); 
    mainFrame.add(checkoutPage);
    mainFrame.validate();
  }

  private void tableSet() {
    String[] columnNames = {"Movie", "Date", "Time", "Seat", "Price"};
    Object[][] data =
    {
      {"Movie1", "Feb 14", "7:00 pm", seats.get(0), ""},
      {"Movie2", "Feb 14", "7:00 pm", seats.get(1), ""},
      {"Movie3", "Feb 14", "7:00 pm", "", ""},
      {"Movie4", "Feb 14", "7:00 pm", "", ""},
      {"Movie5", "Feb 14", "7:00 pm", "", ""},
      {"Movie6", "Feb 14", "7:00 pm", "", ""},
    };
 
    tableModel = new DefaultTableModel(data, columnNames);
    movieTable = new JTable(tableModel);
    movieTable.getColumnModel().getColumn(0).setMinWidth(200);
    movieScroll = new JScrollPane(movieTable);
  }

  private void checkoutPageSetup() {
    gbc.anchor = GridBagConstraints.NORTH; 
    gbc.fill = GridBagConstraints.HORIZONTAL;

    tableSet();

    gbc.insets = new Insets(0, 5, 15, 5);
    gbc.gridwidth = 1;
    gbc.gridx = 0;
    gbc.gridy = 0;
    main.addActionListener(this);
    main.setPreferredSize(new Dimension(150, 30));
    checkoutPage.add(main, gbc);

    gbc.insets = new Insets(0, 5, 15, 5);
    gbc.gridwidth = 3;
    gbc.gridx = 1;
    gbc.gridy = 0;
    spacer.setPreferredSize(new Dimension(225, 30));
    checkoutPage.add(spacer, gbc);

    gbc.insets = new Insets(15, 5, 15, 5);
    gbc.gridwidth = 4;
    gbc.gridx = 0;
    gbc.gridy = 1;
    ticketsLabel.setPreferredSize(new Dimension(50, 30));
    checkoutPage.add(ticketsLabel, gbc);

    gbc.insets = new Insets(15, 0, 0, 0);
    gbc.gridwidth = 4;
    gbc.gridx = 0;
    gbc.gridy = 2;
    movieScroll.setPreferredSize(new Dimension(225, 200));
    checkoutPage.add(movieScroll, gbc);

    gbc.insets = new Insets(15, 5, 15, 5);
    gbc.gridwidth = 1;
    gbc.gridx = 0;
    gbc.gridy = 3;
    creditLabel.setPreferredSize(new Dimension(50, 30));
    checkoutPage.add(creditLabel, gbc);

    gbc.insets = new Insets(15, 5, 15, 5);
    gbc.gridwidth = 1;
    gbc.gridx = 1;
    gbc.gridy = 3;
    credit.setPreferredSize(new Dimension(50, 30));
    credit.setEditable(false);
    checkoutPage.add(credit, gbc);

    gbc.insets = new Insets(15, 5, 15, 5);
    gbc.gridwidth = 1;
    gbc.gridx = 2;
    gbc.gridy = 3;
    priceLabel.setPreferredSize(new Dimension(50, 30));
    checkoutPage.add(priceLabel, gbc);

    gbc.insets = new Insets(15, 5, 15, 5);
    gbc.gridwidth = 1;
    gbc.gridx = 3;
    gbc.gridy = 3;
    credit.setPreferredSize(new Dimension(50, 30));
    price.setEditable(false);
    checkoutPage.add(price, gbc);

    gbc.insets = new Insets(30, 5, 15, 5);
    gbc.gridwidth = 1;
    gbc.gridx = 3;
    gbc.gridy = 4;
    purchase.addActionListener(this);
    purchase.setPreferredSize(new Dimension(150, 30));
    checkoutPage.add(purchase, gbc);
  }

  public void actionPerformed(ActionEvent e)
  {
    if(e.getSource() == main)
    {
      new MainMenu(mainFrame, user);
    }
    else if(e.getSource() == purchase)
    {
      JOptionPane.showMessageDialog(mainFrame, "Purchase Tickets");
    }
  }
}