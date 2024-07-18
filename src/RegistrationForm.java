import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.*;
import javax.swing.*;

public class RegistrationForm extends JFrame {

    private JTextField nameField;
    private JTextField mobileField;
    private JRadioButton maleButton;
    private final JRadioButton femaleButton;
    private JTextArea addressField;
    private JCheckBox termsAndConditions;
    private final JButton submitButton;

    public RegistrationForm() {
        setTitle("Registration Form");
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        nameField = new JTextField(20);
        mobileField = new JTextField(20);
        maleButton = new JRadioButton("Male");
        femaleButton = new JRadioButton("Female");
        ButtonGroup genderGroup = new ButtonGroup();
        genderGroup.add(maleButton);
        genderGroup.add(femaleButton);
        addressField = new JTextArea(3, 20);
        termsAndConditions = new JCheckBox("Accept Terms And Conditions");
        submitButton = new JButton("Submit");

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.LINE_END;
        add(new JLabel("Name:"), gbc);
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.LINE_START;
        add(nameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.LINE_END;
        add(new JLabel("Mobile:"), gbc);
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.LINE_START;
        add(mobileField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.LINE_END;
        add(new JLabel("Gender:"), gbc);
        JPanel genderPanel = new JPanel();
        genderPanel.add(maleButton);
        genderPanel.add(femaleButton);
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.LINE_START;
        add(genderPanel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.LINE_END;
        add(new JLabel("Address:"), gbc);
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.LINE_START;
        add(new JScrollPane(addressField), gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        add(termsAndConditions, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        add(submitButton, gbc);

        submitButton.addActionListener((ActionEvent e) -> {
            String name1 = nameField.getText();
            String mobile = mobileField.getText();
            String gender = maleButton.isSelected() ? "Male" : "Female";
            String address = addressField.getText();
            boolean acceptTerms = termsAndConditions.isSelected();
            // Connect to the database
            try (Connection connection = DriverManager.getConnection("your_database_url", "username", "password")) {
                String query = "INSERT INTO users (name, mobile, gender, address, accept_terms) VALUES (?, ?, ?, ?, ?)";
                try (PreparedStatement statement = connection.prepareStatement(query)) {
                    statement.setString(1, name1);
                    statement.setString(2, mobile);
                    statement.setString(3, gender);
                    statement.setString(4, address);
                    statement.setBoolean(5, acceptTerms);
                    statement.executeUpdate();
                    JOptionPane.showMessageDialog(RegistrationForm.this, "Data successfully stored in the database");
                }
            } catch (SQLException e1) {
                JOptionPane.showMessageDialog(RegistrationForm.this, "Error while connecting to the database: " + e1.getMessage());
            }
        });

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new RegistrationForm());
    }
}
