package view.models;
import server.models.Course;
import server.models.RegistrationForm;
import view.InscriptionFormView;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;


public class InscriptionModel {
    private ObjectOutputStream objectOutputStream;

    private ObjectInputStream objectInputStream;

    private final Socket clientSocket;
    public InscriptionModel(int port) throws IOException {
        this.clientSocket = new Socket("127.0.0.1", port);
    }
    private void mainPrompt(){
        int choice= InscriptionFormComp.getChoice(choice);
        String session = InscriptionFormView.getSelectedSession(selectedSession);
        if (choice==1){
            getCourses(String session);
        } else if (choice==2){
            inscription();
        }
    }
    public void start() {
        try {
            this.objectOutputStream = new ObjectOutputStream(clientSocket.getOutputStream());
            this.objectInputStream = new ObjectInputStream(clientSocket.getInputStream());

            mainPrompt();
        } catch (IOException e){
            e.printStackTrace();
        }
    }
    private void getCourses(String session)  {

        ArrayList<Course> courses;
        try{
            objectOutputStream.writeObject("CHARGER "+session);
            Object obj = objectInputStream.readObject();
            if (obj instanceof ArrayList) {
                courses = (ArrayList<Course>) obj;
                for (int i=0;i<courses.size();i++){
                    Course course = courses.get(i);
                    //System.out.println((i+1)+"\t"+course.getName()+"\t"+course.getCode());
                    //je dois mettre dans le composante un code qui affiche ce tableau
                }
            }
        } catch (IOException | ClassNotFoundException e){
            throw new RuntimeException(e);
        }
    }
    private void inscription(){
        try{
            objectOutputStream.writeObject("INSCRIRE");
            objectOutputStream.writeObject(form);
            if (objectInputStream.readBoolean()){
                System.out.println("Félicitations! Inscription réussie de "+prenom+" au cours "+cours+"\n");
            } else {
                System.out.println("Les informations entrées sont incorrectes.");
            }
        } catch (IOException e){
            e.printStackTrace();
        }
    }

}
