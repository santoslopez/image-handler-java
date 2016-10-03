import java.io.*;
import java.util.*;
import java.io.File;
public class BmpHandlerRotator{
	public String invertir180Grados(String nombreImagen){
		File file = new File(nombreImagen);
		FileInputStream fileInputStream = null;
		FileOutputStream fileOutputStream = null;
		if(file.exists()){
			try{
				fileInputStream = new FileInputStream(file);
				fileOutputStream = new FileOutputStream("HRotation-"+nombreImagen);
				int longitud = (int)file.length();
				byte header[]=new byte[54];
				for(int i=0;i<54;i++){
					header[i]=(byte)(fileInputStream.read());
				}
				byte arrayBytes[] = new byte[longitud-54];//este arreglo no almacena los valores del header, los primeros 54 bytes
				for(int pos=0;pos<arrayBytes.length;pos++){
					arrayBytes[(arrayBytes.length-1)-pos]=(byte)fileInputStream.read();
				}
				fileOutputStream.write(header);
				fileOutputStream.write(arrayBytes);
				fileOutputStream.write(0);
				fileOutputStream.write(0);
				System.out.println("La imagen se ha rotado 180 grados.");
				fileInputStream.close();
				fileOutputStream.close();
			}catch(Exception exception){
				System.out.println("Se produjo el siguiente error: "+ exception);
			}
		}else{
			System.out.println("La imagen no se encontro.");
		}
		return nombreImagen;
	}
}
