package quique.laboratorio.simon;

import android.widget.ImageView;
/*
 * Esta es una clase que se utlizará como contenedor de propiedades para ejecutar runnables dentro del thread del UI
 * */
public class data {
	/*
	 * Esta compuesto por el botón que se le va a intercambiar la imagen, el delay respecto al momento en que es disparado el thread
	 * y el momento en que se intercambia la imagen, el string con el nombre del archivo de la imagen, el string con el
	 * nombre del archivo de sonido que se va a ejectura, y un booleano que indica si se esta iniciando el o sonido 
	 * o si se esta deteniendo.
	 * 	 * */
	private ImageView buttonToChange;
	private int delay;
	private String image;
	private String sound;
	private boolean startSound;
	
	/*
	 * El constructor asigna únicamente las variables a sus respectivas propiedades internas.
	 * */
	data(ImageView buttonToChange, int delay, String image, String sound, boolean startSound){
		this.buttonToChange = buttonToChange;
		this.delay = delay;
		this.image = image;
		this.sound = sound;
		this.startSound = startSound;
	}
	
	/*
	 * Un función set para la variable sonido
	 * */
	public void setSound(String sound){
		this.sound = sound;
	}
	/*
	 * Un función set para la variable  startSound
	 * */
	public void setStartSound(boolean startSound){
		this.startSound = startSound;
	}
	/*
	 * Un función set para la variable botón
	 * */
	public void setButton(ImageView buttonToChange){
		this.buttonToChange = buttonToChange;
	}
	/*
	 * Un función set para la variable del delay 
	 * */
	public void setDelay(int delay){
		this.delay = delay;
	}
	/*
	 * Un función set para la variable Imagen
	 * */
	public void setImage(String image){
		this.image = image;
	}
	/*
	 * Un función get para la variable botón
	 * */
	public ImageView getButton(){
		return buttonToChange;
	}
	/*
	 * Un función get para la variable delay
	 * */
	public int getDelay(){
		return delay;
	}
	/*
	 * Un función get para la variable imagen
	 * */
	public String getImage(){
		return image;
	}
	/*
	 * Un función get para la variable start sound
	 * */
	public boolean getStartSound(){
		return startSound;
	}
	/*
	 * Un función get para la variable sonido.
	 * */
	public String getSound(){
		return this.sound;
	}
}
