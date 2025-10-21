import java.util.Scanner;
import Enums.Output;

public class Main {
	
	public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        StudentSystemClass manager = new StudentSystemClass();

        String command;

        do{
            command = in.nextLine();
            processCommand(in, manager, command);
        }
        while(!command.equals(Output.EXIT.getMsg()));
    }

    public static void processCommand(Scanner in, StudentSystemClass manager, String command){

        switch (command.toLowerCase()){
            case "help" -> printHelp();
            case "exit" -> System.out.println(Output.EXIT.getMsg());
            case "bounds" -> processBound(in, manager);
            case "save" -> processSave(manager);
            case "load" -> processLoad(in.nextLine(),manager);
            case "service" -> processService(in, manager);

            default -> System.out.println(Output.UNKNOWN.getMsg());
        }
    }

    private static void printHelp(){

    }

    private static void processBound(Scanner in, StudentSystemClass manager){
        int latTop = in.nextInt();
        int latBottom = in.nextInt();
        int longRigth = in.nextInt();
        int longLeft = in.nextInt();
        String name = in.nextLine();

        if(load(name)!=null){
            System.out.println("Bounds already exists. Please load it!");
        }
        else if(latTop<=latBottom || longRigth<=longLeft ){
            System.out.println("Invalid bounds.");
        }
        else {
            if(manager.getCurrentArea()!=null){
                manager.saveCurrentArea();
            }
            manager.createNewArea(name, latTop, latBottom, longRigth, longLeft);
            System.out.print(name + "created.\n");
        }
    }

    private static void processSave(StudentSystemClass manager){

    }

    private static void processLoad (String areaName, StudentSystemClass manager){


    }

    private static void processService(Scanner in, StudentSystemClass manager) throws DuplicatedObjectException{
        String type = in.next();
        int lat = in.nextInt();
        int lng = in.nextInt();
        int price = in.nextInt();
        int value = in.nextInt();
        String name = in.nextLine().trim();

        if(!type.equals("eating") && !type.equals("lodging") && !type.equals("leisure")){
            System.out.println(Output.IT.getMsg());
        }
        else if(!manager.isLocationInside(lat, lng)){
            System.out.println(Output.IL.getMsg());
        }
        else if(price<=0){
            switch (type){
                case "lodging" -> System.out.println(Output.IRP.getMsg());
                case "eating" -> System.out.println(Output.IMP.getMsg());
                case "leisure" -> System.out.println(Output.ITP.getMsg());
            }
        }
        else if(type.equals("leisure")) {
            if(value<0 || value>100){
                System.out.println(Output.IDP.getMsg());
            }
        }
        else if(value<=0){
                System.out.println(Output.IC.getMsg());
        }
        else{
            try {
                manager.addService(name, value, lat, lng, price);
            }
            catch (DuplicatedObjectException e){
                System.out.printf(Output.ALREADY_EXISTS.getMsg(), name);
            }
        }
    }


}
