package ssv.com.service;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ssv.com.dto.ResponseQuery;

import ssv.com.entity.Account;
import ssv.com.repository.AccountRepository;

@Service
public class AccountService {

	@Autowired
	private AccountRepository accountRepository;

	public Account loadUserByEmail(String email) {
		for (Account account : accountRepository.findAll()) {
			if (account.getEmail().equals(email)) {
				return accountRepository.findById(account.getId());
			}
		}
		return null;
	}




	public List<Account> findAll() {
		return accountRepository.findAll();
	}

}
