/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package aplicacionferreteria;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.*;
import java.net.URL;

public class AplicacionFerreteria extends JFrame {
    private JPanel panelPrincipal;
    private JPanel panelFormulario;
    private JPanel panelConsulta;
    private CardLayout cardLayout;
    
    private JTextField txtNombreCiudad;
    private JTable tblCiudades;
    private DefaultTableModel modeloTabla;
    
    private static final String NOMBRE_ARCHIVO = "ciudades.txt";

    public AplicacionFerreteria() {
        // 1. Barra de título con módulo asignado, leyenda "Etapa 2" y nombre completo
        setTitle("Catálogo de Ciudades - Etapa 2 - German Olvera Tejero");
        setSize(540, 420);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        setLayout(cardLayout);

        // Inicializar Paneles
        crearPanelPrincipal();
        crearPanelFormulario();
        crearPanelConsulta();

        // Agregar paneles al contenedor principal
        add(panelPrincipal, "MenuPrincipal");
        add(panelFormulario, "FormularioModulo");
        add(panelConsulta, "ConsultaModulo");

        // Mostrar menú principal al inicio
        cardLayout.show(getContentPane(), "MenuPrincipal");
    }

    private void crearPanelPrincipal() {
        panelPrincipal = new JPanel(new BorderLayout(10, 10));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

        // Encabezado con imagen alusiva al Catálogo de Ciudades
        JPanel panelSuperior = new JPanel(new BorderLayout(5, 5));
        JLabel lblTitulo = new JLabel("Ferretería La Golondrina", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));

        JLabel lblImagen = new JLabel("", SwingConstants.CENTER);
        URL urlImagen = getClass().getResource("/imagenes/ciudad.png");
        if (urlImagen != null) {
            ImageIcon iconoOriginal = new ImageIcon(urlImagen);
            Image imgEscalada = iconoOriginal.getImage().getScaledInstance(72, 72, Image.SCALE_SMOOTH);
            lblImagen.setIcon(new ImageIcon(imgEscalada));
        } else {
            lblImagen.setText("[Catálogo de Ciudades]");
        }

        panelSuperior.add(lblTitulo, BorderLayout.NORTH);
        panelSuperior.add(lblImagen, BorderLayout.CENTER);

        // Datos del estudiante
        JPanel panelInfo = new JPanel(new GridLayout(3, 1, 5, 5));
        JLabel lblAlumno = new JLabel("Estudiante: German Olvera Tejero", SwingConstants.CENTER);
        JLabel lblMatricula = new JLabel("Matrícula: ES172000419", SwingConstants.CENTER);
        JLabel lblModulo = new JLabel("Módulo Asignado: Catálogo de Ciudades (Etapa 2)", SwingConstants.CENTER);
        lblModulo.setFont(new Font("Arial", Font.BOLD, 12));

        panelInfo.add(lblAlumno);
        panelInfo.add(lblMatricula);
        panelInfo.add(lblModulo);

