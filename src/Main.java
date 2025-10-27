import java.io.*;
import java.util.Scanner;

import Enums.Help;
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
        while(!command.equals("exit"));
        in.close();
    }

    public static void processCommand(Scanner in, StudentSystemClass manager, String command){

        switch (command.toLowerCase().trim()){
            case "help" -> printHelp();
            case "exit" -> {
                processSave(manager);
                System.out.println(Output.EXIT.getMsg());
            }
            case "bounds" -> processAddBound(in, manager);
            case "save" -> {
                processSave(manager);
                if(manager.getCurrentArea()!=null){
                    System.out.printf(AS, manager.getCurrentArea().getName());
                }
            }
            case "load" -> processLoad(in.nextLine().trim(),manager);
            case "service" -> processAddService(in, manager);
            case "services" -> listServices(manager);
            case "student" -> processAddStudent(in, manager);
            case "leave" -> processRemoveStudent(in.nextLine().trim(), manager);
            case "students" -> listStudents(in.nextLine().trim(), manager);
            case "go" -> changeLocation(in, manager);
            case "move" -> changeLodge(in, manager);
            case "users" -> listUsersInService(in, manager);
            case "where" -> getStudentLocation(in, manager);
            case "visited" -> listVisitedServices(in, manager);
            case "star" -> evaluate(in, manager);
            case "ranking" -> listSortedByRating(manager);

            default -> System.out.println(Output.UNKNOWN.getMsg());
        }
    }

    private static void printHelp(){
        for(Help h : Help.values()){
            System.out.printf(Output.HELP.getMsg(),h.name().toLowerCase(), h.getMsg());
        }
    }

    private static void processSave (StudentSystem system){
        try{
            if(system.getCurrentArea()==null){
                System.out.println(Output.BND.getMsg());
            }
            else{
                String fileName = "AREA_"+system.getCurrentArea().getName().toLowerCase();
                ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName));
                oos.writeObject(system);
                oos.flush();
                oos.close();
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }


    private static void processLoad(String name, StudentSystemClass manager){
        try{
            String fileName = "AREA_"+name.toLowerCase().trim();
            ObjectInputStream in = new ObjectInputStream(new FileInputStream(fileName));
            StudentSystemClass system = (StudentSystemClass) in.readObject();
            if(manager.getCurrentArea()!=null){
                processSave(manager);
            }
            manager.changeArea(system.getCurrentArea());
            System.out.printf(Output.BL.getMsg(), manager.getCurrentArea().getName());
            in.close();
        } catch (FileNotFoundException e){
            System.out.printf(Output.NB.getMsg(), name);
        }
        catch(ClassNotFoundException | IOException e){
            throw new RuntimeException(e);
        }
    }

    private static void processAddBound(Scanner in, StudentSystemClass manager){
        long topLeftLat = in.nextLong();
        long topLeftLong = in.nextLong();
        long bottomRightLat = in.nextLong();
        long bottomRightLong = in.nextLong();
        String name = in.nextLine().trim();


        try{
            if(manager.getCurrentArea()!=null && manager.getCurrentArea().getName().equals(name)){
                System.out.println(Output.BAE.getMsg());
            }
            else{
                String fileName = "AREA_"+name.toLowerCase();
                ObjectInputStream inn = new ObjectInputStream(new FileInputStream(fileName));
                System.out.println(Output.BAE.getMsg());
            }
        }
        catch (FileNotFoundException e){
            if(topLeftLat<=bottomRightLat || bottomRightLong<=topLeftLong){
                System.out.println(Output.IB.getMsg());
            }
            else {
                if(manager.getCurrentArea()!=null){
                    processSave(manager);
                }
                manager.createNewArea(name, topLeftLat, topLeftLong, bottomRightLat, bottomRightLong);
                System.out.printf(Output.BOUNDS.getMsg(), name);
            }
        }
        catch(IOException e){
            throw new RuntimeException(e);
        }
    }

    private static void processAddService(Scanner in, StudentSystemClass manager) throws AlreadyExistsObjectException {
        String type = in.next().toLowerCase().trim();
        long lat = in.nextLong();
        long lng = in.nextLong();
        int price = in.nextInt();
        int value = in.nextInt();
        String name = in.nextLine().trim();

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
        String type = in.nextLine().trim().toLowerCase();
        String name = in.nextLine().trim();
        String country = in.nextLine().trim();
        String currentLodge = in.nextLine().trim();


        try{
            if(!type.equals("bookish") && !type.equals("outgoing") && !type.equals("thrifty")){
                System.out.println(Output.IST.getMsg());
            }
            else{
                manager.addStudent(type, name, country, currentLodge);
                System.out.printf(Output.SC.getMsg(), name);
            }
        }
        catch (Error1Exception e){
            System.out.printf(Output.LDNE.getMsg(), e.getMessage());
        }
        catch (Error2Exception e){
            System.out.printf(Output.LIF.getMsg(), e.getMessage());
        }
        catch (Error3Exception e){
            System.out.printf(Output.ALREADY_EXISTS.getMsg(), e.getMessage());
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
        try{

            Iterator<Student> it = manager.getStudentsAll(place);
            while(it.hasNext()){
                Student s = it.next();
                String type = null;
                if(s instanceof BookishStudent){
                    type = "bookish";
                }
                else if(s instanceof OutgoingStudent){
                    type = "outgoing";
                }
                else if(s instanceof ThriftyStudent){
                    type = "thrifty";
                }
                System.out.printf(Output.PRINT_STUDENT.getMsg(), s.getName(), type, s.getCurrentLodge().getName());

            }
        }
        catch(Error1Exception e){
            if(e.getMessage().equals("all")){
                System.out.println(Output.NST.getMsg());
            }
            else {
                System.out.printf(Output.NSF.getMsg(), place);
            }
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
            System.out.println(Output.AT.getMsg());
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
                    System.out.printf(Output.LUIS.getMsg(), s.getName(), s.getType());
                }
            } else {
                while(it.hasNext()){
                    Student s = it.next();
                    System.out.printf(Output.LUIS.getMsg(), s.getName(), s.getType());
                }
            }
        }
        catch (Error1Exception e) {
            System.out.printf(Output.NDNE.getMsg(), e.getMessage());
        } catch (Error2Exception e) {
            System.out.printf(Output.NCSE.getMsg(), e.getMessage());
        }
    }

    private static void getStudentLocation(Scanner in, StudentSystemClass manager){
        try {
            String name = in.nextLine().trim();
            Service service = manager.getStudentCurrentService(name);
            Student s = manager.getStudent(name);

            System.out.printf(Output.SLOC.getMsg(),s.getName(), service.getName(), service.getType(), service.getLocation().getLatitude(), service.getLocation().getLongitude());
        } catch (Error1Exception e) {
            System.out.printf(Output.NDNE.getMsg(), e.getMessage());
        }
    }

    private static void listVisitedServices(Scanner in, StudentSystemClass manager){
        try {
            String name = in.nextLine().trim();

            Iterator<Service> it = manager.listVisitedServices(name);
            while(it.hasNext()){
                Service s = it.next();
                System.out.println(s.getName());
            }
        } catch (Error1Exception e) {
            System.out.printf(Output.NDNE.getMsg(), e.getMessage());
        } catch (Error2Exception e) {
            System.out.printf(Output.THR.getMsg(), e.getMessage());
        } catch (Error3Exception e) {
            System.out.printf(Output.NVL.getMsg(), e.getMessage());
        }
    }

    private static void evaluate(Scanner in, StudentSystemClass manager){
        try {
            int stars = Integer.parseInt(in.next());
            String location = in.nextLine().trim();
            String description = in.nextLine().trim();

            if(stars<1 || stars>5)
                System.out.println(Output.IEV.getMsg());
            else {
                manager.evaluateService(stars, location, description);
                System.out.println(Output.EVAL.getMsg());
            }

        }   catch (Error1Exception e) {
            System.out.printf(Output.NDNE.getMsg(), e.getMessage());
        }
    }

    private static void listSortedByRating(StudentSystemClass manager){
        try {
            Iterator<Service> it = manager.listServicesByRating();
            while(it.hasNext()){
                Service s = it.next();
                System.out.printf(Output.LSBR.getMsg(), s.getName(), Math.round(s.getAvgRating()));
            }
        } catch (Error1Exception e) {
            System.out.printf(Output.NIS.getMsg(), e.getMessage());
        }

    }

}
