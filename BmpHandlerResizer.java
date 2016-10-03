import java.io.*;
import java.util.*;
import java.io.File;
public class BmpHandlerResizer{
	
	private static byte header[]=new byte[54];
	private static int width;
	private static int height;
	private static int dosexp1 = 1;
	private static int dosexp8 = 256;
	private static int dosexp16 = 65536;
	private static int dosexp24 = 16777216;
	private static int tipoFicheroBM;
	private static int size;
	private static byte[][] red;
	private static byte[][] blue;
	private static byte[][] green;
	
	
	public String resizeWidth(String nombreImagen){
		String imagen = nombreImagen;
		File archivo = new File(imagen);
		FileInputStream fileInputStream = null;
		FileOutputStream fileOutputStream = null;
		if(archivo.exists()){
			try{
				fileInputStream = new FileInputStream(archivo);
				fileOutputStream = new FileOutputStream("Thin-"+imagen);
				byte restoBytes[] = new byte[fileInputStream.available()];

				for(int init=0;init<54;init++){
					header[init]=(byte)(fileInputStream.read());	
					//}
				}
				int widt = (((int)header[21]&0xff)<<24) | (((int)header[20]&0xff)<<16) | (((int)header[19]&0xff)<<8) | ((int)header[18]&0xff);
				int heigh = (((int)header[25]&0xff)<<24) | (((int)header[24]&0xff)<<16) | (((int)header[23]&0xff)<<8) | ((int)header[22]&0xff);	
				red= new byte[heigh][widt];
				blue= new byte[heigh][widt];
				green= new byte[heigh][widt];
				for(int h=0;h<heigh;h++){
					for(int w=0;w<(widt);w++){
						red[h][w]=(byte)fileInputStream.read();
						blue[h][w]=(byte)fileInputStream.read();
						green[h][w]=(byte)fileInputStream.read();
					}
				}
				//byte f[][]= new byte[widt][heigh];
				fileOutputStream.write(header);	
				for(int w=0;w<widt;w++){
					for(int h=0;h<(heigh);h++){
						//fileOutputStream.write(0);
						//fileOutputStream.write(0);
						fileOutputStream.write(red[h][w]);
						fileOutputStream.write(blue[h][w]);
						fileOutputStream.write(green[h][w]);
						//red[h][w]=(byte)fileInputStream.read();
						//blue[h][w]=(byte)fileInputStream.read();
						//green[h][w]=(byte)fileInputStream.read();
					}
				}	
				
				//for(int )
				//fileOutputStream.write(restoBytes);
				/*for(int h=0;h<heigh;h++){
					for(int w=0;w<(widt);w++){
						
						//fileOutputStream.write(0);
						//fileOutputStream.write(0);
						fileOutputStream.write(blue[h][w]);
						//fileOutputStream.write(0);
					}
				}*/
				
				//fileInputStream.read(header);
				//fileInputStream.read(restoBytes);
				//fileOutputStream.write(header);
				//fileOutputStream.write(restoBytes);
				System.out.println("Imagen modificada en ancho.");
				fileInputStream.close();
				fileOutputStream.close();
			}catch(Exception exception){
				System.out.println("Se produjo el siguiente error: "+ exception);
			}
		}else{
			System.out.println("ERROR!!! La imagen no se encontro.");
		}
		return nombreImagen;
	}
}