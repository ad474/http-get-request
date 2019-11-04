import java.net.*; 
import java.io.*; 
import java.util.Scanner;

public class Server{
    public static void main(String[] a){
        try{
            ServerSocket serversocket = new ServerSocket(8000);
            System.out.println("Server Started");
            Socket socket = serversocket.accept(); //listen for connection request
            DataInputStream ip = new DataInputStream(socket.getInputStream()); //create data input & output streams – input from client
            DataOutputStream op = new DataOutputStream(socket.getOutputStream()); //create data input & output streams – output to client 
            //read get command
            int count=ip.available();
            if(count==0){
                while(count==0){
                    count=ip.available();
                }
            }
            byte[] bs = new byte[count];
            String command=new String();
            command="";
            ip.read(bs);
            for (byte b:bs) {
                char c = (char)b;
                command+=c;
                //System.out.print(c);
            }
            //System.out.println("");
            //open file
            String[] cwords=command.split(" ");
//            System.out.println("cwords");
//            for (int i = 0; i < cwords.length; i++) {
//                System.out.println(cwords[i]);
//            }
            String filepath=cwords[1];
            File file = new File("/Users/cosentus/Desktop/"+filepath);
            
            String statusCode="";
            if(file.exists()){
                statusCode="200 OK\n";
                byte[] bytesArray = new byte[(int) file.length()+statusCode.length()];
                byte[] fileBytes=new byte[(int) file.length()];
                int i;
                for (i = 0; i < statusCode.length(); i++) {
                    bytesArray[i]=(byte)statusCode.charAt(i);
                }
                FileInputStream fileInputStream = new FileInputStream(file);
                fileInputStream.read(fileBytes);
                //int c=0;
                for (int j = 0; j < fileBytes.length; j++) {
                    bytesArray[j+i]=fileBytes[j];
                }
                op.write(bytesArray);
            }
            else{
                statusCode="404 Not found";
                byte[] bytesArray = new byte[statusCode.length()];
                int i;
                for (i = 0; i < statusCode.length(); i++) {
                    bytesArray[i]=(byte)statusCode.charAt(i);
                }
                op.write(bytesArray);
            }
            
            //reading bye
            String bye="";
            count=ip.available();
            if(count==0){
                while(count==0){
                    count=ip.available();
                }
            }
            byte[] byes = new byte[count];
            ip.read(byes);
            for (byte b:byes) {
                char c = (char)b;
                bye+=c;
            }
            if(bye.equalsIgnoreCase("bye")){
                System.out.println("Socket closed");
                socket.close();
            }
        }
        catch(IOException ex){ 
            System.err.println(ex); 
        }
    }
}