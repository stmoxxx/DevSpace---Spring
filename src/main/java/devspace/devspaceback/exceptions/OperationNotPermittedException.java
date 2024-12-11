package devspace.devspaceback.exceptions;

public class OperationNotPermittedException extends RuntimeException{

    public  OperationNotPermittedException (String msg){
        super(msg);
    }

}
