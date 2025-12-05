/*
* @author Tomas Mata (63431) t.mata@campus.fct.unl.pt
* @author Rafael Pialgata (68672) r.pialgata@campus.fct.unl.pt
*/

import java.io.*;
import java.util.Scanner;

import Enums.Help;
import Enums.Output;
import Exceptions.*;
import System.*;
import dataStructures.*;

public class Main {

    private static final String HELP="help";
    private static final String EXIT="exit";
    private static final String BOUNDS="bounds";
    private static final String SAVE="save";
    private static final String LOAD="load";
    private static final String SERVICE="service";
    private static final String SERVICES="services";
    private static final String STUDENT="student";
    private static final String LEAVE="leave";
    private static final String STUDENTS="students";
    private static final String GO="go";
    private static final String MOVE="move";
    private static final String USERS="users";
    private static final String WHERE="where";
    private static final String VISITED="visited";
    private static final String STAR="star";
    private static final String RANKING="ranking";
    private static final String RANKED="ranked";
    private static final String TAG="tag";
    private static final String FIND="find";



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
            case HELP -> printHelp(); //done
            case EXIT -> { //done
                processSave(area);
                System.out.println(Output.EXIT.getMsg());
            }
            case BOUNDS -> area = processAddBound(in, area);
            case SAVE -> {
                processSave(area);
                if(area!=null){
                    System.out.printf(Output.AS.getMsg(), area.getName());
                }
            }
            case LOAD -> area = processLoad(in.nextLine().trim(),area);
            case SERVICE -> processAddService(in, area);
            case SERVICES -> listServices(area);
            case STUDENT -> processAddStudent(in, area);
            case LEAVE -> processRemoveStudent(in.nextLine().trim(), area);
            case STUDENTS -> listStudents(in.nextLine().trim(), area);
            case GO -> changeLocation(in, area);
            case MOVE -> changeLodge(in, area);
            case USERS -> listUsersInService(in, area);
            case WHERE -> getStudentLocation(in, area);
            case VISITED -> listVisitedServices(in, area);
            case STAR -> evaluate(in, area);
            case RANKING -> listSortedByRating(area);
            case RANKED -> getClosestRanked(in, area);
            case TAG -> listServiceReviewsTagged(in, area);
            case FIND -> findBestService(in, area);

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
        catch(ClassNotFoundException | IOException ignored){ }
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
                Student s = area.changeLodge(name, location);
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

                Service service = area.listStudentsInService(location);
                TwoWayIterator<Student> it = service.listStudentsInService();
                boolean oldFirst = order.equals(">");

                if(!it.hasNext())
                    System.out.printf(Output.NSO.getMsg(), service.getName());
                if(oldFirst) {
                    while(it.hasNext()){
                        Student s = it.next();
                        System.out.printf(Output.LUIS.getMsg(), s.getName(), s.getType());
                    }
                }
                else {
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
                if(s==null){
                    throw new Error1Exception(name);
                }
                Service service = s.getCurrentService();

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
