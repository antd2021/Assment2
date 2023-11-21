import javax.swing.*;

public class FileStreamsGUIRunner {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new RandProductMaker();
        });
    }
}