package Client.View;

import javax.swing.*;
import java.awt.*;

public class Background extends JPanel{

    // Atributo que guardara la imagen de Background que le pasemos.
    private Image background;
    private String imagePath = "z";
    private int positionX;
    private int positionY = 50;

    // Metodo que es llamado automaticamente por la maquina virtual Java cada vez que repinta
    public void paintComponent(Graphics g) {

    /* Obtenemos el tamaño del panel para hacer que se ajuste a este
    cada vez que redimensionemos la ventana y se lo pasamos al drawImage */
        int width = this.getSize().width;
        int height = this.getSize().height;

        // Mandamos que pinte la imagen en el panel
        if (this.background != null) {
            g.drawImage(this.background, 0, 0, width, height, null);
        }

        super.paintComponent(g);
    }

    // Metodo donde le pasaremos la dirección de la imagen a cargar.
    public void setBackground(String imagePath) {

        this.imagePath = imagePath;
        // Construimos la imagen y se la asignamos al atributo background.
        this.setOpaque(false);
        if (!imagePath.equals("z")) {
            this.background = new ImageIcon(imagePath).getImage();
            repaint();
        }
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public int getPositionX() {
        return positionX;
    }

    public int getPositionY() {
        return positionY;
    }

    public void setPositionX(int positionX) {
        this.positionX = positionX;
    }

    public void setPositionY(int positionY) {
        this.positionY = positionY;
    }


}
