import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class Facturacion {

    public static void main(String[] args) {
        // Crear el marco (JFrame)
        JFrame frame = new JFrame("Facturación");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 700);
        frame.setLayout(new GridBagLayout());

        // Crear el título y añadirlo al marco
        JLabel titleLabel = new JLabel("Papelería DAKAEL", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Serif", Font.BOLD, 24));

        // Crear el panel de información de la empresa
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new GridLayout(5, 2, 5, 5));

        // Crear las etiquetas y campos de texto
        JLabel razonSocialLabel = new JLabel("Razón Social:");
        JTextField razonSocialField = new JTextField();
        razonSocialField.setEditable(false);

        JLabel direccionLabel = new JLabel("Dirección:");
        JTextField direccionField = new JTextField();
        direccionField.setEditable(false);

        JLabel telefonoLabel = new JLabel("Teléfono:");
        JTextField telefonoField = new JTextField();
        telefonoField.setEditable(false);

        JLabel correoLabel = new JLabel("Correo:");
        JTextField correoField = new JTextField();
        correoField.setEditable(false);

        JLabel rucLabel = new JLabel("RUC:");
        JTextField rucField = new JTextField();
        rucField.setEditable(false);

        // Añadir las etiquetas y campos al panel
        infoPanel.add(razonSocialLabel);
        infoPanel.add(razonSocialField);

        infoPanel.add(direccionLabel);
        infoPanel.add(direccionField);

        infoPanel.add(telefonoLabel);
        infoPanel.add(telefonoField);

        infoPanel.add(correoLabel);
        infoPanel.add(correoField);

        infoPanel.add(rucLabel);
        infoPanel.add(rucField);

        // Obtener datos de la base de datos y llenar los campos
        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "SELECT Empresa_RazonSocial, Empresa_Direccion, Empresa_Telefono, Empresa_Correo, Empresa_RUC FROM Empresa";
            try (Statement statement = connection.createStatement();
                 ResultSet resultSet = statement.executeQuery(query)) {
                if (resultSet.next()) {
                    razonSocialField.setText(resultSet.getString("Empresa_RazonSocial"));
                    direccionField.setText(resultSet.getString("Empresa_Direccion"));
                    telefonoField.setText(resultSet.getString("Empresa_Telefono"));
                    correoField.setText(resultSet.getString("Empresa_Correo"));
                    rucField.setText(resultSet.getString("Empresa_RUC"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Error al conectar a la base de datos", "Error", JOptionPane.ERROR_MESSAGE);
        }

        // Crear el panel de botones
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(1, 3, 10, 10)); // Cambiado de 4 a 3 botones

        // Crear los botones
        JButton addButton = new JButton("Añadir");
        JButton deleteButton = new JButton("Eliminar");
        JButton totalButton = new JButton("Total");

        // Ajustar el tamaño preferido de los botones
        Dimension buttonSize = new Dimension(80, 30);
        addButton.setPreferredSize(buttonSize);
        deleteButton.setPreferredSize(buttonSize);
        totalButton.setPreferredSize(buttonSize);

        // Añadir los botones al panel
        buttonPanel.add(addButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(totalButton);

        // Crear un panel para los campos de cliente
        JPanel clientePanel = new JPanel();
        clientePanel.setLayout(new GridLayout(6, 2, 5, 5));

        // Crear las etiquetas y campos de texto para clientes
        JLabel clienteIdLabel = new JLabel("ID Cliente:");
        JTextField clienteIdField = new JTextField();

        JLabel clienteNombreLabel = new JLabel("Nombre:");
        JTextField clienteNombreField = new JTextField();
        clienteNombreField.setEditable(false);

        JLabel clienteApellidoLabel = new JLabel("Apellido:");
        JTextField clienteApellidoField = new JTextField();
        clienteApellidoField.setEditable(false);

        JLabel clienteCorreoLabel = new JLabel("Correo:");
        JTextField clienteCorreoField = new JTextField();
        clienteCorreoField.setEditable(false);

        JLabel clienteTelefonoLabel = new JLabel("Teléfono:");
        JTextField clienteTelefonoField = new JTextField();
        clienteTelefonoField.setEditable(false);

        JLabel clienteDireccionLabel = new JLabel("Dirección:");
        JTextField clienteDireccionField = new JTextField();
        clienteDireccionField.setEditable(false);

        // Añadir las etiquetas y campos al panel de clientes
        clientePanel.add(clienteIdLabel);
        clientePanel.add(clienteIdField);

        clientePanel.add(clienteNombreLabel);
        clientePanel.add(clienteNombreField);

        clientePanel.add(clienteApellidoLabel);
        clientePanel.add(clienteApellidoField);

        clientePanel.add(clienteCorreoLabel);
        clientePanel.add(clienteCorreoField);

        clientePanel.add(clienteTelefonoLabel);
        clientePanel.add(clienteTelefonoField);

        clientePanel.add(clienteDireccionLabel);
        clientePanel.add(clienteDireccionField);

        // Crear un botón de "Obtener Datos"
        JButton obtenerDatosButton = new JButton("Obtener Datos");

        // Añadir ActionListener al botón "Obtener Datos"
        obtenerDatosButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Obtener el ID del cliente ingresado
                String clienteId = clienteIdField.getText();

                // Verificar que el campo no esté vacío
                if (clienteId.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Por favor, ingrese un ID de cliente.", "Advertencia", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                // Consultar la base de datos y cargar los datos del cliente
                try (Connection connection = DatabaseConnection.getConnection()) {
                    String query = "SELECT Clientes_Nombre, Clientes_Apellido, Clientes_Correo, Clientes_Telefono, Clientes_Direccion FROM Clientes WHERE Clientes_ID = ?";
                    try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                        preparedStatement.setString(1, clienteId);
                        try (ResultSet resultSet = preparedStatement.executeQuery()) {
                            if (resultSet.next()) {
                                clienteNombreField.setText(resultSet.getString("Clientes_Nombre"));
                                clienteApellidoField.setText(resultSet.getString("Clientes_Apellido"));
                                clienteCorreoField.setText(resultSet.getString("Clientes_Correo"));
                                clienteTelefonoField.setText(resultSet.getString("Clientes_Telefono"));
                                clienteDireccionField.setText(resultSet.getString("Clientes_Direccion"));
                            } else {
                                JOptionPane.showMessageDialog(frame, "Cliente no encontrado.", "Información", JOptionPane.INFORMATION_MESSAGE);
                            }
                        }
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(frame, "Error al conectar a la base de datos", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Crear una tabla para los productos
        String[] columnNames = {"Producto ID", "Producto Nombre", "Producto Cantidad", "Producto Precio", "Valor"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
        JTable productTable = new JTable(tableModel);

        // Añadir la tabla a un JScrollPane
        JScrollPane tableScrollPane = new JScrollPane(productTable);

        // Añadir ActionListener al botón "Añadir"
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String productoId = JOptionPane.showInputDialog(frame, "Ingrese el ID del Producto:", "Añadir Producto", JOptionPane.PLAIN_MESSAGE);

                if (productoId != null && !productoId.trim().isEmpty()) {
                    // Verificar si el producto ya está en la tabla
                    boolean exists = false;
                    for (int i = 0; i < tableModel.getRowCount(); i++) {
                        if (tableModel.getValueAt(i, 0).equals(productoId)) {
                            exists = true;
                            break;
                        }
                    }

                    if (exists) {
                        JOptionPane.showMessageDialog(frame, "El producto ya está en la tabla.", "Advertencia", JOptionPane.WARNING_MESSAGE);
                        return;
                    }

                    try (Connection connection = DatabaseConnection.getConnection()) {
                        String query = "SELECT Productos_Nombre, Productos_Precio FROM Productos WHERE Productos_ID = ?";
                        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                            preparedStatement.setString(1, productoId);
                            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                                if (resultSet.next()) {
                                    String nombre = resultSet.getString("Productos_Nombre");
                                    double precio = resultSet.getDouble("Productos_Precio");
                                    Object[] newRow = {productoId, nombre, "", precio, ""};
                                    tableModel.addRow(newRow);
                                } else {
                                    JOptionPane.showMessageDialog(frame, "Producto no encontrado.", "Información", JOptionPane.INFORMATION_MESSAGE);
                                }
                            }
                        }
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(frame, "Error al conectar a la base de datos", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(frame, "Por favor, ingrese un ID de producto válido.", "Advertencia", JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        // Añadir ActionListener al botón "Eliminar"
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String productoId = JOptionPane.showInputDialog(frame, "Ingrese el ID del Producto a eliminar:", "Eliminar Producto", JOptionPane.PLAIN_MESSAGE);

                if (productoId != null && !productoId.trim().isEmpty()) {
                    boolean found = false;
                    for (int i = 0; i < tableModel.getRowCount(); i++) {
                        if (tableModel.getValueAt(i, 0).equals(productoId)) {
                            tableModel.removeRow(i);
                            found = true;
                            break;
                        }
                    }
                    if (!found) {
                        JOptionPane.showMessageDialog(frame, "Producto no encontrado en la tabla.", "Información", JOptionPane.INFORMATION_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(frame, "Por favor, ingrese un ID de producto válido.", "Advertencia", JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        // Añadir TableModelListener para calcular el valor
        tableModel.addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {
                int row = e.getFirstRow();
                int column = e.getColumn();

                // Verificar que la columna editada sea la de cantidad o precio
                if (column == 2 || column == 3) {
                    try {
                        int cantidad = Integer.parseInt(tableModel.getValueAt(row, 2).toString());
                        double precio = Double.parseDouble(tableModel.getValueAt(row, 3).toString());
                        double valor = cantidad * precio;
                        tableModel.setValueAt(valor, row, 4);
                    } catch (NumberFormatException ex) {
                        // Manejar el caso en que el valor ingresado no es un número válido
                        tableModel.setValueAt("", row, 4);
                    }
                }
            }
        });

        // Crear panel para los totales
        JPanel totalsPanel = new JPanel();
        totalsPanel.setLayout(new GridLayout(3, 2, 5, 5));

        // Crear etiquetas para los totales
        JLabel totalSinIVALabel = new JLabel("Total Sin IVA:");
        JLabel totalConIVALabel = new JLabel("Total Con IVA:");
        JLabel totalAPagarLabel = new JLabel("Total a Pagar:");

        // Crear campos de texto para los totales
        JTextField totalSinIVAField = new JTextField();
        totalSinIVAField.setEditable(false);
        JTextField totalConIVAField = new JTextField();
        totalConIVAField.setEditable(false);
        JTextField totalAPagarField = new JTextField();
        totalAPagarField.setEditable(false);

        // Añadir etiquetas y campos al panel de totales
        totalsPanel.add(totalSinIVALabel);
        totalsPanel.add(totalSinIVAField);
        totalsPanel.add(totalConIVALabel);
        totalsPanel.add(totalConIVAField);
        totalsPanel.add(totalAPagarLabel);
        totalsPanel.add(totalAPagarField);

        // Añadir ActionListener al botón "Total"
        totalButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                double totalSinIVA = 0.0;

                // Sumar todos los valores de la columna "Valor"
                for (int i = 0; i < tableModel.getRowCount(); i++) {
                    Object value = tableModel.getValueAt(i, 4);
                    if (value != null && !value.toString().trim().isEmpty()) {
                        try {
                            totalSinIVA += Double.parseDouble(value.toString());
                        } catch (NumberFormatException ex) {
                            // Manejar el caso en que el valor no es un número válido
                            JOptionPane.showMessageDialog(frame, "Error al calcular el total: Valor inválido en la tabla.", "Error", JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                    }
                }

                // Calcular el total con IVA
                double iva = totalSinIVA * 0.15;
                double totalConIVA = totalSinIVA + iva;

                // Actualizar los campos de texto
                totalSinIVAField.setText(String.format("%.2f", totalSinIVA));
                totalConIVAField.setText(String.format("%.2f", iva));
                totalAPagarField.setText(String.format("%.2f", totalConIVA));
            }
        });

        // Usar GridBagConstraints para posicionar los componentes
        GridBagConstraints gbc = new GridBagConstraints();

        // Añadir el título al GridBagLayout
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        frame.add(titleLabel, gbc);

        // Añadir el panel de información de la empresa al GridBagLayout
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weighty = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        frame.add(infoPanel, gbc);

        // Crear y añadir una línea separadora
        JSeparator separator = new JSeparator();
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        frame.add(separator, gbc);

        // Añadir el panel de clientes al GridBagLayout
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.weighty = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        frame.add(clientePanel, gbc);

        // Añadir el botón "Obtener Datos" al GridBagLayout
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.weighty = 0;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        frame.add(obtenerDatosButton, gbc);

        // Crear y añadir otra línea separadora
        JSeparator separator2 = new JSeparator();
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        frame.add(separator2, gbc);

        // Añadir el JScrollPane con la tabla al GridBagLayout
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.weighty = 1.0; // Aumentar el peso vertical para ocupar el espacio
        gbc.fill = GridBagConstraints.BOTH;
        frame.add(tableScrollPane, gbc);

        // Crear y añadir una línea separadora antes de los totales
        JSeparator separator3 = new JSeparator();
        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        frame.add(separator3, gbc);

        // Añadir el panel de totales al GridBagLayout
        gbc.gridx = 0;
        gbc.gridy = 8;
        gbc.weighty = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        frame.add(totalsPanel, gbc);

        // Añadir el panel de botones al GridBagLayout
        gbc.gridx = 0;
        gbc.gridy = 9;
        gbc.weighty = 0; // Restablecer el peso vertical
        gbc.anchor = GridBagConstraints.SOUTH;
        frame.add(buttonPanel, gbc);

        // Hacer visible el marco
        frame.setLocationRelativeTo(null); // Centrar la ventana en la pantalla
        frame.setVisible(true);
    }
}