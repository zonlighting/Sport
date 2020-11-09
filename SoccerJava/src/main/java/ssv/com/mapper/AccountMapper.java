package ssv.com.mapper;

import java.util.List;
import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;

import ssv.com.entity.Account;
import ssv.com.entity.Profile;

@Mapper
public interface  AccountMapper {

	public List<Account> findAll();

	public Account findByEmail(String email);

	public Long create(Account account);

	public List<Account> getAll();

	public void replacePass(Account account);

}
