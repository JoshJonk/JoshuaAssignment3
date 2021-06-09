/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package za.ac.mycput;

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

/**
 * Student number: 215162668
 * @author Joshua Jonkers
 */
public class RunSerializableFileClass {
    
    ObjectInputStream input;
    Customer customer;
    Supplier supplier;
    Customer[] customerArray = new Customer[6];
    Supplier[] supplierArray = new Supplier[5];

    public void openFile(){
        try{
            input = new ObjectInputStream( new FileInputStream( "stakeholder.ser" ) ); 
            System.out.println("*** ser file opened for reading ***");               
        }
        catch (IOException ioe){
            System.out.println("error opening ser file: " + ioe.getMessage());
            System.exit(1);
        }
    }    
        
    public void closeFile(){
        try{
        input.close( ); 
        }
        catch (IOException ioe){            
            System.out.println("error closing ser file: " + ioe.getMessage());
            System.exit(1);
        } 
    
    public void readFromFile(){
        try
        {
            for(int i= 0; i< customerArray.length; i++){
                customerArray[i]= (Customer)input.readObject();
                System.out.println(customerArray[i]);
            }
            for(int k= 0; k< supplierArray.length; k++){
                supplierArray[k]= (Supplier)input.readObject();
                System.out.println(supplierArray[k]);
            }    
        }//end try
        catch(EOFException eofe)
        {
            System.out.println("EOF reached");
        }
        catch(ClassNotFoundException ioe)
        {
            System.out.println("Class error reading ser file: " + ioe);
        }
        catch(IOException ioe)
        {
            System.out.println("Error reading from ser file: " + ioe);
            System.exit(1);
        }
        finally{
            closeFile();
            System.out.println("*** file has been closed ***");               
        }    
    }

    public static void main(String[] args){
        
        RunSerializableFileClass obj= new RunSerializableFileClass();
        obj.openFile();
        obj.readFromFile();
        obj.closeFile();
    }
}

