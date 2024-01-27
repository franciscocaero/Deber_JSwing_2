import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.Arrays;

public class Login {
    private JTextField usuar;
    private JPasswordField contras;
    public JPanel log;
    private JButton Iniciar;
    public Login() {
        Iniciar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String dbURL = "jdbc:mysql://localhost:3306/registro";
                String dbUsername = "root";
                String dbPassword = "1234";
                java.sql.Connection connection = null;
                try {
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    connection = DriverManager.getConnection(dbURL, dbUsername, dbPassword);
                    Statement statement;
                    statement = connection.createStatement();
                    java.sql.ResultSet resultset;
                    resultset = statement.executeQuery("select * from users");
                    String usuario;
                    String clave;
                    String descrip;
                    boolean datosCorrectos = false;
                    String con = new String(contras.getPassword());
                    while (resultset.next()) {
                        usuario = resultset.getString("usuario");
                        clave = resultset.getString("clave");
                        descrip = resultset.getString("descripcion");
                        if (usuario.equals(usuar.getText()) && clave.equals(con)) {
                            Informacion informacion = new Informacion();
                            informacion.desc.setText(descrip);
                            JFrame informacionFrame = new JFrame("Información");
                            informacionFrame.setPreferredSize(new Dimension(400, 300));
                            informacionFrame.setLocationRelativeTo(null);
                            informacionFrame.getContentPane().add(informacion.descripcion);
                            informacionFrame.pack();
                            informacionFrame.setVisible(true);
                            datosCorrectos = true;
                            Main.frame.dispose();
                        }
                    }
                    if (!datosCorrectos) {
                        DatosIncorrectosDialog dialogo = new DatosIncorrectosDialog();
                        dialogo.setVisible(true);
                    }
                } catch (Exception ex) {
                    System.out.println("Ex");
                }
            }
        });
    }

    private class DatosIncorrectosDialog extends JDialog {
        public DatosIncorrectosDialog() {
            setTitle("Datos Incorrectos");
            setSize(300, 200);
            setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            setLocationRelativeTo(null);
            setModal(true);
            getContentPane().setBackground(Color.BLACK);
            JLabel mensajeLabel = new JLabel("Usuario o contraseña incorrectos");
            mensajeLabel.setForeground(Color.WHITE);
            mensajeLabel.setHorizontalAlignment(SwingConstants.CENTER);
            mensajeLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
            add(mensajeLabel);
        }
    }
}