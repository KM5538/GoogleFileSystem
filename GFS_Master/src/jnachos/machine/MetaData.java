/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jnachos.machine;

/**
 *
 * @author Neha
 */
public class MetaData {
         
        public String filename; //GFS filename
        
        public int chunk_index; //GFS chunk index
        
        public String chunk_handle; //GFS chunk handle
        
        public String chunk_locations[] = new String[50]; //GFS chunk locations
        
        public int byte_start; //GFS byte range start 
        
        public int byte_end; //GFS byte range end
}
