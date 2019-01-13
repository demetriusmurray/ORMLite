import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.UpdateBuilder;

import java.sql.SQLException;
import java.util.List;

public class AccountService {
    private Dao<Account,Integer> accountDao;

    public AccountService(Dao accountDao){
        this.accountDao = accountDao;
    }

    public Account selectAccount(String input) throws Exception{
        QueryBuilder<Account,Integer> qb = accountDao.queryBuilder();
        qb.where().eq(Account.ID_FIELD_NAME, input);
        List<Account> list = accountDao.query(qb.prepare());
            return list.get(0);
    }

    public int create(Account account) throws SQLException {
        accountDao.create(account);
        int id = account.getId();
        System.out.println(id);
        return 1;
    }

    public List<Account> query() throws SQLException {
        return accountDao.queryForAll();
    }

    public List<Account> queryId(String input) throws SQLException {
        QueryBuilder<Account, Integer> qb = accountDao.queryBuilder();
        qb.where().eq(Account.ID_FIELD_NAME, input);
        return qb.query();
    }

    public List<Account> queryName(String input) throws SQLException {
        QueryBuilder<Account, Integer> qb = accountDao.queryBuilder();
        qb.where().eq(Account.NAME_FIELD_NAME, input);
        return qb.query();
    }

    public List<Account> queryPassword(String input) throws SQLException {
        QueryBuilder<Account, Integer> qb = accountDao.queryBuilder();
        qb.where().eq(Account.PASSWORD_FIELD_NAME, input);
        return qb.query();
    }

    public int update(Account account) throws SQLException {
        return accountDao.update(account);
    }

    public int updateName(Account account, String input) throws SQLException {
        UpdateBuilder<Account, Integer> ub = accountDao.updateBuilder();
        ub.where().eq(Account.ID_FIELD_NAME, account.getId());
        ub.updateColumnValue(Account.NAME_FIELD_NAME, input);
        return ub.update();
    }

    public int updatePassword(Account account, String input) throws SQLException {
        UpdateBuilder<Account, Integer> ub = accountDao.updateBuilder();
        ub.where().eq(Account.ID_FIELD_NAME, account.getId());
        ub.updateColumnValue(Account.PASSWORD_FIELD_NAME, input);
        return ub.update();
    }

    public int delete(Account account) throws SQLException {
        return accountDao.delete(account);
    }

    public int deleteId(String input) throws SQLException {
        DeleteBuilder<Account, Integer> db = accountDao.deleteBuilder();
        db.where().eq(Account.ID_FIELD_NAME, input);
        return db.delete();

    }

    public int deleteName(String input) throws SQLException {
        DeleteBuilder<Account, Integer> db = accountDao.deleteBuilder();
        db.where().eq(Account.NAME_FIELD_NAME, input);
        return db.delete();
    }

    public int deletePassword(String input) throws SQLException {
        DeleteBuilder<Account, Integer> db = accountDao.deleteBuilder();
        db.where().eq(Account.PASSWORD_FIELD_NAME, input);
        return db.delete();
    }

    public int deleteAll(List<Account> accounts) throws SQLException {
        return accountDao.delete(accounts);
    }
}
