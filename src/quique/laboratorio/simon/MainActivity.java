/*
 * Aplicación creada por:
 * Nombre: Enrique Sáurez Apuy
 * Carné: a95872
 * Curso: Laboratorio de Programación
 * II Semestre, 2013s
 * */

package quique.laboratorio.simon;

import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.content.res.AssetFileDescriptor;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.TextView;
import android.view.*;
/*
 * Est es la clase que se encarga de manejar el ui principal
 * */
public class MainActivity extends Activity {
    // Variables requeridas para el correcto funcionamiento de la clase
    private SimonGame mGame;//Logica del juego
    //Variables multiples
    int mCounter,mSequenceSize;
    private int total,width,height,delayTime;
	private int baseDelay = 1000;
    private static int delta = 500;
    private ImageView mButtons[];///Contenedor de los botones
    private String button_idle[], button_press[], sounds[];///Direcciones de imagenes y sonidos.
	private TextView mInformation;	
	private boolean mGameOver;
	private ImageView toChange;
	private Queue<data> cola = new LinkedList<data>();//Lista de valores por ejecutar en la cola
	private Handler mHandler = new Handler();///Handler de los runnables.
	private AssetFileDescriptor mAFD;
	private MediaPlayer mPlayer;///Encargado de manejar los sonidos

