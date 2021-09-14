/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package music;

import java.io.File;

/**
 *
 * @author root
 */
public class DeleteFile {
   DeleteFile(String filename)
   {
   File file=new File("C:\\Users\\Rajaram\\Documents\\NetBeansProjects\\Music\\Definitons\\"+filename);
   file.delete();
   }
}
