// TropicHop
// Copyright (C) 2017 Colin Bartolome
// Licensed under the GPL. See LICENSE.txt for details.

package com.crappycomic.tropichop.net;

import java.io.IOException;

import com.crappycomic.tropichop.model.ClientModel;

/** The half of a connection on the same side as the {@link ClientModel}. */
public interface ClientSideConnection extends Runnable
{
   void sendClientObject(Object object) throws IOException;
}
