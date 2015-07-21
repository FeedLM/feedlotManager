/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package domain;

import abstractt.ComboBox;
//import domain.Animal.

/**
 *
 * @author Developer GAGS
 */
public class AnimalSelector extends ComboBox{
    
    public AnimalSelector(){
        
    }
    
    public void cargararete_visuals(){
        
        addArray(domain.Animal.cargararete_visuals());        
    }
    
     public void cargararete_visualshembrasSinEmparejar(){
        
        addArray(domain.Animal.cargararete_visualshembrasSinEmparejar());        
    }
    
    public void cargarTagsIdsSementales(){
        
        addArray(domain.Animal.cargararete_visualsSementales());
    }
}