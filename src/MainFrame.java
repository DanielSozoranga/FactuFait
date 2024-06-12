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
        setTitle("Interfaz Gráfica con Base de Datos");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

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

        add(mainPanel);
    }

    private void createMenuPanel() {
        JMenuBar menuBar = new JMenuBar();

        // Agregar la barra de navegación
        JMenu navegacionMenu = new JMenu("Navegación");
        menuBar.add(navegacionMenu);

        JMenuItem menuMenuItem = new JMenuItem("Inicio");

        menuMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(mainPanel, "menuPanel");
            }
        });

        navegacionMenu.add(menuMenuItem);


        // Configuración del botón Facturación
        JMenuItem facturacionMenuItem = new JMenuItem("Facturación");
        facturacionMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(mainPanel, "facturacionPanel");
            }
        });
        navegacionMenu.add(facturacionMenuItem);

        // Agregar la barra de navegación a la ventana
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

        JButton clientesButton = new JButton("Clientes");
        clientesButton.setFont(new Font("Arial", Font.PLAIN, 40)); // Aumenta el tamaño de la fuente
        clientesButton.setPreferredSize(new Dimension(300, 120)); // Ajusta las dimensiones del botón
        clientesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(mainPanel, "clientesPanel");
                fetchDataFromDatabase("Clientes", clientesTable, "Clientes_ID", "Clientes_Nombre", "Clientes_Apellido", "Clientes_Correo", "Clientes_Telefono", "Clientes_Direccion");
            }
        });
        menuPanel.add(clientesButton, gbc);

        gbc.gridx = 1;
        JButton productosButton = new JButton("Productos");
        productosButton.setFont(new Font("Arial", Font.PLAIN, 40));
        productosButton.setPreferredSize(new Dimension(300, 120)); // Ajusta las dimensiones del botón
        productosButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(mainPanel, "productosPanel");
                fetchDataFromDatabase("Productos", productosTable, "Productos_ID", "Productos_Nombre", "Productos_Categoria", "Productos_Precio", "Productos_Marca", "Productos_Cantidad_Unidad", "Productos_IVA", "Productos_Stock");
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
                cardLayout.show(mainPanel, "facturacionPanel");
            }
        });
        menuPanel.add(facturacionButton, gbc);

        mainPanel.add(menuPanel, "menuPanel");
        cardLayout.show(mainPanel, "menuPanel");
    }

    private void createFacturacionPanel() {
        JPanel facturacionPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1;
        gbc.weighty = 1;

        JLabel titleLabel = new JLabel("Papelería DAKAEL", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Serif", Font.BOLD, 24));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 3;
        facturacionPanel.add(titleLabel, gbc);

        JPanel infoPanel = new JPanel(new GridLayout(7, 2, 10, 10));
        JLabel razonSocialLabel = new JLabel("Razón Social:");
        JTextField razonSocialField = new JTextField();
        razonSocialField.setEditable(false);

        JLabel direccionLabel = new JLabel("Dirección:");
        JTextField direccionField = new JTextField();
        direccionField.setEditable(true);

        JLabel telefonoLabel = new JLabel("Teléfono:");
        JTextField telefonoField = new JTextField();
        telefonoField.setEditable(true);

        JLabel correoLabel = new JLabel("Correo:");
        JTextField correoField = new JTextField();
        correoField.setEditable(true);

        JLabel rucLabel = new JLabel("RUC:");
        JTextField rucField = new JTextField();
        rucField.setEditable(true);

        JLabel fechaLabel = new JLabel("Fecha:");
        JTextField fechaField = new JTextField();
        fechaField.setEditable(true);

        JLabel nroautorizacionLabel = new JLabel("Factura ID:");
        JTextField nroautorizacionField = new JTextField();
        nroautorizacionField.setEditable(true);

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

        JPanel buttonPanel = new JPanel(new GridLayout(1, 4, 20, 10));
        JButton addButton = new JButton("Añadir");
        JButton deleteButton = new JButton("Eliminar");
        JButton totalButton = new JButton("Total");
        JButton guardarButton = new JButton("Guardar");

        buttonPanel.add(addButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(totalButton);
        buttonPanel.add(guardarButton);

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

            if (clienteId.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Por favor, ingrese un ID de cliente.", "Advertencia", JOptionPane.WARNING_MESSAGE);
                return;
            }

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
                            JOptionPane.showMessageDialog(this, "Cliente no encontrado.", "Información", JOptionPane.INFORMATION_MESSAGE);
                        }
                    }
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error al conectar a la base de datos", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        String[] columnNames = {"Producto", "Nombre", "Cantidad", "Precio", "Valor"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
        JTable productTable = new JTable(tableModel);
        JScrollPane tableScrollPane = new JScrollPane(productTable);

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
            String productoId = JOptionPane.showInputDialog(this, "Ingrese el ID del Producto:", "Añadir Producto", JOptionPane.PLAIN_MESSAGE);

            if (productoId != null && !productoId.trim().isEmpty()) {
                boolean exists = false;
                for (int i = 0; i < tableModel.getRowCount(); i++) {
                    if (tableModel.getValueAt(i, 0).equals(productoId)) {
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
                        preparedStatement.setString(1, productoId);
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
            // Solicitar ID del producto a eliminar
            String idInput = JOptionPane.showInputDialog(this, "Ingrese el ID del producto que desea eliminar:", "Eliminar Producto", JOptionPane.QUESTION_MESSAGE);

            if (idInput != null && !idInput.trim().isEmpty()) {
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
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Por favor, ingrese un ID válido.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "ID no ingresado.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            }
        });

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 3;
        gbc.weighty = 1;
        facturacionPanel.add(tableScrollPane, gbc);

        fechaField.setText(LocalDate.now().toString());
        nroautorizacionField.setText(generateRandomString(20));

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

    // Generar un string aleatorio
    public static String generateRandomString(int length) {
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

    private void addClient() {
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

    private void editClient() {
        String id = JOptionPane.showInputDialog("Ingrese el ID del Cliente a editar:");
        if (id != null && !id.trim().isEmpty()) {
            try (Connection connection = DatabaseConnection.getConnection();
                 PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM Clientes WHERE Clientes_ID = ?")) {
                preparedStatement.setInt(1, Integer.parseInt(id));
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
                            updateStatement.setInt(6, Integer.parseInt(id));
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
    }

    private void deleteClient() {
        String id = JOptionPane.showInputDialog("Ingrese el ID del Cliente a eliminar:");
        if (id != null && !id.trim().isEmpty()) {
            try {
                int clientId = Integer.parseInt(id); // Asegúrate de que el ID sea un número válido
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
    }

    private void addProduct() {
        JTextField nameField = new JTextField(10);
        JTextField categoryField = new JTextField(10);
        JTextField priceField = new JTextField(10);
        JTextField brandField = new JTextField(10);
        JTextField quantityUnitField = new JTextField(10);
        JTextField ivaField = new JTextField(10);
        JTextField stockField = new JTextField(10);

        JPanel myPanel = new JPanel();
        myPanel.setLayout(new GridLayout(7, 2));
        myPanel.add(new JLabel("Nombre:"));
        myPanel.add(nameField);
        myPanel.add(new JLabel("Categoría:"));
        myPanel.add(categoryField);
        myPanel.add(new JLabel("Precio:"));
        myPanel.add(priceField);
        myPanel.add(new JLabel("Marca:"));
        myPanel.add(brandField);
        myPanel.add(new JLabel("Cantidad/Unidad:"));
        myPanel.add(quantityUnitField);
        myPanel.add(new JLabel("IVA:"));
        myPanel.add(ivaField);
        myPanel.add(new JLabel("Stock:"));
        myPanel.add(stockField);

        int result = JOptionPane.showConfirmDialog(null, myPanel, "Añadir Producto", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            try (Connection connection = DatabaseConnection.getConnection();
                 PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO Productos (Productos_Nombre, Productos_Categoria, Productos_Precio, Productos_Marca, Productos_Cantidad_Unidad, Productos_IVA, Productos_Stock) VALUES (?, ?, ?, ?, ?, ?, ?)")) {
                preparedStatement.setString(1, nameField.getText());
                preparedStatement.setString(2, categoryField.getText());
                preparedStatement.setDouble(3, Double.parseDouble(priceField.getText()));
                preparedStatement.setString(4, brandField.getText());
                preparedStatement.setString(5, quantityUnitField.getText());
                preparedStatement.setDouble(6, Double.parseDouble(ivaField.getText()));
                preparedStatement.setInt(7, Integer.parseInt(stockField.getText()));
                preparedStatement.executeUpdate();
                fetchDataFromDatabase("Productos", productosTable, "Productos_ID", "Productos_Nombre", "Productos_Categoria", "Productos_Precio", "Productos_Marca", "Productos_Cantidad_Unidad", "Productos_IVA", "Productos_Stock");
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error al añadir producto: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void editProduct() {
        String id = JOptionPane.showInputDialog("Ingrese el ID del Producto a editar:");
        if (id != null && !id.trim().isEmpty()) {
            try (Connection connection = DatabaseConnection.getConnection();
                 PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM Productos WHERE Productos_ID = ?")) {
                preparedStatement.setInt(1, Integer.parseInt(id));
                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    JTextField nameField = new JTextField(resultSet.getString("Productos_Nombre"), 10);
                    JTextField categoryField = new JTextField(resultSet.getString("Productos_Categoria"), 10);
                    JTextField priceField = new JTextField(resultSet.getDouble("Productos_Precio") + "", 10);
                    JTextField brandField = new JTextField(resultSet.getString("Productos_Marca"), 10);
                    JTextField quantityUnitField = new JTextField(resultSet.getString("Productos_Cantidad_Unidad"), 10);
                    JTextField ivaField = new JTextField(resultSet.getDouble("Productos_IVA") + "", 10);
                    JTextField stockField = new JTextField(resultSet.getInt("Productos_Stock") + "", 10);

                    JPanel myPanel = new JPanel();
                    myPanel.setLayout(new GridLayout(7, 2));
                    myPanel.add(new JLabel("Nombre:"));
                    myPanel.add(nameField);
                    myPanel.add(new JLabel("Categoría:"));
                    myPanel.add(categoryField);
                    myPanel.add(new JLabel("Precio:"));
                    myPanel.add(priceField);
                    myPanel.add(new JLabel("Marca:"));
                    myPanel.add(brandField);
                    myPanel.add(new JLabel("Cantidad/Unidad:"));
                    myPanel.add(quantityUnitField);
                    myPanel.add(new JLabel("IVA:"));
                    myPanel.add(ivaField);
                    myPanel.add(new JLabel("Stock:"));
                    myPanel.add(stockField);

                    int result = JOptionPane.showConfirmDialog(null, myPanel, "Editar Producto", JOptionPane.OK_CANCEL_OPTION);
                    if (result == JOptionPane.OK_OPTION) {
                        try (PreparedStatement updateStatement = connection.prepareStatement("UPDATE Productos SET Productos_Nombre = ?, Productos_Categoria = ?, Productos_Precio = ?, Productos_Marca = ?, Productos_Cantidad_Unidad = ?, Productos_IVA = ?, Productos_Stock = ? WHERE Productos_ID = ?")) {
                            updateStatement.setString(1, nameField.getText());
                            updateStatement.setString(2, categoryField.getText());
                            updateStatement.setDouble(3, Double.parseDouble(priceField.getText()));
                            updateStatement.setString(4, brandField.getText());
                            updateStatement.setString(5, quantityUnitField.getText());
                            updateStatement.setDouble(6, Double.parseDouble(ivaField.getText()));
                            updateStatement.setInt(7, Integer.parseInt(stockField.getText()));
                            updateStatement.setInt(8, Integer.parseInt(id));
                            updateStatement.executeUpdate();
                            fetchDataFromDatabase("Productos", productosTable, "Productos_ID", "Productos_Nombre", "Productos_Categoria", "Productos_Precio", "Productos_Marca", "Productos_Cantidad_Unidad", "Productos_IVA", "Productos_Stock");
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
    }

    private void deleteProduct() {
        String id = JOptionPane.showInputDialog("Ingrese el ID del Producto a eliminar:");
        if (id != null && !id.trim().isEmpty()) {
            try {
                int productId = Integer.parseInt(id); // Asegúrate de que el ID sea un número válido
                try (Connection connection = DatabaseConnection.getConnection();
                     PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM Productos WHERE Productos_ID = ?")) {
                    preparedStatement.setInt(1, productId);
                    ResultSet resultSet = preparedStatement.executeQuery();
                    if (resultSet.next()) {
                        // Mostrar los datos del producto
                        String productInfo = "Nombre: " + resultSet.getString("Productos_Nombre") +
                                "\nCategoría: " + resultSet.getString("Productos_Categoria") +
                                "\nPrecio: " + resultSet.getDouble("Productos_Precio") +
                                "\nMarca: " + resultSet.getString("Productos_Marca") +
                                "\nCantidad/Unidad: " + resultSet.getString("Productos_Cantidad_Unidad") +
                                "\nIVA: " + resultSet.getDouble("Productos_IVA") +
                                "\nStock: " + resultSet.getInt("Productos_Stock");

                        int confirm = JOptionPane.showConfirmDialog(null, "¿Desea eliminar el siguiente producto?\n\n" + productInfo, "Confirmar eliminación", JOptionPane.YES_NO_OPTION);
                        if (confirm == JOptionPane.YES_OPTION) {
                            // Eliminar el producto
                            try (PreparedStatement deleteStatement = connection.prepareStatement("DELETE FROM Productos WHERE Productos_ID = ?")) {
                                deleteStatement.setInt(1, productId);
                                deleteStatement.executeUpdate();

                                // Actualizar la tabla
                                fetchDataFromDatabase("Productos", productosTable, "Productos_ID", "Productos_Nombre", "Productos_Categoria", "Productos_Precio", "Productos_Marca", "Productos_Cantidad_Unidad", "Productos_IVA", "Productos_Stock");
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
