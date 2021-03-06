package View;

import Controller.TicketController;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.List;

public class AddTicket_GUI implements IDefaultPage_GUI{
    private JFrame frame;
    private JPanel panel;
    private JLabel end_label, amount_label, ticket_type_label;
    private List<JLabel> username_labels;
    private JButton add_ticket_button, update_total;
    private JTextField total_amount, ticket_type;
    private List<JTextField> amount_per_user;
    private Boolean split_evenly;
    private JCheckBox splitting_evenly;
    private TicketController ticketController;
    private JComboBox group_list;

    private int groupID;


    public AddTicket_GUI(){
    }

    @Override
    public void Initialize() {
        ticketController = new TicketController();
        split_evenly = false;
        List<Integer> groupids = ticketController.getGroupsFromUser();
        List<String> group_names = new ArrayList<>();
        for (int i = 0; i < groupids.size(); i++){
            group_names.add(ticketController.getGroupName(groupids.get(i)));
        }
        frame = new JFrame();
        add_ticket_button = new JButton("Add ticket");
        update_total = new JButton("Update total");
        splitting_evenly = new JCheckBox("Splitting evenly");
        total_amount = new JTextField(50);
        ticket_type_label = new JLabel("Ticket type:");
        ticket_type = new JTextField(20);
        group_list = new JComboBox(group_names.toArray());
        end_label = new JLabel("Your ticket has been added.");
        amount_label = new JLabel("The total amount is: ");
        username_labels = new ArrayList<JLabel>();
        amount_per_user = new ArrayList<JTextField>();

        panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(600, 600, 200, 600));
        panel.setLayout(null);

        splitting_evenly.setBounds(100, 0, 200, 25);
        add_ticket_button.setBounds(500, 0, 80, 25);
        update_total.setBounds(0, 30, 80, 25);
        amount_label.setBounds(90, 30, 80, 25);
        total_amount.setBounds(180, 30, 80, 25);
        group_list.setBounds(0, 0, 80, 25);
        ticket_type_label.setBounds(0, 60, 80, 25);
        ticket_type.setBounds(90, 60, 80, 25);
        group_list.setSize(80, 25);
        end_label.setBounds(10, 700, 100, 25);

        total_amount.setText("0.00");

        getGroupID(groupids, group_names);
        getUserNamesFromGroup();
        textfieldEditorForSplittingEvenly();

        add_ticket_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                List<Double> useramounts = new ArrayList<>();
                for (JTextField amount: amount_per_user
                     ) {
                    System.out.println("De hoeveelheid is " + amount.getText());
                    useramounts.add(Double.valueOf(amount.getText()));
                }
                editTotalAmount();
                System.out.println(ticketController.createNewTicket(split_evenly, useramounts, ticket_type.getText(), groupID));
                end_label.setText("clicked finish button");
                frame.setVisible(false);
                frame.dispose();
                MainPage_GUI mainPage_gui = new MainPage_GUI();
                mainPage_gui.Initialize();
            }
        });

        update_total.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editTotalAmount();
            }
        });

        splitting_evenly.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                split_evenly = !split_evenly;
                System.out.println(split_evenly);
                textfieldEditorForSplittingEvenly();
            }
        });

        System.out.println(group_list.getSelectedItem());
        group_list.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                for (JLabel label: username_labels
                ) {
                    label.setText("");
                }
                for (JTextField amount: amount_per_user
                ){
                    amount.setVisible(false);
                    panel.remove(amount);
                }
                amount_per_user.clear();
                System.out.println(group_list.getSelectedItem());
                getGroupID(groupids, group_names);
                UpdateUI();
            }
        });




        panel.add(add_ticket_button);
        panel.add(update_total);
        panel.add(total_amount);
        panel.add(amount_label);
        panel.add(splitting_evenly);
        panel.add(end_label);
        panel.add(ticket_type_label);
        panel.add(ticket_type);
        panel.add(group_list);
        

        frame.add(panel, BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Main Page");
        frame.setSize(700, 300);
        frame.setVisible(true);
    }

    private void UpdateUI() {
        panel.invalidate();
        getUserNamesFromGroup();
        panel.revalidate();
        panel.repaint();
        panel.updateUI();
    }

    public void getGroupID(List<Integer> groupids, List<String> group_names) {
        for (int i = 0; i < groupids.size(); i++){
            if (group_list.getSelectedItem().equals(group_names.get(i))){
                groupID = groupids.get(i);
                System.out.println(groupID);
            }
        }
    }

    public void getUserNamesFromGroup(){
        frame.invalidate();
        username_labels.clear();
        List<String> usernames = ticketController.getUsernamesFromGroup(groupID);
        for (int i = 0; i < usernames.size(); i++) {
            username_labels.add(new JLabel(usernames.get(i)));
            username_labels.get(i).setBounds(150, (i * 25) + 100, 100, 25);
            panel.add(username_labels.get(i));
        }
        System.out.println(usernames);
        setUserAmountTextFields(usernames);
        frame.revalidate();
        frame.repaint();
    }

    public void setUserAmountTextFields(List<String> usernames){
        amount_per_user.clear();
        for (int i = 0; i < usernames.size(); i++){
            amount_per_user.add(new JTextField(8));
            amount_per_user.get(i).setBounds(270, (i * 25) + 100, 50, 25);
            panel.add(amount_per_user.get(i));
        }
        textfieldEditorForSplittingEvenly();
    }

    public void textfieldEditorForSplittingEvenly(){
        if (split_evenly){
            total_amount.setEditable(true);
            for (JTextField textfield: amount_per_user
                 ) {
                double amount = splitTotalAmount();
                textfield.setText(String.valueOf(amount));
                textfield.setEditable(false);
            }
        } else {
            for (JTextField textfield: amount_per_user
                 ) {
                double amount = splitTotalAmount();
                textfield.setText(String.valueOf(amount));
                textfield.setEditable(true);
            }
            total_amount.setEditable(false);
        }
    }

    public double splitTotalAmount() {
        double amount = Double.valueOf(total_amount.getText()) / amount_per_user.size();
        amount = Math.floor(amount * 100) / 100;
        System.out.println(amount);
        return amount;
    }

    public void editTotalAmount(){
        double total = 0.00;
        if (split_evenly) {
            if (!amount_label.getText().equals("")) {
                total = Double.parseDouble(total_amount.getText());
            }
        } else {
            for (JTextField amount: amount_per_user
                 ) {
                total += Double.parseDouble(amount.getText());
            }
        }
        double amount = splitTotalAmount();
        for (JTextField text: amount_per_user
             ) {
            text.setText(String.valueOf(amount));
        }
        total_amount.setText(String.valueOf(total));
        System.out.println(total);
    }

}
