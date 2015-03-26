    package client;


    import server.MessageProtocol;
    import server.MessageType;

    import javax.swing.*;
    import java.awt.*;
    import java.awt.event.ActionEvent;
    import java.awt.event.WindowEvent;
    import java.awt.event.WindowListener;
    import java.io.*;
    import java.util.HashMap;

    public class ClientUI extends JFrame {


        private ImageIcon img   = new ImageIcon("C:\\icon.jpg");
        //1)Login Page JComponents

        //1.1)This panel collects information about which server/port to connect

        JPanel serverInformationPanel = new JPanel();
        private JButton connectToServerButton = new JButton("Connect");
        private JTextField serverChooser = new JTextField("localhost",10);
        private JTextField portChooser = new JTextField("7500",10);
        private JSeparator separator1 = new JSeparator();

        //1.2)This panel contains username label and text field

        private JPanel jUsername = new JPanel();
        private JLabel userNameLabel = new JLabel("Username");
        private JTextField usernameField = new JTextField(15);

        //1.3)This panel contains password label and text field

        private JPanel jPassword = new JPanel();
        private JLabel passwordLabel = new JLabel("Password");
        public JPasswordField passwordField = new JPasswordField(15);

        //1.4)This panel contains login
        private JPanel loginButtons = new JPanel();
        public JButton loginButton= new JButton("Login");


        private JSeparator separator2 = new JSeparator();

        //1.5)This panel contains username label and text field for signup

        private JPanel jUsername1 = new JPanel();
        private JLabel userNameLabel1 = new JLabel("Username");
        private JTextField usernameField1 = new JTextField(15);

        //1.6)This panel contains password label and text field for signp

        private JPanel jPassword1 = new JPanel();
        private JLabel passwordLabel1 = new JLabel("Password");
        public JPasswordField passwordField1 = new JPasswordField(15);

        //1.7)This panel contains re password label and text field for signup
        private JPanel jrePassword = new JPanel();
        private JLabel repasswordLabel = new JLabel("Re  Enter");
        public JPasswordField repasswordField = new JPasswordField(15);

        //1.8)This panel contains signupButton
        private JPanel signupButtons = new JPanel();
        public JButton signupButton= new JButton("SignUp");

        //1.9)appealing UI panel
        private JPanel dummyPanel = new JPanel();

        private ClientThread clientThread;

        public ClientUI() throws IOException {
            initUI();
        }

        private void initUI() {
            setSize(300, 600);
            setResizable(false);
            getContentPane().setBackground(Color.WHITE);
            setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            setTitle("Bugle Chat");
            displayLoginScreen();
            addListenersToLoginScreenButtons();
            addWindowsListeners();
        }

        private void addWindowsListeners() {
            this.addWindowListener(new WindowListener() {
                @Override
                public void windowOpened(WindowEvent e) {
                    //To change body of implemented methods use File | Settings | File Templates.
                }

                @Override public void windowClosing(WindowEvent e)
                {
                    try{
                        clientThread.send(new MessageProtocol(clientThread.username, MessageType.SIGNOUT, "SERVER",new HashMap<String, String>()));
                        clientThread.stop();
                    }catch(Exception ex){

                    }

                }

                @Override
                public void windowClosed(WindowEvent e) {
                    //To change body of implemented methods use File | Settings | File Templates.
                }

                @Override
                public void windowIconified(WindowEvent e) {
                    //To change body of implemented methods use File | Settings | File Templates.
                }

                @Override
                public void windowDeiconified(WindowEvent e) {
                    //To change body of implemented methods use File | Settings | File Templates.
                }

                @Override
                public void windowActivated(WindowEvent e) {
                    //To change body of implemented methods use File | Settings | File Templates.
                }

                @Override
                public void windowDeactivated(WindowEvent e) {
                    //To change body of implemented methods use File | Settings | File Templates.
                }

            });
        }

        private void displayLoginScreen() {
            Container panel = getContentPane();
            GroupLayout layout = new GroupLayout(panel);

            this.setLocationRelativeTo(getRootPane());

            serverInformationPanel.add(serverChooser);
            serverInformationPanel.add(portChooser);
            serverInformationPanel.add(connectToServerButton);
            serverInformationPanel.setBackground(Color.WHITE);
            jUsername.add(userNameLabel);
            jUsername.add(usernameField);
            jUsername.setBackground(Color.WHITE);
            jPassword.add(passwordLabel);
            jPassword.add(passwordField);
            jPassword.setBackground(Color.WHITE);
            loginButtons.add(loginButton);
            loginButtons.setBackground(Color.WHITE);
            separator1.setBackground(new Color(50,0,90));
            separator2.setBackground(new Color(50,0,90));
            jUsername1.add(userNameLabel1);
            jUsername1.add(usernameField1);
            jUsername1.setBackground(Color.WHITE);
            jPassword1.add(passwordLabel1);
            jPassword1.add(passwordField1);
            jPassword1.setBackground(Color.WHITE);
            jrePassword.add(repasswordLabel);
            jrePassword.add(repasswordField);
            jrePassword.setBackground(Color.WHITE);
            signupButtons.setBackground(Color.WHITE);
            signupButtons.add(signupButton);
            dummyPanel.setBackground(new Color(50, 0, 90));
            dummyPanel.setPreferredSize(new Dimension(50, 20));

            //disable all text fields
            usernameField.setEnabled(false);
            passwordField.setEnabled(false);
            usernameField1.setEnabled(false);
            passwordField1.setEnabled(false);
            repasswordField.setEnabled(false);
            loginButton.setEnabled(false);
            signupButton.setEnabled(false);


            panel.setLayout(layout);
            layout.setAutoCreateContainerGaps(true);
            layout.setAutoCreateGaps(true);
            layout.setHorizontalGroup(getHorizontalGroupForLoginScreen(layout));
            layout.setVerticalGroup(getVerticalGroupForLoginScreen(layout));
        }

        private GroupLayout.Group getVerticalGroupForLoginScreen(GroupLayout layout) {
            return (layout.createSequentialGroup()
                    .addComponent(serverInformationPanel)
                    .addComponent(separator1)
                    .addComponent(jUsername)
                    .addComponent(jPassword)
                    .addComponent(loginButtons)
                    .addComponent(separator2)
                    .addComponent(jUsername1)
                    .addComponent(jPassword1)
                    .addComponent(jrePassword)
                    .addComponent(signupButtons)
                    .addComponent(dummyPanel)
            );
        }

        private GroupLayout.Group getHorizontalGroupForLoginScreen(GroupLayout layout) {
            return (layout.createParallelGroup()
                    .addComponent(serverInformationPanel)
                    .addComponent(separator1)
                    .addComponent(jUsername)
                    .addComponent(jPassword)
                    .addComponent(loginButtons)
                    .addComponent(separator2)
                    .addComponent(jUsername1)
                    .addComponent(jPassword1)
                    .addComponent(jrePassword)
                    .addComponent(signupButtons)
                    .addComponent(dummyPanel)
            );
        }

        private void addListenersToLoginScreenButtons() {


            connectToServerButton.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    try {
                        startClientThread();
                        connectToServerButton.setEnabled(false);
                        //enable all text fields
                        usernameField.setEnabled(true);
                        passwordField.setEnabled(true);
                        usernameField1.setEnabled(true);
                        passwordField1.setEnabled(true);
                        repasswordField.setEnabled(true);
                        loginButton.setEnabled(true);
                        signupButton.setEnabled(true);
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(null, "server is not available right now at the given address");
                    }
                }
            });


            loginButton.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    String username =  usernameField.getText();
                    String password = passwordField.getText();
                    try {
                        clientThread.loginServer(username, password);
                    } catch (IOException e) {
                        JOptionPane.showMessageDialog(null, "Our server is not responding.Try again later :(");
                    }
                }
            });

            signupButton.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    String username =  usernameField1.getText();
                    String password = passwordField1.getText();
                    String repassword = repasswordField.getText();
                    System.out.println(username+" "+password+" "+repassword);
                    if(!password.equals(repassword)){
                        JOptionPane.showMessageDialog(null, "your passwords do not match");
                    }else if(password.contains("\\s+") || password.contains("\"")||password.contains("'")){
                        JOptionPane.showMessageDialog(null, "Password must be atleast 6 characters  and should not contain spaces or quotes");
                    }else if(password.length()<6){
                        JOptionPane.showMessageDialog(null, "Password must be atleast 6 characters  and should not contain spaces or quotes");
                    }else{
                        try {
                            clientThread.signUp(username, password);
                        } catch (IOException e) {
                            JOptionPane.showMessageDialog(null, "Right now...we are not able to sign you up. Try again later :(");
                        }
                    }
                }
            });


        }

        private void startClientThread() throws IOException {

            this.clientThread = new  ClientThread(this,serverChooser.getText(),Integer.parseInt(portChooser.getText()));
            this.clientThread.start();

        }

        public void proceedToMainScreen(String username) {
            getContentPane().removeAll();
            mainScreen();
            repaint();
        }

        //========================================MAIN SCREEN ELEMENTS====================================


        //2.1)This is the uneditable text component where every message is visible
        private JScrollPane mainTextPanel = new JScrollPane();
        public JTextArea mainTextBox = new JTextArea();

        //2.2) This panel shows all users who are online
        private JScrollPane userListPanel = new JScrollPane();
        public javax.swing.JList userList = new JList();
        public DefaultListModel model =  new DefaultListModel();

        //2.3) This is the panel using which u can send a message. It has a message box with a send button
        private JPanel msgBoxWithSendPannel = new JPanel();
        private JScrollPane messagePanel = new JScrollPane();
        public JTextArea messageBox = new JTextArea();
        public JButton sendMessageButton= new JButton("Send");

        //2.4)This is the panel containing block/unblock functionality
        public JButton blockUserButton = new JButton("Block User");
        public JButton unBlockButton = new JButton("Unblock User");
        JPanel blockPanel = new JPanel();

        //2.5)This panel consists all buttons and labels for file upload facility
        public JFileChooser fileChooser= new JFileChooser();
        private JButton fileChooserButton   = new JButton("Browse");
        private JTextField choosenFilePath  = new JTextField(10);
        public JButton fileSendButton   = new JButton("Send File");
        private JLabel uploadFileLabel = new JLabel("File");

        private void mainScreen() {
            setSize(600, 600);
            //getContentPane().setBackground(new Color(50,0,90));
            setResizable(false);
            setTitle("Bugle Chat!  Welcome....."+clientThread.username);

            sendMessageButton.setPreferredSize(new Dimension(100, 85));

            model.addElement("All");
            userList.setModel(model);
            userListPanel.setViewportView(userList);
            userListPanel.setPreferredSize(new Dimension(50, 50));
            userList.setSelectedIndex(0);


            mainTextBox.setColumns(50);
            mainTextBox.setFont(new java.awt.Font("Consolas", 0, 15)); // NOI18N
            mainTextBox.setRows(15);
            mainTextBox.setEditable(false);
            // mainTextBox.setBackground(new Color(220,220,255));
            mainTextPanel.setViewportView(mainTextBox);


            messageBox.setColumns(57);
            messageBox.setFont(new java.awt.Font("Consolas", 0, 15)); // NOI18N
            messageBox.setRows(4);
            messageBox.setEditable(true);
            messagePanel.setViewportView(messageBox);
            msgBoxWithSendPannel.add(messagePanel);
            msgBoxWithSendPannel.add(sendMessageButton);
            msgBoxWithSendPannel.setOpaque(false);


            blockPanel.add(blockUserButton);
            blockPanel.add(unBlockButton);
            blockPanel.setOpaque(false);

            fileSendButton.setEnabled(false);
            Container panel = getContentPane();
            GroupLayout layout = new GroupLayout(panel);
            panel.setLayout(layout);
            layout.setAutoCreateContainerGaps(true);
            layout.setAutoCreateGaps(true);
            layout.setHorizontalGroup(getHorizontalGroupForMainScreen(layout));
            layout.setVerticalGroup(getVerticalGroupForMainScreen(layout));
            addListenersToMainScreenButtons();
        }

        private void addListenersToMainScreenButtons() {
            fileChooserButton.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    chooseFile(evt);
                }
            });
            fileSendButton.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    try {
                        sendFile(evt);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                    } catch (IOException e) {
                        e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                    }
                }
            });
            sendMessageButton.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    try {
                        sendMessage(evt);
                    } catch (IOException e) {
                        e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                    }
                }
            });
            blockUserButton.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    blockUser(evt);
                }
            });

            unBlockButton.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    unBlockUser(evt);
                }
            });

        }

        private void chooseFile(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
            fileChooser.showDialog(this, "Select");
            File file = fileChooser.getSelectedFile();
            if(file != null){
                String filePath = file.getPath();
                if(System.getProperty("os.name").startsWith("Windows")) {
                    filePath = filePath.replace("\\", "/"); }
                choosenFilePath.setText(filePath);
                fileSendButton.setEnabled(true);
            }
        }

        private void blockUser(ActionEvent evt) {
            String username = userList.getSelectedValue().toString();
            if(!username.equals("All")){
                if(!clientThread.blockedUsers.contains(username))
                    clientThread.blockedUsers.add(username);
                mainTextBox.append(username +" blocked"+"\n");
            }
        }

        private void unBlockUser(ActionEvent evt) {
            String username = userList.getSelectedValue().toString();
            if(clientThread.blockedUsers.contains(username)){
                clientThread.blockedUsers.remove(username);
                mainTextBox.append(username +" unblocked"+"\n");
            }
        }

        private void sendMessage(ActionEvent evt) throws IOException {
            String text =  messageBox.getText();
            if(text.trim().length()==0)return;
            String username = userList.getSelectedValue().toString();
            if(username.equals("All"))
                clientThread.sendBroadCastMessage(clientThread.username,text);
            else{
                //mainTextBox.append(clientThread.username+"-->  "+ username+"--"+text+"\n");
                mainTextBox.append(clientThread.username+"--> " +username+" >> "+ text.trim()+"\n");
                clientThread.sentPrivateMessage(clientThread.username, username, text.trim());
            }
            messageBox.setText("");
        }

        private void sendFile(ActionEvent evt) throws IOException {
            String username = userList.getSelectedValue().toString();
            if(!username.equals("All")){
                byte [] mybytearray  = new byte [100000];

                FileOutputStream fos = new FileOutputStream(choosenFilePath.getText());
                BufferedOutputStream bos = new BufferedOutputStream(fos);
                int bytesRead = clientThread.socketReader.read(mybytearray, 0, mybytearray.length);
                int current = bytesRead;

                // thanks to A. Cádiz for the bug fix
                do {
                    bytesRead =
                            clientThread.socketReader.read(mybytearray, current, (mybytearray.length-current));
                    if(bytesRead >= 0) current += bytesRead;
                } while(bytesRead > -1);

                bos.write(mybytearray, 0 , current);
                bos.flush();
                bos.close();
            }else{
                JOptionPane.showMessageDialog(null, "Cannot Send File to All. Please select a particular user.");
            }
        }

        private GroupLayout.Group getVerticalGroupForMainScreen(GroupLayout layout) {
            return (layout.createSequentialGroup()
                    .addGroup(layout.createParallelGroup()
                            .addComponent(mainTextPanel)
                            .addComponent(userListPanel))
                    .addComponent(msgBoxWithSendPannel)
                    .addComponent(blockPanel)
                    .addComponent(separator1)
                    .addGroup(layout.createParallelGroup()
                            .addComponent(uploadFileLabel)
                            .addComponent(choosenFilePath)
                    )
                    .addGroup(layout.createParallelGroup()
                            .addComponent(fileChooserButton)
                            .addComponent(fileSendButton)
                    )
                    .addComponent(dummyPanel)
            );
        }

        private GroupLayout.Group getHorizontalGroupForMainScreen(GroupLayout layout) {
            return (layout.createParallelGroup()
                    .addGroup(layout.createSequentialGroup()
                            .addComponent(mainTextPanel)
                            .addComponent(userListPanel))
                    .addComponent(msgBoxWithSendPannel)
                    .addComponent(blockPanel)
                    .addComponent(separator1)
                    .addGroup(layout.createSequentialGroup()
                            .addComponent(uploadFileLabel)
                            .addComponent(choosenFilePath)
                    )
                    .addGroup(layout.createSequentialGroup()
                            .addComponent(fileChooserButton)
                            .addComponent(fileSendButton)
                    )
                    .addComponent(dummyPanel)

            );
        }

        public static void main(String[] args) throws IOException {
            try{
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            }
            catch(Exception ex){

            }
            new client.ClientUI().setVisible(true);
        }



    }
