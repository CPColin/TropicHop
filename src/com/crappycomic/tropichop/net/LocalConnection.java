// TropicHop
// Copyright (C) 2017 Colin Bartolome
// Licensed under the GPL. See LICENSE.txt for details.

package com.crappycomic.tropichop.net;

import java.io.*;
import java.util.Set;

import com.crappycomic.tropichop.model.*;
import com.crappycomic.tropichop.view.ViewMessage;

/** A pipe, more or less, for when the {@link Server} and a {@link Client} are in the same VM. */
public class LocalConnection implements ServerSideConnection, ClientSideConnection
{
   private Server server;
   
   private Client client;
   
   public LocalConnection(Server server, Client client)
   {
      this.server = server;
      this.client = client;
   }
   
   @SuppressWarnings("unchecked")
   @Override
   public void sendServerObject(Object object)
   {
      if (object instanceof ClientModel)
      {
         try
         {
            ByteArrayOutputStream out;
            
            new ObjectOutputStream(out = new ByteArrayOutputStream()).writeObject(object);
            
            client.starting();
            client.setModel((ClientModel)new ObjectInputStream(new ByteArrayInputStream(out.toByteArray())).readObject());
         }
         catch (Exception e)
         {
            System.err.println("Exception while serializing ClientModel: " + e.toString());
            e.printStackTrace(System.err);
         }
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
   
   @Override
   public void sendClientObject(Object object)
   {
      if (object instanceof ModelMessage)
         server.receiveMessage(this, (ModelMessage)object);      
   }
   
   @Override
   public void run()
   {
   }
}
