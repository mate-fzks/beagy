
import java.io.*; 
import java.net.*;

public class Server extends Thread {
    private ServerSocket serverSocket = null;
    private Socket socket = null;
    private ObjectOutputStream outStream = null;
    private ObjectInputStream inStream = null;
    private volatile boolean rx_ON = false;
    private boolean isserver = false;
    public VEHICLE v1 = null;
    public VEHICLE v2 = null;
    
    public Server()
    {
    }    
// server start
    public boolean StartServer() {

        System.out.println("Start server");

        try {
            serverSocket = new ServerSocket(7777);
            socket = serverSocket.accept();
            inStream = new ObjectInputStream(socket.getInputStream());
            outStream = new ObjectOutputStream(socket.getOutputStream());
            //TODO START GAME
            outStream.flush();
            
            System.out.println("Connected");
            isserver = true;
            return isserver;
        } catch (SocketTimeoutException s) {
            System.out.println("Socket timed out!");
            isserver = false;
            return isserver;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
    public void SendDatatoClient(VEHICLE Vehicle) {

        System.out.println("Vehicle to be written = " + Vehicle);

        try {
            outStream.reset();
            outStream.writeObject(Vehicle);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void receive() {

        try {
        	this.v2 = (VEHICLE) inStream.readObject();
            System.out.println("Object received");
            
//            System.out.println(arena_rx);
//            System.out.println(arena_rx.getPlayer().getAlcoholLevel());

        } catch (IOException e) {
            e.printStackTrace();
            this.stop_receive();
            this.StopServer();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        
    }
    public void receive_loop() {

        while (rx_ON == true) {
            receive();
        }
        this.StopServer();

    }
    public void start_receive() {

        rx_ON = true;
    }       
    public void stop_receive() {

        rx_ON = false;
    }
   
    public void StopServer() {

        System.out.println("Stop server");

        try {
            serverSocket.close();
            socket.close();
            System.out.println("Server stopped");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public VEHICLE getVehicle2()
    {
    	return this.v2;
    }
    
}

	

