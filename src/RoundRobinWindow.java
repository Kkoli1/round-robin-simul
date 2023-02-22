import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class RoundRobinWindow implements MouseListener, ActionListener {
    // #273242 = dark color
    // #f9013f = red

    RoundRobinAlgo algo;

    Font  f1  = new Font(Font.DIALOG,  Font.BOLD, 50);
    Font  f2  = new Font(Font.DIALOG,  Font.PLAIN, 50);
    Font  x  = new Font(Font.DIALOG,  Font.PLAIN, 20);
    Font  x2  = new Font(Font.DIALOG,  Font.BOLD, 20);
    Border border = BorderFactory.createLineBorder(Color.WHITE, 1);
    Border border2 = BorderFactory.createLineBorder(Color.BLACK, 20);
    Border border3 = BorderFactory.createLineBorder(new Color(249,1,63),1);
    JFrame frame;
    JPanel left,right,right_in, grid_panel;
    JLabel PS_Label, PS_Label2 , PS_Label3, Calculate, Reset, Arrival_label, Burst_label;
    JLabel exit_button;
    JTextField quantum_time , process_size;
    JTextField arrival1, arrival2, arrival3, arrival4, arrival5, arrival6, arrival7;
    JTextField burst1, burst2, burst3, burst4, burst5, burst6, burst7;
    JButton apply_button, next_button, final_report;

    JLabel quantum_time_label, process_size_label, p1,p2,p3,p4,p5,p6,p7,en1,en2,en3,en4,en5,en6,en7, process_label,process_label2, current_time_label, current_time_label2, legend1, legend1_text, legend2, legend2_text;
    JSeparator separator, separator2;
    JLabel[][] display_simulation;
    int n, qt;
    int[] queue_arrival, queue_burst, burst_copy,burst_copy2 ,q_a, q_b, time_finished;
    int current_time = 0, current_index = -1;
    String[] queue_process = {"P1" , "P2" , "P3" , "P4" , "P5" , "P6" , "P7"};

    Queue <Integer> arrived, arrived_burst, copy;
    ArrayList<Integer> arrived_list = new ArrayList<>();

    int current_peek_burst, random_start = 0;
    boolean newCurrent = true, continueCount = true;

    public RoundRobinWindow(){

        int min = 0;
        int max = 5;

        //Generate random int value from 50 to 100
        System.out.println("Random value in int from "+min+" to "+max+ ":");
        random_start = (int)Math.floor(Math.random()*(max-min+1)+min);

        arrived = new LinkedList<Integer>();
        arrived_burst = new LinkedList<Integer>();
        copy = new LinkedList<Integer>();

        PS_Label = new JLabel("PROCESS");
        PS_Label.setBounds(10,40,470,50);
        PS_Label.setHorizontalAlignment(SwingConstants.CENTER);
        PS_Label.setFont(f1);
        PS_Label.setForeground(new Color(249,1,63));

        PS_Label2 = new JLabel("SCHEDULING:");
        PS_Label2.setBounds(10,90,470,50);
        PS_Label2.setHorizontalAlignment(SwingConstants.CENTER);
        PS_Label2.setFont(f1);
        PS_Label2.setForeground(new Color(249,1,63));

        PS_Label3 = new JLabel("RoundRobin");
        PS_Label3.addMouseListener(this);
        PS_Label3.setBounds(10,170,470,50);
        PS_Label3.setHorizontalAlignment(SwingConstants.CENTER);
        PS_Label3.setFont(f2);
        PS_Label3.setForeground(Color.WHITE);

        separator = new JSeparator();
        separator.setOrientation(SwingConstants.HORIZONTAL);
        separator.setForeground(Color.WHITE);
        separator.setBounds(50,155,390,2);

        quantum_time = new JTextField();
        quantum_time.setBounds(142,280,82,30);
        quantum_time.setHorizontalAlignment(SwingConstants.CENTER);
        quantum_time.setBackground(new Color(15,20,28));
        quantum_time.setForeground(Color.white);
        quantum_time.setBorder(border);

        quantum_time_label = new JLabel("Quantum Time");
        quantum_time_label.setBounds(132,310,102,30);
        quantum_time_label.setHorizontalAlignment(SwingConstants.CENTER);
        quantum_time_label.setForeground(Color.white);
        //quantum_time_label.setBorder(border);

        process_size = new JTextField();
        process_size.setBounds(264,280,82,30);
        process_size.setHorizontalAlignment(SwingConstants.CENTER);
        process_size.setBackground(new Color(15,20,28));
        process_size.setForeground(Color.white);
        process_size.setBorder(border);

        process_size_label = new JLabel("Process Size(1-7)");
        process_size_label.setBounds(254,310,102,30);
        process_size_label.setHorizontalAlignment(SwingConstants.CENTER);
        process_size_label.setForeground(Color.white);
        //process_size_label.setBorder(border);

        apply_button = new JButton("Apply");
        apply_button.addMouseListener(this);
        apply_button.setBounds(183,355,124,20);
        apply_button.setHorizontalAlignment(SwingConstants.CENTER);
        //apply_button.setBackground(new Color(15,20,28));
        //apply_button.setForeground(Color.white);
        apply_button.setBorder(border);

        Arrival_label = new JLabel("Arrival Time:");
        Arrival_label.setBounds(132,390,102,30);
        Arrival_label.setHorizontalAlignment(SwingConstants.CENTER);
        Arrival_label.setForeground(Color.white);
        //Arrival_label.setBorder(border);

        Burst_label = new JLabel("Burst Time:");
        Burst_label.setBounds(252,390,102,30);
        Burst_label.setHorizontalAlignment(SwingConstants.CENTER);
        Burst_label.setForeground(Color.white);
        //Burst_label.setBorder(border);

        arrival1 = new JTextField();
        arrival1.setBounds(142,430,82,30);
        arrival1.setHorizontalAlignment(SwingConstants.CENTER);
        arrival1.setBackground(new Color(15,20,28));
        arrival1.setForeground(Color.white);
        arrival1.setBorder(border);

        p1 = new JLabel("P1");
        p1.setBounds(102, 430, 30,30);
        p1.setBorder(border);
        p1.setHorizontalAlignment(SwingConstants.CENTER);
        p1.setForeground(Color.white);

        arrival2 = new JTextField();
        arrival2.setBounds(142,470,82,30);
        arrival2.setHorizontalAlignment(SwingConstants.CENTER);
        arrival2.setBackground(new Color(15,20,28));
        arrival2.setForeground(Color.white);
        arrival2.setBorder(border);

        p2 = new JLabel("P2");
        p2.setBounds(102, 470, 30,30);
        p2.setBorder(border);
        p2.setHorizontalAlignment(SwingConstants.CENTER);
        p2.setForeground(Color.white);

        arrival3 = new JTextField();
        arrival3.setBounds(142,510,82,30);
        arrival3.setHorizontalAlignment(SwingConstants.CENTER);
        arrival3.setBackground(new Color(15,20,28));
        arrival3.setForeground(Color.white);
        arrival3.setBorder(border);

        p3 = new JLabel("P3");
        p3.setBounds(102, 510, 30,30);
        p3.setBorder(border);
        p3.setHorizontalAlignment(SwingConstants.CENTER);
        p3.setForeground(Color.white);

        arrival4 = new JTextField();
        arrival4.setBounds(142,550,82,30);
        arrival4.setHorizontalAlignment(SwingConstants.CENTER);
        arrival4.setBackground(new Color(15,20,28));
        arrival4.setForeground(Color.white);
        arrival4.setBorder(border);

        p4 = new JLabel("P4");
        p4.setBounds(102, 550, 30,30);
        p4.setBorder(border);
        p4.setHorizontalAlignment(SwingConstants.CENTER);
        p4.setForeground(Color.white);

        arrival5 = new JTextField();
        arrival5.setBounds(142,590,82,30);
        arrival5.setHorizontalAlignment(SwingConstants.CENTER);
        arrival5.setBackground(new Color(15,20,28));
        arrival5.setForeground(Color.white);
        arrival5.setBorder(border);

        p5 = new JLabel("P5");
        p5.setBounds(102, 590, 30,30);
        p5.setBorder(border);
        p5.setHorizontalAlignment(SwingConstants.CENTER);
        p5.setForeground(Color.white);

        arrival6 = new JTextField();
        arrival6.setBounds(142,630,82,30);
        arrival6.setHorizontalAlignment(SwingConstants.CENTER);
        arrival6.setBackground(new Color(15,20,28));
        arrival6.setForeground(Color.white);
        arrival6.setBorder(border);

        p6 = new JLabel("P6");
        p6.setBounds(102, 630, 30,30);
        p6.setBorder(border);
        p6.setHorizontalAlignment(SwingConstants.CENTER);
        p6.setForeground(Color.white);

        arrival7 = new JTextField();
        arrival7.setBounds(142,670,82,30);
        arrival7.setHorizontalAlignment(SwingConstants.CENTER);
        arrival7.setBackground(new Color(15,20,28));
        arrival7.setForeground(Color.white);
        arrival7.setBorder(border);

        p7 = new JLabel("P7");
        p7.setBounds(102, 670, 30,30);
        p7.setBorder(border);
        p7.setHorizontalAlignment(SwingConstants.CENTER);
        p7.setForeground(Color.white);

        burst1 = new JTextField();
        burst1.setBounds(264,430,82,30);
        burst1.setHorizontalAlignment(SwingConstants.CENTER);
        burst1.setBackground(new Color(15,20,28));
        burst1.setForeground(Color.white);
        burst1.setBorder(border);

        en1 = new JLabel();
        en1.setBounds(356, 430, 30,30);
        en1.setBorder(border);
        //en1.setOpaque(true);

        burst2 = new JTextField();
        burst2.setBounds(264,470,82,30);
        burst2.setHorizontalAlignment(SwingConstants.CENTER);
        burst2.setBackground(new Color(15,20,28));
        burst2.setForeground(Color.white);
        burst2.setBorder(border);

        en2 = new JLabel();
        en2.setBounds(356, 470, 30,30);
        en2.setBorder(border);
        //en2.setOpaque(true);

        burst3 = new JTextField();
        burst3.setBounds(264,510,82,30);
        burst3.setHorizontalAlignment(SwingConstants.CENTER);
        burst3.setBackground(new Color(15,20,28));
        burst3.setForeground(Color.white);
        burst3.setBorder(border);

        en3 = new JLabel();
        en3.setBounds(356, 510, 30,30);
        en3.setBorder(border);
        //en3.setOpaque(true);

        burst4 = new JTextField();
        burst4.setBounds(264,550,82,30);
        burst4.setHorizontalAlignment(SwingConstants.CENTER);
        burst4.setBackground(new Color(15,20,28));
        burst4.setForeground(Color.white);
        burst4.setBorder(border);

        en4 = new JLabel();
        en4.setBounds(356, 550, 30,30);
        en4.setBorder(border);
        //en4.setOpaque(true);

        burst5 = new JTextField();
        burst5.setBounds(264,590,82,30);
        burst5.setHorizontalAlignment(SwingConstants.CENTER);
        burst5.setBackground(new Color(15,20,28));
        burst5.setForeground(Color.white);
        burst5.setBorder(border);

        en5 = new JLabel();
        en5.setBounds(356, 590, 30,30);
        en5.setBorder(border);
        //en5.setOpaque(true);

        burst6 = new JTextField();
        burst6.setBounds(264,630,82,30);
        burst6.setHorizontalAlignment(SwingConstants.CENTER);
        burst6.setBackground(new Color(15,20,28));
        burst6.setForeground(Color.white);
        burst6.setBorder(border);

        en6 = new JLabel();
        en6.setBounds(356, 630, 30,30);
        en6.setBorder(border);
        //en6.setOpaque(true);

        burst7 = new JTextField();
        burst7.setBounds(264,670,82,30);
        burst7.setHorizontalAlignment(SwingConstants.CENTER);
        burst7.setBackground(new Color(15,20,28));
        burst7.setForeground(Color.white);
        burst7.setBorder(border);

        en7 = new JLabel();
        en7.setBounds(356, 670, 30,30);
        en7.setBorder(border);
        //en7.setOpaque(true);

        Calculate = new JLabel("Calculate");
        Calculate.addMouseListener(this);
        Calculate.setBounds(122,810,122,30);
        Calculate.setHorizontalAlignment(SwingConstants.CENTER);
        Calculate.setForeground(Color.WHITE);
        Calculate.setFont(x);
        Calculate.setBorder(border);

        Reset = new JLabel("Reset");
        Reset.addMouseListener(this);
        Reset.setBounds(244,810,122,30);
        Reset.setHorizontalAlignment(SwingConstants.CENTER);
        Reset.setForeground(Color.WHITE);
        Reset.setFont(x);
        Reset.setBorder(border);

        left = new JPanel();
        left.setBounds(0,0,490,880);
        left.setBackground(new Color(15,20,28));
        left.setLayout(null);
        left.setBorder(border2);
        left.add(PS_Label);
        left.add(PS_Label2);
        left.add(separator);
        left.add(PS_Label3);
        left.add(quantum_time);
        left.add(quantum_time_label);
        left.add(process_size);
        left.add(process_size_label);
        left.add(apply_button);
        left.add(Arrival_label);
        left.add(Burst_label);
        left.add(arrival1);
        left.add(arrival2);
        left.add(arrival3);
        left.add(arrival4);
        left.add(arrival5);
        left.add(arrival6);
        left.add(arrival7);
        left.add(burst1);
        left.add(burst2);
        left.add(burst3);
        left.add(burst4);
        left.add(burst5);
        left.add(burst6);
        left.add(burst7);
        left.add(p1);
        left.add(p2);
        left.add(p3);
        left.add(p4);
        left.add(p5);
        left.add(p6);
        left.add(p7);
        left.add(en1);
        left.add(en2);
        left.add(en3);
        left.add(en4);
        left.add(en5);
        left.add(en6);
        left.add(en7);
        left.add(Calculate);
        left.add(Reset);

        exit_button = new JLabel("X");
        exit_button.addMouseListener(this);
        exit_button.setBounds(1170,30,20,20);
        exit_button.setForeground(Color.WHITE);
        exit_button.setFont(x);
        exit_button.setHorizontalAlignment(SwingConstants.CENTER);

        process_label = new JLabel("Round Robin Simulation");
        process_label.setBounds(10,600,1130,30);
        process_label.setHorizontalAlignment(SwingConstants.CENTER);
        process_label.setForeground(Color.white);
        process_label.setBorder(border);

        process_label2 = new JLabel("");
        process_label2.setBounds(10,630,1130,30);
        process_label2.setHorizontalAlignment(SwingConstants.CENTER);
        process_label2.setForeground(Color.white);
        process_label2.setBorder(border);

        current_time_label = new JLabel();
        current_time_label.setBounds(550,40,50,50);
        current_time_label.setHorizontalAlignment(SwingConstants.CENTER);
        current_time_label.setForeground(Color.white);
        current_time_label.setFont(x);
        current_time_label.setBorder(border);

        current_time_label2 = new JLabel("Current Time");
        current_time_label2.setBounds(530,90,90,30);
        current_time_label2.setHorizontalAlignment(SwingConstants.CENTER);
        current_time_label2.setForeground(Color.white);
        //current_time_label2.setBorder(border);

        next_button = new JButton("Next");
        next_button.addActionListener(this);
        next_button.setBounds(480,700,90,20);
        next_button.setHorizontalAlignment(SwingConstants.CENTER);
        next_button.setEnabled(false);
        next_button.setBorder(border);

        final_report = new JButton("Final Report");
        final_report.addActionListener(this);
        final_report.setBounds(582,700,90,20);
        final_report.setHorizontalAlignment(SwingConstants.CENTER);
        final_report.setEnabled(false);
        final_report.setBorder(border);

        separator2 = new JSeparator();
        separator2.setOrientation(SwingConstants.VERTICAL);
        separator2.setForeground(Color.WHITE);
        separator2.setBounds(575,40,2,590);

        grid_panel = new JPanel();
        grid_panel.setBounds(10,180,1130,340);
        grid_panel.setBackground(new Color(15,20,28));
        grid_panel.setBorder(border);

        legend1 = new JLabel();
        legend1.setBounds(40,155,20,20);
        legend1.setOpaque(true);
        legend1.setBackground(Color.green);
        legend1.setBorder(border);

        legend2 = new JLabel();
        legend2.setBounds(1090,155,20,20);
        legend2.setOpaque(true);
        legend2.setBackground(new Color(249,1,63));
        legend2.setBorder(border);

        legend2_text = new JLabel("!arrived");
        legend2_text.setBounds(1030,155,50,20);
        legend2_text.setForeground(Color.white);
        legend2_text.setHorizontalAlignment(SwingConstants.CENTER);

        legend1_text = new JLabel("arrived");
        legend1_text.setBounds(70,155,50,20);
        legend1_text.setForeground(Color.white);
        legend1_text.setHorizontalAlignment(SwingConstants.CENTER);

        right_in = new JPanel();
        right_in.setLayout(null);
        right_in.setBounds(40,60,1150,780);
        right_in.setBackground(new Color(15,20,28));
        right_in.setBorder(border3);
        right_in.add(process_label);
        right_in.add(process_label2);
        right_in.add(current_time_label);
        right_in.add(current_time_label2);
        right_in.add(next_button);
        right_in.add(final_report);
        right_in.add(grid_panel);
        right_in.add(legend1);
        right_in.add(legend2);
        right_in.add(legend2_text);
        right_in.add(legend1_text);
        //right_in.add(separator2);

        right = new JPanel();
        right.setLayout(null);
        right.setBounds(490,0,1230,880);
        right.setBackground(new Color(15,20,28));
        right.setBorder(border2);
        right.add(exit_button);
        right.add(right_in);


        frame = new JFrame("Round Robin");
        frame.setLayout(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1720,880);

        frame.add(left);
        frame.add(right);
        System.out.println("Panel: " + left.getSize());
        System.out.println("Panel: " + right.getSize());
        System.out.println("Frame: " + frame.getSize());
        frame.setUndecorated(true);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public static void main(String[] args){
        new RoundRobinWindow();
    }


    @Override
    public void mouseClicked(MouseEvent e) {
        if(e.getSource() == exit_button){
            frame.dispose();
        }else if(e.getSource() == apply_button){
            n = Integer.parseInt(process_size.getText());
            qt = Integer.parseInt(quantum_time.getText());
            if (n == 1){
                en1.setOpaque(true);
                en1.setBackground(Color.green);
                arrival1.setEnabled(true);
                burst1.setEnabled(true);
                en2.setOpaque(true);
                en2.setBackground(new Color(249,1,63));
                arrival2.setEnabled(false);
                burst2.setEnabled(false);
                en3.setOpaque(true);
                en3.setBackground(new Color(249,1,63));
                arrival3.setEnabled(false);
                burst3.setEnabled(false);
                en4.setOpaque(true);
                en4.setBackground(new Color(249,1,63));
                arrival4.setEnabled(false);
                burst4.setEnabled(false);
                en5.setOpaque(true);
                en5.setBackground(new Color(249,1,63));
                arrival5.setEnabled(false);
                burst5.setEnabled(false);
                en6.setOpaque(true);
                en6.setBackground(new Color(249,1,63));
                arrival6.setEnabled(false);
                burst6.setEnabled(false);
                en7.setOpaque(true);
                en7.setBackground(new Color(249,1,63));
                arrival7.setEnabled(false);
                burst7.setEnabled(false);
                arrival1.setText(Integer.toString(random_start));
                arrival1.setEnabled(false);
            }else if (n == 2){
                en1.setOpaque(true);
                en1.setBackground(Color.green);
                arrival2.setEnabled(true);
                burst2.setEnabled(true);
                en2.setOpaque(true);
                en2.setBackground(Color.green);
                arrival2.setEnabled(true);
                burst2.setEnabled(true);
                en3.setOpaque(true);
                en3.setBackground(new Color(249,1,63));
                arrival3.setEnabled(false);
                burst3.setEnabled(false);
                en4.setOpaque(true);
                en4.setBackground(new Color(249,1,63));
                arrival4.setEnabled(false);
                burst4.setEnabled(false);
                en5.setOpaque(true);
                en5.setBackground(new Color(249,1,63));
                arrival5.setEnabled(false);
                burst5.setEnabled(false);
                en6.setOpaque(true);
                en6.setBackground(new Color(249,1,63));
                arrival6.setEnabled(false);
                burst6.setEnabled(false);
                en7.setOpaque(true);
                en7.setBackground(new Color(249,1,63));
                arrival7.setEnabled(false);
                burst7.setEnabled(false);
                arrival1.setText(Integer.toString(random_start));
                arrival2.setText(Integer.toString(random_start+1));
                arrival1.setEnabled(false);
                arrival2.setEnabled(false);
            }else if (n == 3){
                en1.setOpaque(true);
                en1.setBackground(Color.green);
                arrival1.setEnabled(true);
                burst1.setEnabled(true);
                en2.setOpaque(true);
                en2.setBackground(Color.green);
                arrival2.setEnabled(true);
                burst2.setEnabled(true);
                en3.setOpaque(true);
                en3.setBackground(Color.green);
                arrival3.setEnabled(true);
                burst3.setEnabled(true);
                en4.setOpaque(true);
                en4.setBackground(new Color(249,1,63));
                arrival4.setEnabled(false);
                burst4.setEnabled(false);
                en5.setOpaque(true);
                en5.setBackground(new Color(249,1,63));
                arrival5.setEnabled(false);
                burst5.setEnabled(false);
                en6.setOpaque(true);
                en6.setBackground(new Color(249,1,63));
                arrival6.setEnabled(false);
                burst6.setEnabled(false);
                en7.setOpaque(true);
                en7.setBackground(new Color(249,1,63));
                arrival7.setEnabled(false);
                burst7.setEnabled(false);
                arrival1.setText(Integer.toString(random_start));
                arrival2.setText(Integer.toString(random_start+1));
                arrival3.setText(Integer.toString(random_start+2));
                arrival1.setEnabled(false);
                arrival2.setEnabled(false);
                arrival3.setEnabled(false);
            }else if (n == 4){
                en1.setOpaque(true);
                en1.setBackground(Color.green);
                arrival1.setEnabled(true);
                burst1.setEnabled(true);
                en2.setOpaque(true);
                en2.setBackground(Color.green);
                arrival2.setEnabled(true);
                burst2.setEnabled(true);
                en3.setOpaque(true);
                en3.setBackground(Color.green);
                arrival3.setEnabled(true);
                burst3.setEnabled(true);
                en4.setOpaque(true);
                en4.setBackground(Color.green);
                arrival4.setEnabled(true);
                burst4.setEnabled(true);
                en5.setOpaque(true);
                en5.setBackground(new Color(249,1,63));
                arrival5.setEnabled(false);
                burst5.setEnabled(false);
                en6.setOpaque(true);
                en6.setBackground(new Color(249,1,63));
                arrival6.setEnabled(false);
                burst6.setEnabled(false);
                en7.setOpaque(true);
                en7.setBackground(new Color(249,1,63));
                arrival7.setEnabled(false);
                burst7.setEnabled(false);
                arrival1.setText(Integer.toString(random_start));
                arrival2.setText(Integer.toString(random_start+1));
                arrival3.setText(Integer.toString(random_start+2));
                arrival4.setText(Integer.toString(random_start+3));
                arrival1.setEnabled(false);
                arrival2.setEnabled(false);
                arrival3.setEnabled(false);
                arrival4.setEnabled(false);
            }else if (n == 5){
                en1.setOpaque(true);
                en1.setBackground(Color.green);
                arrival1.setEnabled(true);
                burst1.setEnabled(true);
                en2.setOpaque(true);
                en2.setBackground(Color.green);
                arrival2.setEnabled(true);
                burst2.setEnabled(true);
                en3.setOpaque(true);
                en3.setBackground(Color.green);
                arrival3.setEnabled(true);
                burst3.setEnabled(true);
                en4.setOpaque(true);
                en4.setBackground(Color.green);
                arrival4.setEnabled(true);
                burst4.setEnabled(true);
                en5.setOpaque(true);
                en5.setBackground(Color.green);
                arrival5.setEnabled(true);
                burst5.setEnabled(true);
                en6.setOpaque(true);
                en6.setBackground(new Color(249,1,63));
                arrival6.setEnabled(false);
                burst6.setEnabled(false);
                en7.setOpaque(true);
                en7.setBackground(new Color(249,1,63));
                arrival7.setEnabled(false);
                burst7.setEnabled(false);
                arrival1.setText(Integer.toString(random_start));
                arrival2.setText(Integer.toString(random_start+1));
                arrival3.setText(Integer.toString(random_start+2));
                arrival4.setText(Integer.toString(random_start+3));
                arrival5.setText(Integer.toString(random_start+4));
                arrival1.setEnabled(false);
                arrival2.setEnabled(false);
                arrival3.setEnabled(false);
                arrival4.setEnabled(false);
                arrival5.setEnabled(false);
            }else if (n == 6){
                en1.setOpaque(true);
                en1.setBackground(Color.green);
                arrival1.setEnabled(true);
                burst1.setEnabled(true);
                en2.setOpaque(true);
                en2.setBackground(Color.green);
                arrival2.setEnabled(true);
                burst2.setEnabled(true);
                en3.setOpaque(true);
                en3.setBackground(Color.green);
                arrival3.setEnabled(true);
                burst3.setEnabled(true);
                en4.setOpaque(true);
                en4.setBackground(Color.green);
                arrival4.setEnabled(true);
                burst4.setEnabled(true);
                en5.setOpaque(true);
                en5.setBackground(Color.green);
                arrival5.setEnabled(true);
                burst5.setEnabled(true);
                en6.setOpaque(true);
                en6.setBackground(Color.green);
                arrival6.setEnabled(true);
                burst6.setEnabled(true);
                en7.setOpaque(true);
                en7.setBackground(new Color(249,1,63));
                arrival7.setEnabled(false);
                burst7.setEnabled(false);
                arrival1.setText(Integer.toString(random_start));
                arrival2.setText(Integer.toString(random_start+1));
                arrival3.setText(Integer.toString(random_start+2));
                arrival4.setText(Integer.toString(random_start+3));
                arrival5.setText(Integer.toString(random_start+4));
                arrival6.setText(Integer.toString(random_start+5));
                arrival1.setEnabled(false);
                arrival2.setEnabled(false);
                arrival3.setEnabled(false);
                arrival4.setEnabled(false);
                arrival5.setEnabled(false);
                arrival6.setEnabled(false);
            }else if (n == 7){
                en1.setOpaque(true);
                en1.setBackground(Color.green);
                arrival1.setEnabled(true);
                burst1.setEnabled(true);
                en2.setOpaque(true);
                en2.setBackground(Color.green);
                arrival2.setEnabled(true);
                burst2.setEnabled(true);
                en3.setOpaque(true);
                en3.setBackground(Color.green);
                arrival3.setEnabled(true);
                burst3.setEnabled(true);
                en4.setOpaque(true);
                en4.setBackground(Color.green);
                arrival4.setEnabled(true);
                burst4.setEnabled(true);
                en5.setOpaque(true);
                en5.setBackground(Color.green);
                arrival5.setEnabled(true);
                burst5.setEnabled(true);
                en6.setOpaque(true);
                en6.setBackground(Color.green);
                arrival6.setEnabled(true);
                burst6.setEnabled(true);
                en7.setOpaque(true);
                en7.setBackground(Color.green);
                arrival7.setEnabled(true);
                burst7.setEnabled(true);
                arrival1.setText(Integer.toString(random_start));
                arrival2.setText(Integer.toString(random_start+1));
                arrival3.setText(Integer.toString(random_start+2));
                arrival4.setText(Integer.toString(random_start+3));
                arrival5.setText(Integer.toString(random_start+4));
                arrival6.setText(Integer.toString(random_start+5));
                arrival7.setText(Integer.toString(random_start+6));
                arrival1.setEnabled(false);
                arrival2.setEnabled(false);
                arrival3.setEnabled(false);
                arrival4.setEnabled(false);
                arrival5.setEnabled(false);
                arrival6.setEnabled(false);
                arrival7.setEnabled(false);
            }
        }else if(e.getSource() == Calculate){
            queue_arrival = new int[n];
            queue_burst = new int[n];
            q_a = new int[n];
            q_b = new int[n];
            for(int i = 0; i < n; i++){
                if(i == 0){
                    queue_arrival[i] = Integer.parseInt(arrival1.getText());
                    queue_burst[i] = Integer.parseInt(burst1.getText());
                }else if(i == 1){
                    queue_arrival[i] = Integer.parseInt(arrival2.getText());
                    queue_burst[i] = Integer.parseInt(burst2.getText());
                }else if(i == 2){
                    queue_arrival[i] = Integer.parseInt(arrival3.getText());
                    queue_burst[i] = Integer.parseInt(burst3.getText());
                }else if(i == 3){
                    queue_arrival[i] = Integer.parseInt(arrival4.getText());
                    queue_burst[i] = Integer.parseInt(burst4.getText());
                }else if(i == 4){
                    queue_arrival[i] = Integer.parseInt(arrival5.getText());
                    queue_burst[i] = Integer.parseInt(burst5.getText());
                }else if(i == 5){
                    queue_arrival[i] = Integer.parseInt(arrival6.getText());
                    queue_burst[i] = Integer.parseInt(burst6.getText());
                }else if(i == 6){
                    queue_arrival[i] = Integer.parseInt(arrival7.getText());
                    queue_burst[i] = Integer.parseInt(burst7.getText());
                }
                q_a[i] = queue_arrival[i];
                q_b[i] = queue_burst[i];
            }
            readyQueueSort(queue_arrival,queue_burst,queue_process);
            for(int i = 0; i < n; i++){
                System.out.print(queue_process[i] + " ");
            }
            System.out.println();
            for(int i = 0; i < n; i++){
                System.out.print(queue_arrival[i] + " ");
            }
            System.out.println();
            for(int i = 0; i < n; i++){
                System.out.print(queue_burst[i] + " ");
            }
            System.out.println();
            next_button.setEnabled(true);
            final_report.setEnabled(true);
            grid_panel.setLayout(new GridLayout(2,n));
            display_simulation = new JLabel[2][n];
            for(int i = 0; i < 2; i++){
                for(int j = 0; j<n; j++){
                    display_simulation[i][j] = new JLabel();
                    display_simulation[i][j].setBorder(border);
                    display_simulation[i][j].setHorizontalAlignment(SwingConstants.CENTER);
                    display_simulation[i][j].setFont(f1);
                    if(i == 0){
                        display_simulation[i][j].setOpaque(true);
                        display_simulation[i][j].setBackground(new Color(249,1,63));
                        display_simulation[i][j].setText(Integer.toString(queue_burst[j]));
                    }else if(i == 1){
                        display_simulation[i][j].setText(queue_process[j]);
                        display_simulation[i][j].setForeground(Color.white);
                    }
                    grid_panel.add(display_simulation[i][j]);
                }
            }
            burst_copy = queue_burst;
            burst_copy2 = queue_burst;
            time_finished = new int[n];
            for(int i = 0; i < n; i++){
                time_finished[i] = -1;
            }


        }else if(e.getSource() == Reset){
            frame.dispose();
            new RoundRobinWindow();
        }else if(e.getSource() == PS_Label3){
            try {
                new Algo();
            } catch (FileNotFoundException fileNotFoundException) {
                fileNotFoundException.printStackTrace();
            }
        }

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {
        if(e.getSource() == exit_button){
            exit_button.setFont(x2);
        }else if(e.getSource() == Calculate){
            Calculate.setFont(x2);
        }else if(e.getSource() == Reset){
            Reset.setFont(x2);
        }else if(e.getSource()==PS_Label3){
            PS_Label3.setFont(f1);
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {
        if(e.getSource() == exit_button){
            exit_button.setFont(x);
        }else if(e.getSource() == Calculate){
            Calculate.setFont(x);
        }else if(e.getSource() == Reset){
            Reset.setFont(x);
        }else if(e.getSource()==PS_Label3){
            PS_Label3.setFont(f2);
        }

    }

    public static void readyQueueSort(int[] arr1, int[] arr2, String[] arr3 ) {
        int n = arr1.length;
        int temp1 = 0;
        int temp2 = 0;
        String temp3;
        for(int i=0; i < n; i++){
            for(int j=1; j < (n-i); j++){
                if(arr1[j-1] > arr1[j]){
                    //swap elements
                    temp1 = arr1[j-1];
                    arr1[j-1] = arr1[j];
                    arr1[j] = temp1;

                    temp2 = arr2[j-1];
                    arr2[j-1] = arr2[j];
                    arr2[j] = temp2;

                    temp3 = arr3[j-1];
                    arr3[j-1] = arr3[j];
                    arr3[j] = temp3;
                }

            }
        }

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == next_button){
            current_time_label.setText(Integer.toString(current_time));
            int copy_current_time = current_time;

            current_time++;


            int[] return_process = new int[n];
            for(int i = 0; i < n; i++){
                return_process[i] = -1;
                if(queue_arrival[i] == copy_current_time){
                    return_process[i] = i;
                    arrived.add(i);
                    arrived_burst.add(queue_burst[i]);
                    arrived_list.add(i);
                }
            }
            for(int i = 0; i < n ; i++){
                if(return_process[i] != -1){
                    display_simulation[0][return_process[i]].setBackground(Color.green);
                }
            }
            continueCount = true;

            if(arrived_burst.size() > 0){

                if(newCurrent){
                    current_index++;
                    if(current_index > n-1){
                        current_index = 0;
                    }
                    if(Integer.parseInt(display_simulation[0][current_index].getText()) == 0){
                        continueCount = false;
                    }
                    current_peek_burst = (burst_copy2[current_index] - qt);
                    System.out.println(current_peek_burst);
                    newCurrent = false;
                }

                boolean arri = false;
                for(int i = 0; i < arrived_list.size(); i++){

                    if(current_index == arrived_list.get(i)){
                        arri = true;
                        System.out.println("inside");
                    }
                }

                System.out.println("Current Index: " + current_index);

                if((burst_copy2[current_index] != 0 && arri && continueCount) || (burst_copy2[current_index] == 0 && arri && continueCount)){
                    System.out.println("IF");
                    if(copy_current_time > queue_arrival[current_index]){
                        if(Integer.parseInt(display_simulation[0][current_index].getText()) > 0){
                            burst_copy[current_index]--;
                            display_simulation[0][current_index].setText(Integer.toString(burst_copy[current_index]));
                        }

                    }
                    if(burst_copy[current_index] == 0){
                        System.out.println("Zero");
                        newCurrent = true;
                        if(time_finished[current_index] == -1){
                            time_finished[current_index] = Integer.parseInt(current_time_label.getText());
                        }

                    }else if((burst_copy[current_index] == current_peek_burst) && (burst_copy[current_index] != 0)){
                        System.out.println("Not zero");
                        newCurrent = true;
                        System.out.println(burst_copy2[current_index]);
                    }else if((burst_copy[current_index] == current_peek_burst) && (burst_copy[current_index] == 0)){
                        System.out.println("and zero");
                        newCurrent = true;

                        burst_copy2[current_index] = 0;
                        if(time_finished[current_index] == -1){
                            time_finished[current_index] = Integer.parseInt(current_time_label.getText());
                        }
                    }

                }else if (Integer.parseInt(display_simulation[0][current_index].getText()) == 0 || !arri){
                    System.out.println("EL IF");
                    current_time--;
                    newCurrent = true;
                    boolean decide = true;
                    System.out.println("Size: "+arrived_list.size());
                    for(int i = 0; i < arrived_list.size(); i++){
                        if(Integer.parseInt(display_simulation[0][i].getText()) != 0){
                            decide = false;
                            break;
                        }
                    }
                    if(decide){
                        current_time++;
                    }
                }
                String getBurstText = display_simulation[0][current_index].getText();
                String getVerdict = "";
                if(Integer.parseInt(getBurstText) == 0){
                    getVerdict = "Done Processing";
                }else if(Integer.parseInt(getBurstText) > 0){
                    getVerdict = "Not Done";
                }
                process_label.setText("CURRENT:     "+queue_process[current_index]+":    Time Left -> " + getBurstText + "    ::    " + getVerdict);
                if(time_finished[current_index] == -1){
                    process_label2.setText("Time Finished:    ??");
                }else{
                    process_label2.setText("Time Finished:    " + time_finished[current_index]);
                }

            }
        }else if(e.getSource() == final_report){
            algo = new RoundRobinAlgo(q_a,q_b,n,qt);
            new RoundRobinFinalReport(q_a,q_b,n,qt);
        }
    }
}
