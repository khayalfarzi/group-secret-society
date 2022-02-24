package az.iktlab.group.secret_society;

import az.iktlab.group.secret_society.DB.SQL;
import az.iktlab.group.secret_society.console.Console;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        Console console = new Console();
        SQL.collectData();
        console.setDirection();
    }
}

