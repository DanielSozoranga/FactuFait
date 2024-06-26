import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Random;
import java.time.LocalDate;

public class MainFrame extends JFrame {
    private CardLayout cardLayout;
    private JPanel mainPanel;
    private JTable clientesTable;
    private JTable productosTable;

    public MainFrame() {
        // Configuración inicial de la ventana principal
        setTitle("Interfaz Gráfica con Base de Datos");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Inicialización del layout de tarjeta y el panel principal
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        // Crear el panel del menú principal
        createMenuPanel();

        // Crear el panel de Clientes
        createClientesPanel();

        // Crear el panel de Productos
        createProductosPanel();

        // Crear el panel de Facturación
        createFacturacionPanel();

        // Agregar el panel principal a la ventana
        add(mainPanel);
    }

    // Crear la barra de menú
    private void createMenuPanel() {
        JMenuBar menuBar = new JMenuBar();

        // Agregar el botón Inicio directamente en la barra de menú
        JButton inicioButton = new JButton("Inicio");
        inicioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                // Mostrar el panel del menú principal al hacer clic en el botón
                cardLayout.show(mainPanel, "menuPanel");
            }
        });
        menuBar.add(inicioButton);

        // Agregar la barra de menú a la ventana
        setJMenuBar(menuBar);


        // Crear el panel del menú principal
        JPanel menuPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(30, 10, 30, 10); // Aumenta el espacio vertical entre los botones y los bordes
        gbc.weightx = 0.33;
        gbc.weighty = 1.0;
        gbc.gridx = 0;
        gbc.gridy = 0;


        // Crear y configurar el botón Clientes
        JButton clientesButton = new JButton("Clientes");
        clientesButton.setFont(new Font("Arial", Font.PLAIN, 40)); // Aumenta el tamaño de la fuente
        clientesButton.setPreferredSize(new Dimension(300, 120)); // Ajusta las dimensiones del botón
        clientesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                // Mostrar el panel de Clientes y cargar datos de la base de datos
                cardLayout.show(mainPanel, "clientesPanel");
                fetchDataFromDatabase("Clientes", clientesTable, "Clientes_ID", "Clientes_Nombre", "Clientes_Apellido", "Clientes_Correo", "Clientes_Telefono", "Clientes_Direccion");
            }
        });
        menuPanel.add(clientesButton, gbc);


        // Crear y configurar el botón Productos
        gbc.gridx = 1;
        JButton productosButton = new JButton("Productos");
        productosButton.setFont(new Font("Arial", Font.PLAIN, 40));
        productosButton.setPreferredSize(new Dimension(300, 120)); // Ajusta las dimensiones del botón
        productosButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                // Mostrar el panel de Productos y cargar datos de la base de datos
                cardLayout.show(mainPanel, "productosPanel");
                fetchDataFromDatabase("Productos", productosTable, "Productos_ID", "Productos_Nombre", "Productos_Categoria", "Productos_Precio", "Productos_Marca");
            }
        });
        menuPanel.add(productosButton, gbc);

        // Configuración del botón Facturación
        gbc.gridx = 2;
        gbc.gridy = 0; // Añadido para especificar la fila
        JButton facturacionButton = new JButton("Facturación");
        facturacionButton.setFont(new Font("Arial", Font.PLAIN, 40));
        facturacionButton.setPreferredSize(new Dimension(300, 120));
        facturacionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Mostrar el panel de Facturación
                cardLayout.show(mainPanel, "facturacionPanel");
            }
        });
        menuPanel.add(facturacionButton, gbc);

        // Agregar el panel del menú principal al panel principal
        mainPanel.add(menuPanel, "menuPanel");
        cardLayout.show(mainPanel, "menuPanel");
    }

    private void createFacturacionPanel() {
        // Crear el panel de facturación con GridBagLayout
        JPanel facturacionPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1;
        gbc.weighty = 1;

        // Crear y configurar el título del panel
        JLabel titleLabel = new JLabel("Papelería DAKAEL", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Serif", Font.BOLD, 24));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 3;
        facturacionPanel.add(titleLabel, gbc);

        // Crear el panel de información de la empresa
        JPanel infoPanel = new JPanel(new GridLayout(7, 2, 10, 10));
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

        JLabel fechaLabel = new JLabel("Fecha:");
        JTextField fechaField = new JTextField();
        fechaField.setEditable(false);

        JLabel nroautorizacionLabel = new JLabel("Factura ID:");
        JTextField nroautorizacionField = new JTextField();
        nroautorizacionField.setEditable(false);

        // Añadir los campos de información de la empresa al panel
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
        infoPanel.add(fechaLabel);
        infoPanel.add(fechaField);
        infoPanel.add(nroautorizacionLabel);
        infoPanel.add(nroautorizacionField);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.weightx = 0.5;
        facturacionPanel.add(infoPanel, gbc);

        // Conectar a la base de datos y obtener la información de la empresa
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
            JOptionPane.showMessageDialog(this, "Error al conectar a la base de datos", "Error", JOptionPane.ERROR_MESSAGE);
        }

        // Crear el panel de botones
        JPanel buttonPanel = new JPanel(new GridLayout(1, 4, 20, 10));
        JButton addButton = new JButton("Añadir");
        JButton deleteButton = new JButton("Eliminar");
        JButton totalButton = new JButton("Total");
        JButton guardarButton = new JButton("Guardar");
        JButton nuevaFacturaButton = new JButton("Nueva Factura");

        // Añadir los botones al panel
        buttonPanel.add(addButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(totalButton);
        buttonPanel.add(guardarButton);
        buttonPanel.add(nuevaFacturaButton);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.weighty = 0;
        facturacionPanel.add(buttonPanel, gbc);

        JPanel clientePanel = new JPanel(new GridLayout(6, 2, 10, 10));
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

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.weightx = 0.5;
        facturacionPanel.add(clientePanel, gbc);

        JButton obtenerDatosButton = new JButton("Obtener Datos");
        gbc.gridx = 2;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.weightx = 0.5;
        facturacionPanel.add(obtenerDatosButton, gbc);

        obtenerDatosButton.addActionListener(e -> {
            String clienteId = clienteIdField.getText();

            if (clienteId.trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Por favor, ingrese un ID de cliente.", "Advertencia", JOptionPane.WARNING_MESSAGE);
                return;
            }

            try {
                int clientId = Integer.parseInt(clienteId.trim());

                try (Connection connection = DatabaseConnection.getConnection()) {
                    String query = "SELECT Clientes_Nombre, Clientes_Apellido, Clientes_Correo, Clientes_Telefono, Clientes_Direccion FROM Clientes WHERE Clientes_ID = ?";
                    try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                        preparedStatement.setInt(1, clientId);
                        try (ResultSet resultSet = preparedStatement.executeQuery()) {
                            if (resultSet.next()) {
                                clienteNombreField.setText(resultSet.getString("Clientes_Nombre"));
                                clienteApellidoField.setText(resultSet.getString("Clientes_Apellido"));
                                clienteCorreoField.setText(resultSet.getString("Clientes_Correo"));
                                clienteTelefonoField.setText(resultSet.getString("Clientes_Telefono"));
                                clienteDireccionField.setText(resultSet.getString("Clientes_Direccion"));
                            } else {
                                JOptionPane.showMessageDialog(this, "Cliente no encontrado.", "Información", JOptionPane.INFORMATION_MESSAGE);
                            }
                        }
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(this, "Error al conectar a la base de datos", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Por favor, ingrese un ID de cliente válido.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        String[] columnNames = {"Producto", "Nombre", "Cantidad", "Precio", "Valor"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
        JTable productTable = new JTable(tableModel);
        JScrollPane tableScrollPane = new JScrollPane(productTable);

        // Añadir InputVerifier a la columna "Cantidad"
        productTable.getColumnModel().getColumn(2).setCellEditor(new DefaultCellEditor(new JTextField()) {
            @Override
            public boolean stopCellEditing() {
                String value = (String) getCellEditorValue();
                try {
                    Integer.parseInt(value);
                    return super.stopCellEditing();
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(null, "Cantidad debe ser un número entero", "Error de entrada", JOptionPane.ERROR_MESSAGE);
                    return false;
                }
            }
        });

        // Añadir TableModelListener para calcular el valor
        tableModel.addTableModelListener(e -> {
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
        });


        addButton.addActionListener(e -> {
            while (true) {
                // Solicitar ID del producto a añadir
                String productoId = JOptionPane.showInputDialog(this, "Ingrese el ID del Producto:", "Añadir Producto", JOptionPane.PLAIN_MESSAGE);

                if (productoId == null) {
                    // Si el usuario cancela la entrada, salimos del bucle
                    return;
                }

                if (productoId.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Debe ingresar un número.", "Error", JOptionPane.ERROR_MESSAGE);
                    continue;
                }

                try {
                    int productId = Integer.parseInt(productoId.trim());

                    boolean exists = false;
                    for (int i = 0; i < tableModel.getRowCount(); i++) {
                        if (Integer.parseInt((String) tableModel.getValueAt(i, 0)) == productId) {
                            exists = true;
                            break;
                        }
                    }

                    if (exists) {
                        JOptionPane.showMessageDialog(this, "El producto ya está en la tabla.", "Advertencia", JOptionPane.WARNING_MESSAGE);
                        return;
                    }

                    try (Connection connection = DatabaseConnection.getConnection()) {
                        String query = "SELECT Productos_Nombre, Productos_Precio FROM Productos WHERE Productos_ID = ?";
                        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                            preparedStatement.setInt(1, productId);
                            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                                if (resultSet.next()) {
                                    String productosNombre = resultSet.getString("Productos_Nombre");
                                    double productosPrecio = resultSet.getDouble("Productos_Precio");

                                    // Agregar fila a la tabla con cantidad predeterminada de 1
                                    tableModel.addRow(new Object[]{productoId, productosNombre, 1, productosPrecio, productosPrecio});
                                } else {
                                    JOptionPane.showMessageDialog(this, "Producto no encontrado.", "Información", JOptionPane.INFORMATION_MESSAGE);
                                }
                            }
                        }
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(this, "Error al conectar a la base de datos", "Error", JOptionPane.ERROR_MESSAGE);
                    }

                    // Salimos del bucle si el ID es válido y la operación se completó
                    break;
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Por favor, ingrese un ID válido.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });


        // Modificar el panel de botones para que ocupe solo la mitad superior
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.weighty = 0;
        facturacionPanel.add(buttonPanel, gbc);

        // Crear el panel para los totales
        JPanel totalPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        JLabel totalSinIvaLabel = new JLabel("Total sin IVA:");
        JTextField totalSinIvaField = new JTextField();
        totalSinIvaField.setEditable(false);

        JLabel totalConIvaLabel = new JLabel("Total con IVA:");
        JTextField totalConIvaField = new JTextField();
        totalConIvaField.setEditable(false);

        totalPanel.add(totalSinIvaLabel);
        totalPanel.add(totalSinIvaField);
        totalPanel.add(totalConIvaLabel);
        totalPanel.add(totalConIvaField);

        // Añadir el panel de totales a la esquina inferior izquierda
        gbc.gridx = 2;
        gbc.gridy = 4;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.SOUTHWEST;
        facturacionPanel.add(totalPanel, gbc);

        // En el listener del botón "Total"
        totalButton.addActionListener(e -> {
            double totalSinIva = 0.0;
            double ivaRate = 0.15; // Tasa de IVA del 15%

            for (int i = 0; i < tableModel.getRowCount(); i++) {
                try {
                    double valor = Double.parseDouble(tableModel.getValueAt(i, 4).toString());
                    totalSinIva += valor;
                } catch (NumberFormatException ex) {
                    // Ignorar filas con valores inválidos
                }
            }

            double totalConIva = totalSinIva * (1 + ivaRate);

            totalSinIvaField.setText(String.format("%.2f", totalSinIva));
            totalConIvaField.setText(String.format("%.2f", totalConIva));
        });


        deleteButton.addActionListener(e -> {
            while (true) {
                // Solicitar ID del producto a eliminar
                String idInput = JOptionPane.showInputDialog(this, "Ingrese el ID del producto que desea eliminar:", "Eliminar Producto", JOptionPane.QUESTION_MESSAGE);

                if (idInput == null) {
                    // Si el usuario cancela la entrada, salimos del bucle
                    return;
                }

                if (idInput.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Debe ingresar un número.", "Error", JOptionPane.ERROR_MESSAGE);
                    continue;
                }

                try {
                    int productId = Integer.parseInt(idInput.trim());

                    // Buscar la fila que contiene el ID especificado
                    boolean found = false;
                    for (int i = 0; i < tableModel.getRowCount(); i++) {
                        if (productId == Integer.parseInt((String) tableModel.getValueAt(i, 0))) { // Asumiendo que el ID está en la primera columna
                            tableModel.removeRow(i);
                            found = true;
                            break;
                        }
                    }

                    if (!found) {
                        JOptionPane.showMessageDialog(this, "Producto con ID " + productId + " no encontrado en la tabla.", "Error", JOptionPane.ERROR_MESSAGE);
                    }

                    // Salimos del bucle si el ID es válido y la operación se completó
                    break;
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Por favor, ingrese un ID válido.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });


        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 3;
        gbc.weighty = 1;
        facturacionPanel.add(tableScrollPane, gbc);

        fechaField.setText(LocalDate.now().toString());
        nroautorizacionField.setText(generateRandomString());

        guardarButton.addActionListener(e -> {
            String razonSocial = razonSocialField.getText();
            String direccion = direccionField.getText();
            String telefono = telefonoField.getText();
            String correo = correoField.getText();
            String ruc = rucField.getText();
            String fecha = fechaField.getText();
            String nroautorizacion = nroautorizacionField.getText();
            String clienteId = clienteIdField.getText();

            if (clienteId.isEmpty() || tableModel.getRowCount() == 0) {
                JOptionPane.showMessageDialog(this, "Por favor, complete todos los campos y agregue productos a la factura.", "Advertencia", JOptionPane.WARNING_MESSAGE);
                return;
            }


        nuevaFacturaButton.addActionListener(evt -> {
            // Limpiar campos y tabla para nueva factura
            clienteIdField.setText("");
            clienteNombreField.setText("");
            clienteApellidoField.setText("");
            clienteCorreoField.setText("");
            clienteTelefonoField.setText("");
            clienteDireccionField.setText("");
            fechaField.setText(LocalDate.now().toString());
            nroautorizacionField.setText(generateRandomString());



            // Limpiar la tabla
            tableModel.setRowCount(0);


        });


            // Calcular los totales
            double totalSinIva = 0.0;
            double ivaRate = 0.15; // Tasa de IVA del 15%
            for (int i = 0; i < tableModel.getRowCount(); i++) {
                try {
                    double valor = Double.parseDouble(tableModel.getValueAt(i, 4).toString());
                    totalSinIva += valor;
                } catch (NumberFormatException ex) {
                    // Ignorar filas con valores inválidos
                }
            }
            double totalConIva = totalSinIva * (1 + ivaRate);

            try (Connection connection = DatabaseConnection.getConnection()) {
                connection.setAutoCommit(false); // Iniciar transacción

                // Insertar datos en la tabla Factura
                String insertFacturaQuery = "INSERT INTO Factura (Razon_Social, Direccion, Telefono, Correo, RUC, Fecha, Nro_Autorizacion, Cliente_ID, total_sin_iva, total_con_iva) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
                try (PreparedStatement facturaStatement = connection.prepareStatement(insertFacturaQuery, Statement.RETURN_GENERATED_KEYS)) {
                    facturaStatement.setString(1, razonSocial);
                    facturaStatement.setString(2, direccion);
                    facturaStatement.setString(3, telefono);
                    facturaStatement.setString(4, correo);
                    facturaStatement.setString(5, ruc);
                    facturaStatement.setString(6, fecha);
                    facturaStatement.setString(7, nroautorizacion);
                    facturaStatement.setString(8, clienteId);
                    facturaStatement.setDouble(9, totalSinIva);
                    facturaStatement.setDouble(10, totalConIva);

                    int affectedRows = facturaStatement.executeUpdate();
                    if (affectedRows == 0) {
                        throw new SQLException("La creación de la factura falló, no se creó ninguna fila.");
                    }

                    ResultSet generatedKeys = facturaStatement.getGeneratedKeys();
                    if (generatedKeys.next()) {
                        long facturaId = generatedKeys.getLong(1);

                        // Insertar detalles de la factura en la tabla DetalleFactura
                        String insertDetalleQuery = "INSERT INTO DetalleFactura (Factura_ID, Productos_ID, Cantidad, Precio, Valor) VALUES (?, ?, ?, ?, ?)";
                        try (PreparedStatement detalleStatement = connection.prepareStatement(insertDetalleQuery)) {
                            for (int i = 0; i < tableModel.getRowCount(); i++) {
                                String productosId = tableModel.getValueAt(i, 0).toString();
                                int cantidad = Integer.parseInt(tableModel.getValueAt(i, 2).toString());
                                double precio = Double.parseDouble(tableModel.getValueAt(i, 3).toString());
                                double valor = cantidad * precio;

                                detalleStatement.setLong(1, facturaId);
                                detalleStatement.setString(2, productosId);
                                detalleStatement.setInt(3, cantidad);
                                detalleStatement.setDouble(4, precio);
                                detalleStatement.setDouble(5, valor);  // Insertar el valor calculado
                                detalleStatement.addBatch();
                            }
                            detalleStatement.executeBatch();
                        }
                    } else {
                        throw new SQLException("La creación de la factura falló, no se obtuvieron las claves generadas.");
                    }

                    connection.commit(); // Confirmar la transacción
                    JOptionPane.showMessageDialog(this, "Factura guardada exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                } catch (SQLException ex) {
                    connection.rollback(); // Revertir la transacción en caso de error
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(this, "Error al guardar la factura: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error al conectar con la base de datos: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });


        JScrollPane scrollPane = new JScrollPane(facturacionPanel);
        mainPanel.add(scrollPane, "facturacionPanel");
    }

    // Generar un string aleatorio de 10 caracteres alfanuméricos
    public static String generateRandomString() {
        int length = 10;
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            sb.append(characters.charAt(random.nextInt(characters.length())));
        }
        return sb.toString();
    }


    private void createClientesPanel() {
        JPanel clientesPanel = new JPanel(new BorderLayout());

        JLabel titleLabel = new JLabel("Registro de Clientes", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24)); // Ajusta el tamaño y el estilo del texto

        clientesPanel.add(titleLabel, BorderLayout.NORTH); // Agrega el título sobre la tabla de clientes

        clientesTable = new JTable();
        clientesTable.setEnabled(false); // Disable editing
        JScrollPane scrollPane = new JScrollPane(clientesTable);
        clientesPanel.add(scrollPane, BorderLayout.CENTER);

        // Crear el panel para los botones de acción
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20)); // Añade espacio entre los botones
        JButton addButton = new JButton("Añadir");
        JButton editButton = new JButton("Editar");
        JButton deleteButton = new JButton("Eliminar");

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addClient();
            }
        });

        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editClient();
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteClient();
            }
        });

        // Agregar los botones al panel
        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);

        clientesPanel.add(buttonPanel, BorderLayout.SOUTH); // Agrega los botones en la parte inferior

        mainPanel.add(clientesPanel, "clientesPanel");
    }

    private void createProductosPanel() {
        JPanel productosPanel = new JPanel(new BorderLayout());

        JLabel titleLabel = new JLabel("Registro de Productos", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24)); // Ajusta el tamaño y el estilo del texto

        productosPanel.add(titleLabel, BorderLayout.NORTH); // Agrega el título sobre la tabla de productos

        productosTable = new JTable();
        productosTable.setEnabled(false); // Disable editing
        JScrollPane scrollPane = new JScrollPane(productosTable);
        productosPanel.add(scrollPane, BorderLayout.CENTER);

        // Crear el panel para los botones de acción
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20)); // Añade espacio entre los botones
        JButton addButton = new JButton("Añadir");
        JButton editButton = new JButton("Editar");
        JButton deleteButton = new JButton("Eliminar");

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addProduct();
            }
        });

        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editProduct();
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteProduct();
            }
        });

        // Agregar los botones al panel
        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);

        productosPanel.add(buttonPanel, BorderLayout.SOUTH); // Agrega los botones en la parte inferior

        mainPanel.add(productosPanel, "productosPanel");
    }

    private void fetchDataFromDatabase(String tableName, JTable table, String... columnNames) {
        String columns = String.join(", ", columnNames);
        String query = "SELECT " + columns + " FROM " + tableName;
        DefaultTableModel model = new DefaultTableModel();

        for (String columnName : columnNames) {
            model.addColumn(columnName);
        }

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                Object[] rowData = new Object[columnNames.length];
                for (int i = 0; i < columnNames.length; i++) {
                    rowData[i] = resultSet.getObject(columnNames[i]);
                }
                model.addRow(rowData);
            }

            table.setModel(model);
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al extraer datos: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void addClient() {
        JTextField firstNameField = new JTextField(10);
        JTextField lastNameField = new JTextField(10);
        JTextField emailField = new JTextField(10);
        JTextField phoneField = new JTextField(10);
        JTextField addressField = new JTextField(10);

        JPanel myPanel = new JPanel();
        myPanel.setLayout(new GridLayout(5, 2));
        myPanel.add(new JLabel("Nombre:"));
        myPanel.add(firstNameField);
        myPanel.add(new JLabel("Apellido:"));
        myPanel.add(lastNameField);
        myPanel.add(new JLabel("Correo:"));
        myPanel.add(emailField);
        myPanel.add(new JLabel("Teléfono:"));
        myPanel.add(phoneField);
        myPanel.add(new JLabel("Dirección:"));
        myPanel.add(addressField);

        int result = JOptionPane.showConfirmDialog(null, myPanel, "Añadir Cliente", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            // Validación para asegurarse de que ningún campo esté vacío
            if (firstNameField.getText().trim().isEmpty() || lastNameField.getText().trim().isEmpty() ||
                    emailField.getText().trim().isEmpty() || phoneField.getText().trim().isEmpty() || addressField.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Todos los campos son obligatorios. Por favor, complete todos los campos.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try (Connection connection = DatabaseConnection.getConnection();
                 PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO Clientes (Clientes_Nombre, Clientes_Apellido, Clientes_Correo, Clientes_Telefono, Clientes_Direccion) VALUES (?, ?, ?, ?, ?)")) {
                preparedStatement.setString(1, firstNameField.getText());
                preparedStatement.setString(2, lastNameField.getText());
                preparedStatement.setString(3, emailField.getText());
                preparedStatement.setString(4, phoneField.getText());
                preparedStatement.setString(5, addressField.getText());
                preparedStatement.executeUpdate();
                fetchDataFromDatabase("Clientes", clientesTable, "Clientes_ID", "Clientes_Nombre", "Clientes_Apellido", "Clientes_Correo", "Clientes_Telefono", "Clientes_Direccion");
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error al añadir cliente: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public void editClient() {
        String id;
        while (true) {
            id = JOptionPane.showInputDialog("Ingrese el ID del Cliente a editar:");

            // Si el usuario cancela la entrada o no ingresa nada, mostramos un mensaje de error y salimos
            if (id == null || id.trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Debe ingresar un número.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Verificamos si el ID es un número válido
            try {
                int clientId = Integer.parseInt(id.trim());
                // Si hemos llegado aquí, el ID es válido, salimos del bucle
                break;
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "ID inválido. Por favor, ingrese un número válido.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM Clientes WHERE Clientes_ID = ?")) {
            preparedStatement.setInt(1, Integer.parseInt(id.trim()));
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                JTextField firstNameField = new JTextField(resultSet.getString("Clientes_Nombre"), 10);
                JTextField lastNameField = new JTextField(resultSet.getString("Clientes_Apellido"), 10);
                JTextField emailField = new JTextField(resultSet.getString("Clientes_Correo"), 10);
                JTextField phoneField = new JTextField(resultSet.getString("Clientes_Telefono"), 10);
                JTextField addressField = new JTextField(resultSet.getString("Clientes_Direccion"), 10);

                JPanel myPanel = new JPanel();
                myPanel.setLayout(new GridLayout(5, 2));
                myPanel.add(new JLabel("Nombre:"));
                myPanel.add(firstNameField);
                myPanel.add(new JLabel("Apellido:"));
                myPanel.add(lastNameField);
                myPanel.add(new JLabel("Correo:"));
                myPanel.add(emailField);
                myPanel.add(new JLabel("Teléfono:"));
                myPanel.add(phoneField);
                myPanel.add(new JLabel("Dirección:"));
                myPanel.add(addressField);

                int result = JOptionPane.showConfirmDialog(null, myPanel, "Editar Cliente", JOptionPane.OK_CANCEL_OPTION);
                if (result == JOptionPane.OK_OPTION) {
                    try (PreparedStatement updateStatement = connection.prepareStatement("UPDATE Clientes SET Clientes_Nombre = ?, Clientes_Apellido = ?, Clientes_Correo = ?, Clientes_Telefono = ?, Clientes_Direccion = ? WHERE Clientes_ID = ?")) {
                        updateStatement.setString(1, firstNameField.getText());
                        updateStatement.setString(2, lastNameField.getText());
                        updateStatement.setString(3, emailField.getText());
                        updateStatement.setString(4, phoneField.getText());
                        updateStatement.setString(5, addressField.getText());
                        updateStatement.setInt(6, Integer.parseInt(id.trim()));
                        updateStatement.executeUpdate();
                        fetchDataFromDatabase("Clientes", clientesTable, "Clientes_ID", "Clientes_Nombre", "Clientes_Apellido", "Clientes_Correo", "Clientes_Telefono", "Clientes_Direccion");
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this, "Cliente no encontrado", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al acceder a la base de datos: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void deleteClient() {
        String id;
        while (true) {
            id = JOptionPane.showInputDialog("Ingrese el ID del Cliente a eliminar:");

            // Si el usuario cancela la entrada o no ingresa nada, mostramos un mensaje de error y salimos
            if (id == null || id.trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Debe ingresar un número.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Verificamos si el ID es un número válido
            try {
                int clientId = Integer.parseInt(id.trim());
                // Si hemos llegado aquí, el ID es válido, salimos del bucle
                break;
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "ID inválido. Por favor, ingrese un número válido.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }

        try {
            int clientId = Integer.parseInt(id.trim());
            try (Connection connection = DatabaseConnection.getConnection();
                 PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM Clientes WHERE Clientes_ID = ?")) {
                preparedStatement.setInt(1, clientId);
                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    // Mostrar los datos del cliente
                    String clientInfo = "Nombre: " + resultSet.getString("Clientes_Nombre") +
                            "\nApellido: " + resultSet.getString("Clientes_Apellido") +
                            "\nCorreo: " + resultSet.getString("Clientes_Correo") +
                            "\nTeléfono: " + resultSet.getString("Clientes_Telefono") +
                            "\nDirección: " + resultSet.getString("Clientes_Direccion");

                    int confirm = JOptionPane.showConfirmDialog(null, "¿Desea eliminar el siguiente cliente?\n\n" + clientInfo, "Confirmar eliminación", JOptionPane.YES_NO_OPTION);
                    if (confirm == JOptionPane.YES_OPTION) {
                        // Eliminar el cliente
                        try (PreparedStatement deleteStatement = connection.prepareStatement("DELETE FROM Clientes WHERE Clientes_ID = ?")) {
                            deleteStatement.setInt(1, clientId);
                            deleteStatement.executeUpdate();

                            // Actualizar la tabla
                            fetchDataFromDatabase("Clientes", clientesTable, "Clientes_ID", "Clientes_Nombre", "Clientes_Apellido", "Clientes_Correo", "Clientes_Telefono", "Clientes_Direccion");
                            JOptionPane.showMessageDialog(this, "Cliente eliminado exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Cliente no encontrado", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error al acceder a la base de datos: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "ID inválido. Por favor, ingrese un número válido.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void addProduct() {
        JTextField nameField = new JTextField(10);
        JTextField categoryField = new JTextField(10);
        JTextField priceField = new JTextField(10);
        JTextField brandField = new JTextField(10);

        // Añadir un InputVerifier al campo de precio
        priceField.setInputVerifier(new InputVerifier() {
            @Override
            public boolean verify(JComponent input) {
                JTextField textField = (JTextField) input;
                String text = textField.getText();
                try {
                    Double.parseDouble(text);
                    return true;
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(null, "Por favor, ingrese un valor decimal válido para el precio.", "Entrada inválida", JOptionPane.ERROR_MESSAGE);
                    return false;
                }
            }
        });

        JPanel myPanel = new JPanel();
        myPanel.setLayout(new GridLayout(4, 2));
        myPanel.add(new JLabel("Nombre:"));
        myPanel.add(nameField);
        myPanel.add(new JLabel("Categoría:"));
        myPanel.add(categoryField);
        myPanel.add(new JLabel("Precio:"));
        myPanel.add(priceField);
        myPanel.add(new JLabel("Marca:"));
        myPanel.add(brandField);

        int result = JOptionPane.showConfirmDialog(null, myPanel, "Añadir Producto", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            // Verificar que todos los campos no estén vacíos y que el precio sea válido
            if (!nameField.getText().trim().isEmpty() && !categoryField.getText().trim().isEmpty() && !priceField.getText().trim().isEmpty() && !brandField.getText().trim().isEmpty()) {
                if (priceField.getInputVerifier().verify(priceField)) {
                    try (Connection connection = DatabaseConnection.getConnection();
                         PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO Productos (Productos_Nombre, Productos_Categoria, Productos_Precio, Productos_Marca) VALUES (?, ?, ?, ?)")) {
                        preparedStatement.setString(1, nameField.getText());
                        preparedStatement.setString(2, categoryField.getText());
                        preparedStatement.setDouble(3, Double.parseDouble(priceField.getText()));
                        preparedStatement.setString(4, brandField.getText());
                        preparedStatement.executeUpdate();
                        fetchDataFromDatabase("Productos", productosTable, "Productos_ID", "Productos_Nombre", "Productos_Categoria", "Productos_Precio", "Productos_Marca");
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(this, "Error al añadir producto: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this, "Todos los campos deben estar llenos.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }



    public void editProduct() {
        String id;
        while (true) {
            id = JOptionPane.showInputDialog("Ingrese el ID del Producto a editar:");

            // Si el usuario cancela la entrada o no ingresa nada, mostramos un mensaje de error y salimos
            if (id == null || id.trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Debe ingresar un número.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Verificamos si el ID es un número válido
            try {
                int productId = Integer.parseInt(id.trim());
                // Si hemos llegado aquí, el ID es válido, salimos del bucle
                break;
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "ID inválido. Por favor, ingrese un número válido.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM Productos WHERE Productos_ID = ?")) {
            preparedStatement.setInt(1, Integer.parseInt(id.trim()));
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                JTextField nameField = new JTextField(resultSet.getString("Productos_Nombre"), 10);
                JTextField categoryField = new JTextField(resultSet.getString("Productos_Categoria"), 10);
                JTextField priceField = new JTextField(resultSet.getDouble("Productos_Precio") + "", 10);
                JTextField brandField = new JTextField(resultSet.getString("Productos_Marca"), 10);

                JPanel myPanel = new JPanel();
                myPanel.setLayout(new GridLayout(4, 2));
                myPanel.add(new JLabel("Nombre:"));
                myPanel.add(nameField);
                myPanel.add(new JLabel("Categoría:"));
                myPanel.add(categoryField);
                myPanel.add(new JLabel("Precio:"));
                myPanel.add(priceField);
                myPanel.add(new JLabel("Marca:"));
                myPanel.add(brandField);

                int result = JOptionPane.showConfirmDialog(null, myPanel, "Editar Producto", JOptionPane.OK_CANCEL_OPTION);
                if (result == JOptionPane.OK_OPTION) {
                    // Validación de campos vacíos
                    if (nameField.getText().trim().isEmpty() || categoryField.getText().trim().isEmpty() ||
                            priceField.getText().trim().isEmpty() || brandField.getText().trim().isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Todos los campos son obligatorios. Por favor, complete todos los campos.", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    // Validación de precio como decimal
                    try {
                        double price = Double.parseDouble(priceField.getText().trim());
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(null, "Precio inválido. Por favor, ingrese un número decimal válido.", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    try (PreparedStatement updateStatement = connection.prepareStatement("UPDATE Productos SET Productos_Nombre = ?, Productos_Categoria = ?, Productos_Precio = ?, Productos_Marca = ? WHERE Productos_ID = ?")) {
                        updateStatement.setString(1, nameField.getText());
                        updateStatement.setString(2, categoryField.getText());
                        updateStatement.setDouble(3, Double.parseDouble(priceField.getText().trim()));
                        updateStatement.setString(4, brandField.getText());
                        updateStatement.setInt(5, Integer.parseInt(id.trim()));
                        updateStatement.executeUpdate();
                        fetchDataFromDatabase("Productos", productosTable, "Productos_ID", "Productos_Nombre", "Productos_Categoria", "Productos_Precio", "Productos_Marca");
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this, "Producto no encontrado", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al acceder a la base de datos: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }


    public void deleteProduct() {
        String id;
        while (true) {
            id = JOptionPane.showInputDialog("Ingrese el ID del Producto a eliminar:");

            // Si el usuario cancela la entrada o no ingresa nada, salimos del bucle
            if (id == null || id.trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Debe ingresar un número.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Verificamos si el ID es un número válido
            try {
                int productId = Integer.parseInt(id.trim());
                // Si hemos llegado aquí, el ID es válido, salimos del bucle
                break;
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "ID inválido. Por favor, ingrese un número válido.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }

        try {
            int productId = Integer.parseInt(id.trim());
            try (Connection connection = DatabaseConnection.getConnection();
                 PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM Productos WHERE Productos_ID = ?")) {
                preparedStatement.setInt(1, productId);
                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    // Mostrar los datos del producto
                    String productInfo = "Nombre: " + resultSet.getString("Productos_Nombre") +
                            "\nCategoría: " + resultSet.getString("Productos_Categoria") +
                            "\nPrecio: " + resultSet.getDouble("Productos_Precio") +
                            "\nMarca: " + resultSet.getString("Productos_Marca");

                    int confirm = JOptionPane.showConfirmDialog(null, "¿Desea eliminar el siguiente producto?\n\n" + productInfo, "Confirmar eliminación", JOptionPane.YES_NO_OPTION);
                    if (confirm == JOptionPane.YES_OPTION) {
                        // Eliminar el producto
                        try (PreparedStatement deleteStatement = connection.prepareStatement("DELETE FROM Productos WHERE Productos_ID = ?")) {
                            deleteStatement.setInt(1, productId);
                            deleteStatement.executeUpdate();

                            // Actualizar la tabla
                            fetchDataFromDatabase("Productos", productosTable, "Productos_ID", "Productos_Nombre", "Productos_Categoria", "Productos_Precio", "Productos_Marca");
                            JOptionPane.showMessageDialog(this, "Producto eliminado exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Producto no encontrado", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error al acceder a la base de datos: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "ID inválido. Por favor, ingrese un número válido.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MainFrame().setVisible(true);
            }
        });
    }
}
