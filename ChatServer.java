
import java.io.*;
import java.net.*;
import java.util.*;

public class ChatServer
{  public static void main(String[] args ) 
   {  
      ArrayList<ChatHandler> AllHandlers = new ArrayList<ChatHandler>();
		
      try 
      {  ServerSocket s = new ServerSocket(1923);
         
         for (;;)
         {  
            Socket incoming = s.accept( );
            new ChatHandler(incoming, AllHandlers).start();
         }   
      }
      catch (Exception e) 
      {  System.out.println(e);
      } 
      
      
   } 
public static StringBuffer chat=new StringBuffer();
}

class ChatHandler extends Thread
{   
    
    
    public ChatHandler(Socket i, ArrayList<ChatHandler> h) 
   { 
 		incoming = i;
		handlers = h;
		handlers.add(this);
		try{
			  in = new ObjectInputStream(incoming.getInputStream());
			  out = new ObjectOutputStream(incoming.getOutputStream());
		}catch(IOException ioe){
        System.out.println("Could not create streams.");
		}
   }
    
	public synchronized void broadcast(){
                
            String message= myObject.getMessage();
            String name=myObject.getName();
           
            
                if (message.equals("Logged in"))
                {
                    Draw cm = new Draw();
                    username=myObject.getName();
                    cm.setName(name);
                    cm.setMessage("Logged in");
                }
                
		ChatHandler left = null;
                     
		
                 
                if((!message.equals("history")) && !(message.equals("userList")) && !(message.equals("board"))){
                    
                    try{
                        ChatServer.chat.append(name);
                        ChatServer.chat.append(":");
                        ChatServer.chat.append(message);
                        ChatServer.chat.append("\n");
                    }catch(Exception e){

                    }
                }
                 
                if(message.equals("history")){
                    
                    System.out.println("Chat.ChatHandler.broadcast()history");
                    Draw cm=new Draw();
                    cm.setName("\n Chat History\n");
                    
                    cm.setMessage(ChatServer.chat.toString());
                        
                    try {
                        out.writeObject(cm);
                    } catch (Exception e) {
                    }
                    return;
                }
                
                if(message.equals("userList")){
                    
                    StringBuffer users=new StringBuffer();
                    
                    for(ChatHandler handler : handlers){
                    System.out.println("Handlers Present: "+handler.username);
                    users.append(handler.username);
                    users.append("\n");
                    }
                     System.out.println("Chat.ChatHandler.broadcast()");
                    
                    Draw cm=new Draw();
                    cm.setName("\n UserList\n");
                    
                    cm.setMessage(users.toString());
                    
                    
                    try {
                        out.writeObject(cm);
                    } catch (Exception e) {
                    }
                    
                    return;
                }
                
                for(ChatHandler handler : handlers){
                    Draw cm = new Draw();
                    
                    cm.setName(myObject.getName()+" :");
                    cm.setMessage(myObject.getMessage());
                    
                    cm.a1 = myObject.geta1();
                    cm.b1 = myObject.getb1();
                    cm.a2 = myObject.geta2();
                    cm.b2 = myObject.getb2();
                    
                    try{
			                  handler.out.writeObject(cm);
			
                    }catch(IOException ioe){
				left = handler; 
			}
		
                }
                
               
                if(myObject.getMessage().equals("bye")){ // my client wants to leave
			done = true;	
			handlers.remove(this);
			System.out.println("Removed handler. Number of handlers: " + handlers.size());
		}
		System.out.println("Number of handlers: " + handlers.size());
                
                handlers.remove(left);
                
   }

   public void run()
   {  
		try{ 	
			while(!done){
				myObject = (Draw)in.readObject();
				System.out.println("Message read: " + myObject.getMessage());
                                
				broadcast();
			}			    
		} catch (IOException e){  
			if(e.getMessage().equals("Connection reset")){
				System.out.println("A client terminated its connection.");
			}else{
				System.out.println("Problem receiving: " + e.getMessage());
			}
		}catch(ClassNotFoundException cnfe){
			System.out.println(cnfe.getMessage());
		}finally{
			handlers.remove(this);
		}
   }
   
   Draw myObject = null;
   private Socket incoming;

   boolean done = false;
   ArrayList<ChatHandler> handlers;
   String username;
   
   
   ObjectOutputStream out;
   ObjectInputStream in;
   
 
}