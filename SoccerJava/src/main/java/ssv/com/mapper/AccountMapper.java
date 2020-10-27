package ssv.com.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import ssv.com.entity.Account;

@Mapper
public interface  AccountMapper {

	public List<Account> findAll();

	public Account findByEmail(String email);

	public Long create(Account account);
}
