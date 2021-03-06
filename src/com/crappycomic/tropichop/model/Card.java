// TropicHop
// Copyright (C) 2017 Colin Bartolome
// Licensed under the GPL. See LICENSE.txt for details.

package com.crappycomic.tropichop.model;

import java.io.Serializable;
import java.util.*;

/** Represents an {@link Action action} card (i.e., a Red Shift) that can be drawn by a {@link Player player}. */
public class Card implements Serializable
{
   private static final long serialVersionUID = 6562181801249036913L;

   private String id;
   
   private List<Action> actions;
   
   void setID(String id)
   {
      this.id = id;
   }
   
   public String getID()
   {
      return id;
   }
   
   void addAction(Action action)
   {
      if (actions == null)
         actions = new ArrayList<>();
      
      actions.add(action);
   }

   public List<Action> getActions()
   {
      return Collections.unmodifiableList(actions);
   }
}
