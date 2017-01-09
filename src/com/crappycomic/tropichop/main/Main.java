// TropicHop
// Copyright (C) 2017 Colin Bartolome
// Licensed under the GPL. See LICENSE.txt for details.

package com.crappycomic.tropichop.main;

import java.awt.FlowLayout;
import java.awt.event.*;
import java.io.*;

import javax.swing.*;

import com.crappycomic.tropichop.model.ServerModel;

/** Displays the initial options and hands off control to the appropriate dialog. */
public class Main
{
   public static void main(String[] args)
   {
      System.out.println("TropicHop");
      System.out.println("Copyright (C) 2017 Colin Bartolome");
      System.out.println("Licensed under the GPL. See LICENSE.txt for details.");

      final JFrame frame = new JFrame("TropicHop");
      JButton button;
      
      frame.setLayout(new FlowLayout());
      
      frame.add(button = new JButton("Create a Game"));
      button.addActionListener(new ActionListener()
      {
         @Override
         public void actionPerformed(ActionEvent evt)
         {
            new CreateGameDialog(frame, null);
         }
      });
      
      frame.add(button = new JButton("Join a Game"));
      button.addActionListener(new ActionListener()
      {
         @Override
         public void actionPerformed(ActionEvent evt)
         {
            new JoinGameDialog(frame);
         }
      });
      
      frame.add(button = new JButton("Load a Game"));
      button.addActionListener(new ActionListener()
      {
         @Override
         public void actionPerformed(ActionEvent evt)
         {
            JFileChooser fileChooser = new JFileChooser();
            
            fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            fileChooser.setMultiSelectionEnabled(false);
            fileChooser.showOpenDialog(frame);
            
            File file = fileChooser.getSelectedFile();
            
            if (file != null)
            {
               try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(file)))
               {
                  new CreateGameDialog(frame, (ServerModel)in.readObject());
               }
               catch (Exception e)
               {
                  JOptionPane.showMessageDialog(frame, "Error loading game: " + e.toString(), "Error", JOptionPane.ERROR_MESSAGE);
                  e.printStackTrace();
               }
            }
         }
      });
      
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);      
      frame.pack();
      frame.setLocationRelativeTo(null);
      frame.setVisible(true);
   }
}
