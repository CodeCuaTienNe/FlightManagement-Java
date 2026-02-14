/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.util.List;
import utils.Validation;

/**
 *
 * @author 2004d
 */
public class Crew implements Serializable {

    private String name;
    private String position;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public Crew(String name, String position) {
        this.name = name;
        this.position = position;
    }

    public static String crewListToString(List<Crew> crewMembers) {
        StringBuilder sb = new StringBuilder();
        sb.append("+-------------------+-------------------+\n");
        sb.append("|        Name       |     Position      |\n");
        sb.append("+-------------------+-------------------+\n");
        for (Crew crew : crewMembers) {
            sb.append(String.format("|%-18s |%-18s |\n", crew.getName(), crew.getPosition()));
            sb.append("+-------------------+-------------------+\n");
        }

        return sb.toString();
    }

    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();
        sb.append(String.format("|%-18s |%-18s |\n", getName(), getPosition()));
        sb.append("+-------------------+-------------------+\n");
        return sb.toString();
    }
}
