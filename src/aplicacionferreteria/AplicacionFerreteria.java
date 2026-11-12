/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package aplicacionferreteria;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;



public class AplicacionFerreteria extends JFrame {
    private JPanel panelPrincipal;
    private JPanel panelFormulario;
    private CardLayout cardLayout;
    private JTextField txtNombreCiudad;
    private static final String NOMBRE_ARCHIVO = "ciudades.txt";

    /**
     * @param args the command line arguments
     */

public AplicacionFerreteria() {
    setTitle("Sistema Ferretería La Golondrina - Módulo Ciudades");
        setSize(480, 320);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        setLayout(cardLayout);

        // Inicializar Paneles
        crearPanelPrincipal();
        crearPanelFormulario();

        // Agregar paneles al contenedor principal
        add(panelPrincipal, "MenuPrincipal");
        add(panelFormulario, "FormularioModulo");

        // Mostrar menú principal al inicio
        cardLayout.show(getContentPane(), "MenuPrincipal");
    }

private void crearPanelPrincipal() {
        panelPrincipal = new JPanel(new GridLayout(5, 1, 10, 10));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel lblTitulo = new JLabel("Ferretería La Golondrina", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 16));
        
        JLabel lblAlumno = new JLabel("Estudiante: German olvera tejero", SwingConstants.CENTER);
        JLabel lblMatricula = new JLabel("Matrícula: ES172000419", SwingConstants.CENTER);
        JLabel lblModulo = new JLabel("Módulo Asignado: Catálogo de Ciudades", SwingConstants.CENTER);

        JButton btnModulo = new JButton("Ingresar a Catálogo de Ciudades");
        JButton btnSalir = new JButton("Salir");

        // Eventos de navegación
        btnModulo.addActionListener(e -> cardLayout.show(getContentPane(), "FormularioModulo"));
        btnSalir.addActionListener(e -> System.exit(0));

        panelPrincipal.add(lblTitulo);
        panelPrincipal.add(lblAlumno);
        panelPrincipal.add(lblMatricula);
        panelPrincipal.add(lblModulo);
        
        JPanel panelBotones = new JPanel(new FlowLayout());
        panelBotones.add(btnModulo);
        panelBotones.add(btnSalir);
        panelPrincipal.add(panelBotones);
    }

    private void crearPanelFormulario() {
        panelFormulario = new JPanel(new BorderLayout(10, 10));
        panelFormulario.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel lblTituloForm = new JLabel("Catálogo de Ciudades", SwingConstants.CENTER);
        lblTituloForm.setFont(new Font("Arial", Font.BOLD, 15));

        // Formulario (JLabel + JTextField)
        JPanel panelCampos = new JPanel(new FlowLayout());
        JLabel lblCiudad = new JLabel("Nombre de la Ciudad:");
        txtNombreCiudad = new JTextField(20);

        panelCampos.add(lblCiudad);
        panelCampos.add(txtNombreCiudad);

        // Botones Guardar y Regresar
        JPanel panelAcciones = new JPanel(new FlowLayout());
        JButton btnGuardar = new JButton("Guardar");
        JButton btnRegresar = new JButton("Regresar al Menú");

        btnGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                guardarCiudad();
            }
        });

        btnRegresar.addActionListener(e -> {
            txtNombreCiudad.setText(""); // Limpia la caja de texto al salir
            cardLayout.show(getContentPane(), "MenuPrincipal");
        });

        panelAcciones.add(btnGuardar);
        panelAcciones.add(btnRegresar);

        panelFormulario.add(lblTituloForm, BorderLayout.NORTH);
        panelFormulario.add(panelCampos, BorderLayout.CENTER);
        panelFormulario.add(panelAcciones, BorderLayout.SOUTH);
    }

    private void guardarCiudad() {
        String ciudad = txtNombreCiudad.getText().trim();

        // 1. Validación de campo vacío
        if (ciudad.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "Error: El campo 'Nombre de la Ciudad' no puede estar vacío.", 
                "Campo Vacío", 
                JOptionPane.ERROR_MESSAGE);
            return;
        }

        // 2. Escritura en el archivo de texto usando FileWriter y BufferedWriter
        try (FileWriter fw = new FileWriter(NOMBRE_ARCHIVO, true);
             BufferedWriter bw = new BufferedWriter(fw)) {

            bw.write(ciudad);
            bw.newLine();

            // 3. Confirmación de éxito
            JOptionPane.showMessageDialog(this, 
                "¡Ciudad registrada exitosamente!\n\nCiudad ingresada: " + ciudad, 
                "Registro Correcto", 
                JOptionPane.INFORMATION_MESSAGE);

            txtNombreCiudad.setText(""); // Limpiar campo después de guardar

        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, 
                "Ocurrió un error al guardar en el archivo: " + ex.getMessage(), 
                "Error de Archivo", 
                JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new AplicacionFerreteria().setVisible(true);
        });
    }
}
