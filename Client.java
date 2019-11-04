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
            //receiving file and opening it
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
            //rendering webpage
            String FILEPATH = ""; 
            File file = new File("xyz.html"); 
            Files.write(file.toPath(), bs);
            Desktop.getDesktop().browse(file.toURI());
            
//            try { 
//  
//                // Initialize a pointer 
//                // in file using OutputStream 
//                OutputStream os= new FileOutputStream(file); 
//
//                // Starts writing the bytes in it 
//                os.write(bs); 
//
//                // Close the file 
//                os.close(); 
//            } 
//
//            catch (Exception e) { 
//                System.out.println("Exception: " + e); 
//            } 
//            Desktop desktop= Desktop.getDesktop();
//            desktop.open(file);
//            
            
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