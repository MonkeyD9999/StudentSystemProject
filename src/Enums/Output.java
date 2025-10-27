package Enums;

public enum Output {
    HELP("%s - %s\n"),
    EXIT("Bye!"),
    IB("Invalid bounds."),
    BND("System bounds not defined."),
    BAE("Bounds already exists. Please load it!"),
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
    UL("Unknown %s!\n"),
    ALREADY_EXISTS("%s already exists!\n"),
    PRINT_SERVICE("%s: %s (%.0f, %.0f).\n"),
    PRINT_STUDENT("%s: %s at %s.\n"),
    BOUNDS("%s created.\n"),
    ELF("eating %s is full!"),
    NS("No services yet!\n"),
    SA("%s %s added.\n"),
    IST("Invalid student type!"),
    LDNE("lodging %s does not exist!\n"),
    LIF("lodging %s is full!\n"),
    SHL("%s has left.\n"),
    NDNE("%s does not exist!\n"),
    NST("No students yet!"),
    NSF("No students from %s!\n"),
    NB("Bounds %s does not exists.\n"),
    BL("%s loaded.\n"),
    NVS("%s is not a valid service!\n");

    String helpMsg;

    Output(String s) {
        helpMsg=s;
    }

    public String getMsg() {
        return helpMsg;
    }
}
