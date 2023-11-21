import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;

public class RandProductSearch extends JFrame {
    private JTextField productNameField;
    private JTextArea resultArea;
    private RandomAccessFile randomAccessFile;
    private ArrayList<Product> productList;

    public RandProductSearch() {
        setTitle("Product Search");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        createGUI();
        setVisible(true);

        try {
            randomAccessFile = new RandomAccessFile("product_data.dat", "r");
            loadProductList();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void createGUI() {
        JButton searchButton = new JButton("Search");
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchProduct();
            }
        });


        JPanel searchPanel = new JPanel(new GridLayout(2, 2));
        searchPanel.add(new JLabel("Product Name:"));
        productNameField = new JTextField();
        searchPanel.add(productNameField);
        searchPanel.add(new JLabel());
        searchPanel.add(searchButton);
        resultArea = new JTextArea();
        resultArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(resultArea);

        add(searchPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }

    private void loadProductList() throws IOException {
        productList = new ArrayList<>();
        while (randomAccessFile.getFilePointer() < randomAccessFile.length()) {
            Product product = readProductFromFile();
            productList.add(product);
        }
    }

    private Product readProductFromFile() throws IOException {
        byte[] idBytes = new byte[6];
        byte[] nameBytes = new byte[35];
        byte[] descBytes = new byte[75];

        randomAccessFile.readFully(idBytes);
        randomAccessFile.readFully(nameBytes);
        randomAccessFile.readFully(descBytes);

        // Convert bytes to strings and trim any trailing spaces
        String id = new String(idBytes).trim();
        String name = new String(nameBytes).trim();
        String desc = new String(descBytes).trim();
        double cost = randomAccessFile.readDouble();

        return new Product(id, name, desc, cost);
    }


    private void searchProduct() {
        // Search for products matching the entered name
        String searchName = productNameField.getText().trim().toLowerCase();
        resultArea.setText("");

        for (Product product : productList) {
            if (product.getProductName().toLowerCase().contains(searchName)) {
                resultArea.append(product.toString() + "\n");
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new RandProductSearch();
        });
    }
}
