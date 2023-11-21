import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.RandomAccessFile;

public class RandProductMaker extends JFrame {
    private JTextField productIdField, productNameField, productDescField, productCostField, recordCountField;
    private RandomAccessFile randomAccessFile;
    private int recordCount;

    public RandProductMaker() {
        setTitle("Product Data Entry");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        createGUI();
        setVisible(true);
        recordCount = 0;

        // Initialize RandomAccessFile
        try {
            randomAccessFile = new RandomAccessFile("product_data.dat", "rw");
        } catch (IOException e) {
            e.printStackTrace();
            // Handle file opening error
        }
    }

    private void createGUI() {
        // GUI creation code

        // Add ActionListener for the "Add" button
        JButton addButton = new JButton("Add");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addRecord();
            }
        });

        JButton quit = new JButton("Quit");
        quit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int response = JOptionPane.showConfirmDialog(RandProductMaker.this, "Are you sure you want to quit?", "Confirm", JOptionPane.YES_NO_OPTION);
                if (response == JOptionPane.YES_OPTION) {
                    System.exit(0);
                }
            }
        });


        JPanel inputPanel = new JPanel(new GridLayout(6, 2));
        // Add labels and text fields for each input
        inputPanel.add(new JLabel("Product ID:"));
        productIdField = new JTextField();
        inputPanel.add(productIdField);

        inputPanel.add(new JLabel("Product Name:"));
        productNameField = new JTextField();
        inputPanel.add(productNameField);

        inputPanel.add(new JLabel("Product Description:"));
        productDescField = new JTextField();
        inputPanel.add(productDescField);

        inputPanel.add(new JLabel("Product Cost:"));
        productCostField = new JTextField();
        inputPanel.add(productCostField);

        inputPanel.add(addButton);
        inputPanel.add(quit);

        // Add record count field
        inputPanel.add(new JLabel("Record Count:"));
        recordCountField = new JTextField("0");
        recordCountField.setEditable(false);
        inputPanel.add(recordCountField);

        // Add inputPanel to the JFrame
        add(inputPanel, BorderLayout.CENTER);
    }

    private void addRecord() {
        // Validate input fields
        if (validateFields()) {
            // Create a new Product instance with padded fields
            Product product = createPaddedProduct();

            try {
                // Move the file pointer to the end of the file
                randomAccessFile.seek(randomAccessFile.length());

                //Write the product and record the entery number
                writeProductToFile(product);
                recordCount++;
                recordCountField.setText(String.valueOf(recordCount));
                clearFields();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private boolean validateFields() {
        String productId = productIdField.getText();
        String productName = productNameField.getText();
        String productDesc = productDescField.getText();
        String productCostText = productCostField.getText();

        // Check if any field is empty
        if (productId.isEmpty() || productName.isEmpty() || productDesc.isEmpty() || productCostText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "All fields are required.", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        // Check if the cost is a valid positive number
        try {
            double productCost = Double.parseDouble(productCostText);
            if (productCost < 0) {
                JOptionPane.showMessageDialog(this, "Cost must be a positive number.", "Validation Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid cost format.", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        return true;
    }

    private Product createPaddedProduct() {
        // Create a new Product instance with padded fields
        String paddedProductId = padAndTrimField(productIdField.getText(), 6);
        String paddedProductName = padAndTrimField(productNameField.getText(), 35);
        String paddedProductDesc = padAndTrimField(productDescField.getText(), 75);
        double productCost = Double.parseDouble(productCostField.getText());

        return new Product(paddedProductId, paddedProductName, paddedProductDesc, productCost);
    }

    private String padAndTrimField(String value, int length) {
        // Fluff and Pad
        if (value.length() > length) {
            return value.substring(0, length).trim();
        } else {
            return String.format("%-" + length + "s", value).trim();
        }
    }


    private void writeProductToFile(Product product) throws IOException {
        // Write padded fields to the RandomAccessFile
        randomAccessFile.writeBytes(product.getId());
        randomAccessFile.writeBytes(product.getProductName());
        randomAccessFile.writeBytes(product.getProductDesc());
        randomAccessFile.writeDouble(product.getProductCost());
    }


    private void clearFields() {
        // Clear input fields
        productIdField.setText("");
        productNameField.setText("");
        productDescField.setText("");
        productCostField.setText("");
    }
}
