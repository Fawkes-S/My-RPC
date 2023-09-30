package service;

import java.util.List;

import org.springframework.stereotype.Service;

import model.User;

@Service
public class TestService {
	public void test(User user){
		System.out.println("调用了TestService.test");
	}

	public void saveUSerList(List<User> userlist) {
		System.out.println("调用了saveUSerList.test");
		
	}
}
