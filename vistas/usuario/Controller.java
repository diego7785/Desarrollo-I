import DB_Connection.DBConnection;
import Exceptions.EmptyFieldException;
import Exceptions.NaturalCustomerException;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import javax.swing.*;
import java.net.URL;
import java.util.*;

import javax.mail.*;
import javax.mail.internet.*;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Controller implements Initializable {
    //Class ConstructorP
    public Controller() {}

    //Database connection
    private DBConnection connection= new DBConnection("", "", "", "", "", "");

    private final Calendar cal = Calendar.getInstance();

    //User class object
    user new_user = new user();

    private final String months[]={"Enero","Febrero","Marzo","Abril","Mayo","Junio","Julio",
            "Agosto","Septiembre","Octubre","Noviembre","Diciembre"};

    //Vectors for the ComboBox
    ObservableList<String> tipoCliente = FXCollections.observableArrayList( "Natural", "Corporativo");
    ObservableList<String> tipoDocumento= FXCollections.observableArrayList( "Cédula Ciudadanía", "Cédula Extranjería",
            "Pasaporte", "Carné Diplomático", "Tarjeta de Identidad");

    ObservableList<String> genero = FXCollections.observableArrayList( "Femenino", "Masculino", "Otro");
    ObservableList<String> estado_civil = FXCollections.observableArrayList( "Casado", "Soltero", "Viudo", "Divorciado");

    ObservableList<String> rol = FXCollections.observableArrayList( "Administrador", "Gerente", "Operador");
    ObservableList<String> estado = FXCollections.observableArrayList( "true", "flase");

    ObservableList<String> plan_asociado = FXCollections.observableArrayList( "Plan Conéctate", "Plan Conéctate Plus", "Plan Conectados Somos Más", "Plan Redes Sin Límites", "Plan Uno Es Más");
    //Login interface
    @FXML
    private JFXButton btn_login;
    @FXML
    private JFXTextField tf_user;
    @FXML
    private JFXPasswordField tf_pass;
    @FXML
    private JFXComboBox<String> cb_rol;
    @FXML
    private JFXComboBox<String> gest_usr_cambiar_estado_cb2;
    @FXML
    private JFXComboBox<String> cb_rol2;
    @FXML
    private JFXComboBox<String> cb_rol1;
    @FXML
    private JFXComboBox<String> cb_rol21;
    @FXML
    private JFXComboBox<String> cb_rol_genero;
    @FXML
    private JFXComboBox<String> cb_rol_estado_civil;
    @FXML
    private JFXComboBox<String> cb_rol_plan_asociado;
    @FXML
    private JFXButton btn_reg;
    @FXML
    private JFXTextField tf_nombre;
    @FXML
    private ImageView agregar_user;
    @FXML
    private ImageView realizar_venta;
    @FXML
    private AnchorPane pane_ventas;
    @FXML
    private AnchorPane pane_gestionar_usuarios;
    @FXML
    private AnchorPane pane_generar_reporte;
    @FXML
    private ImageView generar_reporte;
    @FXML
    private ImageView gen_rep_persona_nat;
    @FXML
    private ImageView gen_rep_corporativo;
    @FXML
    private AnchorPane pane_gen_rep_corp;
    @FXML
    private AnchorPane pane_gen_rep_pnat;
    @FXML
    private Label titulo_label;
    @FXML
    private ImageView realizar_pagos;
    @FXML
    private ImageView generar_factura;
    @FXML
    private  ImageView cerrar_sesion;
    @FXML
    private AnchorPane pane_generar_facturas;
    @FXML
    private ImageView gen_fact_colectiva;
    @FXML
    private ImageView gen_fact_individual;
    @FXML
    private Label gen_fact_tipo_cliente_label;
    @FXML
    private Label gen_fact_id_label;
    @FXML
    private Label gen_fact_linea_label;
    @FXML
    private JFXTextField  gen_fact_id_TextField;
    @FXML
    private JFXTextField  gen_fact_linea_TextField;
    @FXML
    private AnchorPane pane_pagar;
    @FXML
    private ImageView pagar_cliente;
    @FXML
    private ImageView pagar_linea;
    @FXML
    AnchorPane pane_pagar_cliente;
    @FXML
    AnchorPane pane_pagar_linea;
    @FXML
    private AnchorPane pane_gest_usr_agregar;
    @FXML
    private ImageView gest_usr_editar;
    @FXML
    private ImageView gest_usr_cambiar_estadp;
    @FXML
    private ImageView gest_usr_añadir;
    @FXML
    private ImageView gest_usr_listar;
    @FXML
    private ImageView gest_usr_agregar_retroceder_Image;
    @FXML
    private JFXButton gest_usr_editar_btn_buscar;
    @FXML
    private JFXButton btn_reg_nuevo;
    @FXML
    private JFXButton btn_reg_antiguo;
    @FXML
    private JFXButton gen_fact_generar_button;
    @FXML
    private AnchorPane pane_edit_campos;
    @FXML
    private AnchorPane pane_gest_usr_editar;
    @FXML
    private ImageView gest_usr_editar_retroceder_Image;
    @FXML
    private AnchorPane pane_gest_usr_cambiar_estado;
    @FXML
    private ImageView gest_usr_cambiar_estado_retroceder_Image;
    @FXML
    private ImageView gest_usr_cambiar_estado;
    @FXML
    private Label estado_label;
    @FXML
    private AnchorPane pane_estado_campos;
    @FXML
    private AnchorPane pane_estado_est;
    @FXML
    private JFXComboBox<String> gest_usr_cambiar_estado_cb;
    @FXML
    private JFXButton gest_usr_estado_btn_buscar;
    @FXML
    private AnchorPane pane_gest_usr_listar;
    @FXML
    private ImageView gest_usr_listar_retroceder_Image;
    @FXML
    private ImageView ventas_cliente_ant_Image;
    @FXML
    private ImageView ventas_cliente_nue_Image;
    @FXML
    private AnchorPane pane_ventas_antiguo;
    @FXML
    private  AnchorPane pane_ventas_nuevo;
    @FXML
    private AnchorPane pane_gen_fact_individual;
    @FXML
    private AnchorPane pane_gen_fact_colectiva;
    @FXML
    private  JFXComboBox gest_usr_editar_id_cb_buscar12;
    //ventas
    @FXML
    private JFXComboBox <String> cb_ventas_ant_tipo_cliente;
    @FXML
    private JFXComboBox <String> cb_ventas_ant_tipo_id;
    @FXML
    private JFXComboBox <String> cb_ventas_ant_plan_asociado;
    @FXML
    private JFXComboBox <String> cb_ventas_nue_tipo_cliente;
    @FXML
    private JFXComboBox <String> cb_ventas_nue_tipo_id;
    @FXML
    private JFXComboBox <String> cb_ventas_nue_plan_asociado;
    @FXML
    private JFXComboBox <String> cb_ventas_nue_genero;
    @FXML
    private JFXComboBox <String> cb_ventas_nue_estado_civil;
    @FXML
    private JFXTextField tf_nombre_identificacion;
    @FXML
    private JFXTextField tf_nombre_identificacion_ant;
    @FXML
    private JFXTextField tf_correo_electronico;
    @FXML
    private JFXTextField tf_nombres;
    @FXML
    private JFXTextField tf_gen_rep_id_pnat;
    @FXML
    private JFXTextField tf_gen_rep_nit_corp;
    @FXML
    private JFXTextField tf_primer_apellido;
    @FXML
    private JFXTextField tf_segundo_apellido;
    @FXML
    private JFXTextField tf_nueva_linea;
    @FXML
    private JFXTextField tf_nueva_linea_ant;
    //generar reportes
    @FXML
    private JFXComboBox <String> cb_gen_rep_tipo_id_pnat;
    //generar facturas
    @FXML
    private JFXComboBox <String> cb_gen_fact_tip_cliente;
    @FXML
    private JFXComboBox <String> cb_gen_fact_tip_id;
    //pagos
    @FXML
    private JFXComboBox <String> pagar_linea_tip_cl;
    @FXML
    private JFXComboBox <String>  pagar_linea_tip_id;
    @FXML
    private JFXComboBox <String> pagar_cliente_tip_cl;
    @FXML
    private JFXComboBox <String> pagar_cliente_tip_id;
    @FXML
    private JFXTextField tf_pagar_identificacion;
    @FXML
    private JFXTextField tf_pagar_numero_linea;
    @FXML
    private JFXTextField tf_pagar_identificacion_cliente;
    //agregar user
    @FXML
    private JFXComboBox <String> cb_add_user_tipo_id;
    @FXML
    private JFXComboBox <String> cb_add_user_rol;
    @FXML
    private JFXComboBox <String> cb_add_user_genero;
    @FXML
    private JFXComboBox <String> add_user_est_civil;
    @FXML
    private JFXTextField tf_gest_usr_agreg_id;
    @FXML
    private JFXTextField tf_gest_usr_agreg_nombre;
    @FXML
    private JFXTextField tf_gest_usr_agreg_primer_apellido;
    @FXML
    private JFXTextField tf_gest_usr_agreg_segundo_apellido;
    @FXML
    private JFXTextField tf_gest_usr_agreg_email;
    //editar user
    @FXML
    private JFXComboBox <String> cb_edit_user_tipo_id;
    @FXML
    private JFXComboBox <String> cb_edit_user_rol;
    @FXML
    private JFXComboBox <String> edit_user_genero;
    @FXML
    private JFXComboBox <String> edit_user_est_civil;
    @FXML
    private JFXTextField tf_gest_usr_editar_numero;
    @FXML
    private JFXTextField tf_gest_usr_cambiar_nombre;
    @FXML
    private JFXTextField tf_gest_usr_editar_primer_apellido;
    @FXML
    private JFXTextField tf_gest_usr_editar_segundo_apellido;
    @FXML
    private JFXTextField tf_gest_usr_editar_correo;
    @FXML
    private JFXButton gest_usr_editar_btn_guardar;
    @FXML
    private JFXButton gest_usr_editar_estado_buscar;
    //cambiar estado
    @FXML
    private JFXComboBox <String> set_est;
    @FXML
    private JFXComboBox <String> set_est_tipo_id;
    @FXML
    private JFXComboBox <String> gest_usr_editar_rol_ComboBox1;
    @FXML
    private JFXTextField tf_gest_usr_editar_estado_numero;
    @FXML
    private JFXButton gest_usr_editar_est_guardar;
    @FXML
    private VBox cerrar_sesion_vbox;
    @FXML
    private Label cerrar_sesion_label;
    @FXML
    private Label salir_label;
    @FXML
    private Label label_agregar_cliente_antiguo_id;
    @FXML
    private Label label_agregar_compra_nuevo;
    @FXML
    private Label pagar_id_label;
    @FXML
    private Label pagar_id_label2;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Platform.runLater(() -> {
            switch (new_user.get_user_rol()) {
                case 1: {
                    realizar_venta.setVisible(false);
                    generar_reporte.setVisible(false);
                    realizar_pagos.setVisible(false);
                    cerrar_sesion_vbox.setVisible(false);
                    pane_generar_facturas.setVisible(true);
                    titulo_label.setText(" Generar facturas");
                    titulo_label.setLayoutX(445);

                    break;
                }

                case 2: {
                    realizar_pagos.setVisible(false);
                    generar_factura.setVisible(false);
                    agregar_user.setVisible(false);
                    pane_ventas.setVisible(true);
                    cerrar_sesion_vbox.setVisible(false);
                    titulo_label.setText("    Agregar venta");
                    titulo_label.setLayoutX(35);

                    break;
                }

                case 3: {
                    realizar_venta.setVisible(false);
                    generar_reporte.setVisible(false);
                    generar_factura.setVisible(false);
                    agregar_user.setVisible(false);
                    cerrar_sesion_vbox.setVisible(false);
                    pane_pagar.setVisible(true);
                    titulo_label.setText("   Realizar pagos");
                    titulo_label.setLayoutX(311);

                    break;
                }
            }

            JFXComboBox<String>[]  tipoCli = new JFXComboBox[]{cb_gen_fact_tip_cliente, cb_ventas_ant_tipo_cliente, cb_ventas_nue_tipo_cliente,pagar_linea_tip_cl,pagar_cliente_tip_cl};
            for(int i=0; i<= (tipoCli.length-1); i++) {
                tipoCli[i].getItems().addAll(tipoCliente);
            }

            JFXComboBox<String>[] tipoId = new JFXComboBox[]{set_est_tipo_id,cb_edit_user_tipo_id,cb_add_user_tipo_id,pagar_cliente_tip_id,pagar_linea_tip_id,cb_gen_fact_tip_id, cb_ventas_ant_tipo_id, cb_ventas_nue_tipo_id,cb_gen_rep_tipo_id_pnat};
            for(int i=0; i<= (tipoId.length-1); i++) {
                tipoId[i].getItems().addAll(tipoDocumento);
            }

            JFXComboBox<String>[] planA = new JFXComboBox[]{cb_ventas_ant_plan_asociado, cb_ventas_nue_plan_asociado};
            for(int i=0; i<= (planA.length-1); i++) {
                planA[i].getItems().addAll(plan_asociado);
            }

            JFXComboBox<String>[] gen = new JFXComboBox[]{cb_add_user_genero,edit_user_genero,cb_ventas_nue_genero};
            for(int i=0; i<= (gen.length-1); i++) {
                gen[i].getItems().addAll(genero);
            }

            JFXComboBox<String>[] estadoCiv = new JFXComboBox[]{edit_user_est_civil,add_user_est_civil,cb_ventas_nue_estado_civil};
            for(int i=0; i<= (estadoCiv.length-1); i++) {
                estadoCiv[i].getItems().addAll(estado_civil);
            }

            JFXComboBox<String>[] roles = new JFXComboBox[]{cb_add_user_rol,cb_edit_user_rol,gest_usr_editar_rol_ComboBox1};
            for(int i=0; i<= (roles.length-1); i++) {
                roles[i].getItems().addAll(rol);
            }

            JFXComboBox<String>[] estados = new JFXComboBox[]{gest_usr_cambiar_estado_cb2, set_est};
            for(int i=0; i<= (estados.length-1); i++) {
                estados[i].getItems().addAll(estado);
            }
        });
    }

    public void set_user_role (int NR) {
        new_user.set_user_rol(NR);
    }

    @FXML
    public void registro(ActionEvent event) throws Exception{
        String nombre = tf_nombre.getText();
    }

    @FXML
    public void cambiar_pestana(MouseEvent event) throws Exception {
        switch (new_user.get_user_rol()) {
            case 1: {
                realizar_venta.setVisible(false);
                generar_reporte.setVisible(false);
                realizar_pagos.setVisible(false);
                cerrar_sesion_vbox.setVisible(false);

                if (event.getSource().equals(generar_factura)) {
                    pane_generar_facturas.setVisible(true);
                    pane_gestionar_usuarios.setVisible(false);
                    titulo_label.setText(" Generar facturas");
                    titulo_label.setLayoutX(445);
                    pane_gen_fact_individual.setVisible(false);
                    pane_gen_fact_colectiva.setVisible(false);
                    pane_gest_usr_agregar.setVisible(false);
                    pane_gest_usr_editar.setVisible(false);
                    pane_gest_usr_cambiar_estado.setVisible(false);
                    pane_gest_usr_listar.setVisible(false);
                    cerrar_sesion_vbox.setVisible(false);
                }

                if (event.getSource().equals(agregar_user)) {
                    pane_generar_facturas.setVisible(false);
                    pane_gestionar_usuarios.setVisible(true);
                    cerrar_sesion_vbox.setVisible(false);
                    titulo_label.setText("  Gestión usuarios");
                    titulo_label.setLayoutX(580);
                }

                break;
            }

            case 2: {
                realizar_pagos.setVisible(false);
                generar_factura.setVisible(false);
                agregar_user.setVisible(false);
                cerrar_sesion_vbox.setVisible(false);

                if (event.getSource().equals(realizar_venta)) {
                    pane_generar_reporte.setVisible(false);
                    pane_ventas.setVisible(true);
                    titulo_label.setText("    Agregar venta");
                    titulo_label.setLayoutX(35);
                    pane_ventas_antiguo.setVisible(false);
                    pane_ventas_nuevo.setVisible(false);
                    cerrar_sesion_vbox.setVisible(false);
                }

                if (event.getSource().equals(generar_reporte)) {
                    pane_generar_reporte.setVisible(true);
                    pane_gestionar_usuarios.setVisible(false);
                    titulo_label.setText(" Generar reportes");
                    titulo_label.setLayoutX(161);
                    pane_gen_rep_pnat.setVisible(false);
                    pane_gen_rep_corp.setVisible(false);
                    cerrar_sesion_vbox.setVisible(false);
                }

                break;
            }

            case 3: {
                realizar_venta.setVisible(false);
                generar_reporte.setVisible(false);
                generar_factura.setVisible(false);
                agregar_user.setVisible(false);
                cerrar_sesion_vbox.setVisible(false);

                if (event.getSource().equals(realizar_pagos)) {
                    pane_pagar.setVisible(true);
                    titulo_label.setText("   Realizar pagos");
                    titulo_label.setLayoutX(311);
                    pane_pagar_linea.setVisible(false);
                    pane_pagar_cliente.setVisible(false);
                    cerrar_sesion_vbox.setVisible(false);
                }

                break;
            }
        }
    }

    @FXML
    public void cerrar_sesion(MouseEvent event) throws Exception {
        cerrar_sesion_vbox.setVisible(true);
        if (event.getSource().equals(cerrar_sesion_label)) {
            try {
                Stage log = (Stage) cerrar_sesion_label.getScene().getWindow();
                log.close();

                Parent root = FXMLLoader.load(getClass().getResource("login.fxml"));
                Stage primaryStage = new Stage();
                primaryStage.setTitle("Log In");

                primaryStage.setScene(new Scene(root));
                primaryStage.show();
            } catch (IOException ex) {
                System.out.println(ex);
            }
        }
    }

    @FXML
    public void log_out(MouseEvent event) {
        if(event.getSource().equals(salir_label)) {
            cerrar_sesion_vbox.setVisible(false);

            int user_answer = JOptionPane.showConfirmDialog(null, "¿Desea cerrar el programa?", "¡Alerta!", JOptionPane.YES_NO_OPTION);
            if (user_answer == 0) {
                System.exit(0);
            }
        }
    }

    @FXML
    public void choose(ActionEvent event) {
        Object source = event.getSource();
        if (cb_ventas_ant_tipo_cliente.equals(source)) {
            choose_user_type(cb_ventas_ant_tipo_cliente, cb_ventas_ant_tipo_id, label_agregar_cliente_antiguo_id);
        } else if (cb_ventas_nue_tipo_cliente.equals(source)) {
            choose_user_type(cb_ventas_nue_tipo_cliente, cb_ventas_nue_tipo_id, label_agregar_compra_nuevo);
        } else if (pagar_linea_tip_cl.equals(source)) {
            choose_user_type(pagar_linea_tip_cl, pagar_linea_tip_id, pagar_id_label);
        } else if (pagar_cliente_tip_cl.equals(source)) {
            choose_user_type(pagar_cliente_tip_cl, pagar_cliente_tip_id, pagar_id_label2);
        } else if (cb_gen_fact_tip_cliente.equals(source)) {
            choose_user_type(cb_gen_fact_tip_cliente, cb_gen_fact_tip_id, gen_fact_id_label);
        }
    }

    @FXML
    public void choose_user_type(JFXComboBox cb__tipo_cliente, JFXComboBox cb_tipo_id, Label nombre) {
        String tipo = (String) cb__tipo_cliente.getSelectionModel().getSelectedItem();
        if(tipo == "Corporativo") {
            cb_tipo_id.setDisable(true);
            cb_tipo_id.setPromptText("NIT");
            nombre.setText("NIT:");
        }
        else {
            cb_tipo_id.setDisable(false);
            cb_tipo_id.setPromptText("");
            nombre.setText("Identificación:");
        }
    }
    
    public void handleGen_registrar_compra(ActionEvent event) {
        try {
            String tipoCliente = cb_ventas_nue_tipo_cliente.getSelectionModel().getSelectedItem();

            String tipoID = Integer.toString(cb_ventas_nue_tipo_id.getSelectionModel().getSelectedIndex() + 1);
            String planAsociado = Integer.toString(cb_ventas_nue_plan_asociado.getSelectionModel().getSelectedIndex() + 1);

            String documentNumber = tf_nombre_identificacion.getText();
            String email = tf_correo_electronico.getText();

            String nombres = tf_nombres.getText();
            String primerApellido = tf_primer_apellido.getText();

            String segundoApellido = tf_segundo_apellido.getText();
            String nuevaLinea = tf_nueva_linea.getText();

            if(tipoCliente.equals("")||tipoID.equals(" ")||planAsociado.equals(" ")||documentNumber.equals(" ")||email.equals("")||nombres.equals("")||primerApellido.equals("")||segundoApellido.equals("")||nuevaLinea.equals("")){
                throw new EmptyFieldException("Debe llenar todos los campos");
            }

            int userID = new_user.get_user_id();
            String name = nombres + " " + primerApellido + " " + segundoApellido;

            if(connection.modify_DB("INSERT INTO customer(id, name, type, email) " +
                    "VALUES(" + documentNumber + ", '" + name + "', '" + tipoCliente + "', '" + email + "');")) {
                if(connection.modify_DB("INSERT INTO lines " +
                        "VALUES("+nuevaLinea+", "+documentNumber+", "+tipoID+", "+planAsociado+", "+userID+", "+true+");")){
                    JOptionPane.showMessageDialog(null,"Compra registrada exitosamente");
                }
                else{
                    boolean deleted = connection.modify_DB("DELETE FROM customer WHERE id = "+documentNumber+" ;");
                    System.out.println(deleted);
                    JOptionPane.showMessageDialog(null,"¡ERROR! No se pudo registrar la compra");
                }
            }
            else{
                JOptionPane.showMessageDialog(null,"¡ERROR! No se pudo registrar correctamente al cliente");
            }

            Object[] line = connection.read_DB("INSERT INTO lines VALUES(" + nuevaLinea + ", " + documentNumber + ", " + tipoID + ", " + planAsociado + ", " + userID + ", " + true + ");");

            tf_nombre_identificacion.setText("");
            tf_correo_electronico.setText("");

            tf_nombres.setText("");
            tf_primer_apellido.setText("");

            tf_segundo_apellido.setText("");
            tf_nueva_linea.setText("");
        }catch(EmptyFieldException e){
            JOptionPane.showMessageDialog(null, e.getMessage());
        }catch (Exception e){
            JOptionPane.showMessageDialog(null, "Debe llenar todos los campos");
        }
    }

    public void handleGen_registrar_compra_ant(ActionEvent event) throws EmptyFieldException {
        try {
            String tipoCliente = cb_ventas_ant_tipo_cliente.getSelectionModel().getSelectedItem();

            String tipoID = Integer.toString(cb_ventas_ant_tipo_id.getSelectionModel().getSelectedIndex() + 1);
            String planAsociado = Integer.toString(cb_ventas_ant_plan_asociado.getSelectionModel().getSelectedIndex() + 1);

            String documentNumber = tf_nombre_identificacion_ant.getText();
            String nuevaLinea = tf_nueva_linea_ant.getText();

            Object[] lineasI = connection.read_DB("SELECT * FROM Customer INNER JOIN Lines on Customer.id = Lines.CustomerID WHERE Customer.id = '" + documentNumber + "';");
            Vector<String[]> lineas = (Vector) lineasI[1];
            String customer_type = lineas.get(0)[3];

            if(tipoCliente.equals("")||tipoID.equals("")||planAsociado.equals("")||documentNumber.equals("")||nuevaLinea.equals("")) {
                throw new EmptyFieldException("Debe llenar todos los campos");
            }

            if(customer_type.equals("Natural") && lineas.size() ==  3) {
                throw new NaturalCustomerException("Un cliente natural no puede tener más de tres lineas");
            }

            int userID = new_user.get_user_id();

            Object[] customer = connection.read_DB("SELECT * FROM customer WHERE id='"+documentNumber+"' AND type='"+tipoCliente+"';");
            if(connection.modify_DB("INSERT INTO lines " +
                    "VALUES("+nuevaLinea+", "+documentNumber+", "+tipoID+", "+planAsociado+", "+userID+", "+true+");")){
                JOptionPane.showMessageDialog(null,"usuario registrado exitosamente");
            }
            else{
                JOptionPane.showMessageDialog(null,"¡ERROR! No se pudo registrar al usuario");
            }

            tf_gest_usr_agreg_id.setText("");
            tf_gest_usr_agreg_primer_apellido.setText("");
            tf_gest_usr_agreg_segundo_apellido.setText("");
            tf_gest_usr_agreg_nombre.setText("");
            tf_gest_usr_agreg_email.setText("");

            if(connection.modify_DB("INSERT INTO lines " +
                    "VALUES("+nuevaLinea+", "+documentNumber+", "+tipoID+", "+planAsociado+", "+userID+", "+true+");")){
                JOptionPane.showMessageDialog(null,"Compra registrada exitosamente");
            }
            else{
                JOptionPane.showMessageDialog(null,"¡ERROR! No se pudo registrar la compra");
            }

            tf_nombre_identificacion_ant.setText("");
            tf_nueva_linea_ant.setText("");
        }catch (EmptyFieldException e){
            JOptionPane.showMessageDialog(null, e.getMessage());
        }catch (NaturalCustomerException e){
            JOptionPane.showMessageDialog(null, e.getMessage());
        }catch (Exception e){
            JOptionPane.showMessageDialog(null, "Debe llenar todos los campos");
        }
    }

    public void handleGen_agregar_usuario(ActionEvent event) {
        try {
            String rol = Integer.toString(cb_add_user_rol.getSelectionModel().getSelectedIndex() + 1);

            String documentNumber = tf_gest_usr_agreg_id.getText();
            String email = tf_gest_usr_agreg_email.getText();

            String nombres = tf_gest_usr_agreg_nombre.getText();
            String primerApellido = tf_gest_usr_agreg_primer_apellido.getText();

            String segundoApellido = tf_gest_usr_agreg_segundo_apellido.getText();

            String name = nombres + " " + primerApellido + " " + segundoApellido;

            String password = "test";

            if(documentNumber.isBlank()||email.isBlank()||nombres.isBlank()||primerApellido.isBlank()||segundoApellido.isBlank()||segundoApellido.isBlank()||password.isBlank())
                throw new EmptyFieldException("Debe llenar todos los campos");

            Object[] user = connection.read_DB("INSERT INTO users VALUES('" + documentNumber + "', '" + name + "', " + rol + ", " + true + ", '" + password + "');");
        }catch (EmptyFieldException e){
            JOptionPane.showMessageDialog(null, e.getMessage());
        }catch (Exception e){
            JOptionPane.showMessageDialog(null, "Debe llenar todos los campos");
        }
    }

    public void handleGen_pagar_linea(ActionEvent event) {
        //TODO: Use documentType and clientType in the query for line
        try {
            String documentNumber = tf_pagar_identificacion.getText();
            String lineNumber = tf_pagar_numero_linea.getText();

            if(documentNumber.isBlank()||lineNumber.isBlank()){
                throw new EmptyFieldException("Debe llenar todos los campos");
            }

            Object[] line = connection.read_DB("SELECT * FROM lines WHERE customerid='" + documentNumber + "';");
            if (line[0] == "Error") {
                JOptionPane.showMessageDialog(null,"No hay lineas asociadas a ese documento");

                tf_pagar_identificacion.setText("");
                tf_pagar_numero_linea.setText("");
                return;
            }

            if(connection.modify_DB("UPDATE bill SET status=" + true + " WHERE linenumber='" + lineNumber + "';")){
                JOptionPane.showMessageDialog(null,"Pago realizado exitosamente");
            }
            else{
                JOptionPane.showMessageDialog(null,"¡ERROR! No se pudo registrar el pago");
            }

            tf_pagar_identificacion.setText("");
            tf_pagar_numero_linea.setText("");
        }catch (EmptyFieldException e){
            JOptionPane.showMessageDialog(null, e.getMessage());
        }catch (Exception e){
            JOptionPane.showMessageDialog(null, "Debe llenar todos los campos");
        }
    }

    public void handleGen_pagar_cliente(ActionEvent event) {
        try {
            String tipoCliente = pagar_cliente_tip_cl.getSelectionModel().getSelectedItem() + 1;
            String documentNumber = tf_pagar_identificacion_cliente.getText();

            if(documentNumber.isBlank()){
                throw new EmptyFieldException("Debe llenar todos los campos");
            }

            Object[] lineNumber = connection.read_DB("SELECT number FROM (SELECT * FROM customer, lines " +
                    "WHERE customer.id=lines.customerid) AS result WHERE id='" + documentNumber + "' AND type='" + tipoCliente + "';");
            if (lineNumber[0].equals("Error")) {
                return;
            }

            boolean updateBillStatus = connection.modify_DB("UPDATE bill SET status="+true+" WHERE linenumber='"+lineNumber+"';");
            if(updateBillStatus) {
                JOptionPane.showMessageDialog(null, "Pago existoso");

                return;
            }

            JOptionPane.showMessageDialog(null, "No se pudo realizar el pago");
            tf_pagar_identificacion_cliente.setText("");
        }catch (EmptyFieldException e){
            JOptionPane.showMessageDialog(null, e.getMessage());
        }catch (Exception e){
            JOptionPane.showMessageDialog(null, "Debe llenar todos los campos");
        }
    }

    @FXML
    public void user_gest(MouseEvent event) {
        if (event.getSource().equals(gest_usr_añadir)) {
            pane_gestionar_usuarios.setVisible(false);
            pane_generar_facturas.setVisible(false);
            pane_gest_usr_agregar.setVisible(true);
            pane_gest_usr_editar.setVisible(false);
            pane_gest_usr_cambiar_estado.setVisible(false);
            pane_gest_usr_listar.setVisible(false);
            cerrar_sesion_vbox.setVisible(false);
            titulo_label.setText(" Añadir usuarios");
            titulo_label.setLayoutX(580);
        }

        if (event.getSource().equals(gest_usr_agregar_retroceder_Image) || event.getSource().equals(gest_usr_editar_retroceder_Image) || event.getSource().equals(gest_usr_cambiar_estado_retroceder_Image) || event.getSource().equals(gest_usr_listar_retroceder_Image)) {
            pane_gestionar_usuarios.setVisible(true);
            pane_generar_facturas.setVisible(false);
            pane_gest_usr_agregar.setVisible(false);
            pane_gest_usr_editar.setVisible(false);
            pane_gest_usr_cambiar_estado.setVisible(false);
            pane_gest_usr_listar.setVisible(false);
            cerrar_sesion_vbox.setVisible(false);
            titulo_label.setText("  Gestión usuarios");
            titulo_label.setLayoutX(580);
        }

        if (event.getSource().equals(gest_usr_editar)) {
            pane_gestionar_usuarios.setVisible(false);
            pane_generar_facturas.setVisible(false);
            pane_gest_usr_agregar.setVisible(false);
            pane_gest_usr_editar.setVisible(true);
            pane_gest_usr_cambiar_estado.setVisible(false);
            pane_gest_usr_listar.setVisible(false);
            cerrar_sesion_vbox.setVisible(false);
            titulo_label.setText(" Editar usuarios");
            titulo_label.setLayoutX(580);
            pane_edit_campos.setVisible(false);
        }

        if (event.getSource().equals(gest_usr_cambiar_estado)) {
            pane_gestionar_usuarios.setVisible(false);
            pane_generar_facturas.setVisible(false);
            pane_gest_usr_agregar.setVisible(false);
            pane_gest_usr_editar.setVisible(false);
            pane_gest_usr_cambiar_estado.setVisible(true);
            pane_gest_usr_listar.setVisible(false);
            cerrar_sesion_vbox.setVisible(false);
            titulo_label.setText(" Cambiar estado");
            titulo_label.setLayoutX(580);
            pane_estado_campos.setVisible(false);
            pane_estado_est.setVisible(false);
        }

        if (event.getSource().equals(gest_usr_listar)) {
            pane_gestionar_usuarios.setVisible(false);
            pane_generar_facturas.setVisible(false);
            pane_gest_usr_agregar.setVisible(false);
            pane_gest_usr_editar.setVisible(false);
            pane_gest_usr_cambiar_estado.setVisible(false);
            pane_gest_usr_listar.setVisible(true);
            cerrar_sesion_vbox.setVisible(false);
            titulo_label.setText(" Listar usuarios");
            titulo_label.setLayoutX(580);
        }
    }

    public void selec_tipo_cliente(MouseEvent event) {
        if (event.getSource().equals(gen_rep_corporativo)) {
            pane_gen_rep_pnat.setVisible(false);
            pane_gen_rep_corp.setVisible(true);
            cerrar_sesion_vbox.setVisible(false);
        }

        if (event.getSource().equals(gen_rep_persona_nat)) {
            pane_gen_rep_pnat.setVisible(true);
            pane_gen_rep_corp.setVisible(false);
            cerrar_sesion_vbox.setVisible(false);
        }
    }

    public void selec_tipo_factura(MouseEvent event) {
        if(event.getSource().equals(gen_fact_colectiva)) {
            pane_gen_fact_individual.setVisible(false);
            pane_gen_fact_colectiva.setVisible(true);
            cerrar_sesion_vbox.setVisible(false);
        }

        if(event.getSource().equals(gen_fact_individual)) {
            pane_gen_fact_individual.setVisible(true);
            pane_gen_fact_colectiva.setVisible(false);
            cerrar_sesion_vbox.setVisible(false);
        }
    }

    public void select_tipo_pago(MouseEvent event) {
        if(event.getSource().equals(pagar_cliente)) {
            pane_pagar_linea.setVisible(false);
            pane_pagar_cliente.setVisible(true);
            cerrar_sesion_vbox.setVisible(false);
        }

        if(event.getSource().equals(pagar_linea)) {
            pane_pagar_linea.setVisible(true);
            pane_pagar_cliente.setVisible(false);
            cerrar_sesion_vbox.setVisible(false);
        }
    }

    public void buscar_edit(ActionEvent event){
        try {
            if (event.getSource().equals(gest_usr_editar_btn_buscar)) {
                cerrar_sesion_vbox.setVisible(false);
                String documentNumber = tf_gest_usr_editar_numero.getText();

                if (documentNumber.isBlank()) {
                    throw new EmptyFieldException("Debe llenar todos los campos");
                }

                Object[] user = connection.read_DB("SELECT * FROM users WHERE id='" + documentNumber + "';");
                if (user[0] == "Error") {
                    JOptionPane.showMessageDialog(null, "Usuario no registrado");
                    tf_gest_usr_cambiar_nombre.setText("");
                    tf_gest_usr_editar_primer_apellido.setText("");
                    tf_gest_usr_editar_segundo_apellido.setText("");
                    tf_gest_usr_editar_correo.setText("");

                    return;
                }
                pane_edit_campos.setVisible(true);

                if (event.getSource().equals(gest_usr_editar_btn_guardar)) {
                    cerrar_sesion_vbox.setVisible(false);
                    String rol = Integer.toString(cb_edit_user_rol.getSelectionModel().getSelectedIndex() + 1);

                    String nombres = tf_gest_usr_cambiar_nombre.getText();
                    String primerApellido = tf_gest_usr_editar_primer_apellido.getText();

                    String segundoApellido = tf_gest_usr_editar_segundo_apellido.getText();
                    String estadoCivil = edit_user_est_civil.getSelectionModel().getSelectedItem();
                    String genero = edit_user_genero.getSelectionModel().getSelectedItem();

                    String email = tf_gest_usr_editar_correo.getText();

                    if (rol.isBlank() || nombres.isBlank() || primerApellido.isBlank() || segundoApellido.isBlank() || email.isBlank())
                        throw new EmptyFieldException("Debe llenar todos los campos");

                    String name = nombres + " " + primerApellido + " " + segundoApellido;
                    if (connection.modify_DB("UPDATE user SET rolid=" + rol + ", name='" + name + "' " + ", email='"+ email + "' " +
                            ", civil_stade='" + estadoCivil +"' " + ", gender='" + genero +"' " +"WHERE id='" + documentNumber + "';")) {
                        JOptionPane.showMessageDialog(null, "Actualizacion realizada correctamente");
                    }
                    else {
                        JOptionPane.showMessageDialog(null, "¡ERROR! No se pudo realizar la actualizacion");
                    }

                    tf_gest_usr_cambiar_nombre.setText("");
                    tf_gest_usr_cambiar_nombre.setText("");
                    tf_gest_usr_editar_primer_apellido.setText("");
                    tf_gest_usr_editar_segundo_apellido.setText("");
                    tf_gest_usr_editar_correo.setText("");
                }
            }
        }catch (EmptyFieldException e){
            JOptionPane.showMessageDialog(null, e.getMessage());
        }catch (Exception e){
            JOptionPane.showMessageDialog(null, "Debe llenar todos los campos");
        }
    }

    public void buscar_estado(ActionEvent event){
        String documentNumber = tf_gest_usr_editar_estado_numero.getText();
        try {
            if (documentNumber.isBlank()) {
                throw new EmptyFieldException("Debe llenar todos los campos");
            }

            Object[] user = connection.read_DB("SELECT * FROM users WHERE id='" + documentNumber + "';");
            if (user[0] == "Error") {
                JOptionPane.showMessageDialog(null, "Usuario no registrado");
                tf_gest_usr_editar_estado_numero.setText("");

                return;
            }

            pane_estado_campos.setVisible(true);
            pane_estado_est.setVisible(true);

        }catch (EmptyFieldException e){
            JOptionPane.showMessageDialog(null, e.getMessage());
        }catch (Exception e){
            JOptionPane.showMessageDialog(null, "Debe llenar todos los campos");
        }
    }

    public void modificar_estado(ActionEvent actionEvent) {
        try {
            String documentNumber = tf_gest_usr_editar_estado_numero.getText();
            String estado = gest_usr_cambiar_estado_cb2.getSelectionModel().getSelectedItem();
            System.out.println(estado);

            if (connection.modify_DB("UPDATE user SET status='" + estado + "' WHERE id='" + documentNumber + "';")) {
                JOptionPane.showMessageDialog(null, "Actualizacion realizada correctamente");
            }
            else {
                JOptionPane.showMessageDialog(null, "¡ERROR! No se pudo realizar la actualizacion");
            }
        } catch (Exception e) {

        }
    }

    public void tipo_venta(MouseEvent event) {
        if(event.getSource().equals(ventas_cliente_ant_Image)) {
            pane_ventas_antiguo.setVisible(true);
            pane_ventas_nuevo.setVisible(false);
            cerrar_sesion_vbox.setVisible(false);
        }

        if(event.getSource().equals(ventas_cliente_nue_Image)) {
            pane_ventas_antiguo.setVisible(false);
            pane_ventas_nuevo.setVisible(true);
            cerrar_sesion_vbox.setVisible(false);
        }
    }

    public void handleGen_reporte_cliente(ActionEvent actionEvent){
        try {
            String tipoID = Integer.toString(cb_gen_rep_tipo_id_pnat.getSelectionModel().getSelectedIndex() + 1);
            String documentNumber = tf_gen_rep_id_pnat.getText();

            if(documentNumber.isBlank()){
                throw new EmptyFieldException("Debe llenar todos los campos");
            }

            Object[] customer = connection.read_DB("SELECT name, email, city FROM customer WHERE id='" + documentNumber + "' and typeid='"+ tipoID +"'");
            if(customer[0] == "Error") {
                tf_gen_rep_id_pnat.setText("");
                JOptionPane.showMessageDialog(null, "Usuario no registrado");

                return;
            }

            Object[] user = connection.read_DB("SELECT number, planid FROM lines WHERE customerid='" + documentNumber + "';");
            if(user[0] == "Error") {
                JOptionPane.showMessageDialog(null, "Usuario no tiene lineas asociadas");
                tf_gen_rep_id_pnat.setText("");

                return;
            }

            Vector<String[]> userResult = (Vector<String[]>) user[1];
            for (int i = 0; i < userResult.size(); i++) {
                String number = userResult.get(i)[0];
                int plan = Integer.parseInt(userResult.get(i)[1]);

                Object[]  bill = connection.read_DB("SELECT * FROM bill WHERE linenumber='" + number + "';");
                if(bill[0] == "Error") {
                    JOptionPane.showMessageDialog(null, "No hay facturas asociadas a ese numero");
                    tf_gen_rep_id_pnat.setText("");

                    return;
                }

                Object[] priceQuery = connection.read_DB("SELECT cost,minutes,dataplan,messages,data_wpp,minutes_wpp,data_fb,data_waze,minutes_international,data_shared FROM Plan,Lines WHERE planID=id AND number= '"+number+"' ;");
                if(priceQuery[0] == "Error") {
                    JOptionPane.showMessageDialog(null, "Error al cargar informacion del plan");
                    tf_gen_rep_id_pnat.setText("");

                    return;
                }
                Vector<String[]> priceVector = (Vector<String[]>) priceQuery[1];
                String[] price = priceVector.get(0);

                Vector<String[]> billResult = (Vector<String[]>) bill[1];
                Vector<String[]> customerInfo = (Vector<String[]>) customer[1];

                CreateReport report = new CreateReport();
                report.writeReport(billResult.get(i), documentNumber, customerInfo.get(0), number, months[Integer.parseInt(billResult.get(i)[14].substring(5, 7)) - 1], plan, price);
            }

            JOptionPane.showMessageDialog(null, "Reporte generado");
            tf_gen_rep_id_pnat.setText("");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void handleGen_reporte_empresa(ActionEvent actionEvent) {
        try {
            String nit = tf_gen_rep_nit_corp.getText();

            if(nit.isBlank()){
                throw new EmptyFieldException("Nit vacio");
            }

            Object[] customer = connection.read_DB("SELECT name, email, city FROM customer WHERE id='" + nit + "' and typeid=2;");
            if (customer[0] == "Error") {
                JOptionPane.showMessageDialog(null, "Empesa no registrada");
                tf_gen_rep_nit_corp.setText("");

                return;
            }

            Object[] lines = connection.read_DB("SELECT number, planid, status FROM lines WHERE customerid='" + nit + "';");
            if (lines[0] == "Error") {
                JOptionPane.showMessageDialog(null, "Empesa no tiene lineas asociadas");
                tf_gen_rep_nit_corp.setText("");

                return;
            }

            Vector<String[]> customerInfo = (Vector<String[]>) customer[1];
            Vector<String[]> linesInfo = (Vector<String[]>) lines[1];

            for (int i = 0; i < linesInfo.size(); i++) {
                String number = linesInfo.get(i)[0];

                int index = linesInfo.get(i).length - 1;

                Object[] bill = connection.read_DB("SELECT price, status FROM bill WHERE linenumber='" + number + "';");
                if (bill[0] == "Error") {
                    linesInfo.get(i)[index] += "/ /";
                }
                else {
                    Vector<String[]> queryResult = (Vector<String[]>) bill[1];
                    linesInfo.get(i)[index] += "/" + queryResult.get(0)[0] + "/" + queryResult.get(0)[1];
                }
            }

            reportEmpresa report = new reportEmpresa();
            report.writeReport(nit, customerInfo.get(0), linesInfo);

            JOptionPane.showMessageDialog(null, "Reporte generado");
            tf_gen_rep_nit_corp.setText("");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void handleGen_fact_generar_button(ActionEvent actionEvent){
        JOptionPane.showMessageDialog(null, "Generando factura, por favor espere");
      
        try {
            /*Booleano para representar si el cliente quiere factura en el correo o a la dirección
            Se supone que se debe sacar de la bdd pero aún no está. True si quiere factura electrónica, falso si no*/

            String number = gen_fact_linea_TextField.getText();
            String tipoCliente = cb_gen_fact_tip_cliente.getSelectionModel().getSelectedItem();
            int document = Integer.parseInt(gen_fact_id_TextField.getText());
            String tipo_id= cb_gen_fact_tip_id.getSelectionModel().getSelectedItem();

            Object[] priceO = connection.read_DB("SELECT cost,minutes,dataplan,messages,data_wpp,minutes_wpp,data_fb,data_waze,minutes_international,data_shared FROM Plan,Lines WHERE planID=id AND number= '"+number+"' ;");
            Vector<String[]> priceV = (Vector<String[]>) priceO[1];
            String[] price = priceV.get(0);

            Object[] info = connection.read_DB("SELECT * FROM Bill, Lines, Customer WHERE number=linenumber AND number= '" + number + "' AND customerid=Customer.id AND customerid= '" + document + "' AND EXTRACT(YEAR FROM date_pdf) = " + cal.get(Calendar.YEAR) + " AND EXTRACT(MONTH FROM date_pdf) = " + (cal.get(Calendar.MONTH) + 1) + " AND type = '" + tipoCliente + "' ;");
            Vector<String[]>result = (Vector<String[]>) info[1];

            Boolean electronic_bill = false;
            if (result.get(0)[25].equals("t")) {
                electronic_bill = true;
            }

            if(gen_fact_id_TextField.getText().isBlank() || gen_fact_linea_TextField.getText().isBlank()){
                throw new EmptyFieldException("Debe llenar todos los campos");
            }

            String actualDate = cal.get(Calendar.YEAR) + "/" + months[cal.get(Calendar.MONTH)];
            String cutDate = cal.get(Calendar.YEAR) + "/" + months[cal.get(Calendar.MONTH) + 1] + "/05";

            CreateBill bill = new CreateBill();
            String path_file;
            path_file = bill.WriteBill(result.get(0),actualDate, cutDate, months[Integer.parseInt(result.get(0)[14].substring(5, 7)) - 1], electronic_bill, price);

            //Condicional para arrojar un mensaje de como fue enviada la factura del cliente. En este paso, se envia por mail
            if (electronic_bill) {
                Object[] customer = connection.read_DB("SELECT email FROM Customer WHERE id = '"+document+"';");

                Vector<String[]> resultado = (Vector<String[]>) customer[1];
                String email = resultado.get(0)[0];

                String name_bill = path_file+"/bill"+number+".pdf";
                send_bill_by_email(email,name_bill);
            }
            else {
                JOptionPane.showMessageDialog(null, "Factura generada. Lista para imprimir");
            }
        }catch (EmptyFieldException e){
            JOptionPane.showMessageDialog(null, "Debe llenar todos los campos");
        }catch (Exception e){
            JOptionPane.showMessageDialog(null, "No hay registros ");
        }
    }

    public void handleGen_fact_generar_colect_button(ActionEvent actionEvent) throws IOException, MessagingException {
        JOptionPane.showMessageDialog(null, "Generando facturas, por favor espere");

        try {
            Object[] infoHeaderBill = connection.read_DB("SELECT * FROM Bill, Lines, Customer WHERE number=linenumber AND customerid=Customer.id AND EXTRACT(YEAR FROM date_pdf) = " + cal.get(Calendar.YEAR) + " AND EXTRACT(MONTH FROM date_pdf) = " + (cal.get(Calendar.MONTH) + 1) + ";");
            Vector<String[]> result = (Vector) infoHeaderBill[1];

            String actualDate = cal.get(Calendar.YEAR) + "/" + months[cal.get(Calendar.MONTH)];
            String cutDate = cal.get(Calendar.YEAR) + "/" + months[cal.get(Calendar.MONTH) + 1] + "/05";
            String path_name;

            for (int i = 0; i < result.size(); i++) {
                String number = result.get(i)[13];

                Object[] priceO = connection.read_DB("SELECT cost,minutes,dataplan,messages,data_wpp,minutes_wpp,data_fb,data_waze,minutes_international,data_shared FROM Plan,Lines WHERE planID=id AND number= '"+number+"' ;");
                Vector<String[]> priceV = (Vector<String[]>) priceO[1];
                String[] price = priceV.get(0);

                Boolean electronic_bill = false;

                if (result.get(i)[25].equals("t")) {
                    electronic_bill = true;
                }

                System.out.print(electronic_bill+ "---");

                CreateBill bill = new CreateBill();
                path_name = bill.WriteBill(result.get(i),actualDate, cutDate, months[Integer.parseInt(result.get(0)[14].substring(5, 7)) - 1], electronic_bill,  price);
                if (electronic_bill) {
                    Object[] customer = connection.read_DB("SELECT email FROM lines INNER JOIN customer ON lines.customerID = customer.id WHERE lines.number = '"+number+"';");
                    Vector<String[]> resultado = (Vector<String[]>) customer[1];

                    String email = resultado.get(0)[0];
                    send_bill_by_email(email,path_name+"/bill"+number+".pdf");
                }
                else {
                    JOptionPane.showMessageDialog(null, "Factura generada. Lista para imprimir");
                }
            }
        }catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        JOptionPane.showMessageDialog(null, "Facturas generadas");
    }

    public void set_user_id (int id) {
        new_user.set_user_id(id);
    }

    public void send_bill_by_email (String customer_email,String customer_bill) throws MessagingException, IOException {
        Properties propiedad = new Properties();

        propiedad.setProperty("mail.smtp.host", "smtp.gmail.com");
        propiedad.setProperty("mail.smtp.starttls.enable", "true");
        propiedad.setProperty("mail.smtp.port", "587");
        propiedad.setProperty("mail.smtp.auth", "true");

        Session sesion = Session.getDefaultInstance(propiedad);

        String correoEnvia = "telefoniaraja@gmail.com";
        String contrasena = "desarrollo1";
        String destinatario = customer_email;
        String asunto = "Su factura mensual de telefonía RAJA";
        String texto = "Cordial saludo, desde la telefonía RAJA S.A. adjuntamos su factura de este mes";

        MimeMessage mail = new MimeMessage(sesion);

        //PDF
        //Parte del mensaje en texto
        MimeBodyPart mimeBodyPart = new MimeBodyPart();
        mimeBodyPart.setText(texto);
        //Agregar el PDF
        MimeBodyPart mimeBodyPartAdjunto = new MimeBodyPart();
        mimeBodyPartAdjunto.attachFile("/home/angelica/IdeaProjects/Desarrollo-I/"+customer_bill);

        //agregar el texto y PDF al multipart, para mandar el email
        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(mimeBodyPart);
        multipart.addBodyPart(mimeBodyPartAdjunto);

        try {
            mail.setFrom(new InternetAddress (correoEnvia));
            mail.addRecipient(Message.RecipientType.TO, new InternetAddress(destinatario));
            mail.setSubject(asunto);
            mail.setContent(multipart);

            Transport transporte = sesion.getTransport("smtp");
            transporte.connect(correoEnvia,contrasena);
            transporte.sendMessage(mail, mail.getRecipients(Message.RecipientType.TO));
            transporte.close();

            JOptionPane.showMessageDialog(null, "Factura generada. Fue enviada al email del cliente");
        } catch (AddressException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MessagingException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}