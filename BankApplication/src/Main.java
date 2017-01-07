import models.accounts.AccountType;
import models.accounts.CheckingAccount;

/**
 * Created by Man on 07.01.2017.
 */
public class Main {
    public static void main(String[] args) {
        CheckingAccount ck=new CheckingAccount(1000,500);
        System.out.println(ck.getClass().getSimpleName());
        System.out.println(ck.getType().toString());
        System.out.println(AccountType.CHECKING.toString());
    }
}
