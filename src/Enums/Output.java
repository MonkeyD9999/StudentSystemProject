package Enums;

public enum Output {
    HELP("help - list all commands"),
    EXIT("Bye!"),
    SC("%s %S added."),
    IT("Invalid service type!"),
    IL("Invalid location!"),
    IMP("Invalid menu price!"),
    IRP("Invalid room price!"),
    ITP("Invalid ticket price"),
    IDP("Invalid discount price!"),
    IC("Invalid capacity!"),
    UNKNOWN("Unknown command. Type help to see available commands."),
    ALREADY_EXISTS("%s already exists!"),;

    String helpMsg;

    Output(String s) {
        helpMsg=s;
    }

    public String getMsg() {
        return helpMsg;
    }
}
