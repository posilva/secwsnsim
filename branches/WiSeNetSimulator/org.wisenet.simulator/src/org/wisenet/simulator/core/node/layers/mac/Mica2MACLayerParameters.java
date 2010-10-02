/*
 *  Wireless Sensor Network Simulator
 *  The next generation for WSN Simulations
 */
package org.wisenet.simulator.core.node.layers.mac;

/**
 *
 * @author Pedro Marques da Silva <MSc Student @di.fct.unl.pt>
 */
class Mica2MACLayerParameters extends MACLayerParameters {

    public Mica2MACLayerParameters() {
        super();
    }

    @Override
    protected void setSupportedParameters() {
        super.setSupportedParameters();
        // //////////////////////////////
        // MAC layer specific constants
        // //////////////////////////////
        /** The constant component of the time spent waiting before a transmission. */
        init("sendMinWaitingTime", (int) 200);
        /** The variable component of the time spent waiting before a transmission. */
        init("sendRandomWaitingTime", (int) 128);
        /** The constant component of the backoff time. */
        init("sendMinBackOffTime", (int) 100);
        /** The variable component of the backoff time. */
        init("sendRandomBackOffTime", (int) 30);
        /** The time of one transmission in 1/{@link Simulator#ONE_SECOND} second. */
        init("sendTransmissionTime", (int) 960);
        // //////////////////////////////
        // EVENTS
        // //////////////////////////////
        /**
         * The constant self noise level. See either the {@link Mica2MACLayer#calcSNR}
         * or the {@link Mica2MACLayer#isChannelFree} function.
         */
        init("noiseVariance", (double) 0.025);
        /**
         * The maximum noise level that is allowed on sending. This is actually a
         * multiplicator of the {@link Mica2MACLayer#noiseVariance}.
         */
        init("maxAllowedNoiseOnSending", (double) 5.0);
        /** The minimum signal to noise ratio required to spot a message in the air. */
        init("receivingStartSNR", (double) 5.0);
        /**
         * The maximum signal to noise ratio below which a message is marked
         * corrupted.
         */
        init("corruptionSNR", (double) 2.0);

    }
}
