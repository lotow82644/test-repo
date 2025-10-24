import javax.swing.*;
import java.awt.*;
import org.json.*;

public class Main extends JFrame {
    private JTextArea inputArea;
    private JTextArea outputArea;
    private JButton formatBtn;

    public Main() {
        super("JSON Formatter");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);

        inputArea = new JTextArea();
        outputArea = new JTextArea();
        outputArea.setEditable(false);
        formatBtn = new JButton("Format JSON");

        formatBtn.addActionListener(e -> formatJson());

        JSplitPane split = new JSplitPane(JSplitPane.VERTICAL_SPLIT,
                new JScrollPane(inputArea), new JScrollPane(outputArea));
        split.setResizeWeight(0.5);

        getContentPane().setLayout(new BorderLayout(5, 5));
        getContentPane().add(split, BorderLayout.CENTER);
        getContentPane().add(formatBtn, BorderLayout.SOUTH);
    }

    private void formatJson() {
        String text = inputArea.getText().trim();
        if (text.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter JSON in the input area.", "Input required", JOptionPane.WARNING_MESSAGE);
            return;
        }
        try {
            if (text.startsWith("{")) {
                JSONObject obj = new JSONObject(text);
                outputArea.setText(obj.toString(4));
            } else if (text.startsWith("[")) {
                JSONArray arr = new JSONArray(text);
                outputArea.setText(arr.toString(4));
            } else {
                // try object then array
                try {
                    JSONObject obj = new JSONObject(text);
                    outputArea.setText(obj.toString(4));
                } catch (JSONException ex) {
                    JSONArray arr = new JSONArray(text);
                    outputArea.setText(arr.toString(4));
                }
            }
        } catch (JSONException ex) {
            JOptionPane.showMessageDialog(this, "Invalid JSON: " + ex.getMessage(), "Parse error", JOptionPane.ERROR_MESSAGE);
            outputArea.setText("Error: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Main m = new Main();
            m.setVisible(true);
        });
    }
}
