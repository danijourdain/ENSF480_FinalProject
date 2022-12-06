package gui;


import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import controller.*;
import object.*;

public class SeatingPage implements ActionListener // NEED TO ADD SHOWTIME CLASS VARIABLE
{
  GridBagConstraints gbc = new GridBagConstraints();

  JFrame mainFrame;
  User user;
  Showtime showtime;
  ArrayList<Ticket> tickets = new ArrayList<Ticket>();
  ArrayList<Ticket> purchasedT = new ArrayList<Ticket>();
  JPanel seatingPage = new JPanel(new GridBagLayout());
  JPanel seatingTable = new JPanel(new GridLayout(7, 10));
  JButton main = new JButton("Main Page");
  DefaultTableModel tableModel;
  JTable movieTable;
  JButton checkout = new JButton("Checkout");
  ArrayList<String> purchased = new ArrayList<>();

  JLabel spacer = new JLabel();

  SeatingPage(JFrame mainFrame, User user, Showtime showtime) {
    this.mainFrame = mainFrame;
    this.user = user;
    this.showtime = showtime;

    seatingPageSetup();

    mainFrame.getContentPane().removeAll();
    mainFrame.add(seatingPage);
    mainFrame.validate();
  }

  private void tableSet() // USE SHOWTIME TO PULL SEAT INFORMATION. ADJUST PURCHASED ARRAY
  {
    TicketManager ticketM = TicketManager.getInstance();
    tickets = ticketM.getShowtimeTickets(showtime);
    JToggleButton[] array = new JToggleButton[30];
    for (int i = 0; i < array.length; i++) {
      if (tickets.get(i).getEmail() == null) {
        array[i] = new JToggleButton(String.valueOf(i + 1), false);
        array[i].addActionListener(listener);
        seatingTable.add(array[i]);
      } else {
        seatingTable.add(new JLabel("             X"));
      }
    }

    String[] columnNames = { "Movie", "Date", "Time", "Duration" };
    String Mtitle = showtime.getMTitle();
    String date = showtime.getShowDateTime().toLocalDate().toString();
    String time = showtime.getShowDateTime().toLocalTime().toString();
    String duration = null;
    try {
      duration = Integer.toString(ticketM.getMovie(showtime.getMTitle()).getDuration());
    } catch (Exception f) {
      JOptionPane.showMessageDialog(mainFrame, f.getMessage());
    }
    Object[][] data = {{Mtitle, date, time, duration}};

    tableModel = new DefaultTableModel(data, columnNames) {
      @Override
      public boolean isCellEditable(int row, int column) {
        return false;
      }
    };

    movieTable = new JTable(tableModel);
    movieTable.getTableHeader().setReorderingAllowed(false);
    movieTable.getTableHeader().setResizingAllowed(false);
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

  public void actionPerformed(ActionEvent e) {
    if (e.getSource() == main) {
      new MainPage(mainFrame, user);
    } else if (e.getSource() == checkout) {
      for (String j : purchased) {
        purchasedT.add(tickets.get(Integer.valueOf(j)));
      }
      new CheckoutPage(mainFrame, purchasedT, user);
    }
  }

  ActionListener listener = new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
      if (e.getSource() instanceof JToggleButton) {
        int seatNum = Integer.valueOf(((JToggleButton) e.getSource()).getText());
        if (((JToggleButton) e.getSource()).isSelected()) {
          purchased.add(String.valueOf(seatNum - 1));
        } else {
          purchased.remove(String.valueOf(seatNum - 1));
        }
      }
    }
  };
}