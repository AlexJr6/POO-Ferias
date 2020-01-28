package poo.restaurante;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ControllerPrincipal {


    @FXML
    private ComboBox<?> comboBoxOcupadas;

    @FXML
    private ComboBox<String> comboBoxGarcom;


    @FXML
    private ListView<Mesa> listViewOcupadas;

    @FXML
    private ListView<Mesa> listViewReservadas;


    @FXML
    private ContextMenu cancelar;

    @FXML
    private MenuItem cancelaReservaItem;

    @FXML
    private ListView<Mesa> listViewLivres;



    private ObservableList<Mesa>obsMesasLivre;
    private ObservableList<Mesa> obsMesas;
    private ObservableList<Mesa> obsMesasOcupadas;



    private FachadaMesas mesas = new FachadaMesas();



    @FXML
    private Button botaoDesocuparMesa;

    @FXML
    private Button botaoOcuparMesa;

    @FXML
    private Button botaoReservarMesa;

    @FXML
    private ComboBox<?> comboBoxPedidos;


    private static ArrayList<ObsMudaTela> ouvintes = new ArrayList<ObsMudaTela>();

    public static interface ObsMudaTela {
        void mudaTela(Object objetoQualquer);
    }

    public static void addMudaTela(ObsMudaTela novoOuvinte) {
        ouvintes.add(novoOuvinte);
    }

    public static void notificaOuvintes(Object dado) {
        for (ObsMudaTela o : ouvintes) {
            o.mudaTela(dado);
        }
    }




    @FXML
    public void initialize() {
        carregandoMesas();

    }


    public void carregandoMesas() {
        Mesa mesa1 = new Mesa(1);
        Mesa mesa2 = new Mesa(2);
        Mesa mesa3 = new Mesa(3);
        Mesa mesa4 = new Mesa(4);
        Mesa mesa5 = new Mesa(5);


//        System.out.println("TESTES -> "+mesa1.getComanda().pedidos[0].garcom.getNome());


        mesas.mesasLivres.add(mesa1);
        mesas.mesasLivres.add(mesa2);
        mesas.mesasLivres.add(mesa3);
        mesas.mesasLivres.add(mesa4);
        mesas.mesasLivres.add(mesa5);

        obsMesas = FXCollections.observableArrayList(mesas.mesasLivres);
       obsMesasOcupadas = FXCollections.observableArrayList(mesas.mesasOcupadas);

        listViewLivres.setItems(obsMesas);
        listViewOcupadas.setItems(obsMesasOcupadas);

    }
    @FXML
    void imprime(ActionEvent event) {
//
//        System.out.println("teste");
//        Mesa objMesa = listViewLivres.getSelectionModel().getSelectedItem();
//        listViewLivres.getSelectionModel().clearSelection();
//
//        mesas.remove(objMesa);
//
//        System.out.println(mesasOcupadas.isEmpty());
//
//        mesasOcupadas.add(objMesa);
//
//
//        obsMesas = FXCollections.observableArrayList(mesas);
//        obsMesasOcupadas = FXCollections.observableArrayList(mesas);
//
////        listViewLivres.setItems(obsMesas);
////        listViewOcupadas.setItems(obsMesasOcupadas);
//
//
    }

    @FXML
    void reservar (ActionEvent event) {
        Mesa m = listViewLivres.getSelectionModel().getSelectedItem();
        if (m==null) return;
        m.setStatusReservado();
        listViewReservadas.getItems().add(m);
        listViewLivres.getItems().remove(m);
        listViewLivres.getSelectionModel().clearSelection();
    }

    @FXML
    void desocupar(ActionEvent event){
        Mesa m = listViewOcupadas.getSelectionModel().getSelectedItem();
        if(m==null) return;
        m.setStatusLivre();
        listViewLivres.getItems().add(m);
        listViewOcupadas.getItems().remove(m);
        listViewOcupadas.getSelectionModel().clearSelection();
    }
    @FXML
    void ocupar(ActionEvent event ){
        Mesa m = listViewLivres.getSelectionModel().getSelectedItem();
        if(m==null) {
            m = listViewReservadas.getSelectionModel().getSelectedItem();
        }else if(m==null){
            return;
        }
        m.setStatusOcupado();
        listViewOcupadas.getItems().add(m);
        listViewLivres.getItems().remove(m);
        listViewReservadas.getItems().remove(m);
        listViewLivres.getSelectionModel().clearSelection();
        listViewReservadas.getSelectionModel().clearSelection();
    }
    @FXML
    public void visualizar(ActionEvent event) throws IOException {
//        Stage stage1 = (Stage) botaoAbrirMesa.getScene().getWindow();
//        stage1.close();

        FXMLLoader load = new FXMLLoader(this.getClass().getResource("view/Comanda.fxml"));
        Parent root = load.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        System.out.println(listViewLivres.getSelectionModel().getSelectedItem());

        notificaOuvintes(listViewLivres.getSelectionModel().getSelectedItem());
        stage.show();
    }

    @FXML
    void cancelarReserva(ActionEvent event) {
        Mesa m = listViewReservadas.getSelectionModel().getSelectedItem();
        if (m==null) return;
        m.setStatusReservado();
        listViewLivres.getItems().add(m);
        listViewReservadas.getItems().remove(m);
    }
    @FXML
    void atualizarComboBox(MouseEvent event){
        listViewOcupadas.getSelectionModel().getSelectedItem();
    }
}
