import DB_Connection.DBConnection;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import javax.swing.*;
import java.awt.*;
import java.lang.reflect.Array;
import java.net.URL;
import java.util.*;
import java.util.List;

public class Controller implements Initializable {

    //Class Constructor
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
    ObservableList<String> estado = FXCollections.observableArrayList( "I", "II");

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
    private ImageView salir;
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
    private JFXButton gest_usr_editar_est_guardar;//ESTE BOTON NO EXISTE EN LA INTERFAZ

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Platform.runLater(() -> {
            if (new_user.get_user_rol() == 2) {
                realizar_pagos.setVisible(false);
                generar_factura.setVisible(false);
                agregar_user.setVisible(false);
                pane_ventas.setVisible(true);
                titulo_label.setText("  Agregar cliente");
                titulo_label.setLayoutX(46);

            }
            else if (new_user.get_user_rol() == 3) {
                realizar_venta.setVisible(false);
                generar_reporte.setVisible(false);
                generar_factura.setVisible(false);
                agregar_user.setVisible(false);
                pane_pagar.setVisible(true);
                titulo_label.setText("   Realizar pagos");
                titulo_label.setLayoutX(311);
            }
            else if(new_user.get_user_rol() == 1){
                realizar_venta.setVisible(false);
                generar_reporte.setVisible(false);
                realizar_pagos.setVisible(false);
                pane_generar_facturas.setVisible(true);
                titulo_label.setText(" Generar facturas");
                titulo_label.setLayoutX(445);
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

            set_est.getItems().addAll(estado);
        });
    }

    public void set_user_role (int NR)
    {
        new_user.set_user_rol(NR);
    }

    @FXML
    public void registro(ActionEvent event) throws Exception{
        String nombre = tf_nombre.getText();
    }

