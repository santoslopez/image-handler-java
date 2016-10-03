import java.io.*;
import java.util.*;
import java.io.File;
public class BmpHandlerCore{

	private static byte header[]=new byte[54];
	private static int width;
	private static int height;
	private static byte[][] red;
	private static byte[][] blue;
	private static byte[][] green;
	private String pointBmp = ".bmp";
	private String guion = "-";

	/*Metodo para generar diferentes tonalidades de imagenes.

	Se utilizan tres parametros, el primer parametro "nombreImagen" se utiliza para ir generando los diferentes nombres
	de los archivos, por ejemplo: nombreImagen-red.bmp, nombreImagen-blue.bmp, nombreImagen-green.bmp y otros colores.
	El segundo parametro "nombreColor" nos sirve para saber en que IF meterse, y que este sepa como escribir los bytes (Que genere la imagen
	de este mismo color). En el tercer parametro "mensaje", sirve para imprimir el texto que nosotros queramos luego de haberse generado correctamente
	la imagen.

	Nota: Este metodo se llama desde la clase BMPImageHandler

	*/

	public String image(String nombreImagen, String nombreColor, String mensaje){
		File archivo = new File(nombreImagen);
		FileInputStream fileInputStream = null;
		FileOutputStream fileOutputStream = null;
		BmpHandlerCore bmpHandlerCore = new BmpHandlerCore();
		if(archivo.exists()){
			try{
				fileInputStream = new FileInputStream(archivo);
				int longitudNombre = nombreImagen.length();
				String nameImage = nombreImagen.substring(0,longitudNombre-4);//obtenemos el nombre de la imagen sin la extension.
				fileOutputStream = new FileOutputStream(nameImage+guion+nombreColor+pointBmp);
				//Leemos los primeros 54 bytes del encabezado
				for(int init=0;init<54;init+=1){
					header[init]=(byte)fileInputStream.read();
				}
				//Obtenemos el ancho de la imagen
				width = (((int)header[21]&0xff)<<24) | (((int)header[20]&0xff)<<16) | (((int)header[19]&0xff)<<8) | ((int)header[18]&0xff);
				//Obtenemos el alto de la imagen
				height = (((int)header[25]&0xff)<<24) | (((int)header[24]&0xff)<<16) | (((int)header[23]&0xff)<<8) | ((int)header[22]&0xff);
				//Creamos tres arreglos bidimensionales. Los tamaños van hacer dependiendo los valores del heigth y width (640x480)
				blue= new byte[height][width];
				green= new byte[height][width];
				red= new byte[height][width];
				for(int h=0;h<height;h++){
					for(int w=0;w<width;w++){
						blue[h][w]=(byte)(fileInputStream.read());
						green[h][w]=(byte)(fileInputStream.read());
						red[h][w]=(byte)(fileInputStream.read());
					}
				}
				fileOutputStream.write(header);
				for(int h=0;h<height;h++){
					for(int w=0;w<width;w++){
						if(nombreColor.equals("red")){
							//Genero una imagen en tonalidad rojo
							fileOutputStream.write(0);
							fileOutputStream.write(0);
							fileOutputStream.write(red[h][w]);
						}else if(nombreColor=="blue"){
							//Genero una imagen en tonalidad azul
							fileOutputStream.write(blue[h][w]);
							fileOutputStream.write(0);
							fileOutputStream.write(0);
						}else if(nombreColor=="green"){
							//Genero una imagen en tonalidad verde
							fileOutputStream.write(0);
							fileOutputStream.write(green[h][w]);
							fileOutputStream.write(0);
						}else if(nombreColor=="sepia"){
							//Genero una imagen en tonalidad sepia, aunque NO ME GENERA la tonalidad correcta.
							//Esos valores con los que lo multiplique son los recomendados por Microsoft
							int newBlue = (int)((red[h][w]*0.272) +(green[h][w]*0.534)+(blue[h][w]*0.131));
							int newGreen = (int)((red[h][w]*0.349) +(green[h][w]*0.686)+(blue[h][w]*0.168));
							int newRed = (int)((red[h][w]*0.393) +(green[h][w]*0.769)+(blue[h][w]*0.189));
							fileOutputStream.write(Math.min(newBlue,255));
							fileOutputStream.write(Math.min(newGreen,255));
							fileOutputStream.write(Math.min(newRed,255));
						}else{
							//Genera una copia de la misma imagen
							fileOutputStream.write(blue[h][w]);
							fileOutputStream.write(green[h][w]);
							fileOutputStream.write(red[h][w]);
						}
					}
				}
				System.out.println(mensaje);
				fileInputStream.close();
				fileOutputStream.close();
			}catch(Exception exception){
				System.out.println("Se produjo el siguiente error: "+ exception);
			}
		}else{
			System.out.println("La imagen "+ nombreColor +" no fue generada. Imagen que ingreso no fue encontrada");
		}
		return nombreImagen;
	}
	//Invocamos a este metodo desde la clase BMPImageHandler cuando las imagenes de las distintas tonalidades estan por generarse.
	public void cargando(){
		System.out.println("Espere un momento...");
	}
	//Metodo que devuelve el ancho de la imagen.
	public int getWidth(){
		return this.width;
	}
	//Metodo que devuelve el alto de la imagen
	public int getHeigth(){
		return this.height;
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
	//Metodo que devuelve la extension de la imagen con la que se va a trabajar.
	public String getPointBmp(){
		return this.pointBmp;
	}

	/*Metodo que devuelve los primeros 54 bytes de la imagen.
	 Para ver esta informacion en pantalla podemos hacer lo siguiente:
	 BmpHandlerCore bmpHandlerCore = new BmpHandlerCore();
	 System.out.println(Arrays.toString(bmpHandlerCore.getHeader());
	*/
	public byte[] getHeader(){
		return this.header;
	}
}
