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

        Area area = null;

        String command;
        do{
            command = in.next();
            area = processCommand(in, area, command);
        }
        while(!command.equals("exit"));
        in.close();
    }

    public static Area processCommand(Scanner in, Area area, String command){

        switch (command.toLowerCase().trim()){
            case "help" -> printHelp(); //done
            case "exit" -> { //done
                processSave(area);
                System.out.println(Output.EXIT.getMsg());
            }
            case "bounds" -> area = processAddBound(in, area);
            case "save" -> {
                processSave(area);
                if(area!=null){
                    System.out.printf(AS, area.getName());
                }
            }
            case "load" -> area = processLoad(in.nextLine().trim(),area);
            case "service" -> processAddService(in, area);
            case "services" -> listServices(area);
            case "student" -> processAddStudent(in, area);
            case "leave" -> processRemoveStudent(in.nextLine().trim(), area);
            case "students" -> listStudents(in.nextLine().trim(), area);
            case "go" -> changeLocation(in, area);
            case "move" -> changeLodge(in, area);
            case "users" -> listUsersInService(in, area);
            case "where" -> getStudentLocation(in, area);
            case "visited" -> listVisitedServices(in, area);
            case "star" -> evaluate(in, area); //done
            case "ranking" -> listSortedByRating(area); //done
            case "ranked" -> getClosestRanked(in, area); //done
            case "tag" -> listServiceReviewsTagged(in, area); //done
            case "find" -> findBestService(in, area); //doing

            default -> System.out.println(Output.UNKNOWN.getMsg());
        }

        return area;
    }

    private static void printHelp(){
        for(Help h : Help.values()){
            System.out.printf(Output.HELP.getMsg(),h.name().toLowerCase(), h.getMsg());
        }
    }

    private static void processSave (Area area){
        if(area==null){
            System.out.println(Output.BND.getMsg());
        }
        else{
            String fileName = "AREA_" + area.getName().toLowerCase().replaceAll(" ", "_")+".ser";
            try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName));){
                oos.writeObject(area);
            } catch (IOException ignored) {

            }
        }
    }


    private static Area processLoad(String name, Area area){
        String fileName = "AREA_" + name.toLowerCase().replaceAll(" ", "_")+".ser";

        try(ObjectInputStream in = new ObjectInputStream(new FileInputStream(fileName))){
            if(area!=null){
                processSave(area);
            }
            area = (AreaClass) in.readObject();
            System.out.printf(Output.BL.getMsg(), area.getName());
            return area;
        } catch (FileNotFoundException e){
            System.out.println(Output.SBND.getMsg());
        }
        catch(ClassNotFoundException | IOException ignored){
        }
        return area;
    }

    private static Area processAddBound(Scanner in, Area area){
        long topLeftLat = in.nextLong();
        long topLeftLong = in.nextLong();
        long bottomRightLat = in.nextLong();
        long bottomRightLong = in.nextLong();
        String name = in.nextLine().trim();

        try{
            if(area!=null && area.getName().equalsIgnoreCase(name)){
                System.out.println(Output.BAE.getMsg());
            }
            else{
                String fileName = "AREA_"+name.toLowerCase().replaceAll(" ","_")+".ser";
                ObjectInputStream inn = new ObjectInputStream(new FileInputStream(fileName));
                System.out.println(Output.BAE.getMsg());
                inn.close();
            }
        }
        catch (FileNotFoundException e){
            if(topLeftLat<=bottomRightLat || bottomRightLong<=topLeftLong){
                System.out.println(Output.IB.getMsg());
            }
            else {
                if(area!=null){
                    processSave(area);
                }
                area = new AreaClass(name, topLeftLat, topLeftLong, bottomRightLat, bottomRightLong);
                System.out.printf(Output.BOUNDS.getMsg(), name);
            }
        } catch(IOException e){
            throw new RuntimeException(e);
        }

        return area;
    }

    private static void processAddService(Scanner in, Area area) {
        String type = in.next().toLowerCase().trim();
        long lat = in.nextLong();
        long lng = in.nextLong();
        int price = in.nextInt();
        int value = in.nextInt();
        String name = in.nextLine().trim();

        if(area==null){
            System.out.println(Output.BND.getMsg());
        }
        //check invalid type
        else if(!type.equals("eating") && !type.equals("lodging") && !type.equals("leisure")){
            System.out.println(Output.IT.getMsg());
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
                LocationClass loc = new LocationClass(lat, lng);
                area.addService(type, name, value, price, loc);
                System.out.printf(Output.SA.getMsg(), type, name);
            }catch (Error1Exception e) {
                System.out.println(Output.IL.getMsg());
            } catch (AlreadyExistsObjectException e){
                System.out.printf(Output.ALREADY_EXISTS.getMsg(), e.getMessage());
            }
        }
    }

    private static void listServices (Area area){
        if(area==null){
            System.out.println(Output.BND.getMsg());
        }
        else{
            Iterator<Service> it = area.getServicesAll();
            //check if empty
            if(!it.hasNext()){
                System.out.printf(Output.NS.getMsg());
            }
            while(it.hasNext()){
                Service s = it.next();
                System.out.printf(Output.PRINT_SERVICE.getMsg(), s.getName(), s.getType(), s.getLocation().getLatitude(), s.getLocation().getLongitude());
            }
        }
    }

    private static void processAddStudent(Scanner in, Area area){
        String type = in.nextLine().trim().toLowerCase();
        String name = in.nextLine().trim();
        String country = in.nextLine().trim();
        String currentLodge = in.nextLine().trim();

        try{
            if(area==null){
                System.out.println(Output.BND.getMsg());
            }
            else if(!type.equals("bookish") && !type.equals("outgoing") && !type.equals("thrifty")){
                System.out.println(Output.IST.getMsg());
            }
            else{
                area.addStudent(type, name, country, currentLodge);
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

    private static void processRemoveStudent(String name, Area area){
        try{
            if(area==null){
                System.out.println(Output.BND.getMsg());
            } else {
                Student s = area.removeStudent(name);
                System.out.printf(Output.SHL.getMsg(), s.getName());
            }
        }
        catch (Error1Exception e){
            System.out.printf(Output.NDNE.getMsg(), e.getMessage());
        }
    }

    private  static void listStudents(String place, Area area){
        try{
            if(area==null){
                System.out.println(Output.BND.getMsg());
            } else {
                Iterator<Student> it = area.getStudentsAll(place);
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
                    System.out.printf(Output.PRINT_STUDENT.getMsg(), s.getName(), type, s.getCurrentService().getName());

                }
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

    private static void changeLocation(Scanner in, Area area){
        try{
            String name = in.nextLine().trim();
            String location = in.nextLine().trim();

            if(area==null){
                System.out.println(Output.BND.getMsg());
            } else {
                boolean isDistracted =  area.changeLocation(name, location);
                Student student = area.getStudent(name);
                Service newService = student.getCurrentService();

                if (isDistracted){
                    System.out.printf(Output.CHLD.getMsg(), student.getName(),newService.getName(), student.getName());
                } else {
                    System.out.printf(Output.CHL.getMsg(), student.getName(), newService.getName());
                }
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

    private static void changeLodge(Scanner in, Area area){
        try {
            String name = in.nextLine().trim();
            String location = in.nextLine(). trim();

            if(area==null){
                System.out.println(Output.BND.getMsg());
            } else {
                area.changeLodge(name, location);
                Student s = area.getStudent(name);
                System.out.printf(Output.LSUC.getMsg(), s.getCurrentLodge().getName(),s.getName(), s.getName());
            }
        }
        catch (Error2Exception e){
            System.out.printf(Output.LDNE.getMsg(), e.getMessage());
        } catch(Error1Exception e){
            System.out.printf(Output.NDNE.getMsg(), e.getMessage());
        } catch(Error3Exception e){
            System.out.printf(Output.ISH.getMsg(), e.getMessage());
        } catch(Error4Exception e){
            System.out.printf(Output.LSF.getMsg(), e.getMessage());
        } catch (Error5Exception e) {
            System.out.printf(Output.MNAF.getMsg(), e.getMessage());
        }

    }

    private static void listUsersInService(Scanner in, Area area){
        try {
            String order = in.next();
            String location = in.nextLine().trim();

            if(area==null){
                System.out.println(Output.BND.getMsg());
            } else if (!order.equals("<") && !order.equals(">")){
                System.out.println(Output.ONE.getMsg());
            } else {
                TwoWayIterator<Student> it = area.listStudentsInService(location);
                boolean oldFirst = order.equals(">");
                Service service = area.getService(location);

                if(!it.hasNext())
                    System.out.printf(Output.NSO.getMsg(), service.getName());
                if(oldFirst) {
                    while(it.hasNext()){
                        Student s = it.next();
                        System.out.printf(Output.LUIS.getMsg(), s.getName(), s.getType());
                    }
                } else {
                    it.fullForward();
                    while(it.hasPrevious()){
                        Student s = it.previous();
                        System.out.printf(Output.LUIS.getMsg(), s.getName(), s.getType());
                    }
                }
            }

        }
        catch (Error1Exception e) {
            System.out.printf(Output.NDNE.getMsg(), e.getMessage());
        } catch (Error2Exception e) {
            System.out.printf(Output.NCSE.getMsg(), e.getMessage());
        }
    }

    private static void getStudentLocation(Scanner in, Area area){
        try {
            String name = in.nextLine().trim();

            if(area==null){
                System.out.println(Output.BND.getMsg());
            } else {
                Student s = area.getStudent(name);
                Service service = area.getStudentCurrentService(name);

                System.out.printf(Output.SLOC.getMsg(), s.getName(), service.getName(), service.getType(), service.getLocation().getLatitude(), service.getLocation().getLongitude());
            }

        } catch (Error1Exception e) {
            System.out.printf(Output.NDNE.getMsg(), e.getMessage());
        }
    }

    private static void listVisitedServices(Scanner in, Area area){
        try {
            String name = in.nextLine().trim();

            if(area==null){
                System.out.println(Output.BND.getMsg());
            } else {
                Iterator<Service> it = area.listVisitedServices(name);
                while(it.hasNext()){
                    Service s = it.next();
                    System.out.println(s.getName());
                }
            }

        } catch (Error1Exception e) {
            System.out.printf(Output.NDNE.getMsg(), e.getMessage());
        } catch (Error2Exception e) {
            System.out.printf(Output.THR.getMsg(), e.getMessage());
        } catch (Error3Exception e) {
            System.out.printf(Output.NVL.getMsg(), e.getMessage());
        }
    }

    private static void evaluate(Scanner in, Area area){
        try {
            int stars = Integer.parseInt(in.next());
            String location = in.nextLine().trim();
            String description = in.nextLine().trim();

            if(area==null){
                System.out.println(Output.BND.getMsg());
            } else if(stars<1 || stars>5) {
                System.out.println(Output.IEV.getMsg());
            } else {
                area.evaluateService(stars, location, description);
                System.out.println(Output.EVAL.getMsg());
            }
        }   catch (Error1Exception e) {
            System.out.printf(Output.NDNE.getMsg(), e.getMessage());
        }
    }

    private static void listSortedByRating(Area area){
        try {
            if(area!=null){
                System.out.println(Output.SS.getMsg());
                Iterator<Service> it = area.listServicesByRating();
                while(it.hasNext()){
                    Service s = it.next();
                    System.out.printf(Output.LSBR.getMsg(), s.getName(), s.getAvgRating());
                }
            }
            else {
                System.out.printf(Output.NIS.getMsg(), "");
            }
        } catch (Error1Exception e) {
            System.out.printf(Output.NIS.getMsg(), e.getMessage());
        }

    }

    private static void getClosestRanked(Scanner in, Area area){
        try {
            String type = in.next();
            int stars = Integer.parseInt(in.next());
            String name = in.nextLine().trim();

            if(area==null){
                System.out.println(Output.BND.getMsg());
            } else if(stars<1 || stars>5) {
                System.out.println(Output.ISE.getMsg());
            } else {
                Iterator<Service> it = area.listClosestServiceRanked(stars, type, name);
                System.out.printf(Output.SCL.getMsg(), type, stars);
                while (it.hasNext()) {
                    Service s = it.next();
                    System.out.println(s.getName());
                }
            }

        } catch (Error1Exception e) {
            System.out.printf(Output.NDNE.getMsg(), e.getMessage());
        } catch (Error2Exception e) {
            System.out.println(Output.IT.getMsg());
        } catch (Error3Exception e) {
            System.out.printf(Output.NSWT.getMsg(), e.getMessage());
        } catch (Error4Exception e) {
            System.out.printf(Output.NSWA.getMsg(), e.getMessage());
        }
    }

    private static void listServiceReviewsTagged(Scanner in, Area area){
        try {
            String tag = in.nextLine().trim();

            if(area==null){
                System.out.println(Output.BND.getMsg());
            }
            else {
                Iterator<Service> it = area.listServiceReviewsTagged(tag);
                while(it.hasNext()){
                    Service s = it.next();
                    System.out.printf(Output.SPA.getMsg(), s.getType(), s.getName());
                }
            }

        } catch (Error1Exception e) {
            System.out.println(Output.TSN.getMsg());
        }
    }

    private static void findBestService(Scanner in, Area area){
        try {
            String name = in.nextLine().trim();
            String type = in.nextLine().trim().toLowerCase();

            if(area==null){
                System.out.println(Output.BND.getMsg());
            } else {
                Service best = area.getBestService(name, type);
                System.out.println(best.getName());
            }

        } catch (Error2Exception e) {
            System.out.println(Output.IT.getMsg());
        } catch (Error1Exception e) {
            System.out.printf(Output.NDNE.getMsg(), e.getMessage());
        } catch (Error3Exception e) {
            System.out.printf(Output.NSWT.getMsg(), e.getMessage());
        }
    }

}
