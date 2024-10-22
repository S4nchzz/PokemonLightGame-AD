package org.lightPoke;

import org.lightPoke.auth.Login;
import org.lightPoke.auth.Register;
import org.lightPoke.log.LogManagement;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Game {
    private static LogManagement log = LogManagement.getInstance();
    public static void main(String[] args) {
        Login log = Login.getInstance();
        Register reg = Register.getInstance();

        reg.register("iyan", "pass");
    }
}