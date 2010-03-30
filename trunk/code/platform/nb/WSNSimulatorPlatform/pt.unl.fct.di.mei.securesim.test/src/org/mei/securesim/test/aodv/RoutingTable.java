/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.mei.securesim.test.aodv;

import java.util.List;

/**
 *
 * @author posilva
 */
class RoutingTable {
//Destination IP Address
//   -  Destination Sequence Number
//   -  Valid Destination Sequence Number flag
//   -  Other state and routing flags (e.g., valid, invalid, repairable,
//      being repaired)
//   -  Network Interface
//   -  Hop Count (number of hops needed to reach destination)
//   -  Next Hop
//   -  List of Precursors (described in Section 6.2)
//   -  Lifetime (expiration or deletion time of the route)


    class RoutingTableEntry{
        int destinationID;
        boolean validDestinationSeqNum;
        int routingState;
        int hopCount;
        List listOfPrecursors;
        long lifeTime;

        public int getDestinationID() {
            return destinationID;
        }

        public void setDestinationID(int destinationID) {
            this.destinationID = destinationID;
        }

        public int getHopCount() {
            return hopCount;
        }

        public void setHopCount(int hopCount) {
            this.hopCount = hopCount;
        }

        public long getLifeTime() {
            return lifeTime;
        }

        public void setLifeTime(long lifeTime) {
            this.lifeTime = lifeTime;
        }

        public List getListOfPrecursors() {
            return listOfPrecursors;
        }

        public void setListOfPrecursors(List listOfPrecursors) {
            this.listOfPrecursors = listOfPrecursors;
        }

        public int getRoutingState() {
            return routingState;
        }

        public void setRoutingState(int routingState) {
            this.routingState = routingState;
        }

        public boolean isValidDestinationSeqNum() {
            return validDestinationSeqNum;
        }

        public void setValidDestinationSeqNum(boolean validDestinationSeqNum) {
            this.validDestinationSeqNum = validDestinationSeqNum;
        }

    }
}
