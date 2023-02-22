import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class RoundRobinFinalReport implements MouseListener {
    Border border_thicc = BorderFactory.createLineBorder(Color.BLACK, 20);
    Border border = BorderFactory.createLineBorder(new Color(249,1,63), 1);
    Font  x  = new Font(Font.DIALOG,  Font.PLAIN, 20);
    Font  x2  = new Font(Font.DIALOG,  Font.BOLD, 20);
    Font  big_font  = new Font(Font.DIALOG,  Font.PLAIN, 50);
    Font  big_font_BOLD  = new Font(Font.DIALOG,  Font.BOLD, 50);
    Font  big_font_BOLD_lit  = new Font(Font.DIALOG,  Font.BOLD, 30);
    Font  big_font_PLAIN_lit  = new Font(Font.DIALOG,  Font.PLAIN, 30);
    Font  f = new Font(Font.DIALOG,  Font.PLAIN, 20);
    JFrame frame;
    JPanel panel, grid_panel;
    JLabel Final_report_text, report,avg_wait_time, avg_ta_time, wait,turnaround, value1,value2, exit_button;
    JLabel[][] display;

    RoundRobinAlgo algo;
    public RoundRobinFinalReport(int[] q_a, int[] q_b,int n, int qt){
        Final_report_text = new JLabel("FINAL");
        Final_report_text.setBounds(40,40,500,70);
        Final_report_text.setFont(big_font_BOLD);
        Final_report_text.setForeground(Color.white);

        report = new JLabel("REPORT");
        report.setBounds(200,40,500,70);
        report.setFont(big_font);
        report.setForeground(new Color(249,1,63));

        grid_panel = new JPanel();
        grid_panel.setBounds(40,140,900,500);
        grid_panel.setLayout(new GridLayout(n+1,5));
        grid_panel.setBackground(Color.white);

        algo = new RoundRobinAlgo(q_a,q_b,n,qt);

        display = new JLabel[n+1][5];
        for(int i = 0; i < n+1; i++){
            for(int j = 0; j < 5; j++){
                display[i][j] = new JLabel();
                display[i][j].setBorder(border);
                display[i][j].setFont(f);
                grid_panel.add(display[i][j]);
                display[i][j].setHorizontalAlignment(SwingConstants.CENTER);
                if(i == 0){
                    if(j == 0){
                        display[i][j].setText("Process #");
                    }else if(j == 1){
                        display[i][j].setText("Arrival Time");
                    }else if(j == 2){
                        display[i][j].setText("Burst Time");
                    }else if(j == 3){
                        display[i][j].setText("Wait Time");
                    }else if(j == 4){
                        display[i][j].setText("Turn Around Time");
                    }
                }else{
                    if(j == 0){
                        display[i][j].setText(Integer.toString(i));
                    }else if(j == 1){
                        display[i][j].setText(Integer.toString(algo.arrival[i-1]));
                    }else if(j == 2){
                        display[i][j].setText(Integer.toString(algo.burst[i-1]));
                    }else if(j == 3){
                        display[i][j].setText(Integer.toString(algo.wait[i-1]));
                    }else if(j == 4){
                        display[i][j].setText(Integer.toString(algo.turn[i-1]));
                    }
                }
            }
        }

        avg_wait_time = new JLabel("AVG          Time: ");
        avg_wait_time.setBounds(40,660, 250,50);
        avg_wait_time.setForeground(Color.white);
        avg_wait_time.setFont(big_font_BOLD_lit);

        wait = new JLabel("Wait");
        wait.setBounds(112,660, 250,50);
        wait.setForeground(new Color(249,1,63));
        wait.setFont(big_font_PLAIN_lit);

        String waitave = Float.toString (algo.avgWait/n);
        value1 = new JLabel();
        value1.setText(waitave);
        value1.setBounds(280,660, 250,50);
        value1.setForeground(Color.white);
        value1.setFont(big_font_PLAIN_lit);

        avg_ta_time = new JLabel("AVG                      Time: ");
        avg_ta_time.setBounds(40,710, 350,50);
        avg_ta_time.setForeground(Color.white);
        avg_ta_time.setFont(big_font_BOLD_lit);

        turnaround = new JLabel("TurnAround");
        turnaround.setBounds(112,710, 350,50);
        turnaround.setForeground(new Color(249,1,63));
        turnaround.setFont(big_font_PLAIN_lit);

        String waitTT = Float.toString (algo.avgTT/n);
        value2 = new JLabel();
        value2.setText(waitTT);
        value2.setBounds(380,710, 350,50);
        value2.setForeground(Color.white);
        value2.setFont(big_font_PLAIN_lit);

        exit_button = new JLabel("X");
        exit_button.addMouseListener(this);
        exit_button.setBounds(920,40,20,20);
        exit_button.setForeground(Color.WHITE);
        exit_button.setFont(x);
        exit_button.setHorizontalAlignment(SwingConstants.CENTER);

        panel = new JPanel();
        panel.setLayout(null);
        panel.setBounds(0,0,980,800);
        panel.setBackground(new Color(15,20,28));
        panel.setBorder(border_thicc);
        panel.add(avg_wait_time);
        panel.add(avg_ta_time);
        panel.add(wait);
        panel.add(turnaround);
        panel.add(Final_report_text);
        panel.add(report);
        panel.add(grid_panel);
        panel.add(value1);
        panel.add(value2);
        panel.add(exit_button);

        frame = new JFrame();
        frame.setLayout(null);
        frame.setSize(980,800);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setUndecorated(true);

        frame.add(panel);

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public static void main(String[] args){
        int[] q_a = {1,2};
        int[] q_b = {123,2};
        new RoundRobinFinalReport(q_a,q_b,2,2);
    }


    @Override
    public void mouseClicked(MouseEvent e) {
        if(e.getSource() == exit_button){
            frame.dispose();
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
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {
        if(e.getSource() == exit_button){
            exit_button.setFont(x);
        }
    }
}