	/*
	 * Función que se ejecuta cuando se crea el ui inicialmente.
	 * */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_main);
	    ///Se crea el espacio de memoria para las variables.
	    mPlayer = new MediaPlayer();	
	    mButtons = new ImageView[9];
	    button_idle = new String[9];
	    button_press = new String[9];
	    sounds = new String[9];
	    ///Se define la posicion de cada botón, y las imagenes/sonido para cada una de estas.
	    mButtons[0] = (ImageView) findViewById(R.id.imageButton1);
	    button_idle[0] = "amarillo_idle.png";
	    button_press[0] = "amarillo_press.png";
	    sounds[0] = "sonido1.mp3";
	    setImage(mButtons[0],button_idle[0]);
	    mButtons[1] = (ImageView) findViewById(R.id.imageButton2);
	    button_idle[1] = "anaranjado_idle.png";
	    button_press[1] = "anaranjado_press.png";
	    sounds[1] = "sonido2.mp3";
	    setImage(mButtons[1],button_idle[1]);
	    mButtons[2] = (ImageView) findViewById(R.id.imageButton3);
	    button_idle[2] = "azul_idle.png";
	    button_press[2] = "azul_press.png";
	    sounds[2] = "sonido3.mp3";
	    setImage(mButtons[2],button_idle[2]);
	    mButtons[3] = (ImageView) findViewById(R.id.imageButton4);
	    button_idle[3] = "celeste_idle.png";
	    button_press[3] = "celeste_press.png";
	    sounds[3] = "sonido5.mp3";
	    setImage(mButtons[3],button_idle[3]);
	    mButtons[4] = (ImageView) findViewById(R.id.imageButton5);
	    button_idle[4] = "start_button.png";
	    button_press[4] = "start_button.png";
	    setImage(mButtons[4],button_idle[4]);
	    mButtons[5] = (ImageView) findViewById(R.id.imageButton6);
	    button_idle[5] = "gris_idle.png";
	    button_press[5] = "gris_press.png";
	    sounds[5] = "sonido6.mp3";
	    setImage(mButtons[5],button_idle[5]);
	    mButtons[6] = (ImageView) findViewById(R.id.imageButton7);
	    button_idle[6] = "morado_idle.png";
	    button_press[6] = "morado_press.png";
	    sounds[6] = "sonido7.mp3";
	    setImage(mButtons[6],button_idle[6]);
	    mButtons[7] = (ImageView) findViewById(R.id.imageButton8);
	    button_idle[7] = "rojo_idle.png";
	    button_press[7] = "rojo_press.png";
	    sounds[7] = "sonido8.mp3";
	    setImage(mButtons[7],button_idle[7]);
	    mButtons[8] = (ImageView) findViewById(R.id.imageButton9);
	    button_idle[8] = "verde_idle.png";
	    button_press[8] = "verde_press.png";
	    sounds[8] = "sonido9.mp3";
	    setImage(mButtons[8],button_idle[8]);
	    ///Se define el panel de informacion
	    mInformation = (TextView) findViewById(R.id.information);
	    //Se asigna el valor a este panel
	    mInformation.setText("Bienvenido a Simon :)\n Presione el botón del centro para jugar");
	    ///Se crea un nuevo juego y se inicia.
        mGame = new SimonGame();
        startNewGame();
	}
	/*
	 * Esta función se ejecuta la primera vez que se ejecuta el jeugo
	 * */
	@SuppressWarnings("deprecation")
	private void startNewGame(){
		///Se obtiene el tamaño
		total = mGame.getSequenceSize();
		mGame.cleanList();//Se limpia la lista
		///Se obtienen los parametros de la pantalla
		Display display = getWindowManager().getDefaultDisplay();
		Point size = new Point();
	    try {
	        display.getSize(size);
	        width = size.x;
	        height = size.y;
	    } catch (java.lang.NoSuchMethodError ignore) { // Older device
	        width = display.getWidth();
	        height = display.getHeight();
	    }
	    ///Se asignan las imagenes y los listeners a cada boton, al igual que sus propiedades
		for(int i=0; i < mButtons.length; i++){
			//setImage(mButtons[i],button_idle[i]);
			mButtons[i].setEnabled(true);
			mButtons[i].setOnClickListener(new ButtonClickListener(i));
			mButtons[i].setOnTouchListener(new ButtonTouchListener(i));
			mButtons[i].invalidate();
		}
		
		//Se cambia el texto en el panel de información
		mInformation.setText("Bienvenido a quiqueSimon :) \n Presione el botón del centro para iniciar");
		///Se inicializan las variables
		mGameOver=true;
		mCounter = 0;
		mSequenceSize = 0;
	}
	/*
	 * Cada vez que el usuario pierde se llama a esta función
	 * */
	private void restart(){
		total = mGame.getSequenceSize();///Se obtiene el tamaño hasta el cual logro acertar.
		mGame.cleanList();///Se limpian las listas
		//Se definen de nuevo las imagenes.
		for(int i=0; i < mButtons.length; i++){
			setImage(mButtons[i],button_idle[i]);					 
		}
		startSound("mk64loser.wav");///Se inicia el sonido de perdedor.
		///Se actualiza la informacion en el panel de texto
		mInformation.setText("Usted hizo un total de "+Integer.toString(total)+" selecciones correctas\n"+"Presione el botón del centro para seguir divertiendose");
		///Se reinicializan las variables.
		mGameOver=true;
		mCounter = 0;
		mSequenceSize = 0;
	}
	/*
	 * A cada botón se le asigna un listener, que es el encargado de manejar los eventos de click y no click
	 * */
	private class ButtonTouchListener implements View.OnTouchListener{
		int location;
		//ES el botón que fue presionado
		public ButtonTouchListener(int location){   	
    		this.location = location;
    	}
		@Override
		//Cada vez que halla un cambio en el estado del botón.
		public boolean onTouch(View v, MotionEvent event) {
			// TODO Auto-generated method stub
			if(location!=4){///Si no es el botón central
				if(event.getAction() == MotionEvent.ACTION_DOWN) {//Si esta siendo presionado
					setImage(mButtons[location],button_press[location]);//Cambia la imagen a press
					startSound(sounds[location]);//Ejecute el sonido asignado a esta posici[on
				}
				else if(event.getAction() == MotionEvent.ACTION_UP){///Si se esta soltando el botón
					setImage(mButtons[location],button_idle[location]);//Se asigna la imagen de no presionado
	            	try{///Se finaliza el sonido.
	            		mPlayer.stop();
	            		mPlayer.release();
	            	}
	            	catch(IllegalStateException e){
	        			e.printStackTrace();
	            	}
				}
			}
			return false;
		}
		
		
	}
	/*
	 * En esta clase es donde see va a ejecutar la mayor parte del codigo, dado que es el presionado del botón
	 * */
	private class ButtonClickListener implements View.OnClickListener{
    	int location;//Botön presionado.
    	boolean respuesta;///Bool que contiene si el usuario acerto
    	List<Integer> sequence;//Secuencia generada por el computador.
    	public ButtonClickListener(int location){   	///Cuando se crea el listener se le asigna la posicion del botón
    		this.location = location;
    	}
       	//Cada vez que hay un click
    	public void onClick(View view){
    		if(!mGameOver){//Se verifica si hay un juego en transcurso
    			mInformation.setText("Usted está en el nivel: "+Integer.toString(total));
    			if(mGame.getPlayerGuessSize() < mGame.getSequenceSize()){//Se esta recibiendo la respuesta del usuario.
    				respuesta = mGame.addPlayerGuess(location);///Se agrega el guess del usuario, y se comprueba si es correcta
    				if(!respuesta){//Si no es correcta, se va al estado gameover donde se reinicia el juego.
    					mInformation.setText("Usted ha perdido, por favor presione el botön del centro para seguir jugando");
    					restart();
    				}
    			}
    			if((mGame.getPlayerGuessSize()== mGame.getSequenceSize())&&(!mGameOver)){///si no perdio
    				for(int i=0; i < mButtons.length; i++){
    					mButtons[i].setEnabled(false);					 
    				}
    				sequence = mGame.newPosition();///Se genera una nueva posición en la secuencia.
    				for(int i=0; i < sequence.size();++i){///Para cada valor en la secuencia.
    					delayTime = baseDelay*i+delta;///Se define el delay respecto a ahora, cuando se va a alterar la imagen.
    					toChange = mButtons[sequence.get(i)];///Se defin el botón que va a ser cambiado.
    					cola.add(new data(toChange,delayTime,button_press[sequence.get(i)],sounds[sequence.get(i)],true));///Se agrega a la cola este valor que va a ser cambiado.
    					mHandler.postDelayed(r, delayTime);//Se llama al runner con este delay.
    					delayTime+=0.5*baseDelay;///Se hace un offset respecto a este delay para devolver la imagen
    					cola.add(new data(toChange,delayTime,button_idle[sequence.get(i)],sounds[sequence.get(i)],false));
    					mHandler.postDelayed(r, delayTime);
    					mInformation.setText("Usted está en el nivel: "+Integer.toString(total));				 
    				}
    				total = mGame.getSequenceSize();//Se obtiene el total generado hasta ahora.
    				mGame.cleanPlayerList();///Se limpian los guesses del usuario.
    				if(total%5==0){///Cad avez que total se un multiplo de 5 se acelera el juego
    					baseDelay = 8*baseDelay/10;
    				}
    				if(baseDelay<500){//Hasta que la duración sea de 500
    					baseDelay = 500;
    				}
    			}
    			
    		}else{///Si perdio, y se presiono el botón de nuevo juego
    			if(location==4){
    				///Se genera un nuevo juego.
    	    		mInformation.setText("New Game");
    				sequence = mGame.newPosition();
    				startSound("mario02.wav");///Se hace sonar el sonido de nuevo juego.
    				for(int i=0; i < sequence.size();++i){///Para cada botón en la secuencia se hace el mismo proceso descrito en el condicional anterior.
    					toChange = mButtons[sequence.get(i)];
    					delayTime = 2000;
    					cola.add(new data(toChange,delayTime,button_press[sequence.get(i)],sounds[sequence.get(i)],true));
    					mHandler.postDelayed(r, delayTime);
    					delayTime+=0.5*baseDelay;
    					cola.add(new data(toChange,delayTime,button_idle[sequence.get(i)],sounds[sequence.get(i)],false));
    					mHandler.postDelayed(r, delayTime);
    					mInformation.setText("Atento a los botones");;
    				}
    				mSequenceSize = mGame.getSequenceSize();
    				mGameOver = false;
    			}
    		}
    	}
	}
	//Función default
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	///Función para detener el juego
	public void delay(int milliseconds){  
		try {Thread.sleep(milliseconds);
		} 
		catch(Exception e) {
		} 
	}

