/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.frostburg.butler.jon.application.logic;

import java.util.Objects;

/**
 *
 * @author Kevin Raoofi
 */
public class Description {

    private final String descriptionString;

    protected Description(String description) {
        this.descriptionString = description;
    }

    public String getDescriptionString() {
        return descriptionString;
    }

    @Override
    public String toString() {
        return "Description{" + "descriptionString=" + descriptionString + '}';
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 89 * hash + Objects.hashCode(this.descriptionString);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Description other = (Description) obj;
        if (!Objects.equals(this.descriptionString, other.descriptionString)) {
            return false;
        }
        return true;
    }

}
