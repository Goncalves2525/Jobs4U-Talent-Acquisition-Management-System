package tcpMessage;

public enum TcpCode {

    COMMTEST("COMMTEST", 0,
            "Communications test request with no other effect on the server application than the response with an ACK message. This request has no data."),
    DISCONN("DISCONN", 1,
            " End of session request. The server application is supposed to respond with an ACK message, afterwards both client and server applications are expected to close the session (TCP connection). This request has no data."),
    ACK("ACK", 2,
            "Generic acknowledgment and success response message. Used in response to a successful request. This response contains no data."),
    ERR("ERR", 3,
            "Error response message. Used in response to unsuccessful requests that caused an error. This response message may carry a human readable phrase explaining the error."),
    AUTH("AUTH", 4,
            "User authentication request carrying the username in DATA1 and the user’s password in DATA2, both are strings of ASICII codes and are not required to be null terminated. If the authentication is successful, the server application response is ACK, otherwise it’s ERR."),
    LOGOUT("LOGOUT", 5,
            "tbu"),
    REQUEST_CAND_APP_STAT("REQUESTCandidateApplicationStatus", 6,
            "tbu"),
    RESPONSE_CAND_APP_STAT("RESPONSECandidateApplicationStatus", 7,
            "tbu"),
    REQUEST_CUST_ASSIG_JO("REQUESTCustomerAssignedJobOpenings", 8,
            "tbu"),
    RESPONSE_CUST_ASSIG_JO("RESPONSECustomerAssignedJobOpenings", 9,
            "tbu"),
    TESTING("TESTING", 99,
            "Used for testing purposes only, sending any generic message.");

    private final String name;
    private final int code;
    private final String description;

    TcpCode(String name, int code, String description) {
        this.name = name;
        this.code = code;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public int getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public static TcpCode fromCode(int code) {
        for (TcpCode tcpCode : TcpCode.values()) {
            if (tcpCode.getCode() == code) {
                return tcpCode;
            }
        }
        return ERR;
    }
}