    @FXML
    public void cambiar_pestana(MouseEvent event) throws Exception {
        if (new_user.get_user_rol() == 2) {
            realizar_pagos.setVisible(false);
            generar_factura.setVisible(false);
            agregar_user.setVisible(false);

            if (event.getSource().equals(realizar_venta)) {
                pane_generar_reporte.setVisible(false);
                pane_ventas.setVisible(true);
                titulo_label.setText("  Agregar cliente");
                titulo_label.setLayoutX(46);
                pane_ventas_antiguo.setVisible(false);
                pane_ventas_nuevo.setVisible(false);
            }

            if (event.getSource().equals(generar_reporte)) {
                pane_generar_reporte.setVisible(true);
                pane_gestionar_usuarios.setVisible(false);
                titulo_label.setText(" Generar reportes");
                titulo_label.setLayoutX(173);
                pane_gen_rep_pnat.setVisible(false);
                pane_gen_rep_corp.setVisible(false);
            }
        }
        else if (new_user.get_user_rol() == 3) {
            realizar_venta.setVisible(false);
            generar_reporte.setVisible(false);
            generar_factura.setVisible(false);
            agregar_user.setVisible(false);

            if (event.getSource().equals(realizar_pagos)) {
                pane_pagar.setVisible(true);
                titulo_label.setText("   Realizar pagos");
                titulo_label.setLayoutX(311);
                pane_pagar_linea.setVisible(false);
                pane_pagar_cliente.setVisible(false);
            }
        }
        else if(new_user.get_user_rol() == 1){
            realizar_venta.setVisible(false);
            generar_reporte.setVisible(false);
            realizar_pagos.setVisible(false);

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
            }

            if (event.getSource().equals(agregar_user)) {
                pane_generar_facturas.setVisible(false);
                pane_gestionar_usuarios.setVisible(true);
                titulo_label.setText("Gestión usuarios");
                titulo_label.setLayoutX(580);
            }
        }
    }

    @FXML
    public void log_out(MouseEvent event) {
        if(event.getSource().equals(salir)) {
            int user_answer = JOptionPane.showConfirmDialog(null, "¿Seguro desea cerrar la sesion?", "¡Alerta!", JOptionPane.YES_NO_OPTION);
            if (user_answer == 0) {
                System.exit(0);
            }
        }
    }

    public void handleGen_registrar_compra(ActionEvent event) {
        String tipoCliente = cb_ventas_nue_tipo_cliente.getSelectionModel().getSelectedItem();

        String tipoID = Integer.toString(cb_ventas_nue_tipo_id.getSelectionModel().getSelectedIndex()+1);
        String planAsociado = Integer.toString(cb_ventas_nue_plan_asociado.getSelectionModel().getSelectedIndex()+1);

        String documentNumber = tf_nombre_identificacion.getText();
        String email = tf_correo_electronico.getText();

        String nombres = tf_nombres.getText();
        String primerApellido = tf_primer_apellido.getText();

        String segundoApellido = tf_segundo_apellido.getText();
        String nuevaLinea = tf_nueva_linea.getText();

        int userID = new_user.get_user_id();
        String name = nombres + " " + primerApellido + " " + segundoApellido;

        Object[] customer = connection.read_DB("INSERT INTO customer(id, name, type, email) VALUES("+documentNumber+", '"+name+"', '"+tipoCliente+"', '"+email+"');");

        Object[] line = connection.read_DB("INSERT INTO lines VALUES("+nuevaLinea+", "+documentNumber+", "+tipoID+", "+planAsociado+", "+userID+", "+true+");");

        tf_nombre_identificacion.setText("");
        tf_correo_electronico.setText("");

        tf_nombres.setText("");
        tf_primer_apellido.setText("");

        tf_segundo_apellido.setText("");
        tf_nueva_linea.setText("");
    }

    public void handleGen_registrar_compra_ant(ActionEvent event) {
        String tipoCliente = cb_ventas_ant_tipo_cliente.getSelectionModel().getSelectedItem();

        String tipoID = Integer.toString(cb_ventas_ant_tipo_id.getSelectionModel().getSelectedIndex()+1);
        String planAsociado = Integer.toString(cb_ventas_ant_plan_asociado.getSelectionModel().getSelectedIndex()+1);

        String documentNumber = tf_nombre_identificacion_ant.getText();
        String nuevaLinea = tf_nueva_linea_ant.getText();

        int userID = new_user.get_user_id();

        Object[] customer = connection.read_DB("SELECT * FROM customer WHERE id='"+documentNumber+"' AND type='"+tipoCliente+"';");

        Object[] line = connection.read_DB("INSERT INTO lines VALUES("+nuevaLinea+", "+documentNumber+", "+tipoID+", "+planAsociado+", "+userID+", "+true+");");

        tf_nombre_identificacion_ant.setText("");
        tf_nueva_linea_ant.setText("");
    }

    public void handleGen_agregar_usuario(ActionEvent event) {
        String rol = Integer.toString(cb_add_user_rol.getSelectionModel().getSelectedIndex() + 1);

        String documentNumber = tf_gest_usr_agreg_id.getText();
        String email = tf_gest_usr_agreg_email.getText();

        String nombres = tf_gest_usr_agreg_nombre.getText();
        String primerApellido = tf_gest_usr_agreg_primer_apellido.getText();

        String segundoApellido = tf_gest_usr_agreg_segundo_apellido.getText();

        String name = nombres + " " + primerApellido + " " + segundoApellido;

        String password = "test";

        Object[] user = connection.read_DB("INSERT INTO users VALUES('" + documentNumber + "', '" + name + "', " + rol + ", " + true + ", '" + password + "');");

        tf_gest_usr_agreg_id.setText("");
        tf_gest_usr_agreg_primer_apellido.setText("");
        tf_gest_usr_agreg_segundo_apellido.setText("");
        tf_gest_usr_agreg_nombre.setText("");
        tf_gest_usr_agreg_email.setText("");
    }

    public void handleGen_pagar_linea(ActionEvent event) {
        //TODO: Use documentType and clientType in the query for line
        String documentNumber = tf_pagar_identificacion.getText();
        String lineNumber = tf_pagar_numero_linea.getText();

        Object[] line = connection.read_DB("SELECT * FROM lines WHERE customerid='"+documentNumber+"';");
        if(line.length != 2) {
            return;
        }

        Object[] bill = connection.read_DB("UPDATE bill SET status="+true+" WHERE linenumber='"+lineNumber+"';");

        tf_pagar_identificacion.setText("");
        tf_pagar_numero_linea.setText("");
    }

    public void handleGen_pagar_cliente(ActionEvent event) {
        String tipoCliente = pagar_cliente_tip_cl.getSelectionModel().getSelectedItem();
        String documentNumber = tf_pagar_identificacion_cliente.getText();

        Object[] lineNumber = connection.read_DB("SELECT number FROM (SELECT * FROM customer, lines WHERE customer.id=lines.customerid) AS result WHERE id='"+documentNumber+"' AND type='"+tipoCliente+"';");
        if(lineNumber[0] == "Error") {
            return;
        }

        //TODO: lineNumber[1] is needed to update the state in bill table but the read_DB function is returning an object and
        // i dont now how to parse it

        //Object[] bill = connection.read_DB("UPDATE bill SET status="+true+" WHERE linenumber='"+lineNumber+"';");

        tf_pagar_identificacion_cliente.setText("");
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
            titulo_label.setText("Gestión usuarios");
            titulo_label.setLayoutX(580);
        }

        if (event.getSource().equals(gest_usr_editar)) {
            pane_gestionar_usuarios.setVisible(false);
            pane_generar_facturas.setVisible(false);
            pane_gest_usr_agregar.setVisible(false);
            pane_gest_usr_editar.setVisible(true);
            pane_gest_usr_cambiar_estado.setVisible(false);
            pane_gest_usr_listar.setVisible(false);
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
            titulo_label.setText(" Listar usuarios");
            titulo_label.setLayoutX(580);
        }
    }

    public void selec_tipo_cliente(MouseEvent event) {
        if (event.getSource().equals(gen_rep_corporativo)) {
            pane_gen_rep_pnat.setVisible(false);
            pane_gen_rep_corp.setVisible(true);
        }

        if (event.getSource().equals(gen_rep_persona_nat)) {
            pane_gen_rep_pnat.setVisible(true);
            pane_gen_rep_corp.setVisible(false);
        }
    }

    public void selec_tipo_factura(MouseEvent event) {
        if(event.getSource().equals(gen_fact_colectiva)) {
            pane_gen_fact_individual.setVisible(false);
            pane_gen_fact_colectiva.setVisible(true);
        }

        if(event.getSource().equals(gen_fact_individual)) {
            pane_gen_fact_individual.setVisible(true);
            pane_gen_fact_colectiva.setVisible(false);
        }
    }

    public void select_tipo_pago(MouseEvent event) {
        if(event.getSource().equals(pagar_cliente)) {
            pane_pagar_linea.setVisible(false);
            pane_pagar_cliente.setVisible(true);
        }

        if(event.getSource().equals(pagar_linea)) {
            pane_pagar_linea.setVisible(true);
            pane_pagar_cliente.setVisible(false);
        }
    }

    public void buscar_edit(ActionEvent event){
        if(event.getSource().equals(gest_usr_editar_btn_buscar)) {
            String documentNumber = tf_gest_usr_editar_numero.getText();

            Object[] user = connection.read_DB("SELECT * FROM users WHERE id='"+documentNumber+"';");
            pane_edit_campos.setVisible(true);

            if(event.getSource().equals(gest_usr_editar_btn_guardar)) {
                String rol = Integer.toString(cb_edit_user_rol.getSelectionModel().getSelectedIndex() + 1);

                String nombres = tf_gest_usr_cambiar_nombre.getText();
                String primerApellido = tf_gest_usr_editar_primer_apellido.getText();

                String segundoApellido = tf_gest_usr_editar_segundo_apellido.getText();
                String email = tf_gest_usr_editar_correo.getText();

                String name = nombres + " " + primerApellido + " " + segundoApellido;
                user = connection.read_DB("UPDATE users SET rolid=" + rol + ", name='" + name + "' WHERE id='"+documentNumber+"';");

                tf_gest_usr_cambiar_nombre.setText("");
                tf_gest_usr_editar_primer_apellido.setText("");
                tf_gest_usr_editar_segundo_apellido.setText("");
                tf_gest_usr_editar_correo.setText("");
            }
        }
    }

    public void buscar_estado(ActionEvent event){
        if(event.getSource().equals(gest_usr_estado_btn_buscar)) {
            String documentNumber = tf_gest_usr_editar_estado_numero.getText();

            Object[] user = connection.read_DB("SELECT * FROM users WHERE id='"+documentNumber+"';");

            pane_estado_campos.setVisible(true);
            pane_estado_est.setVisible(true);

            if(event.getSource().equals("nameButton")) {
                String rol = Integer.toString(gest_usr_editar_rol_ComboBox1.getSelectionModel().getSelectedIndex() + 1);
                user = connection.read_DB("UPDATE user SET rolid='"+rol+"' WHERE id='"+documentNumber+"';");
            }
        }
    }

    public void tipo_venta(MouseEvent event) {
        if(event.getSource().equals(ventas_cliente_ant_Image)) {
            pane_ventas_antiguo.setVisible(true);
            pane_ventas_nuevo.setVisible(false);
        }

        if(event.getSource().equals(ventas_cliente_nue_Image)) {
            pane_ventas_antiguo.setVisible(false);
            pane_ventas_nuevo.setVisible(true);
        }
    }

    public void handleGen_fact_generar_button(ActionEvent actionEvent){
        String tipoCliente = cb_gen_fact_tip_cliente.getSelectionModel().getSelectedItem();
        int document = Integer.parseInt(gen_fact_id_TextField.getText());
        String number = gen_fact_linea_TextField.getText();

        Object[] info = connection.read_DB("SELECT * FROM Bill, Lines, Customer WHERE number=linenumber AND number='"+number+"' AND customerid=Customer.id AND customerid='"+document+"' AND EXTRACT(YEAR FROM date) = "+cal.get(Calendar.YEAR)+" AND EXTRACT(MONTH FROM date) = "+(cal.get(Calendar.MONTH)+1)+" AND type = '"+tipoCliente+"';");
        Vector<String[]> result = (Vector) info[1];

        String actualDate = cal.get(Calendar.YEAR) + "/" + months[cal.get(Calendar.MONTH)];
        String cutDate = cal.get(Calendar.YEAR) + "/" + months[cal.get(Calendar.MONTH)+1]+"/05";

        CreateBill bill = new CreateBill();

        bill.WriteBill(result.get(0),actualDate, cutDate, months[Integer.parseInt(result.get(0)[13].substring(5,7))-1]);
    }

    public void handleGen_fact_generar_colect_button(ActionEvent actionEvent){
        Object[] infoHeaderBill = connection.read_DB("SELECT * FROM Bill, Lines, Customer WHERE number=linenumber AND customerid=Customer.id AND EXTRACT(YEAR FROM date) = "+cal.get(Calendar.YEAR)+" AND EXTRACT(MONTH FROM date) = "+(cal.get(Calendar.MONTH)+1)+";");
        Vector<String[]> result = (Vector) infoHeaderBill[1];

        String actualDate = cal.get(Calendar.YEAR) + "/" + months[cal.get(Calendar.MONTH)];
        String cutDate = cal.get(Calendar.YEAR) + "/" + months[cal.get(Calendar.MONTH)+1]+"/05";

        for(int i = 0; i < result.size(); i++){
            CreateBill bill = new CreateBill();
            bill.WriteBill(result.get(i),actualDate, cutDate, months[Integer.parseInt(result.get(0)[13].substring(5,7))-1]);
        }
    }

    public void set_user_id (int id) {
        new_user.set_user_id(id);
    }
}