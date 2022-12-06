package gui;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.util.*;

import controller.*;
import object.*;

public class CheckoutPage implements ActionListener {
  GridBagConstraints gbc = new GridBagConstraints();

  JFrame mainFrame;
  ArrayList<Ticket> purchased;
  User user;
  int totalPrice;
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
  JLabel creditCardLabel = new JLabel("Credit Card: ");
  JTextField creditCard = new JTextField(32);
  JButton purchase = new JButton("Purchase");

  JLabel spacer = new JLabel();

  CheckoutPage(JFrame mainFrame, ArrayList<Ticket> purchased, User user) {
    this.mainFrame = mainFrame;
    this.purchased = purchased;
    this.user = user;

    checkoutPageSetup();

    mainFrame.getContentPane().removeAll();
    mainFrame.add(checkoutPage);
    mainFrame.validate();
  }

  private void tableSet() // USING THE INDEXS PASS, GET TICKET ITEMS AND ADD TO TABLE
  {
    String[] columnNames = { "Movie", "Date", "Time", "Seat", "Price" };
    Object[][] data = new Object[purchased.size()][5];
    for (int i = 0; i < purchased.size(); i++) {
      data[i][0] = purchased.get(i).getShowtime().getMTitle();
      data[i][1] = purchased.get(i).getShowtime().getShowDateTime().toLocalDate().toString();
      data[i][2] = purchased.get(i).getShowtime().getShowDateTime().toLocalTime().toString();
      data[i][3] = purchased.get(i).getTicketNo();
      data[i][4] = purchased.get(i).getPrice();
      totalPrice += purchased.get(i).getPrice();
    }

    tableModel = new DefaultTableModel(data, columnNames);
    movieTable = new JTable(tableModel);
    movieTable.getTableHeader().setReorderingAllowed(false);
    movieTable.getTableHeader().setResizingAllowed(false);
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
    try {
      FinanceManager finance = FinanceManager.getInstance();
      credit.setText(String.valueOf(finance.getTotalUserCredit(user)));
    } catch (Exception f) {
      JOptionPane.showMessageDialog(mainFrame, f.getMessage());
    }
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
    price.setText(String.valueOf(totalPrice));
    price.setEditable(false);
    checkoutPage.add(price, gbc);

    gbc.insets = new Insets(30, 5, 15, 5);
    gbc.gridwidth = 1;
    gbc.gridx = 0;
    gbc.gridy = 4;
    creditCardLabel.setPreferredSize(new Dimension(50, 30));
    checkoutPage.add(creditCardLabel, gbc);

    gbc.insets = new Insets(30, 5, 15, 5);
    gbc.gridwidth = 1;
    gbc.gridx = 1;
    gbc.gridy = 4;
    creditCard.setPreferredSize(new Dimension(50, 30));
    if (user.getType().equals("Registered")) {
      creditCard.setText(user.getCreditCard());
    }
    checkoutPage.add(creditCard, gbc);

    gbc.insets = new Insets(30, 5, 15, 5);
    gbc.gridwidth = 1;
    gbc.gridx = 3;
    gbc.gridy = 4;
    purchase.addActionListener(this);
    purchase.setPreferredSize(new Dimension(150, 30));
    checkoutPage.add(purchase, gbc);
  }

  public void actionPerformed(ActionEvent e) {
    if (e.getSource() == main) {
      new MainMenu(mainFrame, user);
    }
    if (e.getSource() == purchase) {
      String creditcard = creditCard.getText();
      TicketManager ticketM = TicketManager.getInstance();
      try {
        int res = ticketM.purchaseTickets(purchased, user, creditcard);
        if (res >= 0) {
          JOptionPane.showMessageDialog(mainFrame, "Tickets Purchased\nCard Charged: " + res);
          new MainMenu(mainFrame, user);
        } else {
          throw new Exception("Purchase Failed");
        }
      } catch (Exception f) {
        JOptionPane.showMessageDialog(mainFrame, f.getMessage());
      }
    }
  }
}