class Test {
    private Bank bank;

    public static void main(String[] args) {
        String name = String.valueOf(args[0]);
        double bal = Double.parseDouble(args[1]);

        Bank testBank = new Bank(name, bal);
        Bank testBank2 = new Bank(name, bal);

        testBank.withdraw(testBank2.getBalance());
        testBank.deposit(testBank2.getBalance());

        testBank.withdraw(100);
    }
}