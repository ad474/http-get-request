import java.awt.Desktop;
import java.net.*; 
import java.io.*; 
import java.nio.file.Files;
import java.util.Scanner;

public class Client {
    public static void main ( String[] a) {
        double radius = 10.0; 
        try{
            Socket clients = new Socket("localhost",8000);
            DataInputStream ip = new DataInputStream(clients.getInputStream()); //create an input stream to receive data from the server 
            DataOutputStream op = new DataOutputStream(clients.getOutputStream()); //create an output stream to send data to the server 
            //getting and sending GET command
            System.out.println("Input command:");
            Scanner input=new Scanner(System.in);
            String s=input.nextLine();
            while(true){
                String[] cwords=s.split(" ");
                if(cwords[0].compareToIgnoreCase("Get")==0){
                    break;
                }
                System.out.println("Input again");
                s=input.next();
            }
            //System.out.println(s);
            byte[] sb=new byte[s.length()];
            for (int i = 0; i < s.length(); i++) {
                sb[i]=(byte)s.charAt(i);
            }
            op.write(sb);
            
            //modified
            String statusCode=ip.readUTF();
            if(statusCode.equals("200 OK")){
                System.out.println(statusCode+"\n");
                int count=ip.available();
                if(count==0){
                    while(count==0){
                        count=ip.available();
                    }
                }
                byte[] bs = new byte[count];
                ip.read(bs);
                for (byte b:bs) {
                    char c = (char)b;
                    System.out.print(c);
                }
                System.out.println("");
                String FILEPATH = ""; 
                File file = new File("xyz.html"); 
                Files.write(file.toPath(), bs);
                Desktop.getDesktop().browse(file.toURI());
            }
            else if (statusCode.equals("404 Not found")){
                System.out.println(statusCode+"\n");
            }
            else{
                System.out.println("error");
            }
            
            //receiving file and opening it
            
            //rendering webpage
            
            String bye;
            bye=input.nextLine();
            while(!bye.equalsIgnoreCase("bye")){
                bye=input.next();
            }
            byte[] byeb=new byte[bye.length()];
            for (int i = 0; i < bye.length(); i++) {
                byeb[i]=(byte)bye.charAt(i);
            }
            op.write(byeb);
            clients.close();
        } 
        catch(IOException ex){
            System.err.println(ex);
        } 
    }
}