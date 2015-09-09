/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import abstractt.ComboBox;
import static domain.Medicina.cargarCodigoMedicinas;
import static domain.Medicina.cargarMedicinas;

/**
 *
 * @author Developer GAGS
 */
public class MedicinaSelector extends ComboBox {

    private Medicina medicina;
    
    public MedicinaSelector() {

    }

    public void cargar() {

        addArray(cargarMedicinas());
    }

    public void cargar2() {

        addArray(cargarCodigoMedicinas());
    }

    public Medicina getMedicnaNombre() {

        medicina = new Medicina();

        medicina.cargarPorNombre(this.getSelectedItem().toString());

        return medicina;
    }
}