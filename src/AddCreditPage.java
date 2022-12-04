import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.*;
//import java.sql.*;

public class AddCreditPage implements ActionListener 
{  
  GridBagConstraints gbc = new GridBagConstraints();

  JFrame mainFrame;
  User user;
  JPanel addCreditPage = new JPanel(new GridBagLayout());
  JButton main = new JButton("Main Page");
  JLabel creditLabel = new JLabel("Add Credit: ");
  JTextField credit = new JTextField(32);
  JLabel creditCardLabel = new JLabel("Credit Card: ");
  JTextField creditCard = new JTextField(32);
  JButton purchase = new JButton("Purchase");

  JLabel spacer = new JLabel();
  
  AddCreditPage(JFrame mainFrame, ArrayList<String> seats, User user)
  {
    this.mainFrame = mainFrame;
    this.user = user;

    checkoutPageSetup();

    mainFrame.getContentPane().removeAll(); 
    mainFrame.add(addCreditPage);
    mainFrame.validate();
  }

  private void checkoutPageSetup() 
  {
    gbc.anchor = GridBagConstraints.NORTH; 
    gbc.fill = GridBagConstraints.HORIZONTAL;

    gbc.insets = new Insets(0, 5, 15, 5);
    gbc.gridwidth = 1;
    gbc.gridx = 0;
    gbc.gridy = 0;
    main.addActionListener(this);
    main.setPreferredSize(new Dimension(150, 30));
    addCreditPage.add(main, gbc);

    gbc.insets = new Insets(0, 5, 15, 5);
    gbc.gridwidth = 3;
    gbc.gridx = 1;
    gbc.gridy = 0;
    spacer.setPreferredSize(new Dimension(225, 30));
    addCreditPage.add(spacer, gbc);

    gbc.insets = new Insets(15, 5, 15, 5);
    gbc.gridwidth = 4;
    gbc.gridx = 0;
    gbc.gridy = 1;
    ticketsLabel.setPreferredSize(new Dimension(50, 30));
    addCreditPage.add(ticketsLabel, gbc);

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
    checkoutPage.add(price, gbc);                                          //CALCULATE PRICE

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
    if(user.getType() == "R") {
      //creditCard.setText(user.getCreditCard());
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

  public void actionPerformed(ActionEvent e)
  {
    if(e.getSource() == main)
    {
      new MainMenu(mainFrame, user);
    }
    if(e.getSource() == purchase)
    {
      String creditcard = creditCard.getText();
      JOptionPane.showMessageDialog(mainFrame, "Purchase Tickets " + creditcard); //PURCHASE FUNCTION
    }
  }
}
