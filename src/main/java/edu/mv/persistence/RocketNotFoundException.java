/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.mv.persistence;

public class RocketNotFoundException extends RuntimeException {

    public RocketNotFoundException(int id) {
        super("Rocket with id " + id + " not found");
    }
}
