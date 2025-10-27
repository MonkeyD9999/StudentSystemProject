package Enums;

public enum Output {
    HELP("%s - %s\n"),
    EXIT("Bye!"),
    AT("Already there!"),
    IB("Invalid bounds."),
    BND("System bounds not defined."),
    BAE("Bounds already exists. Please load it!"),
    PRINT_STUDENT("%s: %s at %s.\n"),
    BOUNDS("%s created.\n"),
    ELF("eating %s is full!\n"),
    NB("Bounds %s does not exists.\n"),
    BL("%s loaded.\n"),
    NVS("%s is not a valid service!\n"),
    SC("%s added.\n"),
    IT("Invalid service type!"),
    IL("Invalid location!"),
    IMP("Invalid menu price!"),
    IRP("Invalid room price!"),
    ITP("Invalid ticket price"),
    IDP("Invalid discount price!"),
    IC("Invalid capacity!"),
    IEV("Invalid evaluation!"),
    UNKNOWN("Unknown command. Type help to see available commands."),
    UL("Unknown %s!\n"),
    ALREADY_EXISTS("%s already exists!\n"),
    PRINT_SERVICE("%s: %s (%d, %d).\n"),
    ESF("eating %s is full!\n"),
    LSF("lodging %s is full!\n"),
    NS("No services yet!\n"),
    NIS("No services in the system."),
    SA("%s %s added.\n"),
    LSUC("lodging %s is now %s’s home. %s is at home.\n"),
    ISH("That is %s’s home!\n"),
    IST("Invalid student type!"),
    IS("(%s is not a valid service!\n"),
    LDNE("lodging %s does not exist!\n"),
    LIF("lodging %s is full!\n"),
    SNAE("%s already exists!\n"),
    SHL("%s has left.\n"),
    NDNE("%s does not exist!\n"),
    NST("No students yet!"),
    NSF("No students from %s!\n"),
    CHL("%s is now at %s.\n"),
    CHLD("%s is now at %s. %s is distracted!\n"),
    MNAF("Move is not acceptable for %s!\n"),
    ONE("This order does not exists!"),
    NCSE("%s does not control student entry and exit!\n"),
    LUIS("%s: %s.\n"),
    LSBR("%s: %d\n"),
    SLOC("%s is at %s %s (%d, %d).\n"),
    THR("%s is thrifty!\n"),
    NVL("%s has not visited any locations!"),
    EVAL("Your evaluation has been registered!"),
    SORD("Services sorted in descending order");

    String helpMsg;

    Output(String s) {
        helpMsg=s;
    }

    public String getMsg() {
        return helpMsg;
    }
}
