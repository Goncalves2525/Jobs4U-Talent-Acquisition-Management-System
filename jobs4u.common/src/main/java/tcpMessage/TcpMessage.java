package tcpMessage;

import console.ConsoleUtils;
import textformat.AnsiColor;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class TcpMessage {

    private final static int VERSION = 1;

    static private int[] measureMessagesLengthAndQuantity(ArrayList<String> messages){
        int messagesLength = 0;
        int messagesQuantity = 0;
        for(String message : messages) {
            messagesLength = messagesLength + message.length();
            messagesQuantity++;
        }
        return new int[]{messagesLength,messagesQuantity};
    }

    static private byte[] buildTcpMessage(ArrayList<String> messages, int[] messagesLengthAndQuantity, int code) {
        // 2 bytes for version and code + x bytes for the complete message length + 2 bytes for each message on the list + 2 bytes for closing
        int size = 2 + messagesLengthAndQuantity[0] + (messagesLengthAndQuantity[1] * 2) + 2;
        byte[] messageBytes = new byte[size];
        messageBytes[0] = (byte) VERSION;
        messageBytes[1] = (byte) code;
        int idx = 1;
        int dataX_len_l = 4;
        int dataX_len_m = 0;
        for(String message : messages) {
            dataX_len_m = message.length();
            messageBytes[idx + 1] = (byte) dataX_len_l;
            messageBytes[idx + 2] = (byte) dataX_len_m;
            byte[] currentMessage = message.getBytes();
            System.arraycopy(currentMessage, 0, messageBytes, dataX_len_l, message.length());
            idx = idx + 2 + dataX_len_m;
            dataX_len_l = idx + 3;
        }
        if(messagesLengthAndQuantity[1] == 0) {
            messageBytes[2] = 0;
            messageBytes[3] = 0;
        } else {
            messageBytes[idx + 1] = 0;
            messageBytes[idx + 2] = 0;
        }
        //System.out.println(Arrays.toString(messageBytes)); // [TESTING ONLY]
        return messageBytes;
    }

    static public int[] readTcpMessageHeader(InputStream inputStream) throws IOException {

        // Init and collect header info [4] Bytes
        int[] headerInfo = {0,0,0,0};
        byte[] header = new byte[4];
        int headerBytes = inputStream.read(header);
        //System.out.println(Arrays.toString(header)); // [TESTING ONLY]

        if (headerBytes == -1) {
            ConsoleUtils.showMessageColor("Connection lost.", AnsiColor.RED);
            return headerInfo;
        }

        if (headerBytes != 4) {
            ConsoleUtils.showMessageColor("Invalid header size\nClosing connection.", AnsiColor.RED);
            return headerInfo;
        }

        // Use of bitwise operation to handle both signed and unsigned contents
        headerInfo[0] = header[0] & 0xFF;      // version
        headerInfo[1] = header[1] & 0xFF;      // code
        headerInfo[2] = header[2] & 0xFF;      // data1_len_l
        headerInfo[3] = header[3] & 0xFF;      // data1_len_m

        return headerInfo;
    }

    static public ArrayList<String> readTcpMessageContent(InputStream inputStream, int data1_len_l, int data1_len_m) throws IOException{

        // Init variables
        ArrayList<String> messagesRead = new ArrayList<>();
        int startDataX = data1_len_l;
        int endDataX = data1_len_m;

        // Go through the message until protocol double zero bytes
        while (startDataX != 0 && endDataX != 0) {

            // Read current data chunk
            byte[] data = new byte[endDataX];
            int dataBytes = inputStream.read(data);
            //System.out.println(data.toString().getBytes()); // [TESTING ONLY]

            if (dataBytes != endDataX) {
                throw new IOException("Invalid data length");
            } else {
                // Convert the byte array to a string and add to messages ArrayList
                String dataString = new String(data);
                //System.out.println(dataString); // [TESTING ONLY]
                messagesRead.add(dataString);
            }

            // Move to next data chunk
            byte[] nextDataX = new byte[2];
            dataBytes = inputStream.read(nextDataX);
            startDataX = nextDataX[0] & 0xFF;
            endDataX = nextDataX[1] & 0xFF;

            // Stop if the next data chunk is missing 2 bytes as expected
            if(dataBytes < 2) {
                break;
            }
        }
        return messagesRead;
    }

    static public void printMessages(ArrayList<String> genericMessages) {
        for(String genericMesssage : genericMessages) {
            System.out.println(genericMesssage);
        }
    }

    static public void printErrorMessages(ArrayList<String> messages) {
        for(String messsage : messages) {
            ConsoleUtils.showMessageColor(messsage, AnsiColor.RED);
        }
    }

    static public byte[] buildTcpMessageTESTING(ArrayList<String> messages){
        // Get full messages length
        int[] messagesLengthAndQuantity = measureMessagesLengthAndQuantity(messages);
        // Construct the full message array
        return buildTcpMessage(messages, messagesLengthAndQuantity, TcpCode.TESTING.getCode());
    }

    static public byte[] buildTcpMessageCOMMTEST(){
        ArrayList<String> messages = new ArrayList<>();
        int[] messagesLengthAndQuantity = new int[]{0,0};
        // Construct the full message array
        return buildTcpMessage(messages, messagesLengthAndQuantity, TcpCode.COMMTEST.getCode());
    }

    static public byte[] buildTcpMessageDISCONN(){
        ArrayList<String> messages = new ArrayList<>();
        int[] messagesLengthAndQuantity = new int[]{0,0};
        // Construct the full message array
        return buildTcpMessage(messages, messagesLengthAndQuantity, TcpCode.DISCONN.getCode());
    }

    static public byte[] buildTcpMessageACK(){
        ArrayList<String> messages = new ArrayList<>();
        int[] messagesLengthAndQuantity = new int[]{0,0};
        // Construct the full message array
        return buildTcpMessage(messages, messagesLengthAndQuantity, TcpCode.ACK.getCode());
    }

    static public byte[] buildTcpMessageERR(ArrayList<String> messages){
        // Get full messages length
        int[] messagesLengthAndQuantity = measureMessagesLengthAndQuantity(messages);
        // Construct the full message array
        return buildTcpMessage(messages, messagesLengthAndQuantity, TcpCode.ERR.getCode());
    }

    static public byte[] buildTcpMessageAUTH(ArrayList<String> messages){
        // Get full messages length
        int[] messagesLengthAndQuantity = measureMessagesLengthAndQuantity(messages);
        // Construct the full message array
        return buildTcpMessage(messages, messagesLengthAndQuantity, TcpCode.AUTH.getCode());
    }

    static public byte[] buildTcpMessageLOGOUT(){
        ArrayList<String> messages = new ArrayList<>();
        int[] messagesLengthAndQuantity = new int[]{0,0};
        // Construct the full message array
        return buildTcpMessage(messages, messagesLengthAndQuantity, TcpCode.LOGOUT.getCode());
    }

    static public byte[] buildTcpMessageREQUESTCandidateApplicationStatus(){
        ArrayList<String> messages = new ArrayList<>();
        int[] messagesLengthAndQuantity = new int[]{0,0};
        // Construct the full message array
        return buildTcpMessage(messages, messagesLengthAndQuantity, TcpCode.REQUEST_CAND_APP_STAT.getCode());
    }

    static public byte[] buildTcpMessageRESPONSECandidateApplicationStatus(ArrayList<String> messages){
        // Get full messages length
        int[] messagesLengthAndQuantity = measureMessagesLengthAndQuantity(messages);
        // Construct the full message array
        return buildTcpMessage(messages, messagesLengthAndQuantity, TcpCode.RESPONSE_CAND_APP_STAT.getCode());
    }

    static public byte[] buildTcpMessageREQUESTCustomerAssignedJobOpenings(){
        ArrayList<String> messages = new ArrayList<>();
        int[] messagesLengthAndQuantity = new int[]{0,0};
        // Construct the full message array
        return buildTcpMessage(messages, messagesLengthAndQuantity, TcpCode.REQUEST_CUST_ASSIG_JO.getCode());
    }

    static public byte[] buildTcpMessageRESPONSECustomerAssignedJobOpenings(ArrayList<String> messages){
        // Get full messages length
        int[] messagesLengthAndQuantity = measureMessagesLengthAndQuantity(messages);
        // Construct the full message array
        return buildTcpMessage(messages, messagesLengthAndQuantity, TcpCode.RESPONSE_CUST_ASSIG_JO.getCode());
    }
}
