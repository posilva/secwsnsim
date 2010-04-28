/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.mei.securesim.test.insens;

import org.mei.securesim.core.application.Application;
import org.mei.securesim.test.insens.messages.APPMsg;
import org.mei.securesim.test.insens.utils.INSENSConstants;

/**
 *
 * @author pedro
 */
public class INSENSApplication extends Application{

    @Override
    public void run() {
        final APPMsg aPPMsg = new APPMsg(null);
        aPPMsg.setAction(INSENSConstants.ACTION_START);
        if (!sendMessage( aPPMsg))
            System.out.println("Não foi enviada a mensagens de start");
        else
            System.out.println("Mensagem enviada");
    }

}
