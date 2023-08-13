package CustomError;



public class HospitalNotFoundException extends RuntimeException {

    public HospitalNotFoundException(String message) {
        super(message);
    }
    
    // Vous pouvez ajouter d'autres constructeurs et méthodes si nécessaire
}
