// TropicHop
// Copyright (C) 2017 Colin Bartolome
// Licensed under the GPL. See LICENSE.txt for details.

package com.crappycomic.tropichop.net;

import java.io.*;
import java.net.Socket;
import java.util.Set;

import com.crappycomic.tropichop.model.*;
import com.crappycomic.tropichop.view.ViewMessage;

/** Represents the client half of a direct connection via TCP/IP. */
public class DirectClientSideConnection implements ClientSideConnection
{
   private Client client;
   
   private ObjectInputStream in;
   
   private ObjectOutputStream out;
   
   private Thread readThread;
   
   DirectClientSideConnection(Client client, Socket socket) throws IOException
   {
      this.client = client;
      out = new ObjectOutputStream(socket.getOutputStream());
      in = new ObjectInputStream(socket.getInputStream());
   }
   
   @Override
   public void sendClientObject(Object object) throws IOException
   {
      try
      {
         System.out.println("Client sending: " + object);
         out.writeObject(object);
         out.flush();
         System.out.println("Sent!");
      }
      catch (IOException ioe)
      {
         readThread.interrupt();
         throw ioe;
      }
   }
   
   @SuppressWarnings("unchecked")
   @Override
   public void run()
   {
      readThread = Thread.currentThread();
      
      while (true)
      {
         Object object;
         
         try
         {
            object = in.readObject();
         }
         catch (Exception e)
         {
            break;
         }
         
         if (Thread.interrupted())
            break;
         
         System.out.println("Client received: " + object);
         
         if (object instanceof ClientModel)
         {
            client.starting();
            client.setModel((ClientModel)object);
         }
         else if (object instanceof ViewMessage)
         {
            client.receiveMessage((ViewMessage)object);
         }
         else if (object instanceof Set<?>)
         {
            client.receivePlayers((Set<Integer>)object);
         }
      }
   }
}