        // Botones del menú principal (incluyendo la nueva opción Consulta)
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 12, 10));
        JButton btnModulo = new JButton("Registrar Ciudad");
        JButton btnConsulta = new JButton("Consulta");
        JButton btnSalir = new JButton("Salir");

        btnModulo.addActionListener(e -> cardLayout.show(getContentPane(), "FormularioModulo"));
        btnConsulta.addActionListener(e -> {
            cargarCiudadesEnTabla(); // Lee el .txt cada vez que se entra a Consulta
            cardLayout.show(getContentPane(), "ConsultaModulo");
        });
        btnSalir.addActionListener(e -> System.exit(0));

        panelBotones.add(btnModulo);
        panelBotones.add(btnConsulta);
        panelBotones.add(btnSalir);

        panelPrincipal.add(panelSuperior, BorderLayout.NORTH);
        panelPrincipal.add(panelInfo, BorderLayout.CENTER);
        panelPrincipal.add(panelBotones, BorderLayout.SOUTH);
    }

    private void crearPanelFormulario() {
        panelFormulario = new JPanel(new BorderLayout(10, 10));
        panelFormulario.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel lblTituloForm = new JLabel("Registro - Catálogo de Ciudades", SwingConstants.CENTER);
        lblTituloForm.setFont(new Font("Arial", Font.BOLD, 15));

        JPanel panelCampos = new JPanel(new FlowLayout());
        JLabel lblCiudad = new JLabel("Nombre de la Ciudad:");
        txtNombreCiudad = new JTextField(20);

        panelCampos.add(lblCiudad);
        panelCampos.add(txtNombreCiudad);

        JPanel panelAcciones = new JPanel(new FlowLayout());
        JButton btnGuardar = new JButton("Guardar");
        JButton btnRegresar = new JButton("Regresar al Menú");

        btnGuardar.addActionListener(e -> guardarCiudad());

        btnRegresar.addActionListener(e -> {
            txtNombreCiudad.setText("");
            cardLayout.show(getContentPane(), "MenuPrincipal");
        });

        panelAcciones.add(btnGuardar);
        panelAcciones.add(btnRegresar);

        panelFormulario.add(lblTituloForm, BorderLayout.NORTH);
        panelFormulario.add(panelCampos, BorderLayout.CENTER);
        panelFormulario.add(panelAcciones, BorderLayout.SOUTH);
    }

    private void crearPanelConsulta() {
        panelConsulta = new JPanel(new BorderLayout(10, 10));
        panelConsulta.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

        JLabel lblTituloConsulta = new JLabel("Consulta y Administración de Ciudades", SwingConstants.CENTER);
        lblTituloConsulta.setFont(new Font("Arial", Font.BOLD, 15));

        // Configuración del JTable no editable directamente
        modeloTabla = new DefaultTableModel(new Object[]{"No.", "Nombre de la Ciudad"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tblCiudades = new JTable(modeloTabla);
        tblCiudades.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tblCiudades.getColumnModel().getColumn(0).setMaxWidth(60);

        JScrollPane scrollTabla = new JScrollPane(tblCiudades);

        // Botones Modificar, Eliminar y Regresar
        JPanel panelAccionesConsulta = new JPanel(new FlowLayout(FlowLayout.CENTER, 12, 5));
        JButton btnModificar = new JButton("Modificar");
        JButton btnEliminar = new JButton("Eliminar");
        JButton btnRegresarMenu = new JButton("Regresar al Menú");

        btnModificar.addActionListener(e -> modificarCiudad());
        btnEliminar.addActionListener(e -> eliminarCiudad());
        btnRegresarMenu.addActionListener(e -> cardLayout.show(getContentPane(), "MenuPrincipal"));

        panelAccionesConsulta.add(btnModificar);
        panelAccionesConsulta.add(btnEliminar);
        panelAccionesConsulta.add(btnRegresarMenu);

        panelConsulta.add(lblTituloConsulta, BorderLayout.NORTH);
        panelConsulta.add(scrollTabla, BorderLayout.CENTER);
        panelConsulta.add(panelAccionesConsulta, BorderLayout.SOUTH);
    }

    private void guardarCiudad() {
        String ciudad = txtNombreCiudad.getText().trim();

        if (ciudad.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Error: El campo 'Nombre de la Ciudad' no puede estar vacío.",
                    "Campo Vacío",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        try (FileWriter fw = new FileWriter(NOMBRE_ARCHIVO, true);
             BufferedWriter bw = new BufferedWriter(fw)) {

            bw.write(ciudad);
            bw.newLine();

            JOptionPane.showMessageDialog(this,
                    "¡Ciudad registrada exitosamente!\n\nCiudad ingresada: " + ciudad,
                    "Registro Correcto",
                    JOptionPane.INFORMATION_MESSAGE);

            txtNombreCiudad.setText("");

        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this,
                    "Ocurrió un error al guardar en el archivo: " + ex.getMessage(),
                    "Error de Archivo",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void cargarCiudadesEnTabla() {
        modeloTabla.setRowCount(0);
        File archivo = new File(NOMBRE_ARCHIVO);
        if (!archivo.exists()) {
            return;
        }

        try (FileReader fr = new FileReader(archivo);
             BufferedReader br = new BufferedReader(fr)) {
            String linea;
            int contador = 1;
            while ((linea = br.readLine()) != null) {
                if (!linea.trim().isEmpty()) {
                    modeloTabla.addRow(new Object[]{contador++, linea.trim()});
                }
            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this,
                    "Error al leer el archivo de ciudades: " + ex.getMessage(),
                    "Error de Lectura",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void actualizarArchivoDesdeTabla() {
        try (FileWriter fw = new FileWriter(NOMBRE_ARCHIVO, false);
             BufferedWriter bw = new BufferedWriter(fw)) {
            for (int i = 0; i < modeloTabla.getRowCount(); i++) {
                String nombreCiudad = modeloTabla.getValueAt(i, 1).toString().trim();
                bw.write(nombreCiudad);
                bw.newLine();
            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this,
                    "Error al actualizar el archivo: " + ex.getMessage(),
                    "Error de Escritura",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void modificarCiudad() {
        int filaSeleccionada = tblCiudades.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this,
                    "Por favor, selecciona una ciudad de la tabla para modificar.",
                    "Sin selección",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        String ciudadActual = modeloTabla.getValueAt(filaSeleccionada, 1).toString();
        JTextField txtEditarCiudad = new JTextField(ciudadActual, 20);
        Object[] mensaje = {"Editar nombre de la ciudad:", txtEditarCiudad};
        Object[] botones = {"Guardar", "Cancelar"};

        int opcion = JOptionPane.showOptionDialog(
                this,
                mensaje,
                "Modificar Ciudad",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE,
                null,
                botones,
                botones[0]
        );

        if (opcion == 0) { // Presionó Guardar
            String nuevaCiudad = txtEditarCiudad.getText().trim();
            if (nuevaCiudad.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "Error: El nombre de la ciudad no puede quedar vacío.",
                        "Validación",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            modeloTabla.setValueAt(nuevaCiudad, filaSeleccionada, 1);
            actualizarArchivoDesdeTabla();
            JOptionPane.showMessageDialog(this,
                    "Registro modificado correctamente.",
                    "Actualización Exitosa",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void eliminarCiudad() {
        int filaSeleccionada = tblCiudades.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this,
                    "Por favor, selecciona una ciudad de la tabla para eliminar.",
                    "Sin selección",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        String ciudadSeleccionada = modeloTabla.getValueAt(filaSeleccionada, 1).toString();
        Object[] opciones = {"Confirmar", "Cancelar"};

        int respuesta = JOptionPane.showOptionDialog(
                this,
                "¿Deseas eliminar la ciudad '" + ciudadSeleccionada + "' del registro?",
                "Confirmar Eliminación",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE,
                null,
                opciones,
                opciones[1]
        );

        if (respuesta == 0) { // Seleccionó Confirmar
            modeloTabla.removeRow(filaSeleccionada);
            actualizarArchivoDesdeTabla();
            cargarCiudadesEnTabla(); // Reenumera la columna "No." en la tabla
            JOptionPane.showMessageDialog(this,
                    "Registro eliminado correctamente.",
                    "Eliminación Exitosa",
                    JOptionPane.INFORMATION_MESSAGE);
        }
        // Si selecciona Cancelar (índice 1) o cierra el diálogo, no realiza cambios.
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new AplicacionFerreteria().setVisible(true);
        });
    }
}