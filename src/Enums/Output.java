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
    ELF("eating %s is full!"),
    NB("Bounds %s does not exists.\n"),
    BL("%s loaded.\n"),
    NVS("%s is not a valid service!\n"),
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
    ALREADY_EXISTS("%s already exists!\n"),
    PRINT_SERVICE("%s: %s (%.0f, %.0f).\n"),
    ESF("eating %s is full!"),
    LSF("lodging %s is full!"),
    NS("No services yet!\n"),
    SA("%s %s added.\n"),
    LSUC("lodging %s is now %s’s home. %s is at home."),
    ISH("That is %s’s home!"),
    IST("Invalid student type!"),
    IS("(%s is not a valid service!"),
    LDNE("lodging %s does not exist!\n"),
    LIF("lodging %s is full!\n"),
    SNAE("%s already exists!\n"),
    SHL("%s has left.\n"),
    NDNE("%s does not exist!\n"),
    NST("No students yet!"),
    NSF("No students from %s!\n"),
    CHL("%s is now at %s."),
    CHLD("%s is now at %s. %s is distracted!"),
    MNAF("Move is not acceptable for %s!"),
    ONE("This order does not exists!"),
    NCSE("%s does not control student entry and exit!"),
    LUIS("%s: %s.");

    String helpMsg;

    Output(String s) {
        helpMsg=s;
    }

    public String getMsg() {
        return helpMsg;
    }
}
