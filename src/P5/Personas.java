/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package P5;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.*;

/**
 *
 * @author josep
 */
@XmlRootElement(name = "personas")
public class Personas {
    List<Persona> personas;
    
    public List<Persona> getPersonas(){
        return personas;
    }
    
    @XmlElement(name = "persona")
    public void setPersonas(List<Persona> personas){
        this.personas = personas;
    }
    public void add(Persona persona){
        if (this.personas == null){
            this.personas = new ArrayList<Persona>();
        }
        this.personas.add(persona);
    }
}
