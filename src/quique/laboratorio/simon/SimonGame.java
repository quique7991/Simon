package quique.laboratorio.simon;
import java.util.*;  
/*
 * Esta es la clase que se encarga de manejar la lógica del juego simon.
 * */
public class SimonGame {
    private List<Integer> sequence= new ArrayList<Integer>();  //Esta es la lista de valores aleatorios que van a contener la secuencia que se va a desplegar
    private List<Integer> player= new ArrayList<Integer>();  //Esta es la lista de los botones presionados por el usuario, para compararlos con sequence.
    public final static int cantidadBotones = 9; ///Se define la cantidad de botones.
    public final static int botonCentral = 4; ///Se define la posición del botón de inicio, que no se utiliza para jugar.
    private Random mRand; ///Variable aleatoria para generar los valores.
    //Constructor de simon, solo se inicializa la variable aleatoria.
    public SimonGame(){
		mRand = new Random();    	
    }
	/*
	 * Un función get para la variable de cantidad de botones.
	 * */
    public int getButtonSize(){
    	return cantidadBotones;    	
    }
	/*
	 * Un función get para obtener el tamaño de la secuencia hasta este ppunto
	 * */
    public int getSequenceSize(){
    	return sequence.size();    	
    }
	/*
	 * Un función get para la cantidad de valores que ha generado el usuario.
	 * */
    public int getPlayerGuessSize(){
    	return player.size();
    }
	/*
	 * Se limpia la lista del usuario.
	 * */
    public int cleanPlayerList(){
    	player.clear();
    	return 0;
    }
    /*
     * Se limpian ambas listas, cuando el usuario pierde el juego.
     * */
    public void cleanList(){
    	sequence.clear();
    	player.clear();
    }
    /*
     * se agrega una nueva posición a la lista de secuencias.
     * */
    public void addPosition(int number){
    	sequence.add(number);
    }
    /*
     * Se agrega el intento del usuario, y luego se comparan las posiciones para verificiar si fue correcto.
     * */
    public boolean addPlayerGuess(int number){
    	boolean respuesta;
    	player.add(number);
    	respuesta = comparePositions();
    	return respuesta;
    }
    /*
     * Esta función es la que se encarga de comparar los botones presionados por el usuario, y la secuencia.
     * */    
    public boolean comparePositions(){
    	boolean correct = true;
		for(int i=0; i< player.size();i++){///Se recorre cada uno de los guesses delusuario
			if(sequence.get(i)!=player.get(i))///Se compara con la secuencia
				correct = false;//De no ser equivalentes se devuelve false;
		}
    	return correct;
    }
    /*
     * Se genera un nuevo valor aleatorio, que sea distinto al valor del botón de inicio, y que este entre 0 y buttonSize
     * */
    public int randomNumber(){
    	int move = 0;
    	do{
    		move = mRand.nextInt(getButtonSize());
    	}while(move == botonCentral);
    	return move;
    }
    /*
     * Esta es la función que se encarga de agregar un nuevo valor aleatorio a la lista de secuencias, y devuelve la secuencia.
     * */
    public List<Integer> newPosition(){
    	int move = randomNumber();
    	addPosition(move);
    	return sequence;
    }
}