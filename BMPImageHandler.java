//Autor: Santos López Tzoy
//Año: 2016
import java.util.Scanner;
public class BMPImageHandler{
	Scanner escaner;

	public BMPImageHandler(){
		escaner=new Scanner(System.in);
	}

	//Validamos que lo que ingresen no este vacio
	public String getString(String ingresar){
		String texto = "";
		while(texto.equals("")){
			System.out.println(ingresar);
			texto=escaner.nextLine();
		}
		return texto;
	}

	/*Mensaje que muestra al generar imagen de cierto color(verde, rojo, azul, sepia, grayscale, etc.)*/
	public static String mensajeImagen(String color){
		String mensaje = "La imagen "+ color +" fue generada correctamente";
		return mensaje;
	}

	/*Formas de ejecutar el proyecto*/
	public static void ejecucion(){
		String imagen = "imagen.bmp";//formato de la imagen esperada
		String javaBMPImageHandler = "java BMPImageHandler";
		System.out.println("\nForms of execute the proyect: "+"\n");
		System.out.println("Part  Command");
		System.out.println("   I: "+javaBMPImageHandler+" -basics "+imagen);//ejecuta parte 1 del proyecto
		System.out.println("II-1: "+javaBMPImageHandler+" -rotate "+imagen);//ejecuta parte 2-1 del proyecto
		System.out.println("II-2: "+javaBMPImageHandler+" -resize "+imagen);//ejecuta parte 2-2 del proyecto
		System.out.println(" III: "+javaBMPImageHandler+" -grayscale "+imagen);//ejecuta parte 3 del proyecto
		System.out.println("  IV: "+javaBMPImageHandler+" -kernel kernel.txt "+imagen);//ejecuta parte 4 del pj1
		System.out.println("Plus: "+javaBMPImageHandler+" -clone "+imagen);//ejecuta la parte de clonar imagen
		System.out.println("exit: "+javaBMPImageHandler+" -quit");//se sale del programa
		System.out.println(" All: "+javaBMPImageHandler+" -all imagen.bmp");//ejecutar todas las partes del pj1
	}

	//Mensaje que muestra cuando la imagen que estamos usando no tiene el formato .bmp
	public static void invalidFormatBMP(){
		System.out.println("La imagen no es de formato .BMP");
	}
	public static void main(String args[]) throws Exception{
		System.out.println("----------Welcome the processing bmp image handler project----------");
		BMPImageHandler bmpimagehandler = new BMPImageHandler();
		String ingresar;
		String cmd = "java BMPImageHandler ";
		do{
			BmpHandlerCore bmpHandlerCore = new BmpHandlerCore();
			BmpHandlerRotator bmpHandlerRotator = new BmpHandlerRotator();
			BmpHandlerResizer bmpHandlerResizer = new BmpHandlerResizer();
			BMPToGrayscale bmpToGrayscale = new BMPToGrayscale();
			ingresar = bmpimagehandler.getString("");
			try{
				if(ingresar.equals(cmd+ "-help")){
					ejecucion();//llamar metodo
				}else if(ingresar.equals(cmd + "-quit")){
					System.out.println("Exit...");
					break;//salirse del programa
				}else{
					String longitudComando = String.valueOf(ingresar.length());
					String imagen = ingresar.substring(29,Integer.parseInt(longitudComando));
					int longitudCmd = ingresar.length();
					String image = ingresar.substring(29,longitudCmd-4);//obtengo solo el nombre de la imagen, en la posicion 29 empieza el nombre del archivo a procesar
					String extension = ingresar.substring(longitudCmd-4,longitudCmd);//obtengo el formato de la imagen
					if (ingresar.equals(cmd + "-basics " + imagen)){
 						if(extension.equals(bmpHandlerCore.getPointBmp())){
 							bmpHandlerCore.cargando();
 							bmpHandlerCore.image(imagen,"green",mensajeImagen("Green"));
							bmpHandlerCore.image(imagen,"red",mensajeImagen("Red"));
							bmpHandlerCore.image(imagen,"blue",mensajeImagen("Blue"));
							bmpHandlerCore.image(imagen,"sepia",mensajeImagen("Sepia"));
							bmpToGrayscale.colorGrayscale("grayscale",imagen);
 						}else{
 							invalidFormatBMP();
 						}
					}else if(ingresar.equals(cmd + "-rotate " + imagen)){
						if(extension.equals(bmpHandlerCore.getPointBmp())) {
							bmpHandlerRotator.invertir180Grados(imagen);
						}else{
							invalidFormatBMP();
						}
					}else if(ingresar.equals(cmd + "-resize " + imagen)){
						bmpHandlerCore.cargando();
						bmpHandlerResizer.resizeWidth(imagen);
					}else if(ingresar.equals(cmd + "-clone " + ingresar.substring(28,Integer.parseInt(longitudComando)))){
						//por la longitud que tiene el texto -clone volvemos a jugar con el substring
						String imageClone= ingresar.substring(28,Integer.parseInt(longitudComando));
						bmpHandlerCore.cargando();
						bmpHandlerCore.image(imageClone,"copia",mensajeImagen("Original (copia)"));
					}else if(ingresar.equals(cmd + "-all " + ingresar.substring(26,Integer.parseInt(longitudComando)))){
						if(extension.equals(bmpHandlerCore.getPointBmp())){
							String nameImage = ingresar.substring(26,Integer.parseInt(longitudComando));
							bmpHandlerCore.cargando();
							bmpHandlerCore.image(nameImage,"green",mensajeImagen("Green"));
							bmpHandlerCore.image(nameImage,"red",mensajeImagen("Red"));
							bmpHandlerCore.image(nameImage,"blue",mensajeImagen("Blue"));
							bmpHandlerCore.image(nameImage,"sepia",mensajeImagen("Sepia"));
							bmpHandlerCore.image(nameImage,"copia",mensajeImagen("Original (copia)"));
							bmpToGrayscale.colorGrayscale("grayscale",nameImage);//metodo de la clase BMPToGrayscale
							bmpHandlerRotator.invertir180Grados(nameImage);//ROTA LA IMAGEN ORIGINAL pero lo invierte en otro color
						}else{
							invalidFormatBMP();
						}
					}else if(ingresar.equals(cmd + "-grayscale " + ingresar.substring(32,Integer.parseInt(longitudComando)))) {
						String imageGrayscale = ingresar.substring(32,Integer.parseInt(longitudComando));
						bmpHandlerCore.cargando();
						bmpToGrayscale.colorGrayscale("grayscale",imageGrayscale);
					}else{
						System.out.println("ERROR in the command!!!");
					}
			}
		}catch(Exception ex){
			System.out.println("ERROR!!!! Invalid command");
		}
	}while(true);
	}
}
