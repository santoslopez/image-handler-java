import java.io.*;
import java.util.*;
import java.io.File;
public class BMPToGrayscale{
	private static byte header[]=new byte[1078];;
	private static byte[][] red;
	private static byte[][] blue;
	private static byte[][] green;
	public String colorGrayscale(String color,String nombreImagen){
		File archivo = new File(nombreImagen);
		FileInputStream fileInputStream = null;
		FileOutputStream fileOutputStream = null;
		BMPImageHandler bmpimagehandler = new BMPImageHandler();
		BMPToGrayscale bmpToGrayscale = new BMPToGrayscale();
		if(archivo.exists()){
			try{
				fileInputStream = new FileInputStream(archivo);
				fileOutputStream = new FileOutputStream(color+nombreImagen);
				for(int i=0;i<1078;i++){
					header[i]=(byte)fileInputStream.read();
				}
				red= new byte[640][480];
				blue= new byte[640][480];
				green= new byte[640][480];
				for(int h=0;h<640;h++){
					for(int w=0;w<480;w++){
						blue[h][w]=(byte)(fileInputStream.read());
						green[h][w]=(byte)(fileInputStream.read());
						red[h][w]=(byte)(fileInputStream.read());
					}
				}
				fileOutputStream.write(bmpToGrayscale.getHeader());
				for(int h=0;h<640;h++){
					for(int w=0;w<480;w++){
						/*Valores multiplicados recomendado por Microsoft*/
						//NO ME GENERA LA IMAGEN CORRECTAMENTE.
						int grayscale = (int)((bmpToGrayscale.getArrayBlue()[h][w]*0.3f)+(bmpToGrayscale.getArrayRed()[h][w]*0.59f)+(bmpToGrayscale.getArrayGreen()[h][w]*0.11f));
					  fileOutputStream.write(grayscale);
					  fileOutputStream.write(grayscale);
					  fileOutputStream.write(grayscale);
					}
				}
				System.out.println(bmpimagehandler.mensajeImagen("Grayscale"));
				fileInputStream.close();
				fileOutputStream.close();
			}catch(Exception exception){
				System.out.println("Se produjo el siguiente error: "+ exception);
			}
		}else{
			System.out.println("La imagen grayscale no fue generada. Imagen que ingreso no fue encontrada");
		}
		return nombreImagen;
	}
	public byte[] getHeader(){
		return this.header;
	}
	public byte[][] getArrayBlue(){
		return this.blue;
	}
	public byte[][] getArrayRed(){
		return this.red;
	}
	public byte[][] getArrayGreen(){
		return this.green;
	}
}
