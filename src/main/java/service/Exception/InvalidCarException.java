package service.Exception;

public class InvalidCarException extends Exception{
    public InvalidCarException() {
        System.out.println("Dont have that car.");
    }
}
