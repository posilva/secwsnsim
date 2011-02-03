/*
 *  Wireless Sensor Network Simulator
 *  The next generation for WSN Simulations
 */
package org.wisenet.simulator.core.energy;

import org.wisenet.simulator.common.ObjectParameter;
import org.wisenet.simulator.common.ObjectParameters;

/**
 *
 * @author Pedro Marques da Silva <MSc Student @di.fct.unl.pt>
 */
public class EnergyModelParameters extends ObjectParameters {

    @Override
    protected void setSupportedParameters() {
        /**
         * Valores Baseados em
         */
        parameters.put("totalEnergy", new ObjectParameter("totalEnergy", "Total energy (Joules)", 9360.0, true));
        // baseado no paper 3 - Evaluation of security Mechanisms  in WSN // skipjack
        parameters.put("encryptEnergy", new ObjectParameter("encryptEnergy", "Encrypt energy (Joules/Byte)", 0.000001788, true));
        parameters.put("decryptEnergy", new ObjectParameter("decryptEnergy", "Decrypt energy (Joules/Byte)", 0.000001788, true));
        //Energy Analysis of public key cryptography for WSN PAPER 2 SHA1
        parameters.put("digestEnergy", new ObjectParameter("digestEnergy", "Digest energy (Joules/Byte)", 0.0000059, true));
        parameters.put("signatureEnergy", new ObjectParameter("signatureEnergy", "Sign energy (Joules/Byte)", 0.0000059, true));
        parameters.put("verifyDigestEnergy", new ObjectParameter("verifyDigestEnergy", "Verify Digest energy (Joules/Byte)", 0.0000059, true));
        parameters.put("verifySignatureEnergy", new ObjectParameter("verifySignatureEnergy", "Verify signature energy (Joules/Byte)", 0.0000059, true));
        parameters.put("cpuTransitionToActiveEnergy", new ObjectParameter("cpuTransitionToActiveEnergy", "CPU Transition to ON energy (Joules)", 0.000000001, true));
        parameters.put("txTransitionToActiveEnergy", new ObjectParameter("txTransitionToActiveEnergy", "Transciever Transition to ON energy (Joules)", 0.000000002, true));
        parameters.put("transmissionEnergy", new ObjectParameter("transmissionEnergy", "Transmission energy (Joules/Byte)", 0.0000592, true));
        parameters.put("receptionEnergy", new ObjectParameter("receptionEnergy", "Reception energy (Joules/Byte)", 0.0000286, true));
        parameters.put("idleEnergy", new ObjectParameter("idleEnergy", "Idle State energy (Joules)", 0.0000059, true));
        parameters.put("sleepEnergy", new ObjectParameter("sleepEnergy", "Sleep State energy (Joules)", 0.0000075, true));
        parameters.put("processingEnergy", new ObjectParameter("processingEnergy", "Simple processing energy (Joules)", 0.0138, true));

    }
}
