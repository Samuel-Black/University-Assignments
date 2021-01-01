public class Bank {
    String name;
    double balance;

    public Bank(String name, double balance) {
        this.name = name;
        this.balance = balance;

    }

    @Override
    public String toString() {
        return name + " " + balance;
    }

    public double getBalance() {
        return this.balance;
    }

    public void deposit(double amount) {
        this.balance += amount;
    }

    public void withdraw(double amount) throws IllegalArgumentException {
        this.balance -= amount;
        if(this.balance < 0)
        {
          throw new IllegalArgumentException("Negative Balance");
        }
    }
}
