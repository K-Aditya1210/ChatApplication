
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.*;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class Client extends JFrame {

    Socket socket;
    BufferedReader br;
    PrintWriter out;

    private JLabel heading = new JLabel("Person2");
    private JTextArea messageArea = new JTextArea();
    private JTextField messageinput = new JTextField();
    private Font font = new Font("Roboto", Font.PLAIN, 20);

    public Client() {
        try {

            System.out.println("Sending request to Server...");
            socket = new Socket("127.0.01", 7777);
            System.out.println("Connection done...");

            br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream());
            //comment createGui and HandleEvent in case you are want to use console based applicatoin
            createGUI();
            handleEvent();
            startreading();
            //use this method in case you are not using the gui 
            // startwriting();

        } catch (Exception e) {
            System.out.println(e);

        }
    }

    public void handleEvent() {

        messageinput.addKeyListener(new KeyListener() {

            @Override
            public void keyTyped(KeyEvent e) {
                // TODO Auto-generated method stub
                // throw new UnsupportedOperationException("Unimplemented method 'keyTyped'");
            }

            @Override
            public void keyPressed(KeyEvent e) {
                // TODO Auto-generated method stub
                // throw new UnsupportedOperationException("Unimplemented method 'keyPressed'");
            }

            @Override
            public void keyReleased(KeyEvent e) {
                // TODO Auto-generated method stub

                // System.out.println("key released"+e.getKeyCode());
                if (e.getKeyCode() == 10) {
                    // System.out.println("you have pressed enter button");
                    String contenttoSent = messageinput.getText();
                    messageArea.append("Me : " + contenttoSent + "\n");
                    out.println(contenttoSent);
                    out.flush();
                    messageinput.setText("");
                    messageinput.requestFocus();
                }

                // throw new UnsupportedOperationException("Unimplemented method 'keyReleased'");
            }

        });

    }

    public void createGUI() {
        this.setTitle("Chat Application");
        this.setSize(600, 500);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //coding for components
        heading.setFont(font);
        messageArea.setFont(font);
        messageinput.setFont(font);

        heading.setIcon(new ImageIcon("igmp.jpg"));
        // heading.setHorizontalTextPosition(SwingConstants.CENTER);
        // heading.setVerticalTextPosition(SwingConstants.BOTTOM);
        heading.setHorizontalAlignment(SwingConstants.CENTER);
        heading.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        messageArea.setEditable(false);

        //frame layout
        this.setLayout(new BorderLayout());

        //adding the components to frame
        this.add(heading, BorderLayout.NORTH);
        JScrollPane scroll = new JScrollPane(messageArea);
        this.add(scroll, BorderLayout.CENTER);
        this.add(messageinput, BorderLayout.SOUTH);

        this.setVisible(true);
    }

    public void startreading() {

        Runnable r1 = () -> {
            System.out.println("Reader started...");
            try {
                while (true) {

                    String msg = br.readLine();
                    if (msg.equals("exit")) {
                        System.out.println("Server Terminated the chat");
                        JOptionPane.showMessageDialog(this, "Server Terminated the chat");
                        messageinput.setEnabled(false);
                        socket.close();
                        break;
                    }
                    // System.out.println("Server : " + msg);
                    messageArea.append("Person1 : " + msg + "\n");

                }
            } catch (Exception e) {
                System.out.println("Connection closed");

            }
        };
        new Thread(r1).start();

    }

    public void startwriting() {
        Runnable r2 = () -> {
            System.out.println("Writer started...");
            try {
                while (!socket.isClosed()) {

                    BufferedReader br1 = new BufferedReader(new InputStreamReader(System.in));
                    String content = br1.readLine();
                    out.println(content);
                    out.flush();

                    if (content.equals("exit")) {
                        socket.close();
                        break;
                    }

                }
            } catch (Exception e) {
                System.out.println("Connection closed");

            }
        };
        new Thread(r2).start();
    }

    public static void main(String[] args) {
        System.out.println("This is Client...");
        Client obj = new Client();
    }
}