/*
 * Función que se encarga de asignar una imagen de assets
 * */
	public void setImage(ImageView mImage,String name){
		try{
		    // Se obtiene el inputStream
		    InputStream ims = getAssets().open(name);
		    // Se carga la imagen como un drawable
		    Drawable d = Drawable.createFromStream(ims, null);
		    // Se asigna la imagen
		    mImage.setImageDrawable(d);
		    ///Se definen las propiedades de la imagen
		    mImage.setScaleType(ScaleType.FIT_CENTER); 
		    mImage.invalidate();	
			mImage.setMinimumWidth(width/3);
			mImage.setMaxWidth(width/3);
			mImage.setMinimumHeight(height/4);
			mImage.setMaxHeight(height/4);
		}
		catch(IOException ex){
		    return;
		}
		
	}

	/*
	 * Funciön que se encarga de iniciar un sonido.
	 * */
	private void startSound(String sound){
		try {
			mPlayer = new MediaPlayer();///Se crea un nuevo player
			mAFD = getAssets().openFd(sound);//Se abre el archivo.
			mPlayer.setDataSource(mAFD.getFileDescriptor(),mAFD.getStartOffset(),mAFD.getLength());//Se obtiene la información de este
			mPlayer.prepare();///Se inicializa el player
		    mPlayer.start();///Se inicia el sonido.
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}
	/*
	 * Este es el runnable que se encarga de cambiar la imagen e iniciar/finalizar el sonido.
	 * */
    private Runnable r = new Runnable() {
        public void run() {
            data prueba = cola.remove();//Se obtiene un valor de la cola
            if(cola.isEmpty()){
            	mInformation.setText("Usted está en el nivel: "+Integer.toString(total));
				for(int i=0; i < mButtons.length; i++){
					mButtons[i].setEnabled(true);					 
				}
            }
            setImage(prueba.getButton(),prueba.getImage());///se asigna la imagen
            if(prueba.getStartSound()){//Si se quiere inicia el sonido.
            	prueba.getButton().setEnabled(false);
    			try {///Se inicia el sonio.
    				mPlayer = new MediaPlayer();
					mAFD = getAssets().openFd(prueba.getSound());
	    			mPlayer.setDataSource(mAFD.getFileDescriptor(),mAFD.getStartOffset(),mAFD.getLength());
	    			mPlayer.prepare();
	    		    mPlayer.start();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            }
            else{///Si se quiere detener
            	try{
            		prueba.getButton().setEnabled(true);
            		mPlayer.stop();///Se finaliza el sonido.
            		mPlayer.release();///Se libera la información del sonido
            	}
            	catch(IllegalStateException e){
        			e.printStackTrace();
            	}
            }
        }
     };
     
}