/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package za.ac.mycput;

import java.io.BufferedWriter;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;

/**
 * Student number: 215162668
 * @author Joshua Jonkers
 */
public class RunSerializableFileClass {
    
    BufferedWriter bw;
    FileWriter fw;
    InputStream inputStream;
    ObjectInputStream objStream;
    String stakeholder = "stakeholder.ser";
    
    public void myFile(String myFile)
    {
        try
        {
            fw = new FileWriter(new File(myFile));
            bw = new BufferedWriter(fw);
            System.out.println(" Has been created "+ myFile );
           
        } catch (IOException ioe)
        {
            System.exit(0);
        }
    }
    
    private String formatDate(String formDate)
    {
        DateTimeFormatter formatting = DateTimeFormatter.ofPattern(
                "dd MMM yyyy",
                Locale.ENGLISH);

        LocalDate parTime = LocalDate.parse(formDate);
        return parTime.format(formatting);
    }
    
    private ArrayList<Customer> cList()
    {
     ArrayList<Customer> customers = new ArrayList<>();
     try
     {
        inputStream = new FileInputStream(stakeholder);
        objStream = new ObjectInputStream(inputStream);
            while (true)
            {
                Object obj = objStream .readObject();
                if (obj instanceof Customer)
                {
                    customers.add((Customer) obj);
                }
            }
           
        } catch (EOFException eofe)
        {
           
        } catch (IOException | ClassNotFoundException e)
        {
           System.exit(0);
           
        } finally
        {
            try
            {
               bw.close();
               objStream.close();
               
            } catch (IOException e)
            {
            }
        }
        if (!customers.isEmpty())
        {
            Collections.sort(customers,
                (Customer sort, Customer sorted) ->
                    sort.getStHolderId().compareTo(sorted.getStHolderId())
            );
        }
        return customers;
    }
    public void readCustomer()
    {
        String format = "%s\t%-10s\t%-10s\t%-10s\t%-10s\n";
        String underLine = "===========================================================\n";
       
        try
        {  
            System.out.print( "======================= CUSTOMERS =========================\n");
           
            System.out.printf(format, "ID", "Name", "Surname",
                    "Date Of Birth", "Age");
           
            System.out.print(underLine);
           
            for (int i = 0; i < cList().size(); i++)
            {  
                System.out.printf(
                        format,
                        cList().get(i).getStHolderId(),
                        cList().get(i).getFirstName(),
                        cList().get(i).getSurName(),
                        formatDate(cList().get(i).getDateOfBirth()),
                        calculateAge(cList().get(i).getDateOfBirth())
                );
            }
           System.out.println("\nAmmount of Customers that can rent:" + canRent());
           System.out.println("\nAmmount of Customers who cannot rent:"+ canNotRent());
        } catch (Exception e)
        {
        }
    }
    
    private ArrayList<Supplier> sList()
    {
        ArrayList<Supplier> suppliers = new ArrayList<>();
       
        try
        {
            inputStream = new FileInputStream(new File(stakeholder));
            objStream = new ObjectInputStream(inputStream);
            while (true)
            {
                Object obj = objStream.readObject();
                if (obj instanceof Supplier)
                {
                    suppliers.add((Supplier) obj);
                }
            }
           
        } catch (EOFException eofe)
        {
         
        } catch (IOException | ClassNotFoundException e)
        {
        } finally
        {
            try
            {
                inputStream.close();
                objStream.close();
               
            } catch (IOException e)
            {
            }
        }
        if (!suppliers.isEmpty())
        {
            Collections.sort(
            suppliers,
            (Supplier supp1, Supplier supp2) ->
            supp1.getName().compareTo(supp2.getName())
            );
        }
        return suppliers;
    }
   
    public void readSupplier()
    {
        try
        {
            System.out.print("======================= SUPPLIERS =========================\n");
            System.out.printf("%s\t%-20s\t%-10s\t%-10s\n", "ID", "Name", "Prod Type",
                "Description");
            System.out.print("===========================================================\n");
            for (int i = 0; i < sList().size(); i++)
            {
               System.out.printf(
                        "%s\t%-20s\t%-10s\t%-10s\n",
                        sList().get(i).getStHolderId(),
                        sList().get(i).getName(),
                        sList().get(i).getProductType(),
                        sList().get(i).getProductDescription()
                );
            }
           
        } catch (Exception e)
        {
        }
    }
   
    private int calculateAge(String formDate)
    {
        LocalDate parseDob = LocalDate.parse(formDate);
        int dobYear  = parseDob.getYear();
        ZonedDateTime todayDate = ZonedDateTime.now();
        int currentYear = todayDate.getYear();
        return currentYear - dobYear;
    }
   
    private int canRent()
    {
        int canRent = 0;
        for (int i = 0; i < cList().size(); i++)
        {
            if (cList().get(i).getCanRent())
            {
                canRent += 1;
            }
        }
        return canRent;
    }
   
    private int canNotRent()
    {
        int canNotRent = 0;
        for (int i = 0; i < cList().size(); i++)
        {
            if (!cList().get(i).getCanRent())
            {
                canNotRent += 1;
            }
        }
        return canNotRent;
    }
   
    public void closeFile(String myFile)
    {
        try
        {
            fw.close();
            bw.close();
            System.out.println(myFile + " has been closed");

        } catch (IOException ioe)
        {
            
        }
    }
    
    public static void main(String[] args) 
    {
        RunSerializableFileClass obj = new RunSerializableFileClass();
        obj.myFile("customerOutput.txt");
        obj.readCustomer();
        obj.closeFile("customerOutput.txt");
        System.out.println("---------------------------------------------------");
        obj.myFile("supplierOutput.txt");
        obj.readSupplier();
        obj.closeFile("supplierOutput.txt");
    }
}

