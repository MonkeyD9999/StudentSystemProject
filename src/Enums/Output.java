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
    ALREADY_EXISTS("%s already exists!"),
    PRINT_SERVICE("%s: %s %.0f %.0f.\n"),
    NS("No services yet!\n"),
    SA("%s %s added.\n"),
    IST("Invalid student type!"),
    LDNE("lodging %s does not exist!\n"),
    LIF("lodging %s is full!\n"),
    SNAE("%s already exists!\n"),
    SHL("%s has left.\n"),
    NDNE("%s does not exist!\n"),
    NST("No students yet!"),
    NSF("No students from %s!\n");

    String helpMsg;

    Output(String s) {
        helpMsg=s;
    }

    public String getMsg() {
        return helpMsg;
    }
}
