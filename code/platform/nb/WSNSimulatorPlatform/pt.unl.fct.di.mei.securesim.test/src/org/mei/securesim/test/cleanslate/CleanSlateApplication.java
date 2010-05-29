/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.mei.securesim.test.cleanslate;

import javax.swing.JOptionPane;
import org.mei.securesim.core.application.Application;
import org.mei.securesim.test.cleanslate.messages.APPMsg;
import org.mei.securesim.test.insens.utils.INSENSConstants;

/**
 *
 * @author Pedro Marques da Silva
 */
public class CleanSlateApplication extends Application{

    @Override
    public void run() {
        final APPMsg aPPMsg = new APPMsg(null);

        if (getNode().isSinkNode()) {
            String option = JOptionPane.showInputDialog("Insert Action:\n" +"\tS - Start\n\tB - Build Routing");
            if (option!= null) {
                option = option.toUpperCase();
                if (option.startsWith("S")) {
                    aPPMsg.setAction(CleanSlateConstants.ACTION_START);
                }
                if (!sendMessage(aPPMsg)) {
                    System.out.println("Não foi enviada a mensagens de start");
                } else {
                    System.out.println("Mensagem enviada");
                }
            }
        }
    }

}
