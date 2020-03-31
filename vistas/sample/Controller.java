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
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.PopupWindow;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.util.Arrays;
import java.util.Calendar;
import java.util.ResourceBundle;
import java.util.Vector;

public class Controller implements Initializable {

    //Class Constructor
    public Controller()
    {
    }

    //Database connection
    private DBConnection connection= new DBConnection("", "", "", "", "", "");
    private final Calendar cal = Calendar.getInstance();
    private final String months[]={"Enero","Febrero","Marzo","Abril","Mayo","Junio","Julio",
            "Agosto","Septiembre","Octubre","Noviembre","Diciembre"};

    //User class object
    User new_user = new User();

    ObservableList<String> tipoCliente = FXCollections.observableArrayList( "Natural", "Corporativo");
    ObservableList<String> tipoDocumento= FXCollections.observableArrayList( "Cédula Ciudadanía", "Cédula Extranjería",
            "Pasaporte", "Carné Diplomático");

    //Login interface
    @FXML
    private JFXButton btn_login;
    @FXML
    private JFXTextField tf_user;
    @FXML
    private JFXPasswordField tf_pass;
    @FXML
    //****
    private JFXComboBox cb_rol;
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
    private JFXComboBox<String> gen_fact_clientes_comboBox;
    @FXML
    private JFXComboBox<String> gen_fact_clientes_comboBox1;
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
    private JFXComboBox gest_usr_cambiar_estado_cb;
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
    @FXML
    private ComboBox pepe;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Platform.runLater(() ->
        {
            if (new_user.get_user_rol() == 1) {
                realizar_pagos.setVisible(false);
                generar_factura.setVisible(false);
                agregar_user.setVisible(false);
                pane_ventas.setVisible(true);
                titulo_label.setText("  Agregar cliente");
                titulo_label.setLayoutX(46);

            } else if (new_user.get_user_rol() == 2) {
                realizar_venta.setVisible(false);
                generar_reporte.setVisible(false);
                generar_factura.setVisible(false);
                agregar_user.setVisible(false);
                pane_pagar.setVisible(true);
                titulo_label.setText("   Realizar pagos");
                titulo_label.setLayoutX(311);
            } else {
                realizar_venta.setVisible(false);
                generar_reporte.setVisible(false);
                realizar_pagos.setVisible(false);
                pane_generar_facturas.setVisible(true);
                titulo_label.setText(" Generar facturas");
                titulo_label.setLayoutX(445);
            }
            gen_fact_clientes_comboBox.getItems().addAll(tipoCliente);
            gen_fact_clientes_comboBox1.getItems().addAll(tipoDocumento);
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
    public void cambiar_pestana(MouseEvent event) throws Exception
    {
        if (new_user.get_user_rol() == 1)
        {
            realizar_pagos.setVisible(false);
            generar_factura.setVisible(false);
            agregar_user.setVisible(false);
            if (event.getSource().equals(realizar_venta))
            {
                pane_generar_reporte.setVisible(false);
                pane_ventas.setVisible(true);
                titulo_label.setText("  Agregar cliente");
                titulo_label.setLayoutX(46);
                pane_ventas_antiguo.setVisible(false);
                pane_ventas_nuevo.setVisible(false);
            }
            if (event.getSource().equals(generar_reporte))
            {
                pane_generar_reporte.setVisible(true);
                pane_gestionar_usuarios.setVisible(false);
                titulo_label.setText(" Generar reportes");
                titulo_label.setLayoutX(173);
                pane_gen_rep_pnat.setVisible(false);
                pane_gen_rep_corp.setVisible(false);
            }
        }
        else if (new_user.get_user_rol() == 2)
        {
            realizar_venta.setVisible(false);
            generar_reporte.setVisible(false);
            generar_factura.setVisible(false);
            agregar_user.setVisible(false);

            if (event.getSource().equals(realizar_pagos))
            {
                pane_pagar.setVisible(true);
                titulo_label.setText("   Realizar pagos");
                titulo_label.setLayoutX(311);
                pane_pagar_linea.setVisible(false);
                pane_pagar_cliente.setVisible(false);
            }
        }
        else
        {
            realizar_venta.setVisible(false);
            generar_reporte.setVisible(false);
            realizar_pagos.setVisible(false);

            if (event.getSource().equals(generar_factura))
            {
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
            if (event.getSource().equals(agregar_user))
            {
                pane_generar_facturas.setVisible(false);
                pane_gestionar_usuarios.setVisible(true);
                titulo_label.setText("Gestión usuarios");
                titulo_label.setLayoutX(580);
            }
        }
    }

    @FXML
    public void log_out(MouseEvent event)
    {
        if(event.getSource().equals(salir))
        {
            int user_answer = JOptionPane.showConfirmDialog(null, "¿Seguro desea cerrar la sesion?", "¡Alerta!", JOptionPane.YES_NO_OPTION);
            if (user_answer == 0)
            {
                System.exit(0);
            }
        }
    }

    @FXML
    public void user_gest(MouseEvent event)
    {
        if (event.getSource().equals(gest_usr_añadir))
        {
            pane_gestionar_usuarios.setVisible(false);
            pane_generar_facturas.setVisible(false);
            pane_gest_usr_agregar.setVisible(true);
            pane_gest_usr_editar.setVisible(false);
            pane_gest_usr_cambiar_estado.setVisible(false);
            pane_gest_usr_listar.setVisible(false);
            titulo_label.setText(" Añadir usuarios");
            titulo_label.setLayoutX(580);
        }
        if (event.getSource().equals(gest_usr_agregar_retroceder_Image) || event.getSource().equals(gest_usr_editar_retroceder_Image) || event.getSource().equals(gest_usr_cambiar_estado_retroceder_Image) || event.getSource().equals(gest_usr_listar_retroceder_Image))
        {
            pane_gestionar_usuarios.setVisible(true);
            pane_generar_facturas.setVisible(false);
            pane_gest_usr_agregar.setVisible(false);
            pane_gest_usr_editar.setVisible(false);
            pane_gest_usr_cambiar_estado.setVisible(false);
            pane_gest_usr_listar.setVisible(false);
            titulo_label.setText("Gestión usuarios");
            titulo_label.setLayoutX(580);
        }
        if (event.getSource().equals(gest_usr_editar))
        {
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
        if (event.getSource().equals(gest_usr_cambiar_estado))
        {
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
        if (event.getSource().equals(gest_usr_listar))
        {
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

    public void selec_tipo_cliente(MouseEvent event)
    {
        if (event.getSource().equals(gen_rep_corporativo))
        {
            pane_gen_rep_pnat.setVisible(false);
            pane_gen_rep_corp.setVisible(true);
        }
        if (event.getSource().equals(gen_rep_persona_nat))
        {
            pane_gen_rep_pnat.setVisible(true);
            pane_gen_rep_corp.setVisible(false);
        }
    }

    public void selec_tipo_factura(MouseEvent event)
    {
        if(event.getSource().equals(gen_fact_colectiva))
        {
            pane_gen_fact_individual.setVisible(false);
            pane_gen_fact_colectiva.setVisible(true);
         }
        if(event.getSource().equals(gen_fact_individual))
        {
            pane_gen_fact_individual.setVisible(true);
            pane_gen_fact_colectiva.setVisible(false);
        }
    }

    public void select_tipo_pago(MouseEvent event)
    {
        if(event.getSource().equals(pagar_cliente))
        {
            pane_pagar_linea.setVisible(false);
            pane_pagar_cliente.setVisible(true);
        }
        if(event.getSource().equals(pagar_linea))
        {
            pane_pagar_linea.setVisible(true);
            pane_pagar_cliente.setVisible(false);
        }
    }

    public void buscar_edit(ActionEvent event){
        if(event.getSource().equals(gest_usr_editar_btn_buscar)){
            pane_edit_campos.setVisible(true);
        }
    }

    public void buscar_estado(ActionEvent event){
        if(event.getSource().equals(gest_usr_estado_btn_buscar)){
            pane_estado_campos.setVisible(true);
            pane_estado_est.setVisible(true);
            //estado_label.setVisible(true);
            //gest_usr_cambiar_estado_cb.setVisible(true);
        }
    }

    public void tipo_venta(MouseEvent event)
    {
        if(event.getSource().equals(ventas_cliente_ant_Image))
        {
            pane_ventas_antiguo.setVisible(true);
            pane_ventas_nuevo.setVisible(false);
        }
        if(event.getSource().equals(ventas_cliente_nue_Image))
        {
            pane_ventas_antiguo.setVisible(false);
            pane_ventas_nuevo.setVisible(true);
        }
    }

    public void handleGen_fact_generar_button(ActionEvent actionEvent){
        String tipoCliente = gen_fact_clientes_comboBox.getSelectionModel().getSelectedItem();
        int document = Integer.parseInt(gen_fact_id_TextField.getText());
        String number = gen_fact_linea_TextField.getText();
        Object[] info = connection.read_DB("SELECT * FROM Bill, Lines, Customer WHERE number=linenumber AND number='"+number+"' AND customerid=Customer.id AND customerid="+document+" AND EXTRACT(YEAR FROM date) = "+cal.get(Calendar.YEAR)+" AND EXTRACT(MONTH FROM date) = "+(cal.get(Calendar.MONTH)+1)+" AND type = '"+tipoCliente+"';");
        Vector<String[]> result = (Vector) info[1];
        String actualDate = cal.get(Calendar.YEAR) + "/" + months[cal.get(Calendar.MONTH)];
        String cutDate = cal.get(Calendar.YEAR) + "/" + months[cal.get(Calendar.MONTH)+1]+"/05";
        CreateBill bill = new CreateBill();

        bill.WriteBill(result.get(0),actualDate, cutDate, months[Integer.parseInt(result.get(0)[13].substring(5,7))-1]);
    }

    public void handleGen_fact_generar_colect_button(ActionEvent actionEvent){
        Object[] infoHeaderBill = connection.read_DB("SELECT * FROM Bill, Lines, Customer WHERE number=linenumber AND customerid=Customer.id AND EXTRACT(YEAR FROM date) = 2020 AND EXTRACT(MONTH FROM date) = 3;");
        Vector<String[]> result = (Vector) infoHeaderBill[1];
        String actualDate = cal.get(Calendar.YEAR) + "/" + months[cal.get(Calendar.MONTH)];
        String cutDate = cal.get(Calendar.YEAR) + "/" + months[cal.get(Calendar.MONTH)+1]+"/05";
        for(int i=0; i<result.size(); i++){
            CreateBill bill = new CreateBill();
            bill.WriteBill(result.get(i),actualDate, cutDate, months[Integer.parseInt(result.get(0)[13].substring(5,7))-1]);
        }
    }

}