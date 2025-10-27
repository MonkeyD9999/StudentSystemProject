import java.io.*;
import java.util.Scanner;
import Enums.Output;
import Exceptions.*;
import System.*;
import dataStructures.*;

public class Main {

    private static final String AS="%s saved.\n";
	
	public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        StudentSystemClass manager = new StudentSystemClass();

        String command;
        do{
            command = in.next();
            processCommand(in, manager, command);
        }
        while(!command.equals(Output.EXIT.getMsg()));
    }

    public static void processCommand(Scanner in, StudentSystemClass manager, String command){

        switch (command.toLowerCase().trim()){
            case "help" -> printHelp();
            case "exit" -> System.out.println(Output.EXIT.getMsg());
            case "bounds" -> processAddBound(in, manager);
            case "save" -> processSave(manager);
            case "load" -> processLoad(in.nextLine(),manager);
            case "service" -> processAddService(in, manager);
            case "services" -> listServices(manager);
            case "student" -> processAddStudent(in, manager);
            case "leave" -> processRemoveStudent(in.nextLine(), manager);
            case "students" -> listStudents(in.nextLine(), manager);
            case "go" -> changeLocation(in, manager);
            case "move" -> changeLodge(in, manager);
            case "users" -> listUsersInService(in, manager);

            default -> System.out.println(Output.UNKNOWN.getMsg());
        }
    }

    private static void printHelp(){

    }

    private static void processSave (StudentSystem system){
        try{
            String fileName = "AREA_"+system.getCurrentArea().getName();
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName));
            oos.writeObject(system);
            oos.flush();
            oos.close();
            System.out.printf(AS, system.getCurrentArea().getName());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }


    private static StudentSystem processLoad(String fileName, StudentSystemClass manager){
        try{
            ObjectInputStream in = new ObjectInputStream(new FileInputStream(fileName));
            StudentSystem system = (StudentSystemClass) in.readObject();
            in.close();
            return system;
        } catch (IOException e) {
            return new StudentSystemClass();
        }catch(ClassNotFoundException e){
            throw new RuntimeException(e);
        }
    }

    private static void processAddBound(Scanner in, StudentSystemClass manager){
        long latMax = in.nextLong();
        long latMin = in.nextLong();
        long longMax = in.nextLong();
        long longMin = in.nextLong();
        String name = in.nextLine();

        //if(processLoad(name, manager)!=null){
        //    System.out.println("Bounds already exists. Please load it!");
        //}
        //else
        if(latMax<=latMin || longMax<=longMin ){
            System.out.println("Invalid bounds.");
        }
        else {
            if(manager.getCurrentArea()!=null){
                processSave(manager);
            }
            manager.createNewArea(name, latMax, latMin, longMax, longMin);
            System.out.print(name + " created.\n");
        }
    }

    private static void processAddService(Scanner in, StudentSystemClass manager) throws AlreadyExistsObjectException {
        String type = in.next();
        long lat = in.nextLong();
        long lng = in.nextLong();
        int price = in.nextInt();
        int value = in.nextInt();
        String name = in.nextLine().trim();

        System.out.println("ok");

        //check invalid type
        if(!type.equals("eating") && !type.equals("lodging") && !type.equals("leisure")){
            System.out.println(Output.IT.getMsg());
        }
        //check valid location
        else if(!manager.isLocationInside(lat, lng)){
            System.out.println(Output.IL.getMsg());
        }
        //check if valid price
        else if(price<=0){
            switch (type){
                case "lodging" -> System.out.println(Output.IRP.getMsg());
                case "eating" -> System.out.println(Output.IMP.getMsg());
                case "leisure" -> System.out.println(Output.ITP.getMsg());
            }
        }
        //if leisure check discount
        else if(type.equals("leisure") && (value<0 || value>100)) {
            System.out.println(Output.IDP.getMsg());
        }
        // else eat/lodge check valid capacity
        else if(value<=0 && (type.equals("eating") || type.equals("lodging"))){
                System.out.println(Output.IC.getMsg());
        }
        // try to add service
        else{
            try {
                manager.addService(type, name, value, lat, lng, price);
                System.out.printf(Output.SA.getMsg(), type, name);
            }
            catch (AlreadyExistsObjectException e){
                System.out.printf(Output.ALREADY_EXISTS.getMsg(), e.getMessage());
            }
        }
    }

    private static void listServices (StudentSystemClass manager){
        Iterator<Service> it = manager.getCurrentArea().getServices().iterator();
        //check if empty
        if(!it.hasNext()){
            System.out.printf(Output.NS.getMsg());
        }
        while(it.hasNext()){
            Service s = it.next();
            System.out.printf(Output.PRINT_SERVICE.getMsg(), s.getName(), s.getType(), s.getLocation().getLatitude(), s.getLocation().getLongitude());
        }
    }

    private static void processAddStudent(Scanner in, StudentSystemClass manager){
        String type = in.nextLine();
        String name = in.nextLine();
        String country = in.nextLine();
        String currentLodge = in.nextLine();

        if(!type.equals("bookish") && !type.equals("outgoing") && !type.equals("thrifty")){
            System.out.println(Output.IST.getMsg());
        }
        try{
            manager.addStudent(type, name, country, currentLodge);
            System.out.printf(Output.SA.getMsg(), name);
        }
        catch (Error1Exception e){
            System.out.printf(Output.LDNE.getMsg(), e.getMessage());
        }
        catch (Error2Exception e){
            System.out.printf(Output.LIF.getMsg(), e.getMessage());
        }
        catch (Error3Exception e){
            System.out.printf(Output.SNAE.getMsg(), e.getMessage());
        }
    }

    private static void processRemoveStudent(String name, StudentSystemClass manager){
        try{
            manager.removeStudent(name);
            System.out.printf(Output.SHL.getMsg(), name);
        }
        catch (Error1Exception e){
            System.out.printf(Output.NDNE.getMsg(), e.getMessage());
        }
    }

    private  static void listStudents(String place, StudentSystemClass manager){
        Iterator<Student> it = manager.getStudentsAll();
        if(!it.hasNext()){
            if(place.equals("all")){
                System.out.println(Output.NST.getMsg());
            }
            else{
                System.out.printf(Output.NSF.getMsg(), place);
            }
        }
        // print nos alunos, alfabeticamente em caso de all, inserçao em pais
        while(it.hasNext()){
            Student s = it.next();

        }

    }

    private static void changeLocation(Scanner in, StudentSystemClass manager){
        try{
            String name = in.nextLine().trim();
            String location = in.nextLine().trim();

            boolean isDistracted =  manager.changeLocation(name, location);
            if (isDistracted){
                System.out.printf(Output.CHLD.getMsg(), name, location, name);
            } else {
                System.out.printf(Output.CHL.getMsg(), name, location);
            }
        }
        catch (Error1Exception e){
            System.out.printf(Output.NDNE.getMsg(), e.getMessage());
        } catch(Error2Exception e){
            System.out.printf(Output.UL.getMsg(), e.getMessage());
        } catch(Error3Exception e){
            System.out.printf(Output.IS.getMsg(), e.getMessage());
        } catch(Error4Exception e){
            System.out.printf(Output.AT.getMsg());
        } catch (Error5Exception e) {
            System.out.printf(Output.ESF.getMsg(), e.getMessage());
        }

    }

    private static void changeLodge(Scanner in, StudentSystemClass manager){
        try {
            String name = in.nextLine().trim();
            String location = in.nextLine(). trim();
            manager.changeLodge(name, location);

            System.out.printf(Output.LSUC.getMsg(), location, name, name);
        }
        catch (Error2Exception e){
            System.out.printf(Output.LDNE.getMsg(), e.getMessage());
        } catch(Error1Exception e){
            System.out.printf(Output.NDNE.getMsg(), e.getMessage());
        } catch(Error3Exception e){
            System.out.printf(Output.ISH.getMsg(), e.getMessage());
        } catch(Error4Exception e){
            System.out.println(Output.LSF.getMsg());
        } catch (Error5Exception e) {
            System.out.println(Output.MNAF.getMsg());
        }

    }


    private static void listUsersInService(Scanner in, StudentSystemClass manager){
        try {
            String order = in.next();
            String location = in.nextLine().trim();

            if (!order.equals("<") && !order.equals(">")){
                System.out.println(Output.ONE.getMsg());
            }

            TwoWayIterator<Student> it = manager.listStudentsInService(location);
            boolean oldFirst = order.equals(">");
            if(!oldFirst) {
                it.fullForward();
                while(it.hasPrevious()){
                    Student s = it.previous();
                    System.out.printf(Output.ONE.getMsg(), s.getName(), s.getType());
                }
            } else {
                while(it.hasNext()){
                    Student s = it.next();
                    System.out.printf(Output.ONE.getMsg(), s.getName(), s.getType());
                }
            }
        }
        catch (Error1Exception e) {
            System.out.printf(Output.NDNE.getMsg(), e.getMessage());
        } catch (Error2Exception e) {
            System.out.printf(Output.NCSE.getMsg(), e.getMessage());
        }
    }



}
