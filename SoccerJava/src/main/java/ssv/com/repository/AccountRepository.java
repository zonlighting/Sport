package ssv.com.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ssv.com.entity.Account;
import ssv.com.mapper.AccountMapper;

@Repository
public class AccountRepository {

	@Autowired
	private AccountMapper accountMapper;

	public List<Account> findAll() {

		return accountMapper.findAll();
	}

	public Account findById(int id) {
		return accountMapper.findById(id);
	}


}
