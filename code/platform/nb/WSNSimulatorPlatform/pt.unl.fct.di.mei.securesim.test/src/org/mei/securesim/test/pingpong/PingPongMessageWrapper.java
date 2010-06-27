/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.mei.securesim.test.pingpong;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.mei.securesim.core.engine.BaseMessage;
import org.mei.securesim.utils.DataUtils;

/**
 *
 * @author posilva
 */
public  class PingPongMessageWrapper {

        int type;
        long id;
        int source;
        int dest;
        long replyId;

        public int getDest() {
            return dest;
        }

        public long getId() {
            return id;
        }

        public long getReplyId() {
            return replyId;
        }

        public int getSource() {
            return source;
        }

        public int getType() {
            return type;
        }

        public void setDest(int dest) {
            this.dest = dest;
        }

        public void setId(long id) {
            this.id = id;
        }

        public void setReplyId(long replyId) {
            this.replyId = replyId;
        }

        public void setSource(int source) {
            this.source = source;
        }

        public void setType(int type) {
            this.type = type;
        }

        public PingPongMessageWrapper() {
        }

        public void wrap(BaseMessage m) {
            try {
                DataInputStream dis = DataUtils.createDataFromByteArray(m.getPayload());
                type = dis.readInt();
                id = dis.readLong();
                source = dis.readInt();
                dest = dis.readInt();
                replyId = dis.readLong();
            } catch (IOException ex) {
                Logger.getLogger(PingPongApplication.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        public byte[] createPayload(int type, long id, int source, int dest, long  replyId) {
            this.type = type;
            this.id = id;
            this.source = source;
            this.dest = dest;
            this.replyId = replyId;
            return createpayload();
        }

        public byte[] createpayload() {
            try {
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                DataOutputStream dos = new DataOutputStream(bos);
                dos.writeInt(type);
                dos.writeLong(id);
                dos.writeInt(source);
                dos.writeInt(dest);
                dos.writeLong(replyId);
                dos.flush();
                return bos.toByteArray();
            } catch (IOException ex) {
                Logger.getLogger(PingPongApplication.class.getName()).log(Level.SEVERE, null, ex);
            }
            return null;
        }
    }
