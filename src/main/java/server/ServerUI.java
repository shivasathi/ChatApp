package server;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class ServerUI extends JFrame {

    public Server server;
    public String filePath;
    public JFileChooser fileChooser= new JFileChooser();
    private JButton serverStartButton   = new JButton("Start Server");
    private JButton fileChooserButton   = new JButton("Browse");
    private JLabel dbFileLabel          = new JLabel("Please specify the users file: ");
    private JScrollPane mainTextArea = new JScrollPane();
    public  JTextArea serverMsgBox       = new JTextArea();
    private JTextField choosenFilePath  = new JTextField();
    private ImageIcon img = new ImageIcon("C:\\icon.jpg");
    private JTextField portChooser = new JTextField(10);

    JPanel jPort = new JPanel();
    private JTextField serverChooser = new JTextField(10);

    public ServerUI() {
        init();
    }

    private void init() {
        setSize(800,500);
        setIconImage(img.getImage());
        setTitle("Bugle Chat");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        portChooser.setText("7500");
        portChooser.setPreferredSize(new Dimension(150,20));
        serverChooser.setPreferredSize(new Dimension(150,20));


        configureServerMsgBox();
        serverStartButton.setEnabled(false);
        addListeners();
        configureChoosenFilePath();
        mainTextArea.setViewportView(serverMsgBox);
        jPort.add(portChooser);


        Container panel = getContentPane();
        GroupLayout layout = new GroupLayout(panel);
        panel.setLayout(layout);
        layout.setAutoCreateContainerGaps(true);
        layout.setAutoCreateGaps(true);
        layout.setHorizontalGroup(getHorizontalGroup(layout));
        layout.setVerticalGroup(getVerticalGroup(layout));
        


    }

    private void addListeners() {
        fileChooserButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chooseFile(evt);
            }
        });
        serverStartButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                try {
                    startServer(evt);
                } catch (IOException e) {
                    serverMsgBox.append(e.getMessage());
                }
            }
        });
    }

    private void configureChoosenFilePath() {
        choosenFilePath.setEditable(false);
        choosenFilePath.setBackground(Color.WHITE);
    }

    private void configureServerMsgBox() {
        serverMsgBox.setColumns(20);
        serverMsgBox.setRows(5);
        serverMsgBox.setFont(new Font("Garamond", 0, 16));
        serverMsgBox.setEditable(false);
        serverMsgBox.setBackground(Color.WHITE);
    }


    private GroupLayout.Group getVerticalGroup(GroupLayout layout) {
        return layout.createSequentialGroup()
                .addComponent(jPort)
                .addGroup(layout.createParallelGroup()
                        .addComponent(choosenFilePath, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addComponent(dbFileLabel)
                        .addComponent(fileChooserButton)
                        .addComponent(serverStartButton))
                .addComponent(mainTextArea, GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE);
    }

    private void startServer(java.awt.event.ActionEvent evt) throws IOException {
        
        server = new Server(Integer.parseInt(portChooser.getText()),filePath,this);
        Thread threadMain = new Thread(server);
        threadMain.start();
        serverStartButton.setEnabled(false);
        fileChooserButton.setEnabled(false);
    }

    private void chooseFile(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        fileChooser.showDialog(this, "Select");
        File file = fileChooser.getSelectedFile();

        if(file != null){
            filePath = file.getPath();
            if(System.getProperty("os.name").startsWith("Windows")) {
                filePath = filePath.replace("\\", "/"); }
            choosenFilePath.setText(filePath);
            serverStartButton.setEnabled(true);
        }
    }

    public GroupLayout.Group getHorizontalGroup(GroupLayout layout) {
        
        return layout.createParallelGroup()

                .addComponent(jPort)
                .addComponent(mainTextArea)
                .addGroup(layout.createSequentialGroup()
                        .addComponent(dbFileLabel)
                        .addComponent(choosenFilePath, GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
                        .addComponent(fileChooserButton, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE)
                        .addComponent(serverStartButton));
    }

    public static void main(String args[]) {

        try{
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }
        catch(Exception ex){

        }
        new ServerUI().setVisible(true);
    }

}

