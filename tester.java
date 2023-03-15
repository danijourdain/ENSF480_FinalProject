
class tester{
    private boolean verify(String number){
       String CARD_REGEX ="^[0-9]{16}$";
        if(!number.matches(CARD_REGEX)){
            System.out.println("false");
            return false;
        }
        int sum = 0;
        boolean second = false;
        for(int i = 15; i>=0; i--){
            char c = number.charAt(i);
            int digit = Character.getNumericValue(c);
            if(i % 2 == 0){
                digit = digit*2;
            }
            sum += digit / 10;
            sum += digit % 10;
        }
        return(sum % 10 == 0);
    }
    public static void main(String args[]){
        tester tester = new tester();
        System.out.println(tester.verify("4716425508284581"));
    }
}
