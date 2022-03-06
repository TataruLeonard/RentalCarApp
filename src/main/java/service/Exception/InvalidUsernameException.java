package service.Exception;

public class InvalidUsernameException extends Exception{
    public InvalidUsernameException() {
        System.out.println("Username already exist");
    }
}
