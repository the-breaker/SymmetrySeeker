package algoritmo.extra;

import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
//import java.io.IOException;
import java.util.ArrayList;
//import javax.imageio.ImageIO;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class Main {
	
    public static void main(String[] args) {
        
        String rutaOrigen="D:/imgs/";
        File dirOrigen=new File(rutaOrigen);
        //Comprueba que sea un directorio
        if (!dirOrigen.isDirectory()){
            System.out.println("La ruta enviada no es un directorio");
            System.exit(-1);
        }
        /**Crea el directorio destino  */
        File dirDestino=new File(rutaOrigen+"/mods_green");
        if (!dirDestino.exists()){
            dirDestino.mkdir();
        }
        //Recorre los ficheros
        File ficheros[]=dirOrigen.listFiles();
        for (File fic:ficheros){            
            //Si no es directorio
            if (!fic.isDirectory()){            
                try{
                    //Carga la imagen
                    Image ima=Toolkit.getDefaultToolkit().getImage(fic.getAbsolutePath());
                    ima = new ImageIcon(ima).getImage(); //Para poder utilizar el getWidth y getHeight y asegurarse la carga de la imagen
                    //Creaci�n BufferedImage con la imagen
                    BufferedImage bi =
                    GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration()
                                        .createCompatibleImage(ima.getWidth(null), ima.getHeight(null), Transparency.OPAQUE);
                    bi.getGraphics().drawImage(ima, 0, 0, null);
                    // Almacenar los filtros que deseamos aplicar
                    ArrayList < Filtro > alFiltros=new ArrayList< Filtro >();
                    alFiltros.add(new Simetrico());
                    int pro=0;
                 // Ejecutar los filtros
                    for (Filtro f:alFiltros){
                        bi=f.filtrar(bi);
                    }
                    // Guardar la imagen final en la carpeta destino
                    try{
                        ImageIO.write(bi, "jpg", new File(dirDestino + "/" + fic.getName())); 
                        System.out.println("Foto finalizada " + fic.getName());
                    }catch(IOException e){
                        System.out.println("Error al procesar foto");
                        e.printStackTrace();
                    }
                }catch(Exception e){
                    System.out.println(fic.getAbsolutePath() + " no tratado");
                }
            }
        }
    }
} 