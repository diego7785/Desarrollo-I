import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.PopupWindow;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.awt.*;

public class Controller {

    @FXML
    private JFXButton btn_login;
    @FXML
    private JFXTextField tf_user;
    @FXML
    private JFXPasswordField tf_pass;
    @FXML
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
    private JFXTextField gen_rep_id_TextField;
    @FXML
    private Label gen_rep_id_label;
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
    private JFXComboBox gen_fact_clientes_comboBox;
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
    private Label pagar_tipo_cliente_label;
    @FXML
    private Label pagar_id_label;
    @FXML
    private Label pagar_linea_label;
    @FXML
    private JFXComboBox pagar_tipo_cliente_ComboBox;
    @FXML
    private JFXTextField pagar_id_TextField;
    @FXML
    private JFXTextField pagar_linea_TextField;
    @FXML
    private  Label pago_mensaje_label;
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
    private JFXComboBox gen_rep_tipo_id_ComboBox;
    @FXML
    private  Label gen_rep_tipo_id_label;
    @FXML
    private AnchorPane pane_gen_fact_individual;
    @FXML
    private AnchorPane pane_gen_fact_colectiva;
    @FXML
    private AnchorPane pane_gen_rep_interno;
    @FXML
    private AnchorPane pane_pagar_interno;




    @FXML
    public void log_in(ActionEvent event) throws Exception {

        System.out.println("oaerkaerokae");

        if(tf_user.getText().equals("user")  && tf_pass.getText().equals("pass")){

            btn_login.getScene().getWindow().hide();

            Stage primaryStage = new Stage();

            Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
            primaryStage.initStyle(StageStyle.TRANSPARENT);

            Scene login = new Scene(root);

            primaryStage.setTitle("");


            primaryStage.setScene(login);
            primaryStage.show();
        }

    }

    @FXML
    public void registro(ActionEvent event) throws Exception{
        String nombre = tf_nombre.getText();

    }

    @FXML

