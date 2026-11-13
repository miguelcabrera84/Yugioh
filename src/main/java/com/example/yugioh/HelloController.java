package com.example.yugioh;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class HelloController {

    @FXML private BorderPane panelMenu;
    @FXML private BorderPane panelCarrusel;
    @FXML private ComboBox<Integer> comboNivel;
    @FXML private ComboBox<String> comboAtributo;
    @FXML private CheckBox checkAtkLowToHigh;
    @FXML private CheckBox checkDefLowToHigh;
    @FXML private Button btnAudioMenu;
    @FXML private Button btnAudioCarrusel;

    @FXML private Label lblTituloFiltro;
    @FXML private Label lblContador;
    @FXML private ImageView imgAnterior;
    @FXML private Label lblNombreAnterior;
    @FXML private ImageView imgActual;
    @FXML private Label lblNombreActual;
    @FXML private Label lblAtributoActual;
    @FXML private Label lblNivelActual;
    @FXML private Label lblTipoActual;
    @FXML private Label lblAtkActual;
    @FXML private Label lblDefActual;
    @FXML private TextArea txtDescripcionActual;
    @FXML private ImageView imgSiguiente;
    @FXML private Label lblNombreSiguiente;

    private ListaCircularDoble<Carta> listaMaestra;
    private ListaCircularDoble<Carta> listaActiva;
    private NodoDoble<Carta> nodoActual;
    private int indiceActual;
    private int totalActual;

    private MediaPlayer mediaPlayer;
    private boolean musicaActiva = true;


    private final Map<String, Image> cacheImagenes = new HashMap<>();

    @FXML
    public void initialize() {
        listaMaestra = LectorCSV.cargarCartas("cartas_yugioh_completo.csv");

        comboNivel.getItems().addAll(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12);
        comboNivel.getSelectionModel().select(Integer.valueOf(4));

        comboAtributo.getItems().addAll("DARK", "LIGHT", "EARTH", "WATER", "FIRE", "WIND");
        comboAtributo.getSelectionModel().select("DARK");

        iniciarMusica();
    }

    private void iniciarMusica() {
        try {
            URL recurso = getClass().getResource("musica.mp3");
            if (recurso != null) {
                Media media = new Media(recurso.toExternalForm());
                mediaPlayer = new MediaPlayer(media);
                mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
                mediaPlayer.play();
            }
        } catch (Exception e) {
            System.out.println("No se pudo cargar musica.mp3: " + e.getMessage());
        }
    }

    @FXML
    private void onToggleAudio() {
        musicaActiva = !musicaActiva;
        String icono = musicaActiva ? "🔊" : "🔇";
        btnAudioMenu.setText(icono);
        btnAudioCarrusel.setText(icono);

        if (mediaPlayer != null) {
            if (musicaActiva) {
                mediaPlayer.play();
            } else {
                mediaPlayer.pause();
            }
        }
    }

    @FXML
    private void onShowAllCards() {
        abrirCarrusel(listaMaestra, "Todas las cartas");
    }

    @FXML
    private void onShowByLevel() {
        Integer nivel = comboNivel.getValue();
        if (nivel != null) {
            ListaCircularDoble<Carta> filtrada = GestorCartas.filtrarPorNivel(listaMaestra, nivel);
            abrirCarrusel(filtrada, "Cartas Nivel " + nivel);
        }
    }

    @FXML
    private void onShowByAttribute() {
        String atributo = comboAtributo.getValue();
        if (atributo != null) {
            ListaCircularDoble<Carta> filtrada = GestorCartas.filtrarPorAtributo(listaMaestra, atributo);
            abrirCarrusel(filtrada, "Cartas Atributo " + atributo);
        }
    }

    @FXML
    private void onShowByAttack() {
        boolean asc = checkAtkLowToHigh.isSelected();
        ListaCircularDoble<Carta> ordenada = GestorCartas.ordenarPorAtaque(listaMaestra, asc);
        abrirCarrusel(ordenada, "Ordenadas por ATK (" + (asc ? "Menor a Mayor" : "Mayor a Menor") + ")");
    }

    @FXML
    private void onShowByDefense() {
        boolean asc = checkDefLowToHigh.isSelected();
        ListaCircularDoble<Carta> ordenada = GestorCartas.ordenarPorDefensa(listaMaestra, asc);
        abrirCarrusel(ordenada, "Ordenadas por DEF (" + (asc ? "Menor a Mayor" : "Mayor a Menor") + ")");
    }

    private void abrirCarrusel(ListaCircularDoble<Carta> lista, String titulo) {
        if (lista == null || lista.estaVacia()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "No se encontraron cartas con ese filtro.");
            alert.showAndWait();
            return;
        }
        this.listaActiva = lista;
        this.nodoActual = lista.getInicio();
        this.indiceActual = 1;
        this.totalActual = lista.getTamano();

        lblTituloFiltro.setText(titulo);
        actualizarVistaCarrusel();

        panelMenu.setVisible(false);
        panelCarrusel.setVisible(true);
    }

    @FXML
    private void onVolverMenu() {
        panelCarrusel.setVisible(false);
        panelMenu.setVisible(true);
    }

    @FXML
    private void onSiguiente() {
        if (nodoActual != null) {
            nodoActual = nodoActual.getSig();
            indiceActual = (indiceActual % totalActual) + 1;
            actualizarVistaCarrusel();
        }
    }

    @FXML
    private void onAnterior() {
        if (nodoActual != null) {
            nodoActual = nodoActual.getAnt();
            indiceActual = (indiceActual == 1) ? totalActual : indiceActual - 1;
            actualizarVistaCarrusel();
        }
    }

    private void actualizarVistaCarrusel() {
        Carta actual = nodoActual.getInfo();
        Carta anterior = nodoActual.getAnt().getInfo();
        Carta siguiente = nodoActual.getSig().getInfo();

        lblContador.setText("(" + indiceActual + "/" + totalActual + ")");

        imgActual.setImage(obtenerImagen(actual));
        lblNombreActual.setText(actual.getNombre());
        lblAtributoActual.setText("Atributo: " + actual.getAtributo());
        lblNivelActual.setText("Nivel: " + (actual.getNivel() >= 0 ? actual.getNivel() : "N/A"));
        lblTipoActual.setText(actual.getTipo());
        lblAtkActual.setText("ATK: " + (actual.getAtk() >= 0 ? actual.getAtk() : "N/A"));
        lblDefActual.setText("DEF: " + (actual.getDef() >= 0 ? actual.getDef() : "N/A"));
        txtDescripcionActual.setText(actual.getDescripcion());

        imgAnterior.setImage(obtenerImagen(anterior));
        lblNombreAnterior.setText(anterior.getNombre());

        imgSiguiente.setImage(obtenerImagen(siguiente));
        lblNombreSiguiente.setText(siguiente.getNombre());
    }

    private Image obtenerImagen(Carta carta) {
        return cacheImagenes.computeIfAbsent(carta.getId(), id -> new Image(carta.getUrlImagen(), true));
    }
}