import java.awt.BorderLayout;
import java.awt.GraphicsConfiguration;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class IDForm extends JFrame {

    private final JTextField idField;
    private final JTextField nameField;
    private final JRadioButton maleButton;
    private final JRadioButton femaleButton;
    private final JTextField addressField;
    private final JTextField contactField;
    private final JButton registerButton;
    private final JButton exitButton;
    private final JPanel dataPanel;

    public IDForm() {
        setTitle("Registration Form");
        setLayout(new BorderLayout());

        // Create form panel
        JPanel formPanel = new JPanel(new GridLayout(6, 2));
        idField = new JTextField();
        nameField = new JTextField();
        maleButton = new JRadioButton("Male");
        femaleButton = new JRadioButton("Female");
        addressField = new JTextField();
        contactField = new JTextField();
        registerButton = new JButton("Register");
        exitButton = new JButton("Exit");

        ButtonGroup genderGroup = new ButtonGroup();
        genderGroup.add(maleButton);
        genderGroup.add(femaleButton);

        formPanel.add(new JLabel("ID:"));
        formPanel.add(idField);

        formPanel.add(new JLabel("Name:"));
        formPanel.add(nameField);

        formPanel.add(new JLabel("Gender:"));
        JPanel genderPanel = new JPanel();
        genderPanel.add(maleButton);
        genderPanel.add(femaleButton);
        formPanel.add(genderPanel);

        formPanel.add(new JLabel("Address:"));
        formPanel.add(addressField);

        formPanel.add(new JLabel("Contact:"));
        formPanel.add(contactField);

        formPanel.add(registerButton);
        formPanel.add(exitButton);

        // Create data panel
        dataPanel = new JPanel();
        dataPanel.setLayout(new BoxLayout(dataPanel, BoxLayout.Y_AXIS));

        add(formPanel, BorderLayout.WEST);
        add(dataPanel, BorderLayout.CENTER);

        registerButton.addActionListener((ActionEvent e) -> {
            handleRegister();
        });

        exitButton.addActionListener((ActionEvent e) -> {
            System.exit(0);
        });

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        pack();
        setVisible(true);
    }

    public IDForm(JTextField addressField, JTextField contactField, JPanel dataPanel, JButton exitButton, JRadioButton femaleButton, JTextField idField, JRadioButton maleButton, JTextField nameField, JButton registerButton) throws HeadlessException {
        this.addressField = addressField;
        this.contactField = contactField;
        this.dataPanel = dataPanel;
        this.exitButton = exitButton;
        this.femaleButton = femaleButton;
        this.idField = idField;
        this.maleButton = maleButton;
        this.nameField = nameField;
        this.registerButton = registerButton;
    }

    public IDForm(JTextField addressField, JTextField contactField, JPanel dataPanel, JButton exitButton, JRadioButton femaleButton, JTextField idField, JRadioButton maleButton, JTextField nameField, JButton registerButton, GraphicsConfiguration gc) {
        super(gc);
        this.addressField = addressField;
        this.contactField = contactField;
        this.dataPanel = dataPanel;
        this.exitButton = exitButton;
        this.femaleButton = femaleButton;
        this.idField = idField;
        this.maleButton = maleButton;
        this.nameField = nameField;
        this.registerButton = registerButton;
    }

    public IDForm(JTextField addressField, JTextField contactField, JPanel dataPanel, JButton exitButton, JRadioButton femaleButton, JTextField idField, JRadioButton maleButton, JTextField nameField, JButton registerButton, String title) throws HeadlessException {
        super(title);
        this.addressField = addressField;
        this.contactField = contactField;
        this.dataPanel = dataPanel;
        this.exitButton = exitButton;
        this.femaleButton = femaleButton;
        this.idField = idField;
        this.maleButton = maleButton;
        this.nameField = nameField;
        this.registerButton = registerButton;
    }

    public IDForm(JTextField addressField, JTextField contactField, JPanel dataPanel, JButton exitButton, JRadioButton femaleButton, JTextField idField, JRadioButton maleButton, JTextField nameField, JButton registerButton, String title, GraphicsConfiguration gc) {
        super(title, gc);
        this.addressField = addressField;
        this.contactField = contactField;
        this.dataPanel = dataPanel;
        this.exitButton = exitButton;
        this.femaleButton = femaleButton;
        this.idField = idField;
        this.maleButton = maleButton;
        this.nameField = nameField;
        this.registerButton = registerButton;
    }

    private void handleRegister() {
        String id = idField.getText();
        String name = nameField.getText();
        String gender = maleButton.isSelected() ? "Male" : (femaleButton.isSelected() ? "Female" : "");
        String address = addressField.getText();
        String contact = contactField.getText();

        if (id.isEmpty() || name.isEmpty() || gender.isEmpty() || address.isEmpty() || contact.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill all fields.");
            return;
        }

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = DriverManager.getConnection("your_database_url", "username", "password");
            String query = "INSERT INTO userDetails (id, name, gender, address, contact) VALUES (?, ?, ?, ?, ?)";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, id);
            preparedStatement.setString(2, name);
            preparedStatement.setString(3, gender);
            preparedStatement.setString(4, address);
            preparedStatement.setString(5, contact);
            preparedStatement.executeUpdate();

            JOptionPane.showMessageDialog(this, "Data successfully stored in the database");
            displayUserData(id, name, gender, address, contact);

        } catch (SQLException e1) {
            System.err.println("Error while connecting to the database: " + e1.getMessage());
        } finally {
            closeResources(connection, preparedStatement);
        }
    }

    private void closeResources(Connection connection, PreparedStatement preparedStatement) {
        try {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            System.err.println("Error while closing the database connection: " + e.getMessage());
        }
    }

    private void displayUserData(String id, String name, String gender, String address, String contact) {
        dataPanel.removeAll();
        dataPanel.add(new JLabel("ID: " + id));
        dataPanel.add(new JLabel("Name: " + name));
        dataPanel.add(new JLabel("Gender: " + gender));
        dataPanel.add(new JLabel("Address: " + address));
        dataPanel.add(new JLabel("Contact: " + contact));
        dataPanel.revalidate();
        dataPanel.repaint();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new IDForm());
    }
}

    

