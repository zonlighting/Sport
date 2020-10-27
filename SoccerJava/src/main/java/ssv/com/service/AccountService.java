package ssv.com.service;

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
				return accountRepository.findByEmail(account.getEmail());
			}
		}
		return null;
	}

	public boolean checkLogin(Account account) {
		for (Account accountExits : accountRepository.findAll()) {
			if (StringUtils.equals(account.getEmail(), accountExits.getEmail())
					&& BCrypt.checkpw(account.getPassword(), accountExits.getPassword())) {
				return true;
			}
		}
		return false;
	}

	public boolean checkEmail(String email) {
		if (accountRepository.findByEmail(email) != null) {
			return false;
		}
		return true;
	}

	public void create(Account acount) {
		try {
			accountRepository.create(acount);
		} catch (Exception e) {
			ResponseQuery.faild("Create profile failed", acount);
		}
	}

}