   /* public  void initialize()
    {
        pagar_tipo_cliente_ComboBox.getItems().addAll("1","2","holi");
    }

    @FXML*/
    public void cambiar_pestana(MouseEvent event) throws Exception{

        if(event.getSource().equals(realizar_venta)){
            pane_gestionar_usuarios.setVisible(false);
            pane_generar_reporte.setVisible(false);
            pane_ventas.setVisible(true);
            pane_generar_facturas.setVisible(false);
            pane_pagar.setVisible(false);
            pane_gest_usr_agregar.setVisible(false);
            pane_gest_usr_editar.setVisible(false);
            pane_gest_usr_cambiar_estado.setVisible(false);
            pane_gest_usr_listar.setVisible(false);
            titulo_label.setText("   Agregar cliente");
            titulo_label.setLayoutX(46);
        }
        if(event.getSource().equals(generar_reporte)){
            pane_generar_reporte.setVisible(true);
            pane_gestionar_usuarios.setVisible(false);
            pane_ventas.setVisible(false);
            pane_generar_facturas.setVisible(false);
            pane_generar_facturas.setVisible(false);
            pane_pagar.setVisible(false);
            pane_gest_usr_agregar.setVisible(false);
            pane_gest_usr_editar.setVisible(false);
            pane_gest_usr_cambiar_estado.setVisible(false);
            pane_gest_usr_listar.setVisible(false);
            titulo_label.setText(" Generar reportes");
            titulo_label.setLayoutX(173);
        }
        if(event.getSource().equals(realizar_pagos))
        {
            pane_ventas.setVisible(false);
            pane_generar_reporte.setVisible(false);
            pane_gestionar_usuarios.setVisible(false);
            pane_generar_facturas.setVisible(false);
            pane_generar_facturas.setVisible(false);
            pane_pagar.setVisible(true);
            pane_gest_usr_agregar.setVisible(false);
            pane_gest_usr_editar.setVisible(false);
            pane_gest_usr_cambiar_estado.setVisible(false);
            pane_gest_usr_listar.setVisible(false);
            titulo_label.setText("   Realizar pagos");
            titulo_label.setLayoutX(311);
        }
        if(event.getSource().equals(generar_factura))
        {
            pane_ventas.setVisible(false);
            pane_generar_reporte.setVisible(false);
            pane_generar_facturas.setVisible(true);
            pane_gestionar_usuarios.setVisible(false);
            pane_pagar.setVisible(false);
            pane_gest_usr_agregar.setVisible(false);
            pane_gest_usr_editar.setVisible(false);
            pane_gest_usr_cambiar_estado.setVisible(false);
            pane_gest_usr_listar.setVisible(false);
            titulo_label.setText(" Generar facturas");
            titulo_label.setLayoutX(445);
        }
        if(event.getSource().equals(agregar_user)){
            pane_gestionar_usuarios.setVisible(true);
            pane_ventas.setVisible(false);
            pane_generar_reporte.setVisible(false);
            pane_generar_facturas.setVisible(false);
            pane_generar_facturas.setVisible(false);
            pane_pagar.setVisible(false);
            pane_gest_usr_agregar.setVisible(false);
            pane_gest_usr_editar.setVisible(false);
            pane_gest_usr_cambiar_estado.setVisible(false);
            pane_gest_usr_listar.setVisible(false);
            titulo_label.setText("Gestión usuarios");
            titulo_label.setLayoutX(580);
        }
        //Those are the four Gestión de usuarios' options
        if (event.getSource().equals(gest_usr_añadir))
        {
            pane_gestionar_usuarios.setVisible(false);
            pane_ventas.setVisible(false);
            pane_generar_reporte.setVisible(false);
            pane_generar_facturas.setVisible(false);
            pane_generar_facturas.setVisible(false);
            pane_pagar.setVisible(false);
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
            pane_ventas.setVisible(false);
            pane_generar_reporte.setVisible(false);
            pane_generar_facturas.setVisible(false);
            pane_generar_facturas.setVisible(false);
            pane_pagar.setVisible(false);
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
            pane_ventas.setVisible(false);
            pane_generar_reporte.setVisible(false);
            pane_generar_facturas.setVisible(false);
            pane_generar_facturas.setVisible(false);
            pane_pagar.setVisible(false);
            pane_gest_usr_agregar.setVisible(false);
            pane_gest_usr_editar.setVisible(true);
            pane_gest_usr_cambiar_estado.setVisible(false);
            pane_gest_usr_listar.setVisible(false);
            titulo_label.setText(" Editar usuarios");
            titulo_label.setLayoutX(580);
        }
        if (event.getSource().equals(gest_usr_cambiar_estado))
        {
            pane_gestionar_usuarios.setVisible(false);
            pane_ventas.setVisible(false);
            pane_generar_reporte.setVisible(false);
            pane_generar_facturas.setVisible(false);
            pane_generar_facturas.setVisible(false);
            pane_pagar.setVisible(false);
            pane_gest_usr_agregar.setVisible(false);
            pane_gest_usr_editar.setVisible(false);
            pane_gest_usr_cambiar_estado.setVisible(true);
            pane_gest_usr_listar.setVisible(false);
            titulo_label.setText(" Cambiar estado");
            titulo_label.setLayoutX(580);
        }
        if (event.getSource().equals(gest_usr_listar))
        {
            pane_gestionar_usuarios.setVisible(false);
            pane_ventas.setVisible(false);
            pane_generar_reporte.setVisible(false);
            pane_generar_facturas.setVisible(false);
            pane_generar_facturas.setVisible(false);
            pane_pagar.setVisible(false);
            pane_gest_usr_agregar.setVisible(false);
            pane_gest_usr_editar.setVisible(false);
            pane_gest_usr_cambiar_estado.setVisible(false);
            pane_gest_usr_listar.setVisible(true);
            titulo_label.setText(" Listar usuarios");
            titulo_label.setLayoutX(580);
        }

        if(event.getSource().equals(salir)){
            System.exit(0);
        }
    }

    public void selec_tipo_cliente(MouseEvent event)
    {
        if (event.getSource().equals(gen_rep_corporativo))
        {
            pane_gen_rep_interno.setVisible(true);
            gen_rep_id_label.setText("                                   NIT:");
            gen_rep_tipo_id_ComboBox.setVisible(false);
            gen_rep_tipo_id_label.setVisible(false);
        }
        if (event.getSource().equals(gen_rep_persona_nat))
        {
            pane_gen_rep_interno.setVisible(true);
            gen_rep_id_label.setText("Documento de Identificación:");
            gen_rep_tipo_id_ComboBox.setVisible(true);
            gen_rep_tipo_id_label.setVisible(true);
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
            pagar_linea_label.setVisible(false);
            pagar_linea_TextField.setVisible(false);
            pago_mensaje_label.setText("Pago por cliente quiere decir que vas a cancelar el valor total de todas las lineas asociadas a un cliente");
            pane_pagar_interno.setVisible(true);
        }
        if(event.getSource().equals(pagar_linea))
        {
            pagar_linea_label.setVisible(true);
            pagar_linea_TextField.setVisible(true);
            pago_mensaje_label.setText("Pago por línea significa que vas a cancelar el valor de un determinado plan");
            pane_pagar_interno.setVisible(true);
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
            estado_label.setVisible(true);
            gest_usr_cambiar_estado_cb.setVisible(true);
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




}