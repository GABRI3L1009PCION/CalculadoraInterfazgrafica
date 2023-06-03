import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JTextField;

public class CalculadoraGUI extends JFrame implements ActionListener {
    private JTextField display;
    private JPanel panel;
    private String nombreUsuario;

    private double primerNumero;
    private String operador;

    public CalculadoraGUI(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;

        setTitle("Calculadora");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        getContentPane().setBackground(Color.BLUE); // Establecer el color de fondo en azul

        display = new JTextField();
        display.setEditable(false);
        display.setFont(new Font("Arial", Font.PLAIN, 20)); // Ajustar el tamaño de la fuente del display
        display.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Agregar un borde vacío al display
        display.setBackground(Color.WHITE); // Establecer el color de fondo del display en blanco
        add(display, BorderLayout.NORTH);

        panel = new JPanel();
        panel.setLayout(new GridLayout(6, 4, 5, 5)); // Ajustar el espaciado entre los botones
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Agregar un borde vacío al panel
        panel.setBackground(Color.BLUE); // Establecer el color de fondo del panel en azul

        String[][] botones = {
            {"7", "8", "9", "/"},
            {"4", "5", "6", "*"},
            {"1", "2", "3", "-"},
            {"0", ".", "=", "+"},
            {"√", "Primo", "Par/Impar", "Borrar"},
            {"!", "Sen", "Cos", "Tan"},
        };

        for (String[] fila : botones) {
            for (String boton : fila) {
                JButton btn = new JButton(boton);
                btn.addActionListener(this);
                if (boton.matches("[0-9]")) {
                    btn.setBackground(Color.LIGHT_GRAY); // Color gris para los botones numéricos
                    btn.setForeground(Color.BLACK); // Texto en negro para los botones numéricos
                    btn.setFont(new Font("Arial", Font.PLAIN, 16)); // Ajustar el tamaño de la fuente de los botones numéricos
                } else if (boton.equals("Borrar")) {
                    btn.setBackground(Color.ORANGE); // Color anaranjado para el botón de borrar
                    btn.setForeground(Color.BLACK); // Texto en negro para el botón de borrar
                    btn.setFont(new Font("Arial", Font.PLAIN, 16)); // Ajustar el tamaño de la fuente del botón de borrar
                } else {
                    btn.setFont(new Font("Arial", Font.PLAIN, 12)); // Ajustar el tamaño de la fuente de los demás botones
                }
                panel.add(btn);
            }
        }

        add(panel, BorderLayout.CENTER);

        pack();
        setSize(400, 400); // Ajustar el tamaño de la ventana
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        String comando = e.getActionCommand();

        if (comando.matches("[0-9]")) {
            if (display.getText().equals("0")) {
                display.setText(comando);
            } else {
                display.setText(display.getText() + comando);
            }
        } else if (comando.equals(".")) {
            if (!display.getText().contains(".")) {
                display.setText(display.getText() + ".");
            }
        } else if (comando.equals("=")) {
            calcularResultado();
        } else if (comando.equals("Borrar")) {
            display.setText("0");
        } else if (comando.matches("[+\\-*/√!]|Par/Impar|Sen|Cos|Tan|Primo")) {
            primerNumero = Double.parseDouble(display.getText());
            operador = comando;
            display.setText("0");
        }
    }

    private void calcularResultado() {
        double segundoNumero = Double.parseDouble(display.getText());
        double resultado = 0;

        switch (operador) {
            case "+":
                resultado = primerNumero + segundoNumero;
                break;
            case "-":
                resultado = primerNumero - segundoNumero;
                break;
            case "*":
                resultado = primerNumero * segundoNumero;
                break;
            case "/":
                resultado = primerNumero / segundoNumero;
                break;
            case "√":
                resultado = Math.sqrt(primerNumero);
                break;
            case "Primo":
                resultado = esPrimo((int) primerNumero) ? 1 : 0;
                break;
            case "Par/Impar":
                resultado = primerNumero % 2 == 0 ? 1 : 0;
                break;
            case "Sen":
                resultado = Math.sin(primerNumero);
                break;
            case "Cos":
                resultado = Math.cos(primerNumero);
                break;
            case "Tan":
                resultado = Math.tan(primerNumero);
                break;
            case "!":
                resultado = factorial((int) primerNumero);
                break;
        }

        display.setText(nombreUsuario + ", Su resultado es: " + String.valueOf(resultado));
    }

    private boolean esPrimo(int numero) {
        if (numero <= 1) {
            return false;
        }
        for (int i = 2; i <= Math.sqrt(numero); i++) {
            if (numero % i == 0) {
                return false;
            }
        }
        return true;
    }

    private int factorial(int numero) {
        int resultado = 1;
        for (int i = 2; i <= numero; i++) {
            resultado *= i;
        }
        return resultado;
    }

    public static void main(String[] args) {
        String nombreUsuario = JOptionPane.showInputDialog(null, "Por favor, ingrese su nombre:", "Presentación", JOptionPane.INFORMATION_MESSAGE);
        new PresentacionGUI(nombreUsuario);
    }
}

class PresentacionGUI extends JFrame {

    public PresentacionGUI(String nombreUsuario) {
        setTitle("Presentación");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        JPanel panel = new JPanel();
        panel.setLayout(null);

        JLabel etiqueta = new JLabel("Bienvenido " + nombreUsuario + "!");
        etiqueta.setBounds(10, 10, 200, 20);
        panel.add(etiqueta);

        ImageIcon icono = new ImageIcon(ClassLoader.getSystemResource("GifBienvenido.gif"));

        JLabel gif = new JLabel(icono);
        gif.setBounds(10, 40, icono.getIconWidth(), icono.getIconHeight());
        panel.add(gif);

        add(panel);

        pack();
        setSize(400, 400);
        setLocationRelativeTo(null);
        setVisible(true);

        Timer timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new CalculadoraGUI(nombreUsuario);
                dispose();
            }
        });
        timer.setRepeats(false);
        timer.start();
    }
}
