import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;
import java.util.List;

public class AccountApp {
    private static final String DATABASE_URL = "jdbc:mysql://localhost:3306/orm_lab?useUnicode=true";
    private Dao<Account, Integer> accountDao;
    private String username;
    private String password;
    private static final Console console = new Console(System.in, System.out);
    private AccountService accountService;

    public static void main(String[] args) throws Exception {
        // turn our static method into an instance of Main
        new AccountApp().doMain(args);
    }

    private void doMain(String[] args) throws Exception{
        ConnectionSource connectionSource = null;
        console.welcome();
        requestCredentials();
        try {
            connectionSource = new JdbcConnectionSource(DATABASE_URL, username, password);
            setupDao(connectionSource);
            accountService = new AccountService(accountDao);
            boolean open = true;
            while (open){
                String input = console.mainMenu();
                if (!String.valueOf(input).trim().equals("quit")) {
                    try {
                        crudOperations(String.valueOf(input).trim());
                    } catch (NullPointerException np) {
                    } finally {
                        console.returnToMain();
                    }
                } else {
                    open = false;
                    console.goodBye();
                }
            }

        } finally {
            if (connectionSource != null)
                connectionSource.close();
        }
    }

    private void requestCredentials() {
        console.print(">> To connect to database, please provide username and password\n");
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        username = console.getName();
        password = console.getPassword();
    }

    private void crudOperations(String input) throws Exception {
        if (input.equals("new account")) {
            newAccount();
        } else if (input.equals("update")) {
            try {
                Account acct = accountService.selectAccount(console.getId());
                updateBy(acct);
            } catch (IndexOutOfBoundsException np) {
                console.accountDNE();
            }
        } else if (input.equals("query")) {
            console.printAccounts(query());
        } else if (input.equals("delete")) {
            delete();
        }
    }

    private void newAccount() throws Exception {
        String name = console.getName();
        String p = console.getPassword();
        Account account = new Account(name, p);
        accountService.create(account);
    }

    private void updateBy(Account accounts) throws Exception{
        String input = console.getBy("update");
        String inputVal = String.valueOf(input).trim();

        if (inputVal.equals("password")) {
                updatePassword(accounts);
            } else if (inputVal.equals("name")) {
                updateName(accounts);
            } else if (inputVal.equals("account")) {
                updateName(accounts);
                updatePassword(accounts);
            }
        }
 //   }

    private void updateName(Account account) throws SQLException{
        String name = console.getName();
        account.setName(name);
        accountService.updateName(account, name);
    }
    private void updatePassword(Account account) throws SQLException {
        String pw = console.getPassword();
        account.setPassword(pw);
        accountService.update(account);
    }

    private List<Account> query() throws SQLException{
        List<Account> query = null;
        String input = console.getAllorBy("query");
        String inputVal = String.valueOf(input).trim();

        if (inputVal.equals("all")) {
            query = queryAll();

        } else if (inputVal.equals("by")) {
            query = queryBy();

        } else if (inputVal.equals("back")) {
        } else {
            query = query();
        }

        return query;
    }

    private List<Account> queryAll() throws SQLException {
        return accountService.query();
    }

    private List<Account> queryBy() throws SQLException {
        String input = console.getBy("query");
        String inputVal = String.valueOf(input).trim();
        if (inputVal.equals("name")) {
            String in = console.getName();
            return accountService.queryName(in);

        } else if (inputVal.equals("id")) {
            String in = console.getId();
            return accountService.queryId(in);

        } else if (inputVal.equals("password")) {
            String in = console.getPassword();
            return accountService.queryPassword(in);

        } else if (inputVal.equals("back")) {
            return null;

        } else {
            return queryBy();
        }
    }

    private int delete() throws SQLException {
        String input = console.getAllorBy("delete");

        if (String.valueOf(input).trim().equals("all")) {
            if (console.areYouSure_delete().equals("YES"))
                return accountService.deleteAll(queryAll());
        } else if (String.valueOf(input).trim().equals("by")) {
            return deleteBy();
        }

        return 0;
    }

    private int deleteBy() throws SQLException {
        String input = console.getBy("delete");
        String inputVal = String.valueOf(input).trim();

        if (inputVal.equals("name")) {
            String in = console.getName();
            return accountService.deleteName(in);

        } else if (inputVal.equals("id")) {
            String in = console.getId();
            return accountService.deleteId(in);

        } else if (inputVal.equals("password")) {
            String in = console.getPassword();
            return accountService.deletePassword(in);

        } else if (inputVal.equals("back")) {
            return 0;

        } else {
            return deleteBy();
        }
    }

    private void setupDao(ConnectionSource connectionSource) throws SQLException {
        accountDao = DaoManager.createDao(connectionSource, Account.class);
    }

}
