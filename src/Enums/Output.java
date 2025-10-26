package Enums;

public enum Output {
    HELP("help - list all commands"),
    EXIT("Bye!"),
    AT("Already there!"),
    SC("%s %S added."),
    IT("Invalid service type!"),
    IL("Invalid location!"),
    IMP("Invalid menu price!"),
    IRP("Invalid room price!"),
    ITP("Invalid ticket price"),
    IDP("Invalid discount price!"),
    IC("Invalid capacity!"),
    UNKNOWN("Unknown command. Type help to see available commands."),
    UL("Unknown %s!"),
    ALREADY_EXISTS("%s already exists!"),
    PRINT_SERVICE("%s: %s %.0f %.0f.\n"),
    ELF("eating %s is full!"),
    NS("No services yet!\n"),
    SA("%s %s added.\n"),
    LSUC("lodging %s is now %s’s home. %s is at home."),
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
