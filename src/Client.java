import java.io.*; 
import java.net.*;
  

public class Client {
    private Socket socket = null;
    private ObjectInputStream inStream = null;
    private ObjectOutputStream outStream = null;
    private boolean isConnected = false;
    private volatile boolean rx_ON = false;
    private boolean isClient = false;
    public VEHICLE v1 = null;
    public VEHICLE v2 = null;
    private String ipaddress = null;

    public Client(String ipaddress) {
    	this.ipaddress = ipaddress;
    }


    public boolean ConnectToServer() {

        while (!isConnected) try {
            System.out.println("Try to connect to:" + ipaddress);
            socket = new Socket(ipaddress, 7777);
            socket.setSoTimeout(10000);

            isConnected = true;

            outStream = new ObjectOutputStream(socket.getOutputStream());
            outStream.flush();

            inStream = new ObjectInputStream(socket.getInputStream());
            System.out.println("Connected!");
            return isClient = true;
        } catch (SocketTimeoutException s) {
            System.out.println("Socket timed out!");
            return isClient = false;
        } catch (SocketException se) {
            se.printStackTrace();
            return isClient = false;
        } catch (IOException e) {
            e.printStackTrace();
            return isClient = false;
        }
        return isClient = false;
    }

    public void Disconnect() {

        try {
            System.out.println("Try to close socket, HOST:" + socket.getInetAddress());
            outStream.close();
            isConnected = false;
            socket.close();
            System.out.println("Success!");


        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void SendDatatoServer(VEHICLE vehicle) {


        System.out.println("Vehicle to be written = " + vehicle);

        try {

            outStream.reset();
            outStream.writeObject(vehicle);

        } catch (IOException e) {
        	
            this.stop_receive();
            this.Disconnect();
            e.printStackTrace();
        }


    }


    public void receive() {

        try {
        	this.v2 = (VEHICLE) inStream.readObject();
            //controller.setArena_rx(arena_rx);
            System.out.println("Object received");
//            System.out.println(arena_rx);
//            System.out.println(arena_rx.getPlayer().getAlcoholLevel());

        } catch (IOException e) {
            this.stop_receive();
            this.Disconnect();

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void receive_loop() {

        while (rx_ON == true) {
            receive();
        }
        this.Disconnect();
    }

    public void start_receive() {

        rx_ON = true;
    }

    public void stop_receive() {

        rx_ON = false;

    }
    public VEHICLE getVehicle2()
    {
    	return this.v2;
    }
}

