package opencvproject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Date;
import java.sql.Timestamp;


public class CommandFrame extends JFrame {

    private JButton applyFilter;
    private JButton saveButton;
    private JComboBox<String> filterSelection;
    private JLabel dialogLabel;

    private boolean isFilming = true;

    public IFilter delegate;

    public CommandFrame() {

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(0, 0, 380, 150);
        setLayout(new FlowLayout());

        filterSelection = new JComboBox<>();
        filterSelection.setEditable(false);
        filterSelection.setEnabled(true);
        filterSelection.addItem("Find Contours BW");
        filterSelection.addItem("Find Contours Colored");
        filterSelection.addItem("Find Contours BW Negative");
        filterSelection.addItem("Find Contours Colored Negative");

        add(filterSelection);

        applyFilter = new JButton();
        applyFilter.setText("Apply filter");
        applyFilter.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //delegate.onSaving();
                onClickHandlerFilter();
            }
        });

        dialogLabel = new JLabel();
        dialogLabel.setText("Select a filter from the menu and try to apply it");

        saveButton = new JButton();
        saveButton.setText("Save");
        saveButton.setEnabled(false);
        saveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                isFilming = true;
                boolean result = delegate.onSaving();
                if (result) {
                    dialogLabel.setText("<html>Snapshot saved successfully in Downloads folder<br>" + new Timestamp(new Date().getTime()) + "</html>");
                } else {
                    dialogLabel.setText("Error: A problem occurred while saving file");
                }
                applyFilter.setText("Apply filter");
                saveButton.setEnabled(false);
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(applyFilter);
        buttonPanel.add(saveButton);
        buttonPanel.setSize( buttonPanel.getPreferredSize() );
        buttonPanel.setLocation(100, 0);
        add(buttonPanel);
        add(dialogLabel);

        setVisible(true);



        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                toFront();
                repaint();
            }
        });
    }


    private void onClickHandlerFilter() {

        if (isFilming) {
            isFilming = false;
            saveButton.setEnabled(true);
            applyFilter.setText("Discard");
            dialogLabel.setText("You can save or discard the picture");

            switch (filterSelection.getSelectedItem().toString()) {
                case "Find Contours BW":
                    delegate.onSnapshot(FilterType.FindContoursBW);
                    break;
                case "Find Contours Colored":
                    delegate.onSnapshot(FilterType.FindContoursColor);
                    break;
                case "Find Contours BW Negative":
                    delegate.onSnapshot(FilterType.FindContoursBWNegative);
                    break;
                case "Find Contours Colored Negative":
                    delegate.onSnapshot(FilterType.FindContoursColorNegative);
                    break;
                default:
                    break;
            }

        } else {
            isFilming = true;
            saveButton.setEnabled(false);
            applyFilter.setText("Apply filter");
            delegate.onDiscarding();
            dialogLabel.setText("Select a filter from the menu and try to apply it");
        }
    }



}
