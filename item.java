
// We need to import the java.sql package to use JDBC
import java.sql.*;

// for reading from the command line
import java.io.*;

// for the login window
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class item implements ActionListener
{
    // command line reader
    private BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

    private Connection con;

    // user is allowed 3 login attempts
    private int loginAttempts = 0;

    // components of the login window
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JFrame mainFrame;


    /*
     * constructs login window and loads JDBC driver
     */
    public item()
    {
        mainFrame = new JFrame("User Login");

        JLabel usernameLabel = new JLabel("Enter username: ");
        JLabel passwordLabel = new JLabel("Enter password: ");

        usernameField = new JTextField(10);
        passwordField = new JPasswordField(10);
        passwordField.setEchoChar('*');

        JButton loginButton = new JButton("Log In");

        JPanel contentPane = new JPanel();
        mainFrame.setContentPane(contentPane);


        // layout components using the GridBag layout manager

        GridBagLayout gb = new GridBagLayout();
        GridBagConstraints c = new GridBagConstraints();

        contentPane.setLayout(gb);
        contentPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // place the username label
        c.gridwidth = GridBagConstraints.RELATIVE;
        c.insets = new Insets(10, 10, 5, 0);
        gb.setConstraints(usernameLabel, c);
        contentPane.add(usernameLabel);

        // place the text field for the username
        c.gridwidth = GridBagConstraints.REMAINDER;
        c.insets = new Insets(10, 0, 5, 10);
        gb.setConstraints(usernameField, c);
        contentPane.add(usernameField);

        // place password label
        c.gridwidth = GridBagConstraints.RELATIVE;
        c.insets = new Insets(0, 10, 10, 0);
        gb.setConstraints(passwordLabel, c);
        contentPane.add(passwordLabel);

        // place the password field
        c.gridwidth = GridBagConstraints.REMAINDER;
        c.insets = new Insets(0, 0, 10, 10);
        gb.setConstraints(passwordField, c);
        contentPane.add(passwordField);

        // place the login button
        c.gridwidth = GridBagConstraints.REMAINDER;
        c.insets = new Insets(5, 10, 10, 10);
        c.anchor = GridBagConstraints.CENTER;
        gb.setConstraints(loginButton, c);
        contentPane.add(loginButton);

        // register password field and OK button with action event handler
        passwordField.addActionListener(this);
        loginButton.addActionListener(this);

        // anonymous inner class for closing the window
        mainFrame.addWindowListener(new WindowAdapter()
        {
            public void windowClosing(WindowEvent e)
            {
                System.exit(0);
            }
        });

        // size the window to obtain a best fit for the components
        mainFrame.pack();

        // center the frame
        Dimension d = mainFrame.getToolkit().getScreenSize();
        Rectangle r = mainFrame.getBounds();
        mainFrame.setLocation( (d.width - r.width)/2, (d.height - r.height)/2 );

        // make the window visible
        mainFrame.setVisible(true);

        // place the cursor in the text field for the username
        usernameField.requestFocus();

        try
        {
            // Load the Oracle JDBC driver
            DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
            // may be oracle.jdbc.driver.OracleDriver as of Oracle 11g
        }
        catch (SQLException ex)
        {
            System.out.println("Message: " + ex.getMessage());
            System.exit(-1);
        }
    }

    /*
     * connects to Oracle database named ug using user supplied username and password
     */
    private boolean connect(String username, String password)
    {
        String connectURL = "jdbc:oracle:thin:@dbhost.ugrad.cs.ubc.ca:1522:ug";

        try
        {
            con = DriverManager.getConnection(connectURL,username,password);

            System.out.println("\nConnected to Oracle!");
            return true;
        }
        catch (SQLException ex)
        {
            System.out.println("Message: " + ex.getMessage());
            return false;
        }
    }


    /*
     * event handler for login window
     */
    public void actionPerformed(ActionEvent e)
    {
        if ( connect(usernameField.getText(), String.valueOf(passwordField.getPassword())) )
        {
            // if the username and password are valid,
            // remove the login window and display a text menu
            mainFrame.dispose();
            showMenu();
        }
        else
        {
            loginAttempts++;

            if (loginAttempts >= 3)
            {
                mainFrame.dispose();
                System.exit(-1);
            }
            else
            {
                // clear the password
                passwordField.setText("");
            }
        }

    }

    /*
 * displays simple text interface
 */
    private void showMenu()
    {
        int choice;
        boolean quit;

        quit = false;

        try
        {
            // disable auto commit mode
            con.setAutoCommit(false);

            while (!quit)
            {
                System.out.print("\n\nPlease choose one of the following: \n");
                System.out.print("1.  Insert Item\n");
                System.out.print("2.  Delete Item\n");
                System.out.print("3.  Update Item price\n");
                System.out.print("4.  Find Item\n");
                System.out.print("5.  Show ALl Items\n");
                System.out.print("6.  Quit\n");

                choice = Integer.parseInt(in.readLine());

                System.out.println(" ");

                switch(choice)
                {
                    case 1:  insertItem(); break;
                    case 2:  deleteItem(); break;
                    case 3:  updateItemPrice(); break;
                    case 4:  findItem(); break;
                    case 5:  showItem(); break;
                    case 6:  quit = true;
                }
            }

            con.close();
            in.close();
            System.out.println("\nGood Bye!\n\n");
            System.exit(0);
        }
        catch (IOException e)
        {
            System.out.println("IOException!");

            try
            {
                con.close();
                System.exit(-1);
            }
            catch (SQLException ex)
            {
                System.out.println("Message: " + ex.getMessage());
            }
        }
        catch (SQLException ex)
        {
            System.out.println("Message: " + ex.getMessage());
        }
    }
    /*
        * inserts a Item
        */
    private void insertItem()
    {
        int                itemID;
        String             itemName;
        double             itemPrice;
        String             itemType;
        PreparedStatement  ps;

        try
        {
            ps = con.prepareStatement("INSERT INTO Item VALUES (?,?,?,?)");

            System.out.print("\nItem ID: ");
            itemID = Integer.parseInt(in.readLine());
            ps.setInt(1, itemID);

            System.out.print("\nItem Name: ");
            itemName = in.readLine();
            ps.setString(2, itemName);

            System.out.print("\nItem Price: ");
            itemPrice = Double.parseDouble(in.readLine());

            while(itemPrice <= 0){
                System.out.print("\nItem Price cannot be negative, please try again: ");
                itemPrice = Double.parseDouble(in.readLine());
            }

            ps.setDouble(3, itemPrice);

            System.out.print("\nItem type: ");
            itemType = in.readLine();
            ps.setString(4, itemType);

            ps.executeUpdate();

            // commit work
            con.commit();

            ps.close();
        }
        catch (IOException e)
        {
            System.out.println("IOException!");
        }
        catch (SQLException ex)
        {
            System.out.println("Message: " + ex.getMessage());
            try
            {
                // undo the insert
                con.rollback();
            }
            catch (SQLException ex2)
            {
                System.out.println("Message: " + ex2.getMessage());
                System.exit(-1);
            }
        }
    }

    /*
        * deletes a branch
        */
    private void deleteItem()
    {
        int                itemID;
        PreparedStatement  ps;

        try
        {
            ps = con.prepareStatement("DELETE FROM Item WHERE itemID = ?");

            System.out.print("\nItem ID: ");
            itemID = Integer.parseInt(in.readLine());
            ps.setInt(1, itemID);

            int rowCount = ps.executeUpdate();

            if (rowCount == 0)
            {
                System.out.println("\nItem " + itemID + " does not exist!");
            }

            con.commit();

            ps.close();
        }
        catch (IOException e)
        {
            System.out.println("IOException!");
        }
        catch (SQLException ex)
        {
            System.out.println("Message: " + ex.getMessage());

            try
            {
                con.rollback();
            }
            catch (SQLException ex2)
            {
                System.out.println("Message: " + ex2.getMessage());
                System.exit(-1);
            }
        }
    }

    /*
     * updates the name of a branch
     */
    private void updateItemPrice()
    {
        int                itemID;
        double             itemPrice;
        PreparedStatement  ps;

        try
        {
            ps = con.prepareStatement("UPDATE Item SET price = ? WHERE itemID = ?");

            System.out.print("\nItem ID: ");
            itemID = Integer.parseInt(in.readLine());
            ps.setInt(2, itemID);

            System.out.print("\nSet new price: ");
            itemPrice = Double.parseDouble(in.readLine());
            while(itemPrice < 0){
                System.out.print("\nItem Price cannot be negative, please try again: ");
                itemPrice = Double.parseDouble(in.readLine());
            }
            ps.setDouble(1, itemPrice);

            int rowCount = ps.executeUpdate();
            if (rowCount == 0)
            {
                System.out.println("\nItem " + itemID + " does not exist!");
            }

            con.commit();

            ps.close();
        }
        catch (IOException e)
        {
            System.out.println("IOException!");
        }
        catch (SQLException ex)
        {
            System.out.println("Message: " + ex.getMessage());

            try
            {
                con.rollback();
            }
            catch (SQLException ex2)
            {
                System.out.println("Message: " + ex2.getMessage());
                System.exit(-1);
            }
        }
    }


    /*
     * display information about branches
     */
    private void showItem()
    {
        String     itemID;
        String     itemName;
        String     itemPrice;
        String     itemType;
        Statement  stmt;
        ResultSet  rs;

        try
        {
            stmt = con.createStatement();

            rs = stmt.executeQuery("SELECT * FROM Item");

            // get info on ResultSet
            ResultSetMetaData rsmd = rs.getMetaData();

            // get number of columns
            int numCols = rsmd.getColumnCount();

            System.out.println(" ");

            // display column names;
            for (int i = 0; i < numCols; i++)
            {
                // get column name and print it

                System.out.printf("%-15s", rsmd.getColumnName(i+1));
            }

            System.out.println(" ");

            while(rs.next())
            {
                // for display purposes get everything from Oracle
                // as a string

                // simplified output formatting; truncation may occur

                itemID = rs.getString("itemID");
                System.out.printf("%-10.10s", itemID);

                itemName = rs.getString("name");
                System.out.printf("%-20.20s", itemName);

                itemPrice = rs.getString("price");
                System.out.printf("%-20.20s", itemPrice);

                itemType = rs.getString("type");
                System.out.printf("%-15.15s", itemType);

            }

            // close the statement;
            // the ResultSet will also be closed
            stmt.close();
        }
        catch (SQLException ex)
        {
            System.out.println("Message: " + ex.getMessage());
        }
    }


    //Find item according to the ItemID
    private void findItem()
    {
        int                 inputID;
        String              itemID;
        String              itemName;
        String              itemPrice;
        String              itemType;
        PreparedStatement   ps;
        ResultSet           rs;

        try
        {
            ps = con.prepareStatement("SELECT * FROM Item WHERE itemID = ? ");

            System.out.print("\nItem ID: ");
            inputID = Integer.parseInt(in.readLine());
            ps.setInt(1, inputID);

            rs = ps.executeQuery();

            // get info on ResultSet
            ResultSetMetaData rsmd = rs.getMetaData();

            // get number of columns
            int numCols = rsmd.getColumnCount();

            System.out.println(" ");

            // display column names;
            for (int i = 0; i < numCols; i++)
            {
                // get column name and print it

                System.out.printf("%-15s", rsmd.getColumnName(i+1));
            }

            System.out.println(" ");

            while(rs.next())
            {
                // for display purposes get everything from Oracle
                // as a string

                // simplified output formatting; truncation may occur

                itemID = rs.getString("itemID");
                System.out.printf("%-10.10s", itemID);

                itemName = rs.getString("name");
                System.out.printf("%-20.20s", itemName);

                itemPrice = rs.getString("price");
                System.out.printf("%-20.20s", itemPrice);

                itemType = rs.getString("type");
                System.out.printf("%-15.15s", itemID);

            }

            // close the statement;
            // the ResultSet will also be closed
            ps.close();
        }
        catch (IOException e)
        {
            System.out.println("IOException!");
        }
        catch (SQLException ex)
        {
            System.out.println("Message: " + ex.getMessage());
        }
    }
    public static void main(String args[])
    {
        item i = new item();
    }
}