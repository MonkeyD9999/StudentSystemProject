import java.io.*;
import java.util.Scanner;
import Enums.Output;
import Exceptions.AlreadyExistsObjectException;
import System.*;

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
        long latTop = in.nextInt();
        long latBottom = in.nextInt();
        long longRigth = in.nextInt();
        long longLeft = in.nextInt();
        String name = in.nextLine();

        if(processLoad(name, manager)!=null){
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

    private static void processService(Scanner in, StudentSystemClass manager) throws AlreadyExistsObjectException {
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
            catch (AlreadyExistsObjectException e){
                System.out.printf(Output.ALREADY_EXISTS.getMsg(), name);
            }
        }
    }

    private static void processSave (StudentSystem system){
        try{
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(TRAINS_FILE));
            oos.writeObject(system);
            oos.flush();
            oos.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }


    private static StudentSystem processLoad(){
        try{
            ObjectInputStream in = new ObjectInputStream(new FileInputStream(TRAINS_FILE));
            StudentSystem system = (StudentSystemClass) in.readObject();
            in.close();
            return system;
        } catch (IOException e) {
            return new StudentSystemClass();
        }catch(ClassNotFoundException e){
            throw new RuntimeException(e);
        }
    }

}
