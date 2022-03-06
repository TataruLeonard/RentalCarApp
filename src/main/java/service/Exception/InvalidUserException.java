package service.Exception;

public class InvalidUserException extends Exception{
    public InvalidUserException() {
        System.out.println("Invalid username or passowrd.");
    }
}
