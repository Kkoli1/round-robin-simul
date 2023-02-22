import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Algo implements ActionListener {
    Border border = BorderFactory.createLineBorder(new Color(249,1,63), 1);
    Border border2 = BorderFactory.createLineBorder(Color.BLACK, 20);
    JFrame frame;
    JPanel panel;
    JTextArea textArea;
    JScrollPane scroll;
    JButton exit;
    public Algo() throws FileNotFoundException {


        exit = new JButton("CLOSE");
        exit.setBounds(285,20,80,20);
        exit.addActionListener(this);

        textArea = new JTextArea();
        textArea.setBackground(new Color(15,20,28));
        textArea.setForeground(Color.white);
        textArea.setEditable(false);

        scroll = new JScrollPane(textArea);
        scroll.setBounds(20,60,610,920);
        scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        File file = new File("C:\\Users\\Kkoli\\IdeaProjects\\RoundRobin\\src\\RobinAlgo.txt");
        Scanner scan = new Scanner(file);
        String str = "";
        while(scan.hasNextLine()){
            str = str.concat(scan.nextLine() + "\n");
        }


        textArea.setText(str);
        textArea.setBorder(border);

        panel = new JPanel();
        panel.setLayout(null);
        panel.setBounds(0,0,650,1000);
        panel.setBorder(border2);
        panel.setBackground(new Color(15,20,28));
        panel.add(scroll);
        panel.add(exit);

        frame = new JFrame("Algo");
        frame.setLayout(null);
        frame.setSize(650,1000);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setUndecorated(true);

        frame.add(panel);

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
    public static void main (String[] args){
        try {
            new Algo();
        } catch (FileNotFoundException fileNotFoundException) {
            fileNotFoundException.printStackTrace();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == exit){
            frame.dispose();
        }
    }
}
