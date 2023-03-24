package Utils;

public final class CardsUtils {
    private CardsUtils(){

    }

//    -----------------Generador de numero de cuenta-----------------------------
    public static String accountNumber(int dig) {
        String accountNumber = "";
        for(int i = 0; i < dig; i++) {
            int newNumber = (int) (Math.random() * 10);
            accountNumber += String.valueOf(newNumber);
        }
        return "VIN-"+accountNumber;
    }

//    ----------------------Generador de CVV para las CARDS-------------------------
    public static String generateCvv(int dig) {
         String newCvv = "";
         for(int i = 0; i < dig; i++) {
         int newNumber = (int) (Math.random() * 10);
         newCvv += String.valueOf(newNumber);
       }
         return newCvv;
    }
//    --------------------Generador de numeros para las CARDS--------------------
    public static String generateNumber(int dig) {
        String generateNumber = "";
        for(int i = 0; i < dig; i++) {
        int newNumber = (int) (Math.random() * 10);
        generateNumber += String.valueOf(newNumber);
        }
        return generateNumber;
    }
}
